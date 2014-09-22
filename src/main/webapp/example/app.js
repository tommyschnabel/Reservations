var exampleApp = angular.module('myExampleApp', [
'ngRoute',
'exampleControllers'
]);

exampleApp.config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
      when('/firstPage', {
        templateUrl: 'example/firstPage.html',
        controller: 'myExampleController'
      }).when('/secondPage', {
        templateUrl: 'example/secondPage.html',
        controller: 'mySecondExampleController'
      }).otherwise({
        redirectTo: '/firstPage'
      });
  }]);