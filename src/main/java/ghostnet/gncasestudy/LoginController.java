package ghostnet.gncasestudy;

import java.util.ArrayList;

import jakarta.enterprise.context.*;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.AbortProcessingException;
import jakarta.faces.event.ComponentSystemEvent;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("loginc")
@RequestScoped
public class LoginController {
	
	@Inject
	private SessionData data;
	
	private Salvager user;
	private String name;
	private boolean loggedIn;
	private String errorUser;
	private String errorPassword;
	
	public LoginController() {
		loggedIn = false;
		user = new Salvager();
		errorUser = "";
		errorPassword = "";
	}

	public Salvager getUser() {
		return user;
	}

	public void setUser(Salvager user) {
		this.user = user;
	}
	
	public String getErrorUser() {
		return errorUser;
	}

	public void setErrorUser(String errorUser) {
		this.errorUser = errorUser;
	}

	public String getErrorPassword() {
		return errorPassword;
	}

	public void setErrorPassword(String errorPassword) {
		this.errorPassword = errorPassword;
	}

	public String login() {
		
		return loggedIn ? "salvagerIndex.xhtml" : null;
	}
	
	public void postValidateUserName(ComponentSystemEvent event) throws AbortProcessingException{
		UIInput tmp = (UIInput)event.getComponent();
		this.name = (String)tmp.getValue();
	}
	
	public void validateLogin(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		resetErrors();
		Salvager tmp = new Salvager();
		tmp.setUserName(name);
		tmp.setPassword((String)value);
		ArrayList<Salvager> list = data.getSalvagers();
		if (list.size() < 1) {
			errorUser = "Unbekannter Nutzername";
			return;
		}
                var wrongPass = false;
		for (Salvager s : list) {
			if (s.isCorrectLoginData(tmp)) {
				data.setLoggedInUser(s);
				loggedIn = true;
				return;
			}
			else if (s.getUserName().equals(tmp.getUserName())) {
				wrongPass = true;
                                break;
                        }
		}
                if (wrongPass) {
                    errorPassword = "Falsches Passwort";
                    return;
                }
                else {
                    errorUser = "Unbekannter Nutzername";
                    return;
                }
	}
	
	private void resetErrors() {
		errorUser = "";
		errorPassword = "";
	}
}