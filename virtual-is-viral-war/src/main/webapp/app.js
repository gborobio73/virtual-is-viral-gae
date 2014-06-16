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
    .state('Upload', {
      url: '/Upload',
      templateUrl: 'partials/uploadFile.html',
      controller: 'uploadWorkController'
    })
    .state('Details', {
      url: '/Details/:workId',
      templateUrl: 'partials/workDetails.html',
      controller: 'detailsController'
    })
});

vivApp.factory('httpRequestInterceptor', function ($location, $q) {
  
  return {
        responseError: function(rejection) {
          if (rejection.status === 401) {
                console.log("Response Error 401",rejection);
                //$location.path('/login'); // for angular view/partial
                window.location = "./login";
            }
            return $q.reject(rejection);
        }
      };

});
 
vivApp.config(function ($httpProvider) {
  $httpProvider.interceptors.push('httpRequestInterceptor');
});