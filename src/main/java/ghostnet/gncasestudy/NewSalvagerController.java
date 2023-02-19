package ghostnet.gncasestudy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.enterprise.context.*;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.ArrayList;

@Named("newSalvagerc")
@RequestScoped
public class NewSalvagerController {

    @Inject
    private SessionData data;

    private Salvager salvager;

    private boolean phoneNumValid;
    private String errorPhone;
    private boolean userValid;
    private String errorUser;
    
    private String errorName;
    private String errorPassword;

    public NewSalvagerController() {
        salvager = new Salvager();
        phoneNumValid = false;
        userValid = false;
        errorName = "";
        errorPhone = "";
        errorUser = "";
        errorPassword = "";
    }

    public String getErrorPhone() {
        return errorPhone;
    }

    public void setErrorPhone(String errorPhone) {
        this.errorPhone = errorPhone;
    }

    public Salvager getSalvager() {
        return salvager;
    }

    public void setSalvager(Salvager salvager) {
        this.salvager = salvager;
    }

    public String getErrorUser() {
        return errorUser;
    }

    public void setErrorUser(String errorUser) {
        this.errorUser = errorUser;
    }

    public String getErrorName() {
        return errorName;
    }

    public void setErrorName(String errorName) {
        this.errorName = errorName;
    }

    public String getErrorPassword() {
        return errorPassword;
    }

    public void setErrorPassword(String errorPassword) {
        this.errorPassword = errorPassword;
    }

    public String finishRegistration() {
        var cond1 = salvager.getName().equals("");
        var cond2 = salvager.getPassword().equals("");
        if (!phoneNumValid || !userValid || cond1 || cond2) {
            errorPhone = phoneNumValid ? "" : "Unzulässige Telefonnummer!";
            errorUser = userValid ? "" : "Nutzername vergeben!";
            errorName = cond1 ? "Pflichtfeld!" : "";
            errorPassword = cond2 ? "Pflichtfeld!" : "";
            return null;
        }
        int id = data.addSalvager(salvager);
        salvager.setId(id);
        data.setLoggedInUser(salvager);
        return "salvagerIndex.xhtml";
    }

    public void validatePhone(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String tmp = (String) value;
        if (tmp.equals("")) {
            phoneNumValid = true;
            return;
        }

        tmp = tmp.replaceAll(" ", "");
        tmp = tmp.replaceAll("-", "");
        Pattern pattern = Pattern.compile("^(?:0{1,2}|\\+{1}){1}[1-9]\\d{7,13}$");
        Matcher matcher = pattern.matcher(tmp);
        boolean matchFound = matcher.find();
        if (matchFound) {
            phoneNumValid = true;
            return;
        }
        phoneNumValid = false;
        //throw new ValidatorException(new FacesMessage("Unzulässige Telefonnummer!"));
    }

    public void validateUser(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String tmp = (String) value;

        boolean cond = false;
        ArrayList<Salvager> sals = data.getSalvagers();
        if (!sals.isEmpty()) {
            for (Salvager s : sals) {
                if (s.getUserName().equals(tmp)) {
                    cond = true;
                    break;
                }
            }
        }
        userValid = !cond;
        //throw new ValidatorException(new FacesMessage("Unzulässige Telefonnummer!"));
    }
}
