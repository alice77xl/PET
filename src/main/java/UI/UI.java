package UI;

import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import unit.OCR;
import unit.OutputXls;
import unit.PreprocessingImage;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class UI extends JFrame {

	private JPanel contentPane;
	//图片文件夹的位置
	private JTextField textField;
	//识别结果的位置
	private JTextField textField_1;
	
	//网店工商信息图片的存放路径
	String dirPath = null;
	//输出路径
	String outPath = null;

	/**
	 * 创建界面
	 */
	public UI() {
		//设置标题
		super("网店工商图片文字提取系统");		
				
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("图片文件夹的位置：");
		label.setBounds(100, 46, 182, 23);
		contentPane.add(label);
		
		textField = new JTextField();
		textField.setBounds(100, 79, 300, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel label_1 = new JLabel("识别结果的位置：");
		label_1.setBounds(100, 249, 141, 31);
		contentPane.add(label_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(100, 293, 300, 21);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JButton button = new JButton("浏览");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dirPath = openDir();
				textField.setText(dirPath);
			}
		});
		button.setBounds(420, 77, 60, 23);
		contentPane.add(button);
		
		JButton button_1 = new JButton("开始识别");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//图片预处理
					outPath = new PreprocessingImage().BinaryImage(dirPath);
					//图片识别
					new OCR().findOCR(outPath);
					//输出为excel
			        new OutputXls(outPath);

					textField_1.setText(outPath);
				} catch (IOException | InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		});
		button_1.setBounds(100, 137, 93, 31);
		contentPane.add(button_1);

		JButton btnNewButton = new JButton("打开");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Runtime.getRuntime().exec("cmd /c start " + outPath);
				} catch (IOException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(420, 292, 60, 23);
		contentPane.add(btnNewButton);
		
//		JLabel label_2 = new JLabel("识别速度：每张图片 ");
//		label_2.setBounds(100, 205, 129, 15);
//		contentPane.add(label_2);
//		
//		textField_2 = new JTextField();
//		textField_2.setBounds(235, 202, 54, 21);
//		contentPane.add(textField_2);
//		textField_2.setColumns(10);
//		
//		JLabel label_3 = new JLabel("秒");
//		label_3.setBounds(300, 205, 29, 15);
//		contentPane.add(label_3);
		
		//设置固定大小
		this.setResizable(false);
		//背景图片的路径。（相对路径或者绝对路径。本例图片放于"java项目名"的文件下）
		String path = "background.jpg";
		//java.net.URL path=UI.class.getResource("image/background.jpg");
		//背景图片
		ImageIcon background = new ImageIcon(path);
		// ImageIcon background = new ImageIcon(getClass().getResource("images/background.jpg"));
		//把背景图片显示在一个标签里面
		JLabel imagelabel = new JLabel(background);
		//把标签的大小位置设置为图片刚好填充整个面板
		imagelabel.setBounds(0, 0, this.getWidth(), this.getHeight());
		//把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明
		JPanel imagePanel = (JPanel) this.getContentPane();
		imagePanel.setOpaque(false);
		//把背景图片添加到分层窗格的最底层作为背景
		this.getLayeredPane().add(imagelabel, new Integer(Integer.MIN_VALUE));
	}
	
	public String openDir() {
		JFileChooser fc=new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);//只能选择目录
		File f=null;
		int flag = 0;
		try{     
			flag=fc.showOpenDialog(null);     
		}catch(HeadlessException head){     
		    System.out.println("Open File Dialog ERROR!");    
		}        
		if(flag==JFileChooser.APPROVE_OPTION){
           //获得该文件 夹  
           f=fc.getSelectedFile();    
           dirPath=f.getPath();
        }    
		return dirPath;
	}
}
