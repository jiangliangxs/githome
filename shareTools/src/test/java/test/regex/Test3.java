package test.regex;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test3 {
	 /**
     * {"内容"}替换成表字段信息{问题}替换成SQL
     * @param str
     * @return
     */
	private static String fillStringByArgs(String str) {
		try {
			//[\u4E00-\u9FA5]*[0-9]*[a-z]*)([A-Z]*
			String pile = "\\$([\u4E00-\u9FA5]*[0-9a-zA-Z_-]*)\\$";
			Matcher m = Pattern.compile(pile).matcher(str);
			System.out.println("groupCount:"+m.groupCount());
			while (m.find()) {
				String name = m.group(1);
//				String value="";
//				String rep = m.group().replaceAll("\\$", "\\\\$");
//				rep = rep.replaceAll("\\$", "\\\\$");
//				str = str.replaceFirst(rep, value);
//				System.out.println("str:"+str);
				System.out.println("name:"+name);
			}
		} catch (Exception e) {
			//  自动生成 catch 块
			e.printStackTrace();
			return " 1=1 ";

		}
		return str;
	}
    public static void main(String[] args){
    	String sql="select from  atd_holiday where scids in($scids-aa$) and hrids($beginDate##_{}$)  in $中国人$ and valid_date between ($beginDate$) and $endDate$";
    	fillStringByArgs(sql);

    }

    private static void mm(){
    	Map<String,Object> param = new HashMap<String,Object>();
        /*List<String> list = new ArrayList<String>();
        list.add("10000");
        list.add("20000");
        list.add("30000");*/
        param.put("中国人", new String[]{"1000","2000","3000","4000"});
        param.put("scids", new String[]{"100000","200000","3000","4000"});
        param.put("beginDate", "'2010-03-20'");
        param.put("endDate", "'2010-03-30'");
        String sql="select from  atd_holiday where scids in($scids$) and hrids in ($中国人$) and valid_date between #beginDate# and #endDate#";
        Pattern pattern1 = Pattern.compile("\\$[a-zA-Z]+\\$");
        Matcher matcher1 = pattern1.matcher(sql);
        while(matcher1.find()){
            String paramStr = matcher1.group();
            String paramName=paramStr.replaceAll("\\$", "");
            System.out.println(paramName);
            Object value=param.get(paramName);
            if(value instanceof String){
                sql=sql.replace(paramStr,"'"+value.toString()+"'" );
            }else if(value instanceof Collection){
                Collection collection=(Collection)value;
                StringBuffer sb = new StringBuffer();
                int i=0;
                for(Iterator it=collection.iterator();it.hasNext();i++){
                    Object obj=it.next();
                    sb.append("'"+obj.toString()+"'");
                    if(i<collection.size()-1){
                        sb.append(",");
                    }
                }
                sql=sql.replace(paramStr,sb.toString());
            }else if(value instanceof Object[]){
                Object[] objArray=(Object[])value;
                StringBuffer sb = new StringBuffer();
                for(int i=0;i<objArray.length;i++){
                    sb.append("'"+objArray[i].toString()+"'");
                    if(i<objArray.length-1){
                        sb.append(",");
                    }
                }
                sql=sql.replace(paramStr,sb.toString());
            }
        }

        Pattern pattern2 = Pattern.compile("#[a-zA-Z]+#");
        Matcher matcher2=pattern2.matcher(sql);
        while(matcher2.find()){
            String paraStr=matcher2.group();
            String paramName=paraStr.replace("#", "");
            System.out.println(paramName);
            Object value=param.get(paramName);
            sql=sql.replace(paraStr, value.toString());
        }

        System.out.println(sql);
    }
}
