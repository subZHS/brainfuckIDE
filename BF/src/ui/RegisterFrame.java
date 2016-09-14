package ui;

import java.awt.*;
import java.awt.event.*;
import java.nio.channels.NonWritableChannelException;
import java.rmi.RemoteException;

import javax.swing.*;

import rmi.RemoteHelper;

public class RegisterFrame{
	JFrame registerFrame;
	JPasswordField passwordField,confirmField;
	JTextField nameField;
	JButton registerButton,cancelButton;
	
	public void go(){
		registerFrame=new JFrame("Register");
		registerFrame.setLocation(450,250);
		registerFrame.setSize(400, 300);
		registerFrame.getContentPane().setBackground(Color.white);
		int height=registerFrame.getHeight();
		int width=registerFrame.getWidth();
		registerFrame.setLayout(null);
		
		Toolkit kit=Toolkit.getDefaultToolkit();
		Image loginImage=kit.getImage("Image/registerImage.jpg");
		registerFrame.setIconImage(loginImage);
		
		Font namefont=new Font("Serif2", Font.BOLD, 13);
		Font textFont=new Font("Serif3", Font.ITALIC, 13);
		Color color=new Color(255, 20, 100);
		
		JPanel namePanel=new JPanel();
		namePanel.setLayout(null);
		JLabel nameLabel=new JLabel("Name");
		namePanel.setBounds(0, 0, width, height/5);
		nameLabel.setFont(namefont);
		nameLabel.setForeground(color);
		nameLabel.setBounds(width/8,height/30,width/5,height/7);
		nameField=new JTextField("please create a user's name");
		nameField.setFont(textFont);
		nameField.setBounds(width/10*3,height/30,width/2,height/7);
		
		namePanel.add(nameLabel);
		namePanel.add(nameField);
		namePanel.setBackground(Color.white);
		registerFrame.getContentPane().add(namePanel);
		
		JPanel passwordPanel=new JPanel();
		passwordPanel.setBackground(Color.white);;
		passwordPanel.setLayout(null);
		passwordPanel.setBounds(0, height/5, width, height/2);
		
		JLabel passwordLabel=new JLabel("Password");
		passwordLabel.setBounds(width/8,height/40,width/5,height/7);
		passwordLabel.setFont(namefont);
		passwordLabel.setForeground(color);
		
		JLabel confirmLabel=new JLabel("Password");
		confirmLabel.setBounds(width/8,height/14*3,width/5,height/7);
		confirmLabel.setFont(namefont);
		confirmLabel.setForeground(color);
		
		
		passwordField=new JPasswordField("please create your password");
		passwordField.setEchoChar((char)0);
		passwordField.setBounds(width*3/10,height/40,width/2,height/7);
		passwordField.setFont(textFont);
		
		confirmField=new JPasswordField("please confirm your password");
		confirmField.setEchoChar((char)0);
		confirmField.setBounds(width*3/10,height/14*3,width/2,height/7);
		confirmField.setFont(textFont);
		
		passwordPanel.add(passwordLabel);
		passwordPanel.add(passwordField);
		passwordPanel.add(confirmLabel);
		passwordPanel.add(confirmField);
		registerFrame.getContentPane().add(passwordPanel);
		
		JPanel buttonPanel=new JPanel();
		buttonPanel.setBackground(Color.white);
		buttonPanel.setBounds(0, height/10*7, width, height/10*3);
		Color buttonColor=new Color(225, 205, 215);
		
		registerButton=new JButton("Register");
		registerButton.setBounds(width/5,0,width/15*4,height/11);
		registerButton.setBackground(buttonColor);
		cancelButton=new JButton("Cancel");
		cancelButton.setBounds(width/5*3,0,width/5,height/11);
		cancelButton.setBackground(buttonColor);
		
		nameField.addFocusListener(new namefieldFocusListener());
		passwordField.addFocusListener(new passwordFieldFocusListener());
		confirmField.addFocusListener(new confirmFieldFocusListener());
		registerButton.addActionListener(new RegisterButtonActionListener());
		cancelButton.addActionListener(new CancelActionListener());
		registerFrame.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e){
				registerFrame.dispose();
				LoginFrame login =new LoginFrame();
				login.go();
			}
			});;
		
		buttonPanel.setLayout(null);
		buttonPanel.add(registerButton);
		buttonPanel.add(cancelButton);
		registerFrame.getContentPane().add(buttonPanel);
		
		registerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		registerFrame.setVisible(true);
		registerFrame.repaint();
	}
	
	class namefieldFocusListener implements FocusListener{

		@Override
		public void focusGained(FocusEvent e) {
			if(nameField.getText().equals("please create a user's name")||nameField.getText().equals("failed, try again")){
				nameField.setText("");
			}
		}

		@Override
		public void focusLost(FocusEvent e) {
			if(nameField.getText().equals("")){
				nameField.setText("please create a user's name");
			}
		}
		
	}
	
	class passwordFieldFocusListener implements FocusListener{

		@Override
		public void focusGained(FocusEvent e) {
			String password=String.valueOf(passwordField.getPassword()).trim();
			if(password.equals("")||password.equals("please create your password")){
				passwordField.setText("");
				passwordField.setFont(new Font("Serif2", Font.BOLD, 20));
				passwordField.setEchoChar('¡¤');
			}
		}

		@Override
		public void focusLost(FocusEvent e) {
			String password=String.valueOf(passwordField.getPassword()).trim();
			if(password.equals("")){
				passwordField.setEchoChar((char)0);
				passwordField.setFont(new Font("Serif3", Font.ITALIC, 13));
				passwordField.setText("please create your password");
			}
		}
		
	}
	
	class confirmFieldFocusListener implements FocusListener{

		@Override
		public void focusGained(FocusEvent e) {
			String password=String.valueOf(confirmField.getPassword()).trim();
			if(password.equals("")||password.equals("please confirm your password")){
				confirmField.setText("");
				confirmField.setFont(new Font("Serif2", Font.BOLD, 20));
				confirmField.setEchoChar('¡¤');
			}
			
		}

		@Override
		public void focusLost(FocusEvent e) {
			String password=String.valueOf(confirmField.getPassword()).trim();
			if(password.equals("")){
				confirmField.setEchoChar((char)0);
				confirmField.setFont(new Font("Serif3", Font.ITALIC, 13));
				confirmField.setText("please confirm your password");
			}
		}
		
	}
	class RegisterButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String username=nameField.getText();
			String password=String.valueOf(passwordField.getPassword()).trim();
			String confirmword=String.valueOf(confirmField.getPassword()).trim();
			boolean issuccess=false;
			try {
				issuccess=RemoteHelper.getInstance().getUserService().register(username, password, confirmword);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			if(issuccess){
				registerFrame.dispose();
				LoginFrame login=new LoginFrame();
				login.go();
			}else{
				nameField.setText("failed, try again");
				passwordField.setText("");
				confirmField.setText("");
			}
		}
		
	}
	
	class CancelActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			registerFrame.dispose();
			LoginFrame login=new LoginFrame();
			login.go();
		}
		
	}
	
}
