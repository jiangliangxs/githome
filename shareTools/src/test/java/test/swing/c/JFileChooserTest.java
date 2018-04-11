package test.swing.c;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class JFileChooserTest  extends JFrame{

	 public JFileChooserTest()
	 {
	  super();
	  setTitle("JFileChooserTest");
	  setBounds(100,100,350,150);
	  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  final JButton button = new JButton();
	  final JLabel label = new JLabel();
	  button.addActionListener(new ActionListener(){  //监听事件
	   public void actionPerformed(ActionEvent e){
	    JFileChooser fileChooser = new JFileChooser();  //对话框
	    int i = fileChooser.showOpenDialog(getContentPane());  //opendialog
	    if(i==JFileChooser.APPROVE_OPTION)  //判断是否为打开的按钮
	    {
	     File selectedFile = fileChooser.getSelectedFile();  //取得选中的文件
	     label.setText(selectedFile.getPath());   //取得路径
	    }
	   }
	  });
	  getContentPane().add(button,BorderLayout.NORTH);  //布局处理
	  getContentPane().add(label,BorderLayout.CENTER);
	  button.setText("上传");
	 }
	 /**
	  * @param args
	  */
	 public static void main(String[] args) {
	  // TODO Auto-generated method stub
	  JFileChooserTest jFileChooserTest = new JFileChooserTest();
	  jFileChooserTest.setVisible(true);
	 }

	}

