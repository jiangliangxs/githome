package test.swing.c;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class InvokeTest extends JFrame {
	public InvokeTest() {
		super("TestJScrollPane");
		this.setLayout(null);
		this.setBounds(200, 200, 300, 300);
		JPanel panel = new JPanel();
//		panel.setPreferredSize(new Dimension(300, 100));// 主要是这句代码，设置panel的首选大小，同时保证宽高大于JScrollPane的宽高，这样下面的JScrollPane才会出现滚动条
		JButton button1 = new JButton("1");
		panel.add(button1);
		JButton button2 = new JButton("2");
		panel.add(button2);
		JButton button3 = new JButton("3");
		panel.add(button3);
		JButton button4 = new JButton("4");
		panel.add(button4);
		JButton button5 = new JButton("5");
		panel.add(button5);
		JButton button6 = new JButton("6");
		panel.add(button6);
		JButton button7 = new JButton("7");
		panel.add(button7);
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setBounds(10, 10, 100, 200);
		this.getContentPane().add(scrollPane);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public static void main(String[] args) {
		new InvokeTest();

	}

};