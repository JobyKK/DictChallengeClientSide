package logic;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import model.Dictionary;
import model.Statistics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RequestUtil {

	static String id;
	public  static boolean isAvailableSession = false;
	public  static String connectionURL = "http://128.199.40.160:8080";//"http://localhost:8080";
	
	/**Created for user authentication in login procedure
	 * @return true - if user is in the database*/
	public static boolean postRequest(String email, String pwd) throws IOException
	{
		String charset = "UTF-8";
		String url = connectionURL + "/WebTest/LoginServlet";
		URL obj = new URL(url);
		String query = String.format("email=%s&pwd=%s", 
				email, 
				pwd);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		//add request header
		con.setRequestMethod("POST");

		con.setDoOutput(true);


		try (OutputStream output = con.getOutputStream()) {
			output.write(query.getBytes());
		}
		

		InputStream response = con.getInputStream();
		System.out.println(query.getBytes(charset));
		
		BufferedReader rd = new BufferedReader(
	            new InputStreamReader(con.getInputStream()));

	    String line;
	    
	    while ((line = rd.readLine()) != null) {
	        System.out.println(line);
	        if(line.equals("not found")){
	        	rd.close();
	        	return false;
	        }
	        else{
	        	id = line;
	        	rd.close();
	        	return true;
	        }
	    }
	    return false;
	}

	public static Statistics postStatistics() throws IOException{
		String charset = "UTF-8";
		String url = connectionURL + "/WebTest/StatisticsServlet";
		URL obj = new URL(url);
		String query = String.format("JSESSIONID=%s", 
				id);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		//add reuqest header
		con.setRequestMethod("POST");

		con.setDoOutput(true);

		//send request
		try (OutputStream output = con.getOutputStream()) {
			output.write(query.getBytes());
		}
		//read
		BufferedReader rd = new BufferedReader(
	            new InputStreamReader(con.getInputStream()));

	    String line;
	    Statistics statistics = null;
	    while ((line = rd.readLine()) != null) {
	    	if(line.equals("session is not available")){
	    		rd.close();
	    		isAvailableSession = false;
	    		return statistics;
	    	}
	    	else{
	    		JSONObject statObject;
				try {
					statObject = new JSONObject(line);
					String dateString = (String)statObject.get("bestTime");
					DateFormat format = new SimpleDateFormat("HH:mm:ss");
					statistics = new Statistics(
							Float.parseFloat(statObject.get("maxScore").toString()),
							format.parse(dateString), 
							Integer.parseInt(statObject.get("dictionariesCount").toString()),
							Integer.parseInt(statObject.get("friendsCount").toString()));
					System.out.println(statObject.get("bestTime"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				isAvailableSession = true;
				rd.close();
				return statistics;
	    	}
	    }
	    return statistics;	
	}
	
	public static ArrayList<Dictionary> postDictionaties() throws IOException{
		String charset = "UTF-8";
		String url = connectionURL + "/WebTest/DictionaryServlet";
		URL obj = new URL(url);
		String query = String.format("JSESSIONID=%s", 
				id);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		//add reuqest header
		con.setRequestMethod("POST");

		con.setDoOutput(true);

		//send request
		try (OutputStream output = con.getOutputStream()) {
			output.write(query.getBytes());
		}
		//read
		BufferedReader rd = new BufferedReader(
	            new InputStreamReader(con.getInputStream()));

		ArrayList<Dictionary> dictionaries= null;
	    String line;
	    while ((line = rd.readLine()) != null) {
	        System.out.println(line);
	        if(line.equals("session is not available")){
	    		rd.close();
	    		isAvailableSession = false;
	    		return dictionaries;
	    	}
	        else {
	        	dictionaries = new ArrayList<Dictionary>();
	        	JSONObject dictObject;
	        	try {
					dictObject = new JSONObject(line);
	        		JSONArray dictArray = (JSONArray) dictObject.get("rows");
	        		for (int i = 0; i < dictArray.length(); i++) {
						JSONObject dict = dictArray.getJSONObject(i);
						//Float.parseFloat((String) statObject.get("maxScore")),
						String dateString = dict.get("bestTime").toString();
						DateFormat format = new SimpleDateFormat("HH:mm:ss");
						Dictionary dictionary = new Dictionary(
								dict.get("name").toString(), 
								Integer.parseInt(dict.get("owner").toString()),
								Float.parseFloat(dict.get("maxScore").toString()),								
								format.parse(dateString), 
								dict.get("wordListPath").toString());
						dictionaries.add(dictionary);
					}
	        		isAvailableSession = true;
	        	} catch (JSONException | NumberFormatException | ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	    }
	    rd.close();
	    return dictionaries;
	}
	
	public static void postGetFile(String userId, String dictionaryId, String dictionaryName) throws IOException{
		System.out.println(connectionURL + "/WebTest/UploadDownloadFileServlet?" + 
				"fileName=dictionaries/" + userId + "/" + dictionaryId + ".csv");
		URL website = new URL(connectionURL + "/WebTest/UploadDownloadFileServlet?" + 
				"fileName=dictionaries/" + userId + "/" + dictionaryId + ".csv");
		ReadableByteChannel rbc = Channels.newChannel(website.openStream());
		FileOutputStream fos = new FileOutputStream("dictionaries/" + 
				dictionaryName + ".csv");
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
	}
	
}
