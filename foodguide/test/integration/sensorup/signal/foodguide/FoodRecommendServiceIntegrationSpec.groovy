package sensorup.signal.foodguide

import grails.test.spock.IntegrationSpec

class FoodRecommendServiceIntegrationSpec extends IntegrationSpec {
	
	def foodRecommendService
	
    def setup() {
    }

    def cleanup() {
    }

	void "recommend daily food servings for randomly selected gender, ages and preferences"() {
		given:
			def gender = Gender.list()[randomIndex(2)[0]]
			def ages = AgeGroup.list()[randomIndex(AgeGroup.count)[0]]
			
			def categories = FoodGroupCategory.list()
			def categoryCount = FoodGroupCategory.count
			def prefSize = randomIndex(categoryCount)[0] + 1
			def prefCats = randomIndex(categoryCount, prefSize).collect { categories[it] }
			def prefs = new FoodPreferences(categories: prefCats)
			
			def servingsPerDays = foodRecommendService.recommendServings(gender, ages)
			
		when:
			println "=== recommendation for ${gender.name}, ${ages.name}, preferences ${prefs.categories*.name}"
		
			def recommendation = foodRecommendService.recommendFoodServings(gender, ages, prefs)
			
		then:
			servingsPerDays*.foodGroup.fgid as Set == ['vf', 'gr', 'mi', 'me'] as Set
			
			servingsPerDays.every { svsPerDay ->
				println "-- you should eat ${svsPerDay.foodGroup.name} at least ${svsPerDay.minServings} servings"
				
				def grpServings = recommendation.foodServings.grep {
					it.food.foodGroupCategory.foodGroup.fgid == svsPerDay.foodGroup.fgid
				}
				
				grpServings.each {
					println "${it.servings} servings of ${it.food.name} ${it.food.servingSize}"
				}
				
				def sumServings = grpServings.sum {
					it.servings
				}
				sumServings >= svsPerDay.minServings && sumServings <= svsPerDay.maxServings
			}
	}
	
    private int[] randomIndex(int length, int count=1) {
        assert length > 0
        assert count > 0
        
        (0..<count).collect { (new Random().nextInt() % length).abs() }
    }
}
