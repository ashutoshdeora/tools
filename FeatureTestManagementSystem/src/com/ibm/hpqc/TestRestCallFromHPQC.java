package com.ibm.hpqc;

import java.util.HashMap;
import java.util.Map;


public class TestRestCallFromHPQC {

	public TestRestCallFromHPQC() {
		con = RestConnector.getInstance();
	}

	private RestConnector con;
	
	
	
	
	public static void main(String[] args) throws Exception {
		new TestRestCallFromHPQC().authenticateLoginLogoutExample(
				"http://" + Constants.HOST + ":" + Constants.PORT + "/qcbin",
				Constants.DOMAIN, Constants.PROJECT, Constants.USERNAME,
				Constants.PASSWORD);
	}

	public void authenticateLoginLogoutExample(final String serverUrl,
			final String domain, final String project, String username,
			String password) throws Exception {

		RestConnector con = RestConnector.getInstance().init(new HashMap<String, String>(), serverUrl, domain, project);

		TestRestCallFromHPQC example = new TestRestCallFromHPQC();
		String authenticationPoint = example.isAuthenticated();
		// now we login to previously returned URL.
		Response loginResponse = example.login(authenticationPoint, username,password);
		
		Iterable<String> newCookies = loginResponse.getResponseHeaders().get("Set-Cookie");
		
			
				String cookieString = null ;
			for (String cookie : newCookies) {
				cookieString = cookie;
				break;
			
		}
			
			
			Map<String, String> requestHeaders = new HashMap<String, String>();
			requestHeaders.put("Content-Type", "application/xml");
			requestHeaders.put("Accept", "application/xml");
			requestHeaders.put("Set-Cookie",cookieString);
			
			String qcsessionurl = con.buildUrl("rest/site-session");
			Response resp = con.httpPost(qcsessionurl, null, requestHeaders);
			System.out.println(resp);
			Iterable<String> QCSessionCookies = resp.getResponseHeaders().get("Set-Cookie");
			String QCSessioncookieString = null ;
			for (String cookie : QCSessionCookies) {
				if(cookie.contains("QCSession")){
				QCSessioncookieString = cookie;
				break;}
			
		}
			
			requestHeaders = new HashMap<String, String>();
			requestHeaders.put("Content-Type", "application/xml");
			requestHeaders.put("Accept", "application/xml");
			String requesCookie = QCSessioncookieString + ";"+ cookieString;
			//requestHeaders.put("Cookie",cookieString);
			requestHeaders.put("Cookie",requesCookie);
			
			String urlOfResourceWeWantToRead = con.buildUrl("rest/domains/DEFAULT/projects/Unity_RentalProject/defects/13773??fields=");
			Response serverResponse = con.httpGet(urlOfResourceWeWantToRead, null,requestHeaders);
					
			//System.out.println(serverResponse.toString());
			//String xmlstringwithoutHtML =Jsoup.parse(serverResponse.toString()).text();
			//String xmlstring = StringEscapeUtils.unescapeHtml4(xmlstringwithoutHtML);
		//	System.out.println(xmlstringwithoutHtML);
			//Entity entity = EntityMarshallingUtils.marshal(Entity.class,
			//		StringEscapeUtils.unescapeHtml4(serverResponse.toString()));
		//	System.out.println(entity);
			//Gson gson =new GsonBuilder().setPrettyPrinting().create();
			
			//com.ibm.hpqc.json.model.Entity  entity = gson.fromJson(serverResponse.toString(), com.ibm.hpqc.json.model.Entity.class);
		//	System.out.println(entity);
			
			

	}

	/**
	 * @param loginUrl
	 *            to authenticate at
	 * @param username
	 * @param password
	 * @return true on operation success, false otherwise
	 * @throws Exception
	 * 
	 *             Logging in to our system is standard http login (basic
	 *             authentication), where one must store the returned cookies
	 *             for further use.
	 */
	public Response login(String loginUrl, String username, String password)
			throws Exception {

		// create a string that lookes like:
		// "Basic ((username:password)<as bytes>)<64encoded>"
		byte[] credBytes = (username + ":" + password).getBytes();
		String credEncodedString = "Basic " + Base64Encoder.encode(credBytes);
		Map<String, String> map = new HashMap<String, String>();
		map.put("Authorization", credEncodedString);
		returnAuthentication(map);
		Response response = con.httpGet(loginUrl, null, map);
		return response;
	}

	public Map<String, String> returnAuthentication(Map<String, String> userData) {
		return userData;
	}

	/**
	 * @return null if authenticated.<br>
	 *         a url to authenticate against if not authenticated.
	 * @throws Exception
	 */
	public String isAuthenticated() throws Exception {

		String isAuthenticateUrl = con.buildUrl("rest/is-authenticated");
		String ret;

		Response response = con.httpGet(isAuthenticateUrl, null, null);

		Iterable<String> authenticationHeader = response.getResponseHeaders().get("WWW-Authenticate");

		String newUrl = authenticationHeader.iterator().next().split("=")[1];
		newUrl = newUrl.replace("\"", "");
		newUrl += "/authenticate";
		ret = newUrl;

		return ret;
	}
}
