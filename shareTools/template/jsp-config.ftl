<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="q" uri="/iqc-tags"%>
<jsp:include page="{jspViewResolverPrefix=jspViewResolverPrefix路径,默认/WEB-INF/page/,前后有分割符}framework/util/import.jsp" />
<script type="text/javascript" src="${webRoot }{jsViewResolverPrefix=jsViewResolverPrefix路径,默认/page/framework/js/project/,前后有分割符}{entityNameFirstLower=实体类名,首字母小写,默认:testTable}-common.js" charset="utf-8"></script>
<q:tableTag indexTitle="{description=描述信息,默认:民生系统测试生成模块}" queryHeight="130px;" queryUrl="{actionNamespace=action命名空间,默认:/project/msthdb,最后无分割符}/{entityNameFirstLower=实体类名,首字母小写,默认:testTable}!prePareQuery.action" menuCodes="{entityNameFirstLower=实体类名,首字母小写,默认:testTable}MenuCode" toolBarButCodes="add,update,del,undel" rightMenuButCodes="add,update,del,undel" />
<!-- table.id,tab.id -->
<script type="text/javascript">
	$(function() {
		//查询列表
		$('#${autoTableId}Table').datagrid(jQuery.extend(zj.getDataGridInit({
			obj : $('#${autoTableId}Table'),
			rightMenuObj : $('#${autoTableId}RightMenu')
		}), {
			//访问后台获取结果列表
			//url : '${webRoot}{actionNamespace=action命名空间,默认:/project/msthdb,最后无分割符}/{entityNameFirstLower=实体类名,首字母小写,默认:testTable}!datagrid.action',
			idField : 'id',
			sortName : 'modified_date',
			sortOrder : 'desc',
			toolbar : '#${autoTableId}Toolbar',
			//如果为true,则自适应,否则有滚动条显示
			fitColumns : true,
			//固定列模式
			frozenColumns : [ [ {
				title : 'id',
				field : 'id',
				width : 80,
				checkbox : true
			}, {
				title : '查看',
				field : 'viewDetail',
				width : 60,
				formatter : function(value, row, index) {
					return '<a href="javascript:void(0);" title="' + row.id + '" onclick="project.msthdb.{entityNameFirstLower=实体类名,首字母小写,默认:testTable}.view({autoTableId:\'${autoTableId}\',id:\'' + row.id + '\',title:\'' + row.id + '\'})" style="text-decoration:none">查看</a>';
				}
			}, {
				title : '是否删除',
				field : 'isDelete',
				width : 60,
				sortable : true,
				formatter : function(value, row, index) {
					return row.isDeleteName;
				}
			} ] ],
			columns : [ [ {
				title : '创建人',
				field : 'createdUser',
				width : 100,
				sortable : true
			}, {
				title : '修改人',
				field : 'modifiedUser',
				width : 100,
				sortable : true
			}, {
				title : '创建时间',
				field : 'createdDate',
				width : 150,
				sortable : true
			}, {
				title : '修改时间',
				field : 'modifiedDate',
				width : 150,
				sortable : true
			}, {
				title : '创建人IP',
				field : 'createdIp',
				width : 200,
				sortable : true
			}, {
				title : '修改人IP',
				field : 'modifiedIp',
				width : 200,
				sortable : true
			} ] ]
		}));
		//编辑实体
		$('.${autoTableId}update').click(function(e, id) {
			zj.execRow({
				obj : $('#${autoTableId}Table'),
				execFun : function(params) {
					var node = params.node;
					zj.openTabByParams({
						tabId : '${autoTableId}Tabs',
						title : '<s:text name='globalPage.update'/>',
						url : '${webRoot}{actionNamespace=action命名空间,默认:/project/msthdb,最后无分割符}/{entityNameFirstLower=实体类名,首字母小写,默认:testTable}!prePareUpdate.action?autoTableId=${autoTableId}&id=' + node.id
					});
				}
			});
		});
		//删除实体
		$('.${autoTableId}del').click(function(e, id) {
			zj.execRows({
				obj : $('#${autoTableId}Table'),
				execFun : function(params) {
					zj.delRows({
						obj : $('#${autoTableId}Table'),
						appendData : {
							isDelete : '1'
						},
						confirmMsg : '确定要删除所选择的数据吗?',
						submitUrl : '${webRoot}{actionNamespace=action命名空间,默认:/project/msthdb,最后无分割符}/{entityNameFirstLower=实体类名,首字母小写,默认:testTable}!del.action',
						callFun : function() {
							$('#${autoTableId}Table').datagrid('unselectAll');
							$('#${autoTableId}Table').datagrid('uncheckAll');
							$('#${autoTableId}Table').datagrid('reload');
						}
					});
				}
			});
		});
		//恢复删除实体
		$('.${autoTableId}undel').click(function(e, id) {
			zj.execRows({
				obj : $('#${autoTableId}Table'),
				execFun : function(params) {
					zj.delRows({
						obj : $('#${autoTableId}Table'),
						appendData : {
							isDelete : '0'
						},
						confirmMsg : '确定要恢复删除所选择的数据吗?',
						submitUrl : '${webRoot}{actionNamespace=action命名空间,默认:/project/msthdb,最后无分割符}/{entityNameFirstLower=实体类名,首字母小写,默认:testTable}!del.action',
						callFun : function() {
							$('#${autoTableId}Table').datagrid('unselectAll');
							$('#${autoTableId}Table').datagrid('uncheckAll');
							$('#${autoTableId}Table').datagrid('reload');
						}
					});
				}
			});
		});
		//添加实体
		$('.${autoTableId}add').click(function(e, id) {
			zj.openTabByParams({
				tabId : '${autoTableId}Tabs',
				title : '<s:text name='globalPage.add'/>',
				url : '${webRoot}{actionNamespace=action命名空间,默认:/project/msthdb,最后无分割符}/{entityNameFirstLower=实体类名,首字母小写,默认:testTable}!prePareAdd.action?autoTableId=${autoTableId}'
			});
		});
	});
</script>

