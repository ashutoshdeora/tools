package com.ibm.hpqc;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibm.hpqc.Entity.Fields.Field;

/**
 * This example shows how to read collections and entities as XML or into
 * objects.
 */
public class ReadExample {

	public static void main(String[] args) throws Exception {
		new ReadExample().readExample("http://" + Constants.HOST + ":"
				+ Constants.PORT + "/qcbin", Constants.DOMAIN,
				Constants.PROJECT, Constants.USERNAME, Constants.PASSWORD);
	}

	public void readExample(final String serverUrl, final String domain,
			final String project, String username, String password)
			throws Exception {

		/*RestConnector con = RestConnector.getInstance().init(
				new HashMap<String, String>(), serverUrl, domain, project);

		
		String qcsessionurl = con.buildUrl("rest/site-session");
		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Content-Type", "application/xml");
		requestHeaders.put("Accept", "application/xml");
		try {
			Response resp = con.httpPost(qcsessionurl, null, requestHeaders);

			Iterable<String> newCookies = resp.getResponseHeaders().get("Set-Cookie");
			if (newCookies != null) {

				for (String cookie : newCookies) {
					int equalIndex = cookie.indexOf('=');
					int semicolonIndex = cookie.indexOf(';');

					String cookieKey = cookie.substring(0, equalIndex);
					String cookieValue = cookie.substring(equalIndex + 1,semicolonIndex);

					//cookies.put(cookieKey, cookieValue);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// create a string that lookes like:
		// "Basic ((username:password)<as bytes>)<64encoded>"
		byte[] credBytes = (username + ":" + password).getBytes();
		String credEncodedString = "Basic " + Base64Encoder.encode(credBytes);

		// read a simple resource, not even an entity..
		String urlOfResourceWeWantToRead = con
				.buildUrl("rest/domains/DEFAULT/projects/Unity_RentalProject/defects/13773");
		requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Accept", "application/xml");
		requestHeaders.put("Authorization", credEncodedString);

		Response serverResponse = con.httpGet(urlOfResourceWeWantToRead, null,
				requestHeaders);

		Assert.assertEquals("failed obtaining response for "
				+ urlOfResourceWeWantToRead, serverResponse.getStatusCode(),
				HttpURLConnection.HTTP_OK);

		String responseStr = serverResponse.toString();
		Assert.assertTrue("server properties not found.",
				responseStr.contains("<ServerProperties>"));

		// After accessing a resource, a qc session is implicitly created.
		// The following code asserts this.
		Assert.assertTrue("cookie string doesn't contain QCSession.", con
				.getCookieString().contains("QCSession"));

		String requirementsUrl = con.buildEntityCollectionUrl("requirement");

		// Use the writing example to generate an entity so that we can read it.
		// Go over this code to learn how to create new entities.
		// String newCreatedResourceUrl = writeExample.createEntity(
		// requirementsUrl, Constants.entityToPostXml);

		// query a collection of entities:
		StringBuilder b = new StringBuilder();
		// The query - where field name has a value that starts with the
		// name of the requirement we posted
		b.append("query={name[" + Constants.entityToPostName + "]}");
		// The fields to display: id, name
		b.append("&fields=id,name");
		// determine the sorting order - descending by id (highest id first)
		b.append("&order-by={id[DESC]}");
		// display 10 results
		b.append("&page-size=10");
		// counting from the 1st result, including
		b.append("&start-index=1");

		serverResponse = con.httpGet(requirementsUrl, b.toString(),
				requestHeaders);
		Assert.assertEquals(
				"failed obtaining response for requirements collection "
						+ requirementsUrl, HttpURLConnection.HTTP_OK,
				serverResponse.getStatusCode());

		String listFromCollectionAsXml = serverResponse.toString();

		System.out.print("ListFromCollectionXML: " + "\n");
		System.out.println(listFromCollectionAsXml);

		Assert.assertTrue(
				"didn't find exactly one match, though we posted exactly one entity with '"
						+ Constants.entityToPostName + "' name",
				listFromCollectionAsXml
						.contains("<Entities TotalResults=\"1\">"));

		// Read the entity we generated in the above step.
		// Just perform a get operation on its url.
		serverResponse = con.httpGet(newCreatedResourceUrl, null,
				requestHeaders);
		Assert.assertEquals(
				"failed obtaining response for requirements collection "
						+ requirementsUrl, HttpURLConnection.HTTP_OK,
				serverResponse.getStatusCode());

		// xml -> class instance
		String postedEntityReturnedXml = serverResponse.toString();

		System.out.print("PostedEntityReturnedXML: " + "\n");
		System.out.println(postedEntityReturnedXml);

		Entity entity = EntityMarshallingUtils.marshal(Entity.class,
				postedEntityReturnedXml);

		// now show that you can do something with that object
		List<Field> fields = entity.getFields().getField();
		boolean encounteredPostedField = false;
		for (Field field : fields) {
			if (field.getName().equals(Constants.entityToPostFieldName)) {
				Assert.assertEquals(Constants.entityToPostFieldName
						+ " differs from " + Constants.entityToPostFieldValue
						+ ", though we set it to 1",
						Constants.entityToPostFieldValue, field.getValue()
								.iterator().next());
				encounteredPostedField = true;
				break;
			}
		}

		Assert.assertTrue("read element didn't contain field '"
				+ Constants.entityToPostFieldName + "' with value '"
				+ Constants.entityToPostFieldValue
				+ "', though it's part of the posted element.",
				encounteredPostedField);

		// cleanup
		//writeExample.deleteEntity(newCreatedResourceUrl);
		//login.logout();
*/	}

	/*public void updateCookies(Response response) {

		Iterable<String> newCookies = response.getResponseHeaders().get(
				"Set-Cookie");
		if (newCookies != null) {

			for (String cookie : newCookies) {
				int equalIndex = cookie.indexOf('=');
				int semicolonIndex = cookie.indexOf(';');

				String cookieKey = cookie.substring(0, equalIndex);
				String cookieValue = cookie.substring(equalIndex + 1,
						semicolonIndex);

				cookies.put(cookieKey, cookieValue);
			}
		}
	}

	public void GetQCSession() {
		String qcsessionurl = restConnector.buildUrl("rest/site-session");
		Map<String, String> requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Content-Type", "application/xml");
		requestHeaders.put("Accept", "application/xml");
		try {
			Response resp = restConnector.httpPost(qcsessionurl, null,
					requestHeaders);
			restConnector.updateCookies(resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
}