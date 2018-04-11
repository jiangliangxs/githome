package {actionPackage=action包路径,默认:project.msthdb.action};

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import {pageModelPackage=pageModel包路径,默认:project.msthdb.pageModel}.{entityName=实体类名,默认:TestTable}Page;
import {servicePackage=service包路径,默认:project.msthdb.service}.I{entityName=实体类名,默认:TestTable}Service;

import com.opensymphony.xwork2.ModelDriven;
import com.zjsystem.framework.action.BasicAction;

/**
 * {description=描述信息,默认:民生系统测试生成模块}
 * 
 * @author {author=作者,默认:民生系统}
 * @date {date=日期,默认:当前时间}
 * @modifiyNote
 * @version 1.0
 */
@Controller
@Scope("prototype")
@Namespace("{actionNamespace=action命名空间,默认:/project/msthdb,最后无分割符}")
@Action("{entityNameFirstLower=实体类名,首字母小写,默认:testTable}")
public class {entityName=实体类名,默认:TestTable}Action extends BasicAction implements ModelDriven<{entityName=实体类名,默认:TestTable}Page> {
	/**
	 * 添加实体
	 */
	public void add() {
		super.writerJSON({entityNameFirstLower=实体类名,首字母小写,默认:testTable}Service.add(page));
	}

	/**
	 * 更新实体
	 */
	public void update() {
		super.writerJSON({entityNameFirstLower=实体类名,首字母小写,默认:testTable}Service.update(page));
	}

	/**
	 * 跳转至更新页面
	 */
	public String prePareUpdate() {
		page = {entityNameFirstLower=实体类名,首字母小写,默认:testTable}Service.load(page);
		return super.prePareUpdate();
	}

	/**
	 * 删除实体
	 */
	public void del() {
		super.writerJSON({entityNameFirstLower=实体类名,首字母小写,默认:testTable}Service.del(page));
	}

	/**
	 * 查询实体列表
	 */
	public void datagrid() {
		super.writerJSON({entityNameFirstLower=实体类名,首字母小写,默认:testTable}Service.datagrid(page));
	}

	/**
	 * 查询单条记录
	 * 
	 * @return 转到页面的后缀
	 */
	public String load() {
		page = {entityNameFirstLower=实体类名,首字母小写,默认:testTable}Service.load(page);
		return "view";
	}

	private static final long serialVersionUID = 1l;
	private {entityName=实体类名,默认:TestTable}Page page = new {entityName=实体类名,默认:TestTable}Page();

	/**
	 * 获取页面实体对象模型,由Struts自动填充页面值
	 */
	@Override
	public {entityName=实体类名,默认:TestTable}Page getModel() {
		return page;
	}

	/**
	 * 获取页面实体对象
	 * 
	 * @return 页面实体对象
	 */
	public {entityName=实体类名,默认:TestTable}Page getPage() {
		return page;
	}

	@Resource
	private I{entityName=实体类名,默认:TestTable}Service {entityNameFirstLower=实体类名,首字母小写,默认:testTable}Service;
}
