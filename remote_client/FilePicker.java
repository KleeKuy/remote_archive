package remote_client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class FilePicker extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;

	private static FilePicker instance;
	
	private JFileChooser chooser;
	private File file;
	
	private FilePicker()
	{
		super("Choose archive location");
				
		checkPrevious();
		
		JPanel mainPanel = new JPanel();
		chooser = new JFileChooser();
	    chooser.setAcceptAllFileFilterUsed(false);
		
		add(mainPanel);
		mainPanel.add(chooser, BorderLayout.SOUTH);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		chooser.addActionListener(this);
	}
	
	public static FilePicker getInstance()
	{
		if(instance==null)
			instance = new FilePicker();
		return instance;
	}

	public void actionPerformed(ActionEvent e)
	{	
		if(e.getActionCommand()=="CancelSelection")
		{
			this.setVisible(false);
			ConnectionInterface.getInstance().setVisible(true);
		}
		if(e.getActionCommand()=="ApproveSelection")
		{
			System.out.println("approved selection!");
			String directory = chooser.getSelectedFile().getPath();
			addFile(directory);		
			JOptionPane.showMessageDialog(null,"New file - "+ directory +"to be added");
			setVisible(false);
			ConnectionInterface.getInstance().getConnection().send(file);
			ConnectionInterface.getInstance().update();
		}	

		//file = null;
	}
	
	/*
	 * Checks in config files whether there are files 
	 * set previously in config
	 */
	private void checkPrevious()
	{
		System.out.println("to be implmented");
		//TODO implement
	}
	
	private void addFile(String directory)
	{
		file = new File(directory);		
	}
	
	public File getFile()
	{
		return file;
	}

	//public void deleteFile(int index)
	//{
	//	listOfFiles.remove(index);
	//}
	
}
