package remote_client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Menu extends JFrame implements ActionListener{	
	
	private static final long serialVersionUID = 1L;
	
	private static Menu instance;
	
	private JPanel mainPanel;
	private JTextField ipTextField;
	private JTextField portTextField;
	private JButton logButton;
	private JButton setLocation;
	private JButton deleteButton;
	private JButton exitButton;
	private JList<String> list;
	
	/**
	 * Class describing main menu
	 */
	private Menu()
	{
		super("Menu");
		
	    final Dimension buttonsize = new Dimension(400,100);
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		logButton = new JButton("Log in");
		logButton.setPreferredSize(buttonsize);
		
		setLocation= new JButton("Pick file to archive"); 
		setLocation.setPreferredSize(buttonsize);
		
	    ipTextField = new JTextField("Specify host IP");
		ipTextField.setPreferredSize(buttonsize);
		
		portTextField = new JTextField("Specify host port");
		portTextField.setPreferredSize(buttonsize);
		
		exitButton = new JButton("Exit");
		exitButton.setPreferredSize(buttonsize);
		
		deleteButton = new JButton("Delete from list");
		deleteButton.setPreferredSize(buttonsize);
		
		//TODO check files from configs
		String[] data = {};
		list = new JList<String>(data) 
				{
			//TODO would prolly need to be able to also remove files from list
				};
		
		JPanel secondPanel = new JPanel();
		secondPanel.add(logButton,BorderLayout.WEST);
		secondPanel.add(exitButton,BorderLayout.EAST);
		
		JPanel thirdPanel = new JPanel();
		thirdPanel.add(setLocation,BorderLayout.WEST);
		thirdPanel.add(deleteButton,BorderLayout.EAST);
		
		add(mainPanel);
		mainPanel.add(secondPanel, BorderLayout.SOUTH);
		mainPanel.add(ipTextField,BorderLayout.EAST);
		mainPanel.add(portTextField,BorderLayout.WEST);
		mainPanel.add(thirdPanel,BorderLayout.NORTH);
		mainPanel.add(list,BorderLayout.CENTER);

		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		logButton.addActionListener(this);
		setLocation.addActionListener(this);
		exitButton.addActionListener(this);
		deleteButton.addActionListener(this);

	}
	
	public static Menu getInstance()
	{
		if(instance == null)
			instance = new Menu();
		return instance;
	}
	
	public void setFocus()
	{
		mainPanel.remove(list);
		String[] files = new String[FilePicker.getInstance().getFileList().size()];
		int i=0;
		while(i<files.length)
			files[i] = FilePicker.getInstance().getFileList().get(i++).getName();
		list = new JList<String>(files)
				{
			
				};
		mainPanel.add(list,BorderLayout.CENTER);
		this.setVisible(true);
		
	}

	public void actionPerformed(ActionEvent e)
	{	
		if(e.getSource()==logButton)
		{
			if(FilePicker.getInstance().getFileList()==null)
				JOptionPane.showMessageDialog(null,"Please select files to archive");
			else if(Common.isNumeric(portTextField.getText()))
				{
				Connection connection = new Connection();
				connection.connect(ipTextField.getText(), portTextField.getText());
				}
			else
				JOptionPane.showMessageDialog(null,"Please enter numeric port value");
		}
		else if(e.getSource()==setLocation)
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
				this.setFocus();
			}
		}
		else
			System.exit(1);
		
	}
}
