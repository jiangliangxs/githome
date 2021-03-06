package test.proxy.test.t0629;
import java.lang.reflect.Method;  
import net.sf.cglib.proxy.Enhancer;  
import net.sf.cglib.proxy.MethodInterceptor;  
import net.sf.cglib.proxy.MethodProxy;  

public class EnhancerDemo {  
    public static void main(String[] args) {  
        Enhancer enhancer = new Enhancer();  
        enhancer.setSuperclass(EnhancerDemo.class);  
        enhancer.setCallback(new MethodInterceptorImpl());  
           
        EnhancerDemo demo = (EnhancerDemo) enhancer.create();  
        demo.test();   
        demo.test();
//        System.out.println(demo);  
    }  
       
    public String test() {  
        System.out.println("EnhancerDemo test()");  
        return "helloworld";
        
    }  

   
    private static class MethodInterceptorImpl implements MethodInterceptor {  
        @Override 
        public Object intercept(Object obj, Method method, Object[] args,  
                MethodProxy proxy) throws Throwable {  
            System.out.println("Before invoke... " + method);  
            Object result = proxy.invokeSuper(obj, args);  
            System.out.println("result--->" + result);
            System.out.println("After invoke..." + method);  
            return result;  
        }            
    }  

} 

