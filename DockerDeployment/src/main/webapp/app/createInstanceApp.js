angular.module('createInstanceui', ['ngRoute','createinstance','createapps','createruns'])
    .config(['$routeProvider', function ($routeProvider) {
        'use strict';
        $routeProvider.when('/topology', {
            templateUrl: 'app/components/createInstance/createInstanceMaster.html',
            controller: 'CreateInstanceController'
        });
        $routeProvider.when('/apps', {
            templateUrl: 'app/components/createApps/createAppsMaster.html',
            controller: 'CreateAppsController'
        });
        $routeProvider.when('/runs', {
            templateUrl: 'app/components/createRuns/createRunsMaster.html',
            controller: 'CreateRunsController'
        });
        $routeProvider.otherwise({redirectTo: '/'});
    }]);