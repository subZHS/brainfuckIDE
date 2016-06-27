package serviceImpl;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UserData {

	static ArrayList<String> userIds = new ArrayList<String>();
	static ArrayList<String> userPasswords = new ArrayList<String>();

	static {
		init();
	}

	public static void init() {
		File userName = new File("data/userName.txt");
		File userPassword = new File("data/userPassword.txt");
		try {
			BufferedReader br = new BufferedReader(new FileReader(userName));
			String line = null;
			while ((line = br.readLine()) != null) {
				userIds.add(line);
			}
			br.close();
			BufferedReader br2 = new BufferedReader(new FileReader(userPassword));
			String line2 = null;
			while ((line2 = br2.readLine()) != null) {
				userPasswords.add(line2);
			}
			br2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean cleanUserData() {
		File userfiles=new File("data/userfiles");
		String[] filelist=userfiles.list();
		for(String filename:filelist){
			File file=new File(userfiles,filename);
			file.delete();
		}
		File fileVersions=new File("data/fileVersions");
		String[] filelist2=fileVersions.list();
		for(String filename:filelist2){
			File file=new File(fileVersions,filename);
			file.delete();
		}
		try {
			File userName=new File("data/userName.txt");
			FileWriter fw=new FileWriter(userName);
			fw.write("");
			fw.close();
			File userPassword=new File("data/userPassword.txt");
			FileWriter fw2=new FileWriter(userPassword);
			fw2.write("");
			fw2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		init();
		return true;
	}

	public static boolean adduser(String username, String password) {
		String[] userList = getuserlist();
		for (int i = 0; i < userList.length; i++) {
			if (userList[i].equals(username)) {
				return false;
			}
		}
		try {
			FileWriter fw=new FileWriter("data/userName.txt",true);
			fw.write(username + "\n");
			fw.close();
			FileWriter fw2=new FileWriter("data/userPassword.txt",true);
			fw2.write(password + "\n");
			fw2.close();
			File dirfile=new File("data/userfiles/"+username);
			dirfile.mkdir();
			File dirVersion=new File("data/fileVersions/"+username);
			dirVersion.mkdir();
		} catch (IOException e) {
			e.printStackTrace();
		}
		init();
		return true;
	}

	public static boolean ismatch(String username, String password) {
		init();
		int index = getnumber(username);
		if (userPasswords.get(index).equals(password)) {
			return true;
		} else {
			return false;
		}
	}

	public static String[] getuserlist() {
		init();
		String[] userList = new String[userIds.size()];
		for (int i = 0; i < userIds.size(); i++) {
			userList[i] = userIds.get(i);
		}
		return userList;
	}

	public static boolean adduserfile(String username, String filename, String file) {
		if (getnumber(username) == -1) {
			return false;
		}
		try {
			File newfile=new File("data/userfiles/"+username+"/"+filename+".txt");
			newfile.createNewFile();
			FileWriter fw = new FileWriter(newfile);
			fw.write(file);
			fw.close();
			//在历史版本的文件夹中创建该文件的历史版本目录
			File userVersion=new File("data/fileVersions/"+username+"/"+filename);
			userVersion.mkdir();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean updateFile(String username, String filename, String file) {
		try {
			File thefile=new File("data/userfiles/"+username+"/"+filename+".txt");
			FileWriter fw=new FileWriter(thefile);
			fw.write(file);
			fw.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
	}

	public static String getfile(String username, String filename) {
		String fileContent="";
		try {
			File thefile=new File("data/userfiles/"+username+"/"+filename+".txt");
			BufferedReader br=new BufferedReader(new FileReader(thefile));
			String line=null;
			while((line=br.readLine())!=null){
				fileContent+=line+"\n";
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileContent;
	}

	public static String getItsFileList(String username) {
		String fileList="";
		String[] list;
		File dir=new File("data/userfiles/"+username);
		list=dir.list();
		for(int i=0;i<list.length;i++){
			fileList+=list[i].replaceAll(".txt", "");
			if(i!=list.length-1){
				fileList+=" ";
			}
		}
		return fileList;
	}

	public static boolean addVersion(String username, String filename, String version) {
		String latest="";
		try{
			File snapshot=new File("data/userfiles/"+username+"/"+filename+".txt");
			BufferedReader br=new BufferedReader(new FileReader(snapshot));
			String line=null;
			while((line=br.readLine())!=null){
				latest+=line+"\n";
			}
			br.close();
		}catch(IOException ex){
			ex.printStackTrace();
		}
		//如果文件没有变动，则不创建新的历史版本
		if(latest.replaceAll("\n", "").equals(version.replaceAll("\n", ""))){
			return false;
		}
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String time=format.format(date);
		try {
			File fileDir=new File("data/fileVersions/"+username+"/"+filename);
			File newVersion=new File(fileDir,time+".txt");
			newVersion.createNewFile();
			FileWriter fw = new FileWriter(newVersion);
			fw.write(version);
			fw.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static String[] getVersionList(String username, String filename) {
		String[] versionList=null;
		File fileDir=new File("data/fileVersions/"+username+"/"+filename);
		versionList=fileDir.list();
		return versionList;
	}

	public static String getVersion(String username, String filename, String versionName) {
		File versionname=new File("data/fileVersions/"+username+"/"+filename+"/"+versionName);
		String version="";
		try {
			BufferedReader br = new BufferedReader(new FileReader(versionname));
			String line=null;
			while((line=br.readLine())!=null){
				version+=line+"\n";
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return version;
	}

	public static int getnumber(String username) {
		String[] userList = getuserlist();
		for (int i = 0; i < userList.length; i++) {
			if (username.equals(userList[i])) {
				return i;
			}
		}
		return -1;
	}

}
