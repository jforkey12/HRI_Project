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

import edu.wpi.disco.Disco;

import rosdisco.Imitate;

public class Client implements NodeMain {
	
static ConnectedNode node1;
public boolean newMsg;
static Thread thread1 = new Thread(){
     public void run(){
   	  Imitate.main(null);   
     }
};

  @Override
  public GraphName getDefaultNodeName() {
    return GraphName.of("rosdisco_services/client");
  }
  
  @Override
  public void onStart(ConnectedNode node) {
	  node1 = node;
      thread1.start();
      
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
			      synchronized (thread1) {
			    	  thread1.notifyAll();
			      }
		    	  node1.getLog().info(
		            String.format("task: %s response : %d", req.getTask(), response.getResponseMessage()));
		      }

		      public void onFailure(RemoteException e) {
		        throw new RosRuntimeException(e);
		      }
		    });
	    System.out.println("AFTER CALL BEFORE thread1.wait");

	    try {
	    	System.out.println("thread1 WAITING");
	    	
	    	synchronized (thread1) {
	    		thread1.wait();
	    	}
	    	System.out.println("thread1 DONE WAITING");
	    } catch (InterruptedException e) { }
	    
	    System.out.println("AFTER CALL AFTER thread1.wait");
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
