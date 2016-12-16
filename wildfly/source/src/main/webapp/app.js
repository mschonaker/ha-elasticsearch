var app = angular.module('WildflyApp', [ 'ngRoute' ]);

app.config([ '$routeProvider', function($routeProvider) {

	$routeProvider

	.when('/message/:id', {
		templateUrl : 'templates/message.html',
		controller : 'MessageController'
	})

	.when('/messages', {
		templateUrl : 'templates/messages.html',
		controller : 'MessagesController'
	})

	.when('/status', {
		templateUrl : 'templates/status.html',
		controller : 'StatusController'
	})

	.otherwise({
		redirectTo : '/messages'
	});

} ]);

app.controller('HeaderController', function($scope, $location) {

	$scope.isActive = viewLocation => viewLocation === $location.path();

});

app.controller('MessagesController', function($scope) {

	$scope.init = () => {
		$scope.aux = null;
		$scope.results = MessagesResource.findAll();
	}
	
	$scope.delete = function(id) {
		MessageResource.delete({
			'id' : id, 
			'$callback' : $scope.init
		})
	}
	
	$scope.insert = function() {
		MessageResource.insert({
			'$entity' : $scope.aux.message,
			'$callback' : $scope.init
		})
	}

	$scope.init();
});

app.controller('MessageController', function($scope, $routeParams, $location) {
	
	MessageResource.find({
		'id' : $routeParams.id,
		'$callback' : function(status, request, entity) {
			$scope.aux = entity;
			$scope.$apply();
		}
	})
	
	$scope.update = function() {
		MessageResource.update({
			'id' : $scope.aux.id,
			'$entity' : $scope.aux.message,
			'$callback' : function(status, request, entity) {
				$location.path('/messages');
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
	
	StatusResource.getElasticSearchClusterInfo({ '$callback':  function(status, request, entity) {
		$scope.clusterStatus = request.responseText;
		$scope.$apply();
	}});
	
});

