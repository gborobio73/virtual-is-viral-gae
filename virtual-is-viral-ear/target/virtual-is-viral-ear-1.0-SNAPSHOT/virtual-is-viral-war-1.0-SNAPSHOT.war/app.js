var vivApp = angular.module('viv', 
  ['ui.router', 'ui.bootstrap', 'ngCookies', 
  'vivControllers', 'vivServices', 'vivDirectives',
  ]);

angular.module('vivControllers', []);
angular.module('vivServices', []);
angular.module('vivDirectives', []);

vivApp.config(function($stateProvider, $urlRouterProvider) {
  //
  // For any unmatched url
  $urlRouterProvider.otherwise("/Board");
  //
  // Now set up the states
  $stateProvider
    .state('Board', {
      url: '/Board',
      templateUrl: 'partials/Board.html',
      controller: 'boardController'
    })
    .state('NewBoard', {
      url: '/NewBoard',
      templateUrl: 'partials/NewBoard.html',
      controller: 'boardController'
    })
    .state('Tiles', {
      url: '/Tiles',
      templateUrl: 'Tiles.html',
      controller: 'boardController'
    })
    .state('Details', {
      url: '/Details/:workId',
      templateUrl: 'partials/workDetails.html',
      controller: 'detailsController'
    })
});

/*vivApp.factory('httpRequestInterceptor', function ($location) {
  
  return {
    
    request: function (config) {

      var doAfterCheckingIt = function(userSessionState){
        console.log ('userSessionState: ' + userSessionState);
        if (userSessionState != true) {
          window.location.replace("/Login.html");
        }
      };

      var sessionState = sessionStorage.vivSessionState;
      sessionParams = {
        "client_id": '17792696780-egbqbeqkdbamg2aojs0e7otgvq1i2p06.apps.googleusercontent.com',
        "session_state": sessionState 
      };
      
      gapi.auth.checkSessionState(sessionParams, doAfterCheckingIt);

      return config || $q.when(config);
    }

  };

});
 
vivApp.config(function ($httpProvider) {
  $httpProvider.interceptors.push('httpRequestInterceptor');
});*/