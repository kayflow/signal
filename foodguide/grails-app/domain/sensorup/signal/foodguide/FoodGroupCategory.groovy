package sensorup.signal.foodguide

class FoodGroupCategory {
	
	FoodGroup foodGroup
	String fgcatid
	String name
	
	static belongsTo = [foodGroup: FoodGroup]
	
	static hasMany = [foods: Food]
	
    static constraints = {
		fgcatid  nullable: false, unique: true
		name nullable: false
    }
	
	@Override
	public String toString() {
		"${foodGroup} / ($fgcatid) ${name}"
	}
}
