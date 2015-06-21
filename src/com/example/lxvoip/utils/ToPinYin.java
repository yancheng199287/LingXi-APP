package com.example.lxvoip.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class ToPinYin {
	
	/**
	 * ï¿½ï¿½ï¿½ï¿½ï¿½ÝµÄºï¿½ï¿½ï¿½list×ªï¿½ï¿½ï¿½ï¿½Æ´ï¿½ï¿½List
	 * @param list
	 */
	public static List<String> getPinyinList(List<String> list){
		List<String> pinyinList = new ArrayList<String>();
		for(Iterator<String> i=list.iterator(); i.hasNext();) {
			String str = (String)i.next();
			try {
				String pinyin = getPinYin(str);
				pinyinList.add(pinyin);
			} catch (BadHanyuPinyinOutputFormatCombination e) {
				e.printStackTrace();
			}
		}
		return pinyinList;
	}
	
    /**
     * »ñÈ¡ººÓï×ÖµÄ´óÐ´Æ´Òô×ÖÄ¸
     * @param 
     * @return
     */
    public static String getPinYin(String zhongwen)   
            throws BadHanyuPinyinOutputFormatCombination {   
  
        String zhongWenPinYin = "";   
        char[] chars = zhongwen.toCharArray();   
  
        for (int i = 0; i < chars.length; i++) {   
            String[] pinYin = PinyinHelper.toHanyuPinyinStringArray(chars[i], getDefaultOutputFormat());   
            // pinyinÊý×é²»Îª¿Õ¾Í×·¼Ó×Ö·û´®
            if (pinYin != null) {   
            	zhongWenPinYin += pinYin[0];   
            } else {   
                zhongWenPinYin += chars[i];   
            }   
        }   
        return zhongWenPinYin;   
    }   
    
    
    
    //»ñÈ¡ºº×ÖÆ´ÒôµÄÊ××ÖÄ¸
    
    public static String getFirstkey(String zhongwen)   
            throws BadHanyuPinyinOutputFormatCombination {   
  
        String zhongWenPinYin = "";   
        char[] chars = zhongwen.toCharArray();   
  
       
        for (int i = 0; i < chars.length; i++) {   
        	
        	 System.out.println("--"+chars[i]);
        	
            String[] pinYin = PinyinHelper.toHanyuPinyinStringArray(chars[i], getDefaultOutputFormat());   
         
            //   Èç¹û²»Òªsubstring·½·¨ ¾ÍÊÇÆ´Òô  ¼ÓÉÏÁË¾ÍÊÇÊ××ÖÄ¸
          
            if (pinYin != null) {   
            	zhongWenPinYin += pinYin[0].substring(0,1);   
            } else {   
                zhongWenPinYin += chars[i];   
            }   
        }   
        return zhongWenPinYin;   
    }   
  
    
    
    
    
    
    
    
    
  
    /**  
     * 
     *   
     * @return  
     */  
    private static HanyuPinyinOutputFormat getDefaultOutputFormat() {   
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();   
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);// ï¿½ï¿½Ð´   
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// Ã»ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½   
        format.setVCharType(HanyuPinyinVCharType.WITH_U_AND_COLON);// uï¿½ï¿½Ê¾   
        return format;   
    }   
}
