web.ns('project.msthdb.{entityNameFirstLower=实体类名,首字母小写,默认:testTable}');
/** 详细信息 */
project.msthdb.{entityNameFirstLower=实体类名,首字母小写,默认:testTable}.view = function(params) {
	var autoTableId = params.autoTableId;
	var id = params.id;
	var title = params.title;
	zj.openTabByParams({
		tabId : autoTableId + 'Tabs',
		title : jq.getDetails(title),
		url : _global.webRoot + '{actionNamespace=action命名空间,默认:/project/msthdb,最后无分割符}/{entityNameFirstLower=实体类名,首字母小写,默认:testTable}!load.action?id=' + id
	});
};