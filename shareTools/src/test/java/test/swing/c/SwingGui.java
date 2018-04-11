package test.swing.c;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;



public class SwingGui extends JFrame implements ActionListener {
	
	
	private JMenuBar jmb;
	
	private JMenu file;
	
	private JMenu help;
	
	private JMenuItem open;
	private JMenuItem select;
	
	
	public SwingGui(){
		this.setBounds(100, 100, 500, 500);
		
		jmb = new JMenuBar();
		
		file = new JMenu("文件");
		
		help = new JMenu("帮助");
		
		open = new JMenuItem("新建");
		open.addActionListener(this);
		
		select = new JMenuItem("打开"); 
		select.addActionListener(this);
		
		this.setJMenuBar(jmb);
		addSubMenu();
		addMenu();
	}
	
	public void addMenu(){
		jmb.add(file);
		jmb.add(help);
	}
	
	public void addSubMenu(){
		file.add(open);
		file.add(select);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==open){
			Runtime rt = Runtime.getRuntime();
			
			try {
				rt.exec("c:/windows/notepad.exe");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		if(e.getSource()==select){
			try {

				File[] files = null;

				JFileChooser jfc = new JFileChooser();
				jfc.setMultiSelectionEnabled(true);
				int i = jfc.showOpenDialog(null);
				       
				if (i == JFileChooser.APPROVE_OPTION) {
					files = jfc.getSelectedFiles();
					System.out.println(files.length);
					
				}

			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
			
		}
		
	}
	
	public static void main(String[] args) {
		SwingGui g = new SwingGui();
		g.setVisible(true);
	}

}
