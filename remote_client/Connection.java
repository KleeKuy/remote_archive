package remote_client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.Objects;

import javax.swing.JOptionPane;

/*
 * Class that is responsible for all the
 * connection with host application
 */
public class Connection {
	
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader inHost;
    private	OutputStream outs;
    private InputStream ins;

	public Connection(final String hostName, String portname) throws UnknownHostException, IOException
	{
		final int port = Integer.parseInt(portname);
		clientSocket = new Socket(hostName, port);
		out = new PrintWriter(clientSocket.getOutputStream(), true);       
		inHost = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		outs = clientSocket.getOutputStream();
		ins = clientSocket.getInputStream();
	}
	
	/*
	 * Application logs in after connecting
	 */
	public final String[] login() throws IOException
	{
		out.println(Protocol.LOGIN);
		return getList();
	}
	
	/*
	 * Method that gets from host file list
	 */
	public final String[] getList() throws IOException
	{
		final int num = Integer.parseInt(inHost.readLine());
		String[] list = new String[num];
		for(int i=0; i<num; i++)
		list[i]=inHost.readLine();
		return list;
	}

	public final void send(File file)
	{
		out.println(Protocol.SENDING_FILE);
		out.println(file.length());
		out.println(file.getName());

		try
		{
		if(Objects.equals(inHost.readLine(),Protocol.ABORT_SEND))
		{
			JOptionPane.showMessageDialog(null,"This file is already archivized!");
			return;	
		}
		
    	byte[] bytes = new byte[4096];
    	InputStream in = new FileInputStream(file);
    	int count;
        while ((count = in.read(bytes)) > 0)
        {
            outs.write(bytes, 0, count);
        }
        in.close();
		} catch(IOException e)
		{
			JOptionPane.showMessageDialog(null,"Exception raised while sending file!");
		}
		System.out.println("sent");
	}
	
	public final void delete(int index)
	{
		out.println(Protocol.DELETE);
		out.println(index);
	}
	
	public final void download(String directory,int index)
	{
		out.println(Protocol.DOWNLOAD);
		out.println(index);
		try
		{
		long size = Long.parseLong(inHost.readLine());
		final String name = inHost.readLine();		
		byte[] bytes = new byte[4096];		
		FileOutputStream out = new FileOutputStream(directory + "\\" + name);
		System.out.println(directory + name);
        int count;
        int sent =0;
        while (sent < size &&(count = ins.read(bytes)) > 0) 
        {
        	sent+=count;
            out.write(bytes, 0, count);
        }
        out.close();
		} catch(IOException e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"Exception raised while receiving file!");
		}
	}
	
	public void disconnect()
	{
		out.println(Protocol.LOGOUT);
		try 
		{
			out.close();
			inHost.close();	
			clientSocket.close();

		} catch (IOException e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"Failed to close socket");
		}
	}
}
