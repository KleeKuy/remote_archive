package remote_client;

import java.awt.Dimension;

/*
 * Class containing common methods and variables
 */

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
	
	public static Dimension buttonsize()
	{
		return new Dimension(400,100);
	}
	
	public enum action
	{
		DOWNLOADING, UPLOADING
	}
}
