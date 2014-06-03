angular.module('vivServices').factory('services', function($http, $http) {
    var services = new Object();

    services.getAllWorks = function() {
             //return the promise directly.
             return $http.get('/rest/works/all')
                       .then(function(result) {
                            //resolve the promise as the data
                            return result.data;
                        });
        };

    services.getUserWorks = function() {
             //return the promise directly.
             return $http.get('/rest/works/user/')
                       .then(function(result) {
                            //resolve the promise as the data
                            return result.data;
                        });
        };
    services.getWork = function(workId) {
             //return the promise directly.
             return $http.get('/rest/works/work/'+workId)
                       .then(function(result) {
                            //resolve the promise as the data
                            return result.data;
                        });
        };
    services.getUnreadNotificationsAmount = function(){
             return $http.get('/rest/notifications/unreadamount/')
                       .then(function(result) {
                            //resolve the promise as the data
                            return result.data;
                        });
        };
    services.getNotifications = function(){
        //return the promise directly.
             return $http.get('/rest/notifications/all')
                       .then(function(result) {
                            //resolve the promise as the data
                            return result.data;
                        });
        };
    services.getUser = function(){
             return $http.get('/rest/user/')
                       .then(function(result) {
                            console.log('/rest/user/ -> ' + JSON.stringify(result.data));
                            return result.data;
                        });
        };
    services.getLogoutUrl = function(){
             return $http.get('/rest/url/')
                       .then(function(result) {
                            console.log('/rest/url/ -> ' + JSON.stringify(result.data));
                            return result.data;
                        });
        };
    services.getUploadURL = function(){
             return $http.get('/rest/upload/url/')
                       .then(function(result) {
                            console.log('/rest/upload/url/ -> ' + JSON.stringify(result.data));
                            return result.data;
                        });
        };
    services.addComment = function(workId, commentText){
    		var data ={
    				workId :workId,
    				commentText: commentText
    		};
            return $http.post('/rest/works/comment/add', data)
                      .then(function(result) {
                           console.log('post /rest/works/comment/add -> ' + JSON.stringify(result));
                           return result;
                       });
       };
   services.deleteComment = function(workId, commentId){
   		var data ={
   				workId :workId,
   				commentId: commentId
   		};
           return $http.post('/rest/works/comment/delete', data)
                     .then(function(result) {                          
                          return result;
                      });
      };
    return services; 
});
