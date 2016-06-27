package serviceImpl;

import java.rmi.RemoteException;
import service.IOService;

public class IOServiceImpl implements IOService{
	
	@Override
	public boolean writeFile(String file, String userId, String fileName) {
//		File f = new File(userId + "_" + fileName);
//		try {
//			FileWriter fw = new FileWriter(f, false);
//			fw.write(file);
//			fw.flush();
//			fw.close();
//			return true;
//		} catch (IOException e) {
//			e.printStackTrace();
//			return false;
//		}
		return UserData.adduserfile(userId, fileName, file);
	}
	
	@Override
	public boolean updateFile(String username, String filename, String file){
		return UserData.updateFile(username, filename, file);
	}
	@Override
	public String readFile(String userId, String fileName) {
		return UserData.getfile(userId, fileName);
	}

	@Override
	public String readFileList(String userId) {
		return UserData.getItsFileList(userId);
	}

	@Override
	public String[] getVersionList(String username, String filename) throws RemoteException {
		return UserData.getVersionList(username, filename);
	}

	@Override
	public String getVersion(String username, String filename, String versionName) throws RemoteException {
		return UserData.getVersion(username, filename, versionName);
	}

	@Override
	public boolean addVersion(String username, String filename, String version) throws RemoteException {
		return UserData.addVersion(username, filename, version);
	}
	
	
}
