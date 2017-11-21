package remote_client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * Class that is used for picking directories where to 
 * download files from host, or to picking files to upload
 * to host
 */
public class FilePicker extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;

	private static FilePicker instance;
	
	private JFileChooser chooser;
	private File file;
	private int index;
	/*
	 * Describes user action, uploading or downloading file
	 */
	private Common.action cmd;
	
	private FilePicker()
	{
		super("Choose archive location");
						
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
		cmd = Common.action.DOWNLOADING;
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	}
	
	public void pickFile()
	{
		file = null;
		cmd = Common.action.UPLOADING;
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	}
	
	public void setIndex(int indeks)
	{
		index=indeks;
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
				String directory = chooser.getSelectedFile().getPath();
				addFile(directory);		
				JOptionPane.showMessageDialog(null,"New file - "+ directory +"to be added");
				setVisible(false);
				ConnectionInterface.getInstance().setVisible(true);
				Queuer.getInstance().addToQueueUpload(file);
				break;
			case DOWNLOADING:
				String dir = directory = chooser.getSelectedFile().getPath();;
				addFile(dir);		
				JOptionPane.showMessageDialog(null,"Download directory - "+ dir);
				setVisible(false);
				Queuer.getInstance().addToQueueDownload(dir,index);
				ConnectionInterface.getInstance().setVisible(true);
				break;
			default:
				break;
			}
		}	
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
