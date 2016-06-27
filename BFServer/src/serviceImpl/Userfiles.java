package serviceImpl;

import java.util.ArrayList;

public class Userfiles {
	ArrayList<String> filenames=new ArrayList<String>();
	ArrayList<String> files=new ArrayList<String>();
	ArrayList<Fileversions> fileversions=new ArrayList<Fileversions>();
	
	public Userfiles(){
		
	}
	
	public Userfiles(String username){
		
	}
	
	public boolean cleanfilelist(){
		filenames=new ArrayList<String>(); 
		files=new ArrayList<String>();
		fileversions=new ArrayList<Fileversions>();
		return true;
	}
	public boolean addVersion(String filename, String version){
		int index=getnumber(filename);
		if(index==-1){
			return false;
		}
		Fileversions fileVersion=fileversions.get(index);
		if(!fileVersion.addVersion(version)){
			return false;
		}
		fileversions.set(index, fileVersion);
		return true;
	}
	
	public String[] getVersionList(String filename){
		int index=getnumber(filename);
		Fileversions fileVersion=fileversions.get(index);
		return fileVersion.getVersionList();
	}
	
	public String getVersion(String filename,String versionName){
		int index=getnumber(filename);
		Fileversions fileVersion=fileversions.get(index);
		return fileVersion.getVerstion(versionName);
	}
	
	public boolean addFile(String filename, String file){
		String[] fileList=getFileList();
		for(int i=0;i<fileList.length;i++){
			if(fileList[i].equals(filename)){
				return false;
			}
		}
		this.filenames.add(filename);
		this.files.add(file);
		return true;
	}
	
	public boolean updateFile(String filename,String file){
		int index=getnumber(filename);
		if(index==-1){
			return false;
		}
		files.set(index, file);
		return true;
	}
	public String getFile(String filename){
		String result="";
		String[] fileList=getFileList();
		for(int i=0;i<fileList.length;i++){
			if(fileList[i].equals(filename)){
				result=files.get(i);
			}
		}
		return result;
	}
	
	public String[] getFileList(){
		String[] fileList=new String[filenames.size()];
		for(int i=0;i<filenames.size();i++){
			fileList[i]=filenames.get(i);
		}
		return fileList;
	}
	
	public int getnumber(String filename){
		String[] fileList=getFileList();
		for(int i=0;i<fileList.length;i++){
			if(filename.equals(fileList[i])){
				return i;
			}
		}
		return -1;
	}
}
