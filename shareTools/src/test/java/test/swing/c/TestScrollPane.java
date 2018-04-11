package test.swing.c;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class TestScrollPane extends JFrame{

    public static void main(String args[]) {

        TestScrollPane theApp = new TestScrollPane(320, 400);
    }
    
    public TestScrollPane(int xPixels, int yPixels){
        super("Add Image");
        JPanel panelContent = new JPanel(new FlowLayout(FlowLayout.LEFT, 1, 1));
        
        JScrollPane scrollPane = new JScrollPane(panelContent);   
        scrollPane.setPreferredSize(new Dimension(xPixels - 5, yPixels));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        int height = 55;
        for (int i = 0; i < 10; i++) {
            JLabel iconType = new JLabel();
            iconType.setIcon(new ImageIcon("icon/pic.png"));
            panelContent.add(iconType);
            height += 55;
        }
        panelContent.setPreferredSize(new Dimension(xPixels - 5, height));
        this.add(scrollPane, BorderLayout.CENTER);
        
        setSize(xPixels, yPixels);   
        setVisible(true);   
    }
}