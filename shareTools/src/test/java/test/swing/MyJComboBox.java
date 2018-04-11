package test.swing;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JComboBox;

class itemListener implements ItemListener {
	private JComboBox jcb = new JComboBox();

	public itemListener(JComboBox jcb) {
		this.jcb = jcb;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		String panduan = jcb.getSelectedItem().toString();
		// e.getItem().toString();
		char x = 0;
		if (panduan == "aaa") {
			System.out.println("---aaa---");
		}
		if (panduan == "bbb") {
			System.out.println("---bbb---");
		}
	}
}