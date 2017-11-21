package remote_client;

/*
 * Class describing messages used to communicate between host and client
 */
public class Protocol 
{
	static final String LOGIN = "login";
	
	static final String LOGOUT ="logout";
	
	static final String SENDING_FILE = "sending_file";
		
	static final String DELETE = "delete";
	
	static final String SIZE = "size";
	
	static final String NAME = "name";
	
	static final String DOWNLOAD = "download";
		
	static final String CONFIRM_SEND = "c_send";
	
	static final String ABORT_SEND = "a_send";
	
}
