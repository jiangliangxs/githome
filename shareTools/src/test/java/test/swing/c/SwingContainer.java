package test.swing.c;

import java.awt.*;
import javax.swing.*;

public class SwingContainer extends JFrame
{
	public SwingContainer()
	{
		setSize(400,300);
		setLocation(100,100);
		
		Container cc = this.getContentPane();		
		// 布局方式：窗口大小变化时，组件？？
		// 1。流布局：组件在窗口中“流动”
		cc.setLayout(new FlowLayout());
		
		
		for(int i=0;i<20;i++){
			JButton b = new JButton("kkkk"+i);
		cc.add(b);
			}
	}
	
	public static void main(String[] args)
	{
		SwingContainer a = new SwingContainer();
		a.setVisible(true);
	}
}