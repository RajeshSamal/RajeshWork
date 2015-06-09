angular.module('masthead', [])
.controller('MastheadController', ['$scope','Settings', function($scope) {
    $scope.template = 'app/components/masthead/masthead.html';
    $scope.changeURL = function() {
    	Settings.url=this.value;
    };
}]);
