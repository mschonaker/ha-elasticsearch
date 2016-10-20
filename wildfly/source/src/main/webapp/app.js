var app = angular.module('WildflyApp', [ 'ngRoute' ]);

app.config([ '$routeProvider', function($routeProvider) {

	$routeProvider

	.when('/user/:username', {
		templateUrl : 'templates/user.html',
		controller : 'UserController'
	})

	.when('/users', {
		templateUrl : 'templates/users.html',
		controller : 'UsersController'
	})

	.when('/status', {
		templateUrl : 'templates/status.html',
		controller : 'StatusController'
	})

	.otherwise({
		redirectTo : '/users'
	});

} ]);

app.controller('HeaderController', function($scope, $location) {

	$scope.isActive = viewLocation => viewLocation === $location.path();

});

app.controller('UsersController', function($scope) {

	$scope.init = () => {
		$scope.aux = null;
		UsersResource.findAll({
			'$callback' : function(status, request, entity) {
				$scope.users = entity;
				$scope.$apply();
			}
		})
	}
	
	$scope.delete = function(username) {
		UserResource.delete({
			'username' : username, 
			'$callback' : $scope.init
		})
	}
	
	$scope.insert = function() {
		UserResource.insert({
			'$entity' : $scope.aux,
			'$callback' : function(status, request, entity) {
				if (status == 501)
					alert("The user already exists: " + request.responseText)
				else
					$scope.init();
			}
		})
	}

	$scope.init();

});

app.controller('UserController', function($scope, $routeParams, $location) {
	
	UserResource.find({
		'username' : $routeParams.username,
		'$callback' : function(status, request, entity) {
			$scope.aux = entity;
			$scope.$apply();
		}
	})
	
	$scope.update = function() {
		UserResource.update({
			'$entity' : $scope.aux,
			'$callback' : function(status, request, entity) {
				$location.path('/users');
				$scope.$apply();
			}
		})
	}
	
});

app.controller('StatusController', function($scope) {
	
	StatusResource.getHostname({ '$callback':  function(status, request, entity) {
		$scope.status = request.responseText;
		$scope.$apply();
	}});
	
});

