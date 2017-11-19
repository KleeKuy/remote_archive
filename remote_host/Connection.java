package remote_host;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
	private InputStream ins;
	private OutputStream outs;
	private Thread thread;
	
	public Connection(String portstring)
	{
		port = Integer.parseInt(portstring);
		
		try
		{
			serverSocket = new ServerSocket (port);
			socket = serverSocket.accept();   
			outs = socket.getOutputStream();
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
	
	private final void receive()
	{
		try
		{
		long size = Long.parseLong(in.readLine());
		final String name = in.readLine();		
		byte[] bytes = new byte[4096];		
		FileOutputStream out = new FileOutputStream(Location.getInstance().getDirectory() + "\\" + name);
		System.out.println(Location.getInstance().getDirectory() + name);
        int count;
        int sent=0;
        while (sent < size && (count = ins.read(bytes)) > 0)
        {
        	sent+=count;
        	System.out.println(sent + " out of " + size);
            out.write(bytes, 0, count);
        }
        System.out.print("smth");
        out.close();
		} catch(IOException e)
		{
			JOptionPane.showMessageDialog(null,"Exception raised while receiving file!");
		}
	}

	private final void updateList()
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
	
	private final void delete() throws IOException
	{
		int index = Integer.parseInt(in.readLine());
		Location.getInstance().deleteFile(index);
	}
	
	private final void sendToClient() throws IOException
	{
		int index = Integer.parseInt(in.readLine());
		File file = Location.getInstance().getFileList().get(index);
		System.out.println("sending " + file.getName());
		
		outClient.println(file.length());
		outClient.println(file.getName());
		
		byte[] bytes = new byte[4096];
		InputStream inst = new FileInputStream(file);
		int count;
	    while ((count = inst.read(bytes)) > 0)
	    {
	        outs.write(bytes, 0, count);
	    }
	    inst.close();
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
				case Protocol.DELETE:
					delete();
					updateList();
					break;
				case Protocol.DOWNLOAD:
					sendToClient();
					break;
				default:
					System.out.println("Incorrect message!");
					break;		
			}
			} catch (IOException e) 
			{
				e.printStackTrace();
				JOptionPane.showMessageDialog(null,"Error while communicating with host!");
				System.exit(1);
			}
		}			
	}
	
}
