package test.swing.c;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class JCheckBox2 implements ItemListener {
	JFrame f = null;
	JCheckBox c4 = null;
	JCheckBox c5 = null;

	JCheckBox2() {
		f = new JFrame("JCheckBox");
		Container contentPane = f.getContentPane();
		contentPane.setLayout(new GridLayout(2, 1));
		JPanel p1 = new JPanel();
		p1.setLayout(new GridLayout(1, 3));
		p1.setBorder(BorderFactory.createTitledBorder("您最喜欢哪一家速食店呢？"));
		JCheckBox c1 = new JCheckBox("麦当劳");
		JCheckBox c2 = new JCheckBox("肯德鸡");
		JCheckBox c3 = new JCheckBox("21世纪");
		p1.add(c1);
		p1.add(c2);
		p1.add(c3);
		JPanel p2 = new JPanel();
		p2.setLayout(new GridLayout(2, 1));
		p2.setBorder(BorderFactory.createTitledBorder("您喜欢哪种程序语言，喜欢的请打勾："));
		c4 = new JCheckBox("JAVA", new ImageIcon(".\\icons\\x.jpg"));
		c5 = new JCheckBox("C++", new ImageIcon(".\\icons\\x.jpg"));
		c4.addItemListener(this);
		c5.addItemListener(this);
		p2.add(c4);
		p2.add(c5);
		contentPane.add(p1);
		contentPane.add(p2);
		f.pack();
		f.show();
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == e.SELECTED) {
			if (e.getSource() == c4)
				c4.setIcon(new ImageIcon(".\\icons\\r.jpg"));
			if (e.getSource() == c5)
				c5.setIcon(new ImageIcon(".\\icons\\r.jpg"));

		} else {
			if (e.getSource() == c4)
				c4.setIcon(new ImageIcon(".\\icons\\x.jpg"));
			if (e.getSource() == c5)
				c5.setIcon(new ImageIcon(".\\icons\\x.jpg"));
		}
	}

	public static void main(String args[]) {
		new JCheckBox2();
	}
}