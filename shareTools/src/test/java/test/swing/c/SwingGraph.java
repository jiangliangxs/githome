package test.swing.c;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*
  直奔主题
*/


/*
class A implements ActionListener
{
	private JButton b;
	
	public A(JButton x)
	{
		b = x;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		b.setBackground(Color.red);
	}
}
*/

public class SwingGraph extends JFrame implements ActionListener
{
	private JButton b;
	private JButton b2;
	
	public void actionPerformed(ActionEvent e)
	{
		/*
		if(e.getSource()==b)
			b.setForeground(Color.red);
		if(e.getSource()==b2)
			b2.setForeground(Color.red);
		*/
		
		//((JButton)e.getSource()).setForeground(Color.green);
		
		if(e.getSource()==b){
			Graphics g = this.getContentPane().getGraphics();  // 此指针不可保存
			g.drawOval(100,100,50,50);
			g.setColor(Color.red);
			g.drawLine(0,0,500,400);
			g.fillOval(200,100,80,50);
			g.drawString("中国人民银行",100,200);
		}
	}

	public SwingGraph()
	{
		this.setBounds(100,100,500,400);
		Container cc = this.getContentPane();
		cc.setLayout(new FlowLayout());
		
		b = new JButton("ok");
		cc.add(b);
		b2 = new JButton("cancel");
		cc.add(b2);
		
		b.addActionListener(this);
		b2.addActionListener(this);
	}
	
	public static void main(String[] args)
	{
		new SwingGraph().setVisible(true);
	}
}