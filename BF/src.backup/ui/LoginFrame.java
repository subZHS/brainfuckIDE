package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.rmi.RemoteException;

import javax.swing.*;

import rmi.RemoteHelper;

public class LoginFrame extends JFrame {

	JFrame loginFrame;
	JPasswordField passwordField;
	JTextField namefield;
	JButton loginButton, registerButton, cancelButton;
	private static LoginFrame loginframe = new LoginFrame();

	private LoginFrame() {
	}

	public static LoginFrame getInstance() {
		return loginframe;
	}

	public void go() {
		loginFrame = new JFrame("Log In");
		loginFrame.setLocation(400, 200);
		loginFrame.setSize(400, 300);
		loginFrame.getContentPane().setBackground(Color.white);
		int height = loginFrame.getHeight();
		int width = loginFrame.getWidth();
		loginFrame.setLayout(null);

		Toolkit kit = Toolkit.getDefaultToolkit();
		Image loginImage = kit.getImage("Image/logintitle.jpg");
		loginFrame.setIconImage(loginImage);

		Color color = new Color(255, 20, 100);

		JLabel titlelabel = new JLabel("          welcome to use BrainFuck IDE");
		titlelabel.setForeground(color);
		Font titleFont = new Font("Serif1", Font.ITALIC, 20);
		titlelabel.setFont(titleFont);
		titlelabel.setBounds(0, 0, width, height / 5);
		loginFrame.getContentPane().add(titlelabel);

		JPanel textPanel = new JPanel();
		textPanel.setBackground(Color.white);
		;
		textPanel.setLayout(null);
		textPanel.setBounds(0, height / 5, width, height / 2);

		JLabel nameLabel = new JLabel(" Name");
		Font namefont = new Font("Serif2", Font.BOLD, 13);
		nameLabel.setBounds(width / 8, 0, width / 5, height / 7);
		nameLabel.setFont(namefont);
		nameLabel.setForeground(color);

		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(width / 8, height / 14 * 3, width / 5, height / 7);
		passwordLabel.setFont(namefont);
		passwordLabel.setForeground(color);

		Font textFont = new Font("Serif3", Font.ITALIC, 13);

		namefield = new JTextField("please enter your name");
		namefield.setBounds(width * 3 / 10, 0, width / 2, height / 7);
		namefield.setFont(textFont);
		namefield.addFocusListener(new namefieldFocusListener());

		passwordField = new JPasswordField("please enter your password");
		passwordField.setEchoChar((char) 0);
		passwordField.setBounds(width * 3 / 10, height / 14 * 3, width / 2, height / 7);
		passwordField.setFont(textFont);
		passwordField.addFocusListener(new passwordFieldFocusListener());

		textPanel.add(nameLabel);
		textPanel.add(namefield);
		textPanel.add(passwordLabel);
		textPanel.add(passwordField);
		loginFrame.getContentPane().add(textPanel);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.white);
		buttonPanel.setBounds(0, height / 10 * 7, width, height / 10 * 3);
		Color buttonColor = new Color(225, 205, 215);
		loginButton = new JButton("Log In");
		loginButton.setBackground(buttonColor);
		loginButton.setBounds(width / 10, 0, width / 5, height / 11);
		registerButton = new JButton("Register");
		registerButton.setBounds(width / 20 * 7, 0, width / 15 * 4, height / 11);
		registerButton.setBackground(buttonColor);
		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(width / 20 * 13, 0, width / 5, height / 11);
		cancelButton.setBackground(buttonColor);

		loginButton.addActionListener(new loginButtonActionListener());
		registerButton.addActionListener(new registerButtonActionListener());
		cancelButton.addActionListener(new cacelButtonActionListener());

		buttonPanel.setLayout(null);
		buttonPanel.add(loginButton);
		buttonPanel.add(registerButton);
		buttonPanel.add(cancelButton);
		loginFrame.getContentPane().add(buttonPanel);

		loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		loginFrame.setVisible(true);
		loginFrame.repaint();
	}

	class loginButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String username = namefield.getText();
			String password = String.valueOf(passwordField.getPassword()).trim();
			boolean issuccess=false;
			try {
				issuccess=RemoteHelper.getInstance().getUserService().login(username, password);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			if(issuccess){
				loginFrame.dispose();
				MyFrame.getInstance().go();
				MyFrame.getInstance().usernamelabel.setText(username);
				MyFrame.getInstance().repaint();
			}else{
				namefield.setText("failed, try again");
				passwordField.setText("");
			}
		}

	}

	class registerButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			loginFrame.dispose();
			RegisterFrame.getInstance().go();
		}

	}

	class cacelButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			loginFrame.dispose();
		}

	}

	class namefieldFocusListener implements FocusListener {

		@Override
		public void focusGained(FocusEvent e) {
			if (namefield.getText().equals("please enter your name") ||namefield.getText().equals("failed, try again")) {
				namefield.setText("");
			}
		}

		@Override
		public void focusLost(FocusEvent e) {
			if (namefield.getText().equals("")) {
				namefield.setText("please enter your name");
			}
		}

	}

	class passwordFieldFocusListener implements FocusListener {

		@Override
		public void focusGained(FocusEvent e) {
			char[] c=passwordField.getPassword();
			String pwd=String.valueOf(c).trim();
			if (pwd.equals("please enter your password") ||pwd.equals("")) {
				passwordField.setText("");
				passwordField.setFont(new Font("Serif2", Font.BOLD, 20));
				passwordField.setEchoChar('¡¤');
			}
		}

		@Override
		public void focusLost(FocusEvent e) {
			if(String.valueOf(passwordField.getPassword()).trim().equals("")){
				passwordField.setEchoChar((char)0);
				passwordField.setFont(new Font("Serif3", Font.ITALIC, 13));
				passwordField.setText("please enter your password");
			}
		}
	}
}
