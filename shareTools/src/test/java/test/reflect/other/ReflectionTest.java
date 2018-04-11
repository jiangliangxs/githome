package test.reflect.other;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ReflectionTest {
    public static void main(String[] args) {
        Class c=null;
        try {
            c=Class.forName("java.lang.String");
            System.out.println("package "+c.getPackage().getName()+";");
            System.out.print(Modifier.toString(c.getModifiers())+" ");
            System.out.print("class "+c.getSimpleName()+" ");
            System.out.print("extends "+c.getSuperclass().getSimpleName());
            Class[] inters=c.getInterfaces();
            if(inters.length>0){
                System.out.print(" implements ");
                for(int i=0;i<inters.length;i++){
                    System.out.print(inters[i].getSimpleName());
                    if(i<inters.length-1){
                        System.out.print(",");
                    }
                }
            }
            System.out.println("{");
            printFields(c);
            printMethods(c);
            System.out.println("}");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void printFields(Class c){
        Field[] fs=c.getDeclaredFields();
        for(int i=0;i<fs.length;i++){
            System.out.print("\t"+Modifier.toString(fs[i].getModifiers())+" ");
            System.out.print(fs[i].getType().getSimpleName()+" ");
            System.out.println(fs[i].getName()+";");
        }
    }

    public static void printMethods(Class c){
        Method[] ms=c.getDeclaredMethods();
        for(int i=0;i<ms.length;i++){
            System.out.print("\t"+Modifier.toString(ms[i].getModifiers())+" ");
            System.out.print(ms[i].getReturnType().getSimpleName()+" ");
            System.out.print(ms[i].getName()+"(");
            Class[] paras=ms[i].getParameterTypes();
            for(int j=0;j<paras.length;j++){
                System.out.print(paras[j].getSimpleName()+" arg"+j);
                if(j<paras.length-1){
                    System.out.print(",");
                }
            }
            System.out.print(")");

            Class[] exceps=ms[i].getExceptionTypes();
            if(exceps.length>0){
                System.out.print(" throws ");
                for(int k=0;k<exceps.length;k++){
                    System.out.print(exceps[k].getSimpleName());
                    if(k<exceps.length-1){
                        System.out.print(",");
                    }
                }
            }

            System.out.println("{");
            System.out.println("\t\t...");
            System.out.println("\t}");

        }
    }

}
