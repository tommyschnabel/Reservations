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
                }).when('/adminAddFlight', {
                    templateUrl: 'templates/adminAddFlight.html',
                    controller: 'addFlightController'
                }).when('/atlanta', {
                    templateUrl: 'templates/Suggestion_Atlanta.html',
                    controller: 'suggestionController'
                }).when('/dallas', {
                    templateUrl: 'templates/Suggestion_Dallas.html',
                    controller: 'suggestionController'
                }).when('/sanFrancisco', {
                    templateUrl: 'templates/Suggestion_SanFrancisco.html',
                    controller: 'suggestionController'
                }).when('/newYork', {
                    templateUrl: 'templates/Suggestion_NewYork.html',
                    controller: 'suggestionController'
                }).when('/chicago', {
                    templateUrl: 'templates/Suggestion_Chicago.html',
                    controller: 'suggestionController'
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
