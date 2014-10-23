angular.module('reservationsApp', ['ngRoute', 'ngGrid', 'ui.bootstrap', 'reservationsControllers'])
    .config(['$routeProvider',
        function($routeProvider) {
            $routeProvider.
                when('/home', {
                    templateUrl: 'templates/home.html',
                    controller: 'homeController'
                }).when('/login', {
                    templateUrl: 'templates/login.html',
                    controller: 'loginController'
                }).when('/register', {
                    templateUrl: 'templates/register.html',
                    controller: 'registerController'
                }).when('/searchResults', {
                    templateUrl: 'templates/results.html',
                    controller: 'searchResultsController'
                }).when('/account', {
                    templateUrl: 'templates/account.html',
                    controller: 'accountController'
                }).when('/reservationConfirm', {
                    templateUrl: 'templates/reservation.html',
                    controller: 'reservationConfirmController'
                }).when('/test', {
                    templateUrl: 'templates/test.html',
                    controller: 'testController'
                }).otherwise({
                    redirectTo: '/home'
                });

//          This would get the '#' out of our url's, but it seems to not be the easiest thing to do
//          I'm reverting to an old saying here: "if it ain't broke, don't fix it"
//          If we have time later, we can fix this
//          $locationProvider.html5Mode(true);
        }
    ]
);
