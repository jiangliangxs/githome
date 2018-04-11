package test.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SetTest {
	private JFrame jframe = null;// 定义窗体
	private JButton btn1, btn2, execBtn;// 定义按钮
	private JTextField jtf1, jtf2;// 文本区域
	private JPasswordField jdebug;
	private JLabel jlb1, jlb2, jlb3, jlb4;// 定义标签
	private JComboBox filterBox, encodeBox;// 去重与编码

	public void tFrame() {
		jframe = new JFrame("张军制作");
		addEventHandler();
		// 设置东西位置及大小
		jframe.setLayout(null);// 使该窗体取消布局管理器设置
		jlb1 = new JLabel("选择原文件：");
		jlb2 = new JLabel("文件另存为：");
		jlb3 = new JLabel("是否去重：");
		jlb4 = new JLabel("编码方式：");
		jlb1.setBounds(5, 5, 95, 30);
		jlb2.setBounds(5, 45, 95, 30);
		jlb3.setBounds(5, 85, 95, 30);
		jlb4.setBounds(5, 125, 95, 30);

		jtf1 = new JTextField();
		jtf2 = new JTextField();
		jdebug =  new JPasswordField();
//		jtf1.setEditable(false);
//		jtf2.setEditable(false);
		jtf1.setBounds(100, 5, 280, 30);
		jtf2.setBounds(100, 45, 280, 30);

		btn1 = new JButton("浏览...");
		btn2 = new JButton("保存...");
		filterBox = new JComboBox(new String[] { "是", "否" });
		encodeBox = new JComboBox(new String[] { "GBK", "UTF-8", "UTF8" });
		execBtn = new JButton("开始执行...");

		btn1.setBounds(390, 5, 100, 30);
		btn2.setBounds(390, 45, 100, 30);
		filterBox.setBounds(100, 85, 80, 30);
		encodeBox.setBounds(100, 125, 80, 30);
		jdebug.setBounds(100, 165, 300, 30);
		execBtn.setBounds(200, 200, 100, 30);
		jdebug.setEchoChar('*');

		btn1.addActionListener(new SelfActionEvent());
		btn2.addActionListener(new SelfActionEvent());
		execBtn.addActionListener(new SelfActionEvent());
//		jtf1.addKeyListener(new KeyListener() {
//
//			@Override
//			public void keyTyped(KeyEvent e) {
//				// TODO Auto-generated method stub
//				System.out.println("The charactor you typed : " + "\""
//						+ e.getKeyChar() + "\"");
//			}
//
//			@Override
//			public void keyPressed(KeyEvent e) {
//				// TODO Auto-generated method stub
//
//				// 写大写字母时，释放按键是不知道是否先释放的是shift还是字母按键；但是
//				// 可以肯定的是按按键时必须是先按的shift。
//				if (e.getKeyCode() == KeyEvent.VK_CONTROL
//						|| e.getKeyCode() == KeyEvent.VK_SHIFT
//						|| e.getKeyCode() == KeyEvent.VK_ALT) {
//					System.out.println("\n" + "The Component triggered : "
//							+ e.getSource());
//				}
//				if (!(e.isControlDown() || e.isShiftDown() || e.isAltDown())) {
//					System.out.println("\n" + "The Component triggered : "
//							+ e.getSource());
//				}
//				String keyText = KeyEvent.getKeyText(e.getKeyCode());
//				if (e.isActionKey()) {
//					System.out.print("The action key you pressed : " + "\""
//							+ keyText + "\"");
//				} else {
//					System.out.print("The non-action key you pressed : " + "\""
//							+ keyText + "\"");
//					int keyCode = e.getKeyCode();
//					switch (keyCode) {
//					case KeyEvent.VK_CONTROL:
//						System.out.print(", Control key is pressed");
//						break;
//					case KeyEvent.VK_SHIFT:
//						System.out.print(", Shift key is pressed");
//						break;
//					case KeyEvent.VK_ALT:
//						System.out.print(", Alt key is pressed");
//						break;
//					}
//				}
//				System.out.println();
//			}
//
//			@Override
//			public void keyReleased(KeyEvent e) {
//				// TODO Auto-generated method stub
//				// the integer code for an actual key on the keyboard.
//				// (For KEY_TYPED events, the keyCode is VK_UNDEFINED.)
//				System.out.println("The key you released : " + "\""
//						+ KeyEvent.getKeyText(e.getKeyCode()) + "\"");
//			}
//
//		});

		jframe.add(jlb1);
		jframe.add(jlb2);
		jframe.add(jlb3);
		jframe.add(jlb4);

		jframe.add(jtf1);
		jframe.add(jtf2);

		jframe.add(btn1);
		jframe.add(btn2);
		jframe.add(filterBox);
		jframe.add(encodeBox);
		jframe.add(jdebug);
		jframe.add(execBtn);
		

		jframe.setSize(500, 270);
		jframe.setLocation(500, 300);
		jframe.setVisible(true);
		jframe.setResizable(false);// 禁止最大化
		jframe.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}

	public void addEventHandler() {
		jframe.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int op = JOptionPane.showConfirmDialog(jframe, "确定要退出程序吗？",
						"张军提示", JOptionPane.YES_NO_OPTION);
				if (op == JOptionPane.YES_OPTION) {
					JOptionPane.showMessageDialog(null, "欢迎下次使用...", "张军提示",
							JOptionPane.INFORMATION_MESSAGE);
					System.exit(0);
				}
			}
		});
	}

	class SelfActionEvent implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String str = e.getActionCommand();// 获取字符串
			// JOptionPane.showMessageDialog(null, str);
			if ("浏览...".equals(str)) {
				String rtnPath = openFile(0);
				if (rtnPath != null && !rtnPath.equals("")) {
					jtf1.setText(rtnPath);
				}
			} else if ("保存...".equals(str)) {
				String rtnPath = openFile(1);
				if (rtnPath != null && !rtnPath.equals("")) {
					jtf2.setText(rtnPath);
				}
			} else {
				if ("开始执行...".equals(str)) {
					try {
						String srcFilePath = jtf1.getText();
						String descFilePath = jtf2.getText();
						String filter = String.valueOf(filterBox
								.getSelectedItem());
						String encode = String.valueOf(encodeBox
								.getSelectedItem());
						if ("".equals(srcFilePath)) {
							JOptionPane.showMessageDialog(null, "原文件不能为空",
									"张军提示", JOptionPane.WARNING_MESSAGE);
							return;
						}
						if ("".equals(descFilePath)) {
							JOptionPane.showMessageDialog(null, "目标文件不能为空",
									"张军提示", JOptionPane.WARNING_MESSAGE);
							return;
						}
						CompUtil<Cfile> cu = new CompUtil<Cfile>();
						cu.attrName = "sname";
						cu.sort = CompUtil.DESC;
						cu.filterEqual = String.valueOf(filterBox
								.getSelectedItem()).equals("是");
						SortedSet<Cfile> set = new TreeSet<Cfile>(cu);
						// Student.filterEquals = false;
						List<String> lst = readFile2(new File(srcFilePath),
								encode);
						Cfile obj = null;
						for (String rts : lst) {
							obj = new Cfile();
							obj.sname = new File(rts);
							set.add(obj);
						}
						String rs = "";
						Iterator<Cfile> it = set.iterator();
						boolean first = false;
						while (it.hasNext()) {
							Cfile s = it.next();
							if (!"".equals(s.sname)) {
								printLog(getFileExtension(s.sname.getPath()) + "-"
										+ s.sname);
								if (first) {
									rs += "\n";
								}
								first = true;
								rs += s.sname;
							}
						}
						writeFile(new File(descFilePath), rs);
						JOptionPane.showMessageDialog(null, "恭喜你！文件生成成功...",
								"张军提示", JOptionPane.INFORMATION_MESSAGE);
						printLog(srcFilePath + "\n" + descFilePath + "\n"
								+ filter + "\n" + encode);
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "执行失败："
								+ e1.getMessage(), "张军提示",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	private void printLog(String msg) {
		if ("zhangjun360901061".equals(jdebug.getText())) {
			System.out.println(msg);
		}
	}

	public String openFile(int type) {
		String rtnPath = "";
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("张军制作");
		FileNameExtensionFilter filter1 = new FileNameExtensionFilter("*.txt",
				"txt");
		chooser.setAcceptAllFileFilterUsed(false);
		// chooser.setMultiSelectionEnabled(true);
		chooser.setFileFilter(filter1);
		filter1 = new FileNameExtensionFilter("*.java", "java");
		chooser.setFileFilter(filter1);
		filter1 = new FileNameExtensionFilter("*.js", "js");
		chooser.setFileFilter(filter1);
		int returnVal = JFileChooser.ERROR_OPTION;
		if (type == 0) {
			returnVal = chooser.showOpenDialog(jframe.getFocusOwner());
		} else if (type == 1) {
			returnVal = chooser.showSaveDialog(jframe.getFocusOwner());
		}
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			rtnPath = chooser.getSelectedFile().getPath();
			String des = chooser.getFileFilter().getDescription();
			if (!"txt".equals(getFileExtension(rtnPath))
					&& !"java".equals(getFileExtension(rtnPath))
					&& !"js".equals(getFileExtension(rtnPath))) {
				rtnPath += des.replaceFirst("\\*", "");
			}
			printLog("You chose to open this file: " + rtnPath);
		}
		return rtnPath;
	}

	private List<String> readFile2(File srcFile, String charsetName)
			throws Exception {
		List<String> lst = new ArrayList<String>();
		FileInputStream fis = new FileInputStream(srcFile);
		InputStreamReader isr = new InputStreamReader(fis, charsetName);
		BufferedReader br = new BufferedReader(isr);
		String str = null;
		while ((str = br.readLine()) != null) {
			lst.add(str);
		}
		try {
			fis.close();
		} catch (Exception e) {
		}
		try {
			isr.close();
		} catch (Exception e) {
		}
		try {
			br.close();
		} catch (Exception e) {
		}
		return lst;
	}

	private void writeFile(File destFile, String content) throws Exception {
		if (content == null)
			return;
		FileOutputStream fos = new FileOutputStream(destFile);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		byte[] buy = content.getBytes();
		bos.write(buy);
		bos.flush();
		try {
			fos.close();
		} catch (Exception e) {
		}
		try {
			bos.close();
		} catch (Exception e) {
		}
	}

	private String getFileExtension(String fileName) {
		String extension = "";
		int index = -1;
		if (fileName != null) {
			index = fileName.lastIndexOf(".");
			if (index >= 0) {
				extension = fileName.substring(index + 1);
			}
		}
		return extension;
	}

	public static void main(String[] args) throws Exception {
		SetTest t = new SetTest();
		t.tFrame();
	}

}
