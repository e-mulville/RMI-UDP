/*
 * Created on 01-Mar-2016
 */
package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.ArrayList;

import common.MessageInfo;

public class UDPServer {

	private DatagramSocket recvSoc;
	private int[] receivedMessages;
	private boolean close;
	public ArrayList<Integer> msgList;

	private void run() {
		int				pacSize;
		byte[]			pacData;
		byte[] buf = new byte[5000];
		DatagramPacket 	pac =  new DatagramPacket(buf, buf.length);
		

		while(!close) {
			try {		
				recvSoc.setSoTimeout(30000);
				recvSoc.receive(pac);
			}
			catch (IOException e) {
	
			}
			catch( Exception e) {
			}
			pacData = pac.getData();
			String data = pacData.toString(); 
			processMessage(data);
		}
		
		// TO-DO: Receive the messages and process them by calling processMessage(...).
		//        Use a timeout (e.g. 30 secs) to ensure the program doesn't block forever

	}

	public void processMessage(String data) {

		MessageInfo msg = null;
		
		try {
			msg = new MessageInfo(data);
		}
		catch (Exception e) {
			System.out.println("Error making MessageInfo");
		}
		if (msgList.isEmpty()) {
			msgList = new ArrayList<Integer>();
			msgList.add(msg.messageNum);

                } 
		else {
			msgList.add(msg.messageNum);	
		}

		if (msg.messageNum == msg.totalMessages){
			int msgsRecieved = msgList.size();
			ArrayList<Integer> MissingMsgList = new ArrayList<Integer>(); 
			for(int i = 0; i <= msg.totalMessages; i++){
				boolean found = false; 	
				for( int j = 0; j <= msgList.size(); j++){
					if(msgList.get(j) == i){
						found = true;
					}
				}
				if(found =! true){
					MissingMsgList.add(i);
				}	
			}
			System.out.println("Total number of messages recieved: " + msgsRecieved);
			System.out.println("The messages lost are: ");
			for(int i = 0; i < MissingMsgList.size() ; i++){	
				System.out.println(MissingMsgList.get(i) + ", ");	
			}
			close = true;
		}



		// TO-DO: Use the data to construct a new MessageInfo object

		// TO-DO: On receipt of first message, initialise the receive buffer

		// TO-DO: Log receipt of the message
	

		// TO-DO: If this is the last expected message, then identify
		//        any missing messages

	}


	public UDPServer(int rp) {
		// TO-DO: Initialise UDP socket for receiving data
		try {
			DatagramSocket recvSoc = new DatagramSocket(rp); 
		}
		catch (SocketException e){
			System.out.println("Error initlaising socket on recieve port");
		}

		// Done Initialisation
		System.out.println("UDPServer ready");
	}

	public static void main(String args[]) {
		int	recvPort;

		// Get the parameters from command line
		if (args.length < 1) {
			System.err.println("Arguments required: recv port");
			System.exit(-1);
		}
		recvPort = Integer.parseInt(args[0]);

		UDPServer udp = new UDPServer(recvPort);
		udp.run();

		// TO-DO: Construct Server object and start it by calling run().
	}

}
