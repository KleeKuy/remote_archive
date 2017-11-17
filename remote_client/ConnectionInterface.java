package remote_client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

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
			
			uploadButton.addActionListener(this);
			exitButton.addActionListener(this);
			deleteButton.addActionListener(this);
	}
	
	public void connect(final String hostName, String portname)
	{
		mainPanel.remove(list);
		this.setVisible(true);
		connection = new Connection(hostName,portname);
		try
		{
			list=connection.login();
		} catch (IOException e) 
		{
			JOptionPane.showMessageDialog(null,"Cant get file list from host");
		}
		mainPanel.add(list,BorderLayout.NORTH);
		SwingUtilities.updateComponentTreeUI(this);
		System.out.println("logged in");
	}
	
	public Connection getConnection()
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
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		 if(e.getSource()==uploadButton)
		{
			setVisible(false);
			FilePicker.getInstance().setVisible(true);
		}
		else if(e.getSource()==deleteButton) //not implemented
		{
			//int selectedIndex = list.getSelectedIndex();
			
		//	if(selectedIndex != -1)
			//{
				//FilePicker.getInstance().deleteFile(selectedIndex);
			///	this.setVisible(false);
			//	this.update();
			//}
		}
		else if(e.getSource()==downloadButton) //not implemented
		{
			
		}
		else 
		{
			connection.disconnect();
			this.setVisible(false);
		 	Menu.getInstance().setVisible(true);
		}
	}
}
