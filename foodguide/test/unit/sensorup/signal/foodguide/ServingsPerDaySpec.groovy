package sensorup.signal.foodguide

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(ServingsPerDay)
class ServingsPerDaySpec extends Specification {

    def setup() {
		mockDomain(FoodGroup, [
			[id:1, fgid:'ts', name:'Test 1'],
		])
		mockDomain(Gender, [
			[id:1, name:'Male'],
			[id:2, name:'Female'],
		])
		mockDomain(AgeGroup, [
			[id:1, name:'1 to 30'],
			[id:2, name:'31+'],
		])
    }

    def cleanup() {
    }

    void "servings per day should validate"() {		
		when:
		
			def sperday = new ServingsPerDay(
				gender: Gender.get(1), 
				ages: AgeGroup.get(1), 
				foodGroup: FoodGroup.get(1),
				minServings: 5, 
				maxServings: 5)
			
		then:
			sperday.validate()
    }
}
