import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Listener {
	public static void main (String[] args) {
        BufferedReader br = null;

        try {
			String line;
            String newLine;

			br = new BufferedReader(new FileReader("cmd.txt"));
 
			while ((line = br.readLine()) != null) {
				System.out.println(line);
                newLine = buildCommand(line);
		    		executeCommand(newLine);
			}
 
		}
        catch (IOException e) {
			e.printStackTrace();
		}
        finally {
			try {
				if (br != null) {
                    br.close();
                }
			}
            catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public static String buildCommand(String line) {
	    String command = "Not sure what " + line + " means...";

		if(line.equals("forward")) {
			System.out.println("moving forward...");
			command = "rostopic pub /turtle1/command_velocity turtlesim/Velocity -- 2.0 0.0";
		}
		else if(line.equals("left")) {
			System.out.println("moving left...");
			command = "rostopic pub /turtle1/command_velocity turtlesim/Velocity -- 0.0 1.0";
		}
		else if(line.equals("right")) {
			System.out.println("moving right...");
			command = "rostopic pub /turtle1/command_velocity turtlesim/Velocity -- 0.0 -1.0";
		}
		else if(line.equals("quit")) {
			System.out.println("quitting...");
			return "";
		}

	    return command;
	}

	public static void executeCommand(String command) {
	    Runtime r = Runtime.getRuntime();
        try {
        	    Process p = r.exec(command);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
	}
}
