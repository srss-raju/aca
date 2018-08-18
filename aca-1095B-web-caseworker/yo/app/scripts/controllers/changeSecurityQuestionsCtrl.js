/**
 * Created by fasaleem on 12/14/15.
 */

'use strict';

angular.module('aca1095BUiAppCW')
  .controller('changeSecurityQuestionsCtrl',['$scope', '$rootScope', '$state', '$timeout','UrlService','ENV','CommonService',function($scope, $rootScope, $state, $timeout,UrlService,ENV,CommonService) {

    $scope.showSuccessMessage = false;
    $scope.showErrorMessage = false;

    $scope.label= {
      titleAction: "Change",
      titleDescription: "Security Questions"
    };

    $scope.profile = {};
    $scope.profile.securityQuestions = {
      loadNewData: false
    };

    $scope.arialabel={
      title:"Change Security Questions"
    };

    $scope.uichange = ['arrow up and arrow down keys to select your security question','Answer the security question selected above'];
    $scope.ubchange = ['press enter to cancel and go back without changing the security questions','press enter to save the new security questions'];

    $(".securitytitle").focus();
    $scope.spinnerShow = true;

    $rootScope.$on('unicornSecurityQuestionsSuccessCast', function(event, args) {
      if (args.status === 2) {
        CommonService.setSuccessMessage(args.message);
        $state.go('state.user-profile');
      }
      else if(args.status===5){
        var i= 3,j= 0,z=0;
        $scope.spinnerShow = false;
        $timeout(function(){
          $(".dsui-unicorn-security-questions input,.dsui-unicorn-security-questions select").each(function () {
            $(this).attr('tabindex', i++);
            $(this).attr('aria-label',$scope.uichange[j++]);
            if(j==2){
              j=0;
            }
          });
          $(".dsui-unicorn-security-questions button").each(function () {
            $(this).attr('tabindex', i++);
            $(this).attr('aria-label',$scope.ubchange[z++]);
          });
          $('.dsui-unicorn-security-questions').find('.form-group').eq(0).find('p').eq(1).empty().append('These security questions will help us verify your identity.');
        },10);
      }
      else if(args.status===1){
        $state.go('state.user-profile');
      }
    });

    $rootScope.$on('unicornSecurityQuestionsErrorCast', function(event, args) {
        $scope.showErrorMessage = true;
        $scope.errorMessage = args.message;
        $timeout(function(){
          $('.errormessage').focus();
        }, 100);
    });

    $scope.$watch('user', function () {
      if ($scope.user) {
        $scope.profile.securityQuestions = {
          application : ENV.unicornApplicationName,
          getSecureQuestionsUrl: UrlService.getService('GETCHANGESECURITYQUESTIONSURL') + $scope.user.email,
          submitSecurityQuestionsUrl:UrlService.getService('SUBMITSECURITYQUESTIONSURL'),
          loadNewData: true
        };
      };
    });

  }]);
