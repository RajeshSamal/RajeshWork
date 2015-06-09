var ci = angular.module('createruns', []);
ci.controller('CreateRunsController', ['$scope', '$http','$location', function($scope,$http,$location) {
	
	
	
	$scope.vmDetails = [];


	 
$http({
    method: 'GET',
    url: 'service/getVM'
  }).success(function(data) { 
	  angular.forEach(data, function(ip, index) {
		  	item = {}
	        item ["name"] = index;
	        item ["ip"] = ip;
	        $scope.vmDetails.push(item);
	     
	    });
  }); 
    
$scope.dockerUI = function(ip) {
	
	 console.log('ip is ' +  ip);
	 
	 var a = document.createElement("a");
	    a.target = "_blank";
	    a.href = "http://localhost:8080/DockerDeployment/index.html#/ip="+ip;
	    a.click();
     
       
      
};
    
}]);






