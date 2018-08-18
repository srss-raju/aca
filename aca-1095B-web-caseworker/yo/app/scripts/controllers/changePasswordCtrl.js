/**
 * Created by fasaleem on 12/14/15.
 */

'use strict';

angular.module('aca1095BUiAppCW')
  .controller('changePasswordCtrl',['$scope', '$rootScope', '$log','$state','ENV','UrlService','$timeout','CommonService',function($scope, $rootScope, $log,$state,ENV,UrlService,$timeout,CommonService) {

    $('.passwordtitle').focus();

    $scope.showSuccessMessage = false;
    $scope.showErrorMessage = false;

    $scope.label= {
      titleAction: "Change",
      titleDescription: "Password"
    };

    $scope.profile = {};
    $scope.profile.updatePassword = {
      loadNewData: false
    };

    /*aria labels*/
    $scope.arialabel={
      title:"Change the Password",
    };

   /* $scope.uchangepass = {
      cpassword : 'enter the current password',
      npassword: 'enter the new password',
      ncpassword : 'confirm the password',
      cancel :'cancel to go back without changing the password',
      save:'save the new password'
    }
*/
    $scope.uchangepass = ['enter the current password', 'enter the new password with at least 8 characters and containing at least one uppercase letter,one lowercase letter,one number and one special character','enter the confirmed  password']
    $scope.unchange= ['cancel to go back without changing the password','save the new password'];

    $scope.spinnerShow = true;

    $rootScope.$on('unicornPasswordSuccessCast', function(event, args) {
      if (args.status === 2) {
        CommonService.setSuccessMessage(args.message);
        if(!CommonService.changePasswordFlag){
          $state.go('state.user-profile');
        }else{
          location.href = ENV.logout;
        }
      }
      else if(args.status===8){
        $scope.spinnerShow = false;
        var i= 3,j= 0,z=0;
        $timeout(function(){
          $(".dsui-unicorn-change-password input").each(function () {
            $(this).attr('tabindex', i++);
            $(this).attr('aria-label',$scope.uchangepass[j++]);
          });
          $(".dsui-unicorn-change-password button").each(function () {
            $(this).attr('tabindex', i++);
            $(this).attr('aria-label',$scope.unchange[z++]);
          });
          $('.dsui-font1').empty().append('At least 8 characters and containing at least one letter,one number and one special character');
          $(".dsui-unicorn-change-password input").eq(0).focus();
        },10);
      }
      else if(args.status===1){
        if(!CommonService.changePasswordFlag){
          $state.go('state.user-profile');
        }else{
          location.href = ENV.logout;
        }

      }
    });

    $rootScope.$on('unicornPasswordErrorCast', function(event, args) {
      if(args.message.indexOf('benchmarking@deloitte.com') != -1)
      {
        args.message = args.message.replace('benchmarking@deloitte.com','us1095bsupport@deloitte.com');
      }
      if(args.message.indexOf('Password must be at least 8 characters long and contain at least one letter, one number, and one special character') != -1)
      {
        args.message = 'Password must be 8-15 characters long and should include at least one lowercase letter, one uppercase letter, one number, and one special character';
      }
      $scope.showErrorMessage = true;
      $scope.errorMessage = args.message;
      $timeout(function(){
        $('.errormessage').focus();
      }, 1000);
      $timeout(function(){
        $('.passwordtitle').focus();
        $scope.showErrorMessage = false;
      }, 10000);
    });

    $scope.$watch('user', function () {
      if ($scope.user) {
        $scope.profile.updatePassword = {
          application : ENV.unicornApplicationName,
          updatePasswordUrl: UrlService.getService('MODIFYUSERPASSWORD'),
          email: $scope.user.email,
          loadNewData: true
        };
      };
    });
  }]);
