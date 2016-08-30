package com.inted.as.messaging;

import java.net.URI;
import java.util.List;

import org.apache.olingo.client.api.ODataClient;
import org.apache.olingo.client.api.communication.request.cud.CUDRequestFactory;
import org.apache.olingo.client.api.communication.request.cud.ODataEntityCreateRequest;
import org.apache.olingo.client.api.communication.request.retrieve.ODataEntitySetRequest;
import org.apache.olingo.client.api.communication.request.retrieve.RetrieveRequestFactory;
import org.apache.olingo.client.api.communication.response.ODataResponse;
import org.apache.olingo.client.api.communication.response.ODataRetrieveResponse;
import org.apache.olingo.client.api.domain.ClientEntity;
import org.apache.olingo.client.api.domain.ClientEntitySet;
import org.apache.olingo.client.core.ODataClientFactory;
import org.apache.olingo.client.api.domain.ClientObjectFactory;
import org.apache.olingo.commons.api.edm.FullQualifiedName;

public class CsdrClient {

	// ODATA EDM Namespace
	private static final String NAMESPACE = "com.inted.csdr";

	private static final String ES_CDR_NAME = "CallDetailRecords";

	private static final String ET_CDR_NAME = "CallDetailRecord";

	private static final String ES_SD_NAME = "ClientSessionDataSet";

	private static final String ET_SD_NAME = "ClientSessionData";

	// OData Service URL
	private final String serviceRoot = "http://localhost:8080/csdr/svc/";

	// Create OData V4 Client
	private ODataClient client;

	private ClientObjectFactory factory;

	public CsdrClient() {
		client = ODataClientFactory.getClient();
		factory = client.getObjectFactory();
	}
	
	public int addCallDetailRecord(CallDetailRecord cdr) {

		URI entitySetUri = client.newURIBuilder(serviceRoot).appendEntitySetSegment(ES_CDR_NAME).build();
		String fqn = NAMESPACE + "." + ET_CDR_NAME;
		FullQualifiedName cdrFqn = new FullQualifiedName(fqn);
		ClientEntity et = client.getObjectFactory().newEntity(cdrFqn);

		et.getProperties().add(factory.newPrimitiveProperty("SipMethod",
				factory.newPrimitiveValueBuilder().buildString(cdr.getSipMethod())));

		et.getProperties().add(factory.newPrimitiveProperty("Duration",
				factory.newPrimitiveValueBuilder().buildString(cdr.getDuration())));

		et.getProperties().add(
				factory.newPrimitiveProperty("From", factory.newPrimitiveValueBuilder().buildString(cdr.getFrom())));

		et.getProperties()
				.add(factory.newPrimitiveProperty("To", factory.newPrimitiveValueBuilder().buildString(cdr.getTo())));

		et.getProperties().add(factory.newPrimitiveProperty("CallId",
				factory.newPrimitiveValueBuilder().buildString(cdr.getCallId())));

		et.getProperties().add(
				factory.newPrimitiveProperty("CSeq", factory.newPrimitiveValueBuilder().buildString(cdr.getcSeq())));

		et.getProperties().add(factory.newPrimitiveProperty("UserAgent",
				factory.newPrimitiveValueBuilder().buildString(cdr.getUserAgent())));

		et.getProperties().add(factory.newPrimitiveProperty("Contact",
				factory.newPrimitiveValueBuilder().buildString(cdr.getContact())));

		et.getProperties().add(factory.newPrimitiveProperty("ContainsSdp",
				factory.newPrimitiveValueBuilder().buildBoolean(cdr.getContainsSdp())));

		et.getProperties().add(factory.newPrimitiveProperty("SessionExpires",
				factory.newPrimitiveValueBuilder().buildInt32(cdr.getSessionExpires())));

		int resultCode = createEntity(entitySetUri, et);

		return resultCode;
	}

	public int addSessionData(SessionData sd) {

		URI entitySetUri = client.newURIBuilder(serviceRoot).appendEntitySetSegment(ES_SD_NAME).build();
		String fqn = NAMESPACE + "." + ET_SD_NAME;
		FullQualifiedName sdFqn = new FullQualifiedName(fqn);
		ClientEntity et = client.getObjectFactory().newEntity(sdFqn);

		et.getProperties().add(factory.newPrimitiveProperty("SubscriberUri",
				factory.newPrimitiveValueBuilder().buildString(sd.getSubscriberUri())));

		et.getProperties().add(factory.newPrimitiveProperty("LastKnownSessionId",
				factory.newPrimitiveValueBuilder().buildString(sd.getLastKnownSessionId())));

		et.getProperties().add(factory.newPrimitiveProperty("LastKnownState",
				factory.newPrimitiveValueBuilder().buildString(sd.getLastKnownState())));
		
		et.getProperties().add(factory.newPrimitiveProperty("UserAgent",
				factory.newPrimitiveValueBuilder().buildString(sd.getUserAgent())));
		
		et.getProperties().add(factory.newPrimitiveProperty("MediaType",
				factory.newPrimitiveValueBuilder().buildString(sd.getMediaType())));
		
		et.getProperties().add(factory.newPrimitiveProperty("MediaPort",
				factory.newPrimitiveValueBuilder().buildString(sd.getMediaPort())));
		
		et.getProperties().add(factory.newPrimitiveProperty("MediaProtocol",
				factory.newPrimitiveValueBuilder().buildString(sd.getMediaProtocol())));

		int resultCode = createEntity(entitySetUri, et);

		return resultCode;

	}

	public int createEntity(URI targetUri, ClientEntity entity) {

		CUDRequestFactory crf = client.getCUDRequestFactory();

		ODataEntityCreateRequest request = crf.getEntityCreateRequest(targetUri, entity);
		request.setAccept("application/json;odata.metadata=minimal");

		ODataResponse response = request.execute();

		return response.getStatusCode();
	}

	public List<ClientEntity> fetchEntities(URI entitySetUri) {

		RetrieveRequestFactory rrf = client.getRetrieveRequestFactory();

		ODataEntitySetRequest<ClientEntitySet> request = rrf.getEntitySetRequest(entitySetUri);
		request.setAccept("application/json;odata.metadata=minimal");

		ODataRetrieveResponse<ClientEntitySet> response = request.execute();

		ClientEntitySet retrievedEntitySet = response.getBody();

		return retrievedEntitySet.getEntities();
	}
}
