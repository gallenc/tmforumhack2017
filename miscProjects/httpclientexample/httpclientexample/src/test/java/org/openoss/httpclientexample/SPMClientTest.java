package org.openoss.httpclientexample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;



public class SPMClientTest {




	private static final String USER_AGENT = "Mozilla/5.0";

	private static final String GET_URL = "http://localhost:8181/serviceProblemManagement/rest/v1-0";

	private static final String POST_URL = "http://localhost:8181/serviceProblemManagement/rest/v1-0";

	//	public static void main(String[] args) throws IOException {
	//		sendGET();
	//		System.out.println("GET DONE");
	//		sendPOST();
	//		System.out.println("POST DONE");
	//	}

	//	@Test
	//	public void sendGET() throws IOException {
	//		CloseableHttpClient httpClient = HttpClients.createDefault();
	//		HttpGet httpGet = new HttpGet(GET_URL+"/spm/api/serviceProblem/");
	//		httpGet.addHeader("User-Agent", USER_AGENT);
	//		CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
	//
	//		System.out.println("GET Response Status:: "
	//				+ httpResponse.getStatusLine().getStatusCode());
	//
	//		BufferedReader reader = new BufferedReader(new InputStreamReader(
	//				httpResponse.getEntity().getContent()));
	//
	//		String inputLine;
	//		StringBuffer response = new StringBuffer();
	//
	//		while ((inputLine = reader.readLine()) != null) {
	//			response.append(inputLine);
	//		}
	//		reader.close();
	//
	//		// print result
	//		System.out.println(response.toString());
	//		httpClient.close();
	//	}

	@Test
	public void sendPOST() throws IOException {

		String postURL_1_data_data = "{ \"description\" : \"trouble in the internet connection\", "
				+"\n  \"category\": \"serviceProvider.declared\","
				+"\n  \"originatorParty\" : {"
				+"\n     \"role\" : \"Service Provider\", "
				+"\n     \"id\" : \"SP1\","
				+"\n     \"href\": \"http://api/party/SP1\" "
				+"\n    }"
				+"\n  }";
		
		CloseableHttpClient httpClient=null;
		try{

			String POST_URL = "http://localhost:8181/serviceProblemManagement/rest/v1-0";

			httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(POST_URL+"/spm/api/serviceProblem/");
			httpPost.addHeader("User-Agent", USER_AGENT);
			httpPost.addHeader("Accept", "application/json");
			httpPost.addHeader("Content-type", "application/json");

			String jsonpost = postURL_1_data_data;
			HttpEntity entity = new ByteArrayEntity(jsonpost.getBytes("UTF-8"));
			httpPost.setEntity(entity);


			CloseableHttpResponse httpResponse = httpClient.execute(httpPost);

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					httpResponse.getEntity().getContent()));

			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = reader.readLine()) != null) {
				response.append(inputLine);
			}
			reader.close();
			httpClient.close();

		} catch (Exception e) {
			if (httpClient!=null) httpClient.close();
		}


	}
}
