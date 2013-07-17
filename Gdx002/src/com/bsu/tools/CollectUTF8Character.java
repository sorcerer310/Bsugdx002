package com.bsu.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CollectUTF8Character {
	
	private static ArrayList<String> filelist = new ArrayList<String>();  
	private static StringBuffer sb_cs = new StringBuffer();
	public static void main(String[] args){
		CollectUTF8Character cc = new CollectUTF8Character();
		cc.refreshFileList(System.getProperty("user.dir"));
		
        String retstr = sb_cs.toString().replaceAll("(?s)(.)(?=.*\\1)","");   
        System.out.println("------------------");
        System.out.println(retstr);
	}
	
	/**
	 * 遍历指定目录下所有文件
	 * @param strPath
	 */
	public void refreshFileList(String strPath) {  
        File dir = new File(strPath);  
        File[] files = dir.listFiles();  
        if (files == null)  
            return;  
        for (int i = 0; i < files.length; i++) {  
            if (files[i].isDirectory()) {  
                refreshFileList(files[i].getAbsolutePath());  
            } else {  
                String strFileName = files[i].getAbsolutePath().toLowerCase();
                String extName = getFileExtname(strFileName);
                System.out.print(extName+" ");
                //如果文件扩展名不为java跳过
                if(!extName.equals("java"))
                	continue;
                String s = readTxt(strFileName);
                if(s!=null)
                	sb_cs.append(s);
                filelist.add(files[i].getAbsolutePath());  
            }  
        }  
        

    } 
	/**
	 * 读取文件内容 
	 * @param fileName	//文件名
	 */
	public String readTxt(String fileName) {  
        BufferedReader reader = null;  
//        String id = null;  
//        String state = null;  
//        String time = null;
        StringBuffer csb = new StringBuffer();
        try {  
            reader = new BufferedReader(new FileReader(fileName));  
            String tempString = null;  
  
            //读取文件每一行做处理 
            while ((tempString = reader.readLine()) != null) {
            	if(tempString.indexOf("//")!=-1 || tempString.indexOf("*")!=-1)
            		continue;
            	String regEx = "[\\u4e00-\\u9fa5]";
            	Pattern p = Pattern.compile(regEx);
            	Matcher m = p.matcher(tempString);
            	while (m.find()) {
//            		for (int i = 0; i <= m.groupCount(); i++) {
            			System.out.println(tempString);
            			csb.append(m.group());
//            		}
            	}
//           	　　System.out.println("共有 " + count + "个 ");
//            	System.out.println();
//                if (tempString.startsWith("<clientID>")) {  
//                    int start = tempString.indexOf(">");  
//                    id = tempString.substring(start + 1).trim();  
//                    tempString = reader.readLine();  
//                }  
//  
//                if (tempString.startsWith("<currentTime>")) {  
//                    int start = tempString.indexOf(">");  
//                    time = tempString.substring(start + 1).trim();  
//                    tempString = reader.readLine();  
//                }  
//  
//                if (tempString.startsWith("<state>")) {  
//                    int start = tempString.indexOf(">");  
//                    state = tempString.substring(start + 1).trim();  
//                }  
//  
//                String info = id + "/n" + time + "/n" + state;  
//                System.out.println(info);  
//                return;  
            }  
            if(csb.length()>0){
            	System.out.println(fileName);  
            	System.out.println(csb.toString());
            	return csb.toString();
            }
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (reader != null) {  
                try {  
                    reader.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        return null;
    }  
	/**
	 * 获得文件扩展名
	 * @param fname	文件名 
	 * @return		返回扩展名
	 */
	private String getFileExtname(String fname){
		File file =new File(fname);
		String fileName=file.getName();
		String[] token = fileName.split("\\.");
		if(token.length>1)
			return token[1];
		return "";
	}
}
