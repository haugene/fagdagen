package controllers;

import models.Slot;
import models.Track;
import org.apache.commons.lang3.StringUtils;
import play.mvc.*;

import utils.InitialDataUtil;
import views.html.*;

import java.util.List;

public class Application extends Controller {
  
    public static Result index()
    {
        List<Slot> slots = Slot.getSortedSlots();

        return ok(index.render(Track.findAll(), slots, slots.get(slots.size()-1), isUserLoggedIn()));
    }

    /**
     * Checks the session to see if the user is logged in
     * @return true if user is logged in, false otherwise
     */
    public static Boolean isUserLoggedIn()
    {
        if(StringUtils.isNotBlank(session("username")))
        {
            return true;
        }
        return  false;
    }
}
