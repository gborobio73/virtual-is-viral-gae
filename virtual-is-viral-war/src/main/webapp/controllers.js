 
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

    var getUser = function(){
      services.getUser().then(
        function(result) {
          $scope.user = result;
        });
    }
    var workId = $stateParams.workId;
    getWork(workId);
    getUser();

    $scope.canDelete = function(commentCreator){
      console.log("canDelete commentCreator ->" + commentCreator);
      console.log("canDelete $scope.user ->" + $scope.user.Name);
      var can_delete = (commentCreator == $scope.user.Name);
      console.log("commentCreator == $scope.user ->" + can_delete);
      return can_delete;
    };
  });

