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
import java.io.IOException;

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
            long responseNum = 0;
    		try {
    			System.out.println("DEBUG: SLEEP TO SIMULATE ROBOT MOVEMENT");
			if(!command.equals("")) {
				executeCommand(command);

			}
    			Thread.sleep(5000);
                System.out.println("DEBUG: BEFORE SETTING RESPONSE MESSAGE");

			if(command.equals(command)){
				responseNum = 1;
			}
            	  response.setResponseMessage(responseNum);
            	  System.out.println("DEBUG: AFTER SETTING RESPONSE MESSAGE");
              }
              catch (Exception e){}
          } 
	
	 public String buildCommand(String line) {
		String command = "Do not know how to execute that command yet...";
		
		if(line.equalsIgnoreCase("SetDinnerTable()")) {
			System.out.println("Setting Dinner Table.. non-primitive.. do nothing...");
		}
		else if(line.equalsIgnoreCase("SetPlace()")) {
			System.out.println("Setting Place.. non-primitive.. do nothing...");
		}
		else if(line.equalsIgnoreCase("SetNapkin()")) {
			System.out.println("Setting Napkin.. robot moves forward...");
			command = "rostopic pub /turtle1/command_velocity turtlesim/Velocity -- 2.0 0.0";

		}
		else if(line.equalsIgnoreCase("SetCloth()")) {
			System.out.println("Setting cloth...Robot moves in a circle...");
			command = "rostopic pub /turtle1/command_velocity turtlesim/Velocity -- 5.0 3.8";
		}
		
		else if(line.equalsIgnoreCase("quit")) {
			System.out.println("quitting...");
			return "";	
		}
		return command;
	 }

	public void executeCommand(String command) {
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
