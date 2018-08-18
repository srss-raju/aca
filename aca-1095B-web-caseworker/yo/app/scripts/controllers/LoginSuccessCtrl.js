/**
 * Created by sdalavi on 12/7/2015.
 */
'use strict';

(function () {
  var aca1095BUiAppCW = angular.module('aca1095BUiAppCW');
  aca1095BUiAppCW.controller('LoginSuccessCtrl', ['$scope', '$rootScope', 'UserService', 'UrlService', '$state', '$log', 'ENV', 'CommonService',
    function ($scope, $rootScope, UserService, UrlService, $state, $log, ENV, CommonService) {

      var adminrole = null;

      adminrole = angular.lowercase(ENV.state_admin);
      $scope.profile = {
        entityUrl: UrlService.getService('ENTITYURL'),
        sessionUrl: UrlService.getService('SESSIONURL'),
        email: null,
        load: false,
        userSessionUrl: UrlService.getService('USERSESSIONURL') + '?time=' + new Date().getTime()
      };

      $scope.spinnerShow = true;

      $rootScope.$on('unicornInitializeSuccessCast', function (event, args) {
        if (args.status === 5) {
          $scope.goToLogin($scope.userInfo);
          $scope.spinnerShow = false;
        }
      });

      $rootScope.$on('unicornInitializeErrorCast', function (event, args) {
        if (args.status === 4) {
          $scope.spinnerShow = false;
          location.href = ENV.logout;
        }
      });

      $scope.goToLogin = function (userInfo) {
        var isStateAdmin = false;
        if (userInfo.stringRoles) {
          for (var i = 0; i < userInfo.stringRoles.length || 0; i++) {
            if (userInfo.stringRoles[i].toLowerCase().indexOf(adminrole) > -1) {
              isStateAdmin = true;
              break;
            }
          }
        }

        // first time login
        if (userInfo.firstTimeLogin && userInfo.expiryDate !== "Expired") {
          $state.go('state.terms-and-conditions');
        } else if (userInfo.expired && userInfo.expiryDate === "Expired") {
          CommonService.changePasswordFlag = angular.copy(userInfo.expired);
          CommonService.userInfo = angular.copy(userInfo);
          $state.go('state.change-expired-password');
        } else if (isStateAdmin){
          $state.go('state.a-dashboard');
        } else if(userInfo.state === '1095B_OPS' && userInfo.role === '1095B_OPS') {
          $state.go('operations.xml-status');
        } else {
          $state.go('state.cw-search-user');
        }

        /*if(userInfo.firstTimeLogin) {
          $state.go('state.terms-and-conditions');
        } else if(isStateAdmin) {
          $state.go('state.a-dashboard');
        } else {
          $state.go('state.cw-search-user');
        }*/

      };

      // route to first time login or (admin or caseworker portal)
       UserService.checkLogin()
       .then(function (userInfo) {
       if(userInfo && userInfo !== '') {
       $scope.profile.email = userInfo.email;
       $scope.profile.sessionUrl = $scope.profile.sessionUrl + userInfo.email;
       $scope.profile.load = true;
       $scope.userInfo = userInfo;
       }
       });


    }]);
}());
