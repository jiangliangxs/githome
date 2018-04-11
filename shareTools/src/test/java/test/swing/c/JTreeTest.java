package test.swing.c;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class JTreeTest  extends JFrame{

	 public JTreeTest()
	 {
	  super();
	  setTitle("Swing树组件");
	  setBounds(100,100,350,150);
	  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  DefaultMutableTreeNode root = new DefaultMutableTreeNode("根节点");
	  DefaultMutableTreeNode nodeFirst = new DefaultMutableTreeNode("一级节点A");
	  root.add(nodeFirst);
	  //创建不允许有节点的二级节点
	  DefaultMutableTreeNode nodeSecond = new DefaultMutableTreeNode("二级子节点",false);
	  nodeFirst.add(nodeSecond);
	  root.add(new DefaultMutableTreeNode("二级节点B"));
	  JTree treeRoot = new JTree(root);
	  getContentPane().add(treeRoot,BorderLayout.WEST);
	     //利用根节点创建树模型，采用默认方法
	  DefaultTreeModel treeModelDefault = new DefaultTreeModel(root);   //树模型,判断子结点
	  JTree treeDefault = new JTree(treeModelDefault);
	  getContentPane().add(treeDefault,BorderLayout.CENTER);
	  //利用根节点创建模型,非默认方法：
	  DefaultTreeModel treeModelPointed = new DefaultTreeModel(root,true); //树模型,不判断子结点
	  JTree treePointed= new JTree(treeModelPointed);
	  getContentPane().add(treePointed,BorderLayout.EAST);
	 }
	 /**
	  * @param args
	  */
	 public static void main(String[] args) {
	  // TODO Auto-generated method stub
	  JTreeTest jTreeTest = new JTreeTest();
	  jTreeTest.setVisible(true);
	 }

	}
