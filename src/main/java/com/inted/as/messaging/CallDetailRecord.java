package com.inted.as.messaging;

public class CallDetailRecord {

	private String sipMethod;

	private String duration;

	private String to;

	private String from;

	private String callId;
	
	private String cSeq;

	private String userAgent;

	private String contact;

	private Boolean containsSdp;

	private Integer sessionExpires;

	public String getSipMethod() {
		return sipMethod;
	}

	public void setSipMethod(String sipMethod) {
		this.sipMethod = sipMethod;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getCallId() {
		return callId;
	}

	public void setCallId(String callId) {
		this.callId = callId;
	}

	public String getcSeq() {
		return cSeq;
	}

	public void setcSeq(String cSeq) {
		this.cSeq = cSeq;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public Boolean getContainsSdp() {
		return containsSdp;
	}

	public void setContainsSdp(Boolean containsSdp) {
		this.containsSdp = containsSdp;
	}

	public Integer getSessionExpires() {
		return sessionExpires;
	}

	public void setSessionExpires(Integer sessionExpires) {
		this.sessionExpires = sessionExpires;
	}
	
	
}
