package sensorup.signal.foodguide

import java.io.File;

import grails.transaction.Transactional

@Transactional
class DataLoadService {
	def grailsApplication
	
	private File _dataFolder
	
    def loadFoodGuideData() {
		if (dataBaseEmpty()) { // check for persistent db
			loadFoodGroupCategories()
			loadDirectionalStatement()
			loadFoods()
			loadServingsPerDay()
		}
    }
	
	private loadFoodGroupCategories() {
		def file = new File(dataFolder, "foodgroups-en_ONPP.csv")
		assert file.exists()
		
		file.eachLine(0) { line, i ->	// fgid,foodgroup,fgcat_id,fgcat
			if (i > 0) {
				def fields = parseLine(line)
				assert fields.length == 4
				
				def foodGroup = FoodGroup.findOrSaveWhere(
						fgid: fields[0], name: fields[1])
				
				def category = FoodGroupCategory.findOrSaveWhere(
						foodGroup: foodGroup, fgcatid: fields[2], name: fields[3])
					.save(failOnError:true)
				
				foodGroup.addToCategories(category).save(failOnError:true)
			}
		}
	}
	
	private loadDirectionalStatement() {
		def file = new File(dataFolder, "fg_directional_satements-en_ONPP.csv")
		assert file.exists()
		
		file.eachLine(0) { line, i ->	// fgid,directional-statement
			if (i > 0) {
				def fields = parseLine(line)
				assert fields.length == 2
				
				def foodGroup = FoodGroup.findWhere(fgid: fields[0])
				assert foodGroup
				
				def direction = new DirectionalStatement(foodGroup: foodGroup, statement: fields[1])
					.save(failOnError:true)					
					
				foodGroup.addToDirections(direction).save(failOnError:true)
			}
		}
	}
	
	private loadFoods() {
		def file = new File(dataFolder, "foods-en_ONPP_rev.csv")
		assert file.exists()
		
		file.eachLine(0) { line, i ->	// fgid, fgcat_id, srvg_sz, food,
			if (i > 0) {
				def fields = parseLine(line)
				assert fields.length == 4
				
				def foodGroup = FoodGroup.findWhere(fgid: fields[0])
				def foodCategory = FoodGroupCategory.findWhere(fgcatid: fields[1])
				
				assert foodGroup
				assert foodCategory
				assert foodCategory.foodGroup == foodGroup
				
				def food = new Food(
						foodGroupCategory: foodCategory, 
						servingSize: fields[2], 
						name: fields[3])
					.save(failOnError:true)
				
				foodCategory.addToFoods(food).save(failOnError:true)
			}
		}
	}
	
	private loadServingsPerDay() {
		def file = new File(dataFolder, "servings_per_day-en_ONPP.csv")
		assert file.exists()
		
		def ageGroups = [:]
		
		file.eachLine(0) { line, i ->	// fgid,gender,ages,servings
			if (i > 0) {
				def fields = parseLine(line)
				assert fields.length == 4
				
				def foodGroup = FoodGroup.findWhere(fgid: fields[0])
				assert foodGroup
				
				def gender = Gender.findOrSaveWhere(name: fields[1]).save(failOnError:true)
				def ages = AgeGroup.findOrSaveWhere(name: fields[2]).save(failOnError:true)
				
				def servings = parseIntRange(fields[3])
				
				def servingsPerDay = new ServingsPerDay(foodGroup: foodGroup, gender: gender, ages: ages,
						minServings: servings[0], maxServings: servings[1])
					.save(failOnError:true)
					
				foodGroup.addToServingsPerDay(servingsPerDay).save(failOnError:true)
			}
		}
	}
	
	private def parseIntRange(rangeExpr) {
		def match = rangeExpr =~ /(\d+) to (\d+)/
		if (match) {
			return [1,2].collect {match[0][it] as int}
		}
		def num = rangeExpr.trim() as int
		return [num, num]
	}
	
	private String[] parseLine(line) {
		def tokens = line.split(",")
		def fields = []
		def dqutes = []
		
		tokens.each { tok ->
			if (dqutes) { // double quotes pending
				dqutes << tok
				if (tok.trim().endsWith('"')) {
					fields << dqutes.join(",")
					dqutes = []
				}
			} else {
				if (tok.trim().startsWith('"')) {
					dqutes << tok
				} else {
					fields << tok.trim()
				}
			}
		}
		println fields
		
		return fields
	}
	
	private File getDataFolder() {
		if (!_dataFolder) {
			def dataRes = grailsApplication.mainContext.getResource("WEB-INF/data")
			assert dataRes.exists()
			
			_dataFolder = dataRes.file
		}
		return _dataFolder
	}
	
	private boolean dataBaseEmpty() {
		return FoodGroupCategory.count == 0
	}

}
