package controllers;

import models.Track;
import org.apache.commons.lang3.StringUtils;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {
  
    public static Result index() {
        if(StringUtils.isNotBlank(session("username")))
        {
            System.out.println("Logged in as: " + session("username"));
        } else
        {
            System.out.println("User is not logged in");
        }

        return ok(index.render(Track.findAll()));
    }
  
}
