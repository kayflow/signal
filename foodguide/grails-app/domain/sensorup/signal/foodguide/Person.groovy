package sensorup.signal.foodguide

class Person {
	String name
	Gender gender
	AgeGroup ages
	
	static hasMany = [preferredCategories: FoodGroupCategory]
	
    static constraints = {
		name nullable: false, unique: true
    }
	
	@Override
	public String toString() {
		name
	}
}
