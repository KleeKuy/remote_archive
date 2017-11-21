package remote_client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Synchronizer implements Runnable {

	private ServerSocket serverSocket;
	private Socket socket;
	private BufferedReader in;
	private Thread thread;
	private String portname;

	
	public Synchronizer(String port)
	{
		portname = port;
		thread = new Thread();
		thread.start();
	}
	
	private void init()
	{
		int port = Integer.parseInt(portname);
		try 
		{
			System.out.println(port);
			serverSocket = new ServerSocket(++port);
			socket = serverSocket.accept();
			in = new BufferedReader(
	                new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) 
		{
			ConnectionInterface.getInstance().disconnect();
			ConnectionInterface.getInstance().setVisible(false);
			Menu.getInstance().setVisible(true);
			e.printStackTrace();
			return;
		}   
	}
	
	public void disconnect()
	{
		try {
			if(socket!=null)
			{
			socket.close();
			serverSocket.close();
			}
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		thread.interrupt();
	}
	
	private void synchronize() throws NumberFormatException, IOException
	{
		final int num = Integer.parseInt(in.readLine());
		String[] list = new String[num];
		for(int i=0; i<num; i++)
		list[i]=in.readLine();
		ConnectionInterface.getInstance().synchronizeList(list);
	}
	
	@Override
	public void run() 
	{
		init();
		while(true)
		{
			try {
				if(in.readLine()==Protocol.SYNCHRONIZE)
					synchronize();
			} catch (IOException e) 
			{
				ConnectionInterface.getInstance().disconnect();
				e.printStackTrace();
			}
		}
		
	}

}
