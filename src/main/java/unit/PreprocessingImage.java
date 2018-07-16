package unit;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

//图片预处理
public class PreprocessingImage {
	
	public PreprocessingImage() {}
	
	//图片二值化，dirPath为工商信息图片存放的路径
	public String BinaryImage (String dirPath) throws IOException {

		//输出预处理图片的路径
        String outPath = dirPath + "\\output\\";
        
        File file = new File(dirPath);
        File outFile = new File(outPath);
        if (!outFile.exists()) {
        	//新建output文件夹
            outFile.mkdir();
        }

        //二值化后的图片
        BufferedImage binaryImage = null;
        //未二值化的图片
        BufferedImage unBinaryImage = null;
        
        //批处理图片
        File[] files = file.listFiles();
        for (File f : files) {
            if (!f.isDirectory()) {
            	//读图片
                unBinaryImage = ImageIO.read(f);

                int max = new Color(255, 255, 255).getRGB();
                int min = new Color(0, 0, 0).getRGB();

                //图片裁剪（600x75）、二值化
                binaryImage = new BufferedImage(600, 75, BufferedImage.TYPE_BYTE_BINARY);

                //图片灰度化
                for (int y = 5; y < 80; y++) {
                    for (int x = 0; x < 600; x++) {
                        int RBG = getGray(unBinaryImage.getRGB(x, y));
                        if (RBG == 0) {
                        	binaryImage.setRGB(x, y - 5, max);
                        } else if (RBG == 229) {
                        	binaryImage.setRGB(x, y - 5, max);
                        } else {
                        	binaryImage.setRGB(x, y - 5, min);
                        }
                    }
                }
                
                //获取图片名
                String fileName = f.getName();
                String preImage = fileName.substring(fileName.indexOf(".") + 1);
                //生成预处理后的图片到读取目录的output文件夹下
                ImageIO.write(binaryImage, preImage, new File(outPath + fileName));
            }
        }
        return outPath;
    }

	//灰度化
    public static int getGray(int rgb) {

        int red, blue, green;
        Color c = new Color(rgb);
        red = c.getRed();
        green = c.getGreen();
        blue = c.getBlue();

        int top = (red + green + blue) / 3;
        return top;

    }

}
