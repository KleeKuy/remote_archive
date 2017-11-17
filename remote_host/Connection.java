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


public class Connection implements Runnable
{
	private final int port;
	private ServerSocket serverSocket;
	private Socket socket;
	private BufferedReader in;
	private PrintWriter outClient;
	private InputStream  ins;
	private Thread thread;
	
	public Connection(String portstring)
	{
		port = Integer.parseInt(portstring);
		
		try
		{
			serverSocket = new ServerSocket (port);
			socket = serverSocket.accept();   
			in = new BufferedReader(
	                new InputStreamReader(socket.getInputStream()));
			outClient = new PrintWriter(socket.getOutputStream(), true);       
			
			ins = socket.getInputStream();
		} catch(IOException e)
		{
			System.out.println("Exception caught when trying to listen on port "
	                + port + " or listening for a connection");
		}
		thread = new Thread(this);
		thread.start();
	}
	
	public void receive()
	{
		try
		{
		int packetNumber = Integer.parseInt(in.readLine())/4096;
		final String name = in.readLine();		
		byte[] bytes = new byte[4096];		
		FileOutputStream out = new FileOutputStream(Location.getInstance().getDirectory() + "\\" + name);
		System.out.println(Location.getInstance().getDirectory() + name);
        int count;
        System.out.println(packetNumber);
        if(packetNumber!=0)
        while ((count = ins.read(bytes)) > 0 && packetNumber > 0) 
        {
        	System.out.println(bytes);
        	System.out.println(packetNumber);
        	packetNumber--;
            out.write(bytes, 0, count);
        }
        else
        {
        	System.out.println("was one");
        	count = ins.read(bytes);
        	out.write(bytes, 0, count);
        }
        System.out.print("smth");
        out.close();
		} catch(IOException e)
		{
			JOptionPane.showMessageDialog(null,"Exception raised while receiving file!");
		}
	}

	private void updateList()
	{
		Location.getInstance().setFileList();
		int len = Location.getInstance().getFileList().size();
		outClient.println(len);
		for(int i=0; i<len; i++)
		{
			outClient.println(Location.getInstance().getFileList().get(i).getName());
		}
	}
	
	private void stop() throws IOException
	{
		in.close();
		outClient.close();
		ins.close();
		serverSocket.close();
		socket.close();
		thread.interrupt();
		
	}
	
	@Override
	public void run()
	{
		boolean end = false;
		String msg = null;
		while(end==false)
		{
		try
		{
			msg = in.readLine();
			System.out.println(msg);
			switch(msg)
			{
				case Protocol.LOGIN:
					updateList();
					break;
				case Protocol.LOGOUT:
					stop();
					end=true;
					break;
				case Protocol.SENDING_FILE:
					receive();
					updateList();
					break;
				default:
					System.out.println("Incorrect message!");
					break;		
			}
			} catch (IOException e) 
			{
				JOptionPane.showMessageDialog(null,"Error while receiving message!");
			}
		}			
	}
	
}
