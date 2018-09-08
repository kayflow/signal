<%@ page import="sensorup.signal.foodguide.Family" %>



<div class="fieldcontain ${hasErrors(bean: familyInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="family.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${familyInstance?.name}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: familyInstance, field: 'members', 'error')} ">
	<label for="members">
		<g:message code="family.members.label" default="Members" />
		
	</label>
	<g:select name="members" from="${sensorup.signal.foodguide.Person.list()}" multiple="multiple" optionKey="id" size="5" value="${familyInstance?.members*.id}" class="many-to-many"/>

</div>

