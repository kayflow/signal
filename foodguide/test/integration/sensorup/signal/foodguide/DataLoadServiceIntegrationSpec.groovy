package sensorup.signal.foodguide

import grails.test.spock.IntegrationSpec

class DataLoadServiceIntegrationSpec extends IntegrationSpec {
	def dataLoadService
	
    def setup() {
    }

    def cleanup() {
    }

    void "check loaded food guide data counts"() {
		when:
			println "food group count: ${FoodGroup.count}"
			println "food group category count: ${FoodGroupCategory.count}"
			println "food group directional statements count: ${DirectionalStatement.count}"
			println "food count: ${Food.count}"
			println "food servings per day count: ${ServingsPerDay.count}"
			println "gender count: ${Gender.count}"
			println "age group count: ${AgeGroup.count}"
			
		then:
			FoodGroup.count == 4
			FoodGroupCategory.count == 9
			DirectionalStatement.count == 10
			Food.count == 155
			ServingsPerDay.count == 64
			Gender.count == 2
			AgeGroup.count == 8
			FoodGroup.list().every { it.categories && it.servingsPerDay && it.directions }
			FoodGroupCategory.list().every { it.foods }
    }
}
