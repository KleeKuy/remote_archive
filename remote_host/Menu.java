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
	
	public Menu()
	{
		super("Remote archive host");
		
	    final Dimension buttonsize = new Dimension(400,100);
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		JButton logButton = new JButton("Start server");
		logButton.setPreferredSize(buttonsize);
	    ipTextField = new JTextField("Specify host port");
		ipTextField.setPreferredSize(buttonsize);
		
		
		add(mainPanel);
		mainPanel.add(logButton, BorderLayout.SOUTH);
		mainPanel.add(ipTextField,BorderLayout.NORTH);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		logButton.addActionListener(this);

	}

	public void actionPerformed(ActionEvent e)
	{
        //JOptionPane.showMessageDialog(null,Connection.Connect(ipTextField.getText()));
		Connection connection = new Connection(ipTextField.getText());
		JOptionPane.showMessageDialog(null,"done");
	}
}