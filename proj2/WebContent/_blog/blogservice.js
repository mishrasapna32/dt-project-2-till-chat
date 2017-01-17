/**
 * 
 */
app.factory('BlogService',function($http){
	var blogService={}
	blogService.addPost=function(blogPost){
		console.log('addpost in service')
		return $http.post("http://localhost:8087/Database/blog",blogPost);
	}
	
	blogService.getBlogPosts=function(){
		console.log('getBlogposts in service')
		return $http.get("http://localhost:8087/Database/blog/list")
	}
	blogService.getBlogDetail=function(id){
		console.log('getBlogDetails')
		return $http.get("http://localhost:8087/Database/blog/get/"+ id)
	}
	
	blogService.addComment=function(comment){
		return $http.post("http://localhost:8087/Database/blog/comment",comment)
	}
	blogService.getComments=function(blogId){
		console.log('getcomments -- service')
		return $http.get("http://localhost:8087/Database/blog/getcomments/"+blogId)
	}
	return blogService;
})