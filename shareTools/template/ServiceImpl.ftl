package {serviceImplPackage=serviceImpl包路径,默认:project.msthdb.serviceImpl};

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.zjsystem.framework.dao.util.DaoUtil;

import project.msthdb.mapper.{entityName=实体类名,默认:TestTable}Mapper;
import project.msthdb.model.{entityName=实体类名,默认:TestTable};
import project.msthdb.pageModel.{entityName=实体类名,默认:TestTable}Page;
import {servicePackage=service包路径,默认:project.msthdb.service}.I{entityName=实体类名,默认:TestTable}Service;
import project.msthdb.util.BeanTool;
import zj.java.util.JavaUtil;
import zj.message.util.MessageUtil;
import zj.page.bean.Datagrid;

/**
 * {description=描述信息,默认:民生系统测试生成模块}
 * 
 * @author {author=作者,默认:民生系统}
 * @date {date=日期,默认:当前时间}
 * @modifiyNote
 * @version 1.0
 */
@Service
public class {entityName=实体类名,默认:TestTable}Service implements I{entityName=实体类名,默认:TestTable}Service {
	@Resource
	private {entityName=实体类名,默认:TestTable}Mapper {entityNameFirstLower=实体类名,首字母小写,默认:testTable}Mapper;

	@Override
	public Map<String, Object> add({entityName=实体类名,默认:TestTable}Page p_page) {
		Map<String, Object> messages = new HashMap<String, Object>();
		messages.put(MessageUtil.AJAX_MSG, "添加失败");
		messages.put(MessageUtil.AJAX_SUCCESS, MessageUtil.AJAX_FAIL);
		{entityName=实体类名,默认:TestTable} entity = new {entityName=实体类名,默认:TestTable}();
		BeanUtils.copyProperties(p_page, entity);
		{entityNameFirstLower=实体类名,首字母小写,默认:testTable}Mapper.insertSelective(entity);
		messages.put(MessageUtil.AJAX_SUCCESS, MessageUtil.AJAX_SUCCESS);
		messages.put(MessageUtil.AJAX_MSG, "添加数据成功");
		return messages;
	}

	@Override
	public Map<String, Object> update({entityName=实体类名,默认:TestTable}Page p_page) {
		Map<String, Object> messages = new HashMap<String, Object>();
		messages.put(MessageUtil.AJAX_MSG, "更新失败");
		messages.put(MessageUtil.AJAX_SUCCESS, MessageUtil.AJAX_FAIL);
		{entityName=实体类名,默认:TestTable} entity = {entityNameFirstLower=实体类名,首字母小写,默认:testTable}Mapper.selectByPrimaryKey(p_page.getId());
		if (entity == null) {
			messages.put(MessageUtil.AJAX_MSG, "数据不存在");
			return messages;
		} else {
			BeanUtils.copyProperties(p_page, entity);
			{entityNameFirstLower=实体类名,首字母小写,默认:testTable}Mapper.updateByPrimaryKey(entity);
			messages.put(MessageUtil.AJAX_SUCCESS, MessageUtil.AJAX_SUCCESS);
			messages.put(MessageUtil.AJAX_MSG, "更新数据成功");
		}
		return messages;
	}

	@Override
	public Map<String, Object> del({entityName=实体类名,默认:TestTable}Page p_page) {
		Map<String, Object> messages = new HashMap<String, Object>();
		messages.put(MessageUtil.AJAX_SUCCESS, MessageUtil.AJAX_FAIL);
		messages.put(MessageUtil.AJAX_MSG, "操作失败");
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("isDelete", p_page.getIsDelete());
		params.put("ids", Arrays.asList(JavaUtil.split(p_page.getIds(), ",")));
		{entityNameFirstLower=实体类名,首字母小写,默认:testTable}Mapper.updateByMap(params);
		messages.put(MessageUtil.AJAX_SUCCESS, MessageUtil.AJAX_SUCCESS);
		messages.put(MessageUtil.AJAX_MSG, "操作成功");
		return messages;
	}

	@Override
	public Datagrid<{entityName=实体类名,默认:TestTable}Page> datagrid({entityName=实体类名,默认:TestTable}Page p_page) {
		Map<String, Object> params = BeanTool.copyObjToMap(p_page);
		int totalCount = {entityNameFirstLower=实体类名,首字母小写,默认:testTable}Mapper.getCountByMap(params);
		List<{entityName=实体类名,默认:TestTable}Page> pages = new ArrayList<{entityName=实体类名,默认:TestTable}Page>();
		if (totalCount == 0) {
			return new Datagrid<{entityName=实体类名,默认:TestTable}Page>(0, pages);
		}
		List<{entityName=实体类名,默认:TestTable}> records = {entityNameFirstLower=实体类名,首字母小写,默认:testTable}Mapper.selectByMap(params);
		for ({entityName=实体类名,默认:TestTable} record : records) {
			{entityName=实体类名,默认:TestTable}Page page = new {entityName=实体类名,默认:TestTable}Page();
			BeanUtils.copyProperties(record, page);
			page.setIsDeleteName(DaoUtil.queryConstantValue("_yn", page.getIsDelete()));
			pages.add(page);
		}
		return new Datagrid<{entityName=实体类名,默认:TestTable}Page>(totalCount, pages);
	}

	@Override
	public {entityName=实体类名,默认:TestTable}Page load({entityName=实体类名,默认:TestTable}Page p_page) {
		{entityName=实体类名,默认:TestTable} record = {entityNameFirstLower=实体类名,首字母小写,默认:testTable}Mapper.selectByPrimaryKey(p_page.getId());
		if (record != null) {
			BeanUtils.copyProperties(record, p_page);
		}
		p_page.setIsDeleteName(DaoUtil.queryConstantValue("_yn", p_page.getIsDelete()));
		return p_page;
	}

}
