package remote_host;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Location extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;

	private static Location instance;
	
	private JFileChooser chooser;
	private ArrayList<File> listOfFiles;
	private String directory;

	
	private Location()
	{
		super("Choose archive location");
		
		checkPrevious();
		
		JPanel mainPanel = new JPanel();
		chooser = new JFileChooser();
	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    chooser.setAcceptAllFileFilterUsed(false);
		
		add(mainPanel);
		mainPanel.add(chooser, BorderLayout.SOUTH);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		chooser.addActionListener(this);
	}
	
	public static Location getInstance()
	{
		if(instance==null)
			instance = new Location();
		return instance;
	}

	public void actionPerformed(ActionEvent e)
	{	
		if(e.getActionCommand()=="CancelSelection")
		{
			Menu.getInstance().setVisible(true);
		}
		else if(e.getActionCommand()=="ApproveSelection")
		{
			directory = chooser.getSelectedFile().getPath();
			setFileList();		
			JOptionPane.showMessageDialog(null,"Archive location set to : " + directory);
			
		}	
		setVisible(false);
		Menu.getInstance().setVisible(true);
	}
	
	/*
	 * Checks in config files whether there is a location 
	 * set previously
	 */
	public void checkPrevious()
	{
		System.out.println("to be implmented");
		//TODO implement
	}
	
	public void setFileList()
	{
		listOfFiles= new ArrayList<File>();
		File folder = new File(directory);
		File[] tmp = folder.listFiles();
		for(int i = 0; i<tmp.length; i++)
			listOfFiles.add(tmp[i]);
	}
	
	public ArrayList<File> getFileList()
	{
		return listOfFiles;
	}

	public String getDirectory()
	{
		return directory;
	}
}
