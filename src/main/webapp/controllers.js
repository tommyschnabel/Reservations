var controllers = angular.module('reservationsControllers', []);


//HEADER CONTROLLER
controllers.controller('headerController', ['$scope', '$location', '$http',
  		function ($scope, $location, $http) {
		    $scope.changePage = function(path) {
		        $location.path(path);
		    };
			
		    $scope.testJackson = function() {
			$http({ url: 'http://localhost:8080/reservations/api/login/', method: 'POST', data: { email: 'fake@fakemail.org', password: 'password'}});
		    }
  		}
	]
);


//HOME CONTROLLER
controllers.controller('homeController', ['$scope', '$location',
        function ($scope, $location) {
        }
    ]
);


//LOGIN CONTROLLER
controllers.controller('loginController', ['$scope', '$location',
  		function ($scope, $location) {
  		}
	]
);


//TEST CONTROLLER
controllers.controller('registerController', ['$scope', '$location',
  		function ($scope, $location) {
  		}
	]
);

<<<<<<< HEAD
//TEST CONTROLLER
controllers.controller('testController', ['$scope', '$http',
=======
controllers.controller('headerController', ['$scope', '$http',
>>>>>>> Working on implementing Jackson
  		function ($scope, $http) {
		    $scope.status = 'Nothing has happened yet';
		    $scope.errorMessages = 'None';
			
		    $scope.testJackson = function() {
				$http({ url: 'http://localhost:8080/reservations/api/login/', 
<<<<<<< HEAD
					method: 'PUT', 
=======
					method: 'POST', 
>>>>>>> Working on implementing Jackson
					data: { 
						email: 'fake@fakemail.org', 
						password: 'password'
					}
				}).then(function(results) {
<<<<<<< HEAD
					$scope.status = 'Success';
					$scope.errorMessages = 'None';		
=======
					$scope.status = 'Success';			
>>>>>>> Working on implementing Jackson
				}).catch(function(error) {
					$scope.status = 'Fail';
					$scope.errorMessages = error;
				});
			}
  		}
	]
);
