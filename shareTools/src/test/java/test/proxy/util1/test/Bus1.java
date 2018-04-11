package test.proxy.util1.test;



import test.proxy.common.Constants;
import test.proxy.common.InProgressMethod;
import test.proxy.util1.test.Business1;

public class Bus1 implements Business1 {
    @InProgressMethod(excuteMethod=Constants.ExecuteCons.DOBEFORE)
    public void doSomething1() {
    	System.out.println("Bus1 target ...doSomething1");
    }
    
}
