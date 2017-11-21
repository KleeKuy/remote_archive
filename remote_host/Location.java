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
	private JFileChooser chooser;
	private ArrayList<File> listOfFiles;
	private String directory;

	
	public Location()
	{
		super("Choose archive location");
				
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
	
	synchronized public void setFileList()
	{
		listOfFiles= new ArrayList<File>();
		File folder = new File(directory);
		File[] tmp = folder.listFiles();
		for(int i = 0; i<tmp.length; i++)
			listOfFiles.add(tmp[i]);
	}
	
	public final ArrayList<File> getFileList()
	{
		return listOfFiles;
	}

	public final String getDirectory()
	{
		return directory;
	}
	
	public void deleteFile(int index)
	{
		listOfFiles.get(index).delete();
		listOfFiles.remove(index);
	}
}
