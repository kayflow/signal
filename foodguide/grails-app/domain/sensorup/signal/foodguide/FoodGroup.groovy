package sensorup.signal.foodguide

class FoodGroup {
	
	String fgid
	String name	
	
	static hasMany = [
		categories: FoodGroupCategory,
		servingsPerDay: ServingsPerDay,
		directions: DirectionalStatement]

    static constraints = {
		fgid  nullable: false, unique: true
		name nullable: false
    }
	
	@Override
	public String toString() {
		"($fgid) ${name}"
	}
}
