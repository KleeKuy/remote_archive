package remote_client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Location extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;

	private static Location instance;
	
	private Location()
	{
		super("Choose archive location");
		
		JPanel mainPanel = new JPanel();
		JFileChooser chooser = new JFileChooser();
		JButton okButton = new JButton("OK");
		
		add(mainPanel);
		mainPanel.add(chooser, BorderLayout.SOUTH);
		mainPanel.add(okButton, BorderLayout.NORTH);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		okButton.addActionListener(this);
		chooser.addActionListener(this);
		
	  /*  int returnVal = chooser.showOpenDialog(parent);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	       System.out.println("You chose to open this file: " +
	            chooser.getSelectedFile().getName());*/
		
	}
	
	public static Location getInstance()
	{
		if(instance==null)
			instance = new Location();
		return instance;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		setVisible(false);
		Menu.getInstance().setVisible(true);
		
	}
	
	/*
	 * Checks in config files whether there is a location 
	 * set previously
	 */
	public void checkPrevious()
	{
		//TODO implement
	}

}
