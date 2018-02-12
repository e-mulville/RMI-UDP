/*
 * Created on 01-Mar-2016
 */
package rmi;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.RMISecurityManager;
import java.net.MalformedURLException;

import common.MessageInfo;

public class RMIClient {

	public static void main(String[] args) {

		RMIServerI iRMIServer = null;

		if (args.length < 2){
			System.out.println("Needs 2 arguments: ServerHostName/IPAddress, TotalMessageCount");
			System.exit(-1);
		}

		String urlServer = new String("rmi://" + args[0] + "/RMIServer");
		int numMessages = Integer.parseInt(args[1]);

		if(System.getSecurityManager()==null){
			System.setSecurityManager(new RMISecurityManager());
		}

		try {
			 iRMIServer = (RMIServerI) Naming.lookup(urlServer);
			 for(int i = 0; i < numMessages; i++) {
				MessageInfo msg = new MessageInfo(numMessages,i);
				iRMIServer.receiveMessage(msg);
			}
		} catch (MalformedURLException e) {
			System.out.println("Error: Malformed hostname.");
		} catch (RemoteException e) {
			System.out.println("Error: Remote Exception.");
		} catch (NotBoundException e) {
			System.out.println("Error: Not Bound Exception.");
		}
	}
}
