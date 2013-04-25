/**
 * This is a simple {@link ServiceServer} {@link NodeMain}.
 * 
 * @author James Forkey
 */

package rosdisco;

import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.NodeMain;
import org.ros.node.service.ServiceResponseBuilder;
import org.ros.node.service.ServiceServer;

public class Server extends AbstractNodeMain {
	
  @Override
  public GraphName getDefaultNodeName() {
    return GraphName.of("rosdisco_services/server");
  }

  @Override
  public void onStart(final ConnectedNode connectedNode) {
    connectedNode.newServiceServer("rosdisco", rosd_messages.RosD._TYPE,
        new ServiceResponseBuilder<rosd_messages.RosDRequest, rosd_messages.RosDResponse>() {
    	@Override
          public void build(rosd_messages.RosDRequest request, rosd_messages.RosDResponse response) {
    		System.out.println("DEBUG: START OF BUILD MESSAGE");
		String command = buildCommand(request.getTask());

    		try {
    			System.out.println("DEBUG: SLEEP TO SIMULATE ROBOT MOVEMENT");
    			  Thread.sleep(10000);
			if(!command.equals("")) {
				executeCommand(command);
			}
    			System.out.println("DEBUG: BEFORE SETTING RESPONSE MESSAGE");

			if(command.equals(command)){
				long responseNum = 1;
			}

            	  response.setResponseMessage(responseNum);
            	  System.out.println("DEBUG: AFTER SETTING RESPONSE MESSAGE");
              }
              catch (Exception e){}
          } 
	
	 public static String buildCommand(String line) {
		String command = "Do not know how to execute that command yet...";
		
		if(line.equalsIgnoreCase("forward")) {
			System.out.println("moving forward...");
			command = "rostopic pub /turtle1/command_velocity turtlesim/Velocity -- 2.0 0.0";
		}
		else if(line.equalsIgnoreCase("left")) {

		}
		else if(line.equalsIgnoreCase("right")) {

		}
		else if(line.equalsIgnoreCase("SetCloth()")) {
			System.out.println("Setting cloth...");
			command = "rostopic pub /turtle1/command_velocity turtlesim/Velocity -- 2.0 0.0";
		}
		else if(line.equalsIgnoreCase("quit")) {
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
        });
  }
}
