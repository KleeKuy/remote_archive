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
	
	private enum action
	{
		DOWNLOADING, UPLOADING
	}
	
	private action cmd;
	
	private FilePicker()
	{
		super("Choose archive location");
				
		checkPrevious();
		
		JPanel mainPanel = new JPanel();
		chooser = new JFileChooser();
	    chooser.setAcceptAllFileFilterUsed(true);
		
		add(mainPanel);
		mainPanel.add(chooser, BorderLayout.SOUTH);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		chooser.addActionListener(this);
	}
	
	public void pickDir()
	{
		cmd = action.DOWNLOADING;
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	}
	
	public void pickFile()
	{
		file = null;
		cmd = action.UPLOADING;
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
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
			switch(cmd)
			{
			case UPLOADING:
				System.out.println("approved selection!");
				String directory = chooser.getSelectedFile().getPath();
				addFile(directory);		
				JOptionPane.showMessageDialog(null,"New file - "+ directory +"to be added");
				setVisible(false);
				ConnectionInterface.getInstance().getConnection().send(file);
				ConnectionInterface.getInstance().update();
				break;
			case DOWNLOADING:
				String dir = directory = chooser.getSelectedFile().getPath();;
				addFile(dir);		
				JOptionPane.showMessageDialog(null,"Download directory - "+ dir);
				setVisible(false);
				ConnectionInterface.getInstance().getConnection().download(dir);
				ConnectionInterface.getInstance().setVisible(true);
				break;
			default:
				System.out.println("Something went wrong...");
				break;
			}
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
	
	public final File getFile()
	{
		return file;
	}
	
}
