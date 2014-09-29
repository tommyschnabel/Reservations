angular.module('controllers')
	.controller('headerController', ['$scope', '$location',
  		function ($scope, $location) {
            $scope.changePage = function(path) {
                $location.path(path);
            };
  		}
	]
);