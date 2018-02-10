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
import java.rmi.RMISecurityManager;
import java.rmi.registry.Registry;

import common.*;

public class RMIServer extends UnicastRemoteObject implements RMIServerI {

	private int totalMessages = -1;
	private int[] receivedMessages;

	public RMIServer() throws RemoteException {
	}

	public void receiveMessage(MessageInfo msg) throws RemoteException {

		if (receivedMessages == null) {
			receivedMessages = new int[msg.totalMessages];
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

		// TO-DO: Initialise Security Manager

		// TO-DO: Instantiate the server class

		// TO-DO: Bind to RMI registry

	}

	protected static void rebindServer(String serverURL, RMIServer server) {
		
		try {
			RMIServerI server1 = new RMIServer();
			RMIServerI stub = (RMIServer) UnicastRemoteObject.exportObject(server1, 0);
			Registry registry = LocateRegistry.getRegistry();
			registry.rebind("RMIServer", stub);
		}
		catch ( RemoteException e) {
			try {			
				RMIServerI server1 = new RMIServer();
				RMIServerI stub = (RMIServer) UnicastRemoteObject.exportObject(server1, 0);
				Registry registry = LocateRegistry.createRegistry(1099);
				registry.rebind("RMIServer", stub);
			}
			catch ( RemoteException er) {
				System.out.println("Error initializing registry or binding server.");
				System.exit(-1);
			}	
		}
		

		// TO-DO:
		// Start / find the registry (hint use LocateRegistry.createRegistry(...)
		// If we *know* the registry is running we could skip this (eg run rmiregistry in the start script)

		// TO-DO:
		// Now rebind the server to the registry (rebind replaces any existing servers bound to the serverURL)
		// Note - Registry.rebind (as returned by createRegistry / getRegistry) does something similar but
		// expects different things from the URL field.
	}
}
