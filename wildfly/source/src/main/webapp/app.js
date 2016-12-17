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
	
	$scope.callback = (status, request, entity) => {
		
		$scope.message = null;
		$scope.results = entity;
		$scope.$apply();
		
	};
	
	$scope.findAll = () =>  MessagesResource.findAll({'$callback' : $scope.callback});
	
	$scope.delete = (id) => MessagesResource.delete({
			'id' : id,
			'$callback' : $scope.callback
	});
	
	$scope.insert = () => MessagesResource.insert({
			'$entity' : $scope.message,
			'$callback' : $scope.callback
	});
	
	$scope.$on('$locationChangeSuccess', $scope.findAll);

	$scope.findAll();
});

app.controller('MessageController', function($scope, $routeParams, $location) {
	
	MessageResource.find({
		'id' : $routeParams.id,
		'$callback' : (status, request, entity) => {
			$scope.aux = entity;
			$scope.$apply();
		}
	});
	
	$scope.update = () => MessageResource.update({
			'$entity' : $scope.aux,
			'$callback' : () => { 
				$location.path('/messages');
				$scope.$apply();
			}
	});
	
});

app.controller('StatusController', function($scope) {
	
	StatusResource.getHostname({ '$callback': (status, request, entity) => {
		$scope.status = request.responseText;
		$scope.$apply();
	}});
	
	StatusResource.getElasticSearchClusterInfo({ '$callback': (status, request, entity) => {
		$scope.clusterStatus = request.responseText;
		$scope.$apply();
	}});
	
});
