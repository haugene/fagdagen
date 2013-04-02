package controllers;

import models.Presentation;
import models.Slot;
import models.Track;
import org.apache.commons.lang3.StringUtils;
import play.mvc.*;

import utils.InitialDataUtil;
import views.html.*;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Application extends Controller {
  
    public static Result index()
    {
        TreeMap<Slot, List<Presentation>> presentationsBySlot = Slot.getPresentationsBySlot();
        Slot lastSlot = presentationsBySlot.lastKey();

        return ok(index.render(Track.findAll(), presentationsBySlot, lastSlot, isUserLoggedIn()));
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
