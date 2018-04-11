package test.swing.c;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

class MyFrame extends JFrame implements ActionListener {
	JPanel contentPane;
	JMenuBar menubar;
	JMenu fileMenu;
	JMenuItem openItem, saveItem, exitItem;
	JScrollPane scroll;
	JTextArea txa;

	MyFrame(String s) {
		super(s);
		setSize(400, 300);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		setContentPane(contentPane);

		txa = new JTextArea();
		txa.setEditable(false);
		scroll = new JScrollPane();
		this.setContentPane(scroll);

		// 菜单栏
		menubar = new JMenuBar();
		fileMenu = new JMenu("文件");
		openItem = new JMenuItem("打开");
		saveItem = new JMenuItem("保存");
		exitItem = new JMenuItem("退出");
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);
		menubar.add(fileMenu);
		this.setJMenuBar(menubar);

		// 为菜单项添加时间监听
		openItem.addActionListener(this);
		saveItem.addActionListener(this);
		exitItem.addActionListener(this);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	// 实现ActionListener接口的actionPerformed方法

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == exitItem)
			System.exit(0);
		if (e.getSource() == openItem) {
			JFileChooser chooser = new JFileChooser(); // 创建文件对话框
			chooser.setCurrentDirectory(new File(".")); // 路径为当前工程目录
			chooser.setMultiSelectionEnabled(true); // 设置为多选
			// 显示“打开”文件对话框，返回用户操作
			int result = chooser.showOpenDialog(null);
			if (result == JFileChooser.APPROVE_OPTION) // 若用户单击“打开”按钮
			{
				txa.append("打开文件：");
				File files[] = chooser.getSelectedFiles(); // 得到所选文件名称
				for (int i = 0; i < files.length; i++) {
					txa.append("\"" + files[i].getName() + "\"");
				}
				txa.append("\n");
			}
		}

		if (e.getSource() == saveItem) {
			JFileChooser chooser = new JFileChooser(); // 创建文件对话框
			chooser.setCurrentDirectory(new File(".")); // 路径为当前工程目录
			int result = chooser.showSaveDialog(null); // 显示“保存”文件对话框，返回用户操作
			if (result == JFileChooser.APPROVE_OPTION)
				;
			{ // 若用户单击“保存”按钮
				String str = chooser.getSelectedFile().getName();
				txa.append("保存文件：\"" + str + "\"");
			}
		}
	}
}

public class JFileDialogDemo {

	public static void main(String[] args) {
		MyFrame frm = new MyFrame("文件对话框演示");

	}

}