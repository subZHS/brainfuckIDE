package ui;

import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import rmi.RemoteHelper;

public class MyFrame extends JFrame {

	private static MyFrame myFrame = new MyFrame();
	boolean islogined = false;
	JFrame mainFrame;
	JMenu versionmenu, usermenu, filemenu, runmenu;
	JMenuItem newItem, openItem, saveItem, exitItem, executeItem, loginItem, registerItem, logoutItem;
	JLabel usernamelabel, filenamelabel;
	JTextArea codeArea;
	JTextArea inputArea;
	JTextArea outputArea;

	private MyFrame() {
	}

	public static MyFrame getInstance() {
		return myFrame;
	}

	public void go() {
		mainFrame = new JFrame("BF client");

		JMenuBar menuBar = new JMenuBar();
		filemenu = new JMenu("File");
		menuBar.add(filemenu);
		runmenu = new JMenu("Run");
		menuBar.add(runmenu);
		versionmenu = new JMenu("Version");
		menuBar.add(versionmenu);

		usermenu = new JMenu();
		ImageIcon userImage = new ImageIcon("image/userImage.png");
		userImage.setImage(userImage.getImage().getScaledInstance(userImage.getIconWidth() / 30,
				userImage.getIconHeight() / 30, Image.SCALE_SMOOTH));
		usermenu.setIcon(userImage);

		loginItem = new JMenuItem("Log in");
		registerItem = new JMenuItem("Register");
		logoutItem = new JMenuItem("Log out");
		usernamelabel = new JLabel("  unsigned");
		filenamelabel = new JLabel("filename");

		if (!usernamelabel.getText().equals("  unsigned")) {
			islogined = true;
		}
		if (islogined) {
			usermenu.add(logoutItem);
		} else {
			usermenu.add(loginItem);
		}
		usermenu.add(registerItem);

		menuBar.add(usermenu);
		menuBar.add(usernamelabel);
		JLabel breaaklabel = new JLabel("  :  ");
		menuBar.add(breaaklabel);
		menuBar.add(filenamelabel);

		newItem = new JMenuItem("New");
		openItem = new JMenuItem("Open");
		saveItem = new JMenuItem("Save");
		exitItem = new JMenuItem("Exit");
		executeItem = new JMenuItem("Execute");
		runmenu.add(executeItem);
		mainFrame.setJMenuBar(menuBar);

		filemenu.addMenuListener(new FilemenuListener());
		newItem.addActionListener(new NewItemActionListener());
		openItem.addItemListener(new OpenItemListener());
		saveItem.addActionListener(new SaveItemActionListener());
		exitItem.addActionListener(new ExitItemActionListener());
		executeItem.addActionListener(new ExecuteItemActionListener());
		versionmenu.addMenuListener(new VersionmenuMenuListener());
		loginItem.addActionListener(new LoginActionListener());
		logoutItem.addActionListener(new LogoutActionListener());
		registerItem.addActionListener(new RegisterActionListener());

		Font font = new Font("Serif", Font.BOLD, 16);
		Color color = new Color(220, 205, 215);
		codeArea = new JTextArea("Code Sction" + "\r\n" + "Your code goes here", 13, 50);
		codeArea.setFont(font);
		codeArea.setBackground(Color.white);
		codeArea.setBorder(BorderFactory.createLineBorder(color, 3));
		codeArea.setBorder(BorderFactory.createTitledBorder(codeArea.getBorder(), "Code Section : "));
		codeArea.setLineWrap(true);
		codeArea.setEditable(false);
		JScrollPane codescroller = new JScrollPane(codeArea);

		JPanel ioPanel = new JPanel();
		ioPanel.setLayout(new BorderLayout());
		inputArea = new JTextArea("Input Section :", 7, 31);
		inputArea.setFont(font);
		inputArea.setBackground(Color.white);
		inputArea.setBorder(BorderFactory.createLineBorder(color, 3));
		inputArea.setBorder(BorderFactory.createTitledBorder(codeArea.getBorder(), "Input Section :"));
		inputArea.setLineWrap(true);
		inputArea.setEditable(false);
		JScrollPane inputscroller = new JScrollPane(inputArea);
		outputArea = new JTextArea("Output Section", 7, 31);
		outputArea.setFont(font);
		outputArea.setBackground(Color.white);
		outputArea.setBorder(BorderFactory.createLineBorder(color, 3));
		outputArea.setBorder(BorderFactory.createTitledBorder(codeArea.getBorder(), "Output Section"));
		outputArea.setLineWrap(true);
		JScrollPane outputscroller = new JScrollPane(outputArea);
		ioPanel.add(inputscroller, BorderLayout.WEST);
		ioPanel.add(outputscroller, BorderLayout.EAST);
		mainFrame.add(ioPanel, BorderLayout.SOUTH);
		mainFrame.add(codescroller, BorderLayout.CENTER);

		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mainFrame.setSize(800, 600);
		mainFrame.setLocation(200, 100);
		mainFrame.setVisible(true);

	}

	class FilemenuListener implements MenuListener{

		@Override
		public void menuCanceled(MenuEvent e) {
		}
		@Override
		public void menuDeselected(MenuEvent e) {
			filemenu.removeAll();
		}

		@Override
		public void menuSelected(MenuEvent e) {
			if(!usernamelabel.getText().equals("  unsigned")){
				filemenu.add(newItem);
				filemenu.add(openItem);
				filemenu.add(saveItem);
			}
			filemenu.add(exitItem);
		}
		
	}
	class NewItemActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			NewaFileFrame.getInstance().go();
		}

	}

	class OpenItemListener implements ItemListener,ActionListener {

		String userId = usernamelabel.getText();
		String[] fileList = null;
		@Override
		public void actionPerformed(ActionEvent e) {
			for(int i=0;i<fileList.length ;i++){
				if(e.getActionCommand().equals(fileList[i])){
					try {
						String file=RemoteHelper.getInstance().getIOService().readFile(userId, fileList[i]);
						codeArea.setText(file);
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				}
			}
		}

		@Override
		public void itemStateChanged(ItemEvent arg0) {
			if(arg0.getStateChange()==ItemEvent.SELECTED){
				if (!userId.equals("  unsigned")) {
					try {
						fileList = RemoteHelper.getInstance().getIOService().readFileList(userId);
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
					for(int i=0;i<fileList.length;i++){
						JMenuItem filenameItem=new JMenuItem(fileList[i]);
						openItem.add(filenameItem);
						filenameItem.addActionListener(this);
					}
				}
			}else{
				openItem.removeAll();
			}
		}

	}

	class SaveItemActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String username = usernamelabel.getText();
			String filename = filenamelabel.getText();
			boolean versionadded = false;
			boolean fileupdated = false;
			if (username.equals("  unsigned") && filename.equals("filename")) {
				String version = codeArea.getText();
				String updatedfile = codeArea.getText();
				try {
					versionadded = RemoteHelper.getInstance().getIOService().addVersion(username, filename, version);
					fileupdated = RemoteHelper.getInstance().getIOService().updateFile(username, filename, updatedfile);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
			JFrame savedFrame = new JFrame();
			JLabel label;
			if (versionadded && fileupdated) {
				label = new JLabel("  the file has saved");
			} else {
				label = new JLabel(" the file failed to save");
			}
			label.setBackground(Color.white);
			label.setFont(new Font("Serif", Font.BOLD, 16));
			label.setForeground(Color.BLACK);
			savedFrame.getContentPane().add(label);
			savedFrame.setBounds(400, 300, 200, 150);
			savedFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			savedFrame.setVisible(true);
			MyFrame.getInstance().repaint();
		}

	}

	class ExitItemActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.exit(0);
		}

	}

	class ExecuteItemActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String code = codeArea.getText();
			String param = inputArea.getText();
			try {
				String output = RemoteHelper.getInstance().getExecuteService().execute(code, param);
				outputArea.setText(output);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}

	}

	class VersionmenuMenuListener implements MenuListener, ActionListener {
		String[] versions = null;
		String username = usernamelabel.getText();
		String filename = filenamelabel.getText();

		@Override
		public void menuCanceled(MenuEvent arg0) {
		}

		@Override
		public void menuDeselected(MenuEvent arg0) {
			versionmenu.removeAll();
		}

		@Override
		public void menuSelected(MenuEvent arg0) {
			if (islogined || !filename.equals("filename")) {
				try {
					versions = RemoteHelper.getInstance().getIOService().getVersionList(username, filename);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				for (String version : versions) {
					JMenuItem versionItem = new JMenuItem(version);
					versionmenu.add(versionItem);
					versionItem.addActionListener(this);
				}

				MyFrame.getInstance().repaint();
			}
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			for (int i = 0; i < versions.length; i++) {
				if (cmd.equals(versions[i])) {
					try {
						String versionContent = RemoteHelper.getInstance().getIOService().getVersion(username, filename,
								cmd);
						codeArea.setText(versionContent);
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				}
			}
		}

	}

	class LoginActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LoginFrame.getInstance().go();
		}

	}

	class LogoutActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			usernamelabel.setText("  unsigned");
			MyFrame.getInstance().repaint();
		}

	}

	class RegisterActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			RegisterFrame.getInstance().go();
		}

	}
}
