package it.uniroma3.siw.siwdata.web.form;

public class Message {

	

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	private String type; private String message;
	public Message() { }
	public Message(String type, String message) { this.type = type;
	this.message = message; }

}
