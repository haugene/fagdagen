package controllers;

import com.avaje.ebean.Ebean;
import domain.enums.SlotType;
import models.Presentation;
import models.Slot;
import models.Track;
import models.Vote;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Result;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static play.data.Form.form;

/**
 * Controller for form post actions
 *
 * @author kristian.haugene
 * @author daniel.nordstrom.carlsen
 */
public class Forms extends Controller {

    /**
     * Reads a HTML form dynamically. Retrieves fields for a presentation, and stores it in the database.
     * @return redirect to index
     */
    public static Result savePresentation() {
        DynamicForm form = form().bindFromRequest();

        String name = getString(form, "name");
        String presenter = getString(form, "presenter");
        String description = getString(form, "description");
        String businessUnit = getString(form, "businessUnit");
        Long slotId = getLong(form, "slot");
        Long trackId = getLong(form, "track");
        Integer rank = getInteger(form, "rank");

        if(isBlank(name) || isBlank(presenter) || isBlank(businessUnit) || isBlank(description) || isNull(slotId) || isNull(trackId) || isNull(rank))
        {
            flash("form_result", "Could not save presentation, there were empty fields");
        } else
        {
            Slot slot = Slot.find.byId(slotId);
            Track track = Track.find.byId(trackId);

            Presentation presentation = new Presentation(name, description, presenter, businessUnit, slot, track, rank);
            Ebean.save(presentation);
        }

        return redirect(routes.Application.index());
    }

    /**
     * Reads a HTML form dynamically. Retrieves fields for a keynote, and stores it in the database.
     * @return redirect to index
     */
    public static Result saveKeynote()
    {
        DynamicForm form = form().bindFromRequest();

        String name = getString(form, "name");
        String presenter = getString(form, "presenter");
        String description = getString(form, "description");
        Long slotId = getLong(form, "slot");
        Long trackId = getLong(form, "track");
        Integer rank = getInteger(form, "rank");

        if(isBlank(name) || isBlank(presenter) || isBlank(description) || isNull(slotId) || isNull(trackId) || isNull(rank))
        {
            flash("form_result", "Could not save keynote, there were empty fields");
        } else
        {
            Slot slot = Slot.find.byId(slotId);
            Track track = Track.find.byId(trackId);

            // Save presentation with blank businessUnit, this is a keynote
            Presentation presentation = new Presentation(name, description, presenter, "", slot, track, rank);
            Ebean.save(presentation);
        }

        return redirect(routes.Application.index());
    }

    /**
     * Reads a HTML form dynamically. Retrieves fields for a presentation, and updates corresponding entry in the database.
     * @return redirect to index
     */
    public static Result editPresentation()
    {
        DynamicForm form = form().bindFromRequest();

        String name = getString(form, "name");
        String presenter = getString(form, "presenters");
        String description = getString(form, "abstract");
        String businessUnit = getString(form, "businessUnit");
        Integer rank = getInteger(form, "rank");
        Long id = getLong(form, "presentationId");
        Long trackId = getLong(form, "track");
        Long slotId = getLong(form, "slot");

        Boolean success = updatePresentation(id, name, presenter, businessUnit, description, rank, trackId, slotId);
        if(!success)
        {
            flash("form_result", "Could not update presentation, missing data");
        }

        return redirect(routes.Application.index());
    }

    /**
     * Reads a HTML form dynamically. Retrieves the id of a presentation and deletes it from the database.
     * @return redirect to index
     */
    public static Result deletePresentation() {

        DynamicForm form = form().bindFromRequest();

        Long id = getLong(form, "presentationId");

        if(isNull(id))
        {
            flash("form_result", "Could not delete presentation, could not parse ID");
        } else
        {
            Presentation presentation = Presentation.find.byId(id);
            Ebean.delete(presentation);
        }
        return redirect(routes.Application.index());
    }

    /**
     * Reads a HTML form dynamically. Retrieves fields for Slot and saves it to database
     * @return redirect to index
     */
    public static Result saveSlot()
    {
        DynamicForm form = form().bindFromRequest();

        String fromTime = getString(form, "fromTime");
        String toTime = getString(form, "toTime");
        SlotType slotType = getSlotType(getString(form, "slotType"));

        if(isBlank(fromTime) || isBlank(toTime))
        {
            flash("form_result", "Could not save slot, missing fields");
        } else
        {
            Slot slot = new Slot();
            String[] start = fromTime.split(":");
            String[] end = toTime.split(":");
            slot.startTime = new DateTime(2013, 4, 25, Integer.parseInt(start[0]), Integer.parseInt(start[1])).toDate();
            slot.endTime = new DateTime(2013, 4, 25, Integer.parseInt(end[0]), Integer.parseInt(end[1])).toDate();
            slot.slotType = slotType;

            Ebean.save(slot);
        }

        return redirect(routes.Application.index());
    }

    /**
     * Reads a HTML form dynamically. Retrieves the id of a slot and deletes it from the database.
     * @return redirect to index
     */
    public static Result deleteSlot() {

        DynamicForm form = form().bindFromRequest();

        Long id = getLong(form, "slotId");

        if(isNull(id))
        {
            flash("form_result", "Could not delete slot, could not parse ID");
        } else
        {
            Slot slot = Slot.find.byId(id);
            Ebean.delete(slot);
        }
        return redirect(routes.Application.index());
    }

    public static Result vote(Long presentationId, Integer rating)
    {

        Vote vote = Vote.getVoteForPresentation(Application.getSessionId(), Presentation.find.byId(presentationId));

        if(vote == null)
        {
            // Create a new vote and save it
            Vote newVote = new Vote(Presentation.find.byId(presentationId), rating, Application.getSessionId());
            Ebean.save(newVote);
        } else
        {
            // The user has already voted, update it
            vote.rating = rating;
            Ebean.update(vote);
        }

        return redirect(routes.Application.index());
    }

    private static SlotType getSlotType(String slotType) {
        if("BREAK".equalsIgnoreCase(slotType))
        {
            return SlotType.BREAK;
        } else if ("KEYNOTE".equalsIgnoreCase(slotType))
        {
            return SlotType.KEYNOTE;
        }
        return SlotType.PRESENTATION;
    }


    /**
     * Updates a Presentation with given information.
     *
     * @param id the presentation to update
     * @param name
     * @param presenter
     * @param businessUnit
     * @param description
     * @param rank
     * @param trackId
     * @param slotId
     * @return
     */
    private static Boolean updatePresentation(Long id, String name, String presenter, String businessUnit, String description, Integer rank, Long trackId, Long slotId) {

        // Check that we have valid input
        if(isNull(id, rank, trackId, slotId) || isBlank(name) || isBlank(presenter) || isBlank(businessUnit)|| isBlank(description))
        {
            return false;
        }

        // Get the presentation object
        Presentation presentation = Presentation.find.byId(id);

        // Get slot and track
        Track track = Track.find.byId(trackId);
        Slot slot = Slot.find.byId(slotId);

        if(isNull(presentation, track, slot))
        {
            return false;
        }

        // Update fields
        presentation.name = name;
        presentation.presenter = presenter;
        presentation.businessUnit = businessUnit;
        presentation.description = description;
        presentation.rank = rank;
        presentation.track = track;
        presentation.slot = slot;

        // Update db and return
        Ebean.update(presentation);
        return true;
    }

    /**
     * Returns true if any of the objects passed is null
     * @param objects
     * @return
     */
    private static boolean isNull(Object... objects)
    {
        for(Object object : objects)
        {
            if (object == null){
                return true;
            }
        }

        return false;
    }

    /**
     * Gets a String from DynamicForm
     * @param form
     * @param key
     * @return
     */
    private static String getString(DynamicForm form, String key)
    {
        String string = form.get(key);
        if(StringUtils.isNotBlank(string))
        {
            return string;
        } else
        {
            return "";
        }
    }

    /**
     * Gets a Long from DynamicForm
     * @param form
     * @param key
     * @return
     */
    private static Long getLong(DynamicForm form, String key)
    {
        Long number = null;
        try {
            number = Long.parseLong(form.get(key));
        } catch (Exception e)
        {
            // Nothing
        }
        return number;
    }

    /**
     * Gets a Integer from DynamicForm
     * @param form
     * @param key
     * @return
     */
    private static Integer getInteger(DynamicForm form, String key)
    {
        Integer number = null;
        try {
            number = Integer.parseInt(form.get(key));
        } catch (Exception e)
        {
            // Nothing
        }
        return number;
    }
}