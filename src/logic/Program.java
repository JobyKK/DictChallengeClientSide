package logic;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.text.SimpleDateFormat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Dictionary;
import model.Statistics;
import model.User;

public class Program {
	
	final static public String dir = System.getProperty("user.dir") + "/";
	public static UserData user;
	private static RequestUtil hibernateUtil;
	private static Boolean isConnectionToInternet = false;
	public static LanguageController languageController;
	public static TestProgressController testProgressController;
	
	/** Constructor */
	public Program(){
		user = new UserData();
		languageController = new LanguageController();
		testProgressController = new TestProgressController();
		if(checkConnection())
			 hibernateUtil = new RequestUtil();
	}
	
	/**Test connection with Internet
	 * used in connection settings*/
	public static boolean checkConnection(){
			try {
				InetAddress[] addresses = InetAddress.getAllByName("www.google.com");
				  for (InetAddress address : addresses) {
				    if (address.isReachable(100)){
				    	isConnectionToInternet = true;
				    	return true;
				    }
				  }
				/*URL url = new URL("http://www.google.com");
				//System.out.println(url.getHost());
				HttpURLConnection con = (HttpURLConnection) url
						.openConnection();
				con.connect();
				if (con.getResponseCode() == 200){
					System.out.println("Connection established");
					isConnectionToInternet = true;
					return true;
				}*/
			} catch (Exception exception) {
				System.out.println("No Connection");
				isConnectionToInternet = false;
			}
		return false;
	}
	
	public static Boolean isConnectedToInternet(){
		return isConnectionToInternet;
	}
	
	/**Get list with users for testing*//*
	public void getUsers(){
		Session session = hibernateUtil.getSessionFactory().openSession();
	      Transaction tx = null;
	      try{
	         tx = session.beginTransaction();
	         List<User> users = session.createQuery("FROM User").list(); 
	         for (User user : users){
	            System.out.println("First Name: " + user.getEmail()); 
	         }
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	}
	*/
	/**Created for user authentication in login procedure
	 * @return true - if user is in the database*/
	public static boolean checkUser(String email, String password){
		boolean flag = false;
		try {
			if(RequestUtil.postRequest(email, password)){
				user.setSignedIn(true);
				flag = true;
			}
			else {
				user.setSignedIn(false);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	@Deprecated
	public void setUser(User user){
		//user.
	}
	/**Extract user's statistics.
	 * Used in menu controller.
	 * @return ObservableList of statistic's fields.*/
	public static ObservableList<String> getStatistics(){
		ObservableList<String> items = FXCollections.observableArrayList();
		if(user.getStatistics() == null){
				try {
					user.setStatistics(RequestUtil.postStatistics());
					user.setSignedIn(RequestUtil.isAvailableSession);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		if(user.isSignedIn()){
			SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
			Statistics statistics = user.getStatistics();
			items.add("Max score - " + statistics.getMaxScore());
			items.add("Best time - " + dateFormat.format(statistics.getBestTime()));
			items.add("Dictionaries - " + statistics.getDictionariesCount());
			items.add("Friends - " + statistics.getFriendsCount());
		}
		return items;
	}
	
	/**Initialize user's dictionaries.
	 * Used when program should download dictionaries. */
	public static void initDictionaries(){
		if(user.getDictionaries() == null){
			try {
				user.setDictionaries(RequestUtil.postDictionaties());
				user.setSignedIn(RequestUtil.isAvailableSession);
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		}
	}
	/**Initialize user's languages.
	 * Used when user have chosen dictionary, and now program needs download languages*/
	/*public static void initLanguages(){
		Session session = hibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try{
			String query = "select l from Language l "
					+ "where l.id like :id";
			System.out.println(user.getChosenDictionary().getLanguageByLanguage1().getId());
			List<Language> languages1List = 
					session.createQuery(query).setParameter("id", user.
							getChosenDictionary().getLanguageByLanguage1().getId()).list();
			List<Language> languages2List = 
					session.createQuery(query).setParameter("id", user.
							getChosenDictionary().getLanguageByLanguage2().getId()).list();
			for(Language language : languages1List)
				LanguageController.setLanguage1Net(language.getName());
			for(Language language : languages2List)
				LanguageController.setLanguage2Net(language.getName());
		}catch(HibernateException e){
			if(tx != null) tx.rollback();
		}finally{
			session.close();
		}
	}*/
	
	/**Convert ArrayList to ObservableList*/
	public static ObservableList<String> getDictionaries(){
		initDictionaries();
		ObservableList<String> items = FXCollections.observableArrayList();
		for(Dictionary dictionary : user.getDictionaries())
			items.add(dictionary.getName());
		return items;
	}
	
	public static void submitScore(Float score){
		
	}
	
}
