 
angular.module('vivControllers').controller(
  'boardController', function ($scope, services) {

    $scope.getAllWorks = function() {
      services.getAllWorks().then(
        function(result) {
          //console.log('called getSllWorksServices: '+JSON.stringify(result));
          $scope.works = result;
          }); 
    };

    $scope.getUserWorks = function() {
        services.getUserWorks().then(
        function(result) {
          $scope.works = result;
          }); 
    };

    $scope.getAllWorks();
  });


angular.module('vivControllers').controller(
  'detailsController', function ($scope, $stateParams, services) {
    
    var getWork = function(workId) {
      services.getWork(workId).then(
        function(result) {
          $scope.work = result;
          console.log ('getWork() returns: ' + JSON.stringify(result));
          }); 
    };
    var workId = $stateParams.workId;
    getWork(workId);

  });

