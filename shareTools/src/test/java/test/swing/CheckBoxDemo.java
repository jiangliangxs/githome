package test.swing;

/**
 * 
 */

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * @author shyboy(chao.shen@duoguo.cn)
 * 
 */
@SuppressWarnings("serial")
public class CheckBoxDemo extends JPanel implements ItemListener {

	private JCheckBox chinCheckBox;// 下巴复选框

	private JCheckBox glassesCheckBox;// 眼睛复选框

	private JCheckBox hairCheckBox;// 头发复选框

	private JCheckBox toothCheckBox;// 牙齿复选框

	private JPanel topPanel;

	private JPanel bottomPanel;

	private StringBuilder choices;

	private JLabel pictureLabel;

	/**
	 * 构造方法
	 */
	public CheckBoxDemo() {
		chinCheckBox = new JCheckBox("下巴");
		chinCheckBox.setMnemonic(KeyEvent.VK_1);// 快捷键ALT+1
		chinCheckBox.setSelected(true);

		glassesCheckBox = new JCheckBox("眼睛");
		glassesCheckBox.setMnemonic(KeyEvent.VK_2);// 快捷键ALT+2
		glassesCheckBox.setSelected(true);

		hairCheckBox = new JCheckBox("头发");
		hairCheckBox.setMnemonic(KeyEvent.VK_3);
		hairCheckBox.setSelected(true);

		toothCheckBox = new JCheckBox("牙齿");
		toothCheckBox.setMnemonic(KeyEvent.VK_4);
		toothCheckBox.setSelected(true);

		chinCheckBox.addItemListener(this);
		glassesCheckBox.addItemListener(this);
		hairCheckBox.addItemListener(this);
		toothCheckBox.addItemListener(this);

		topPanel = new JPanel(new GridLayout(1, 4));
		topPanel.add(chinCheckBox);
		topPanel.add(glassesCheckBox);
		topPanel.add(hairCheckBox);
		topPanel.add(toothCheckBox);

		choices = new StringBuilder("cght");
		pictureLabel = new JLabel();
		updatePicture();

		bottomPanel = new JPanel(new FlowLayout());
		bottomPanel.add(pictureLabel);

		setLayout(new BorderLayout());
		this.add(topPanel, BorderLayout.NORTH);
		this.add(bottomPanel, BorderLayout.CENTER);
	}

	/**
	 * 更新图片
	 */
	public void updatePicture() {
		ImageIcon imageIcon = createImageIcon("images/geek-" + choices.toString() + ".gif");

		pictureLabel.setIcon(imageIcon);
		pictureLabel.setToolTipText(choices.toString());

		if (null == imageIcon) {
			pictureLabel.setText("无图片");
		} else {
			pictureLabel.setText(null);
		}
	}

	/**
	 * 设置复选框对应的相应图标
	 * 
	 * @param path
	 *            ：图片路径
	 * @return ImageIcon
	 */
	public static ImageIcon createImageIcon(String path) {
		if (null != path && !"".equals(path)) {
			URL url = CheckBoxDemo.class.getResource(path);

			if (null != url) {
				return new ImageIcon(url);
			} else {
				System.err.println("找不到图片的……");
				return null;
			}
		}

		return null;
	}

	/**
	 * 创建并显示相应的界面
	 */
	public static void createAndShowGUI() {
		JFrame jFrame = new JFrame("Swing中的JCheckBox");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 当窗口被关闭时应用程序退出

		CheckBoxDemo checkBoxDemo = new CheckBoxDemo();
		checkBoxDemo.setOpaque(true);

		jFrame.setContentPane(checkBoxDemo);
		jFrame.pack();
		jFrame.setVisible(true);
		jFrame.setSize(500, 300);
		jFrame.setLocation(300, 200);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		int index = 0;
		char c = '-';

		Object source = e.getItemSelectable();
		if (source == chinCheckBox) {
			index = 0;
			c = 'c';
		} else if (source == glassesCheckBox) {
			index = 1;
			c = 'g';
		} else if (source == hairCheckBox) {
			index = 2;
			c = 'h';
		} else if (source == toothCheckBox) {
			index = 3;
			c = 't';
		}

		if (e.getStateChange() == ItemEvent.DESELECTED) {
			c = '-';
		}

		choices.setCharAt(index, c);
		updatePicture();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				createAndShowGUI();

			}
		});

	}

}
