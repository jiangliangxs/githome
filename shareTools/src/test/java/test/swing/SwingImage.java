package test.swing;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.html.ImageView;

public class SwingImage extends JFrame {
	private Icon myIcon;
	private JPanel myPanel;
	private JLabel myLabel;
	private JButton myButton;
	private Container c;

	public SwingImage() {
		c = getContentPane();
		myIcon = new ImageIcon("E:\\document\\ico\\cycle-red.jpg");
		myPanel = new JPanel();
		myPanel.setLayout(new BorderLayout());
		myButton = new JButton("这里是按钮显示icon", new ImageIcon(SwingImage.class.getResource("cycle-red.gif"))); // 这里把icon图作为图标
		myLabel = new JLabel("这里是JLable显示icon", myIcon, JLabel.LEFT);
		myPanel.add(myButton, BorderLayout.SOUTH);
		myPanel.add(myLabel, BorderLayout.CENTER);
		myPanel.setPreferredSize(new Dimension(100, 100));
		c.add(myPanel);
		setBounds(100, 100, 400, 300);
		setVisible(true);
	}

	public static void main(String args[]) {
		SwingImage app = new SwingImage();
	}
}