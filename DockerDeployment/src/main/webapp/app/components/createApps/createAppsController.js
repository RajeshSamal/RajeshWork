var ci = angular.module('createapps', []);
ci.controller('CreateAppsController', ['$scope', '$http','$location', function($scope,$http,$location) {
	
	$scope.submitTheForm = function() {
		 
		 
		$scope.dockerFile =document.getElementById("fileUpload").files[0];
		 $scope.appName = document.getElementById("applicationName").value;
	      var myurl = 'service/deploy/'+$scope.appName+'/'+$scope.tmpName;
	        var formData = new FormData();
	        formData.append("file", $scope.dockerFile);
	        var promise =  $http.post(myurl, formData, {
               transformRequest: function(data, headersGetterFunction) {
                   return data;
               },
               headers: { 'Content-Type': undefined }
               });
	        $location.path("runs");
	      
	        
	        
	       
	};
	$scope.appName= "";
	$scope.tmpName= "";
	
	$scope.setAppName = function() {
		 
		 
	      $scope.appName = this.app;
	      $scope.tmpName = this.app;
	      
	        
	        
	       
	};
	$scope.appNames = [];


	 
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




