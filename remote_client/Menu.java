package remote_client;

import java.awt.BorderLayout;
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

	private JButton exitButton;
	
	/**
	 * Class describing main menu
	 */
	private Menu()
	{
		super("Menu");
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		logButton = new JButton("Log in");
		logButton.setPreferredSize(Common.buttonsize());
		
	    ipTextField = new JTextField("Specify host IP");
		ipTextField.setPreferredSize(Common.buttonsize());
		
		portTextField = new JTextField("Specify host port");
		portTextField.setPreferredSize(Common.buttonsize());
		
		exitButton = new JButton("Exit");
		exitButton.setPreferredSize(Common.buttonsize());
		
		JPanel secondPanel = new JPanel();
		secondPanel.add(logButton,BorderLayout.WEST);
		secondPanel.add(exitButton,BorderLayout.EAST);

		add(mainPanel);
		mainPanel.add(secondPanel, BorderLayout.SOUTH);
		mainPanel.add(ipTextField,BorderLayout.EAST);
		mainPanel.add(portTextField,BorderLayout.WEST);


		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		logButton.addActionListener(this);
		exitButton.addActionListener(this);

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
			if(FilePicker.getInstance().getFileList()==null)
				JOptionPane.showMessageDialog(null,"Please select files to archive");
			else if(Common.isNumeric(portTextField.getText()))
				{
				this.setVisible(false);
				ConnectionInterface.getInstance().connect(ipTextField.getText(), portTextField.getText());
				}
			else
				JOptionPane.showMessageDialog(null,"Please enter numeric port value");
		}
		else
			System.exit(1);
		
	}
}
