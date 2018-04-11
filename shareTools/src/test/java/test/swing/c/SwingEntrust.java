package test.swing.c;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


class SwingEntrustA implements ActionListener
{
	public void actionPerformed(ActionEvent e)
	{
		System.out.println("#@%#^@#^");
	}
}

public class SwingEntrust extends JFrame
{
	public SwingEntrust()
	{
		this.setBounds(100,100,500,400);
		Container cc = this.getContentPane();
		cc.setLayout(new FlowLayout());
		
		JButton b = new JButton("ok");
		cc.add(b);
		
		b.addActionListener(new SwingEntrustA());
		JLabel L1 = new JLabel("ok");
		L1.setForeground(Color.red);
	}
	
	public static void main(String[] args)
	{
		new SwingEntrust().setVisible(true);
	}
}