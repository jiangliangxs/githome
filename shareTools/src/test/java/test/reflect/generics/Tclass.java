package test.reflect.generics;

import java.util.ArrayList;
import java.util.List;


public abstract class Tclass {
	protected Object retObject;
	@SuppressWarnings("unchecked")
	public <T> T serviceStart(){
		return (T)retObject;
	}
	public <T>void t(){
		List<T> lst = new ArrayList<T>();
		
	}
	public <T>List<T> t1(){
		List<T> lst = new ArrayList<T>();
		return lst;
	}
}