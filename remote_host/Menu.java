package remote_host;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Menu extends JFrame implements ActionListener{	
	

	private static final long serialVersionUID = 1L;
	
	private JTextField ipTextField;
	private JButton logButton;
	private JButton setLocation;
	
	private static Menu instance;
	
	private Menu()
	{
		super("Remote archive host");
		
	    final Dimension buttonsize = new Dimension(400,100);
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		logButton = new JButton("Start server");
		logButton.setPreferredSize(buttonsize);
		setLocation = new JButton("Set location");
		setLocation.setPreferredSize(buttonsize);
	    ipTextField = new JTextField("Specify host port");
		ipTextField.setPreferredSize(buttonsize);
		
		
		add(mainPanel);
		mainPanel.add(logButton, BorderLayout.SOUTH);
		mainPanel.add(ipTextField,BorderLayout.NORTH);
		mainPanel.add(setLocation,BorderLayout.CENTER);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		logButton.addActionListener(this);
		setLocation.addActionListener(this);

	}
	
	static public Menu getInstance()
	{
		if(instance == null)
			instance = new Menu();
		return instance;
	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==logButton)
		{
			if(Location.getInstance().getFileList()==null)
				JOptionPane.showMessageDialog(null,"Please select location!");
			else
				new Connection(ipTextField.getText());
		}
		else
			Location.getInstance().setVisible(true);
	}
}