package test.swing.c;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class TestFlowLayout {
	JPanel panel = new JPanel();
	JFrame frame = new JFrame();

	public void init() {
		// 当取消下面这句时，JFrame可以根据内容自动缩放(?不知为何)
		// frame.setLayout(new FlowLayout());
		for (int i = 0; i < 500; i++) {
			JLabel label = new JLabel("test" + i);
			label.setPreferredSize(new Dimension(50, 50));
			panel.add(label);
		}
		/*
		 * 当使用JScrollPane添加panel时，JFrame就失去了根据内容自动缩放的功能(?不知为何)
		 */
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		frame.add(scrollPane);
		frame.add(panel);
		frame.setPreferredSize(new Dimension(400, 400));
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new TestFlowLayout().init();
	}
}