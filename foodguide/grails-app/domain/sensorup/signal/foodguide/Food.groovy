package sensorup.signal.foodguide

class Food {
	
	String name
	String servingSize
	
	static belongsTo = [foodGroupCategory: FoodGroupCategory]
	
    static constraints = {
		name nullable: false
		servingSize nullable: false
    }
}
