<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="q" uri="/iqc-tags"%>
<jsp:include page="{jspViewResolverPrefix=jspViewResolverPrefix路径,默认/WEB-INF/page/,前后有分割符}framework/head.jsp" />
<div class="dcSearch">
	<form id="${autoTableId }Query" method="post">
		<table class="tabc">
			<tr>
				<td class="tdName">创建人</td>
				<td class="tdValue"><input class="tdValueInput easyui-validatebox" type="text" name="createdUser" /></td>
				<td class="tdName">修改人</td>
				<td class="tdValue"><input class="tdValueInput easyui-validatebox" type="text" name="modifiedUser" /></td>
				<td class="tdName">创建IP</td>
				<td class="tdValue"><input class="tdValueInput easyui-validatebox" type="text" name="createdIp" /></td>
				<td class="tdSearch" rowspan="3"><a href="javascript:void(0);" class="query easyui-linkbutton" data-options="iconCls:'icon-search',plain:true"><s:text name="globalPage.query" /> </a> <br /> <a href="javascript:void(0);" class=" reset easyui-linkbutton" data-options="iconCls:'icon-reset',plain:true"><s:text name="globalPage.reset" /> </a></td>
			</tr>
			<tr>
				<td class="tdName">修改IP</td>
				<td class="tdValue"><input class="tdValueInput easyui-validatebox" type="text" name="modifiedIp" /></td>
				<td class="tdName">创建开始时间</td>
				<td class="tdValue"><q:dateTime name="createdDateStart" isEasyUi="true" property="dateFmt:'yyyy-MM-dd HH:mm:ss'" /></td>
				<td class="tdName">创建结束时间</td>
				<td class="tdValue"><q:dateTime name="createdDateEnd" isEasyUi="true" property="dateFmt:'yyyy-MM-dd HH:mm:ss'" /></td>
			</tr>
			<tr>
				<td class="tdName">修改开始时间</td>
				<td class="tdValue"><q:dateTime name="modifiedDateStart" isEasyUi="true" property="dateFmt:'yyyy-MM-dd HH:mm:ss'" /></td>
				<td class="tdName">修改结束时间</td>
				<td class="tdValue"><q:dateTime name="modifiedDateEnd" isEasyUi="true" property="dateFmt:'yyyy-MM-dd HH:mm:ss'" /></td>
				<td class="tdName">删除状态</td>
				<td class="tdValue"><input class="isDelete clearCombobox easyui-validatebox" type="text" name="isDelete" style="width:180px" /></td>
			</tr>
		</table>
	</form>
	<script type="text/javascript">
		$(function() {
			//查询
			$('#${autoTableId}Query').find('.query').click(function() {
				if (!$('#${autoTableId}Table').datagrid('options').url) {
					$('#${autoTableId}Table').datagrid('options').url = '${webRoot}{actionNamespace=action命名空间,默认:/project/msthdb,最后无分割符}/{entityNameFirstLower=实体类名,首字母小写,默认:testTable}!datagrid.action';
				}
				zj.queryTable({
					formId : '${autoTableId}Query',
					tableId : '${autoTableId}Table'
				});
			});
			//重置
			$('#${autoTableId}Query').find('.reset').click(function() {
				$('#${autoTableId}Query').find('input').val('');
				basic.common.clearCombox({
					layout : $('#${autoTableId}Query')
				});
			});
			//是否删除
			$('#${autoTableId}Query').find('.isDelete').combobox({
				valueField : 'id',
				textField : 'text',
				editable : false,
				url : '${webRoot}/basic/permissions/constant/constant!combo.action?type=_yn&id=0'
			}).combobox('initClear');
		});
	</script>
</div>

<jsp:include page="{jspViewResolverPrefix=jspViewResolverPrefix路径,默认/WEB-INF/page/,前后有分割符}framework/foot.jsp" />