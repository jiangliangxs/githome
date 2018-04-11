package test.swing.c;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;

public class JFileFilterTest extends JFrame {

	public JFileFilterTest() {
		super();
		setTitle("表格");
		setBounds(100, 100, 350, 150);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final JButton button = new JButton();
		final JLabel label = new JLabel("双击选择照片", SwingConstants.CENTER);
		label.addMouseListener(new MouseAdapter() { // 适配器
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) { // 双击事件
					JFileChooser fileChooser = new JFileChooser();
					FileFilter filter = new FileFilter() {

						// 要过滤的文件
						public boolean accept(File f) {
							// 显示的文件类型
							if (f.isDirectory()) {
								return true;
							}
							// 显示满足条件的文件
							return f.getName().endsWith(".jpg") || f.getName().endsWith(".gif");
						}

						/**
						 * 这就是显示在打开框中
						 */
						public String getDescription() {

							return "*.jpg,*.gif";
						}
					};
					// jdk1.6 FileNameExtensionFilter
					// new FileNameExtensionFilter("图像文件(jpg/gif)","jpg","jpeg","gif");
					fileChooser.setFileFilter(filter);
					int i = fileChooser.showOpenDialog(getContentPane()); // opendialog
					if (i == JFileChooser.APPROVE_OPTION) // 判断是否为打开的按钮
					{
						File selectedFile = fileChooser.getSelectedFile(); // 取得选中的文件
						// label.setText(selectedFile.getPath()); //取得路径
						label.setIcon(new ImageIcon(selectedFile.getAbsolutePath())); // 将图片显示在标签上
					}
				}
			}
		});

		getContentPane().add(button, BorderLayout.NORTH); // 布局处理
		getContentPane().add(label, BorderLayout.CENTER);
		button.setText("上传");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFileFilterTest jFileFilterTest = new JFileFilterTest();
		jFileFilterTest.setVisible(true);
	}

}