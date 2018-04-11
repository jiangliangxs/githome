package test.swing.c;


import java.awt.*;
import javax.swing.*;

public class SwingBeautify extends JFrame
{
	public SwingBeautify()
	{
		setSize(500,400);
		setLocation(100,100);
		
		Container cc = this.getContentPane();		
		cc.setLayout(new FlowLayout());
		
		JLabel L1 = new JLabel("中国人民");
		cc.add(L1);
		
		JTextField t1 = new JTextField(10);
		cc.add(t1);
		
		JComboBox c1 = new JComboBox();
		c1.addItem("bei jing ");
		c1.addItem("nan jing ");
		c1.addItem("ha er bin ");
		c1.addItem("....@$#@$ ");
		cc.add(c1);
		
		JPanel p1 = new JPanel();
		JCheckBox cb1 = new JCheckBox("数学");
		JCheckBox cb2 = new JCheckBox("英语");
		JCheckBox cb3 = new JCheckBox("物理");
		p1.add(cb1);
		p1.add(cb2);
		p1.add(cb3);
		cc.add(p1);
		
		JPanel p2 = new JPanel();
		cc.add(p2);
		JRadioButton r1 = new JRadioButton("男");
		JRadioButton r2 = new JRadioButton("女");
		
		p2.add(r1);
		p2.add(r2);
		
		ButtonGroup g1 = new ButtonGroup();  // 逻辑组件，不显示
		g1.add(r1);
		g1.add(r2);
		
		TextArea ta = new TextArea(5,30);
		cc.add(ta);
		
		L1.setFont(new Font("隶书",Font.PLAIN,40));
		L1.setForeground(Color.red);
		
		t1.setForeground(new Color(255,0,0));
		t1.setBackground(new Color(100,255,200));
		
		// 布局方式：窗口大小变化时，组件？？
		// 1。流布局：组件在窗口中“流动”
		//cc.setLayout(new FlowLayout());
		/*
		cc.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		for(int i=0; i<20; i++){
			JButton b = new JButton("kk " + i);
			cc.add(b);
		}
		*/
		
		//2。边界布局（东西南北中）：停靠在某边
		/*
		cc.setLayout(new BorderLayout());
		cc.add(new JButton("tool bar"), BorderLayout.NORTH);
		cc.add(new JButton("status bar"), BorderLayout.SOUTH);
		cc.add(new JButton(" tree "), BorderLayout.WEST);
		cc.add(new JButton("xxxx"), BorderLayout.CENTER);
		*/
	}
	
	public static void main(String[] args)
	{
		SwingBeautify a = new SwingBeautify();
		a.setVisible(true);
	}
}