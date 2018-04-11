package test.swing;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

public class Radio {
	static JFrame frm = new JFrame("radio button");
	static JRadioButton rb1 = new JRadioButton("radio1");
	static JRadioButton rb2 = new JRadioButton("radio2");

	static JTextArea ta = new JTextArea("text area ", 7, 7);

	public static void main(String[] args) {

		rb1.addItemListener(new mylistener());
		rb2.addItemListener(new mylistener());

		frm.setLayout(new FlowLayout(FlowLayout.CENTER));
		frm.setBounds(100, 100, 500, 400);
		frm.setResizable(false);

		//
		ButtonGroup bg = new ButtonGroup();
		bg.add(rb1);
		bg.add(rb2);

		//

		frm.add(rb1);
		frm.add(rb2);
		frm.add(ta);

		frm.setVisible(true);
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	//

	static class mylistener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {

			// 方法1：

			JRadioButton rb = (JRadioButton) e.getSource();
			if (rb == rb1)
				ta.setBackground(Color.red);
			if (rb == rb2)
				ta.setBackground(Color.GREEN);

			// 方法2：

			if (rb1.isSelected())
				ta.setBackground(Color.red);
			if (rb2.isSelected())
				ta.setBackground(Color.GREEN);

		}
	}

}
