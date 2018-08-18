/**
 * Created by fasaleem on 12/14/15.
 */


'use strict';

angular.module('aca1095BUiAppCW')
  .controller('userProfileCtrl', ['$scope', '$rootScope', '$state','ENV','CommonService','$timeout', function ($scope, $rootScope, $state,ENV,CommonService,$timeout) {

    var role = null,
      userrole=null;

    $scope.showSuccessMessage = false;
    $('.title').focus();

    $scope.successMessage = CommonService.getSuccessMessage();
    if($scope.successMessage){
      $scope.showSuccessMessage = true;
      CommonService.setSuccessMessage('');
      $('.successmessage').focus();
      $timeout(function(){
          $('.title').focus();
        $scope.showSuccessMessage = false;
      },6000);
    }

    $scope.$watch('user', function () {
      if ($scope.user) {
        $scope.profile = {
          firstName: $scope.user.firstName,
          lastName: $scope.user.lastName,
          email: $scope.user.email
        };
        role = angular.lowercase($scope.user.role);
        //aria labels
        $scope.arialabel = {
          title:'This is the profile of ' + $scope.user.firstName + $scope.user.lastName,
          changepassword:'Press enter to go to change password screen',
          changesecurity:'Press enter to go to change the security questions screen',
          cancel:'Press enter to go back'
        }
      };
    });

    $scope.goBack = function() {
      if (role !== null) {
        userrole = ENV.unicornRolePreFix + ENV.state_admin;
        userrole = angular.lowercase(userrole);
        // first time login
        if (role === userrole) {
          $state.go('state.a-dashboard');
        } else {
          $state.go('state.cw-search-user');
        }
      }
    }
  }]);
