/**
 * Created by sdalavi on 12/7/2015.
 */
'use strict';

(function () {
  function StateCtrl($scope, $rootScope,$timeout,$document, UserService, $state,ENV,StateService, confirmationDialog) {
    $scope.stateService = StateService;
    $scope.stateService.showDropDown = false;
    $scope.appLogOut = function(){
      //location.href = './saml/logout/alias/ACA1095BSP-DD_EntityAlias';
      $scope.stateService.showDropDown = false;
      location.href = ENV.logout;
    };
    $scope.myProfileView = function(){
      $scope.stateService.showDropDown = false;
      $state.go('state.user-profile');
    };
    $scope.openDropdown = function(){
      $scope.stateService.showDropDown = true;
      if (!$scope.$$phase){
        $scope.$apply();
      }
    };

    $scope.$watch(function () { return confirmationDialog.modalBox }, function (newVal, oldVal) {
      if (typeof newVal !== 'undefined') {
        $scope.modalBox = confirmationDialog.modalBox;
      }
    });

    //capturing the user inactivity
    $rootScope.$on('$stateChangeError', function (event) {
      if (!UserService.getUserInfo()) {
        event.preventDefault();
        //Temp-Site
        location.href = ENV.login;
      }
    });

    // Timeout timer value
    var TimeOutTimerValue = 900000; //15 mins

    // Start a timeout
    var TimeOut_Thread = $timeout(function() {
      LogoutByTimer();
    } , TimeOutTimerValue);

    var bodyElement = angular.element($document);
    angular.forEach(['keydown', 'keyup', 'click', 'mousemove', 'DOMMouseScroll', 'mousewheel', 'mousedown', 'touchstart', 'touchmove', 'scroll', 'focus'],
      function(EventName) {
        bodyElement.bind(EventName, function (e) {
          TimeOut_Resetter(e);
        });
      });

    function LogoutByTimer(){
      /// redirect to another page(eg. login-form.html) here
      location.href = ENV.logout;
    }

    function TimeOut_Resetter(e){
      /// Stop the pending timeout
      $timeout.cancel(TimeOut_Thread);

      /// Reset the timeout
      TimeOut_Thread = $timeout(function(){
        LogoutByTimer();
      } , TimeOutTimerValue);
    }

    $(document).on('click', function(e){
      /*console.log(e.target, e.target.className.indexOf('action-button'));*/
      if(e.target.className.indexOf('action-button') === -1){
        $scope.stateService.showDropDown = false;
        if (!$scope.$$phase){
          $scope.$apply();
        }
      }
    });
    // route to login page
    UserService.checkLogin()
      .then(function (userInfo) {
        if(userInfo === '') {
         /* $state.go('siteminder-login');*/
        } else {
          $scope.user = userInfo;
          $scope.hideMyProfile = (userInfo.firstTimeLogin || (userInfo.expiryDate === 'Expired' && userInfo.expired));
        }
      });
  }

  function StateService(){
    var service = {};
    service.showDropdown = false;

    return service;
  }

  angular.module('aca1095BUiAppCW')
    .controller('StateCtrl', ['$scope','$rootScope','$timeout','$document', 'UserService', '$state','ENV','StateService', 'confirmationDialog', StateCtrl])
    .service('StateService', StateService);
}());
