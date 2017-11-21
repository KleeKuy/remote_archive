package remote_host;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Synchronizer {
	
	private Socket socket;
	private PrintWriter out;
	private String[] synchronizedList;
	private Location location;

	public Synchronizer(int port, String ipAdress,Location loc)
	{
		location = loc;
		try 
		{
			System.out.println(ipAdress + " " + ++port);
			socket = new Socket(ipAdress,port);
			out = new PrintWriter(socket.getOutputStream(), true);   
		} catch (IOException e) 
		{
			e.printStackTrace();
		}   
	}
	
	public void synchronize()
	{
		accessConfig();
		out.println(Protocol.SYNCHRONIZE);
		location.setFileList();
		int len = location.getFileList().size();
		out.println(len);
		for(int i=0; i<len; i++)
		{
			out.println(location.getFileList().get(i).getName());
		}
	}
	
	synchronized private final void accessConfig()
	{
	/*	File folder = new File(directory);
		File[] tmp = folder.listFiles();
		for(int i = 0; i<tmp.length; i++)
			listOfFiles.add(tmp[i]);*/
	}
	
	public void disconnect() throws IOException
	{
		out.close();
		socket.close();
	}
}
