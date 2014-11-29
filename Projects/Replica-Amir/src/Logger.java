import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

// LOGGER
public class Logger {
	
	// prints the log and also writes it into the specified file
	public static synchronized void log(String label, String file, String message) {
		try {
			// should we also write it in a file?
			// this is just for printing the log pretty 
			// and print the username and school name 
			if (!file.equals("N/A")) {
				PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("logs/"+file+".log", true)));
				writer.println("[" + label + "] (" + file + ") " + message);
				writer.close();
			}
			System.out.println("[" + label + "] (" + file + ") " + message);
		}catch (Exception e) {
			System.out.println("Problems with log file " + file);
		}
	}
}
