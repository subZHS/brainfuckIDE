//服务器IOService的Stub，内容相�?
package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
public interface IOService extends Remote{
	public boolean writeFile(String file, String userId, String fileName)throws RemoteException;
	
	public boolean updateFile(String username, String filename, String file)throws RemoteException;
	
	public String readFile(String userId, String fileName)throws RemoteException;
	
	public String[] readFileList(String userId)throws RemoteException;
	
	public String[] getVersionList(String username, String filename)throws RemoteException;
	
	public String getVersion(String username, String filename, String versionName)throws RemoteException;
	
	public boolean addVersion(String username, String filename, String version)throws RemoteException;
	
}
