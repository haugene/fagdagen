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

    public static Result addData()
    {
        if(isUserLoggedIn())
        {
            InitialDataUtil.addInitialData();
            return index();
        }

        // The user has to log in
        flash("success", "You need to log in before adding data. Log in and try again");
        return Admin.login();
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
