package test.io;
//package zj.web.util.parse;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.net.URL;
//import java.net.URLConnection;
//import java.util.Scanner;
//
//import org.htmlparser.NodeFilter;
//import org.htmlparser.Parser;
//import org.htmlparser.filters.TagNameFilter;
//import org.htmlparser.tags.ParagraphTag;
//import org.htmlparser.util.NodeList;
//import org.htmlparser.visitors.HtmlPage;
//
//public class InetUtil {
//	private URL url;
//	private String dir="d:\\story\\";
//	private String fileName;
//	private int port;//端口号
//	private String protocol;//协议
//	private String host;//主机地址
//	public InetUtil(String protocol,String host,int port ){
//		this.protocol=protocol;
//		this.host=host;
//		this.port=port;
//	}
//    public void copyFile(String fileID){
//    	String ID="/story/"+fileID;
//    	fileName=fileID+".txt";
//    	try {
//   		 url=new URL(protocol,host, port, ID);
//   		 URLConnection  conn= url.openConnection();//获取 连接对象
//   		 Scanner scan=new Scanner(conn.getInputStream(),"utf-8");
//   		 StringBuilder str=new StringBuilder();
//   		 while(scan.hasNext()){
//   			str.append(scan.next());
//   		 }
//   		 File dirs=new File(dir);//创建路径
//   		 if(!dirs.exists()){
//   			 dirs.mkdirs();
//   		 }
//   		 File file=new File(dir+fileName);//创建文件
//   		 FileOutputStream out=new FileOutputStream(file);//获取文件输出流
//   		 Parser parser = Parser.createParser(str.toString(), "GBK");//创建html文件解析器
//   		 HtmlPage page = new HtmlPage(parser);//得到html文件解析页
//   		 parser.visitAllNodesWith(page);//遍历所有的节点
//   		 NodeList nodelist = page.getBody();//获取html文件中的body属性中的内容
//   		 NodeFilter filter = new TagNameFilter("P");//过滤html文件中<p>中的内容
//   		 nodelist = nodelist.extractAllNodesThatMatch(filter, true);
//   		 for (int i = 0; i < nodelist.size(); i++) {
//   			 ParagraphTag link = (ParagraphTag) nodelist.elementAt(i);
//   			 String s=link.getStringText()+"\n";
//   			 byte[] b=s.getBytes();
//   			 out.write(b, 0, b.length);
//   			 System.out.println(link.getStringText());
//   			}
//   		 out.close();
//   	} catch (Exception e) {
//   		e.printStackTrace();
//   	    }
//    }
//}
