<%@ page import="sensorup.signal.foodguide.Person" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'person.label', default: 'Person')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-person" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-person" class="content scaffold-show" role="main">
			<h1>${personInstance?.name}</h1>
			
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list person">
			
				<g:if test="${personInstance?.ages}">
				<li class="fieldcontain">
					<span id="ages-label" class="property-label"><g:message code="person.ages.label" default="Ages" /></span>
					
						<span class="property-value" aria-labelledby="ages-label"><g:link controller="ageGroup" action="show" id="${personInstance?.ages?.id}">${personInstance?.ages?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${personInstance?.gender}">
				<li class="fieldcontain">
					<span id="gender-label" class="property-label"><g:message code="person.gender.label" default="Gender" /></span>
					
						<span class="property-value" aria-labelledby="gender-label"><g:link controller="gender" action="show" id="${personInstance?.gender?.id}">${personInstance?.gender?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>

			
				<g:if test="${personInstance?.preferredCategories}">
				<li class="fieldcontain">
					<span id="preferredCategories-label" class="property-label"><g:message code="person.preferredCategories.label" default="Preferred Categories" /></span>
					
						<g:each in="${personInstance.preferredCategories}" var="p">
						<span class="property-value" aria-labelledby="preferredCategories-label"><g:link controller="foodGroupCategory" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
				
				<li class="fieldcontain">
					<span id="preferredCategories-label" class="property-label">Daily Recommendation</span><br/>

					<g:render template="recommend" model="['recommendation': recommendation]" />
				</li>
				
				<g:link action="show" id="${personInstance.id}">[Recommend New]</g:link>
			
			</ol>
			<g:form url="[resource:personInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${personInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
