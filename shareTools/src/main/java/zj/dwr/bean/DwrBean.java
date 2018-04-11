package zj.dwr.bean;

import java.io.Serializable;
import java.util.List;

/**
 * dwr封装类<br>
 * 
 * @version 1.00 （2011/11/08）
 * @author 张军 {@link <a href=http://user.qzone.qq.com/360901061/>张军QQ空间</a>}
 */
public class DwrBean<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> funNameList;

	public List<String> getFunNameList() {
		return funNameList;
	}

	public void setFunNameList(List<String> funNameList) {
		this.funNameList = funNameList;
	}

	/** 传给前台的值 **/
	private T tvalue;

	public T getTvalue() {
		return tvalue;
	}

	public void setTvalue(T tvalue) {
		this.tvalue = tvalue;
	}
	/** 调用前台的函数名 **/

}
