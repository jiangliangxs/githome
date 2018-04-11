package test.swing.c;

import java.awt.Color;

import javax.swing.JApplet;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.UIManager;

public class Tree extends JApplet {

	public void init() {
		UIManager.put("Tree.background", Color.LIGHT_GRAY);
		UIManager.put("Tree.textBackground", Color.LIGHT_GRAY);
		UIManager.put("Tree.line", Color.red);
		UIManager.put("ScrollBar.width", new Integer(20));
		getContentPane().add(new JScrollPane(new JTree()));
		// UIManager.put("Button.foreground", Color.red);
		// getContentPane().add(new JTree());
		// getContentPane().add(new JButton());
		// getContentPane().add(new JScrollPane(new JTree()));

	}
}