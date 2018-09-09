package sensorup.signal.foodguide

class FoodServing {
	Food food
	int servings
}

class FoodRecommendation {
	List<FoodServing> foodServings
	
	Map<FoodGroup, List<FoodServing>> getFoodServingsPerGroup() {
		foodServings?.groupBy { it.food.foodGroupCategory.foodGroup }
	}
}

class FamilyFoodRecommendation extends FoodRecommendation {
	List<MemberRecommendation> memberRecommendations
}

class MemberRecommendation {
	Person member
	FoodRecommendation recommendation
}

/**
 * Represent food preferences of individual or family.
 * 
 * 0 or multiple categories allowed for each food group, assuming 
 * all categories preferred for a food group without explicit preferences.
 */
class FoodPreferences {
	Collection<FoodGroupCategory> categories = []
	
	static final NoPreferences = new FoodPreferences(categories: [])
}