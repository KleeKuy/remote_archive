package remote_client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.sun.glass.events.WindowEvent;

public class ConnectionInterface extends JFrame implements ActionListener {

	private Connection connection;
	private JPanel mainPanel;
	private JButton uploadButton;
	private JButton deleteButton;
	private JButton exitButton;
	private JButton downloadButton;
	private JList<String> list;
	private static ConnectionInterface instance;
	public static ConnectionInterface getInstance()
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
			list.setLayoutOrientation(JList.VERTICAL);

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
		list=connection.login();

		}catch(UnknownHostException e)
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
		
		mainPanel.add(list,BorderLayout.NORTH);
		SwingUtilities.updateComponentTreeUI(this);
		System.out.println("logged in");
	}
	
	public final Connection getConnection()
	{
		return connection;
	}

	public void update()
	{
		mainPanel.remove(list);
		try
		{
			list=connection.getList();
		} catch (IOException e) 
		{
			JOptionPane.showMessageDialog(null,"Cant get file list from host");
		}
		mainPanel.add(list,BorderLayout.NORTH);
		SwingUtilities.updateComponentTreeUI(this);
		System.out.println("connected list gotten");
		this.setVisible(true);
	}
	
	private final void deleteRequest()
	{
		int selectedIndex = list.getSelectedIndex();
		if(selectedIndex != -1)
			connection.delete(selectedIndex);
	}
	
	private final void downloadRequest()
	{
		int selectedIndex = list.getSelectedIndex();
		if(selectedIndex == -1)
			JOptionPane.showMessageDialog(null,"No file selected!");
		else
		{
			connection.setIndex(selectedIndex);
			JOptionPane.showMessageDialog(null,"Select directory to download file");
			this.setVisible(false);
			FilePicker.getInstance().pickDir();
			FilePicker.getInstance().setVisible(true);
		}
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
			update();
		}
		else if(e.getSource()==downloadButton)
		{
			downloadRequest();
		}
		else 
		{
			connection.disconnect();
			this.setVisible(false);
		 	Menu.getInstance().setVisible(true);
		}
	}
}
