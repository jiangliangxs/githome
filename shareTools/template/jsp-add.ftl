<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="q" uri="/iqc-tags"%>
<jsp:include page="{jspViewResolverPrefix=jspViewResolverPrefix路径,默认/WEB-INF/page/,前后有分割符}framework/head.jsp" />
<div class="dcDetail">
	<form id="${autoTableId }add" method="post">
		<table class="tabc">
			<tr>
				<td class="tdName" style="width:10%">创建人</td>
				<td class="tdValue" style="width:40%"><input class="tdValueInput easyui-validatebox" type="text" name="createdUser" /></td>
				<td class="tdName" style="width:10%">修改人</td>
				<td class="tdValue" style="width:40%"><input class="tdValueInput easyui-validatebox" type="text" name="modifiedUser" /></td>
			</tr>
			<tr>
				<td class="tdName">创建IP</td>
				<td class="tdValue"><input class="tdValueInput easyui-validatebox" type="text" name="createdIp" /></td>
				<td class="tdName">修改IP</td>
				<td class="tdValue"><input class="tdValueInput easyui-validatebox" type="text" name="modifiedIp" /></td>
			</tr>
			<tr>
				<td class="tdName">创建时间</td>
				<td class="tdValue"><q:dateTime name="createdDate" isEasyUi="true" property="dateFmt:'yyyy-MM-dd HH:mm:ss'"/></td>
				<td class="tdName">删除状态</td>
				<td class="tdValue"><input class="tdValueInput isDelete clearCombobox easyui-validatebox" type="text" name="isDelete" style="width:180px"/></td>
			</tr>
			<tr>
				<td class="tdOperate" colspan="4" align="center"><a class="add easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true"><s:text name="globalPage.save" /> </a>
				</td>
			</tr>
		</table>
	</form>
	<script type="text/javascript">
		$(function() {
			$('#${autoTableId}add').find('.add').click(function() {
				zj.saveTabByParams({
					$this : $(this),
					formId : '${autoTableId}add',
					submitUrl : '${webRoot}{actionNamespace=action命名空间,默认:/project/msthdb,最后无分割符}/{entityNameFirstLower=实体类名,首字母小写,默认:testTable}!add.action',
					tabId : '${autoTableId}Tabs',
					closeable : true,
					callFun : function() {
						$('#${autoTableId}Table').datagrid('unselectAll');
						$('#${autoTableId}Table').datagrid('uncheckAll');
						$('#${autoTableId}Table').datagrid('reload');
					}
				});
			});
			//是否删除
			$('#${autoTableId}add').find('.isDelete').combobox({
				valueField : 'id',
				textField : 'text',
				editable : false,
				url : '${webRoot}/basic/permissions/constant/constant!combo.action?type=_yn&id=0'
			}).combobox('initClear');
		});
	</script>
</div>

<jsp:include page="{jspViewResolverPrefix=jspViewResolverPrefix路径,默认/WEB-INF/page/,前后有分割符}framework/foot.jsp" />