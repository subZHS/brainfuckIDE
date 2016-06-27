package serviceImpl;

import java.rmi.RemoteException;

import service.UserService;

public class UserServiceImpl implements UserService{

	@Override
	
	public boolean login(String username, String password) throws RemoteException {
		if(UserData.getnumber(username)==-1){
			return false;
		}
		if(!UserData.ismatch(username, password)){
			return false;
		}
		return true;
	}

	@Override
	public boolean logout(String username) throws RemoteException {
		if(UserData.getnumber(username)==-1){
			return false;
		}
		return true;
	}
	
	public boolean register(String username, String password, String confirmword) throws RemoteException {
		if(!password.equals(confirmword)){
			return false;
		}
		if(UserData.getnumber(username)!=-1){
			return false;
		}
		if(UserData.adduser(username, password)){
			return true;
		}else{
			return false;
		}
	}

}
