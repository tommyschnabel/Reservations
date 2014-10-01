var controllers = angular.module('reservationsControllers', []);


//HEADER CONTROLLER
controllers.controller('headerController', ['$scope', '$location',
  		function ($scope, $location) {
            $scope.changePage = function(path) {
                $location.path(path);
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