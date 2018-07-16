package unit;

import java.io.File;
import java.io.IOException;

public class OCR {
	
	public void findOCR(String dirPath) throws IOException, InterruptedException {

        File file = new File(dirPath);
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for (File f : files) {
                if(!f.isDirectory()){
                    Process process = Runtime.getRuntime().exec(
                        new String[]
                        		{
                                        "D:\\Program Files\\tesseract\\Tesseract-OCR\\tesseract",
                                        dirPath + f.getName(),
                                        dirPath + f.getName().substring(0,f.getName().indexOf(".")),
                                        "-l",
                                        "chi_sim"
                                });
                    process.waitFor();
                }
            }
        }else {
            return;
        }

    }

}
