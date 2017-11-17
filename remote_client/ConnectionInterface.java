package remote_client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ConnectionInterface extends JFrame implements ActionListener {

	private Connection connection;
	
	private JPanel mainPanel;
	private JButton setLocation;
	private JButton deleteButton;
	private JButton exitButton;
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
			
			setLocation= new JButton("Pick file to archive"); 
			setLocation.setPreferredSize(Common.buttonsize());
			
			exitButton = new JButton("Disconnect");
			exitButton.setPreferredSize(Common.buttonsize());
			
			deleteButton = new JButton("Delete file");
			deleteButton.setPreferredSize(Common.buttonsize());
			
			//TODO check files from configs
			String[] data = {};
			list = new JList<String>(data);
			list.setLayoutOrientation(JList.VERTICAL);

			
			add(mainPanel);
			mainPanel.add(exitButton, BorderLayout.SOUTH);
			mainPanel.add(setLocation,BorderLayout.EAST);
			mainPanel.add(deleteButton,BorderLayout.WEST);
			mainPanel.add(list,BorderLayout.NORTH);

			pack();
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setVisible(true);
			
			setLocation.addActionListener(this);
			exitButton.addActionListener(this);
			deleteButton.addActionListener(this);
	}
	
	public void connect(final String hostName, String portname)
	{
		this.setVisible(true);
		//Connection connection = new Connection(hostName,portname);

	}
	
	public void update()
	{
		mainPanel.remove(list);
		String[] files = new String[FilePicker.getInstance().getFileList().size()];
		int i=0;
		while(i<files.length)
			files[i] = FilePicker.getInstance().getFileList().get(i++).getName();
		list = new JList<String>(files);
		mainPanel.add(list,BorderLayout.NORTH);
		SwingUtilities.updateComponentTreeUI(this);
		this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		 if(e.getSource()==setLocation)
		{
			setVisible(false);
			FilePicker location = FilePicker.getInstance();
			location.setVisible(true);
		}
		else if(e.getSource()==deleteButton)
		{
			int selectedIndex = list.getSelectedIndex();
			
			if(selectedIndex != -1)
			{
				FilePicker.getInstance().deleteFile(selectedIndex);
				this.setVisible(false);
				this.update();
			}
		}
		else 
		{
			this.setVisible(false);
		 	Menu.getInstance().setVisible(true);
		}
	}


}
