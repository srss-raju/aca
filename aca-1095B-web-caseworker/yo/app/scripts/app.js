'use strict';

/**
 * @ngdoc overview
 * @name aca1095BUiAppCW
 * @description
 * # aca1095BUiAppCW
 *
 * Main module of the application.
 */

/*messages could be localized see i18n for angular*/
var messages = {
  required: "This is a required field",
  minlength: "At least @value@ characters",
  maxlength: "No more than @value@ characters",
  pattern: "Invalid character",
  "email": "Email address is invalid",
  "number": "insert only numbers",
  "custom": "custom not valid type \"@value@\"",
  "async": "async not valid type \"@value@\""
};

function mainCtrl($scope, textModal, confirmationDialog) {
  $scope.privacyStatement = function(){
    var modalAttribute = {
      windowClass: 'center-modal',
      animation: true,
      backdrop: 'static',
      keyboard: false,
      label : {
        "title" : "Privacy Statement",
        "content" : "views/templates/privacyStatement.html",
        "isHTML" : true
      },
      modalSize : 'larger',
      modalHeight : 'fullscreen'
    };
    textModal.openModal(modalAttribute);
  };

  $scope.termsOfUse = function(){
    var modalAttribute = {
      windowClass: 'center-modal',
      animation: true,
      backdrop: 'static',
      keyboard: false,
      label : {
        "title" : "Terms Of Use",
        "content" : "views/templates/termsOfUse.html",
        "isHTML" : true
      },
      modalSize : 'larger',
      modalHeight : 'fullscreen'
    };
    textModal.openModal(modalAttribute);
  };

  $scope.$watch(function () {
    return confirmationDialog.modalBox
  }, function (newVal, oldVal) {
    if (typeof newVal !== 'undefined') {
      $scope.modalBox = confirmationDialog.modalBox;
    }
  });

}

angular.module('aca1095BUiAppCW', [
  'ngAnimate',
  'ngSanitize',
  'ui.router',
  'ui.bootstrap',
  'config',
  'unicorn-forgot-password',
  'unicorn-first-time-login',
  'unicorn-security-questions',
  'unicorn-init',
  'unicorn-change-password',
  'ngFloatingLabels'])
  .config(function ($stateProvider,$httpProvider, $urlRouterProvider,UrlServiceProvider,ENV) {
    UrlServiceProvider.setOptions({'Environment':ENV});
    //initialize if not there
    if (!$httpProvider.defaults.headers.common) {
      $httpProvider.defaults.headers.common = {};
    }
    //disable IE ajax request caching
    $httpProvider.defaults.headers.common['If-Modified-Since'] = 'Mon, 26 Jul 1997 05:00:00 GMT';
    // extra
    $httpProvider.defaults.headers.common['Cache-Control'] = 'no-cache';
    $httpProvider.defaults.headers.common['Cache-Control'] = 'no-store';
    $httpProvider.defaults.headers.common.pragma = 'no-cache';

    $stateProvider
      // login
      .state('siteminder-login', {
        url:'/siteminder-login',
        controller: 'SiteMinderCtrl'
      })
      .state('login', {
        url:'/login',
        templateUrl: 'views/login.html',
        controller: 'CWLoginCtrl'
      })
      .state('login-success', {
        url:'/login-success',
        templateUrl: 'views/login-success.html',
        controller: 'LoginSuccessCtrl'
      })
      .state('loginerror', {
        url:'/loginerror',
        templateUrl: 'views/loginerror.html'
      })
      .state('login-inactive', {
        url:'/login-inactive',
        templateUrl: 'views/login-inactive.html'
      })

      // case worker admin portal
      .state('state', {
        url:'/state',
        templateUrl: 'views/state.html',
        controller: 'StateCtrl',
        abstract: true
      })
      .state('state.cw-search-user', {
        url:'/cw-search-user',
        templateUrl: 'views/cw-search-user.html',
        controller: 'CWSearchUserCtrl',
        controllerAs:'su',
        data: {
          id : "cw_search_user",
          displayName: "Search Recipient",
          stateName : "state.cw-search-user",
          isParent : true
        }
      })
      .state('state.cw-view-user', {
        url:'/cw-view-user',
        templateUrl: 'views/cw-view-user.html',
        controller: 'CWViewUser',
        data: {
          id : "cw_search_user.cw_view_user",
          displayName : "View 1095-B Form",
          stateName : "state.cw-view-user",
          isParent : false
        }
      })
      .state('state.cw-view-current-form', {
        url:'/cw-view-current-form',
        templateUrl: 'views/cw-view-current-form.html',
        controller: 'CWViewCurrentForm',
        data: {
          id : "cw_search_user.cw_view_user.cw-view-current-form",
          displayName : "View Current Form",
          stateName : "state.cw-view-current-form",
          isParent : false
        }
      })
      .state('state.cw-view-historic-form', {
        url:'/cw-view-historic-form',
        templateUrl: 'views/cw-view-historic-form.html',
        controller: 'CWViewHistoricForm',
        data: {
          id : "cw_search_user.cw_view_user.cw-view-historic-form",
          displayName : "View Historic Form",
          stateName : "state.cw-view-historic-form",
          isParent : false
        }
      })
      .state('state.cw-view-source-data', {
        url:'/cw-view-source-data',
        templateUrl: 'views/cw-view-source-data.html',
        controller: 'CWViewSourceDataCtrl',
        data: {
          id : "cw_search_user.cw_view_user.cw_view_source_data",
          displayName: "View Recipient Data",
          stateName : "state.cw-view-source-data",
          isParent : false
        }
      })
      .state('state.cw-correct-data', {
        url:'/cw-correct-data',
        templateUrl: 'views/cw-correct-data.html',
        controller: 'CWCorrectData',
        data: {
          id : "cw_search_user.cw_view_user.cw_view_source_data.cw-correct-data",
          displayName: "Edit Recipient Data",
          stateName : "state.cw-correct-data",
          isParent : false
        }
      })
      .state('state.cw-view-form', {
        url: '/cw-view-form',
        templateUrl: 'views/cw-view-form.html',
        controller: 'CWViewForm',
        controllerAs: 'vm',
        data: {
          id: "cw_search_user.cw_view_user.cw-view-form",
          displayName: "View/Print 1095-B Form",
          stateName: "state.cw-view-form",
          isParent: false
        }
      })


      //admin portal
      .state('state.a-dashboard',{
        url:'/a-dashboard',
        templateUrl:'views/a-dashboard.html',
        controller:'ADashboardCtrl',
        data: {
          id : "a_dashboard",
          displayName : "Administration Dashboard",
          stateName : "state.a-dashboard",
          isParent : true
        }
      })
      .state('state.a-create-user',{
        url:'/a-create-user',
        templateUrl:'views/a-create-user.html',
        controller:'ACreateUserCtrl',
        data: {
          id : "a_dashboard.state.a_create_user",
          displayName : "Create User",
          stateName : "state.a-create-user",
          isParent : false
        }
      })
      .state('state.a-search-user',{
        url:'/a-search-user',
        templateUrl:'views/a-search-user.html',
        controller:'ASearchUserCtrl',
        data: {
          id : "a_dashboard.a_search_user",
          displayName : "Search User",
          stateName : "state.a-search-user",
          isParent : false
        }
      })

      // xml status
      .state('operations', {
        url:'/operations',
        templateUrl: 'views/operations.html',
        controller: 'operationsCtrl',
        controllerAs: 'vm',
        abstract: true
      })
      .state('operations.xml-status', {
        url:'/xml-status',
        templateUrl: 'views/xml-status.html',
        controller: 'OPXmlStatusCtrl',
        controllerAs: 'vm',
        data: {
          id : "operations.xml-status",
          displayName : "Operations Team Portal",
          stateName : "operations.xml-status",
          isParent : true
        }
      })

      /*common for all the screens*/
      .state('state.terms-and-conditions', {
        url: '/terms-and-conditions',
        templateUrl: 'views/terms-and-conditions.html',
        controller: 'termsAndConditionsCtrl',
        controllerAs:'tc'
      })
      .state('state.new-profile-registration', {
        url: '/new-profile',
        templateUrl: 'views/new-profile-registration.html',
        controller: 'newProfileRegistrationCtrl'
      })
      .state('state.user-profile', {
        url: '/user-profile',
        templateUrl: 'views/user-profile.html',
        controller: 'userProfileCtrl',
        data: {
          id : "user-settings.user-profile",
          displayName : "User Profile",
          stateName : "state.user-profile",
          isParent : false
        }
      })
      .state('state.change-password', {
        url: '/change-password',
        templateUrl: 'views/change-password.html',
        controller: 'changePasswordCtrl'
      })
      .state('state.change-expired-password', {
        url: '/change-expired-password',
        templateUrl: 'views/change-expired-password.html',
        controller: 'changeExpiredPassword',
        controllerAs: 'vm'
      })
      .state('state.change-security-questions', {
        url: '/change-security-questions',
        templateUrl: 'views/change-security-questions.html',
        controller: 'changeSecurityQuestionsCtrl'
      })
      .state('forgot-password', {
        url: '/forgot-password',
        templateUrl: 'views/forgot-password.html',
        controller: 'forgotPasswordCtrl'
      });

    $urlRouterProvider.otherwise( function($injector, $location) {
      var $state = $injector.get("$state");
      //var url = $location.url();
      //var absUrl = $location.absUrl();

      //Temp-Site
      $state.go('siteminder-login');
      //$state.go('state.cw-search-user');

    });
  })
  .controller('mainCtrl',[
    '$scope',
    'textModal',
    'confirmationDialog',
    mainCtrl]);
