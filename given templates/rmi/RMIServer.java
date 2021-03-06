/*
 * Created on 01-Mar-2016
 */
package rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.registry.Registry;

import common.*;

public class RMIServer extends UnicastRemoteObject implements RMIServerI {

	private int[] receivedMessages; 

	public RMIServer() throws RemoteException {
	}

	public void receiveMessage(MessageInfo msg) throws RemoteException {

		if (receivedMessages == null) {
			receivedMessages = new int[msg.totalMessages];
		}

		receivedMessages[msg.messageNum] = 1;	
		
		if(msg.messageNum + 1 == msg.totalMessages){
			
			int n = 0;
			String lost = "The messages not recieved are: ";			
			for( int i = 0; i < msg.totalMessages; i++){

				if(receivedMessages[i] != 1){
					n++;
					lost = lost + " " + (i + 1) + ", ";
				} 
			}
			if(n == 0){
				lost = lost + "None";
			}
			System.out.println("Total number of messages recieved: " + (msg.totalMessages - n));
			System.out.println(lost);
			System.exit(0);
		} 

		// TO-DO: On receipt of first message, initialise the receive buffer

		// TO-DO: Log receipt of the message

		// TO-DO: If this is the last expected message, then identify
		//        any missing messages

	}


	public static void main(String[] args) {

		RMIServer rmis = null;


		if(System.getSecurityManager()==null){
           		 System.setSecurityManager(new RMISecurityManager());
		}
		
		try {
			rmis = new RMIServer();
		}
		catch (RemoteException e){
			System.out.println("Error making server");
		}
			
		rmis.rebindServer(rmis);

		// TO-DO: Initialise Security Manager

		// TO-DO: Instantiate the server class

		// TO-DO: Bind to RMI registry

	}

	protected static void rebindServer(RMIServer server) {
		
		try {
			LocateRegistry.createRegistry( 1099 );
			Naming.rebind("RMIServer", new RMIServer());
		} catch (Exception e) {
			System.out.println("Error rebinding server.");
			System.exit(-1);
		}
		System.out.println("Server is set up.");
		
	
		// TO-DO:
		// Start / find the registry (hint use LocateRegistry.createRegistry(...)
		// If we *know* the registry is running we could skip this (eg run rmiregistry in the start script)

		// TO-DO:
		// Now rebind the server to the registry (rebind replaces any existing servers bound to the serverURL)
		// Note - Registry.rebind (as returned by createRegistry / getRegistry) does something similar but
		// expects different things from the URL field.
	}
}
