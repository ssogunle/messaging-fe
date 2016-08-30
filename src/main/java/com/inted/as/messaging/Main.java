package com.inted.as.messaging;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.sdp.SdpException;
import javax.sdp.SdpFactory;
import javax.sdp.SessionDescription;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.sip.B2buaHelper;
import javax.servlet.sip.Proxy;
import javax.servlet.sip.SipFactory;
import javax.servlet.sip.SipServlet;
import javax.servlet.sip.SipServletMessage;
import javax.servlet.sip.SipServletRequest;
import javax.servlet.sip.SipServletResponse;
import javax.servlet.sip.SipSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Servlet implementation class Main
 */
public class Main extends SipServlet {
	private static final long serialVersionUID = 1L;
	private static Log LOG = LogFactory.getLog(Main.class);

	private CsdrClient client;
	private SipFactory sipFactory;

	/**
	 * @see SipServlet#SipServlet()
	 */
	public Main() {
		super();
		client = new CsdrClient();
	}

	/**
	 * @see Servlet#service(ServletRequest request, ServletResponse response)
	 */
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		System.out.println("The Messaging-FE has been started");
		super.init(servletConfig);
	}

	@Override
	protected void doMessage(SipServletRequest request) throws ServletException, IOException {
		System.out.println("MESSAGE-REQUEST received");

		if (request.isInitial()) {
			Proxy proxy = request.getProxy();
			proxy.proxyTo(request.getRequestURI());
		} else {
			doMessage(request);
		}

		String cseq = request.getHeader("CSeq");
		String callId = request.getCallId();

		if (cseq != null && callId != null && cseq.contains("INVITE")) {

			CallDetailRecord cdr = createCdr(request);
			client.addCallDetailRecord(cdr);

			try {
				SessionData sd = createData(request);
				client.addSessionData(sd);
			} catch (SdpException e) {
				e.printStackTrace();
			}
		}
	}

	public CallDetailRecord createCdr(SipServletMessage msg) {

		CallDetailRecord cdr = new CallDetailRecord();
		SipSession session = msg.getSession();

		cdr.setCallId(msg.getCallId());
		cdr.setContact(msg.getHeader("Contact"));
		cdr.setContainsSdp(msg.getContentLength() > 0);
		cdr.setcSeq(msg.getHeader("CSeq"));

		long duration = session.getLastAccessedTime() - session.getCreationTime();
		cdr.setDuration("" + duration);

		cdr.setFrom(session.getRemoteParty().getValue());
		cdr.setTo(session.getLocalParty().getValue());
		cdr.setSipMethod(msg.getMethod());

		if (msg.getHeader("User-Agent") != null)
			cdr.setUserAgent(msg.getHeader("User-Agent"));

		cdr.setSessionExpires(msg.getExpires());

		return cdr;
	}

	public SessionData createData(SipServletMessage msg) throws IOException, SdpException {
		SessionData sd = new SessionData();
		SipSession session = msg.getSession();
		sd.setLastKnownSessionId(session.getId());
		sd.setLastKnownState(session.getState().name());

		if (msg.getContentLength() > 0) {

			String content = new String(msg.getRawContent());
			sd = addSdp(sd, content);
		}

		sd.setSubscriberUri(msg.getFrom().getURI().toString());

		if (msg.getHeader("User-Agent") != null)
			sd.setUserAgent(msg.getHeader("User-Agent"));

		return sd;
	}

	public SessionData addSdp(SessionData sd, String content) throws SdpException {

		SdpFactory sdpFactory = SdpFactory.getInstance();
		SessionDescription sdc = sdpFactory.createSessionDescription(content);
		Vector mds = sdc.getMediaDescriptions(true);

		Iterator mdIt = mds.iterator();
		while (mdIt.hasNext()) {

			String next = mdIt.next().toString();
			String[] comp = next.split(" ");

			if (comp.length > 2) {
				String type = comp[0].substring(2, comp[0].length());
				String port = comp[1];
				String protocol = comp[2];

				sd.setMediaPort(port);
				sd.setMediaProtocol(protocol);
				sd.setMediaType(type);
			}

		}
		return sd;
	}
}
