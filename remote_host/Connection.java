package remote_host;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Connection
{
	public Connection(String portstring)
	{
		final int port = Integer.parseInt(portstring);
		try 
		{
			ServerSocket serverSocket =
					new ServerSocket (port);
			Socket socket = serverSocket.accept();   
			
			BufferedReader in = new BufferedReader(
	                new InputStreamReader(socket.getInputStream()));
			
			PrintWriter outClient =
	                new PrintWriter(socket.getOutputStream(), true);       
			
			InputStream  ins = socket.getInputStream();
			
			//TODO do smth with dis n0_of_files, also TODO add protocol class
			int number_of_files = 0;
			if(in.readLine()=="files");
			number_of_files=Integer.parseInt(in.readLine());
			
			for(int i=0; i<number_of_files; i++)
			{
				System.out.println(in.readLine());
				
				boolean onePacket = false;
				
				int size = Integer.parseInt(in.readLine())/4096;
				if(size==0)
					onePacket = true;
				byte[] bytes = new byte[4096];
				System.out.println(in.readLine());
				String name = in.readLine();
				
				FileOutputStream out = new FileOutputStream(Location.getInstance().getDirectory() + "\\" + name);

				System.out.println(Location.getInstance().getDirectory() + name);
				
		        int count;

		        if(!onePacket)
		        while ((count = ins.read(bytes)) > 0 && size > 0) 
		        {
		        	//System.out.println(bytes);
		        	System.out.println(size);
		        	size--;
		            out.write(bytes, 0, count);
		        }
		        else
		        {
		        	System.out.println("was one");
		        	count = ins.read(bytes);
		        	out.write(bytes, 0, count);
		        }
		        	

		        System.out.println("smth");
		        outClient.println("file_received");
		        out.close();

			}
			
			
			serverSocket.close();
		}
		catch(IOException e)
		{
			System.out.println("Exception caught when trying to listen on port "
	                + port + " or listening for a connection");
		}
		
	}
}
