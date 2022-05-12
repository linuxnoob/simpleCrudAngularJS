var app = angular.module("UserManagement", []);

// Controller Part
app.controller("UserController", function($scope, $http) {


    $scope.users = [];
    $scope.user = {
        id: 1,
        name: "",
        number: "",
        email: "",
        address: "",

    };

    //load the data
    _refreshUserData();

    // HTTP POST/PUT methods for add/edit users
    // Call: http://localhost:8080/user
    $scope.submitUser = function() {

        var method = "";
        var url = "";
        if(_checkEmptyForm()){
        if ($scope.user.id == -1 ) {
            method = "POST";
            url = '/user/saveUser';
        } else{
            method = "PUT";
            url = '/user/updateUser';
            }

        $http({
            method: method,
            url: url,
            data: angular.toJson($scope.user),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(_success, _error);
        }
    };

    $scope.createUser = function() {

        _clearFormData();
    }

    //DELETE- delete user by Id
 
    $scope.deleteUser = function(user) {
        var x = document.getElementById("tableHide");
        x.style.display = "none";
        $http({

            method: 'DELETE',
            url: '/user/deleteUser',
            params:{userId : user.id}
        }).then(_success, _error);

    };

    // edit data
    $scope.editUser = function(user) {
         var x = document.getElementById("tableHide");
                                  if (x.style.display === "none") {
                                    x.style.display = "block";
                                  } else {
                                    x.style.display = "none";
                                  }
        $scope.user.id = user.id;
        $scope.user.name = user.name;
        $scope.user.number = user.number;
        $scope.user.email = user.email;
        $scope.user.address = user.address;
    };

    // Private Method
    // HTTP GET- get all users collection
    // Call: http://localhost:8080/users
    function _refreshUserData() {
        $http({
            method: 'GET',
            url: '/user/getAllUser'
        }).then(
            function(res) { // success
                var x = document.getElementById("tableData");
                var y = document.getElementById("emptyText");
                console.log("res data " + $scope.users);
                $scope.users = res.data;
                if(res.data.length!=0){
                            x.style.display = "block";
                            y.style.display = "none";

                }else  if(res.data.length==0){
                    y.style.display = "block";
                }
            },
            function(res) { // error
                console.log("Error: " + res.status + " : " + res.data);
            }
        );
    }

    function _success(res) {
        _refreshUserData();
        _clearFormData();
    }

    function _error(res) {
        var message = res.data.message;
        var status = res.data.status;
        var timestamp = res.data.timestamp;
        var path = res.path;
        console.log("Error: " + res.status + " : " + message);

        alert("Error: " + status + ":" + message);
    }

    // Clear the form
    function _clearFormData() {
         var x = document.getElementById("tableHide");
                          if (x.style.display === "none") {
                            x.style.display = "block";
                          } else {
                            x.style.display = "none";
                          }

        $scope.user.id = -1;
        $scope.user.name = "";
        $scope.user.number = "";
        $scope.user.email = "";
        $scope.user.address = "";

    };


    function _checkEmptyForm() {
       var name = document.getElementById('nameId').value;
       var email = document.getElementById('emailId').value;
       var number = document.getElementById('numberId').value;
       var address = document.getElementById('addressId').value;
       console.log("user data " + document.getElementById('nameId').value);
       if(name =="" && email =="" && number =="" && address ==""){
            alert("Error: data can't be empty");
            return false;
       }else{
            return true;
       }
    };
});