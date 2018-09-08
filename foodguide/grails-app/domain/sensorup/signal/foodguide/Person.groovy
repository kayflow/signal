package sensorup.signal.foodguide

class Person {
	String name
	Gender gender
	AgeGroup ages
	
	static hasMany = [preferredCategories: FoodGroupCategory]
	
    static constraints = {
    }
}
