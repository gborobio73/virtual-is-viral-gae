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
          $scope.user = result;
        });
    }

    var getLogoutUrl = function(){
      services.getLogoutUrl().then(
        function(result) {
          console.log('getLogoutUrl -> ' + JSON.stringify(result));
          $scope.logoutURL = result.logoutURL;
        });
    }

    getUser();
    getLogoutUrl();
    getUnreadNotificationsAmount();
    getNotifications();

    $scope.markAllNotificationsAsRead = function() {
        console.log("markAllNotificationsAsRead:");
        $scope.unreadNotifications = 0;
    };    
  });