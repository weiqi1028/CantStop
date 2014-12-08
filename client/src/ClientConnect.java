/** 
 * @author Dan Han, Weiqi Zhang, Abdulaziz Alshahrani
 * build connection to server,
 * send String to server and
 * receive String from server
 */

import java.io.*;
import java.net.*;
public class ClientConnect {
	public Socket sock;
	public String string;
	private BufferedReader in;
	private PrintWriter out;
	
	// create connection to server
	public boolean Create(String host, int port){
		while(true){
		try{
			sock = new Socket(host, port);
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			out = new PrintWriter(sock.getOutputStream(), true);
			return true;
		}
		catch (UnknownHostException e) {
	         continue;
	      }
		catch (IOException e) {
	         continue;
	      }
	}
	}
	
	// release resources
	public void destroy(){
		try{
			in.close();
			out.close();
			sock.close();
		}
		catch(IOException ioe){
			System.err.println("Unable to close writer, reader, or socket: " + ioe.getMessage());
		}	
	}
	
	// read from socket
	public String ReceiveFromSocket(){
		try{
				string = in.readLine(); 
		}
		catch (IOException ioe){
			System.err.println("Unable to read from or write to client: " + ioe.getMessage());
			return null;         	 
		}
		return string;
	}
	
	// write to socket
	public void SendToSocket (String s){
		out.println(s); 
	}
}
