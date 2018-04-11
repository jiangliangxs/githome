package {servicePackage=service包路径,默认:project.msthdb.service};

import java.util.Map;

import javax.jws.WebService;

import {pageModelPackage=pageModel包路径,默认:project.msthdb.pageModel}.{entityName=实体类名}Page;
import zj.page.bean.Datagrid;

/**
 * {description=描述信息,默认:民生系统测试生成模块}
 * 
 * @author {author=作者,默认:民生系统}
 * @date {date=日期,默认:当前时间}
 * @modifiyNote
 * @version 1.0
 */
@WebService
public interface I{entityName=实体类名}Service {
	/**
	 * 添加实体
	 * 
	 * @param p_page
	 *            页面实体
	 * @return 写入页面结果
	 */
	public Map<String, Object> add({entityName=实体类名}Page p_page);

	/**
	 * 更新实体
	 * 
	 * @param p_page
	 *            页面实体
	 * @return 写入页面结果
	 */
	public Map<String, Object> update({entityName=实体类名}Page p_page);

	/**
	 * 删除实体
	 * 
	 * @param p_page
	 *            页面实体
	 * @return 写入页面结果
	 */
	public Map<String, Object> del({entityName=实体类名}Page p_page);

	/**
	 * 查询实体列表
	 * 
	 * @param p_page
	 *            页面实体
	 * @return 写入页面结果
	 */
	public Datagrid<{entityName=实体类名}Page> datagrid({entityName=实体类名}Page p_page);

	/**
	 * 查询实体
	 * 
	 * @param p_page
	 *            页面实体
	 * @return 结果实体
	 */
	public {entityName=实体类名}Page load({entityName=实体类名}Page p_page);
}
