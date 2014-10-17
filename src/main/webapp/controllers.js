var controllers = angular.module('reservationsControllers', []);

//HEADER CONTROLLER
controllers.controller('headerController', ['$scope', '$location', '$rootScope',
  		function ($scope, $location, $rootScope) {

            $rootScope.errorMessages = [];

            $scope.clearErrors = function() {
                $rootScope.errorMessages = [];
                $scope.errorMessages = [];
            };

            $scope.logout = function() {
                $rootScope.user = null;
                $location.path('#/home');
            };
  		}
	]
);


//HOME/SEARCH CONTROLLER
controllers.controller('homeController', ['$scope', '$location', '$http', '$rootScope',
        function ($scope, $location, $http, $rootScope) {
            $scope.flightFrom = 'Atlanta';
            $scope.flightTo = 'Chicago';

            $scope.search = function() {
                var startDate = '',
                    endDate = '';

                //Validate cities aren't the same
                if ($scope.flightFrom === $scope.flightTo) {
                    $rootScope.errorMessages.push('You cannot fly to the same city you are departing from');
                    return;
                }

                //Validate dates are entered
                if ($scope.flightDepart === '' || $scope.flightReturn === ''
                    || $scope.flightDepart === undefined || $scope.flightReturn === undefined) {
                    $rootScope.errorMessages.push('One of the date fields is empty');
                    return;
                }

                //Validate dates are in right format
                if ($scope.flightDepart.length !== 10 || $scope.flightReturn.length !== 10
                    || $scope.flightDepart[2] != '/' || $scope.flightDepart[5] != '/'
                    || $scope.flightReturn[2] != '/' || $scope.flightReturn[5] != '/') {
                    $rootScope.errorMessages.push('One of the date fields is not in the format "MM/dd/yyyy"');
                    return;
                }

                //Year
                for (var i = 6; i < 10; i++) {
                    startDate += $scope.flightDepart[i];
                    endDate += $scope.flightReturn[i];
                }

                //Month
                for (var i = 0; i < 2; i++) {
                    startDate += $scope.flightDepart[i];
                    endDate += $scope.flightReturn[i];
                }

                //Day
                for (var i = 3; i < 5; i++) {
                    startDate += $scope.flightDepart[i];
                    endDate += $scope.flightReturn[i];
                }

                //Send search request
                $http({
                    url: '/Reservations/api/reservations/search/',
                    method: 'POST',
                    data: {
                        searchType: 'Date',
                        startCity: $scope.flightFrom,
                        endCity: $scope.flightTo,
                        startDate: startDate,
                        endDate: endDate
                    }
                }).then(function(results) {
                    //Set results
                    if (results.status === 200 && results.data.length > 0) {
                        $rootScope.searchResults = results;
                        $scope.searchResults = results;
                        $location.path('/searchResults');
                        return;
                    } else if (results.status === 500) {
                        //Do nothing because for some reason some requests are happening twice
                        // and it'll throw this back
                    } else if (results === {} || results === undefined || results === ''
                        || results.data === '' || results.data === {} || results.data === undefined) {
                        $rootScope.errorMessages.push('The search results came back empty');
                        $scope.searchResults = results;
                        $rootScope.searchResults = results;
                        return;
                    }
                }).catch(function(error) {
                    $rootScope.errorMessages.push(error);
                });
            };
        }
    ]
);


//SEARCH RESULTS CONTROLLER
controllers.controller('searchResultsController', ['$scope', '$rootScope',
        function($scope, $rootScope) {
//            $scope.data = [ //Dummy data
//                {name: 'tommy', age: 20},
//                {name: 'christine', age: 21}
//            ];

            $scope.data = $scope.searchResults.data || $rootScope.searchResults.data;
            $scope.resultsGridOptions = { data: 'data' };
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
                        $rootScope.errorMessages.push("Could not log in, reason unknown");
                        return;
                    }

                    //Navigate back to home page after login
                    $location.path('#/home');
                }).catch(function(error) {
                    $rootScope.errorMessages.push(error);
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
                    $rootScope.errorMessages.push('Make sure to enter a password and confirm it');
                }

                if ($scope.password !== $scope.confirmPassword) {
                    $rootScope.errorMessages.push("Your passwords don't match");
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
                            $rootScope.errorMessages.push('Registered user correctly. Could not log in, reason unknown');
                            return;
                        }

                        //Then return them to the home page
                        $location.path('#/home');

                    }).catch(function(error) {
                        $rootScope.errorMessages = error;
                    });
                }).catch(function(error) {
                    $rootScope.errorMessages = error;
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
