angular.module('requestServices', [])
.factory('requestvm', function ($resource,$http) {
	return{
	    getVms : function() {
	        return $http({
	            url: 'service/getVM',
	            method: 'GET'
	        })
	    }
	 }
  
  
});



var ci = angular.module('createruns', []);
ci.controller('CreateRunsController', ['$scope', '$http','$location','requestvm', function($scope,$http,$location,requestvm) {
	
	$scope.vmDetails = [];
	var a = requestvm.getVms();
	a.success(function(data) { 
		angular.forEach(data, function(ip, index) {
		  	item = {}
	        item ["name"] = index;
	        item ["ip"] = ip;
	        item ["DockerUI"] = "DockerUI";
	        item ["Cadvisor"] = "Cadvisor";
	        item ["Jenkins"] = "Jenkins";
	        $scope.vmDetails.push(item);
	     
	    });
	  });
	
	
	$scope.gridOptions = { 
		    data: 'vmDetails',
		    columnDefs: [
			              
			              {field: 'name', displayName: 'VM Name'},
			              {field: 'ip', displayName: 'IP'},
			              {field: 'DockerUI', displayName: 'DockerUI', cellTemplate: '<div class="ngCellText"><a href="http://{{row.entity[\'ip\']}}:9000/#/">{{row.entity[\'DockerUI\']}}</a></div>'},
			              {field: 'Cadvisor', displayName: 'Cadvisor', cellTemplate: '<div class="ngCellText"><a href="http://{{row.entity[\'ip\']}}:9090/#/">{{row.entity[\'Cadvisor\']}}</a></div>'},
			              {field: 'Jenkins', displayName: 'Jenkins', cellTemplate: '<div class="ngCellText"><a href="http://209.238.153.222/job/CSOP_Plan_{{row.entity[\'name\']}}">{{row.entity[\'Jenkins\']}}</a></div>'}
			          ]
		};
	
	 
	


	 

    
$scope.dockerUI = function(ip) {
	
	 console.log('ip is ' +  ip);
	 
	 var a = document.createElement("a");
	    a.target = "_blank";
	    a.href = "http://localhost:8080/DockerDeployment/index.html#/ip="+ip;
	    a.click();
     
       
      
};
    
}]);







