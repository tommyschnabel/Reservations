var exampleApp = angular.module('myExampleApp', [
'ngRoute',
'exampleControllers'
]);

exampleApp.config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
      when('/firstPage', {
        templateUrl: 'firstPage.html',
        controller: 'myExampleController'
      }).when('/secondPage', {
        templateUrl: 'secondPage.html',
        controller: 'mySecondExampleController'
      }).otherwise({
        redirectTo: '/firstPage'
      });
  }]);