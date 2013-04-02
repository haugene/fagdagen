package controllers;

import com.avaje.ebean.Ebean;
import domain.forms.AddPresentation;
import models.Presentation;
import models.Slot;
import models.Track;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.modals.newPresentationModal;

import static play.data.Form.form;

/**
 * Controller for operations on presentations
 */
public class Forms extends Controller {

    public static Result savePresentation() {
        DynamicForm requestData = form().bindFromRequest();

        String name = requestData.get("name");
        String presenter = requestData.get("presenter");
        String description = requestData.get("description");

        Long slotId = Long.parseLong(requestData.get("slot"));
        Long trackId = Long.parseLong(requestData.get("track"));

        Slot slot = Slot.find.byId(slotId);
        Track track = Track.find.byId(trackId);

        int rank = Integer.parseInt(requestData.get("rank"));

        Presentation presentation = new Presentation(name, description, presenter, slot, track, rank);
        Ebean.save(presentation);

        return redirect(routes.Application.index());
    }

    public static Result deletePresentation() {
        DynamicForm requestData = form().bindFromRequest();
        Long id = Long.parseLong(requestData.get("presentationId"));

        Presentation presentation = Presentation.find.byId(id);

        Ebean.delete(presentation);

        return redirect(routes.Application.index());
    }
}
