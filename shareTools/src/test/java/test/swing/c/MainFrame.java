package test.swing.c;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class MainFrame extends JFrame {
    
    public final static int MAX_X=620;
    public final static int MAX_Y=480;
    private JMenuBar jmb ;
    private JLabel nameLbl;
    private JLabel sexLbl;
    private JLabel ageLbl;
    private JLabel classLbl;
    private JLabel photoLbl;
    
    private JButton selectJbt;
    private JButton deleteJbt;
    private JButton udpateJbt;
    private JButton insertJbt;
    private JRadioButton maleJrb;
    private JRadioButton femaleJrb;
    private ButtonGroup bg;
    
    private JTextField nameJtf;
    private JTextField ageJtf;
    private JTextField classJtf;
    private JTextField searchJtf;
    
    
    private JTable jtbl;
    private DefaultTableModel dtm; //表格用的数据模型
    private JScrollPane jsp;//存放表格的，表格必须放在里面
    private String fileName; //图片路径
    
    public MainFrame(){
        
        init();
        
        this.setBounds(100, 100,MAX_X,MAX_Y);
        this.setTitle("学生信息管理");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        
    }
    public void init(){
        this.setLayout(null);
        this.add(this.getNameLbl());
        this.add(this.getNameJtf());
        this.add(this.getSexLbl());
        this.getBg();
        this.add(this.getMaleJrb());
        this.add(this.getFemaleJrb());
        this.add(this.getPhotoLbl());
        this.add(this.getAgeLbl());
        this.add(this.getAgeJtf());
        this.add(this.getClassLbl());
        this.add(this.getClassJtf());
        this.add(this.getUdpateJbt());
        this.add(this.getJsp());
    }
    
    
    
    public JMenuBar getJmb() {
        return jmb;
    }
    public void setJmb(JMenuBar jmb) {
        this.jmb = jmb;
    }
    //-------------------------------------name------------------------
    public JLabel getNameLbl() {
        if(nameLbl==null){
            nameLbl = new JLabel("姓名:");
            nameLbl.setBounds(30, 30, 50, 30);
        }
        return nameLbl;
    }
    public void setNameLbl(JLabel nameLbl) {
        this.nameLbl = nameLbl;
    }
    public JLabel getSexLbl() {
        if(sexLbl == null){
            sexLbl = new JLabel("性别:");
            sexLbl.setBounds(250, 30, 50, 30);
        }
        return sexLbl;
    }
    public void setSexLbl(JLabel sexLbl) {
        this.sexLbl = sexLbl;
    }
    //-----------------------------------------age--------------------------
    public JLabel getAgeLbl() {
        if(ageLbl==null){
            ageLbl = new JLabel("年龄:");
            ageLbl.setBounds(30, 100, 50, 30);
        }
        return ageLbl;
    }
    public void setAgeLbl(JLabel ageLbl) {
        this.ageLbl = ageLbl;
    }
    //----------------------------------------class---------------------------------
    public JLabel getClassLbl() {
        if(classLbl == null){
            classLbl = new JLabel("班级:");
            classLbl.setBounds(250, 100, 50, 30);
        }
        return classLbl;
    }
    public void setClassLbl(JLabel classLbl) {
        this.classLbl = classLbl;
    }
    //-----------------------------------------photo------------------------------
    public JLabel getPhotoLbl() {
        if(photoLbl == null){
            photoLbl = new JLabel();
            photoLbl.setBounds(430, 10, 180, 180);
            
            setDefaultPhoto();//设置默认图片
            photoLbl.setToolTipText("点我改图片");
            photoLbl.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent arg0) {
                    // TODO Auto-generated method stub
                
                    JFileChooser chooser = new JFileChooser();//文件选择器
                    FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "请选择图片文件", "png", "jpg");//文件名过滤器
                    chooser.setFileFilter(filter);//给文件选择器加入文件过滤器
                    
                    int returnVal = chooser.showOpenDialog(MainFrame.this);
                    if(returnVal == JFileChooser.APPROVE_OPTION) {
                 
                            File file = chooser.getSelectedFile();//得到文件对象
                            fileName = chooser.getSelectedFile().getName();//得到文件名
                            //得到要存入的路径
                            String newFile = "c:/pic/"+fileName;
                            //得到文件后，上传到我们统一文件夹下，并显示出来
                            //使用二进制流进行操作
                            try {
                                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
                                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(newFile));
                                
                                int i = bis.read();
                                while(i!=-1){
                                    bos.write(i);
                                    i = bis.read();
                                }
                                bos.close();
                                bis.close();
                                //将上传完的图片显示出来
                                photoLbl.setIcon(new ImageIcon(newFile));
                            } catch (FileNotFoundException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                    }

                    
                    
                }
                
                
                
            });
        }
        return photoLbl;
    }

    public void setPhotoLbl(JLabel photoLbl) {
        this.photoLbl = photoLbl;
    }
    public JButton getSelectJbt() {
        return selectJbt;
    }
    public void setSelectJbt(JButton selectJbt) {
        this.selectJbt = selectJbt;
    }
    public JButton getDeleteJbt() {
        return deleteJbt;
    }
    public void setDeleteJbt(JButton deleteJbt) {
        this.deleteJbt = deleteJbt;
    }
    public JButton getUdpateJbt() {
        if(udpateJbt==null){
            udpateJbt = new JButton("修改");
            udpateJbt.setBounds(350, 150, 60, 30);
            udpateJbt.addActionListener(new ActionListener() {
                
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    // TODO Auto-generated method stub
                    String name = nameJtf.getText();
                    String sex = maleJrb.isSelected()?"male":"female";
                    int age =  Integer.parseInt(ageJtf.getText());
                    String clazz = classJtf.getText();
                
                    
                        //更新表格
                        //jtbl.setModel(arg0);
                }
            });
        }
        return udpateJbt;
    }
    public void setUdpateJbt(JButton udpateJbt) {
        this.udpateJbt = udpateJbt;
    }
    public JButton getInsertJbt() {
        return insertJbt;
    }
    public void setInsertJbt(JButton insertJbt) {
        this.insertJbt = insertJbt;
    }
    //------------------------------------------------namejtf----------------------
    public JTextField getNameJtf() {
        if(nameJtf==null){
            nameJtf = new JTextField();
            nameJtf.setBounds(80, 30, 120, 30);
        }
        return nameJtf;
    }
    public void setNameJtf(JTextField nameJtf) {
        this.nameJtf = nameJtf;
    }
    //---------------------------------------------agejtf------------------------------
    public JTextField getAgeJtf() {
        if(ageJtf==null){
            ageJtf = new JTextField();
            ageJtf.setBounds(80, 100, 120, 30);
        }
        return ageJtf;
    }
    public void setAgeJtf(JTextField ageJtf) {
        this.ageJtf = ageJtf;
    }
    public JTextField getClassJtf() {
        if(classJtf == null){
            classJtf = new JTextField();
            classJtf.setBounds(300, 100, 120, 30);
        }
        return classJtf;
    }
    public void setClassJtf(JTextField classJtf) {
        this.classJtf = classJtf;
    }
    public JTextField getSearchJtf() {
        return searchJtf;
    }
    public void setSearchJtf(JTextField searchJtf) {
        this.searchJtf = searchJtf;
    }
    //---------------------------------------------jtbl--------------------
    public JTable getJtbl() {
        if(jtbl == null){
            jtbl = new JTable(this.getDtm());
            jtbl.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent arg0) {
                    // TODO Auto-generated method stub
                    //1.得到选中的行
                    int row = jtbl.getSelectedRow();
                    //2.将选中行的列加入到指定控件中
                    //System.out.println(dtm.getValueAt(jtbl.getSelectedRow(), 0));
                    nameJtf.setText((String)dtm.getValueAt(row, 0));
                    ageJtf.setText(((Integer)dtm.getValueAt(row, 2)).toString());
                    String sex = (String)dtm.getValueAt(row, 1);
                    if(sex.equals("male")){
                        maleJrb.setSelected(true);
                    }else{
                        femaleJrb.setSelected(true);
                    }
                    classJtf.setText((String)dtm.getValueAt(row, 3));
                    
                    photoLbl.setIcon(new ImageIcon("c:/pic/"+(String)dtm.getValueAt(row, 4)));
                    fileName = (String)dtm.getValueAt(row, 4);//存下当前显示的图片的路径
                }
                
                
            });
        }
        return jtbl;
    }
    public void setJtbl(JTable jtbl) {
        this.jtbl = jtbl;
    }
    public DefaultTableModel getDtm() {
        
        Vector cols = new Vector();
        cols.add("姓名");cols.add("性别");cols.add("年龄");cols.add("班级");cols.add("照片");
        
        Vector data = new Vector();
        
        ArrayList alist = new ArrayList();
        for(Object obj : alist){
            Vector v = new Vector();
            data.add(v);
        }
        dtm = new DefaultTableModel(data,cols);
        return dtm;
    }
    public void setDtm(DefaultTableModel dtm) {
        this.dtm = dtm;
    }
    //------------------------------------jsp------------------------
    public JScrollPane getJsp() {
        if(jsp == null){
            jsp = new JScrollPane(this.getJtbl());
            jsp.setBounds(0, 240, MAX_X, 240);
        }
        return jsp;
    }
    public void setJsp(JScrollPane jsp) {
        this.jsp = jsp;
    }
    public static void main(String[] args) {
        
        new MainFrame();
        
    }
    public JRadioButton getMaleJrb() {
        if(maleJrb == null){
            maleJrb = new JRadioButton("男");
            maleJrb.setBounds(300, 30, 60, 30);
        }
        return maleJrb;
    }
    public void setMaleJrb(JRadioButton maleJrb) {
        this.maleJrb = maleJrb;
    }
    public JRadioButton getFemaleJrb() {
        if(femaleJrb == null){
            femaleJrb = new JRadioButton("女");
            femaleJrb.setBounds(360, 30, 60, 30);
        }
        return femaleJrb;
    }
    public void setFemaleJrb(JRadioButton femaleJrb) {
        this.femaleJrb = femaleJrb;
    }
    public ButtonGroup getBg() {
        if(bg == null){
            bg = new ButtonGroup();
            bg.add(this.getMaleJrb());bg.add(this.getFemaleJrb());
        }
        return bg;
    }
    public void setBg(ButtonGroup bg) {
        this.bg = bg;
    }
    //设置photoLbl中的默认照片
    private void setDefaultPhoto() {
        // TODO Auto-generated method stub
        getPhotoLbl().setIcon(new ImageIcon("src/default.jpg"));
    }
}