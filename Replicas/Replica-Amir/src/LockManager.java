import java.util.HashMap;

// this class is just a hash map that returns 
// an object based on a key
// this will be used to lock the object
public class LockManager {
	private HashMap<String, String> h = new HashMap<String, String>();
	
	public synchronized String get(String key) {
		if (h.get(key) == null) h.put(key, key);
		return h.get(key);
	}
	
	public synchronized String get(char k) {
		return this.get(""+k);
	}
	
}
