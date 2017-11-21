package remote_client;

import java.io.File;
import java.util.ArrayList;

public class Queuer implements Runnable{

	private static Queuer instance;
	private ArrayList<queEvent> events;
	private Thread thread;
	
	
	private Queuer()
	{
		events = new ArrayList<queEvent>();
		thread = new Thread(this);
		thread.start();
	}
	
	public static Queuer getInstance()
	{
		if(instance == null)
			instance = new Queuer();
		return instance;
	}
	
	synchronized public void addToQueueUpload(File file)
	{
		queEventUpload event = new queEventUpload(file);
		events.add(event);
		this.notify();
	}
	
	synchronized public void addToQueueDownload(String directory, int index)
	{
		queEventDownload event = new queEventDownload(directory,index);
		events.add(event);
		this.notify();
	}
	
	synchronized public void addToQueueDelete(int index)
	{
		queEventDelete event = new queEventDelete(index);
		events.add(event);
		this.notify();
	}
	
	synchronized private void waiting()
	{
		System.out.println("start to wait");
		try 
		{
			this.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() 
	{
		System.out.println("run first");
		while(true)
		{
			System.out.println("WHILE");
			if(events.isEmpty()==false)
			{
				System.out.println("to execute");
				events.get(0).execute();
				events.remove(0);
			} else
				waiting();
		}
		
	}

} //Queuer

abstract class queEvent
{
	 abstract public void execute();
}

class queEventDownload extends queEvent
{
	private int index;
	private String directory;
	
	public queEventDownload(String dir, int ind)
	{
		directory = dir;
		index = ind;
	}
	
	@Override
	public void execute()
	{
		ConnectionInterface.getInstance().download(directory, index);
	}
}

class queEventUpload extends queEvent
{
	private File file;
	
	public queEventUpload(File file)
	{
		this.file=file;
	}
	
	@Override
	public void execute()
	{
		ConnectionInterface.getInstance().send(file);
		ConnectionInterface.getInstance().update();
	}
	
}

class queEventDelete extends queEvent
{

	private int index;
	public queEventDelete(int in)
	{
		index = in;
	}
	@Override
	public void execute()
	{
		ConnectionInterface.getInstance().delete(index);
	}
	
}
