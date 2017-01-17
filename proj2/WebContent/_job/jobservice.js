/**
 * 
 */
app.factory('JobService',function($http){
	var jobService={};
	var BASE_URL ="http://localhost:8087/Database"
	jobService.saveJob=function(job){
		return $http.post(BASE_URL + "/postJob" , job);
	}
	jobService.getAllJobs=function(){
		return $http.get(BASE_URL + "/getAllJobs");
	}
	
	jobService.getJobDetail=function(jobId){
		return $http.get(BASE_URL + "/getJobDetail/"+jobId)
	}
	return jobService;
})