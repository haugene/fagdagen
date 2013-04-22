package controllers;

import models.Slot;
import org.apache.commons.lang3.StringUtils;
import play.mvc.*;
import views.html.*;
import java.util.List;

public class Application extends Controller {
  
    public static Result index()
    {
        // Ensure that we have a session ID
        generateSessionIdIfEmpty();

        // Get all slots and pass them to view
        List<Slot> slots = Slot.getSortedSlots();
        return ok(index.render(slots, slots.get(slots.size()-1), isUserLoggedIn()));
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

    public static void generateSessionIdIfEmpty(){
        // Generate a unique id
        String uuid=session("uuid");
        if(uuid==null) {
            uuid=java.util.UUID.randomUUID().toString();
            session("uuid", uuid);
        }
    }

    public static String getSessionId()
    {
        return session("uuid");
    }
}
