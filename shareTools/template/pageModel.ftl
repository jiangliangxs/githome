package {pageModelPackage=pageModel包路径,默认:project.msthdb.pageModel};

import project.msthdb.common.MsthDbBaseModel;

/**
 * {description=描述信息,默认:民生系统测试生成模块}
 * 
 * @author {author=作者,默认:民生系统}
 * @date {date=日期,默认:当前时间}
 * @modifiyNote
 * @version 1.0
 */
public class {entityName=实体类名,默认:TestTable}Page extends MsthDbBaseModel {
	private static final long serialVersionUID = 1L;
	private String isDeleteName;

	public String getIsDeleteName() {
		return isDeleteName;
	}

	public void setIsDeleteName(String isDeleteName) {
		this.isDeleteName = isDeleteName;
	}

}
