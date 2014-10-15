var controllers = angular.module('reservationsControllers', []);

//HEADER CONTROLLER
controllers.controller('headerController', ['$scope', '$location', '$http',
  		function ($scope, $location, $http) {
            $scope.register = function() {

            };
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


//REGISTER CONTROLLER
controllers.controller('registerController', ['$scope', '$location',
  		function ($scope, $location) {
  		}
	]
);

//FLIGHT CONTROLLER
controllers.controller('flightController', ['$scope', '$location',
        function ($scope, $location) {
        }
    ]
);

//PASSENGER INFORMATION CONTROLLER
controllers.controller('passengerController', ['$scope', '$location',
        function ($scope, $location) {
        }
    ]
);

//TEST CONTROLLER
controllers.controller('testController', ['$scope', '$http',
  		function ($scope, $http) {
		    $scope.status = 'Nothing has happened yet';
		    $scope.errorMessages = 'None';
			
		    $scope.testJackson = function() {
				$http({ 
                    url: 'http://localhost:8080/reservations/api/login/',
					method: 'POST', 
					data: { 
						email: 'fake@fakemail.org', 
						password: 'password'
					}
				}).then(function(results) {
					$scope.status = 'Success';
					$scope.errorMessages = 'None';
				}).catch(function(error) {
					$scope.status = 'Fail';
					$scope.errorMessages = error;
				});
			}
  		}
	]
);
