angular.module('reservationsApp', ['ngRoute', 'reservationsControllers'])
    .config(['$routeProvider',
        function($routeProvider, $locationProvider) {
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
                }).when('/flight', {
                    templateUrl: 'templates/flight.html',
                    controller: 'flightController'
                }).when('/passenger', {
                    templateUrl: 'templates/passengerinformation.html',
                    controller: 'passengerController'
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
