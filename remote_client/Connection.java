package remote_client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JOptionPane;


public class Connection {
	
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader inHost;
    private	OutputStream outs;
    private InputStream ins;
	private int index;

	public Connection(final String hostName, String portname) throws UnknownHostException, IOException
	{
		//TODO possibly read from config port and ip valuse
		//hence in the future connection class will be storing
		//port and ip as class variables?
		final int port = Integer.parseInt(portname);

		clientSocket = new Socket(hostName, port);
		out = new PrintWriter(clientSocket.getOutputStream(), true);       
		inHost = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		outs = clientSocket.getOutputStream();
		ins = clientSocket.getInputStream();
		
	}
	
	public final JList<String> login() throws IOException
	{
		out.println(Protocol.LOGIN);
		return getList();
	}
	public final JList<String> getList() throws IOException
	{
		final int num = Integer.parseInt(inHost.readLine());
		String[] list = new String[num];
		for(int i=0; i<num; i++)
		list[i]=inHost.readLine();
		return new JList<String>(list);
	}

	public final void send(File file)
	{
		System.out.println("sending");
		out.println(Protocol.SENDING_FILE);
		out.println(file.length());
		out.println(file.getName());
		
		try
		{
    	byte[] bytes = new byte[4096];
    	InputStream in = new FileInputStream(file);
    	int count;
    	long size=file.length();
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
	
	public final void download(String directory)
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
	
	public void setIndex(int index)
	{
		this.index=index;
	}
	
	
	
	/*public void connect(final String hostName, String portname)
	{
		final int port = Integer.parseInt(portname);
		
		try 
		{
			PrintWriter out =
	                new PrintWriter(clientSocket.getOutputStream(), true);       
			BufferedReader inHost = new BufferedReader(
	                new InputStreamReader(clientSocket.getInputStream()));
			OutputStream outs = clientSocket.getOutputStream();

			//TODO this is subject to change, as of now functionality is that
			//we connect to host, send stuff and disconnect, future versions
			//will support keeping connection for some time to enable more
			//actions
	        out.println(FilePicker.getInstance().getFileList().size());
	        
	        for(int i=0; i<FilePicker.getInstance().getFileList().size(); i++)
	        {
	        	int count;
	        	out.println(Protocol.SIZE);
	        	out.println((int) FilePicker.getInstance().getFileList().get(i).length());
	        	out.println(Protocol.NAME);
	        	out.println(FilePicker.getInstance().getFileList().get(i).getName());
	        	
	        	byte[] bytes = new byte[4096];
	        	InputStream in = new FileInputStream(FilePicker.getInstance().getFileList().get(i));
	            while ((count = in.read(bytes)) > 0)
	            {
	                outs.write(bytes, 0, count);
	            }
	            
	            String response = inHost.readLine();
	            System.out.println(response);
	            in.close();

	        }
	        out.close();
	        outs.close();
	        clientSocket.close();
		}
		catch(UnknownHostException e)
		{
			JOptionPane.showMessageDialog(null,"Don't know about host " + hostName);
		}
		catch(IOException e)
		{
			JOptionPane.showMessageDialog(null,"Couldn't get I/O for the connection to " + hostName);
		}
		JOptionPane.showMessageDialog(null,"Conection succesful");
	}*/

	public void disconnect()
	{
		out.println(Protocol.LOGOUT);
		try 
		{
			out.close();
		//	outs.close();
			inHost.close();	
			clientSocket.close();

		} catch (IOException e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,"Failed to close socket");
		}
	}
}
