/**
 * Created by speta on 11/13/2015.
 */

'use strict';

  angular.module('aca1095BUiAppCW')
    .controller('ASearchUserCtrl',['$scope','$state','$timeout','AdminService', 'UserService', 'ENV',function($scope,$state,$timeout,AdminService, UserService,ENV){

      /*screen labels*/
      $scope.label={
        titleAction : "Search",
        titleDescription : "User",
        clear:"Clear Search",
        search:"Search",
        name:"Name",
        uname:"Username",
        status:"User status",
        role:"Role",
        edit:"Edit User",
        editdetails:"Edit Details"
      };

      $scope.aria={
        title:"This is Search User screen. Please enter username to search for any user",
        search:"Enter the name or the username to search for any user",
        clear:"Clear the entered details.",
        editdetail:"Edit Details of"
      };

      $('.adminsearchtitle').focus();

      $scope.userNotFound = false;    /** Set to false by default : error message not displayed**/
      $scope.errorMessage = "Sorry, no record has been found.";

      $scope.showDetails = false; /**set to false by default: the table with user details is not displayed**/
      /*role values*/
      $scope.roles = [];

      $scope.searchadmin={
        fname:"",
        lname:"",
        uname:""
      };
      $scope.fnGetActiveStatus=function(record){
        return record.status==='1'? 'Inactive' : 'Active';
      };
      $scope.adminsearch = angular.copy($scope.searchadmin);

      $scope.fnShowErrorMessage=function(message){
        $scope.userNotFound = true;
        $scope.errorMessage = message;
        $timeout(function(){
        $('.errormessage').focus();
        }, 50);
        $timeout(function(){
          $('.adminsearchtitle').focus();
          $scope.userNotFound = false;
        }, 4000);
      };

      /*clear all the fields and the table */
      $scope.clearSearch = function(){
        $scope.adminsearch=angular.copy($scope.searchadmin);
        $scope.adminSearchUserForm.$setPristine();
        $scope.showDetails = false;
      };

      /*search user based on input fields*/
      $scope.searchUser = function(callback){
        $scope.showDetails = false;
        AdminService.getUserDetails().then(function(result){
          if(result.error){
            $scope.fnShowErrorMessage(result.message);
          }else{
            if(result.data.length === 0){
              $scope.fnShowErrorMessage("Sorry, no record has been found.");
            }else{
              $scope.searchUserDetails = result.data;
              $scope.showDetails = true;
              $('.inputsearch').focus();
            }
          }
          if(callback instanceof Function){
            callback();
          }
        });
      };
      $scope.fnGetRoles=function(record){
        var tempRoles=_.pluck(record.role,'appRole');
        tempRoles = _.filter(tempRoles,function(value){
          if(value.indexOf(ENV.unicornRolePreFix)>-1 && (value.indexOf(ENV.unicornEnterpriseAdminRoleName) === -1 && value.indexOf(ENV.unicornSuperAdminRoleName) === -1)){
            return true;
          }else{
            return false;
          }
        });
        tempRoles = _.map(tempRoles,function(value){
          return value.split(ENV.unicornRolePreFix)[1];
        });
        record.roles = tempRoles;
        return tempRoles.join(', ');
      };

      $scope.editDetails = function(record){
        var option={};
        option = {
          templateUrl: "views/templates/editUserDetails.html",
          controller: "AEditUserCtrl",
          backdrop:'static',
          scope:$scope,
          resolve: {
            record: function () {
              return record;
            },
            roles: function () {
              return $scope.roles;
            }
          }
        };
        AdminService.openModal(option);
      };
      $scope.fnInit=function(){
        $scope.spinnerShow = true;
        UserService.getRoles().then(function(result){
          if(!result.hasOwnProperty('error')){
            $scope.roles = result.data;
          }
          $scope.searchUser(function(){
            $scope.spinnerShow = false;
          });
        });
      };
      $scope.fnInit();
    }]);
