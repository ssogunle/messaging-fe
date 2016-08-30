package com.inted.as.messaging;

public class SessionData {

	private String subscriberUri;

	private String lastKnownSessionId;

	private String lastKnownState;

	private String userAgent;

	private String mediaType;

	private String mediaPort;

	private String mediaProtocol;

	public String getLastKnownSessionId() {
		return lastKnownSessionId;
	}

	public void setLastKnownSessionId(String lastKnownSessionId) {
		this.lastKnownSessionId = lastKnownSessionId;
	}

	public String getLastKnownState() {
		return lastKnownState;
	}

	public void setLastKnownState(String lastKnownState) {
		this.lastKnownState = lastKnownState;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public String getMediaPort() {
		return mediaPort;
	}

	public void setMediaPort(String mediaPort) {
		this.mediaPort = mediaPort;
	}

	public String getMediaProtocol() {
		return mediaProtocol;
	}

	public void setMediaProtocol(String mediaProtocol) {
		this.mediaProtocol = mediaProtocol;
	}

	public String getSubscriberUri() {
		return subscriberUri;
	}

	public void setSubscriberUri(String subscriberUri) {
		this.subscriberUri = subscriberUri;
	}

}
