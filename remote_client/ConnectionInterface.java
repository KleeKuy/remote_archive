package remote_client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/*
 * Interface for connection, contains connection menu
 * which is used by user to communicate with host
 */
public class ConnectionInterface extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private Connection connection;
	private JPanel mainPanel;
	private JButton uploadButton;
	private JButton deleteButton;
	private JButton exitButton;
	private JButton downloadButton;
	/*
	 * List of files archivized
	 */
	private JList<String> list;
	private static ConnectionInterface instance;
	
	synchronized public static ConnectionInterface getInstance()
	{
		if(instance == null)
			instance = new ConnectionInterface();
		return instance;
	}
	
	private ConnectionInterface()
	{
		super("Connected!");
			
			mainPanel = new JPanel();
			mainPanel.setLayout(new BorderLayout());
			
			uploadButton= new JButton("Pick file to archive"); 
			uploadButton.setPreferredSize(Common.buttonsize());
			
			exitButton = new JButton("Disconnect");
			exitButton.setPreferredSize(Common.buttonsize());
			
			deleteButton = new JButton("Delete file");
			deleteButton.setPreferredSize(Common.buttonsize());
			
			downloadButton = new JButton("Download file");
			downloadButton.setPreferredSize(Common.buttonsize());
			
			String[] data = {};
			list = new JList<String>(data);
			list.setLayoutOrientation(JList.VERTICAL_WRAP);

			JPanel secondPanel = new JPanel();
			secondPanel.add(exitButton, BorderLayout.WEST);
			secondPanel.add(downloadButton, BorderLayout.EAST);
			
			add(mainPanel);
			mainPanel.add(secondPanel, BorderLayout.SOUTH);
			mainPanel.add(uploadButton,BorderLayout.EAST);
			mainPanel.add(deleteButton,BorderLayout.WEST);
			mainPanel.add(list,BorderLayout.NORTH);

			pack();
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setVisible(true);
			
			downloadButton.addActionListener(this);
			uploadButton.addActionListener(this);
			exitButton.addActionListener(this);
			deleteButton.addActionListener(this);
	}
	
	public void connect(final String hostName, String portname)
	{
		mainPanel.remove(list);
		this.setVisible(true);
		try
		{
		connection = new Connection(hostName,portname);
		list=new JList<String>(connection.login());
		}
		catch(UnknownHostException e)
		{
			JOptionPane.showMessageDialog(null,"Don't know about host " + hostName);
			this.setVisible(false);
			Menu.getInstance().setVisible(true);
			return;
		}
		catch(IOException e)
		{
			JOptionPane.showMessageDialog(null,"Couldn't get I/O for the connection to " + hostName);
			this.setVisible(false);
			Menu.getInstance().setVisible(true);
			return;
		}
		list.setLayoutOrientation(JList.VERTICAL);
		mainPanel.add(list,BorderLayout.NORTH);
		SwingUtilities.updateComponentTreeUI(this);
		System.out.println("logged in");
	}
	
	public final void send(File file)
	{
		connection.send(file);
	}
	
	public final void download(String Dir,int index)
	{
		connection.download(Dir,index);
	}
	
	public final void disconnect()
	{
		JOptionPane.showMessageDialog(null,"Disconnecting!");
		connection.disconnect();
		this.setVisible(false);
	 	Menu.getInstance().setVisible(true);
	}

	/*
	 * updates list of files and refreshes the list
	 */
	synchronized public void update()
	{
		try
		{
			String[] list=connection.getList();
			synchronizeList(list);
		} catch (IOException e) 
		{
			JOptionPane.showMessageDialog(null,"Cant get file list from host");
		}
		this.setVisible(true);
	}
	
	private final void deleteRequest()
	{
		int selectedIndex = list.getSelectedIndex();
		if(selectedIndex != -1)
			Queuer.getInstance().addToQueueDelete(selectedIndex);
	}
	
	public void delete(int index)
	{
		connection.delete(index);
		update();
	}
	
	private final void downloadRequest()
	{
		int selectedIndex = list.getSelectedIndex();
		if(selectedIndex == -1)
			JOptionPane.showMessageDialog(null,"No file selected!");
		else
		{
			FilePicker.getInstance().setIndex(selectedIndex);
			JOptionPane.showMessageDialog(null,"Select directory to download file");
			this.setVisible(false);
			FilePicker.getInstance().pickDir();
			FilePicker.getInstance().setVisible(true);
		}
	}
	
	/*
	 * updates list of files and refreshes the list
	 */
	synchronized public void synchronizeList(String[] stringList)
	{
		mainPanel.remove(list);
		list = new JList<String>(stringList);
		list.setLayoutOrientation(JList.VERTICAL_WRAP);
		mainPanel.add(list,BorderLayout.NORTH);
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		 if(e.getSource()==uploadButton)
		{
			setVisible(false);
			FilePicker.getInstance().pickFile();
			FilePicker.getInstance().setVisible(true);
		}
		else if(e.getSource()==deleteButton)
		{
			deleteRequest();
		}
		else if(e.getSource()==downloadButton)
		{
			downloadRequest();
		}
		else 
		{
			disconnect();
		}
	}
}
