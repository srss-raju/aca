/**
 * Created by speta on 11/11/2015.
 */

'use strict';

(function () {
angular.module('aca1095BUiAppCW').controller('ACreateUserCtrl',['$scope', '$log', '$state' ,'$element', '$timeout', 'UserService', 'ENV', function($scope,$log,$state,$element, $timeout, UserService, ENV){

  var adminrole=null;
  adminrole = angular.lowercase(ENV.state_admin);

  /*screen labels*/
  $scope.label={
    titleAction : "Create",
    titleDescription : "New User",
    cancel:"Cancel",
    create:"Create",
    back:"Back"
  };
  $scope.userObject={};

  /*aria labels*/
  $scope.arialabel={
    title:"Create New User",
    firstname:"enter first name. this is a required field",
    lastname:"enter the Last Name. this is a required field",
    email:"enter the Email ID. this is a required field",
    role:"select the Role, arrow up and arrow down keys to select you the role. this is a required field",
    cancel:"Press enter or click on Cancel button to clear the entered values.",
    create:"Press enter or click on Create button to create the user.",
    back:"Click here to go Back to admin dashboard."
  };

  $scope.createuser={
    fname:"",
    lname:"",
    email:"",
    role:""
  };

  /*role values*/
  $scope.roles = [];

  $scope.createAdmin = angular.copy($scope.createuser);

  $scope.userNotFound = false;
  $scope.errorMessage = "User exists in the system. Please try again.";
  $scope.successFlag = false;
  $scope.successMessage = "A new user has been successfully created";

  /*by default display create,cancel buttons*/
  $scope.buttonswitch="create";

  /*Create User method on create button click*/
  $scope.fnShowSuccessMessage=function(message){
    $scope.successFlag = true;
    $scope.userNotFound = false;
    $scope.successMessage=message;
    $scope.buttonswitch="created";
  };
  $scope.fnShowErrorMessage=function(message){
    $scope.buttonswitch="create";
    $scope.successFlag = false;
    $scope.userNotFound = true;
    if(message.indexOf("duplicate")>-1){
      $scope.errorMessage = "User exists in the system. Please try again.";
    }else{
      $scope.errorMessage = message;
    }
  };
  $scope.fnHandleFormActionsToggle=function(){
    if($scope.successFlag){
      $timeout(function(){
        if($('.successmessage').length){
          $('.successmessage').focus();
        }
      }, 50);
      $timeout(function(){
        if($('.backbutton').length){
          $('.backbutton').focus();
          $scope.successFlag = false;
        }
      }, 5000);
    }
    else{
      $timeout(function(){
        if($('.errormessage').length){
          $('.errormessage').focus();
        }
      }, 50);
      $timeout(function(){
        $('.title').focus();
        $scope.userNotFound = false;
      }, 5000);
    }
  };
  $scope.create_user= function(){
    $scope.spinnerShow=true;
    var userObject={
      "companyName": $scope.userObject.state,
      "fName": $scope.createAdmin.fname,
      "lName": $scope.createAdmin.lname,
      "emailId": $scope.createAdmin.email,
      "applicationDetails": []
    };
    UserService.createUser(userObject).then(function(result){
      if(result.error){
        $scope.fnShowErrorMessage(result.message);
        $scope.fnHandleFormActionsToggle();
        $scope.spinnerShow=false;
      }else{
        userObject.applicationDetails.push({
          'appName' : ENV.unicornApplicationName,
          'appRole' : $scope.createAdmin.role.actualRole
        });
        if($scope.createAdmin.role.actualRole.toLowerCase().indexOf(adminrole)>-1){
          userObject.applicationDetails.push({
            'appName' : ENV.unicornApplicationName,
            'appRole' : ENV.unicornRolePreFix+ENV.unicornEnterpriseAdminRoleName
          });
        }
        UserService.assignRole(userObject).then(function(result1){
          $scope.spinnerShow=false;
          if(result1.error){
            $scope.fnShowErrorMessage(result1.message);
            $scope.fnHandleFormActionsToggle();
          }else{
            $scope.fnShowSuccessMessage("A new user has been successfully created");
            $scope.fnHandleFormActionsToggle();
          }
        });
      }
    });
  };

  /*cancel user on cancel button click*/
  $scope.cancel_user=function(){
    $scope.createAdmin = angular.copy($scope.createuser);
    $scope.adminCreateUser.$setPristine();
    $scope.userNotFound = false;
    $scope.successFlag = false;
  };

  $scope.go_back = function(){
    $scope.cancel_user();
    $state.go('state.a-dashboard');
  };
  $scope.fnInit=function(){
    $scope.spinnerShow=true;
    $('.title').focus();
    UserService.checkLogin().then(function(){
      $scope.userObject = UserService.getUserInfo();
      UserService.getRoles().then(function(result){
        $scope.spinnerShow=false;
        if(result.error){
          $scope.fnShowErrorMessage(result.message);
          $scope.fnHandleFormActionsToggle();
        }else{
          $scope.roles = result.data;
        }
      });
    });
  };
  $scope.fnInit();
}]);
}());
