package remote_client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Menu extends JFrame implements ActionListener{	
	

	private static final long serialVersionUID = 1L;
	
	private JTextField ipTextField;
	private JTextField portTextField;
	
	public Menu()
	{
		super("Menu"); // TODO temp value
		
	    final Dimension buttonsize = new Dimension(400,100);
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		JButton logButton = new JButton("Log in");
		logButton.setPreferredSize(buttonsize);
	    ipTextField = new JTextField("Specify host IP");
		ipTextField.setPreferredSize(buttonsize);
		portTextField = new JTextField("Specify host port");
		portTextField.setPreferredSize(buttonsize);
		
		
		add(mainPanel);
		mainPanel.add(logButton, BorderLayout.SOUTH);
		mainPanel.add(ipTextField,BorderLayout.NORTH);
		mainPanel.add(portTextField,BorderLayout.CENTER);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		logButton.addActionListener(this);

	}

	public void actionPerformed(ActionEvent e)
	{
		//TODO check if port is integer
        JOptionPane.showMessageDialog(null,Connection.Connect(ipTextField.getText(),portTextField.getText()));
		
	}
}
