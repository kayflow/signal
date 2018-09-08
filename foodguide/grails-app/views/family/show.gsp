
<%@ page import="sensorup.signal.foodguide.Family" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'family.label', default: 'Family')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-family" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-family" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list family">
			
				<g:if test="${familyInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="family.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${familyInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${familyInstance?.members}">
				<li class="fieldcontain">
					<span id="members-label" class="property-label"><g:message code="family.members.label" default="Members" /></span>
					
						<g:each in="${familyInstance.members}" var="m">
						<span class="property-value" aria-labelledby="members-label"><g:link controller="person" action="show" id="${m.id}">${m?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<li class="fieldcontain">
					<span id="preferredCategories-label" class="property-label">Family Daily Recommendation</span><br/>

					<g:render template="recommend" model="['recommendation': recommendation, 'direction': true]" />
				</li>
				
				<g:each in="${recommendation.memberRecommendations}" var="memberItem">
				
					<li class="fieldcontain">
						<span id="preferredCategories-label" class="property-label">For member '${memberItem.member}'</span><br/>
	
						<g:render template="recommend" model="['recommendation': memberItem.recommendation, 'direction': false]" />
					</li>				
				
				</g:each>
				
				
				<g:link action="show" id="${familyInstance.id}">[Recommend New]</g:link>			
			
			</ol>
			<g:form url="[resource:familyInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${familyInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
