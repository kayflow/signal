package sensorup.signal.foodguide

import java.util.List;

import grails.transaction.Transactional

@Transactional(readOnly=true)
class FoodRecommendService {

    FoodRecommendation recommendFoodServings(Gender gender, AgeGroup ages, FoodPreferences preferences) {
		assert gender
		assert ages
		assert preferences
		
		def servingsPerDays = recommendServings(gender, ages)
		
		def prefsPerGroup = preferences.categories.groupBy { it.foodGroup }
		
		def servings = servingsPerDays.collect { ServingsPerDay svsPerDay ->
			def foods = recommendFood(svsPerDay.foodGroup, prefsPerGroup[svsPerDay.foodGroup])
			
			selectFoodServings(foods, svsPerDay.minServings, svsPerDay.maxServings)
		}.flatten()
		
		return new FoodRecommendation(foodServings: servings)
    }
	
	List<ServingsPerDay> recommendServings(Gender gender, AgeGroup ages) {
		assert gender
		assert ages
		
		ServingsPerDay.findAllWhere(gender: gender, ages: ages)
	}
	
	List<Food> recommendFood(FoodGroup foodGroup, List<FoodGroupCategory> preferences) {		
		def categories = preferences.grep {	it.foodGroup.fgid == foodGroup.fgid }
			?: foodGroup.categories
		
		categories.foods.flatten()
	}
	
	List<FoodServing> selectFoodServings(List<Food> foods, int minServings, int maxServings) {
		if (! foods) return []
		
		//TODO selection of food among candidate is up to some strategy, 
		//	like food varity or convenience. we could provide this strategies as option later.
		
		// simple strategy of picking 1 food only
		
		[new FoodServing(food: foods[0], servings: minServings)]	
	}
}
