package test.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class SimpleTable {
	public SimpleTable() {
		JFrame f = new JFrame();
		Object[][] playerInfo = { { " 阿 呆 ", new Integer(66), new Integer(32), new Integer(98), new Boolean(false) }, { "阿呆", new Integer(82), new Integer(69), new Integer(128), new Boolean(true) }, };
		String[] Names = { "姓名", "语文", "数学", "总分", "及格" };
		JTable table = new JTable(playerInfo, Names);
		table.setPreferredScrollableViewportSize(new Dimension(550, 30)); // 设置此表视口的首选大小
		JScrollPane scrollPane = new JScrollPane(table);
		f.getContentPane().add(scrollPane, BorderLayout.CENTER); // JFrame中仅包含一个JRootPane子容器。在 //JFrame中显示的所有非菜单组件均应该包含在JRootPane所提供的Content pane中因此frame.add(child);是错误的
		f.setTitle("SimpleTable");
		f.pack();
		f.show();
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	public static void main(String[] args) {
		SimpleTable b = new SimpleTable();
	}
}
