//服务器UserService的Stub，内容相�?
package service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserService extends Remote{
	public boolean login(String username, String password) throws RemoteException;

	public boolean logout(String username) throws RemoteException;
	
	public boolean register(String username, String password, String confirmword) throws RemoteException;
	
}
