 
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
    $scope.addingComment = false;
    $scope.removingComment = false;
    $scope.showEditFields = false;
    $scope.savingWork = false;
    $scope.isOwner= false;
    $scope.work ={};
    
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
    
    $scope.isOwner = $scope.work.user != null && ($scope.work.user == $scope.user);
    
    $scope.canDelete = function(comment){      
      var can_delete = (comment.user ==  $scope.user.name);
      return can_delete;
    };
    
    $scope.addComment = function(){
    	$scope.addingComment = true;
    	console.log("addComment to work id->" + JSON.stringify($scope.commentText));
    	services.addComment($scope.work.id, $scope.commentText).then(
	        function(result) {
	          console.log ('addComment() returns: ' + JSON.stringify(result));
	          $scope.commentText = "";
	          getWork($scope.work.id);
	          $scope.addingComment = false;
	          });
    };
    
    $scope.deleteComment= function(workId, commentId){
    	$scope.removingComment = true;
        console.log("deleteComment ->" + workId + " " + commentId);        
    	services.deleteComment(workId, commentId).then(
	        function(result) {	          
	          getWork($scope.work.id);
	          $scope.removingComment = false;
	          });    	
      };
      
    $scope.saveWork= function(){
      	$scope.savingWork = true;
        console.log("saveWork ->" + $scope.work.id + " " + $scope.work.name + " " + $scope.work.description);        
      	services.editWork($scope.work.id, $scope.work.name, $scope.work.description).then(
  	        function(result) {
  	        	$scope.savingWork = false;
  	        	$scope.showEditFields = false;
  	        	getWork($scope.work.id);  	          
  	          });   	
        };
    
  });

