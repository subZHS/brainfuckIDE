package ui;

import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import rmi.RemoteHelper;

public class MyFrame {

	JFrame mainFrame;
	JMenu versionmenu, usermenu, filemenu, runmenu, functionmenu, openItem;
	JMenuItem newItem, saveItem, exitItem, executeItem, registerItem, logoutItem, redoItem, undoItem;
	JLabel usernamelabel, filenamelabel;
	JTextArea codeArea;
	JTextArea inputArea;
	JTextArea outputArea;
	codeKeyListener codelistener=new codeKeyListener();

	public void go() {
		mainFrame = new JFrame("BF client");

		JMenuBar menuBar = new JMenuBar();
		filemenu = new JMenu("File");
		menuBar.add(filemenu);
		runmenu = new JMenu("Run");
		menuBar.add(runmenu);
		versionmenu = new JMenu("Version");
		menuBar.add(versionmenu);
		functionmenu = new JMenu("Function");
		menuBar.add(functionmenu);

		usermenu = new JMenu();
		ImageIcon userImage = new ImageIcon("image/userImage.png");
		userImage.setImage(userImage.getImage().getScaledInstance(userImage.getIconWidth() / 30,
				userImage.getIconHeight() / 30, Image.SCALE_SMOOTH));
		usermenu.setIcon(userImage);

		registerItem = new JMenuItem("Register");
		logoutItem = new JMenuItem("Log out");
		undoItem = new JMenuItem("undo");
		redoItem = new JMenuItem("redo");
		usernamelabel = new JLabel("  unsigned");
		filenamelabel = new JLabel("filename");

		functionmenu.add(undoItem);
		functionmenu.add(redoItem);
		usermenu.add(logoutItem);
		usermenu.add(registerItem);

		menuBar.add(functionmenu);
		menuBar.add(usermenu);
		menuBar.add(usernamelabel);
		JLabel breaaklabel = new JLabel("  :  ");
		menuBar.add(breaaklabel);
		menuBar.add(filenamelabel);

		newItem = new JMenuItem("New");
		openItem = new JMenu("Open");
		saveItem = new JMenuItem("Save");
		exitItem = new JMenuItem("Exit");
		executeItem = new JMenuItem("Execute");
		runmenu.add(executeItem);
		mainFrame.setJMenuBar(menuBar);

		filemenu.addMenuListener(new FilemenuListener());
		newItem.addActionListener(new NewItemActionListener());
		openItem.addMenuListener(new OpenItemListener());
		saveItem.addActionListener(new SaveItemActionListener());
		exitItem.addActionListener(new ExitItemActionListener());
		executeItem.addActionListener(new ExecuteItemActionListener());
		versionmenu.addMenuListener(new VersionmenuListener());
		undoItem.addActionListener(codelistener.new undoActionListener());
		redoItem.addActionListener(codelistener.new redoActionListener());
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
		codeArea.addKeyListener(codelistener);

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

	class FilemenuListener implements MenuListener {

		@Override
		public void menuCanceled(MenuEvent e) {
		}

		@Override
		public void menuDeselected(MenuEvent e) {
			filemenu.removeAll();
		}

		@Override
		public void menuSelected(MenuEvent e) {
			if (!usernamelabel.getText().equals("  unsigned")) {
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
			NewaFileFrame newfile = new NewaFileFrame(usernamelabel.getText());
			newfile.go();
			newfile.newFileFrame.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {
					if (newfile.success == true) {
						mainFrame.dispose();
					}
				}
			});
		}

	}

	class OpenItemListener implements MenuListener {
		String[] fileList = null;

		@Override
		public void menuSelected(MenuEvent e) {
			if (!usernamelabel.getText().equals("  unsigned")) {
				try {
					String str = RemoteHelper.getInstance().getIOService().readFileList(usernamelabel.getText());
					fileList = str.split(" ");
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				for (int i = 0; i < fileList.length; i++) {
					JMenuItem filenameItem = new JMenuItem(fileList[i]);
					openItem.add(filenameItem);
					filenameItem.addActionListener(new filelistListener());
				}
				codelistener = new codeKeyListener();
				mainFrame.repaint();
			}
		}

		@Override
		public void menuCanceled(MenuEvent e) {
		}

		@Override
		public void menuDeselected(MenuEvent e) {
			openItem.removeAll();
		}

		class filelistListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				// if(e.getActionCommand()==){
				for (int i = 0; i < fileList.length; i++) {
					if (e.getActionCommand().equals(fileList[i])) {
						try {
							String file = RemoteHelper.getInstance().getIOService().readFile(usernamelabel.getText(),
									fileList[i]);
							codeArea.setEditable(true);
							codeArea.setText(file);
							filenamelabel.setText(fileList[i]);
							mainFrame.repaint();
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
					}
				}
				// }
			}
		}

	}

	class SaveItemActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String username = usernamelabel.getText();
			String filename = filenamelabel.getText();
			boolean versionadded = false;
			boolean fileupdated = false;
			if (!username.equals("  unsigned") && !filename.equals("filename")) {
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
			mainFrame.repaint();
		}

	}

	class ExitItemActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}

	}

	class ExecuteItemActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
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

	class VersionmenuListener implements MenuListener {
		String[] versions = null;

		@Override
		public void menuSelected(MenuEvent e) {
			if (!filenamelabel.getText().equals("filename")) {
				try {
					versions = RemoteHelper.getInstance().getIOService().getVersionList(usernamelabel.getText(),
							filenamelabel.getText());
				} catch (RemoteException ex) {
					ex.printStackTrace();
				}
				for (String version : versions) {
					JMenuItem versionItem = new JMenuItem(version);
					versionmenu.add(versionItem);
					versionItem.addActionListener(new versionListListener());
				}
				mainFrame.repaint();
			}
		}

		@Override
		public void menuCanceled(MenuEvent e) {
		}

		@Override
		public void menuDeselected(MenuEvent e) {
			versionmenu.removeAll();
		}

		class versionListListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				String cmd = e.getActionCommand();
				for (int i = 0; i < versions.length; i++) {
					if (cmd.equals(versions[i])) {
						try {
							String versionContent = RemoteHelper.getInstance().getIOService()
									.getVersion(usernamelabel.getText(), filenamelabel.getText(), cmd);
							codeArea.setText(versionContent);
							mainFrame.repaint();
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		}
	}

	class codeKeyListener implements KeyListener {

		ArrayList<String> strlist = new ArrayList<String>(15);
		ArrayList<KeyEvent> keyList = new ArrayList<KeyEvent>(50);

		@Override
		public void keyPressed(KeyEvent arg0) {
			if (strlist.size() == 14) {
				strlist.remove(0);
			}
			if (keyList.size() == 49) {
				strlist.remove(0);
			}
			keyList.add(arg0);
			if (keyList.indexOf(arg0) == 0) {
				strlist.add(codeArea.getText());
			}
			// 删除文字的时候
			if (arg0.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
				if (keyList.size() != 1 && keyList.get(keyList.size() - 2).getKeyChar() != KeyEvent.VK_BACK_SPACE) {
					strlist.add(codeArea.getText());
				}
			}
			if (arg0.getKeyChar() != KeyEvent.VK_BACK_SPACE) {
				if (keyList.size() != 1 && keyList.get(keyList.size() - 2).getKeyChar() == KeyEvent.VK_BACK_SPACE) {
					strlist.add(codeArea.getText());
				}
			}
			// 粘贴的时候
			if (arg0.getKeyCode() == KeyEvent.VK_V && arg0.isControlDown()) {
				strlist.add(codeArea.getText());
			}
			// 剪切的时候
			if (arg0.getKeyCode() == KeyEvent.VK_X && arg0.isControlDown()) {
				strlist.add(codeArea.getText());
			}

		}

		@Override
		public void keyReleased(KeyEvent arg0) {
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
		}

		public class undoActionListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int index = strlist.indexOf(codeArea.getText());
				if (index == -1) {
					strlist.add(codeArea.getText());
					codeArea.setText(strlist.get(strlist.size() - 2));
					return;
				}
				for (int i = strlist.size() - 1; i >= 0; i--) {
					String str = strlist.get(i);
					if (str.equals(codeArea.getText())) {
						if (i > 0) {
							codeArea.setText(strlist.get(i - 1));
						}
						break;
					}
				}
			}

		}

		public class redoActionListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for (int i = strlist.size() - 1; i >= 0; i--) {
					String str = strlist.get(i);
					if (str.equals(codeArea.getText())) {
						if (i != strlist.size() - 1) {
							codeArea.setText(strlist.get(i + 1));
						}
						break;
					}
				}
			}
		}
	}

	class LogoutActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			mainFrame.dispose();
			LoginFrame login = new LoginFrame();
			login.go();
		}

	}

	class RegisterActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			mainFrame.dispose();
			RegisterFrame register = new RegisterFrame();
			register.go();
		}

	}
}
