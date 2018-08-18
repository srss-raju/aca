/**
 * Created by fasaleem on 12/14/15.
 */

'use strict';

angular.module('aca1095BUiAppCW')
  .controller('forgotPasswordCtrl',['$scope','$rootScope','UrlService','$timeout','ENV',function($scope,$rootScope,UrlService,$timeout,ENV) {

    /*screen labels*/
    $scope.label={
      titleAction : "Forgot",
      titleDescription : "Password",
    };

    /*aria labels*/
    $scope.arialabel={
      title:"Forgot Password"
    };

    $('.title').focus();

    $scope.userNotFound = false;
    $scope.errorMessage = "User exists in the system. Please try again.";
    $scope.successFlag = false;
    $scope.successMessage = "A new user has been successfully created";
    $scope.spinnerShow = true;

    $scope.profile={
      application:"1095B",
      getProfileUrl:UrlService.getService('GETPROFILEURL'),
      getSecurityQuestionsUrl:UrlService.getService('GETSECURITYQUESTIONSURL'),
      updatePasswordUrl:UrlService.getService('UPDATEPASSWORDURL')
    };

    $rootScope.$on('unicornForgotPasswordSuccessCast',function(event,args){
      $scope.userNotFound = false;
      if(args.status===8){
        var i=3;
        $(".dcui-forgotpassword-well input").each(function () {
          $(this).attr('tabindex', i++);
        });
        $(".dcui-forgotpassword-well button").each(function () {
          $(this).attr('tabindex', i++);
        });
        $('.dsui-forgotpassword-well h5').eq(2).text('Please input a new password. Do not use any of your 24 previous passwords.');
        $scope.spinnerShow = false;
      }
      else if(args.status===4){
        $scope.userNotFound = false;
        $scope.successFlag = true;
        $scope.successMessage = args.message;
        $timeout(function(){
          $('.successmessage').focus();
        }, 1000);
        $timeout(function(){
          location.href = ENV.logout;
        }, 5000);
      }
    });

    $rootScope.$on('unicornForgotPasswordErrorCast',function(event,args){
      if(args.message.indexOf('benchmarking@deloitte.com') != -1)
      {
        args.message = args.message.replace('benchmarking@deloitte.com','us1095bsupport@deloitte.com');
      }
      if(args.message.indexOf('Password must be 8-20 characters long and should include a letter, a symbol and a number') != -1)
      {
        args.message = 'Password must be 8-15 characters long and should include at least one lowercase letter, one uppercase letter, one number, and one special character';
      }
      $scope.userNotFound = true;
      $scope.successFlag =false;
      $scope.errorMessage = args.message;
      $timeout(function(){
        $('.errormessage').focus();
      }, 1000);
      $timeout(function(){
        $('.title').focus();
      }, 6000);
      $timeout(function(){
        $scope.userNotFound = false;
        $scope.successFlag =false;
      }, 15000);
    });

  }]);
