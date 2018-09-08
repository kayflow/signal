package sensorup.signal.foodguide

import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(FoodRecommendService)
@Mock([Gender,AgeGroup,FoodGroup,FoodGroupCategory,Food,ServingsPerDay])
class FoodRecommendServiceSpec extends Specification {

    def setup() {
		mockDomain(Gender, [
			[id:1, name:'Male'],
			[id:2, name:'Female'],
		])
		mockDomain(AgeGroup, [
			[id:1, name:'1 to 30'],
			[id:2, name:'31+'],
		])
		mockDomain(FoodGroup, [
			[id:1, fgid:'a', name:'Food group A'],
			[id:2, fgid:'b', name:'Food group B'],
		])
		mockDomain(FoodGroupCategory, [
			[id:1, fgcatid:'1', name:'A 1', foodGroup: FoodGroup.get(1)],
			[id:2, fgcatid:'2', name:'A 2', foodGroup: FoodGroup.get(1)],
			
			[id:3, fgcatid:'3', name:'B 1', foodGroup: FoodGroup.get(2)],
		])
		mockDomain(Food, [
			[id:1, name:'Food 1', servingSize:'1/2 cup', foodGroupCategory: FoodGroupCategory.get(1)],
			[id:2, name:'Food 2', servingSize:'200 mL', foodGroupCategory: FoodGroupCategory.get(1)],
			
			[id:3, name:'Food 3', servingSize:'1 fruits', foodGroupCategory: FoodGroupCategory.get(2)],
			
			[id:4, name:'Food 4', servingSize:'1 slice', foodGroupCategory: FoodGroupCategory.get(3)],
		])
		mockDomain(ServingsPerDay, [
			[id:1, foodGroup: FoodGroup.get(1), gender: Gender.get(1), ages: AgeGroup.get(1), minServings: 1, maxServings: 2],
			[id:2, foodGroup: FoodGroup.get(1), gender: Gender.get(1), ages: AgeGroup.get(2), minServings: 3, maxServings: 3],						
			[id:3, foodGroup: FoodGroup.get(1), gender: Gender.get(2), ages: AgeGroup.get(1), minServings: 4, maxServings: 4],
			[id:4, foodGroup: FoodGroup.get(1), gender: Gender.get(2), ages: AgeGroup.get(2), minServings: 5, maxServings: 5],			
			[id:5, foodGroup: FoodGroup.get(2), gender: Gender.get(1), ages: AgeGroup.get(1), minServings: 6, maxServings: 6],
			[id:6, foodGroup: FoodGroup.get(2), gender: Gender.get(1), ages: AgeGroup.get(2), minServings: 7, maxServings: 7],						
			[id:7, foodGroup: FoodGroup.get(2), gender: Gender.get(2), ages: AgeGroup.get(1), minServings: 8, maxServings: 8],
			[id:8, foodGroup: FoodGroup.get(2), gender: Gender.get(2), ages: AgeGroup.get(2), minServings: 9, maxServings: 9],
		])
		
		println ServingsPerDay.count()
    }

    def cleanup() {
    }
	
	@Unroll
	void "recommend daily food servings for gender #genderId, age group #agesId and preferences #prefCatIds"() {
		given:
			def gender = Gender.get(genderId)
			def ages = AgeGroup.get(agesId)
			def prefCats = prefCatIds.collect { FoodGroupCategory.get(it)}
			def prefs = new FoodPreferences(categories: prefCats)
			
			def servingsPerDays = service.recommendServings(gender, ages)
			
		when:
			def recommendation = service.recommendFoodServings(gender, ages, prefs)
			
		then:
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
			
		where:
			genderId | agesId	|| prefCatIds
				1	 | 1		|| []
				1	 | 1		|| [1]
				1	 | 1		|| [2]
				1	 | 2		|| []
				1	 | 2		|| [1]
				1	 | 2		|| [2]
				2	 | 1		|| []
				2	 | 1		|| [1]
				2	 | 1		|| [2]
				2	 | 2		|| []
				2	 | 2		|| [1]
				2	 | 2		|| [2]
	}
	
	@Unroll
    void "recommend servings based on gender #genderId and age group #agesId"() {
		given:
			def gender = Gender.get(genderId)
			def ages = AgeGroup.get(agesId)			
		when:
			def servings = service.recommendServings(gender, ages)
		then:
			servings*.id == servingsIds
			
		where:
			genderId | agesId	|| servingsIds
				1	 | 1		|| [1,5]
				1	 | 2		|| [2,6]
				2	 | 1		|| [3,7]
				2	 | 2		|| [4,8]
    }
	
	@Unroll	
	void "recommend food #foodIds for food group #foodGroupId filtered with preferences #prefCatIds"() {
		given:
			def foodGroup = FoodGroup.get(foodGroupId)
			def prefs = prefCatIds.collect { FoodGroupCategory.get(it)}
		when:
			def foods = service.recommendFood(foodGroup, prefs)
			
		then:
			foods*.id == foodIds
			foods.every {it.foodGroupCategory.foodGroup.id == foodGroupId}
			
		where:
			foodGroupId | prefCatIds || foodIds
				1		| []		 || [1,2,3]
				2		| []		 || [4]
				1		| [1]		 || [1,2]
				1		| [2]		 || [3]
				2		| [1]		 || [4]	// ignore unrelated preference
				2		| [3]		 || [4]
	}
}
