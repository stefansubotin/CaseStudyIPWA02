package ghostnet.gncasestudy;

import jakarta.enterprise.context.*;
//import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Named("reportMissingc")
@RequestScoped
public class ReportMissingController{
	
	@Inject
	private SessionData data;
	
	private GhostNet net;
	private String errorPhone;
	private boolean phoneNumValid;

	private String errorSize;
	private boolean sizeValid;
	
	private String errorName;
	private String errorReporter;
	
	public ReportMissingController() {
		setNet(new GhostNet());
		net.setId(-1);
		net.setName("");
		net.setReportedBy("");
		phoneNumValid = false;
		sizeValid = false;
		resetErrors();
	}

	public GhostNet getNet() {
		return net;
	}

	public void setNet(GhostNet net) {
		this.net = net;
	}
	
	public String getErrorPhone() {
		return errorPhone;
	}

	public void setErrorPhone(String errorPhone) {
		this.errorPhone = errorPhone;
	}

	public String getErrorSize() {
		return errorSize;
	}

	public void setErrorSize(String errorSize) {
		this.errorSize = errorSize;
	}

	public String getErrorName() {
		return errorName;
	}

	public void setErrorName(String errorName) {
		this.errorName = errorName;
	}

	public String getErrorReporter() {
		return errorReporter;
	}

	public void setErrorReporter(String errorReporter) {
		this.errorReporter = errorReporter;
	}

	public void validatePhone(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String tmp = (String)value;
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
		return;
		//throw new ValidatorException(new FacesMessage("Unzulässige Telefonnummer!"));
	}
	
	public void validateSize(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		int tmp = (int)value;
		if (tmp > 0) {
			sizeValid = true;
			return;
		}
		sizeValid = false;
		return;
		//throw new ValidatorException(new FacesMessage("Größe unzulässig!"));
	}

	public String completeReport() {
		if (!checkValidity()) return null;
		net.setStatus(Status.MISSING);
		data.addNet(net);
		net = new GhostNet();
		net.setId(-1);
		return "index.xhtml";
	}
	
	private void resetErrors() {
		errorPhone = "";
		errorSize = "";
		errorName = "";
		errorReporter = "";
	}
	
	private boolean checkValidity() {
		resetErrors();
                var cond1 = !net.getName().equals("");
                var cond2 = !net.getReportedBy().equals("");
                var cond3 = !net.getReportedBy().equals("anonym");
		if (phoneNumValid && sizeValid && cond1 && cond2 && cond3) return true;
		if (!phoneNumValid) errorPhone = "Unzulässige Telefonnummer!";
		if (!sizeValid) errorSize = "Größe kann nicht null sein!";
		if (net.getName().equals("")) errorName = "Notwendiges Feld";
		if (net.getReportedBy().equals("anonym") || net.getReportedBy().equals("")) errorReporter = "Notwendiges Feld";
		return false;
	}
}