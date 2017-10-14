package remote_client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Menu extends JFrame implements ActionListener{	
	
	private static final long serialVersionUID = 1L;
	
	private static Menu instance;
	
	private JTextField ipTextField;
	private JTextField portTextField;
	private JButton logButton;
	private JButton setLocation;
	
	/**
	 * Class describing main menu
	 */
	private Menu()
	{
		super("Menu");
		
	    final Dimension buttonsize = new Dimension(400,100);
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		logButton = new JButton("Log in");
		logButton.setPreferredSize(buttonsize);
		setLocation= new JButton("Set archive location"); 
		setLocation.setPreferredSize(buttonsize);
	    ipTextField = new JTextField("Specify host IP");
		ipTextField.setPreferredSize(buttonsize);
		portTextField = new JTextField("Specify host port");
		portTextField.setPreferredSize(buttonsize);
		
		
		
		add(mainPanel);
		mainPanel.add(logButton, BorderLayout.SOUTH);
		mainPanel.add(ipTextField,BorderLayout.EAST);
		mainPanel.add(portTextField,BorderLayout.WEST);
		mainPanel.add(setLocation,BorderLayout.NORTH);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		logButton.addActionListener(this);
		setLocation.addActionListener(this);

	}
	
	public static Menu getInstance()
	{
		if(instance == null)
			instance = new Menu();
		return instance;
	}

	public void actionPerformed(ActionEvent e)
	{	

		if(e.getSource()==logButton)
		{
			//TODO rethink connection method. Should it really be string type?
			//prolly not, but it should use its own window rather than dialog sooo...
			if(Common.isNumeric(portTextField.getText()))
				JOptionPane.showMessageDialog(null,Connection.Connect(ipTextField.getText(),portTextField.getText()));
			else
				JOptionPane.showMessageDialog(null,"Please enter numeric port value");
		}
		else
		{
			setVisible(false);
			Location location = Location.getInstance();
			location.setVisible(true);

		}
		
	}
}
