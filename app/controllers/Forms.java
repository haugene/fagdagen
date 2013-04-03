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
        DynamicForm requestData = form().bindFromRequest();

        String name = requestData.get("name");
        String presenter = requestData.get("presenter");
        String description = requestData.get("description");

        Long slotId = null;
        Long trackId = null;
        Integer rank = null;
        // Added try/catch to parsing.
        try {
            slotId = Long.parseLong(requestData.get("slot"));
            trackId = Long.parseLong(requestData.get("track"));
            rank = Integer.parseInt(requestData.get("rank"));
        } catch (Exception e)
        {
            // Do nothing, let the values be null
        }

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

    public static Result editPresentation()
    {
        flash("form_result", "Edit button clicked");
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

    private static boolean isNull(Object object)
    {
        return object == null;
    }
}
