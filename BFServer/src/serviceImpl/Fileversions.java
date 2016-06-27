package serviceImpl;

import java.text.*;
import java.util.ArrayList;
import java.util.Date;

public class Fileversions {
	ArrayList<String> versionNames=new ArrayList<String>();
	ArrayList<String> versionContents=new ArrayList<String>();
	
	public boolean cleanFileversion(){
		versionNames=new ArrayList<String>();
		versionContents=new ArrayList<String>();
		return true;
	}
	public boolean addVersion(String version){
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String versionName=format.format(date);
		String[] versionList=getVersionList();
		for(int i=0;i<versionList.length;i++){
			if(versionList[i].equals(versionName)){
				return false;
			}
		}
		versionNames.add(versionName);
		versionContents.add(version);
		return true;
	}
	
	public String[] getVersionList(){
		String[] verionList=new String[versionNames.size()];
		for(int i=0;i<versionNames.size();i++){
			verionList[i]=versionNames.get(i);
		}
		return verionList;
	}
	
	public String getVerstion(String versionName){
		String result="";
		String[] versionList=getVersionList();
		for(int i=0;i<versionList.length;i++){
			if(versionList[i].equals(versionName)){
				result=versionContents.get(i);
			}
		}
		return result;
	}
}
