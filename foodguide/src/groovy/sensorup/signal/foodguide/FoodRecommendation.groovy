package sensorup.signal.foodguide

class FoodRecommendation {
	List<FoodServing> foodServings
}

class FoodServing {
	Food food
	int servings
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