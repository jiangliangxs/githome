package test.swing.c;

import java.awt.BorderLayout;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

/**
*
* @author 1
*/
public class ScrollBarExample1 extends JPanel{
   
   JLabel label;
  
   /** Creates a new instance of ScrollBarExample1 */
   public ScrollBarExample1() 
   {
       super(true);
       label = new JLabel();
       setLayout(new BorderLayout());
       
       JScrollBar hbar = new JScrollBar(JScrollBar.HORIZONTAL, 30, 20, 0, 300);
       JScrollBar vbar = new JScrollBar(JScrollBar.VERTICAL, 30, 40, 0, 300);
       
       hbar.setUnitIncrement(2);
       vbar.setBlockIncrement(1);
       
       hbar.addAdjustmentListener(new MyAdjustmentListener());
       vbar.addAdjustmentListener(new MyAdjustmentListener());
       
       add(hbar, BorderLayout.SOUTH);
       add(vbar, BorderLayout.EAST);
       add(label, BorderLayout.CENTER);
   }
     
  class MyAdjustmentListener implements AdjustmentListener
   {
       public void adjustmentValueChanged(AdjustmentEvent e)
       {
           label.setText("" + e.getValue()); 
           repaint();
       }
   }

   public static void main(String s[])
   {
       JFrame frame = new JFrame("Scroll Bar Example");
       ScrollBarExample1 jcrollBar = new ScrollBarExample1();
       
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setContentPane(jcrollBar);
       frame.setSize(200, 200);
       frame.setVisible(true);
   }
}