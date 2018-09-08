package sensorup.signal.foodguide



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class FamilyController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

	FoodRecommendService foodRecommendService
	
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Family.list(params), model:[familyInstanceCount: Family.count()]
    }

    def show(Family familyInstance) {
		
		def recommendation = foodRecommendService.recommendFoodServings(familyInstance)
		
        respond familyInstance, model:[recommendation: recommendation]
    }

    def create() {
        respond new Family(params)
    }

    @Transactional
    def save(Family familyInstance) {
        if (familyInstance == null) {
            notFound()
            return
        }

        if (familyInstance.hasErrors()) {
            respond familyInstance.errors, view:'create'
            return
        }

        familyInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'family.label', default: 'Family'), familyInstance.id])
                redirect familyInstance
            }
            '*' { respond familyInstance, [status: CREATED] }
        }
    }

    def edit(Family familyInstance) {
        respond familyInstance
    }

    @Transactional
    def update(Family familyInstance) {
        if (familyInstance == null) {
            notFound()
            return
        }

        if (familyInstance.hasErrors()) {
            respond familyInstance.errors, view:'edit'
            return
        }

        familyInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Family.label', default: 'Family'), familyInstance.id])
                redirect familyInstance
            }
            '*'{ respond familyInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Family familyInstance) {

        if (familyInstance == null) {
            notFound()
            return
        }

        familyInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Family.label', default: 'Family'), familyInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'family.label', default: 'Family'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
