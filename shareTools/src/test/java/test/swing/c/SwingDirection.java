package test.swing.c;

import java.awt.*;
import javax.swing.*;

public class SwingDirection extends JFrame
{
	public SwingDirection()
	{
		setSize(400,300);
		setLocation(100,100);
		
		Container cc = this.getContentPane();		
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
		cc.setLayout(new BorderLayout());
		cc.add(new JButton("tool bar"), BorderLayout.NORTH);
		cc.add(new JButton("status bar"), BorderLayout.SOUTH);
		cc.add(new JButton(" tree "), BorderLayout.WEST);
		cc.add(new JButton("xxxx"), BorderLayout.CENTER);
		
	}
	
	public static void main(String[] args)
	{
		SwingDirection a = new SwingDirection();
		a.setVisible(true);
	}
}