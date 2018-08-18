/**
 * Created by sdalavi on 12/7/2015.
 */
'use strict';

(function () {
  var aca1095BUiAppCW = angular.module('aca1095BUiAppCW');
  aca1095BUiAppCW.controller('SiteMinderCtrl', ['$scope','UserService','ENV', function($scope,UserService,ENV) {

    $scope.login = {
      errorMessage: null,
      userNotFound: false,
    };

    /*checks if there is an error*/
    if(UserService.getLoginErrorFlag()){
      $scope.login.userNotFound = true;
      $scope.login.errorMessage = 'Please provide valid credentials';
    }
      // re-directed to site minder login
      //location.href = './saml/login/alias/ACA1095BSP-DD_EntityAlias';
      location.href = ENV.login;
    }]);
}());
