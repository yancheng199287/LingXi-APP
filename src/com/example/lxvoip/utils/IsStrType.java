package com.example.lxvoip.utils;

//判断字符串类型
public class IsStrType {
	
	
	public static int tell(String str){
      //  String type="未知";		
		int a=0;
		
		if(isNumber(str)){
			a=1;
			//type="0是数字";
		}else if(isWord(str)){
			//type="A是字母";
			a=2;
		}else{
			//type="是中文";
			a=3;
		}
		
		return a;
		
		
	}
	
	
	
	//2.判断一个字符串的首字符是否为字母
	public static boolean isWord(String s)  
	  {  
	  char   c   =   s.charAt(0);  
	  int   i   =(int)c;  
	  if((i>=65&&i<=90)||(i>=97&&i<=122))  
	  {  
	  return   true;  
	  }  
	  else  
	  {  
	  return   false;  
	  }  
	  }
	
	
	
	
	
	
	
	//判断是否为数字
	public static boolean isNumber(String str){  
		  for (int i = str.length();--i>=0;){    
		   if (!Character.isDigit(str.charAt(i))){  
		    return false;  
		   }  
		  }  
		  return true;  
		}  
	
	//判断是否为汉字
	public static boolean isChinese(String str){
		  
	    char[] chars=str.toCharArray(); 
	    boolean isGB2312=false; 
	    for(int i=0;i<chars.length;i++){
	                byte[] bytes=(""+chars[i]).getBytes(); 
	                if(bytes.length==2){ 
	                            int[] ints=new int[2]; 
	                            ints[0]=bytes[0]& 0xff; 
	                            ints[1]=bytes[1]& 0xff; 
	                            if(ints[0]>=0x81 && ints[0]<=0xFE && ints[1]>=0x40 && ints[1]<=0xFE){ 
	                                        isGB2312=true; 
	                                        break; 
	                            } 
	                } 
	    } 
	    return isGB2312; 
	}
	

}
