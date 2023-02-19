package ghostnet.gncasestudy;

public enum Status{
	REPORTED("gemeldet"), UPCOMING_SALVAGE("Bergung bevorstehend"), SALVAGED("geborgen"), MISSING("verschollen"), FOUND("gefunden"), ERROR("Fehler");
	
	private String display;
	
	private Status(String display) {
		this.display = display;
	}
	
	public String getDisplay() {
		return display;
	}
}