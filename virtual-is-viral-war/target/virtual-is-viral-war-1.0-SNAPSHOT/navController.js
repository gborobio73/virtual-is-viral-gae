angular.module('vivControllers').controller(
  'userDataController', function ($scope, $location, services) {
    
    var getUnreadNotificationsAmount = function(user){
      services.getUnreadNotificationsAmount(user).then(
        function(result) {
          $scope.unreadNotifications = result;
          }); 
    }

    var getNotifications = function(user){
      services.getNotifications(user).then(
        function(result) {
          $scope.notifications = result;
          }); 
    }

    $scope.user = services.getUser();
    getUnreadNotificationsAmount($scope.user.id);
    getNotifications($scope.user.id);

    $scope.markAllNotificationsAsRead = function(user) {
        console.log("markAllNotificationsAsRead for user:" +user);
        $scope.unreadNotifications = 0;
    };    
  });