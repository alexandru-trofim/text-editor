package org.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SwingWorker;

import org.gui.components.EditorConfig;
import org.gui.components.TextAreaPanel;

public class OpenFileButtonListener implements ActionListener {
	
	private TextAreaPanel panel;
	private JFrame frame;
	

	public OpenFileButtonListener(TextAreaPanel panel, JFrame frame) {
		this.panel = panel;
		this.frame = frame;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
//		FileDialog fd = new FileDialog(frame, "Open File", FileDialog.LOAD);
//		
//		System.out.println(fd);
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Choose File To Open");
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);

		if (chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
		  System.out.println("No Selection ");
		  return;
		} 

		System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
		System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
		
		File fileToOpen = chooser.getSelectedFile();
		
		new FileLoaderWorker(fileToOpen, panel).execute();
		
		
	}
	
	class FileLoaderWorker extends SwingWorker<List<char[]>, Void> {
		
		File file;
		TextAreaPanel panel;
		
		public FileLoaderWorker(File file, TextAreaPanel panel) {
			super();
			this.file = file;
			this.panel = panel;
		}

		@Override
		protected List<char[]> doInBackground() throws Exception {
			List<char[]> lines = new ArrayList<>();
			try (BufferedReader br = new BufferedReader(new FileReader(file))) {
				String line;
				while((line = br.readLine()) != null) {
					if (line.length() == 0) {
						lines.add(new char[] {'\0'});
					} else {
					    char[] lineChars = new char[line.length() + 1];

					    System.arraycopy(line.toCharArray(), 0, lineChars, 0, line.length());
					    lineChars[line.length()] = '\0';
					    lines.add(lineChars);					}
				}
				
			}				
			
			return lines;
		}
		
		@Override
		protected void done() {
			try {
				List<char[]> result = get();
				panel.getTextEngine().setText(result);
				
				/* Set new preferred size 
				 * Get the last line position */
				int lastLineYPos = panel.getTextEngine().getYLinePos(panel.getTextEngine().getText().size() - 1);
				/* TODO: Implement and for width */
				panel.setContentHeight(lastLineYPos + EditorConfig.PADDING_BOTTOM);
				
				System.out.println("Opened file successfulley!!");
			} catch (Exception e) {
				System.out.println("Error while opening file!!!");
				e.printStackTrace();
			}
		}
		
	}
		

}
