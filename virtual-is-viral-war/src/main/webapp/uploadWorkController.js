angular.module('vivControllers').controller(
  'uploadWorkController', function ($scope, services) {
    
    var getUploadURL= function(){
      services.getUploadURL().then(
        function(result) {
          $scope.uploadURL = result.uploadFileURL;
          }); 
    }

    getUploadURL();

    /*public functions*/
    $scope.uploadWork = function(){
    	console.log("twitter_userid: " + $scope.twitter_userid);
    	console.log("twitter_password: " + $scope.twitter_password);
    	console.log("filename: " + $scope.filename);
    	console.log("myTweetPic: " + $scope.myTweetPic);
      /*var work ={};
      work.name = $scope.title;
      work.description = $scope.description;
      work.imageId = $scope.file;
      
      console.log("uploadWork work ->" + JSON.stringify(work));*/      
    };
  });

/*
public Work(String id, String user, String name, String description, String imageId, List<Comment> comments)
  {
    Id = id;
    User = user;
    Name = name;
    Description = description;
    ImageId = imageId;
    Comments = comments;
  }
*/