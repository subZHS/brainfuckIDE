package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import service.ExecuteService;
import service.IOService;
import service.UserService;
import serviceImpl.ExecuteServiceImpl;
import serviceImpl.IOServiceImpl;
import serviceImpl.UserServiceImpl;

public class DataRemoteObject extends UnicastRemoteObject implements IOService, UserService,ExecuteService{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4029039744279087114L;
	private IOService iOService;
	private UserService userService;
	private ExecuteService executeService;
	protected DataRemoteObject() throws RemoteException {
		iOService = new IOServiceImpl();
		userService = new UserServiceImpl();
		executeService=new ExecuteServiceImpl();
	}

	@Override
	public boolean writeFile(String file, String userId, String fileName) throws RemoteException{
		// TODO Auto-generated method stub
		return iOService.writeFile(file, userId, fileName);
	}

	@Override
	public String readFile(String userId, String fileName) throws RemoteException{
		// TODO Auto-generated method stub
		return iOService.readFile(userId, fileName);
	}

	@Override
	public String readFileList(String userId) throws RemoteException{
		// TODO Auto-generated method stub
		return iOService.readFileList(userId);
	}

	@Override
	public boolean login(String username, String password) throws RemoteException {
		// TODO Auto-generated method stub
		return userService.login(username, password);
	}

	@Override
	public boolean logout(String username) throws RemoteException {
		// TODO Auto-generated method stub
		return userService.logout(username);
	}

	@Override
	public boolean register(String username, String password, String cornfirmword) throws RemoteException {
		// TODO Auto-generated method stub
		return userService.register(username, password, cornfirmword);
	}

	@Override
	public String[] getVersionList(String username, String filename) throws RemoteException {
		// TODO Auto-generated method stub
		return iOService.getVersionList(username, filename);
	}

	@Override
	public String getVersion(String username, String filename, String versionName) throws RemoteException {
		// TODO Auto-generated method stub
		return iOService.getVersion(username, filename, versionName);
	}

	@Override
	public boolean addVersion(String username, String filename, String version) throws RemoteException {
		// TODO Auto-generated method stub
		return iOService.addVersion(username, filename,version);
	}

	@Override
	public boolean updateFile(String username, String filename, String file) throws RemoteException {
		// TODO Auto-generated method stub
		return iOService.updateFile(username, filename, file);
	}

	@Override
	public String execute(String code, String param) throws RemoteException {
		// TODO Auto-generated method stub
		return executeService.execute(code, param);
	}

}
