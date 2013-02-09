/**
 * 
 * @author Cameron Ohrt
 * 
 */
import java.util.List;

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
	
	public List<Status> searchTweets(String query){
		try {
			Query toSearch = new Query(query);
			QueryResult results = engine.search(toSearch);
			return results.getTweets();
		} catch (TwitterException ex) {
			System.out.println("Search failed.");
		}
		return null;
	}
	
	public User followUser(String screenName){
		try{			
		return engine.createFriendship(screenName);
		} catch (TwitterException ex){
			System.out.println("Follow unsuccessful.");
		}
		return null;
	}
	
	public User followUser(long ID){
		try{			
		return engine.createFriendship(ID);
		} catch (TwitterException ex){
			System.out.println("Follow unsuccessful.");
		}
		return null;
	}
	
	

	public static void main(String[] args) {
		TwitterEngine test = new TwitterEngine();
		List TList = test.searchTweets("hockey");
		for(int i = 0; i < TList.size(); i++)
			System.out.println(TList.get(i));
		
	}
}
