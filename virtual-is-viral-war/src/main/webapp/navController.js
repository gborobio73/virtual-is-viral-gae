angular.module('vivControllers').controller(
  'userDataController', function ($scope, $location, services) {
    $scope.user = {};

    var getUnreadNotifications = function(){
      services.getUnreadNotifications().then(
        function(result) {
          console.log('getUnreadNotifications amount -> ' + result.length);
          $scope.unreadNotificationsAmount = result.length;
          console.log('getUnreadNotifications -> ' + JSON.stringify(result));
          $scope.unreadNotifications = result;
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
    getUnreadNotifications();

    $scope.markAllNotificationsAsRead = function() {
        services.markNotificationsAsRead();
        $scope.unreadNotificationsAmount = 0;
    };    
  });