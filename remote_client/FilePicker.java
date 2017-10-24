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
	private ArrayList<File> listOfFiles;
	
	private FilePicker()
	{
		super("Choose archive location");
		
		listOfFiles = new ArrayList<File>();
		
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
			Menu.getInstance().setFocus();
		}
		else if(e.getActionCommand()=="ApproveSelection")
		{
			String directory = chooser.getSelectedFile().getPath();
			addFile(directory);		
			JOptionPane.showMessageDialog(null,"New file - "+ directory +" added");
			
		}	
		setVisible(false);
		Menu.getInstance().setFocus();
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
		File file = new File(directory);		
		listOfFiles.add(file);
	}
	
	public ArrayList<File> getFileList()
	{
		return listOfFiles;
	}

}
