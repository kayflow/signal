package sensorup.signal.foodguide

class Family {
	String name
	
	static hasMany = [members: Person]
	
    static constraints = {
		name nullable: false, unique: true
    }
}
