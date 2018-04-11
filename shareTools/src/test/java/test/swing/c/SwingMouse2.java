package test.swing.c;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

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

public class SwingMouse2 extends JFrame implements ActionListener, MouseListener, MouseMotionListener
{
	private JButton b;
	private JButton b2;
	private JLabel L1;

	public void mouseDragged(MouseEvent e)
	{
		Graphics g = this.getContentPane().getGraphics();
		
		g.setColor(Color.red);
		g.fillOval(e.getX(), e.getY(), 5, 5);
	}
	
	public void mouseMoved(MouseEvent e)
	{
	}
	
	public void mouseClicked(MouseEvent e){ 
		//右键三击
		if(e.getButton()==e.BUTTON3 && e.getClickCount()==3){
			System.out.println("右键三击");
		}
	}
	public void mousePressed(MouseEvent e) { }
	public void mouseReleased(MouseEvent e){ }
	public void mouseEntered(MouseEvent e){ 
		L1.setForeground(Color.blue);
	}
	public void mouseExited(MouseEvent e) { 
		L1.setForeground(Color.gray);
	}

	
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

	public SwingMouse2()
	{
		this.setBounds(100,100,500,400);
		Container cc = this.getContentPane();
		cc.setLayout(new FlowLayout());
		
		L1 = new JLabel("中古人民");
		cc.add(L1);
		L1.setFont(new Font("隶书", Font.BOLD, 40));
		
		b = new JButton("ok");
		cc.add(b);
		b2 = new JButton("cancel");
		cc.add(b2);
		
		b.addActionListener(this);
		b2.addActionListener(this);
		
		L1.addMouseListener(this);
		
		cc.addMouseMotionListener(this);
	}
	
	public static void main(String[] args)
	{
		new SwingMouse2().setVisible(true);
	}
}