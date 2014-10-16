var controllers = angular.module('reservationsControllers', []);

//HEADER CONTROLLER
controllers.controller('headerController', ['$scope', '$location', '$rootScope',
  		function ($scope, $location, $rootScope) {
            $scope.logout = function() {
                $rootScope.user = null;
                $location.path('#/home');
            };
  		}
	]
);


//HOME/SEARCH CONTROLLER
controllers.controller('homeController', ['$scope', '$location',
        function ($scope, $location) {
        }
    ]
);


//SEARCH RESULTS CONTROLLER
controllers.controller('searchResultsController', ['$scope',
        function($scope) {
            $scope.resultsGridOptions = { data: [
                                            {name: 'tommy', age: 20},
                                            {name: 'christine', age: 21}
                                        ]};
        }
    ]
);


//LOGIN CONTROLLER
controllers.controller('loginController', ['$scope', '$http', '$location', '$rootScope',
  		function ($scope, $http, $location, $rootScope) {

            $scope.submit = function() {
                $http({
                    url: '/Reservations/api/user/login/',
                    method: 'POST',
                    data: {
                        'email': $scope.email,
                        'password': $scope.password
                    }
                }).then(function(results) {
                    if (results !== null) {
                        $rootScope.user = results.data;
                    } else {
                        $scope.errorMessages = "Could not log in, reason unknown";
                        return;
                    }

                    //Navigate back to home page after login
                    $location.path('#/home');
                }).catch(function(error) {
                    $scope.errorMessages = error;
                });
            };
  		}
	]
);


//REGISTER CONTROLLER
controllers.controller('registerController', ['$scope', '$http', '$location', '$rootScope',
  		function ($scope, $http, $location, $rootScope) {
            $scope.submit = function() {

                //make sure the passwords match
                if ($scope.password === '' || $scope.confirmPassword === '') {
                    $scope.errorMessages = 'Make sure to enter a password and confirm it';
                }

                if ($scope.password !== $scope.confirmPassword) {
                    $scope.errorMessages = "Your passwords don't match";
                    return;
                }

                //make the request to the server to register
                $http({
                    url: '/Reservations/api/user/register',
                    method: 'POST',
                    data: {
                        id: 0, //fake id, 0 shouldn't be a valid id
                        fname: $scope.firstname,
                        lname: $scope.lastname,
                        email: $scope.email,
                        password: $scope.password
                    }
                }).then(function() {

                    //After register has been successful, log the user in
                    $http({
                        url: '/Reservations/api/user/login',
                        method: 'POST',
                        data: {
                            email: $scope.email,
                            password: $scope.password
                        }
                    }).then(function(results) {
                        //If the login was successful, set them as logged in
                        if (results !== null) {
                            $rootScope.user = results.data;
                        } else {
                            $scope.errorMessages = 'Registered user correctly. Could not log in, reason unknown';
                            return;
                        }

                        //Then return them to the home page
                        $location.path('#/home');

                    }).catch(function(error) {
                        $scope.errorMessages = error;
                    });
                }).catch(function(error) {
                    $scope.errorMessages = error;
                });
            };
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
                    url: '/Reservations/api/login/',
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
