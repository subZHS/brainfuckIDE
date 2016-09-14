package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.rmi.RemoteException;

import javax.swing.*;

import rmi.RemoteHelper;

public class NewaFileFrame{

	JFrame newFileFrame;
	JButton ensureButton, cancelButton;
	JTextField filenameField;
	String username;
	boolean success=false;
	
	public NewaFileFrame(){
		
	}
	public NewaFileFrame(String username){
		this.username=username;
	}

	public void close(){
		newFileFrame.dispose();
	}
	public void fresh(){
		newFileFrame.repaint();
	}
	
	protected void go() {
		newFileFrame = new JFrame("new a file");
		newFileFrame.setBounds(200, 200, 280, 180);
		newFileFrame.getContentPane().setBackground(Color.white);
		newFileFrame.setLayout(null);
		int width = newFileFrame.getWidth();
		int height = newFileFrame.getHeight();

		JLabel filenameLabel = new JLabel("filename");
		Font namefont = new Font("Serif2", Font.BOLD, 13);
		filenameLabel.setFont(namefont);
		filenameLabel.setBounds(width / 11, height / 9, width / 5, height / 5);

		filenameField = new JTextField("filename");
		Font textFont = new Font("Serif3", Font.ITALIC, 13);
		filenameField.setFont(textFont);
		filenameField.setBounds(width / 11 * 4, height / 9, width / 2, height / 5);

		Color buttonColor = new Color(225, 205, 215);

		ensureButton = new JButton("Yes");
		ensureButton.setBackground(buttonColor);
		ensureButton.setBounds(width / 6, height / 2, width / 4, height / 6);
		cancelButton = new JButton("cancel");
		cancelButton.setBackground(buttonColor);
		cancelButton.setBounds(width / 2, height / 2, width / 64 * 19, height / 6);

		filenameField.addFocusListener(new filenameFieldFocusListener());
		ensureButton.addActionListener(new ensureButtonActionListener());
		cancelButton.addActionListener(new cancelButtonActionListener());

		newFileFrame.getContentPane().add(filenameLabel);
		newFileFrame.getContentPane().add(filenameField);
		newFileFrame.getContentPane().add(ensureButton);
		newFileFrame.getContentPane().add(cancelButton);
		newFileFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		newFileFrame.setVisible(true);
		newFileFrame.repaint();
	}

	class filenameFieldFocusListener implements FocusListener {
		@Override
		public void focusGained(FocusEvent e) {
			if (filenameField.getText().equals("filename") || filenameField.getText().equals("failed. try again"))
				filenameField.setText("");
		}

		@Override
		public void focusLost(FocusEvent e) {
			if (filenameField.getText().equals("")) {
				filenameField.setText("filename");
			}
		}
	}

	class ensureButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String filename = filenameField.getText();
			if (filename.equals("filename") || filename.equals("") || filename.equals("failed. try again")) {
				filenameField.setText("failed. try again");
			} else {
				String userId = username;
				boolean issuccess = false;
				try {
					issuccess = RemoteHelper.getInstance().getIOService().writeFile("", userId, filename);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				if (!issuccess) {
					filenameField.setText("failed. try again");
					success=false;
				} else {
					success=true;
					newFileFrame.dispose();
					MyFrame myFrame=new MyFrame();
					myFrame.go();
					myFrame.usernamelabel.setText(username);
					myFrame.filenamelabel.setText(filename);
					myFrame.codeArea.setEditable(true);
					myFrame.inputArea.setEditable(true);
					myFrame.mainFrame.repaint();
				}
			}
		}

	}

	class cancelButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			newFileFrame.dispose();
		}
	}
}
