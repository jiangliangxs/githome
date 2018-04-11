package test.swing.c;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import test.regex.Test4;

@SuppressWarnings(value = "all")
public class TestCheckBox extends JDialog {
	private String[] str;
	private List<JCheckBox> cb;

	public TestCheckBox(String[] str, JFrame f) {
		super(f, "挑选词", true);
		this.str = str;
		final JPanel p = new JPanel();
		final List<String> cj = new ArrayList<String>();
		JButton btnExit = new JButton("确定");
		final JTextField jf = new JTextField(20);
		JButton btnAdd = new JButton("添加");
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JCheckBox jc = new JCheckBox(jf.getText());
				p.add(jc);
				cb.add(jc);
				paintComponents(TestCheckBox.this.getGraphics());
			}
		});
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < cb.size(); i++) {
					if (cb.get(i).isSelected()) {
						System.out.print(cb.get(i).getText() + " ");
					}
				}
			}
		});
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		JPanel pBottom = new JPanel();
		pBottom.setLayout(new FlowLayout(FlowLayout.CENTER));
		pBottom.add(btnExit);
		pBottom.add(jf);
		pBottom.add(btnAdd);

		cb = new ArrayList<JCheckBox>();

		JScrollPane sp = new JScrollPane(p);
		p.setLayout(new GridLayout(str.length / 1, 3));
		int savei = 0;
		for (int i = 0; i < str.length; i++) {
			cb.add(new JCheckBox(str[i]));
			cb.get(i).setSelected(true);
			p.add(cb.get(i));
		}

		add(sp, BorderLayout.CENTER);
		add(pBottom, BorderLayout.SOUTH);
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (size.width - 500) / 2 - 100;
		int y = (size.height - 300) / 2 - 100;
		setBounds(x, y, 550, 450);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {
		String str[] = new String[] { "1", "2", "3", "4", "5", "6", "7" };
		new TestCheckBox(str, null);
	}
}
