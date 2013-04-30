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

//hack for connectedNode
static ConnectedNode node1;

//thread to run disco
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
	  //hack to generalize node
	  node1 = node;
	  
	  //start the thread that runs disco
      thread1.start();
      
    }
  	//the method disco calls to send new requests
	public static void buildRequest(String str){
		
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
			    //successful message received, release the lock on the disco thread
				synchronized (thread1) {
					thread1.notifyAll();
			    }
				//print the task and message
		    	node1.getLog().info(
		    		String.format("task: %s response : %d", req.getTask(), response.getResponseMessage()));
		      }

		      public void onFailure(RemoteException e) {
		    	  //We should still release the lock on the disco thread on failure 
		    	  synchronized (thread1) {
				    	  thread1.notifyAll();
				      }
		        throw new RosRuntimeException(e);
		      }
		    });
	    System.out.println("DEBUG: After call method");

	    try {
	    	System.out.println("DEBUG: Starting thread1.wait");
	    	
	    	synchronized (thread1) {
	    		thread1.wait();
	    	}
	    	System.out.println("DEBUG: thread1 done waiting");
	    } catch (InterruptedException e) { }
	    
	    System.out.println("DEBUG: AFTER CALL AFTER thread1.wait");
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
