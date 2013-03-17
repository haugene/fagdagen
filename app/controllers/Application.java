package controllers;

import models.Track;
import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {
  
    public static Result index() {
        return ok(index.render(Track.findAll()));
    }
  
}
