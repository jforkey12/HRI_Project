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
  public void onStart(ConnectedNode connectedNode) {
    connectedNode.newServiceServer("rosdisco", rosd_messages.RosD._TYPE,
        new ServiceResponseBuilder<rosd_messages.RosDRequest, rosd_messages.RosDResponse>() {
          @Override
          public void
              build(rosd_messages.RosDRequest request, rosd_messages.RosDResponse response) {
              try {
            	  Thread.sleep(5000); 
              }
              catch (Exception e){}
        	  response.setResponseMessage( 123/*request.getTask()*/);
          }
        });
  }
}
