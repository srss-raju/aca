/**
 * Created by speta on 11/24/2015.
 */

'use strict';

angular.module('aca1095BUiAppCW')
  .controller('newProfileRegistrationCtrl',['$scope','$rootScope','$timeout','$state','$log','UrlService','ENV' , function($scope,$rootScope,$timeout,$state,$log,UrlService,ENV){

    var role;

   /*screen labels*/
    $scope.label={
      titleAction : "New",
      titleDescription : "Profile Registration",
      cancel:"Cancel",
      save:"Save",
      managename:"Manage your Name",
      managepassword:"Manage your Password",
      securityques:"Security Questions",
      secdesc:"Please choose three(3) secuirty questions to answer"
    };

    /*aria labels*/
    $scope.arialabel={
      title:"New Profile Registration"
    };

    $('.title').focus();

    $scope.uichange = [
      'First name',
      'Last Name',
      'Email Id',
      'enter the current password',
      'enter the new password with at least 8 characters and containing at least one uppercase letter,one lowercase letter,one number and one special character',
      'enter the confirmed  password',
      'arrow up and arrow down keys to select your security question',
      'Answer the security question selected above',
      'arrow up and arrow down keys to select your security question',
      'Answer the security question selected above',
      'arrow up and arrow down keys to select your security question',
      'Answer the security question selected above'
    ];

    $scope.ubchange = [
      'press enter to save the password and security questions'
    ];

    $scope.userNotFound = false;
    $scope.errorMessage = "User exists in the system. Please try again.";
    $scope.successFlag = false;
    $scope.successMessage = "A new user has been successfully created";
    $scope.spinnerShow = true;

    $scope.$watch('user',function(){
      if($scope.user){
        role = angular.lowercase($scope.user.role);
        $scope.profile={
          application:ENV.unicornApplicationName,
          getFtlUrl:UrlService.getService('GETFTLURL') + $scope.user.email,
          updateFtlUrl:UrlService.getService('UPDATEFTLURL'),
          load:true
        };
      }
    });

    $rootScope.$on('unicornFtlSuccessCast',function(event,args){
      if(args.status===7){
        var i= 2,j= 0,k=0;
        $timeout(function(){
          $(".dsui-unicorn-ftl").find(".col-md-4 h3").eq(1).after('<p class="passwordFont">At least 8 characters and containing at least one uppercase letter,one lowercase letter, one number, and one special character</p>');
          $(".dsui-unicorn-ftl input, .dsui-unicorn-ftl select").each(function () {
            $(this).attr('tabindex', i++);
            $(this).attr('aria-label',$scope.uichange[j++]);
          });
          $(".dsui-unicorn-ftl button").each(function () {
            $(this).attr('tabindex', i++);
            $(this).attr('aria-label',$scope.ubchange[k++]);
          });
        },10);
        $scope.spinnerShow = false;
      }
      if(args.status===2){
        $scope.userNotFound = false;
        $scope.successFlag = true;
        $scope.successMessage = args.message;
        $timeout(function(){
          $scope.semessage = args.message;
          $('.semessageflag').focus();
        }, 100);
        $timeout(function(){
          location.href = ENV.logout;
        },3000);
      }
    });

    $rootScope.$on('unicornFtlErrorCast',function(event,args){

       if(args.message.indexOf('Password must be at least 8 characters long and contain at least one letter, one number, and one special character') != -1)
      {
        args.message = 'Password must be 8-15 characters long and should include at least one lowercase letter, one uppercase letter, one number, and one special character';
      }
      $scope.userNotFound = true;
      $scope.successFlag = false;
      $scope.errorMessage = args.message;
      $scope.spinnerShow = false;
      $timeout(function(){
        $scope.semessage = args.message;
        $('.semessageflag').focus();
      }, 100);
      $timeout(function(){
        $scope.userNotFound = false;
        $('.title').focus();
      }, 11000);

    });

  }]);
