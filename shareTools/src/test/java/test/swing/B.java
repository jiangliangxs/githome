package test.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class B extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JCheckBox ch;
	JTextArea ta;

	public B() {
		init();
	}

	public void init() {
		setSize(300, 300);
		setLayout(null);
		ta = new JTextArea(10, 20);
		ta.setBounds(20, 20, 100, 100);
		add(ta);
		ch = new JCheckBox("1");
		ch.setBounds(100, 100, 50, 100);
		ch.addActionListener(this);
		add(ch);
	}

	public static void main(String[] args) {
		new B().setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(ch)) {
			if (ch.isSelected()) {
				ta.append("CheckBox" + ch.getText() + "cliked");
			} else {
				ta.append("CheckBox" + ch.getText() + "is No cliked");
			}
		}
	}
}
