package unit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class OutputXls {
	
	public OutputXls(String dirPath) throws IOException {
		
    	FileOutputStream fos = new FileOutputStream(dirPath+"\\识别结果.xls");	
    	Workbook wb = new HSSFWorkbook();
    	Sheet sheet = wb.createSheet("识别结果");
    	
    	// 使用 Sheet 接口创建一行
  	    Row row = sheet.createRow(0);
  	    // 使用重载的方法为单元格设置值
  	    Cell c1 = row.createCell(0);
  	    c1.setCellValue("企业名称");
  	    Cell c2 = row.createCell(1);
  	    c2.setCellValue("企业注册号");

  	    BufferedReader bf = null;
  	    String s = null , name = null , number = null;
  	    File[] files = new File(dirPath).listFiles();

    	ArrayList<String> outputname = new ArrayList<String>();
    	ArrayList<String> outputnumber = new ArrayList<String>();
    	  
    	for(File f:files){
    		  //对于每个文件
    	      if(f.getName().substring(f.getName().indexOf(".")+1).equals("txt")){
    	          StringBuffer buffer = new StringBuffer();
    	          bf = new BufferedReader(new FileReader(f.getAbsolutePath()));
    	          int index = 0;
    	          while((s = bf.readLine())!=null){
    	        	  String ss[] = s.split(":");

    	        	  if(index == 0) {
    	        		  number = ss[1].replaceAll(" ", "");
    	        		  outputnumber.add(number);
    	        	  }
    	        	  if(index == 1) {
    	        		  name = ss[1].replaceAll(" ", "");
    	        		  outputname.add(name);
    	        	  }
    	        	  index++;
    	              if(index == 2) {
    	            	  break;
    	              }
    	        	 
    	          }
    	      }

    	  }
    	  for(int j = 0 ; j < outputname.size(); j++) {
    		  // 使用 Sheet创建一行
			  Row rowoutput = sheet.createRow(j+1);
		      rowoutput.createCell(0).setCellValue(outputname.get(j));
		      rowoutput.createCell(1).setCellValue(outputnumber.get(j));
    	  }
    	  wb.write(fos); 
    	  fos.close();
    }

}
