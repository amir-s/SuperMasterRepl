import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Map.Entry;

// Config file which acts like a hashmap and will save and load from the file

public class Config {
	private HashMap<String, String> cnf = new HashMap<String, String>();
	public Config() {
		Scanner sc;
		try {
			// read the file
			// and put every written option to MAP
			sc = new Scanner(new File("setting.cnf"));
			while (sc.hasNextLine()) {
				String k = sc.nextLine();
				String v = sc.nextLine();
				cnf.put(k, v);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("There was a problem with Config file " + e.getMessage());
		}
	}
	
	// Insert something into the config-file as String
	public Config set(String key, String val) {
		cnf.put(key.toLowerCase(), val);
		return this;
	}
	
	// Insert something into the config-file as Integer
	public Config set(String key, int val) {
		cnf.put(key, ""+val);
		return this;
	}
	
	// Get something based on its key from the config file as Integer
	public int getInt(String key) {
		return Integer.parseInt(cnf.get(key));
	}
	
	// Get something based on its key from the config file as String
	public String get(String key) {
		return cnf.get(key);
	}
	
	// write all the keys and values to the file
	// so we can recover all the data from another app
	// or after the process exists
	public Config write() throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter("setting.cnf", "UTF-8");
		
		// iterate over all key-values and write them in separate lines in file
		for (Entry<String, String> item : cnf.entrySet()) {
			writer.println(item.getKey());
			writer.println(item.getValue());
		}
		writer.close();
		return this;
	}
}
