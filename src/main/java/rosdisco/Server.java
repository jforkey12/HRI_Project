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
public boolean newMsg;
public String responseMsg;
	
  @Override
  public GraphName getDefaultNodeName() {
    return GraphName.of("rosdisco_services/server");
  }

  @Override
  public void onStart(final ConnectedNode connectedNode) {
    connectedNode.newServiceServer("rosdisco", rosd_messages.RosD._TYPE,
        new ServiceResponseBuilder<rosd_messages.RosDRequest, rosd_messages.RosDResponse>() {
    	@Override
          public void
             build(rosd_messages.RosDRequest request, rosd_messages.RosDResponse response) {
    		System.out.println("START OF BUILD MESSAGE");
    		try {
    			System.out.println("SLEEP TO SIMULATE ROBOT MOVEMENT");
    			  Thread.sleep(10000);
    			  System.out.println("BEFORE SETTING RESPONSE MESSAGE");
            	  response.setResponseMessage( 123/*request.getTask()*/);
            	  System.out.println("AFTER SETTING RESPONSE MESSAGE");
            	  
            	  synchronized (Client.thread1){
            		  notifyAll();
            	  }
            	  System.out.println("AFTER NOTIFYALL ");
              }
              catch (Exception e){}
          } 
        });
  }
}
