package remote_client;

import java.io.File;
import java.util.ArrayList;

/**
 * Class that is used to keep all the actions performed by user, so that
 * all downloading/uploading is done in the background while user can continue
 * to use the application. Every action performed by user is added to events 
 * ArrayList and the actions are executed one by one in run method. If there are
 * no actions in queue thread waits (this.wait) and is notified when new action
 * is added.
 */
public class Queuer implements Runnable{

	private static Queuer instance;
	/*
	 * List with user actions that are not yet executed
	 */
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
		try 
		{
			this.wait();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() 
	{
		while(true)
		{
			if(events.isEmpty()==false)
			{
				events.get(0).execute();
				events.remove(0);
			} else
				waiting();
		}
		
	}

} //Queuer

/*
 * Abstract class that is base for all the event classes
 * so that those classes can be hold in one container
 */
abstract class queEvent
{
	 abstract public void execute();
}

/*
 * Class representing download event that is to be added to queue
 */
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

/*
 * Class representing upload event that is to be added to queue
 */
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

/*
 * Class representing delete event that is to be added to queue
 */
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
