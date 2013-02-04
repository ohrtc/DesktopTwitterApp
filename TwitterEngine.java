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

	public void updateStatus(String status) {
		try {
			engine.updateStatus(status);
		} catch (TwitterException ex) {
			System.out.println("Tweet failed.");
		}
	}

	public static void main(String[] args) {
		TwitterEngine test = new TwitterEngine();
		test.updateStatus("Hello, test.");
	}

}
