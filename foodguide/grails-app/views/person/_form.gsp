<%@ page import="sensorup.signal.foodguide.Person" %>



<div class="fieldcontain ${hasErrors(bean: personInstance, field: 'ages', 'error')} required">
	<label for="ages">
		<g:message code="person.ages.label" default="Ages" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="ages" name="ages.id" from="${sensorup.signal.foodguide.AgeGroup.list()}" optionKey="id" required="" value="${personInstance?.ages?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: personInstance, field: 'gender', 'error')} required">
	<label for="gender">
		<g:message code="person.gender.label" default="Gender" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="gender" name="gender.id" from="${sensorup.signal.foodguide.Gender.list()}" optionKey="id" required="" value="${personInstance?.gender?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: personInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="person.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${personInstance?.name}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: personInstance, field: 'preferredCategories', 'error')} ">
	<label for="preferredCategories">
		<g:message code="person.preferredCategories.label" default="Preferred Categories" />
		
	</label>
	<g:select name="preferredCategories" from="${sensorup.signal.foodguide.FoodGroupCategory.list()}" multiple="multiple" optionKey="id" size="5" value="${personInstance?.preferredCategories*.id}" class="many-to-many"/>

</div>

