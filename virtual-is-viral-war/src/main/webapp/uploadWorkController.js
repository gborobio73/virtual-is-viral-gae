angular.module('vivControllers').controller(
  'uploadWorkController', function ($scope, services) {
    


    $scope.uploadWork = function(work){
      var work ={};
      work.name = $scope.title;
      work.description = $scope.description;
      work.imageId = $scope.file;
      
      console.log("uploadWork work ->" + JSON.stringify(work));      
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