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

            $rootScope.errorModal = function ($scope, $modal, $log, $templateCache) {

                $scope.close = function() {
                    $modal.dismiss();
                };
            };
  		}
	]
);


////ERROR MODAL CONTROLLER
//controllers.controller('errorModalController', '$templateCache',
//    function ($scope, $modal, $log, $templateCache) {
//
//        $scope.close = function() {
//            $modal.close();
//        };
//
//    }
//);
//$modal.open({
//    templateUrl: 'errorModal.html',
//    controller: $rootScope.errorModal,
//    size: 'lg',
//    backdrop: 'static'
//});

//HOME/SEARCH CONTROLLER
controllers.controller('homeController', ['$scope', '$location', '$http', '$rootScope',
        function ($scope, $location, $http, $rootScope) {
            $scope.flightFrom = 'Chicago';
            $scope.flightTo = 'Atlanta';

            $scope.search = function() {
                var startDate = '',
                    endDate = '';

                //We'll use this to decide which suggestions page to show
                $rootScope.destination = $scope.flightTo;

                //Validate cities aren't the same
                if ($scope.flightFrom === $scope.flightTo) {
                    $rootScope.errorMessages.push('You cannot fly to the same city you are departing from');
                }

                //Validate dates are entered
                if ($scope.flightDepart === '' || $scope.flightReturn === ''
                    || $scope.flightDepart === undefined || $scope.flightReturn === undefined) {
                    $rootScope.errorMessages.push('One of the date fields in search was empty');
                }

                //Starting date
                startDate += $scope.flightDepart.getFullYear();

                if ($scope.flightDepart.getMonth() < 10) {
                    startDate += '0';
                }
                startDate += $scope.flightDepart.getMonth() + 1;

                if ($scope.flightDepart.getDate() < 10) {
                    startDate += '0';
                }
                startDate += $scope.flightDepart.getDate();

                //Ending date
                endDate += $scope.flightReturn.getFullYear();

                if ($scope.flightReturn.getMonth() < 10) {
                    endDate += '0';
                }
                endDate += $scope.flightReturn.getMonth() + 1;

                if ($scope.flightReturn.getDate() < 10) {
                    endDate += '0';
                }
                endDate += $scope.flightReturn.getDate();

                //Add Hours and Minutes
                startDate += '0000'; //Search from start of the first day
                endDate += '2359';   // to the end of the last day

                //Validate the start date isn't after the end date
                if (startDate > endDate) {
                    $rootScope.errorMessages.push('The starting date was after the ending date in the search');
                }

                if ($rootScope.errorMessages.length > 0) {
                    $location.path('/searchResults');
                }

                //Send search request
                $http({
                    url: '/Reservations/api/reservations/search/',
                    method: 'POST',
                    data: {
                        searchType: 'Date',
                        startCity: $scope.flightFrom.replace(' ', ''),
                        endCity: $scope.flightTo.replace(' ', ''),
                        startDate: startDate,
                        endDate: endDate
                    },
                    timeout: 2000
                }).then(function(results) {
                    //Set results
                    if (results.status === 200) {
                        $rootScope.searchResults = {};
                        $rootScope.searchResults.data = [];
                        if ($scope.roundTrip) {
                            $rootScope.searchResults.data.push({ 
                                placeholder: true, 
                                date: 'Flights going ',
                                startingCity: 'to ',
                                destination: $scope.flightFrom
                            });
                        }
                        $rootScope.searchResults.data = $rootScope.searchResults.data.concat(results.data);
                    } else if (results.status === 500) {
                        //Do nothing because for some reason some requests are happening twice
                        // and it'll throw this back
                    }
                    
                    if ($scope.roundTrip) {
                        $http({
                            url: '/Reservations/api/reservations/search/',
                            method: 'POST',
                            data: {
                                searchType: 'Date',
                                startCity: $scope.flightTo.replace(' ', ''),
                                endCity: $scope.flightFrom.replace(' ', ''),
                                startDate: startDate,
                                endDate: endDate
                            },
                            timeout: 2000
                        }).then(function(results) {
                            $rootScope.searchResults.data.push({ 
                                placeholder: true, 
                                date: 'Flights going ',
                                startingCity: 'to ',
                                destination: $scope.flightTo
                            });
                            $rootScope.searchResults.data = $rootScope.searchResults.data.concat(results.data);
                        $location.path('/searchResults');
                        }).catch(function (error) {
                            $rootScope.errorMessages.push(error);
                        });
                    }
                    
                    if (!$scope.roundTrip) {
                        $location.path('/searchResults');
                    }
                }).catch(function(error) {
                    $rootScope.errorMessages.push(error);
                });
            };
        }
    ]
);


//SEARCH RESULTS CONTROLLER
controllers.controller('searchResultsController', ['$scope', '$rootScope', '$location', '$filter',
        function($scope, $rootScope, $location, $filter) {

            if ($scope.searchResults === undefined) {
                $scope.searchResults = { data: [] };
            }

            //Put it in $rootScope so we can reuse it
            $rootScope.setViewableDate = function(item) {
                var originalDate = item.date,
                    viewableDate = '',
                    temp,
                    timeOfDay = 'AM';
                
                if (item.placeholder) {
                    item.viewableDate = item.date;
                    return;
                }

                //Month
                viewableDate += originalDate[4];
                viewableDate += originalDate[5];
                viewableDate += '/';
                //Day
                viewableDate += originalDate[6];
                viewableDate += originalDate[7];
                viewableDate += '/';
                //Year
                viewableDate += originalDate[0];
                viewableDate += originalDate[1];
                viewableDate += originalDate[2];
                viewableDate += originalDate[3];
                viewableDate += ' ';

                //Hour
                temp = originalDate[8] + originalDate[9];
                if (temp > 12) {
                    temp -= 12;
                    timeOfDay = 'PM';
                }
                viewableDate += temp;
                viewableDate += ':';

                //Minute
                viewableDate += originalDate[10];
                viewableDate += originalDate[11];
                viewableDate += timeOfDay;

                item.viewableDate = viewableDate;
            };

            $rootScope.setViewablePrice = function(item) {
                if (item.placeholder) {
                    return;
                }
                
                item.viewablePrice = $filter('currency')(item.economyPrice);
                item.viewablePrice += ' / ';
                item.viewablePrice += $filter('currency')(item.firstClassPrice);
            };

            angular.forEach($scope.searchResults.data, function(item) {
                $scope.setViewableDate(item);
                $scope.setViewablePrice(item);

                item.class = 'Economy';
            });

            $scope.resultsGridOptions = {
                data: 'searchResults.data',
                selectedItems: [],
                enableRowSelection: true,
                columnDefs: [
                    {
                        field: 'viewableDate',
                        displayName: 'Date',
                        width: '**'
                    },
                    {
                        field: 'startingCity',
                        displayName: 'Starting City',
                        width: '**'
                    },
                    {
                        field: 'destination',
                        displayName: 'Destination',
                        width: '**'
                    },
                    {
                        field: 'airline',
                        displayName: 'Airline',
                        width: '*'
                    },
                    {
                        field: 'viewablePrice',
                        displayName: 'Price (Economy/First Class)',
                        width: '***'
                    },
                    {
                        field: 'seatsInEconomy',
                        displayName: 'Seats (Economy)',
                        width: '**'
                    },
                    {
                        field: 'seatsInFirstClass',
                        displayName: 'Seats (First Class)',
                        width: '**'
                    }
                ]
            };

            $scope.reserve = function() {
                $rootScope.reservations = $scope.resultsGridOptions.selectedItems;
                $location.path('/reservationConfirm');
            };
        }
    ]
);


//RESERVATION CONFIRMATION CONTROLLER
controllers.controller('reservationConfirmController', ['$scope', '$http', '$location', '$rootScope',
    function ($scope, $http, $location, $rootScope) {

        $scope.reservationsGridOptions = {
            data: 'reservations',
            enableRowSelection: false,
            columnDefs: [
                {
                    field: 'viewableDate',
                    displayName: 'Date'
                },
                {
                    field: 'startingCity',
                    displayName: 'Starting City'
                },
                {
                    field: 'destination',
                    displayName: 'Destination'
                },
                {
                    field: 'airline',
                    displayName: 'Airline'
                },
                {
                    field: 'price',
                    displayName: 'Price',
                    cellTemplate: 'templates/priceConfirmColumn.html'
                },
                {
                    field: 'class',
                    displayName: 'Class',
                    enableCellEdit: true,
                    cellTemplate: 'templates/classSelect.html',
                    editableCellTemplate: 'templates/classSelect.html'
                }
            ]
        };

        $scope.getSeatClass = function(seatClass) {
            if (seatClass.toLowerCase().search('economy')) {
                return 'Economy';
            } else if (seatClass.toLowerCase().search('first')) {
                return 'FirstClass';
            } else {
                return 'unknown class';
            }
        };

        $scope.confirm = function() {
            angular.forEach($scope.reservations, function(reservation) {
                $http({
                    url: '/Reservations/api/reservations/create/',
                    method: 'PUT',
                    data: {
                        flightId: reservation.id,
                        userId: $rootScope.user.id,
                        seatClass: $scope.getSeatClass(reservation.class)
                    }
                }).then(function(response) {
                    if (response.status < 200 || response.status > 299) {
                        $rootScope.errorMessages.push(response);
                    } else {
                        $rootScope.searchResults.data = [];

                        switch($scope.destination) {
                            case 'Atlanta':
                                $location.path('/atlanta');
                                break;
                            case 'Chicago':
                                $location.path('/chicago');
                                break;
                            case 'Dallas':
                                $location.path('/dallas');
                                break;
                            case 'New York':
                                $location.path('/newYork');
                                break;
                            case 'San Francisco':
                                $location.path('/sanFrancisco');
                                break;
                        }
                    }
                }).catch(function(response) {
                    $rootScope.errorMessages = response;
                });
            });
        };

        $scope.cancel = function() {
            $location.path('/searchResults');
        };
    }
]);


//LOGIN CONTROLLER
controllers.controller('loginController', ['$scope', '$http', '$location', '$rootScope',
  		function ($scope, $http, $location, $rootScope) {

            $scope.submit = function() {
                
                //Make sure that the email is in the format email@domain.com
                if ($scope.email.search(/^.+@.+\..+/) === -1) {
                    $rootScope.errorMessages.push("Email was not in correct format");
                }
                
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
                
                //Make sure that the email is in the format email@domain.com
                if ($scope.email.search(/^.+@.+\..+/) === -1) {
                    $rootScope.errorMessages.push("Email was not in correct format");
                    return;
                }
                
                if ($scope.firstname.length <= 1) {
                    $rootScope.errorMessages.push("First name must be more than one character long");
                    return;
                }
                
                if ($scope.lastname.length <= 1) {
                    $rootScope.errorMessages.push("Last name must be more than one character long");
                    return;
                }

                //make sure passwords aren't empty
                if ($scope.password === '' || $scope.confirmPassword === '') {
                    $rootScope.errorMessages.push('Make sure to enter a password and confirm it');
                    return;
                }

                //Make sure passwords match
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
                        $rootScope.errorMessages.push(error);
                    });
                }).catch(function(error) {
                    $rootScope.errorMessages.push(error);
                });
            };
  		}
	]
);


//ACCOUNT CONTROLLER
controllers.controller('accountController', ['$scope', '$http', '$rootScope', '$location',
        function ($scope, $http, $rootScope, $location) {

            //Redirect to home page if user isn't logged in
            if (!$rootScope.user) {
                $location.path('/home');
            }

            $scope.loadReservations = function() {
                //Get the reservations for the user that's logged in
                $http({
                    url: '/Reservations/api/reservations',
                    method: 'GET',
                    params: { uid: $rootScope.user.id }
                }).then(function (results) {

                    //The request was successful
                    if (results.status === 200) {
                        $scope.userReservations = results.data;

                        //Get the flight for each reservation
                        angular.forEach($scope.userReservations, function (item) {
                            $http({
                                url: 'api/reservations/flight',
                                method: 'GET',
                                params: { flightId: item.flightId }
                            }).then(function (result) {
                                item.flight = result.data;
                                $scope.setViewableDate(item.flight);
                            }).catch(function (error) {
                                $rootScope.errorMessages.push(error);
                            });
                        });
                    } else {
                        $rootScope.errorMessages.push(results);
                    }
                }).catch(function (error) {
                    $rootScope.errorMessages.push(error);
                });
            };
            $scope.loadReservations();

            $scope.accountGridOptions = {
                data: 'userReservations',
                selectedItems: [],
                columnDefs: [
                    {
                        field: 'flight.viewableDate',
                        displayName: 'Date'
                    },
                    {
                        field: 'flight.startingCity',
                        displayName: 'Starting City'
                    },
                    {
                        field: 'flight.destination',
                        displayName: 'Destination'
                    },
                    {
                        field: 'flight.airline',
                        displayName: 'Airline'
                    },
                    {
                        field: 'flightClass',
                        displayName: 'Seat Class'
                    },
                    {
                        field: 'flight.distance',
                        displayName: 'Distance (Mi)'
                    }
                ]
            };

            $scope.deleteReservations = function() {
                angular.forEach($scope.accountGridOptions.selectedItems, function(reservation) {
                    $http({
                        url: '/Reservations/api/reservations/delete',
                        method: 'DELETE',
                        params: {
                            uid: $rootScope.user.id,
                            resId: reservation.id
                        }
                    }).then(function(result) {
                        $rootScope.errorMessages.push(result);
                        $scope.loadReservations();
                    }).catch(function(error) {
                        $rootScope.errorMessages.push(error);
                    });
                });
            };
        }
    ]
);


//ADMIN ADD FLIGHT CONTROLLER
controllers.controller('addFlightController', ['$scope', '$http', '$rootScope', '$location',
  		function ($scope, $http, $rootScope, $location) {
            
            //Uncomment when isadmin is added to the db
//            if (!user.isAdmin) {
//                $location.path('/home');
//            }
            
            $scope.times = [
                {
                    name: '9:00 AM',
                    value: '0900'
                },
                {
                    name: '1:00 PM',
                    value: '1300'
                },
                {
                    name: '5:00 PM',
                    value: '1700'
                },
                {
                    name: '9:00 PM',
                    value: '2100'
                }
            ];
            
            $scope.destination = undefined;
            
            $scope.open = function($event) {
                $event.preventDefault();
                $event.stopPropagation();

                $scope.opened = true;
            };
            
            $scope.submit = function() {
                var dates = [];
                
                if (!$scope.date) {
                    $rootScope.errorMessages.push('Date was in the wrong format');
                    return;
                }
                
                dates.push($scope.date.getFullYear());
                dates.push($scope.date.getMonth() + 1);
                dates.push($scope.date.getDate());
                dates.push($scope.time.value);
                
                for (var i = 0; i < dates.length; i++) {
                    if (dates[i] < 10) {
                        dates[i] = ('0' + dates[i]).slice(-2);
                    }
                }
                
                $scope.submitableDate = dates.join('');
                
                if (!$scope.airline || !$scope.startingCity || !$scope.destination || !$scope.economyPrice
                    || !$scope.firstClassPrice || !$scope.seatsInEconomy || !$scope.seatsInFirstClass) {
                    $rootScope.errorMessages.push('Not all forms were filled in');
                    return;
                }
                
                //Get rid of status message before new request
                $scope.successful = false;
                $scope.unsuccessful = false;
                
                $http({
                    url: 'api/reservations/flight/create/',
					method: 'POST',
					data: {
                        id: 0,
                        date: $scope.submitableDate,
						airline: $scope.airline,
                        startingCity: $scope.startingCity,
                        destination: $scope.destination,
                        seatsInFirstClass: $scope.seatsInFirstClass,
                        seatsInEconomy: $scope.seatsInEconomy,
                        economyPrice: $scope.economyPrice,
                        firstClassPrice: $scope.firstClassPrice
					}
				}).then(function(results) {
                    if (results.status >= 200 && results.status <= 299) {
                        $scope.successful = true;
                        $scope.economyPrice = '';
                        $scope.firstClassPrice = '';
                        $scope.seatsInEconomy = '';
                        $scope.seatsInFirstClass = '';
                    } else {
                        $scope.unsuccessful = true;
                    }
				}).catch(function(error) {
                    $scope.unsuccessful = true;
                    $rootScope.errorMessages.push(error);
				});
            };
  		}
	]
);


//SUGGESTION CONTROLER (Dummy because Angular wants a controller for those pages)
controllers.controller('suggestionController', ['$scope',
    function ($scope) {

    }
]);


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
