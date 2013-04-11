package controllers;

import domain.forms.Login;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.login;

import static play.data.Form.form;

/**
 * Controller for the admin pages
 *
 * @author kristian.haugene
 * @author daniel.nordstrom.carlsen
 */
public class Admin extends Controller {

    /**
     * Login page.
     */
    public static Result login() {
        return ok(login.render(form(Login.class)));
    }

    /**
     * Handle login form submission.
     */
    public static Result authenticate() {
        Form<Login> loginForm = form(Login.class).bindFromRequest();
        if(loginForm.hasErrors()) {
            return badRequest(login.render(loginForm));
        } else {
            session("username", loginForm.get().username);
            return redirect(routes.Application.index());
        }
    }

    /**
     * Logout and clean the session.
     */
    public static Result logout() {
        session().clear();
        flash("success", "You've been logged out");
        return redirect(routes.Admin.login());
    }

}
