<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="q" uri="/iqc-tags"%>
<jsp:include page="{jspViewResolverPrefix=jspViewResolverPrefix路径,默认/WEB-INF/page/,前后有分割符}framework/head.jsp" />
<div class="dcDetail">
	<table class="tabc">
		<tr>
			<td class="tdName" style="width:10%">创建人</td>
			<td class="tdValue" style="width:40%">${createdUser }</td>
			<td class="tdName" style="width:10%">修改人</td>
			<td class="tdValue" style="width:40%">${modifiedUser }</td>
		</tr>
		<tr>
			<td class="tdName">创建IP</td>
			<td class="tdValue">${createdIp }</td>
			<td class="tdName">修改IP</td>
			<td class="tdValue">${modifiedIp }</td>
		</tr>
		<tr>
			<td class="tdName">修改时间</td>
			<td class="tdValue">${modifiedDate }</td>
			<td class="tdName">删除状态</td>
			<td class="tdValue">${isDeleteName }</td>
		</tr>
	</table>
</div>

<jsp:include page="{jspViewResolverPrefix=jspViewResolverPrefix路径,默认/WEB-INF/page/,前后有分割符}framework/foot.jsp" />