//请不要修改本文件名
package serviceImpl;


import java.rmi.RemoteException;
import java.util.ArrayList;

import service.ExecuteService;
import service.UserService;

public class ExecuteServiceImpl implements ExecuteService {

	/**
	 * 请实现该方法
	 */
	@Override
	public String execute(String code, String param) throws RemoteException {
		// TODO Auto-generated method stub
		String output="";
		String input=param;
		int inputpointer=0;
		ArrayList<Integer> register=new ArrayList<Integer>();
		for(int i=0;i<10;i++){
			register.add(0);
		}
		int pointer=0;
		
		for(int codepointer=0;codepointer<code.length();codepointer++){
			
			char instruct=code.charAt(codepointer);
			
			if(pointer==register.size()-5){
				for(int i=0;i<5;i++){
					register.add(0);
				}
			}
			
			if(instruct=='+'){
				int number=register.get(pointer);
				register.set(pointer, number+1);
			}
			
			if(instruct=='-'){
				int number=register.get(pointer);
				register.set(pointer, number-1);
			}
			
			if(instruct=='>'){
				pointer++;
			}
			
			if(instruct=='<'){
				pointer--;
			}
			
			if(instruct=='.'){
				int number=register.get(pointer);
				char c=(char)number;
				output+=c;
				
			}
			
			if(instruct==','){
				int number = input.charAt(inputpointer);
				inputpointer++;
//				while(number=='\n' || number==' '){
//					inputpointer++;
//					number=input.charAt(inputpointer);
//				}
//				while(input.charAt(inputpointer)<='9' && input.charAt(inputpointer)>='0'){
//					number=input.charAt(inputpointer)+(number-48)*10;
//					inputpointer++;
//				}
				register.set(pointer, number);
			}
			
			if(instruct=='['){
				if(register.get(pointer)==0){
					int cyclenumber=0;
					int cycleindex=1;
					while(code.charAt(codepointer)!=']' || cycleindex!=cyclenumber){
						if(code.charAt(codepointer)==']'){
							cycleindex++;
						}
						if(code.charAt(codepointer)=='['){
							cyclenumber++;
						}
						codepointer++;
					}
				}
			}
			
			if(instruct==']'){
				if(register.get(pointer)!=0){
					int cyclenumber=0;
					int cycleindex=1;
					while(code.charAt(codepointer)!='[' || cycleindex!=cyclenumber){
						if(code.charAt(codepointer)=='['){
							cycleindex++;
						}
						if(code.charAt(codepointer)==']'){
							cyclenumber++;
						}
						codepointer--;
					}
				}
			}
			
		}
		
		return output;
	}

}
