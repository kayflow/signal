class BootStrap {
	def dataLoadService
	
    def init = { servletContext ->
		dataLoadService.loadFoodGuideData()
    }
	
    def destroy = {
    }
}
