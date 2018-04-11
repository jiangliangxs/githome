//package test.sort;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//
//import org.apache.commons.collections.ComparatorUtils;
//import org.apache.commons.collections.comparators.ComparatorChain;
//import org.springframework.util.comparator.ComparableComparator;
//
//public class CompareTipA  {       
//    private int  id;       
//    private String  name;       
//    private String age;       
//  
//    @SuppressWarnings("unchecked")   
//    public static void main(String []args) {       
//  
//        //在列表中加入若干CompareTipA对象   
//        ArrayList<Object> list = new ArrayList<Object>();       
//        list.add(new CompareTipA(1,"3","五"));       
//        list.add(new CompareTipA(1,"1","六"));       
//        list.add(new CompareTipA(1,"5","二"));       
//        list.add(new CompareTipA(1,"4","四"));       
//        list.add(new CompareTipA(4,"2","一"));       
//  
//        //创建一个排序规则   
//        Comparator mycmp = ComparableComparator.getInstance();       
//        mycmp = ComparatorUtils.nullLowComparator(mycmp);  //允许null          
//        mycmp = ComparatorUtils.reversedComparator(mycmp); //逆序          
//  
//        //声明要排序的对象的属性，并指明所使用的排序规则，如果不指明，则用默认排序   
//        ArrayList<Object> sortFields = new ArrayList<Object>();       
//        sortFields.add(new BeanComparator("id", mycmp)); //id逆序  (主)       
//        sortFields.add(new BeanComparator("name"));      //name正序 (副)    
//  
//        //创建一个排序链   
//        ComparatorChain multiSort = new ComparatorChain(sortFields);    
//  
//        //开始真正的排序，按照先主，后副的规则   
//        Collections.sort(list,multiSort);       
//  
//        for (int i =0;i<list.size();i++) {       
//            System.out.println(list.get(i));    //输出       
//        }       
//        /**  
//         * 输出结果如下:  
//         * [id=4,name=2,age=一]  
//         * [id=1,name=1,age=六]  
//         * [id=1,name=3,age=五]  
//         * [id=1,name=4,age=四]  
//         * [id=1,name=5,age=二]  
//         */  
//    }       
//  
//    public CompareTipA(int id, String name,String age) {       
//        this.id = id;       
//        this.name = name;       
//        this.age = age;       
//    }       
//  
//    public int getId() {       
//        return this.id;       
//    }       
//  
//    public void setId(int id) {       
//        this.id = id;       
//    }       
//  
//    public String getName() {       
//        return this.name;       
//    }       
//  
//    public void setName(String name) {       
//        this.name = name;       
//    }       
//  
////    public int compareTo(Object o) {       
////        return this.comparator.compare(this, o);       
////    }       
//  
//    public String toString() {       
//        return "[id=" + this.id + ",name=" + this.name +",age=" + this.age +"]";       
//    }       
//  
//    public String getAge() {       
//        return age;       
//    }       
//  
//    public void setAge(String age) {       
//        this.age = age;       
//    }       
//}  
//
