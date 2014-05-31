 
angular.module('vivControllers').controller(
  'boardController', function ($scope, services) {

    $scope.getAllWorks = function() {
      services.getAllWorks().then(
        function(result) {
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
    
    $scope.canDelete = function(comment, work){
      console.log("canDelete comment ->" + JSON.stringify(comment));
      console.log("canDelete work ->" + JSON.stringify(work));
      var can_delete = (comment.User == work.User);
      console.log("can delete ->" + can_delete);
      return can_delete;
    };
  });

