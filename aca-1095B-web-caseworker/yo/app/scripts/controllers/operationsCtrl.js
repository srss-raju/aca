/**
 * Created by pchavan on 3/28/2016.
 */

'use strict';

(function(){
  function operationsCtrl($scope, $rootScope, ENV, $timeout, $document, $state, UserService, operationsPortal, confirmationDialog){
    var vm = this;
    vm.operationsPortal = operationsPortal;
    vm.operationsPortal.showDropDown = false;
    /* TIMEOUT time 15 minutes  =  900000 ms*/
    vm.inactivityTime = 900000;
    /** Application logout on timeout **/
    vm.closeModalBox = function(){
      confirmationDialog.modalBx.close();
    };
    vm.applicationLogoutConfirm = function(){
      if(vm.operationsPortal.unsavedChanges){
        vm.operationsPortal.showDropDown = false;
        var modalAttribute = {
          windowClass: 'center-modal',
          animation: false,
          templateUrl: 'views/templates/cautionDialog.html',
          backdrop: 'static',
          keyboard: false,
          label : {
            title: 'Are you sure ?',
            noAction: 'Cancel',
            yesAction: 'OK',
            loading: false,
            description: 'Are you sure you want to navigate away from this page? If you continue, your unsaved changes will be lost. Press OK to exit or cancel to stay on the current page. '
          }
        };
        confirmationDialog.openModal(modalAttribute, vm.applicationLogout, vm.closeModalBox, vm.closeModalBox);
      }else{
        vm.applicationLogout();
      }
    };
    vm.applicationLogout = function(){
      vm.operationsPortal.showDropDown = false;
      location.href = ENV.logout;
    };

    vm.timeOut = $timeout(function() {
        vm.applicationLogout();
    } , vm.inactivityTime);

    vm.timeOutCancel = function(){
      $timeout.cancel(vm.timeOut);
      vm.timeOut = $timeout(function() {
        vm.applicationLogoutConfirm();
      } , vm.inactivityTime);
    };

    angular.forEach(['keydown', 'keyup', 'click', 'mousemove', 'DOMMouseScroll', 'mousewheel', 'mousedown', 'touchstart', 'touchmove', 'scroll', 'focus'],
      function(EventName) {
        angular.element($document).bind(EventName, function () {
          vm.timeOutCancel();
        });
      });

    /** user profile dropdown **/
    vm.myProfileView = function(){
      vm.operationsPortal.showDropDown = false;
      $state.go('state.user-profile');
    };
    vm.openDropdown = function(){
      vm.operationsPortal.showDropDown = true;
      if (!$scope.$$phase){
        $scope.$apply();
      }
    };
    $(document).on('click', function(e){
      if((e.target.className.baseVal != undefined && e.target.className.baseVal === "")
        || e.target.className.indexOf('action-button') === -1){
        vm.operationsPortal.showDropDown = false;
        if (!$scope.$$phase){
          $scope.$apply();
        }
      }
    });

    //capturing the user inactivity
    $rootScope.$on('$stateChangeError', function (event) {
      if (!UserService.getUserInfo()) {
        event.preventDefault();
        location.href = ENV.login;
      }
    });

    UserService.checkLogin()
      .then(function (userInfo) {
        if(userInfo === '') {
          /* $state.go('siteminder-login');*/
        } else {
          vm.user = userInfo;
          vm.hideMyProfile = (userInfo.firstTimeLogin || (userInfo.expiryDate === 'Expired' && userInfo.expired));
        }
      });

  }
  function operationsPortal(){
    var service = {};
    service.showDropdown = false;
    service.unsavedChanges = false;

    return service;
  }
  angular.module('aca1095BUiAppCW')
    .controller('operationsCtrl', ['$scope', '$rootScope', 'ENV', '$timeout', '$document', '$state', 'UserService', 'operationsPortal', 'confirmationDialog', operationsCtrl])
    .service('operationsPortal', operationsPortal);
}());
