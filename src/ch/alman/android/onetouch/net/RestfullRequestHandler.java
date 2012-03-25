package ch.alman.android.onetouch.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.ProtocolException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.Build;
import ch.alman.android.onetouch.log.Logger;
import ch.alman.android.onetouch.utils.Settings;
import ch.almana.spectrum.rest.access.BaseModelAccess;
import ch.almana.spectrum.rest.net.IRequestHandler;

public class RestfullRequestHandler implements IRequestHandler {

	private static final String PATH_SEP = "/";
	private Settings settings;

	public RestfullRequestHandler() {
		super();
		settings = Settings.getInstance();
		// HTTP connection reuse which was buggy pre-froyo
		if (Integer.parseInt(Build.VERSION.SDK) < Build.VERSION_CODES.FROYO) {
			System.setProperty("http.keepAlive", "false");
		}
		Authenticator.setDefault(new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(settings.getUsername(), settings.getPassword().toCharArray());
			}
		});

	}

	public String getPayload(BaseModelAccess modelAccess) throws IOException {
		if (Logger.FAKE_DATA) {
			return "{\"ns1.alarm-response-list\":{\"@throttle\":\"40\",\"@total-alarms\":\"591\",\"ns1.alarm-responses\":{\"ns1.alarm\":[{\"@id\":\"4f2f897e-4629-1001-0362-02004c4f4f50\",\"ns1.attribute\":[{\"@id\":\"0x11f53\",\"$\":\"0x61e9f1\"},{\"@id\":\"0x10000\",\"$\":\"HubCat29xx\"},{\"@id\":\"0x11f56\",\"$\":\"2\"},{\"@id\":\"0x12b4c\",\"$\":\"HIGH MEMORY UTILIZATION\"}]},{\"@id\":\"4f571242-1820-1055-0167-02004c4f4f50\",\"ns1.attribute\":[{\"@id\":\"0x11f53\",\"$\":\"0x60f9b0\"},{\"@id\":\"0x10000\",\"$\":\"SwCiscoIOS\"},{\"@id\":\"0x11f56\",\"$\":\"1\"},{\"@id\":\"0x12b4c\",\"$\":\"A COLD START TRAP HAS BEEN RECEIVED\"}]},{\"@id\":\"4f589a41-b9c3-105d-0167-02004c4f4f50\",\"ns1.attribute\":[{\"@id\":\"0x11f53\",\"$\":\"0x621852\"},{\"@id\":\"0x10000\",\"$\":\"SwCiscoIOS\"},{\"@id\":\"0x11f56\",\"$\":\"0\"},{\"@id\":\"0x12b4c\",\"$\":\"VLAN CREATED\"}]},{\"@id\":\"4f58a681-0e83-105e-0167-02004c4f4f50\",\"ns1.attribute\":[{\"@id\":\"0x11f53\",\"$\":\"0x6118c5\"},{\"@id\":\"0x10000\",\"$\":\"SM_SLA\"},{\"@id\":\"0x11f56\",\"$\":\"3\"},{\"@id\":\"0x12b4c\",\"$\":\"SLA DOWNTIME VIOLATION\"}]},{\"@id\":\"4f58c018-921a-105e-0167-02004c4f4f50\",\"ns1.attribute\":[{\"@id\":\"0x11f53\",\"$\":\"0x6118bd\"},{\"@id\":\"0x10000\",\"$\":\"SM_SLA\"},{\"@id\":\"0x11f56\",\"$\":\"2\"},{\"@id\":\"0x12b4c\",\"$\":\"SLA DOWNTIME WARNING\"}]},{\"@id\":\"4f5f0f9d-2e4b-1001-0193-02004c4f4f50\",\"ns1.attribute\":[{\"@id\":\"0x11f53\",\"$\":\"0x616044\"},{\"@id\":\"0x10000\",\"$\":\"Host_Device\"},{\"@id\":\"0x11f56\",\"$\":\"1\"},{\"@id\":\"0x12b4c\",\"$\":\"DISK THRESHOLD EXCEEDED\"}]},{\"@id\":\"4f61f0ab-f57d-1011-0193-02004c4f4f50\",\"ns1.attribute\":[{\"@id\":\"0x11f53\",\"$\":\"0x621997\"},{\"@id\":\"0x10000\",\"$\":\"RFC2271App\"},{\"@id\":\"0x11f56\",\"$\":\"0\"},{\"@id\":\"0x12b4c\",\"$\":\"Model Created\"}]},{\"@id\":\"4f69e0b8-702e-103d-0193-02004c4f4f50\",\"ns1.attribute\":[{\"@id\":\"0x11f53\",\"$\":\"0x6219e6\"},{\"@id\":\"0x10000\",\"$\":\"EntityModule\"},{\"@id\":\"0x11f56\",\"$\":\"0\"},{\"@id\":\"0x12b4c\",\"$\":\"Model Created\"}]},{\"@id\":\"4f69e7ae-a0b8-103d-0193-02004c4f4f50\",\"ns1.attribute\":[{\"@id\":\"0x11f53\",\"$\":\"0x621a2c\"},{\"@id\":\"0x10000\",\"$\":\"CiscoVTPApp\"},{\"@id\":\"0x11f56\",\"$\":\"0\"},{\"@id\":\"0x12b4c\",\"$\":\"Model Created\"}]},{\"@id\":\"4f69e819-a68f-103d-0193-02004c4f4f50\",\"ns1.attribute\":[{\"@id\":\"0x11f53\",\"$\":\"0x621a5c\"},{\"@id\":\"0x10000\",\"$\":\"Gen_IF_Port\"},{\"@id\":\"0x11f56\",\"$\":\"0\"},{\"@id\":\"0x12b4c\",\"$\":\"Model Created\"}]},{\"@id\":\"4ece42fb-5ccc-1060-030b-02004c4f4f50\",\"ns1.attribute\":[{\"@id\":\"0x11f53\",\"$\":\"0x616cc7\"},{\"@id\":\"0x10000\",\"$\":\"Rtr_Cisco\"},{\"@id\":\"0x11f56\",\"$\":\"1\"},{\"@id\":\"0x12b4c\",\"$\":\"IKE TUNNEL STOP\"}]},{\"@id\":\"4f4e4c7d-9776-1025-0167-02004c4f4f50\",\"ns1.attribute\":[{\"@id\":\"0x11f53\",\"$\":\"0x61ac47\"},{\"@id\":\"0x10000\",\"$\":\"HubCat29xx\"},{\"@id\":\"0x11f56\",\"$\":\"1\"},{\"@id\":\"0x12b4c\",\"$\":\"A COLD START TRAP HAS BEEN RECEIVED\"}]},{\"@id\":\"4f5f56c9-fa36-1002-0193-02004c4f4f50\",\"ns1.attribute\":[{\"@id\":\"0x11f53\",\"$\":\"0x6172e1\"},{\"@id\":\"0x10000\",\"$\":\"Pingable\"},{\"@id\":\"0x11f56\",\"$\":\"1\"},{\"@id\":\"0x12b4c\",\"$\":\"FIBRE CHANNEL TRUNK IF DOWN NOTIFY\"}]},{\"@id\":\"4f4f47e6-6c56-102b-0167-02004c4f4f50\",\"ns1.attribute\":[{\"@id\":\"0x11f53\",\"$\":\"0x600b84\"},{\"@id\":\"0x10000\",\"$\":\"SRMApplication\"},{\"@id\":\"0x11f56\",\"$\":\"2\"},{\"@id\":\"0x12b4c\",\"$\":\"DISK SPACE USAGE BETWEEN 85% AND 95%\"}]},{\"@id\":\"4f57075d-dacd-1054-0167-02004c4f4f50\",\"ns1.attribute\":[{\"@id\":\"0x11f53\",\"$\":\"0x60b9ed\"},{\"@id\":\"0x10000\",\"$\":\"Gen_IF_Port\"},{\"@id\":\"0x11f56\",\"$\":\"3\"},{\"@id\":\"0x12b4c\",\"$\":\"BAD LINK DETECTED\"}]},{\"@id\":\"4f594a06-d9b7-1002-01c6-02004c4f4f50\",\"ns1.attribute\":[{\"@id\":\"0x11f53\",\"$\":\"0x610bb7\"},{\"@id\":\"0x10000\",\"$\":\"GnSNMPDev\"},{\"@id\":\"0x11f56\",\"$\":\"0\"},{\"@id\":\"0x12b4c\",\"$\":\"Infoblox STATE CHANGE EVENT\"}]},{\"@id\":\"4f5f0ce0-47f5-1001-0122-008010002309\",\"ns1.attribute\":[{\"@id\":\"0x11f53\",\"$\":\"0x600b91\"},{\"@id\":\"0x10000\",\"$\":\"Pingable\"},{\"@id\":\"0x11f56\",\"$\":\"1\"},{\"@id\":\"0x12b4c\",\"$\":\"DUPLICATE MAC WITH DIFFERENT IP DETECTED\"}]},{\"@id\":\"4f68b772-56d6-1036-0193-02004c4f4f50\",\"ns1.attribute\":[{\"@id\":\"0x11f53\",\"$\":\"0x6219bc\"},{\"@id\":\"0x10000\",\"$\":\"Pingable\"},{\"@id\":\"0x11f56\",\"$\":\"0\"},{\"@id\":\"0x12b4c\",\"$\":\"Model Created\"}]},{\"@id\":\"4f68d98a-41ee-1037-0193-02004c4f4f50\",\"ns1.attribute\":[{\"@id\":\"0x11f53\",\"$\":\"0x6219ba\"},{\"@id\":\"0x10000\",\"$\":\"Pingable\"},{\"@id\":\"0x11f56\",\"$\":\"2\"},{\"@id\":\"0x12b4c\",\"$\":\"Heavily degraded response time (jmeter)\"}]},{\"@id\":\"4f6a1e86-1a22-103f-0193-02004c4f4f50\",\"ns1.attribute\":[{\"@id\":\"0x11f53\",\"$\":\"0x614ca0\"},{\"@id\":\"0x10000\",\"$\":\"Host_Device\"},{\"@id\":\"0x11f56\",\"$\":\"1\"},{\"@id\":\"0x12b4c\",\"$\":\"A COLD START TRAP HAS BEEN RECEIVED\"}]},{\"@id\":\"4f5f0f69-7ac4-1000-0193-02004c4f4f50\",\"ns1.attribute\":[{\"@id\":\"0x11f53\",\"$\":\"0x621965\"},{\"@id\":\"0x10000\",\"$\":\"RTM_TestHost\"},{\"@id\":\"0x11f56\",\"$\":\"0\"},{\"@id\":\"0x12b4c\",\"$\":\"Model Created\"}]},{\"@id\":\"4f5f0fa7-423a-1001-0193-02004c4f4f50\",\"ns1.attribute\":[{\"@id\":\"0x11f53\",\"$\":\"0x621969\"},{\"@id\":\"0x10000\",\"$\":\"RTM_TestHost\"},{\"@id\":\"0x11f56\",\"$\":\"0\"},{\"@id\":\"0x12b4c\",\"$\":\"Model Created\"}]},{\"@id\":\"4f606778-3c65-1009-0193-02004c4f4f50\",\"ns1.attribute\":[{\"@id\":\"0x11f53\",\"$\":\"0x621990\"},{\"@id\":\"0x10000\",\"$\":\"vLan\"},{\"@id\":\"0x11f56\",\"$\":\"0\"},{\"@id\":\"0x12b4c\",\"$\":\"Model Created\"}]},{\"@id\":\"4f615229-9904-100e-0193-02004c4f4f50\",\"ns1.attribute\":[{\"@id\":\"0x11f53\",\"$\":\"0x6214d9\"},{\"@id\":\"0x10000\",\"$\":\"Host_systemEDGE\"},{\"@id\":\"0x11f56\",\"$\":\"1\"},{\"@id\":\"0x12b4c\",\"$\":\"A COLD START TRAP HAS BEEN RECEIVED\"}]},{\"@id\":\"4f62ed11-146c-1017-0193-02004c4f4f50\",\"ns1.attribute\":[{\"@id\":\"0x11f53\",\"$\":\"0x621577\"},{\"@id\":\"0x10000\",\"$\":\"SwCiscoIOS\"},{\"@id\":\"0x11f56\",\"$\":\"2\"},{\"@id\":\"0x12b4c\",\"$\":\"HIGH MEMORY UTILIZATION\"}]},{\"@id\":\"4f67c554-e9f9-1030-0193-02004c4f4f50\",\"ns1.attribute\":[{\"@id\":\"0x11f53\",\"$\":\"0x61e741\"},{\"@id\":\"0x10000\",\"$\":\"Gen_IF_Port\"},{\"@id\":\"0x11f56\",\"$\":\"3\"},{\"@id\":\"0x12b4c\",\"$\":\"BAD LINK DETECTED\"}]},{\"@id\":\"4f69e654-9779-103d-0193-02004c4f4f50\",\"ns1.attribute\":[{\"@id\":\"0x11f53\",\"$\":\"0x6219ed\"},{\"@id\":\"0x10000\",\"$\":\"802dot1xApp\"},{\"@id\":\"0x11f56\",\"$\":\"0\"},{\"@id\":\"0x12b4c\",\"$\":\"Model Created\"}]},{\"@id\":\"4f6a2421-3cfc-103f-0193-02004c4f4f50\",\"ns1.attribute\":[{\"@id\":\"0x11f53\",\"$\":\"0x620175\"},{\"@id\":\"0x10000\",\"$\":\"SM_Service\"},{\"@id\":\"0x11f56\",\"$\":\"2\"},{\"@id\":\"0x12b4c\",\"$\":\"SERVICE IS DEGRADED\"}]},{\"@id\":\"4ea6ba71-6e88-10d4-0358-02004c4f4f50\",\"ns1.attribute\":[{\"@id\":\"0x11f53\",\"$\":\"0x611ffd\"},{\"@id\":\"0x10000\",\"$\":\"Host_Device\"},{\"@id\":\"0x11f56\",\"$\":\"3\"},{\"@id\":\"0x12b4c\",\"$\":\"PROCESS COUNT HAS FALLEN BELOW MINIMUM NUMBER REQUESTED\"}]},{\"@id\":\"4f631174-d7c6-1017-0193-02004c4f4f50\",\"ns1.attribute\":[{\"@id\":\"0x11f53\",\"$\":\"0x6023c9\"},{\"@id\":\"0x10000\",\"$\":\"SwCiscoIOS\"},{\"@id\":\"0x11f56\",\"$\":\"0\"},{\"@id\":\"0x12b4c\",\"$\":\"VLAN CREATED\"}]},{\"@id\":\"4f632eaf-7ddd-1018-0193-02004c4f4f50\",\"ns1.attribute\":[{\"@id\":\"0x11f53\",\"$\":\"0x620166\"},{\"@id\":\"0x10000\",\"$\":\"Pingable\"},{\"@id\":\"0x11f56\",\"$\":\"2\"},{\"@id\":\"0x12b4c\",\"$\":\"Heavily degraded response time (jmeter)\"}]},{\"@id\":\"4f664437-50e5-1028-0193-02004c4f4f50\",\"ns1.attribute\":[{\"@id\":\"0x11f53\",\"$\":\"0x61c9c5\"},{\"@id\":\"0x10000\",\"$\":\"SwCiscoIOS\"},{\"@id\":\"0x11f56\",\"$\":\"2\"},{\"@id\":\"0x12b4c\",\"$\":\"HIGH MEMORY UTILIZATION\"}]},{\"@id\":\"4f69e453-8901-103d-0193-02004c4f4f50\",\"ns1.attribute\":[{\"@id\":\"0x11f53\",\"$\":\"0x617a22\"},{\"@id\":\"0x10000\",\"$\":\"Host_Device\"},{\"@id\":\"0x11f56\",\"$\":\"2\"},{\"@id\":\"0x12b4c\",\"$\":\"MANAGEMENT AGENT LOST\"}]},{\"@id\":\"4f69e654-97a6-103d-0193-02004c4f4f50\",\"ns1.attribute\":[{\"@id\":\"0x11f53\",\"$\":\"0x6219fa\"},{\"@id\":\"0x10000\",\"$\":\"CiscoFlashApp\"},{\"@id\":\"0x11f56\",\"$\":\"0\"},{\"@id\":\"0x12b4c\",\"$\":\"Model Created\"}]},{\"@id\":\"4f69e819-a66b-103d-0193-02004c4f4f50\",\"ns1.attribute\":[{\"@id\":\"0x11f53\",\"$\":\"0x621a46\"},{\"@id\":\"0x10000\",\"$\":\"CiscoRTTMonApp\"},{\"@id\":\"0x11f56\",\"$\":\"0\"},{\"@id\":\"0x12b4c\",\"$\":\"Model Created\"}]},{\"@id\":\"4f46539e-6c44-1001-0082-02004c4f4f50\",\"ns1.attribute\":[{\"@id\":\"0x11f53\",\"$\":\"0x61716c\"},{\"@id\":\"0x10000\",\"$\":\"Pingable\"},{\"@id\":\"0x11f56\",\"$\":\"1\"},{\"@id\":\"0x12b4c\",\"$\":\"CISCO FABRIC SERVICE MERGE FAILED\"}]},{\"@id\":\"4f587c1c-f403-105c-0167-02004c4f4f50\",\"ns1.attribute\":[{\"@id\":\"0x11f53\",\"$\":\"0x621661\"},{\"@id\":\"0x10000\",\"$\":\"SwCiscoIOS\"},{\"@id\":\"0x11f56\",\"$\":\"0\"},{\"@id\":\"0x12b4c\",\"$\":\"VLAN CREATED\"}]},{\"@id\":\"4f599290-216e-1004-01c6-02004c4f4f50\",\"ns1.attribute\":[{\"@id\":\"0x11f53\",\"$\":\"0x61df52\"},{\"@id\":\"0x10000\",\"$\":\"SwCiscoIOS\"},{\"@id\":\"0x11f56\",\"$\":\"0\"},{\"@id\":\"0x12b4c\",\"$\":\"VLAN CREATED\"}]},{\"@id\":\"4f5f0fa7-424c-1001-0193-02004c4f4f50\",\"ns1.attribute\":[{\"@id\":\"0x11f53\",\"$\":\"0x62196c\"},{\"@id\":\"0x10000\",\"$\":\"RTM_TestHost\"},{\"@id\":\"0x11f56\",\"$\":\"0\"},{\"@id\":\"0x12b4c\",\"$\":\"Model Created\"}]},{\"@id\":\"4f605f24-0cff-1009-0193-02004c4f4f50\",\"ns1.attribute\":[{\"@id\":\"0x11f53\",\"$\":\"0x61e646\"},{\"@id\":\"0x10000\",\"$\":\"CiscoNXOS\"},{\"@id\":\"0x11f56\",\"$\":\"1\"},{\"@id\":\"0x12b4c\",\"$\":\"THE COMMUNICATION LINK IS DOWN\"}]}]},\"ns1.link\":{\"@rel\":\"next\",\"@href\":\"https:\\/\\/spectrum.urz.unibas.ch\\/spectrum\\/restful\\/alarms?id=e4b75c2f-fdca-47f9-b6f8-fea892b3b8be&start=40&throttlesize=40\",\"@type\":\"application\\/xml\"}}}";
		}
		StringBuilder path = new StringBuilder();
		path.append(PATH_SEP).append(settings.getSpectroServerUrlPath()).append(PATH_SEP);
		path.append("restful/");
		path.append(modelAccess.getRestNoun());
		if (true) {
			HttpURLConnection conn = doGetRequest(modelAccess, path);
			//HttpURLConnection conn = doPostRequest(modelAccess, path);
			Logger.d("Reading response");
			StringBuilder sb = new StringBuilder();
			String line;
			InputStream is = conn.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				Logger.d(line);
			}
			Logger.d("Finished loading");
			return sb.toString();
		} else {
			URL url = new URL(settings.getSpectroServerProtocoll(), settings.getSpectroServerName(), path.toString());
			return httpClientPost(url.toString(), modelAccess);
		}
	}

	protected HttpURLConnection doGetRequest(BaseModelAccess modelAccess, StringBuilder path) throws MalformedURLException, IOException, ProtocolException {
		int throttlesize = settings.getThrottlesize();
		String attrSep = "?";
		if (throttlesize > 0) {
			path.append(attrSep);
			path.append("throttlesize=").append(throttlesize);
			attrSep = "&";
		}
		for (String attr : modelAccess.getAttributesHandles()) {
			path.append(attrSep).append("attr=").append(attr);
			attrSep = "&";
		}

		URL url = new URL(settings.getSpectroServerProtocoll(), settings.getSpectroServerName(), path.toString());
		Logger.i("Loading " + url.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");

		return conn;
	}

	protected HttpURLConnection doPostRequest(BaseModelAccess modelAccess, StringBuilder path) throws MalformedURLException, IOException,
			ProtocolException {

		URL url = new URL(settings.getSpectroServerProtocoll(), settings.getSpectroServerName(), path.toString());
		Logger.i("Loading " + url.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		// conn.setRequestMethod("GET");
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setRequestProperty("Accept", "application/json");

		BufferedWriter postWriter = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
		postWriter.write(getPostConfig(modelAccess));
		//		postWriter.write("");
		return conn;
	}

	private String getPostConfig(BaseModelAccess modelAccess) {
		StringBuilder sb = new StringBuilder();
		sb.append("<rs:alarm-request ");
		int throttlesize = settings.getThrottlesize();
		if (throttlesize > 0) {
			sb.append("throttlesize=\"");
			sb.append(throttlesize);
			sb.append("\"");
		}
		sb.append(" xmlns:rs=\"http://www.ca.com/spectrum/restful/schema/request\"\n" +
				"  xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
				"  xsi:schemaLocation=\"http://www.ca.com/spectrum/restful/schema/request ../../../xsd/Request.xsd \">");
		sb.append("\n");
		for (String attr : modelAccess.getAttributesHandles()) {
			sb.append("<rs:requested-attribute id=\"");
			sb.append(attr);
			sb.append("\" />");
			sb.append("\n");
		}

		sb.append("</rs:alarm-request>");
		sb.append("\n");
		return sb.toString();
	}

	protected String httpClientPost(String url, BaseModelAccess modelAccess) {
		final UsernamePasswordCredentials creds = new UsernamePasswordCredentials(settings.getUsername(), settings.getPassword());

		HttpPost post = new HttpPost(url);
		post.setHeader("Accept", "application/json");
		//		get.setHeader("Content-type", "application/json");

		try {
			String postConfig = getPostConfig(modelAccess);
			HttpEntity entity = new StringEntity(postConfig);
			post.setEntity(entity);
		} catch (UnsupportedEncodingException e) {
			Logger.e("Cannot set post data", e);
		}

		HttpResponse resp = null;
		BufferedReader in = null;
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			// post 8 : httpClient = AndroidHttpClient.newInstance("onetouch", context);

			AuthScope authscope = AuthScope.ANY;
			httpClient.getCredentialsProvider().setCredentials(authscope, creds);

			resp = httpClient.execute(post);

			if (resp != null) {
				in = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
				StringBuffer sb = new StringBuffer("");
				String line = "";
				String NL = System.getProperty("line.separator");
				while ((line = in.readLine()) != null) {
					sb.append(line + NL);
				}
				in.close();
				String page = sb.toString();
				System.out.println(page);
				return sb.toString();
			} else {
				return "no data";
			}
		} catch (ClientProtocolException e) {
			Logger.e("HTTP protocol error", e);
			return e.getMessage();
		} catch (IOException e) {
			Logger.e("Communication error", e);
			return e.getMessage();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
