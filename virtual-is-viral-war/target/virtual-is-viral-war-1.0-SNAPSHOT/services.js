angular.module('vivServices').factory('services', function($http, $http) {
    var services = new Object();

    services.getAllWorks = function() {
             //return the promise directly.
             return $http.get('/rest/works/all')
                       .then(function(result) {
                            //resolve the promise as the data
                            return result.data;
                        });
        }

    services.getUserWorks = function(user) {
             //return the promise directly.
             return $http.get('/rest/works/user/'+user)
                       .then(function(result) {
                            //resolve the promise as the data
                            return result.data;
                        });
        }
    services.getWork = function(workId) {
             //return the promise directly.
             return $http.get('/rest/works/work/'+workId)
                       .then(function(result) {
                            //resolve the promise as the data
                            return result.data;
                        });
        }
    services.getUnreadNotificationsAmount = function(user){
             return $http.get('/rest/notifications/unreadamount/'+user)
                       .then(function(result) {
                            //resolve the promise as the data
                            return result.data;
                        });
        }
    services.getNotifications = function(user){
        //return the promise directly.
             return $http.get('/rest/notifications/'+user)
                       .then(function(result) {
                            //resolve the promise as the data
                            return result.data;
                        });
        }
    services.getUser = function(){
             return JSON.parse(sessionStorage.getItem('user'));
        }

    services.removeUser = function (){
        sessionStorage.removeItem('user');
    }

    return services; 
});
