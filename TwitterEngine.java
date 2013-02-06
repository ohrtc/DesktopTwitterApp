/**
 * 
 * @author Cameron Ohrt
 * 
 */
import twitter4j.*;

public class TwitterEngine {
	private Twitter engine;

	public TwitterEngine() {
		engine = TwitterFactory.getSingleton();
	}

	public Status updateStatus(String status) {
		try {
			Status result = engine.updateStatus(status);
			return result;
		} catch (TwitterException ex) {
			System.out.println("Tweet failed.");
		}
		return null;
	}

	public Status retweet(long SID) {
		try {
			Status result = engine.retweetStatus(SID);
			return result;
		} catch (TwitterException ex) {
			System.out.println("Retweet failed.");
		}
		return null;
	}
	
	public ResponseList<User> searchPeople(String query, int numPages){
		try {
			ResponseList<User> results = engine.searchUsers(query, numPages);
			return results;
		} catch (TwitterException ex) {
			System.out.println("Search failed.");
		}
		return null;
	}
	
	public QueryResult searchTweets(String query){
		try {
			Query toSearch = new Query(query);
			QueryResult results = engine.search(toSearch);
			return results;
		} catch (TwitterException ex) {
			System.out.println("Search failed.");
		}
		return null;
	}
	
	public void newmethod(){
	}
	
	

	public static void main(String[] args) {
		TwitterEngine test = new TwitterEngine();
		test.updateStatus("Hello, test.");
	}
}
