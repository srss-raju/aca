/**
 * Created by speta on 11/18/2015.
 */

'use strict';

angular.module('aca1095BUiAppCW')
  .controller('AEditUserCtrl',['$scope','$timeout','AdminService','record', 'roles', 'ENV', 'UserService', function($scope,$timeout,AdminService,record,roles, ENV, UserService){

    var adminrole=null;
    adminrole = angular.lowercase(ENV.state_admin);

    /*screen labels*/
    $scope.label={
      titleDescription : "Edit User Details",
      adlabel:'Activate/Deactivate Account Settings',
      activate:'Activate',
      deactivate:'Deactivate',
      cancel:'Cancel',
      save:'Save'
    };

    /*aria labels*/
    $scope.dialogaria = {
      title:"Edit the User Details",
      fname:"Type the first name. this is a required field",
      lname:"Type the Last Name. this is a required field",
      uname:"type the Email ID. this is a required field",
      role:"select the Role, arrow up and arrow down keys to select you the role. this is a required field",
      active:"the user is active",
      deactive:"the user is deactive",
      activate:"The user is deactive.Click to activate the user",
      deactivate:"the user is active. click to Deactivate the user",
      cancel:"cancel editing the user",
      save:"save the edited user details"
    };

    /*success/error message*/
    $scope.userNotFound = false;
    $scope.errorMessage = "User is already created";
    $scope.successFlag = false;
    $scope.successMessage = "User has been successfully updated";
    $scope.spinnerShow=false;
    $scope.disableSearch = true;
    $scope.$watch('$scope.edituserform',function(){
      if($scope.edituserform){
        $scope.edituserform.firstname.$setDirty("firstname", true);
        $scope.edituserform.lastname.$setDirty("lastname", true);
        $scope.edituserform.email.$setDirty("email", true);
        $scope.edituserform.role.$setDirty("role", true);
      }
    });

    $scope.$watch('edituser',function(newVal){
     if(
       $scope.edituser_master.fname === newVal.fname
       && $scope.edituser_master.lname === newVal.lname
       && $scope.edituser_master.email === newVal.email
       && $scope.edituser_master.role === newVal.role
       && $scope.edituser_master.userStatus === newVal.userStatus){
       $scope.disableSearch = true;
     }
      else{
       if($scope.edituserform.$valid){
         $scope.disableSearch = false;
       }else{
         $scope.disableSearch = true;
       }
     }
    },true);

    $scope.fnShowSuccessMessage=function(message){
      $scope.successFlag = true;
      $scope.userNotFound = false;
      $scope.successMessage=message;
    };
    $scope.fnShowErrorMessage=function(message){
      $scope.successFlag = false;
      $scope.userNotFound = true;
      $scope.errorMessage = message;
    };
    $scope.fnHandleFormActionsToggle=function(){
      if($scope.successFlag) {
        if ($('.dia-success-message').length) {
          $(".dia-success-message").focus();
        }

        if($('.dia-cancel-button').length){
          $('.dia-cancel-button').focus();
        }
      } else if ($scope.userNotFound){
        if($('.dia-error-message').length){
          $(".dia-error-message").focus();
        }
        if($('.dia-first-name').length){
          $('.dia-first-name').focus();
        }
      }
    };
    /*save method*/
    $scope.save_data = function(){
      /*make the service call to save the data*/
      $scope.spinnerShow=true;
      var userInformationPayload = {
        'fName': $scope.edituser.fname,
        'lName': $scope.edituser.lname,
        'emailId': $scope.edituser.email
      };
      var newRoles = [], oldRoles = [];
      if($scope.edituser.data.role){
        oldRoles = angular.copy($scope.edituser.data.role);
        newRoles = angular.copy($scope.edituser.data.role);
        newRoles = _.map(newRoles,function(role){
          if(role.appRole.indexOf(ENV.unicornRolePreFix)>-1){
            role.appRole=ENV.unicornRolePreFix+$scope.edituser.role;
          }
          return role;
        });
      }else{
        newRoles.push({
          'appName' : ENV.unicornApplicationName,
          'appRole' : ENV.unicornRolePreFix+$scope.edituser.role
        });
      }
      if($scope.edituser.role.toLowerCase().indexOf(adminrole)>-1){
        newRoles.push({
          'appName' : ENV.unicornApplicationName,
          'appRole' : ENV.unicornRolePreFix+ENV.unicornEnterpriseAdminRoleName
        });
      }

      var userRolesPayload = {
        'emailId' : $scope.edituser.email,
        'newApps' : newRoles,
        'oldApps' : oldRoles
      };
      UserService.updateUserInformation(userInformationPayload)
        .then(function(result){
          if(result.error){
            $scope.spinnerShow=false;
            $scope.fnShowErrorMessage(result.message);
          }else{
            UserService.updateUserRoles(userRolesPayload)
              .then(function(result1){
                if(result1.error){
                  $scope.spinnerShow=false;
                  $scope.fnShowErrorMessage(result.message);
                }else{
                  UserService.updateUserStatus($scope.edituser.email,$scope.edituser.userStatus)
                    .then(function(result2){
                      $scope.spinnerShow=false;
                      if(result2.error){
                        $scope.fnShowErrorMessage(result.message);
                      }else{
                        $scope.fnShowSuccessMessage('User has been successfully updated');
                        $timeout(function(){
                          AdminService.cancel_modal();
                          $scope.$parent.fnInit();
                        },3000);
                      }
                    });
                }
              });
          }
        });
    };

    /*cancel method*/
    $scope.cancel_modal = function(){
      $timeout(function(){
        AdminService.cancel_modal();
      },200);
    };
    $scope.fnInit=function(){

      $scope.spinnerShow=true;
      /*role values*/
      $scope.roles = roles || [];

      /*assign the values to the input fields*/
      if(record) {
        $scope.edituser = {
          fname: record.fName,
          lname: record.lName,
          email: record.emailId,
          role: record.roles[0],
          userStatus : record.status==='1'? false : true,
          data: record
        };
        $scope.edituser_master= angular.copy($scope.edituser);
      }
      $scope.spinnerShow=false;

      $(".titledialog").focus();
    };
    $scope.fnInit();
  }]);
