<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:if
	test="${not empty breadcrumbItems and fn:length(breadcrumbItems) gt 0}">
	<div class="container">
		<ul class="breadcrumb transparent">
			<c:forEach items="${breadcrumbItems}" var="breadcrumbItem"
				varStatus="loop">
				<c:choose>
					<c:when test="${loop.index eq fn:length(breadcrumbItems)-1}">
						<li class="active">${breadcrumbItem.key}</li>
					</c:when>
					<c:when test="${loop.index ne fn:length(breadcrumbItems)-1}">
						<li><a href="${contextRoot.concat(breadcrumbItem.value)}">${breadcrumbItem.key}</a></li>
					</c:when>
				</c:choose>
			</c:forEach>
		</ul>
	</div>
</c:if>
