angular.module('seturl', ['dockerui'])
.controller('SeturlController', ['$scope','Settings','Container', function($scope,Settings,Container) {
    $scope.changeURL = function() {
    	Settings.url=this.value;
    };
}]);
