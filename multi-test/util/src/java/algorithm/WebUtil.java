package algorithm;

import java.io.IOException;

import org.jsoup.Jsoup;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.model.Search;

public class WebUtil {
	public static final String API_KEY = "AIzaSyBZVyVt1dxr0BvRp8AIHYK5H6WV_Dk4pSk";
	public static final String CX = "002984914276044763183:lholwuagtcq";
	public static final String APP_NAME = "HQBot";
	public static final WebUtil INSTANCE = new WebUtil();

	public static WebUtil getInstance() {
		return INSTANCE;
	}

	private HttpTransport httpTransport;
	private JsonFactory jsonFactory;
	private Customsearch customsearch;
	private org.jsoup.nodes.Document doc;

	private WebUtil() {
		httpTransport = new NetHttpTransport();
		jsonFactory = new JacksonFactory();
		customsearch = new Customsearch.Builder(httpTransport, jsonFactory, null).setApplicationName(APP_NAME).build();
	}

	public Search runSearch(String query, long numResults) {
		try {
			Customsearch.Cse.List list = customsearch.cse().list(query);
			list.setKey(API_KEY);
			list.setCx(CX);
			list.setFilter("1");
			list.setNum(numResults);
			Search results = list.execute();
			return results;
		} catch (Exception e) {
			System.err.println(e);
		}
		return null;
	}

	public String getSiteText(String url) {
		try {
			doc = Jsoup.connect(url).get();
			String textContents = doc.body().text();
			return textContents;
		} catch (IOException e) {
		}
		return "";
	}

}