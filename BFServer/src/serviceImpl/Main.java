package serviceImpl;

import java.rmi.RemoteException;

import serviceImpl.ExecuteServiceImpl;

public class Main{

	public static void main(String[] args) throws RemoteException {
		//测试解析器的代码
		/*
		String code=",>++++++[<-------->-],,[<+>-],<.>.";
		String input="3+4"+"\n";
		ExecuteServiceImpl ei=new ExecuteServiceImpl();
		String output=ei.execute(code,input);
		System.out.print(output);
		*/
		//测试数据功能的代码
		/*
		UserData userdata=UserData.getInstance();
		
		boolean ifadd=userdata.adduser("admin", "123456a");
//		String[] filelist=userdata.getuserlist();
		for(String user: userdata.userIds){
			System.out.println(user);
		}
		boolean ifmatch=userdata.ismatch("admin", "123456a");
		System.out.println(ifadd);
		System.out.println(ifmatch);
		*/
//		UserData2.updateFile("admin1", "firstFile", "nothing else");
//		UserData2.getfile("admin1", "firstFile");
		UserData.getItsFileList("admin2");
//		UserData.adduserfile("admin1", "firstFile", "nothing else");
//		UserData.addVersion("admin2", "another", "nothing else");
//		UserData.getVersion("admin1", "firstFile","2016-06-26-10-47-07.txt");
//		UserData.getItsFileList("admin1");
	}

}