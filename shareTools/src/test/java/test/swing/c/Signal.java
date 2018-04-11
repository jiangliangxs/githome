package test.swing.c;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Signal extends JFrame {
	class panel extends JPanel {
		public void paint(Graphics g) {
			super.paint(g);
			int d = 1;
			for (int i = 0; i < 361; i++) {
				int x = i;
				int y = (int) (Math.sin(x * 3.14 / 180) * 100);
				int y1 = (int) (Math.sin((x + 1) * 3.14 / 180) * 100);
				g.drawLine(x, y * d + 300, x + 1, y1 * d + 300);
			}
			g.drawLine(0, 300, 500, 300);
		}
	}

	public Signal() {
		panel p = new panel(); // 创建一个panel类
		this.add(p); // 把panel加到JFrame中
		this.setSize(600, 600);
		this.setTitle("实验1");
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new Signal();
	}
}