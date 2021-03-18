package notepadApplication;

import java.io.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.awt.*;

public class Notepad extends Frame {
	
	Clipboard cBoard = getToolkit().getSystemClipboard();
	TextArea tArea;
	String fName;
	
	Notepad(){
		
		gaListener gListen = new gaListener();
		addWindowListener(gListen);
		tArea = new TextArea();
		add(tArea);
		MenuBar mBar = new MenuBar();
		Menu fileMenu = new Menu("File");
		MenuItem nOption = new MenuItem("New");
		MenuItem oOption = new MenuItem("Open");
		MenuItem sOption = new MenuItem("Save");
		MenuItem cOption = new MenuItem("Close");
		nOption.addActionListener(new Ne_option());
		fileMenu.add(nOption);
		oOption.addActionListener(new Ope_option());
		fileMenu.add(oOption);
		sOption.addActionListener(new Sav_option());
		fileMenu.add(sOption);
		cOption.addActionListener(new Clos_option());
		fileMenu.add(cOption);
		mBar.add(fileMenu);
		Menu editMenu = new Menu("Edit");
		MenuItem cutOption = new MenuItem("Cut");
		MenuItem copyOption = new MenuItem("Copy");
		MenuItem pasteOption = new MenuItem("Paste");
		cutOption.addActionListener(new Cu_option());
		editMenu.add(cutOption);
		copyOption.addActionListener(new Cop_option());
		editMenu.add(copyOption);
		pasteOption.addActionListener(new Past_option());
		editMenu.add(pasteOption);
		mBar.add(editMenu);
		setMenuBar(mBar);
		setTitle("Notepad in Java");
	}
	
class gaListener extends WindowAdapter{
	
	public void windowClosing(WindowEvent closeNotepad) {
		System.exit(0);
	}
}

class Ne_option implements ActionListener{
	
	public void actionPerformed(ActionEvent ne) {	
		tArea.setText(" ");
	}
}

class Ope_option implements ActionListener{
	
	public void actionPerformed(ActionEvent ope) {
		FileDialog fDialog = new FileDialog(Notepad.this,
		"Select a text fi le",FileDialog.LOAD);
		fDialog.show();
		if (fDialog.getFile()!=null) {
			fName = fDialog.getDirectory() + fDialog.getFile();
			setTitle(fName);
			ReadFile();
		}
		tArea.requestFocus();	
	}
}

class Clos_option implements ActionListener{
	
	public void actionPerformed(ActionEvent close_o) {
		System.exit(0);
	}
}

class Sav_option implements ActionListener{
	
	public void actionPerformed(ActionEvent sav_o) {
		FileDialog fDialog = new FileDialog(Notepad.this,"Save the text file with .txt extension",FileDialog.SAVE);
		fDialog.show();
		if (fDialog.getFile()!=null) {
			fName = fDialog.getDirectory() + fDialog.getFile();
			setTitle(fName);
			try {
				DataOutputStream dOutStream = new DataOutputStream(new FileOutputStream(fName));
				String oLine = tArea.getText();
				BufferedReader bReader = new BufferedReader(new StringReader(oLine));
				while((oLine = bReader.readLine())!=null) {
					dOutStream.writeBytes(oLine + "\r\n");
					dOutStream.close();
				}
			}
			catch(Exception ex) {
				System.out.print("Required fi le not found");
			}
			tArea.requestFocus();
		}
	}
}

void ReadFile() {
	BufferedReader br;
	StringBuffer sBuffer = new StringBuffer();
	try {
		br = new BufferedReader(new FileReader(fName));
		String oLine;
		while((oLine=br.readLine())!=null)
			sBuffer.append(oLine + "\n");
		tArea.setText(sBuffer.toString());
		br.close();
	}
	catch(FileNotFoundException fe) {
		System.out.print("Required file not found");
	}
		catch(IOException ioe) {}
}

class Cu_option implements ActionListener{
	
	public void actionPerformed(ActionEvent cut_o) {
		String sText = tArea.getSelectedText();
		StringSelection sSelection = new StringSelection(sText);
		cBoard.setContents(sSelection,sSelection);
		tArea.replaceRange(" ",tArea.getSelectionStart(),tArea.getSelectionEnd());
	}
}

class Cop_option implements ActionListener{
	public void actionPerformed(ActionEvent copy_o) {
		String sText = tArea.getSelectedText();
		StringSelection cString = new StringSelection(sText);
		cBoard.setContents(cString,cString);
	}
}

class Past_option implements ActionListener{
	public void actionPerformed(ActionEvent paste_o) {
		Transferable ctransfer = cBoard.getContents(Notepad.this);
		try {
			String sText = (String)ctransfer.getTransferData(DataFlavor.stringFlavor);
			tArea.replaceRange(sText,tArea.getSelectionStart(),tArea.getSelectionEnd());
		}
		catch(Exception exe){
			System.out.println("Not a string fl avor");
		}
	}
}

	public static void main(String[] args) {
		
		Frame nFrame = new Notepad();
		nFrame.setSize(600,600);
		nFrame.setVisible(true);

	}

}
