package test.sort;
import java.util.Arrays;


public class SortUtil1 {
	public static void main(String[] args) {
	int[] ary1 = {1,6,8,4,2,44,222,6};
	//int Comma;//选择排序没有去掉逗号
	int CommaBubble = 0;//使用for each 输出时去掉最后的一个逗号 
	int CommaInsert = 0;//使用for each 输出时去掉最后的一个逗号
	System.out.print("for each 输出排序前数组:"+"\n");
	for(int x : ary1) {
		System.out.print( x +", ");
	}
	
	SelectSoft(ary1);
	System.out.println("\n选择排序（一）：\n");
	System.out.print("for each 输出排序后数组:"+"\n");
	for(int x : ary1) {
		System.out.print( x +", ");
	}
	System.out.println();
	System.out.println("使用Arrays.toString输出：");
	System.out.println(Arrays.toString(ary1));
	SelectSoft1(ary1);
	System.out.println("\n选择排序（二）：\n");
	System.out.print("for each 输出排序后数组:"+"\n");
	for(int x : ary1) {
		System.out.print( x +", ");
	}
	System.out.println();
	System.out.println("使用Arrays.toString输出：");
	System.out.println(Arrays.toString(ary1));
	
	BubbleSoft(ary1); 
	System.out.println("\n冒泡排序（一）：\n");
	System.out.print("for each 输出排序后数组:"+"\n");
	for(int x : ary1) {//for each 的应用，不在最后一行输出“，”
		if(CommaBubble == ary1.length -1) {
			System.out.println(x);
		}
		else {
		System.out.print( x +", ");
		}
		CommaBubble++;
	}
	System.out.println();
	System.out.println("使用Arrays.toString输出：");
	System.out.println(Arrays.toString(ary1));
	
	InsertSoft(ary1);
	System.out.println("\n插入排序（一）：\n");
	System.out.print("for each 输出排序后数组:"+"\n");
	for(int x : ary1) {//for each 的应用，不在最后一行输出“，”
		if(CommaInsert == ary1.length -1) {
			System.out.println(x);
		}
		else {
		System.out.print( x +", ");
		}
		CommaInsert++;
	}
	System.out.println();
	System.out.println("使用Arrays.toString输出：");
	System.out.println(Arrays.toString(ary1));
}
	//插入排序（一）
	public static void InsertSoft(int[] ary1) {
		for (int i = 0; i < ary1.length-1; i++)
		 { 	int m = ary1[i+1];
		 int index = 0;
		 //使用IllegalArgumentExecption抛出异常
		 if(i == ary1.length-3){
			 //throw new IllegalArgumentException("I等于" + (ary1.length - 3) + "啦！中止了");
		 }
			for (int j = i; j >= 0; j--) {
				if(m < ary1[j]) {
					ary1[j+1] = m;
					index = j;
				}
				else {
					break;
				}
				ary1[index] = m;
			}
			
		}
		
	}
	//选择式排序（一）：
	public static void SelectSoft(int[] ary1){
		for (int i = 0; i < ary1.length - 1; i++) {
			int min = ary1[i];
			for (int j = i + 1; j < ary1.length; j++) {
				if(ary1[j] < min){
					min = ary1[j];
					ary1[j] = ary1[i];
					ary1[i] = min;
				}
				}
			}
	}
	//选择排序（二）
	public static void SelectSoft1(int[] ary1){
		for (int i = 0; i < ary1.length - 1; i++) {
			int min = ary1[i];
			int index = i;
			for (int j = i; j < ary1.length - 1 && ary1[j+1] < min; j++) {
//				if(ary1[j+1] < min){
					min = ary1[j+1];
					index = j + 1;
//				}
				ary1[index] = ary1[i];
				ary1[i] = min;
			}
		}
	}
	//冒泡排序 （一）
	public static void BubbleSoft(int[] ary1) {
		for (int i = 0; i < ary1.length-1; i++) {
			for (int j = 0; j < ary1.length - i - 1 && ary1[j] > ary1[j+1]; j++) {
//			if(ary1[j] > ary1[j+1])	{
				int temp = ary1[j+1];
				ary1[j+1] = ary1[j];
				ary1[j] = temp;
//			}
		  }	
		}
	}
}