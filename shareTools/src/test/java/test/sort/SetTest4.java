//package utils.sortUtils.test;
//
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Set;
//import java.util.SortedSet;
//import java.util.TreeSet;
//
//import javax.swing.JButton;
//import javax.swing.JFileChooser;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JTextField;
//
//import utils.sortUtils.util1.CompUtil;
//import utils.sortUtils.util3.Student;
//
//import com.jun.zhang.util.file.FileUtils;
//
//public class SetTest4 {
//	private JFrame jframe = null;// 定义窗体
//
//	public void tFrame() {
//		jframe = new JFrame("Window Event Test!");
//		addEventHandler();
//		// 设置东西位置及大小
//		jframe.setLayout(null);// 使该窗体取消布局管理器设置
//		JButton btn1, btn2, btn3, btn4;// 定义按钮
//		JTextField jtf1;// 文本区域
//		JLabel jlb1, jlb2, jlb3;// 定义标签
//		btn1 = new JButton("原文件路径：");
//		btn2 = new JButton("保存文件路径：");
//		btn3 = new JButton("是否去重：");
//		btn4 = new JButton("编码方式：");
//		btn1.setBounds(0, 0, 100, 30);
//		btn2.setBounds(0, 40, 100, 30);
//		btn3.setBounds(0, 80, 100, 30);
//		btn1.addActionListener(new SelfActionEvent());
//		btn2.addActionListener(new SelfActionEvent());
//		btn3.addActionListener(new SelfActionEvent());
//		jframe.add(btn1);
//		jframe.add(btn2);
//		jframe.add(btn3);
//		jframe.setSize(500, 580);
//		jframe.setLocation(500, 300);
//		jframe.setVisible(true);
//		jframe.setResizable(false);// 禁止最大化
//		jframe.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//	}
//
//	public void addEventHandler() {
//		jframe.addWindowListener(new WindowAdapter() {
//			public void windowClosing(WindowEvent e) {
//				int op = JOptionPane.showConfirmDialog(jframe, "确定要退出程序吗？",
//						"确认退出", JOptionPane.YES_NO_OPTION);
//				if (op == JOptionPane.YES_OPTION) {
//					System.exit(0);
//				}
//			}
//		});
//	}
//
//	class SelfActionEvent implements ActionListener {
//		public void actionPerformed(ActionEvent e) {
//			String str = e.getActionCommand();// 获取字符串
//			JOptionPane.showMessageDialog(null, str);
//			openFile();
//		}
//	}
//
//	public void openFile() {
//		JFileChooser chooser = new JFileChooser();
//		// FileNameExtensionFilter filter = new
//		// FileNameExtensionFilter("txt & GIF Images", "jpg", "gif");
//		// chooser.setFileFilter(filter);
//		int returnVal = chooser.showOpenDialog(jframe.getFocusOwner());
//		if (returnVal == JFileChooser.APPROVE_OPTION) {
//			System.out.println("You chose to open this file: "
//					+ chooser.getSelectedFile().getName());
//		}
//
//	}
//
//	public static void main(String[] args) throws Exception {
//		// System.out.println("HashSet-----------");
//		// hset();
//		// System.out.println("**********************");
//		// System.out.println("TreeSet-----------");
//		// tset();
//
//		tset2();
//		// SetTest t = new SetTest();
//		// t.tFrame();
//	}
//
//	public static void hset() {
//		Set<Student> set = new HashSet<Student>();
//		set.add(new Student(10, "zhang"));
//		set.add(new Student(11, "ahang1"));
//		set.add(new Student(12, "fhang2"));
//		set.add(new Student(13, "zhang3"));
//		set.add(new Student(14, "hhang"));
//		set.add(new Student(15, "fhang6"));
//		set.add(new Student(16, "dhang5"));
//		set.add(new Student(10, "zhang"));
//
//		Iterator<Student> it = set.iterator();
//		while (it.hasNext()) {
//			Student s = it.next();
//			System.out.println(s.getId() + "\t" + s.getName());
//		}
//	}
//
//	public static void tset() {
//		SortedSet<Student> set = new TreeSet<Student>(Student.SN);
//		set.add(new Student(10, "zhang"));
//		set.add(new Student(11, "ahang1"));
//		set.add(new Student(12, "fhang2"));
//		set.add(new Student(18, "zhang3"));
//		set.add(new Student(14, "hhang"));
//		set.add(new Student(15, "fhang6"));
//		set.add(new Student(16, "dhang5"));
//		set.add(new Student(10, "zhang"));
//		set.add(new Student(15, "zhang"));
//		set.add(new Student(11, "zhang"));
//
//		Iterator<Student> it = set.iterator();
//		while (it.hasNext()) {
//			Student s = it.next();
//			System.out.println(s.getId() + "\t" + s.getName());
//		}
//	}
//
//	public static void tset2() throws Exception {
//		CompUtil<Student> cu = new CompUtil<Student>();
//		cu.attrName = "name";
//		cu.sort = CompUtil.ASC;
//		SortedSet<Student> set = new TreeSet<Student>(cu);
////		Student.filterEquals = false;
////		List<String> lst = FileUtils.readFile2(
////				new File("E:\\测试资料\\文件\\old.txt"), "utf-8");
//		List<String> lst  = new ArrayList<String>();
//		lst.add("a1");
//		lst.add("a2");
//		lst.add("a6");
//		lst.add("a8");
//		lst.add("a9");
//		lst.add("a5");
//		Student stus = null;
//		for (String rts : lst) {
//			stus = new Student(rts);
//			set.add(stus);
//		}
//		String rs = "";
//		Iterator<Student> it = set.iterator();
//		boolean first = false;
//		while (it.hasNext()) {
//			Student s = it.next();
//			if (!"".equals(s.getName())) {
//				System.out.println(FileUtils.getFileExtension(s.getName()) + "-" + s.getName());
//				if (first) {
//					rs += "\n";
//				}
//				first = true;
//				rs += s.getName();
//			}
//		}
////		FileUtils.writeFile("E:\\测试资料\\文件\\new.txt", rs);
//	}
//
//}
