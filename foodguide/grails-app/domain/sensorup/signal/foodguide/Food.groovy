package sensorup.signal.foodguide

class Food {
	FoodGroupCategory foodGroupCategory
	String name
	String servingSize
	
	static belongsTo = [foodGroupCategory: FoodGroupCategory]
	
    static constraints = {
		name nullable: false	//XXX unique: 'foodGroupCategory' - because some data violate this, so need to check business logic
		servingSize nullable: false
    }
	
	@Override
	public String toString() {
		name
	}
}
