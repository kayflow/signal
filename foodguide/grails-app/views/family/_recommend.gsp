<%@ page import="sensorup.signal.foodguide.FoodRecommendation" %>

	<g:if test="${recommendation?.foodServings}">
	
		<g:each in="${recommendation?.foodServingsPerGroup}" var="grpServings">
			
			<g:if test="${direction}">
			<span class="property-value" aria-labelledby="preferredCategories-label">
				
				<ul>
				<g:each in="${grpServings.key.directions.sort{it.id}}" var="direction">
					<li>${direction.statement}</li>								
				</g:each>
				</ul>
			</span>
			
			<br/>
			</g:if>
			
			<g:each in="${grpServings.value}" var="serving">
				<span class="property-value" aria-labelledby="preferredCategories-label">
					>> <b>${serving.servings}</b> servings  ${serving.food.servingSize} of <b>${serving.food.name}</b>
				</span>
			</g:each>
			
			<br/>
		
		</g:each>
	</g:if>
