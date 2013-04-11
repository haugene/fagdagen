package controllers;

import com.avaje.ebean.Ebean;
import models.Presentation;
import models.Slot;
import models.Track;
import org.apache.commons.lang3.StringUtils;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Result;
import static org.apache.commons.lang3.StringUtils.isBlank;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static play.data.Form.form;

/**
 * Controller for form post actions
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
        Long slotId = getLong(form, "slot");
        Long trackId = getLong(form, "track");
        Integer rank = getInteger(form, "rank");

        if(isBlank(name) || isBlank(presenter) || isBlank(description) || isNull(slotId) || isNull(trackId) || isNull(rank))
        {
            flash("form_result", "Could not save presentation, there were empty fields");
        } else
        {
            Slot slot = Slot.find.byId(slotId);
            Track track = Track.find.byId(trackId);

            Presentation presentation = new Presentation(name, description, presenter, slot, track, rank);
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
        Integer rank = getInteger(form, "rank");
        Long id = getLong(form, "presentationId");

        Boolean success = updatePresentation(id, name, presenter, description, rank);
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

        DynamicForm requestData = form().bindFromRequest();

        // Might throw exception parsing number
        try {
            Long id = Long.parseLong(requestData.get("presentationId"));
            Presentation presentation = Presentation.find.byId(id);
            Ebean.delete(presentation);
        }catch (Exception e)
        {
            flash("form_result", "Could not delete presentation, could not parse ID");
        }

        return redirect(routes.Application.index());
    }

    /**
     * Updates a Presentation with given information.
     * @param id the presentation to update
     * @param name
     * @param presenter
     * @param description
     * @param rank
     * @return
     */
    private static Boolean updatePresentation(Long id, String name, String presenter, String description, Integer rank) {

        // Check that we have valid input
        if(isNull(id) || isNull(rank) || isBlank(name) || isBlank(presenter) || isBlank(description))
        {
            return false;
        }

        // Get the presentation object
        Presentation presentation = Presentation.find.byId(id);

        if(presentation == null)
        {
            return false;
        }

        // Update fields
        presentation.name = name;
        presentation.presenter = presenter;
        presentation.description = description;
        presentation.rank = rank;

        // Update db and return
        Ebean.update(presentation);
        return true;
    }

    private static boolean isNull(Object object)
    {
        return object == null;
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