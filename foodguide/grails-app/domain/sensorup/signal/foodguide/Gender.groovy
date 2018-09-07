package sensorup.signal.foodguide

class Gender {
	String name
	
    static constraints = {
		name nullable: false, unique: true
    }
}
