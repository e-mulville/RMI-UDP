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

	public DatagramSocket recvSoc;
	private int[] receivedMessages;
	private boolean close = false;
	public ArrayList<Integer> msgList = new ArrayList<Integer>();
	public 	int totalMessages = 9999;
	public 	int MessagesRecieved = 9999;

	private void run() {
		int			pacSize;
		byte[]			pacData;
		byte[] buf = new byte[5000];
		DatagramPacket 	pac =  new DatagramPacket(buf, buf.length);
		String data = null;
		

		while(!close) {
			try {	
				recvSoc.setSoTimeout(30000);
				recvSoc.receive(pac);
				pacData = pac.getData();
				data = new String(pacData); 

			}
			catch( SocketException e) {
				System.out.println( e + "SocketExcepction");
			}
			catch( Exception e) {
				System.out.println("Messages Lost = " + (totalMessages - MessagesRecieved));
				ArrayList<Integer> MissingMsgList = new ArrayList<Integer>(); 
				boolean found = false;
				for(int i = 0; i <= totalMessages; i++){
					
					found = false;
				

					for( int j = 0; j <= msgList.size() - 1; j++){
						if(msgList.get(j) == i){
							found = true;
						}
					}
					if(found != true){
						MissingMsgList.add(i);
					}	
				}
				System.out.println("Total number of messages recieved: " + MessagesRecieved);
				System.out.println("The messages lost are: ");
				for(int i = 0; i < MissingMsgList.size() ; i++){	
					System.out.println("Lost message number : " + MissingMsgList.get(i));	
				}
				close = true;
			}

			processMessage(data);

			
		}
		
		// TO-DO: Receive the messages and process them by calling processMessage(...).
		//        Use a timeout (e.g. 30 secs) to ensure the program doesn't block forever

	}

	public void processMessage(String data) {
		
		String data2 = data.replaceAll("\\s","");
		int messageNum = 9999;
		String[] fields = null;
		fields = data2.split("\\D");
		totalMessages = Integer.parseInt(fields[0]);
		messageNum = Integer.parseInt(fields[1]);

		if (msgList == null) {
			//msgList = new ArrayList<Integer>();
			msgList.add(messageNum);

                } 
		else {
			msgList.add(messageNum);	
		}
		MessagesRecieved = msgList.size();

		if ((messageNum + 1 == totalMessages) ){
				System.out.println("Messages Lost = " + (totalMessages - MessagesRecieved));
				ArrayList<Integer> MissingMsgList = new ArrayList<Integer>(); 
				boolean found = false;
				for(int i = 0; i <= totalMessages; i++){
					
					found = false;
				

					for( int j = 0; j <= msgList.size() - 1; j++){
						if(msgList.get(j) == i){
							found = true;
						}
					}
					if(found != true){
						MissingMsgList.add(i);
					}	
				}
				System.out.println("Total number of messages recieved: " + MessagesRecieved);
				System.out.println("The messages lost are: ");
				for(int i = 0; i < MissingMsgList.size() ; i++){	
					System.out.println("Lost message number : " + MissingMsgList.get(i));	
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
			recvSoc = new DatagramSocket(rp); 

		}
		catch (SocketException e){
			System.out.println("Error initlaising socket on recieve port");
		}
		catch(Exception e) {
			System.out.println("aie");
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

		System.out.println(recvPort);

		UDPServer udp = new UDPServer(recvPort);
		udp.run();

			

		// TO-DO: Construct Server object and start it by calling run().
	}

}
