package test.swing;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import test.io.TestResource;

public class JButtonDemo extends JFrame {

	private static final long serialVersionUID = 8651755261675396406L;
	private JPanel contentPane;

	public static void main(String[] args) {
		try {
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JButtonDemo frame = new JButtonDemo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public JButtonDemo() {
		System.out.println(JButtonDemo.class.getResource("cycle-red.ico"));
		setTitle("按钮使用");// 设置窗体的标题
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 设置窗体退出时操作
		setBounds(100, 100, 250, 100);// 设置窗体位置和大小
		contentPane = new JPanel();// 创建内容面板
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));// 设置面板的边框
		setContentPane(contentPane);// 应用内容面板
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));// 设置内容面板为流式布局

		JButton modifyButton = new JButton("修改");// 创建按钮
		modifyButton.setIcon(new ImageIcon(JButtonDemo.class.getResource("cycle-red.ico")));// 为按钮增加图标
		contentPane.add(modifyButton);// 在面板上增加按钮

		JButton deleteButton = new JButton("删除");// 创建按钮
		deleteButton.setIcon(new ImageIcon(JButtonDemo.class.getResource("cycle-red.ico")));// 为按钮增加图标
		contentPane.add(deleteButton);// 在面板上增加按钮
	}

}