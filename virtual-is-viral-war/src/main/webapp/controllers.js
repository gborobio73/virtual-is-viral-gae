 
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

    var getConnectedUser = function(){
        services.getUser().then(
          function(result) {
            $scope.user = result;
          });
      }
    
    var workId = $stateParams.workId;
    getWork(workId);
    getConnectedUser();
    
    $scope.canDelete = function(comment){      
      var can_delete = (comment.user ==  $scope.user.name);
      return can_delete;
    };
    
    $scope.addComment = function(){
    	console.log("addComment to work id->" + JSON.stringify($scope.commentText));
    	services.addComment($scope.work.id, $scope.commentText).then(
	        function(result) {
	          console.log ('addComment() returns: ' + JSON.stringify(result));
	          $scope.commentText = "";
	          getWork($scope.work.id);
	          }); 
    };
    
    $scope.deleteComment= function(workId, commentId){
        console.log("deleteComment ->" + workId + " " + commentId);        
    	services.deleteComment(workId, commentId).then(
	        function(result) {	          
	          getWork($scope.work.id);
	          }); 
      };
  });

