/**
 * Created by pchavan on 3/10/2016.
 */
'use strict';

(function () {

  function changeExpiredPassword ($scope, $element, $log, CommonService, ENV, unicornServices, $timeout){
    /** Allow user to land on this screen only when userInfo is available **/
    if(CommonService.userInfo){
      $log.info(CommonService.userInfo);
    }else{
      //$location.path(ENV.logout);
      location.href = ENV.logout;
    }

    var vm = this;

    var unicornApi = unicornServices;

    /** Screen Label Initialization **/
    vm.label = {
      titleAction : "Change",
      titleDescription : "Expired Password",

      newPassword : "New Password",
      confirmPassword : "Confirm Password",

      saveButton : "Save",
      cancelButton : "Cancel",

      passwordPolicy : "At least 8 characters and containing at least one uppercase letter,one lowercase letter,one number and one special character."

    };

    vm.ariaLabel = {
      title : "Please change the password since your password has expired. ",
      newPassword : "Enter the new password with at least 8 characters and containing at least one uppercase letter,one lowercase letter,one number and one special character.",
      confirmPassword : "Enter the confirmed  password."
    };

    /** variable initialization **/
    vm.userData = {
      newPassword : null,
      confirmPassword : null,
      emailId : ""
    };

    vm.userData.emailId = CommonService.userInfo.email || "";
    vm.successData = {};
    vm.successMessage = "";
    vm.errorMessage = "";

    /** Error message mapping : To be tested **/

    vm.errorMessageList = [
      {"unicornMessage" : "Password has been previously used. Please input a new password", "aca1095Message" : "Please input a new password. Do not use any of your 24 previous passwords."},
      {"unicornMessage" : "Previous password cannot be reused and can change only once per day.", "aca1095Message" : "Previous password cannot be reused and can change only once per day."},
      {"unicornMessage" : "Cannot reuse the default password.", "aca1095Message" : "Cannot reuse the default password."}
    ];

    /** flags to show hide success and error message **/
    vm.showSuccessMessage = false;
    vm.showErrorMessage = false;
    vm.passwordsNotMatch = false;
    vm.passwordNotCompliant = false;
    vm.emptyFields = true;

    /** Validate Password **/
    vm.validate = {};
      /** Compare if new and confirmed password are the same **/
      vm.validate.validateConfirmPassword=function(newPassword,confirmPassword){
        if((confirmPassword !== null && typeof(confirmPassword) !== 'undefined') && (confirmPassword !== null && typeof(confirmPassword) !== 'undefined')){
          return newPassword === confirmPassword;
        }else{
          return false;
        }
      };
      /** Check Password Policy **/
      vm.validate.validatePassword=function(password){
        if(password !== null && typeof(password) !== 'undefined'){
          if(password.length>7 && password.length<15){
            var passwordRegEx = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*(_|[^\w])).+$/;
            return passwordRegEx.test(password);
          }else{
            return false;
          }
        }else{
          return false;
        }
      };
      vm.validate.checkOnUpdate = function(userData){
        vm.passwordNotCompliant = !vm.validate.validatePassword(userData.newPassword);

        vm.passwordsNotMatch = !vm.validate.validateConfirmPassword(userData.newPassword, userData.confirmPassword);
      };

    /** Save new Password Enable**/
    vm.enableSavePassword = function(userData){
      /*$log.info("enableSavePassword", userData);*/
      vm.emptyFields = userData.newPassword === null || userData.newPassword === '' || userData.confirmPassword === null || userData.confirmPassword === '';
    };

    /*** Map error message : To be tested**/
    vm.mapErrorMessage = function(errormsg){
        if(errormsg !== null && errormsg.length){
          if(errormsg.indexOf("Password has been previously used. Please input a new password") != -1){
            return "Please input a new password. Do not use any of your 24 previous passwords.";
          }else if(errormsg.indexOf("Previous password cannot be reused and can change only once per day") != -1){
            return "Previous password cannot be reused and can change only once per day.";
          }else if(errormsg.indexOf("Cannot reuse the default password") != -1){
            return "Cannot reuse the default password..";
          }else{
            return "Unknown error occurred. Please logout and try later.";
          }
        }else{
          return "Unknown error occurred. Please logout and try later.";
        }
    };

    /*** Save new Password **/
    vm.updateExpiredPassword = function(userData){
      /*$log.info("updateExpiredPassword");*/
      vm.successData = {};
      vm.validate.checkOnUpdate(userData);
      if(vm.passwordNotCompliant){
        vm.showSuccessMessage = false;
        vm.showErrorMessage = true;
        vm.errorMessage = "Please enter valid password in accordance to the password policy.";
      }else if(!vm.passwordNotCompliant && vm.passwordsNotMatch){
        vm.showSuccessMessage = false;
        vm.showErrorMessage = true;
        vm.errorMessage = "Please enter same new and confirm password.";
      }else{
        unicornApi.updateExpiredPassword(userData)
          .then(function (response) {
            /*$log.info("success:  ", response);*/
            vm.successData = response;
            if(vm.successData.status === '200'){
              vm.showSuccessMessage = true;
              vm.showErrorMessage = false;
              vm.successMessage = "Password updated successfully.";
              $timeout(function(){
                if($('.notificationMessage').length){
                  $('.notificationMessage').focus();
                }
              }, 50);
              $timeout(function(){
                location.href = ENV.logout;
              }, 3000);
            }else if(vm.successData.status === '500'){
              vm.showSuccessMessage = false;
              vm.showErrorMessage = true;
              vm.errorMessage = vm.mapErrorMessage(vm.successData.errormsg);
              $timeout(function(){
                if($('.notificationMessage').length){
                  $('.notificationMessage').focus();
                }
              }, 50);
            }else{
              vm.showSuccessMessage = false;
              vm.showErrorMessage = true;
              vm.errorMessage = "Unknown error occurred. Please logout and try later.";
              $timeout(function(){
                if($('.notificationMessage').length){
                  $('.notificationMessage').focus();
                }
              }, 50);
            }
          }, function () {
            vm.showSuccessMessage = false;
            vm.showErrorMessage = true;
            vm.errorMessage = "Unknown error occurred. Please logout and try later.";
            $timeout(function(){
              if($('.notificationMessage').length){
                $('.notificationMessage').focus();
              }
            }, 50);
          });
      }
    };

    /** Go back or cancel **/
    vm.cancel = function(){
      /*$log.info("cancel");*/
      location.href = ENV.logout;
    };

  }

  angular.module('aca1095BUiAppCW')
    .controller('changeExpiredPassword', ['$scope', '$element', '$log', 'CommonService', 'ENV', 'unicornServices', '$timeout',changeExpiredPassword]);
}());
