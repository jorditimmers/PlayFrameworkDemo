package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.data.Form;
import play.data.FormFactory;

import javax.inject.Inject;

public class FormController extends Controller {
    private final Form<UserData> userForm;

    @Inject
    public FormController(FormFactory formFactory) {
        this.userForm = formFactory.form(UserData.class);
    }

    public Result submitForm() {
        Form<UserData> filledForm = userForm.bindFromRequest();
        if (filledForm.hasErrors()) {
            return badRequest("Form has errors");
        } else {
            UserData userData = filledForm.get();
            return ok("Received data: " + userData.username + ", " + userData.email);
        }
    }
}
