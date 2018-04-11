package test.swing.c;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class JTableTest3 extends JFrame {

	public JTableTest3() {
		super();
		setTitle("表格");
		setBounds(100, 100, 240, 150);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		String[] columnNames = { "A", "B" }; // 列名
		String[][] tableVales = { { "A1", "B1" }, { "A2", "B2" }, { "A3", "B3" }, { "A4", "B4" }, { "A5", "B5" } }; // 数据
		JTable table = new JTable(tableVales, columnNames); // 生成一个JTable
		JScrollPane scrollPane = new JScrollPane(table); // 支持滚动
		getContentPane().add(scrollPane, BorderLayout.CENTER);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JTableTest3 jTableTest = new JTableTest3();
		jTableTest.setVisible(true);
	}

}