angular.module('vivControllers').controller(
  'uploadWorkController', function ($scope, services) {
    
    var getUploadURL= function(){
      services.getUploadURL().then(
        function(result) {
        	console.log('getUploadURL -> ' + result);
        	$scope.uploadUrl = result;
          }); 
    }

    getUploadURL();
  });
