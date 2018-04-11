package test.swing.c;

import java.awt.BorderLayout;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

public class JTableTest2 extends JFrame {

	public JTableTest2() {
		super();
		setTitle("表格");
		setBounds(100, 100, 240, 150);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Vector columnNameV = new Vector();
		columnNameV.add("A");
		columnNameV.add("B");
		Vector tableValueV = new Vector();
		for (int row = 1; row < 6; row++) {
			Vector rowV = new Vector(); // 一个数组
			rowV.add("A" + row); // 添加两列
			rowV.add("B" + row);
			tableValueV.add(rowV); // 添加一个数组
		}
		JTable table = new JTable(tableValueV, columnNameV); // 指定列名与数据
		getContentPane().add(table, BorderLayout.CENTER); // 数据居中
		JTableHeader tableHeader = table.getTableHeader(); // 获得列名
		getContentPane().add(tableHeader, BorderLayout.NORTH); // 列名居上
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JTableTest2 jTableTest2 = new JTableTest2();
		jTableTest2.setVisible(true);
	}

}
