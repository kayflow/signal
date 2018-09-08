package sensorup.signal.foodguide

class DirectionalStatement {

	String statement
	
	static belongsTo = [foodGroup: FoodGroup]

    static constraints = {
		statement nullable: false
    }
	
	@Override
	public String toString() {
		statement
	}
}
