package test.swing.c;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

public class test extends JFrame {  
  private static final long serialVersionUID = 1L;  

  public static void main(String[] args) throws Exception {  
      new ConfigFrame("保存文件");  
  }  
}  

class ConfigFrame extends JFrame {  
  private static final long serialVersionUID = 1L;  

  public ConfigFrame(String title) {  
      //设置窗口属性  
      final int width = 300;  
      final int height = 200;  
      final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();  
      final int left = (screen.width - width) / 2;  
      final int top = (screen.height - height) / 2;  
      this.setLocation(left, top);  
      this.setSize(width, height);  
      this.setTitle(title);  

      //添加组件  
      JPanel panel = new JPanel();  
      this.add(panel);  
      JButton openBtn = new JButton("打开");  
      JButton saveBtn = new JButton("保存");  
      panel.add(openBtn);  
      panel.add(saveBtn);  

      final JFileChooser chooser = new JFileChooser("."); //在当前目录下，创建文件选择器  

      JpgFileFilter jpgFilter = new JpgFileFilter(); //jpg过滤器  
      GifFileFilter gifFilter = new GifFileFilter(); //gif过滤器  
      chooser.addChoosableFileFilter(jpgFilter); //加载jpg文件过滤器  
      chooser.addChoosableFileFilter(gifFilter); //加载gif文件过滤器  

      chooser.setFileFilter(jpgFilter); //设置默认的文件管理器。如果不设置,则最后添加的文件过滤器为默认过滤器  

      openBtn.addActionListener(new ActionListener() {  
          public void actionPerformed(ActionEvent e) {  
              int returnVal = chooser.showOpenDialog(ConfigFrame.this); //显示窗口  

              if (returnVal == JFileChooser.APPROVE_OPTION) {  
                  String filepath = chooser.getSelectedFile().getPath();  
                  String filename = chooser.getSelectedFile().getName();  

                  System.out.println("path:" + filepath);  
                  System.out.println("name:" + filename);  
              }  

          }  
      });  

      saveBtn.addActionListener(new ActionListener() {  
          public void actionPerformed(ActionEvent e) {  
              chooser.setSelectedFile(new File("picture.jpg")); //设置保存时的，默认文件名              

              int returnVal = chooser.showSaveDialog(ConfigFrame.this); //显示保存文件窗口  

              if (returnVal == JFileChooser.APPROVE_OPTION) {  
                  String filepath = chooser.getSelectedFile().getPath();  
                  String filename = chooser.getSelectedFile().getName();  
              }  

          }  
      });  

      this.setVisible(true);  
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
  }  
}  

class JpgFileFilter extends FileFilter {  
  public String getDescription() {  
      return "*.jpg";  
  }  

  public boolean accept(File file) {  
      String name = file.getName();  
      return name.toLowerCase().endsWith(".jpg");  
  }  
}  

class GifFileFilter extends FileFilter {  
  public String getDescription() {  
      return "*.gif";  
  }  

  public boolean accept(File file) {  
      String name = file.getName();  
      return name.toLowerCase().endsWith(".gif");  
  }  
}  