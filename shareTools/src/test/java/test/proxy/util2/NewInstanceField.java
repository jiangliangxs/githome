//package test.proxy.util2;
//
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Field;
//import java.lang.reflect.InvocationHandler;
//import java.util.ArrayList;
//import java.util.List;
//
//import test.proxy.util1.InterfaceFactory;
//import test.proxy.util1.test.Son1;
//import zj.reflect.util.ClassUtil;
//
//public class NewInstanceField {
//	public static Object getInstanceObj(Class<?> clazz, boolean selfFlg,
//			Object rtnObj) throws Exception {
//		ClassUtil.setClass(clazz);
//		List<Class<?>> list = new ArrayList<Class<?>>();
//		list = getSuperclass(clazz, list);
//		Object obj =  null;
//		if (selfFlg) {
//			rtnObj = ClassUtil.getInstanceObj();
//			obj = rtnObj;
//		}
//		if (obj == null)
//		 obj = ClassUtil.getInstanceObj();
//
//		for (Class<?> tmpClass : list) {
//			Field[] fields = tmpClass.getDeclaredFields();
//			for (Field field : fields) {
//				Annotation ann = field.getAnnotation(Resource.class);
//				if (ann != null) {
//					field.setAccessible(true);
//					Object objField = field.get(obj);
//					if (objField == null) {
//						try {
//							field.set(obj, field.getType().newInstance());
//						} catch (Exception e) {
//							System.out
//									.println("[\"" + field
//											+ "\"] throws Exception: "
//											+ e.getMessage());
//						}
//					}
//						getInstanceObj(field.getType(),
//								false, obj);
//					System.out.println("objField-->" + field.getName());
//
//				} else {
//					// System.out.println("no have annotation");
//					continue;
//				}
//			}
//		}
//		System.out.println("obj--->" + obj);
//		return rtnObj;
//
//	}
//
//	public static Object getInstanceObj(Class<?> clazz,
//			InvocationHandler handler, Class<?>... classes) throws Exception {
//		ClassUtil.setClass(clazz);
//		List<Class<?>> list = new ArrayList<Class<?>>();
//		list = getSuperclass(clazz, list);
//		Object obj = ClassUtil.getInstanceObj();
//		for (Class<?> tmpClass : list) {
//			Field[] fields = tmpClass.getDeclaredFields();
//			for (Field field : fields) {
//				System.out.println("field.getType()--->"
//						+ field.getType().getSimpleName());
//				Annotation ann = field.getAnnotation(Resource.class);
//				if (ann != null) {
//					field.setAccessible(true);
//					Object objField = field.get(obj);
//					if (objField == null) {
//						try {
//							field.set(obj, field.getType().newInstance());
//						} catch (Exception e) {
//							System.out
//									.println("[\"" + field
//											+ "\"] throws Exception: "
//											+ e.getMessage());
//						}
//					}
//					getInstanceObj(field.getType(), handler, classes);
//				} else {
//					// System.out.println("no have annotation");
//					continue;
//				}
//			}
//		}
//		obj = InterfaceFactory.getInterface(obj, NewInstanceField.class
//				.getClassLoader(), new Son1(obj), classes);
//		return obj;
//
//	}
//
//	private static List<Class<?>> getSuperclass(Class<?> clazz,
//			List<Class<?>> clazzList) {
//		if (clazzList == null)
//			return new ArrayList<Class<?>>();
//		if (clazzList.size() == 0 || clazzList.isEmpty())
//			clazzList.add(clazz);
//		if ("java.lang.Object".equals(clazz.getSuperclass().getName())) {
//			return clazzList;
//		}
//		clazzList.add(clazz.getSuperclass());
//		return getSuperclass(clazz.getSuperclass(), clazzList);
//	}
//
//}
