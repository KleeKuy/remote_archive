package remote_client;

import java.awt.Dimension;

public class Common {

	public static boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
	
	public static void shutdown()
	{
		//TODO implement, idea is that whenever
		//program is to be closed this method will
		//be called, and it will save stuff and
		//close the app
	}
	
	public static Dimension buttonsize()
	{
		return new Dimension(400,100);
	}
}
