var exampleControllers = angular.module('exampleControllers', []);

exampleControllers.controller('myExampleController', ['$scope', '$location',
  function ($scope, $location) {
  	$scope.message = 'this is the first page';

  	$scope.goToSecondPage = function() {
  		$location.path('/secondPage');
  	};
  }]);

exampleControllers.controller('mySecondExampleController', ['$scope', '$location',
  function ($scope, $location) {
  	$scope.message = 'this is the second page!';

  	$scope.goToFirstPage = function() {
  		$location.path('/firstPage');
  	};
  }]);