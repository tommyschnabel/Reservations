var controllers = angular.module('reservationsControllers', []);

//HEADER CONTROLLER
controllers.controller('headerController', ['$scope', '$location', '$rootScope', '$filter', '$modal',
  		function ($scope, $location, $rootScope, $filter, $modal) {
            var oneElementMargin = '45%',
                twoElementMargin = '38%',
                threeElementMargin = '25%',
                fourElementMargin = '17%';

            $scope.margin = oneElementMargin;
            
            $scope.$watch('user', function() {

                if ($scope.user) {
                    if ($scope.searchResults) {
                        if ($scope.user.admin) {
                            $scope.margin = fourElementMargin;
                        } else {
                            $scope.margin = threeElementMargin;
                        }
                    } else {
                        if ($scope.user.admin) {
                            $scope.margin = threeElementMargin;
                        } else {
                            $scope.margin = oneElementMargin;
                        }
                    }
                } else {
                    if ($scope.searchResults) {
                        $scope.margin = twoElementMargin;
                    } else {
                        $scope.margin = oneElementMargin;
                    }
                }
                $('ul#navbar').css({ 'margin-left': $scope.margin });
            });

            $scope.$watch('searchResults', function() {

                if ($scope.user) {
                    if ($scope.searchResults) {
                        if ($scope.user.admin) {
                            $scope.margin = fourElementMargin;
                        } else {
                            $scope.margin = threeElementMargin;
                        }
                    } else {
                        if ($scope.user.admin) {
                            $scope.margin = threeElementMargin;
                        } else {
                            $scope.margin = oneElementMargin;
                        }
                    }
                } else {
                    if ($scope.searchResults) {
                        $scope.margin = twoElementMargin;
                    } else {
                        $scope.margin = oneElementMargin;
                    }
                }
                $('ul#navbar').css({ 'margin-left': $scope.margin });
            });

            $scope.logout = function() {
                $rootScope.user = null;
                $location.path('#/home');
            };

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

            $rootScope.errorModal = function(errors) {
                if (!Array.isArray(errors)) {
                    errors = [ errors ];
                }

                $modal.open({
                    templateUrl: 'errorModal.html',
                    controller: 'errorModalController',
                    size: 'sm',
                    resolve: {
                        errors: function () {
                            return errors;
                        }
                    }
                });

            };

            $rootScope.setViewablePrice = function(item) {
                if (item.placeholder) {
                    return;
                }
                
                item.viewablePrice = $filter('currency')(item.economyPrice);
                item.viewablePrice += ' / ';
                item.viewablePrice += $filter('currency')(item.firstClassPrice);
            };
  		}
	]
);


////ERROR MODAL CONTROLLER
controllers.controller('errorModalController',
    function ($scope, $modalInstance, errors) {

        $scope.errors = errors;

        $scope.close = function() {
            $modalInstance.dismiss('cancel');
        };

    }
);


//HOME/SEARCH CONTROLLER
controllers.controller('homeController', ['$scope', '$location', '$http', '$rootScope',
        function ($scope, $location, $http, $rootScope) {
            $scope.flightFrom = 'Chicago';
            $scope.flightTo = 'Atlanta';

            $scope.search = function() {
                var startDate = '',
                    endDate = '',
                    errors = [];

                //We'll use this to decide which suggestions page to show
                $rootScope.destination = $scope.flightTo;

                //Validate cities aren't the same
                if ($scope.flightFrom === $scope.flightTo) {
                    errors.push('You cannot fly to the same city you are departing from');
                }

                //Validate dates are entered
                if ($scope.flightDepart === '' || $scope.flightReturn === ''
                    || $scope.flightDepart === undefined || $scope.flightReturn === undefined) {
                    errors.push('One of the date fields is empty');
                }

                if (errors.length > 0) {
                    $scope.errorModal(errors);
                    console.log(errors);
                    return;
                }

                //Starting date
                startDate += $scope.flightDepart.getFullYear();

                if ($scope.flightDepart.getMonth() + 1 < 10) {
                    startDate += '0';
                }
                startDate += $scope.flightDepart.getMonth() + 1;

                if ($scope.flightDepart.getDate() < 10) {
                    startDate += '0';
                }
                startDate += $scope.flightDepart.getDate();

                //Ending date
                endDate += $scope.flightReturn.getFullYear();

                if ($scope.flightReturn.getMonth() + 1 < 10) {
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
                    errors.push('The starting date was after the ending date in the search');
                }

                if (errors.length > 0) {
                    $scope.errorModal(errors);
                    console.log(errors);
                    return;
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
                            errors.push(error);
                            console.log(error);
                        });
                    }
                    
                    if (!$scope.roundTrip && errors.length < 1) {
                        $location.path('/searchResults');
                    }
                }).catch(function(error) {
                    errors.push(error);
                    console.log(error);
                });

                if (errors.length > 0) {
                    $scope.errorModal(errors);
                }
            };
        }
    ]
);


//SEARCH RESULTS CONTROLLER
controllers.controller('searchResultsController', ['$scope', '$rootScope', '$location', '$filter', '$http',
        function($scope, $rootScope, $location, $filter, $http) {

            if ($scope.searchResults === undefined) {
                $scope.searchResults = { data: [] };
            }

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
                        width: '****'
                    },
                    {
                        field: 'startingCity',
                        displayName: 'Starting City',
                        width: '***'
                    },
                    {
                        field: 'destination',
                        displayName: 'Destination',
                        width: '***'
                    },
                    {
                        field: 'duration',
                        displayName: 'Duration',
                        width: '**'
                    },
                    {
                        field: 'airline',
                        displayName: 'Airline',
                        width: '***'
                    },
                    {
                        field: 'viewablePrice',
                        displayName: 'Price (Economy/First Class)',
                        width: '******'
                    },
                    {
                        field: 'seatsInEconomy',
                        displayName: 'Seats (Economy)',
                        width: '****'
                    },
                    {
                        field: 'seatsInFirstClass',
                        displayName: 'Seats (First Class)',
                        width: '****'
                    }
                ]
            };
            
            $scope.$watch('resultsGridOptions.selectedItems.length', function() {
                $scope.hasClicked = false;
            });
            
            $scope.delete = function() {
                var errors = [];
                $scope.hasClicked = false;
                
                //Make the message go away before deleting again
                //Status messages don't work properly right now, but it's good enough for now
                $scope.successful = false;
                $scope.unsuccessful = false;
                
                angular.forEach($scope.resultsGridOptions.selectedItems, function(flight, index) {
                    $http({
                        url: 'api/admin/flight/delete/',
                        method: 'DELETE',
                        params: {
                            flightId: flight.id
                        }
                    }).then(function(result) {
                        if (result.status >= 200 && result.status <= 299) {
                            $scope.successful = true;
                            $scope.searchResults.data.splice(index, 1);
                        } else {
                            errors.push(result);
                            console.log(result);
                        }
                    }).catch(function(error) {
                        errors.push(error);
                    });
                });

                if (errors.length > 0) {
                    $scope.errorModal(errors);
                }
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
            var errors = [];

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
                        errors.push(response);
                        console.log(response);
                    } else {
                        if (errors.length < 1) {
                            $rootScope.searchResults.data = [];

                            switch ($scope.destination) {
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
                    }
                }).catch(function(response) {
                    errors.push(response);
                    console.log(response);
                });
            });

            if (errors.length > 0) {
                $scope.errorModal(errors);
            }
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
                var errors = [];
                
                //Make sure that the email is in the format email@domain.com
                if ($scope.email.search(/^.+@.+\..+/) === -1) {
                    errors.push("Email was not in correct format");
                    console.log("Email was not in correct format");
                }

                if (errors.length < 1) {
                    $http({
                        url: '/Reservations/api/user/login/',
                        method: 'POST',
                        data: {
                            'email': $scope.email,
                            'password': $scope.password
                        }
                    }).then(function (results) {
                        if (results.data && results.data !== '' && results.status >= 200 && results.status <= 299) {
                            $rootScope.user = results.data;

                            //Navigate back to home page after login
                            $location.path('#/home');
                        } else {
                            errors.push("Could not log in ");
                            errors.push(results);
                            console.log(results);
                            $scope.errorModal(errors);
                        }
                    }).catch(function (error) {
                        errors.push(error);
                        console.log(error);
                        $scope.errorModal(errors);
                    });
                }

                if (errors.length > 0) {
                    $scope.errorModal(errors);
                }
            };
  		}
	]
);


//REGISTER CONTROLLER
controllers.controller('registerController', ['$scope', '$http', '$location', '$rootScope',
  		function ($scope, $http, $location, $rootScope) {
            $scope.submit = function() {
                var errors = [];
                
                //Make sure that the email is in the format email@domain.com
                if ($scope.email.search(/^.+@.+\..+/) === -1) {
                    errors.push("Email was not in correct format");
                    console.log("Email was not in correct format");
                }
                
                if ($scope.firstname.length <= 1) {
                    errors.push("First name must be more than one character long");
                    console.log("First name must be more than one character long");
                }
                
                if ($scope.lastname.length <= 1) {
                    errors.push("Last name must be more than one character long");
                    console.log("Last name must be more than one character long");
                }

                //make sure passwords aren't empty
                if ($scope.password === '' || $scope.confirmPassword === '') {
                    errors.push('Make sure to enter a password and confirm it');
                    console.log('Make sure to enter a password and confirm it');
                }

                //Make sure passwords match
                if ($scope.password !== $scope.confirmPassword) {
                    errors.push("Your passwords don't match");
                    console.log("Your passwords don't match");
                }

                if (errors.length > 0) {
                    $scope.errorModal(errors);
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
                        if (results.status >= 200 && results.status <= 299 && results.data !== '') {
                            $rootScope.user = results.data;

                            //Then return them to the home page
                            $location.path('#/home');
                        } else {
                            errors.push(results);
                            console.log(results);
                            $scope.errorModal(errors);
                        }
                    }).catch(function(error) {
                        errors.push(error);
                        console.log(error);
                        $scope.errorModal(errors);
                    });
                }).catch(function(error) {
                    errors.push(error);
                    console.log(error);
                    $scope.errorModal(errors);
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
                var errors = [];

                //Get the reservations for the user that's logged in
                $http({
                    url: '/Reservations/api/reservations',
                    method: 'GET',
                    params: { uid: $rootScope.user.id }
                }).then(function (results) {

                    //The request was successful
                    if (results.status >= 200 && results.status <= 299) {
                        $scope.userReservations = results.data;

                        //Get the flight for each reservation
                        angular.forEach($scope.userReservations, function (item) {
                            $http({
                                url: 'api/reservations/flight',
                                method: 'GET',
                                params: { flightId: item.flightId }
                            }).then(function (result) {
                                if (result.status >= 200 && result.status <= 299) {
                                    item.flight = result.data;
                                    $scope.setViewableDate(item.flight);
                                } else {
                                    errors.push(result);
                                    console.log(result);
                                }
                            }).catch(function (error) {
                                errors.push(error);
                                console.log(error);
                            });
                        });
                    } else {
                        errors.push(results);
                        console.log(result);
                    }
                }).catch(function (error) {
                    errors.push(error);
                    console.log(error);
                });

                if (errors.length > 0) {
                    $scope.errorModal(errors);
                }
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
                var errors = [];

                angular.forEach($scope.accountGridOptions.selectedItems, function(reservation) {
                    $http({
                        url: '/Reservations/api/reservations/delete',
                        method: 'DELETE',
                        params: {
                            uid: $rootScope.user.id,
                            resId: reservation.id
                        }
                    }).then(function(result) {
                        if (result.status < 200 || result.status > 299) {
                            errors.push(result);
                            console.log(result);
                        }
                    }).catch(function(error) {
                        errors.push(error);
                        console.log(error);
                    });
                });

                if (errors.length > 0) {
                    $scope.errorModal(errors);
                } else {
                    $scope.successful = true;
                }

                $scope.loadReservations();
            };
        }
    ]
);


//ADMIN ADD FLIGHT CONTROLLER
controllers.controller('addFlightController', ['$scope', '$http', '$rootScope', '$location',
  		function ($scope, $http, $rootScope, $location) {
            
            if (!$scope.user || !$scope.user.admin) {
                $location.path('/home');
            }
            
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
                var dates = [],
                    errors = [];
                
                if (!$scope.date) {
                    errors.push('Date was undefined, or was in wrong format');
                    console.log('Date was undefined, or was in wrong format');
                }

                if (!$scope.airline || !$scope.startingCity || !$scope.destination || !$scope.economyPrice
                    || !$scope.firstClassPrice || !$scope.seatsInEconomy || !$scope.seatsInFirstClass) {
                    errors.push('Not all forms were filled in');
                    console.log('Not all forms were filled in');
                }

                if ($scope.startingCity && $scope.destination && $scope.startingCity === $scope.destination) {
                    errors.push('Starting city and Destination cannot be the same');
                    console.log('Starting city and Destination cannot be the same');
                }

                if ($scope.seatsInEconomy && $scope.seatsInEconomy.search(/^\D/) !== -1) {
                    errors.push('Seats in economy must contain only 0-9');
                    console.log('Seats in economy must contain only 0-9');
                }

                if ($scope.seatsInFirstClass && $scope.seatsInFirstClass.search(/^\D/) !== -1) {
                    errors.push('Seats in first class must contain only 0-9');
                    console.log('Seats in first class must contain only 0-9');
                }

                if ($scope.economyPrice && $scope.economyPrice.search(/^\D/) !== -1) {
                    errors.push('Economy price must contain only 0-9');
                    console.log('Economy price must contain only 0-9');
                }

                if ($scope.firstClassPrice && $scope.firstClassPrice.search(/^\D/) !== -1) {
                    errors.push('First class price class must contain only 0-9');
                    console.log('First class price must contain only 0-9');
                }

                if (errors.length > 0) {
                    $scope.errorModal(errors);
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
                
                //Get rid of status message before new request
                $scope.successful = false;
                
                $http({
                    url: 'api/admin/flight/create/',
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
                        errors.push(results);
                        console.log(results);
                    }
				}).catch(function(error) {
                    errors.push(error);
                    console.log(error);
				});

                if (errors.length > 0) {
                    $scope.errorModal(errors);
                }
            };
  		}
	]
);


//ADMIN RESERVATIONS CONTROLLER
controllers.controller('adminReservationsController', ['$scope', '$rootScope', '$location', '$filter', '$http',
        function($scope, $rootScope, $location, $filter, $http) {
            
            if (!$scope.user || !$scope.user.admin) {
                $location.path('/home');
            }
            
            $scope.load = function() {
                var errors = [];

                $http({
                    url: 'api/admin/reservations',
					method: 'GET'
                }).then(function(results) {
                    if (results.status >= 200 && results.status <= 299) {
                        $scope.adminReservations = results.data;
                        
                        //Get the flight for each reservation
                        angular.forEach($scope.adminReservations, function (reservation) {
                            $http({
                                url: 'api/reservations/flight',
                                method: 'GET',
                                params: { flightId: reservation.flightId }
                            }).then(function (result) {
                                if (results.status >= 200 && results.status <= 299) {
                                    reservation.flight = result.data;
                                    $scope.setViewableDate(reservation.flight);
                                } else {
                                    errors.push(result);
                                    console.log(result);
                                }
                            }).catch(function (error) {
                                errors.push(error);
                                console.log(error);
                            });
                        });
                    } else {
                        errors.push(results);
                        errors.push(results);
                    }
                }).catch(function(error) {
					errors.push(error);
                    errors.push(error);
				});

                if (errors.length > 0) {
                    $scope.errorModal(errors);
                }
            };
           
            $scope.load();

            $scope.reservationsGridOptions = {
                data: 'adminReservations',
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
            
            $scope.$watch('reservationsGridOptions.selectedItems.length', function() {
                $scope.hasClicked = false;
            });
            
            $scope.delete = function() {
                var errors = [];

                $scope.hasClicked = false;
                
                angular.forEach($scope.reservationsGridOptions.selectedItems, function(reservation) {
                    $http({
                        url: 'api/admin/reservation/delete/',
                        method: 'DELETE',
                        params: {
                            reservationId: reservation.id
                        }
                    }).then(function(result) {
                        if (result.status < 200 || result.status > 299) {
                            errors.push(result);
                            console.log(result);
                            $scope.successful = false;
                        }
                    }).catch(function(error) {
                        errors.push(error);
                        console.log(error);
                        $scope.successful = false;
                    });
                });

                if (errors.length > 0) {
                    $scope.errorModal(errors);
                }
                
                //Reload the grid to make sure everything is up to date
                $scope.load();
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
