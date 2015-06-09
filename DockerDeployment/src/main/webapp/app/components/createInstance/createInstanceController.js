var ci = angular.module('createinstance', []);
ci.controller('CreateInstanceController', ['$scope', '$http','$location',function($scope,$http,$location) {
	
	$scope.submitTheForm = function() {
		 $scope.imageFile =document.getElementById("fileUpload").files[0];
		 $scope.appName = document.getElementById("applicationName").value;
		 console.log('only file is ' +  $scope.imageFile);
	      console.log('file is ' + JSON.stringify( $scope.imageFile));
	      console.log('app name is ' + $scope.appName);
	      var myurl = 'service/createImage/'+$scope.appName;
	        var formData = new FormData();
	        formData.append("file", $scope.imageFile);
	       var promise =   $http.post(myurl, formData, {
                transformRequest: function(data, headersGetterFunction) {
                    return data;
                },
                headers: { 'Content-Type': undefined }
                });
	       $location.path("");
	        
	        
	       
	};
    
}]);

