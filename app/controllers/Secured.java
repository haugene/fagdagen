package controllers;

import play.mvc.*;
import play.mvc.Http.*;

import static play.mvc.Controller.flash;

public class Secured extends Security.Authenticator {

    @Override
    public String getUsername(Context ctx) {
        return ctx.session().get("username");
    }

    @Override
    public Result onUnauthorized(Context ctx) {
        flash("form_result", "You need to be authorized to modify the contents");
        return redirect(routes.Application.index());
    }

}