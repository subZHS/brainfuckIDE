package serviceImpl;

import java.util.ArrayList;

public class PreUserData {

	private static PreUserData userData=new PreUserData();
	ArrayList<String> userIds = new ArrayList<String>();
	ArrayList<String> userPasswords = new ArrayList<String>();
	ArrayList<Userfiles> usersData = new ArrayList<Userfiles>();
	
	private PreUserData(){
		
	}
	
	public boolean cleanUserData(){
		userIds=new ArrayList<String>();
		userPasswords=new ArrayList<String>();
		usersData=new ArrayList<Userfiles>();
		return true;
	}
	public static PreUserData getInstance(){
		return userData;
	}

	public boolean addVersion(String username, String filename, String version) {
		Userfiles userFiles = getUserfiles(username);
		if (!userFiles.addVersion(filename, version)) {
			return false;
		}
		int index = getnumber(username);
		usersData.set(index, userFiles);
		return true;
	}

	public String[] getVersionList(String username, String filename) {
		Userfiles userFiles = getUserfiles(username);
		return userFiles.getVersionList(filename);
	}

	public String getVersion(String username, String filename, String versionName) {
		Userfiles userFiles = getUserfiles(username);
		return userFiles.getVersion(filename, versionName);
	}

	public boolean ismatch(String username, String password) {
		int index=getnumber(username);
		if (userPasswords.get(index).equals(password)) {
			return true;
		}else{
			return false;
		}
	}

	public boolean adduser(String username, String password) {
		String[] userList = getuserlist();
		for (int i = 0; i < userList.length; i++) {
			if (userList[i].equals(username)) {
				System.out.println(username);
				return false;
			}
		}
		userIds.add(username);
		userPasswords.add(password);
		Userfiles itsfiles = new Userfiles();
		usersData.add(itsfiles);
		return true;
	}

	public boolean adduserfile(String username, String filename, String file) {
		if(getnumber(username)==-1){
			return false;
		}
		Userfiles itsfiles = getUserfiles(username);
		Boolean isavailable = itsfiles.addFile(filename, file);
		if (isavailable) {
			int index = getnumber(username);
			usersData.set(index, itsfiles);
		}
		return isavailable;
	}
	
	public boolean updateFile(String username,String filename,String file){
		Userfiles itsflies=getUserfiles(username);
		return itsflies.updateFile(filename, file);
	}

	public String[] getuserlist() {
		String[] userList = new String[userIds.size()];
		for (int i = 0; i < userIds.size(); i++) {
			userList[i] = userIds.get(i);
		}
		return userList;
	}

	public String getfile(String username, String filename) {
		Userfiles userFiles = getUserfiles(username);
		return userFiles.getFile(filename);
	}

	public String[] getItsFileList(String username) {
		Userfiles itsfiles = getUserfiles(username);
		return itsfiles.getFileList();
	}

	public Userfiles getUserfiles(String username) {
		String[] userList = getuserlist();
		for (int i = 0; i < userList.length; i++) {
			if (userList[i].equals(username)) {
				return usersData.get(i);
			}
		}
		return null;
	}

	public int getnumber(String username) {
		String[] userList = getuserlist();
		for (int i = 0; i < userList.length; i++) {
			if (username.equals(userList[i])) {
				return i;
			}
		}
		return -1;
	}

}
