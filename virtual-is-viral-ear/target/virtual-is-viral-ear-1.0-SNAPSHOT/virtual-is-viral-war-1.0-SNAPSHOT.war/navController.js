angular.module('vivControllers').controller(
  'userDataController', function ($scope, $location, services) {
    $scope.user = {};

    var getUnreadNotificationsAmount = function(){
      services.getUnreadNotificationsAmount().then(
        function(result) {
          $scope.unreadNotifications = result;
          }); 
    }

    var getNotifications = function(){
      services.getNotifications().then(
        function(result) {
          $scope.notifications = result;
          }); 
    }
    
    var getUser = function(){
      services.getUser().then(
        function(result) {
          console.log('user -> ' + JSON.stringify(result));
          $scope.user = result;
          console.log('$scope.user -> ' + JSON.stringify($scope.user));
      
        });
    }

    getUser();
    getUnreadNotificationsAmount();
    getNotifications();

    $scope.markAllNotificationsAsRead = function() {
        console.log("markAllNotificationsAsRead:");
        $scope.unreadNotifications = 0;
    };    
  });