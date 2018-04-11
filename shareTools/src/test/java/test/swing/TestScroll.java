package test.swing;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TestScroll extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JMenuBar jb;
	JTextArea ja;
	JScrollPane jsp;
//	JTextArea txaDisplay = new JTextArea(); 
//	JScrollPane scroll = new JScrollPane(txaDisplay); 
//	//把定义的JTextArea放到JScrollPane里面去 
//
//	//分别设置水平和垂直滚动条自动出现 
//	scroll.setHorizontalScrollBarPolicy( 
//	JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
//	scroll.setVerticalScrollBarPolicy( 
//	JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); 
//
//	//分别设置水平和垂直滚动条总是出现 
//	scroll.setHorizontalScrollBarPolicy( 
//	JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); 
//	scroll.setVerticalScrollBarPolicy( 
//	JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
//
//	//分别设置水平和垂直滚动条总是隐藏scroll.setHorizontalScrollBarPolicy( 
//	JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
//	scroll.setVerticalScrollBarPolicy( 
//	JScrollPane.VERTICAL_SCROLLBAR_NEVER); 
	public void setImage() {
		jb = new JMenuBar();
		this.setJMenuBar(jb);
		ja = new JTextArea();
		jsp = new JScrollPane(ja);
		this.setSize(600, 400);
		this.setLayout(new BorderLayout());
		this.add(jsp);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		TestScroll a = new TestScroll();
		a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		a.setImage();
	}
}
