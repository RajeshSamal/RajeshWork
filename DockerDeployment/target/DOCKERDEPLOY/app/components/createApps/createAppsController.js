var ci = angular.module('createapps', []);
ci.controller('CreateAppsController', ['$scope', '$http','$location', function($scope,$http,$location) {
	
	$scope.submitTheForm = function() {
		 
		 
		$scope.dockerFile =document.getElementById("fileUpload").files[0];
		 $scope.appName = document.getElementById("applicationName").value;
	      var myurl = 'service/deploy/'+$scope.appName;
	        var formData = new FormData();
	        formData.append("file", $scope.dockerFile);
	        return  $http.post(myurl, formData, {
               transformRequest: function(data, headersGetterFunction) {
                   return data;
               },
               headers: { 'Content-Type': undefined }
               }).success(function(data, status) {                       
            	   $location.path("runs");
               }).error(function(data, status) {
                   alert("Error ... " + status);
               });
	      
	        
	        
	       
	};
	$scope.appName= "";
	
	$scope.setAppName = function() {
		 
		 
	      $scope.appName = this.app;
	      
	        
	        
	       
	};
	$scope.appNames = [];

/*var myDataPromise = myService.getData();
myDataPromise.then(function(result) { 
  apps = result;
  console.log(apps);
  
  

  
});
	*/
	 
$http({
    method: 'GET',
    url: 'service/getApp'
  }).success(function(data) { 
    angular.forEach(data, function(app, index) {
        $scope.appNames.push(app);
     
    });
  }); 
    
   
    
}]);

/*ci.factory('myService', function($http) {

    var getData = function() {

        return $http({method:"GET", url:"service/getApp"}).then(function(result){
            return result.data;
        });
    };
    return { getData: getData };
});*/




