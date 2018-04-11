package test.swing;

import javax.swing.JComboBox;
import java.awt.BorderLayout;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

public class ComboBoxComboxItem2 extends JFrame implements ActionListener {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public ComboBoxComboxItem2() {
	
    Vector<ComboxItem> model = new Vector<ComboxItem>();
    model.addElement(new ComboxItem("s1", "car"));
    model.addElement(new ComboxItem("s2", "plane"));
    model.addElement(new ComboxItem("s3", "train"));
    model.addElement(new ComboxItem("s4", "boat"));
    JComboBox comboBox;

    // Easiest approach is to just override toString() method
    // of the ComboxItem class

    comboBox = new JComboBox(model);
    // comboBox.setDragEnabled(true);
    comboBox.addActionListener(this);
    getContentPane().add(comboBox, BorderLayout.NORTH);

    // Most flexible approach is to create a custom render
    // to diplay the ComboxItem data

    comboBox = new JComboBox(model);
    // comboBox.setDragEnabled(true);
//    comboBox.setRenderer(new ComboxItemRenderer());
    comboBox.addActionListener(this);
    getContentPane().add(comboBox, BorderLayout.SOUTH);
  }

  public void actionPerformed(ActionEvent e) {
    JComboBox comboBox = (JComboBox) e.getSource();
    ComboxItem item = (ComboxItem) comboBox.getSelectedItem();
    System.out.println(item.getValue() + " : " + item.getText());
  }
//
//  class ComboxItemRenderer extends BasicComboBoxRenderer {
//    public Component getListCellRendererComponent(JList list, Object value, int index,
//        boolean isSelected, boolean cellHasFocus) {
//      super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
//
//      if (value != null) {
//        ComboxItem item = (ComboxItem) value;
//        setText(item.getDescription().toUpperCase());
//      }
//
//      if (index == -1) {
//        ComboxItem item = (ComboxItem) value;
//        setText("" + item.getId());
//      }
//
//      return this;
//    }
//  }

  class ComboxItem {
    private String text;
    private String value;

    public ComboxItem(String value,String text) {
      this.value = value;
      this.text = text;
    }

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
    public String toString(){
    	return this.text;
    }
  }

  public static void main(String[] args) {
    JFrame frame = new ComboBoxComboxItem2();
    frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  }


}


