/**
 * A simple {@link ServiceClient} {@link NodeMain}.
 * 
 * @author James Forkey
 */
package rosdisco;

import org.ros.exception.RemoteException;
import org.ros.exception.RosRuntimeException;
import org.ros.exception.ServiceNotFoundException;
import org.ros.internal.node.service.Service.Request;

import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.Node;
import org.ros.node.NodeMain;
import org.ros.node.service.ServiceClient;
import org.ros.node.service.ServiceResponseListener;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;

import rosdisco.Imitate;

public class Client implements NodeMain {
	
static ConnectedNode node1;

  @Override
  public GraphName getDefaultNodeName() {
    return GraphName.of("rosdisco_services/client");
  }
  
  
  @Override
  public void onStart(ConnectedNode node) {
	  node1 = node;
	  
     ServiceClient<rosd_messages.RosDRequest, rosd_messages.RosDResponse> serviceClient;
//     System.out.println(node.getServiceRequestMessageFactory());
     try {
    	
      serviceClient = node1.newServiceClient("rosdisco", rosd_messages.RosD._TYPE);
      System.out.println("Starting Disco...");
      System.out.println(node1.getServiceRequestMessageFactory());

      Thread thread1 = new Thread(){
 	      public void run(){
 	    	  Imitate.main(null);   
 	      }
      };
      thread1.start();

      } catch (ServiceNotFoundException e) {
    	  	throw new RosRuntimeException(e);
    }
    final rosd_messages.RosDRequest request = serviceClient.newMessage();
    
    request.setTask("onStart");
    
    serviceClient.call(request, new ServiceResponseListener<rosd_messages.RosDResponse>() {
    	
      public void onSuccess(rosd_messages.RosDResponse response) {
        node1.getLog().info(
            String.format("task: %s response : %d", request.getTask(), response.getResponseMessage()));
      }

      public void onFailure(RemoteException e) {
        throw new RosRuntimeException(e);
      }
    });
    System.out.println("AFTER CALL");
    
  }

	public static void test(String str){
		final rosd_messages.RosDRequest req;

		req = node1.getServiceRequestMessageFactory().newFromType(rosd_messages.RosD._TYPE);

		req.setTask(str);
		
	     ServiceClient<rosd_messages.RosDRequest, rosd_messages.RosDResponse> sClient;
	     
		try {
			sClient = node1.newServiceClient("rosdisco", rosd_messages.RosD._TYPE);
	      } catch (ServiceNotFoundException e) {
	    	  	throw new RosRuntimeException(e);
	    }
		sClient.call(req, new ServiceResponseListener<rosd_messages.RosDResponse>() {
		      public void onSuccess(rosd_messages.RosDResponse response) {
		        node1.getLog().info(
		            String.format("task: %s response : %d", req.getTask(), response.getResponseMessage()));
		      }

		      public void onFailure(RemoteException e) {
		        throw new RosRuntimeException(e);
		      }
		    });
		    System.out.println("AFTER CALL");
	}

	@Override
	public void onError(Node arg0, Throwable arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onShutdown(Node arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onShutdownComplete(Node arg0) {
		// TODO Auto-generated method stub
		
	}


}
