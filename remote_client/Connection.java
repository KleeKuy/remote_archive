package remote_client;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.*;

public class Connection {

	//TODO rethink this method,
	//querry - is it proper? does this make sense?
	//questionable imho, should be reorganized
	//object of connection class should be initalized, no?
	public static String Connect(final String hostName, String portname)
	{
		final int port = Integer.parseInt(portname);
		
		try 
		{
			Socket clientSocket = new Socket(hostName, port);
			
			PrintWriter out =
	                new PrintWriter(clientSocket.getOutputStream(), true);       
			
			BufferedReader inHost = new BufferedReader(
	                new InputStreamReader(clientSocket.getInputStream()));
			
			OutputStream outs = clientSocket.getOutputStream();
	      //  BufferedReader in = new BufferedReader(
	      //          new InputStreamReader(clientSocket.getInputStream()));
	        out.println(Protocol.N_FILES);
	        out.println(Location.getInstance().getFileList().length);
	        //TODO maybe change this long thingy to loacl varaible here?
	        for(int i=0; i<Location.getInstance().getFileList().length; i++)
	        {
	        	int count;
	        	out.println(Protocol.SIZE);
	        	out.println((int) Location.getInstance().getFileList()[i].length());
	        	out.println(Protocol.NAME);
	        	out.println(Location.getInstance().getFileList()[i].getName());
	        	
	        	byte[] bytes = new byte[4096];
	        	InputStream in = new FileInputStream(Location.getInstance().getFileList()[i]);
	            while ((count = in.read(bytes)) > 0)
	            {
	                outs.write(bytes, 0, count);
	            }
	            
	            String response = inHost.readLine();
	            System.out.println(response);
	            //while(true)
	          //  {
	            	//TODO hmm, dis bad    	
	            	//if(response=="file_received")
	            	//	break;
	       //     }
	            System.out.println("closing");
	            in.close();

	        }
	        out.close();
	        outs.close();
	        clientSocket.close();
		}
		catch(UnknownHostException e)
		{
			return "Don't know about host " + hostName;
		}
		catch(IOException e)
		{
			return "Couldn't get I/O for the connection to " +
					hostName;
		}
		return "Connection Succesful";
	}

}
