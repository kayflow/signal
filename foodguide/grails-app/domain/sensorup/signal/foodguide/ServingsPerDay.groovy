package sensorup.signal.foodguide

class ServingsPerDay {
	
	FoodGroup foodGroup
	Gender gender
	AgeGroup ages
	int minServings
	int maxServings
	
	static belongsTo = [foodGroup: FoodGroup]
	
    static constraints = {
		gender nullable: false
		ages nullable: false
		foodGroup nullable: false, unique: ['gender', 'ages']
		minServings min: 1
		maxServings min: 1
    }
	
	@Override
	public String toString() {
		"${minServings} - ${maxServings} servings of ${foodGroup} for ${gender}, ${ages} daily"
	}
}
