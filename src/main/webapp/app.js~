angular.module('reservationsApp', ['ngRoute', 'reservationsControllers'])
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
          }).otherwise({
              redirectTo: '/home'
          });
      }
]);
