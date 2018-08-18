'use strict';

/**
 * @ngdoc overview
 * @name aca1095BUiAppCitizen
 * @description
 * # aca1095BUiAppCitizen
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

function mainCtrl($scope, textModal, CitizenInactive){
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

  $scope.$watch(function () { return CitizenInactive.modalBox }, function (newVal, oldVal) {
     if (typeof newVal !== 'undefined') {
         $scope.modalBox = CitizenInactive.modalBox;
     }
  });

}

angular.module('aca1095BUiAppCitizen', [
  'ngAnimate',
  'ngSanitize',
  'ui.router',
  'ui.bootstrap',
  'config',
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
      // citizen portal
      .state('citizen', {
        url:'/citizen',
        templateUrl: 'views/citizen.html',
        controller: 'CitizenHeaderCtrl',
        abstract: true
      })
      .state('citizen.citizen-state', {
        url:'/citizen-state',
        templateUrl: 'views/citizen-state.html',
        controller: 'CitizenStateCtrl',
        controllerAs: 'vm',
        authenticate: false
      })
      .state('citizen.citizen-form', {
        url:'/citizen-form/:selectedState/:selectedYear',
        templateUrl: 'views/citizen-form.html',
        controller: 'CitizenFormCtrl',
        controllerAs: 'vm',
        authenticate: true
      })
      .state('citizen.citizen-view-general', {
        url:'/citizen-view-general',
        templateUrl: 'views/citizen-view-general.html',
        controller: 'CitizenViewGeneralCtrl',
        controllerAs: 'vm',
        authenticate: true
      })
      .state('citizen.citizen-view-detail', {
        url:'/citizen-view-detail',
        templateUrl: 'views/citizen-view-detail.html',
        controller: 'CitizenViewDetailCtrl',
        controllerAs: 'vm',
        authenticate: true
      })
      .state('citizen.citizen-static', {
        url:'/citizen-static',
        templateUrl: 'views/citizen-static.html',
        controller: 'CitizenStaticCtrl',
        controllerAs: 'vm',
        authenticate: false
      });

    $urlRouterProvider.otherwise( function($injector, $location) {
      var $state = $injector.get("$state");
      $state.go('citizen.citizen-state');
      //$state.go('citizen.citizen-view-general');
    });
  })
  .run(function ($rootScope, $state, CitizenSession) {
    $rootScope.$on("$stateChangeStart", function(event, toState, toParams, fromState, fromParams){

      if(!CitizenSession.selectedState && !CitizenSession.selectedYear){

        if(toState.name !== 'citizen.citizen-state'){
          //Prevent access to all pages except static page
          if(toState.authenticate || CitizenSession.viewPage === 'back'){
            CitizenSession.viewPage = '';
            $state.transitionTo("citizen.citizen-state");
            event.preventDefault();
          }
          if(toState.name === 'citizen.citizen-static' && CitizenSession.viewPage === ''){
            $state.transitionTo("citizen.citizen-state");
            event.preventDefault();
          }
        }

      }else if(CitizenSession.selectedState && CitizenSession.selectedYear){
        //Allow access to all pages except static page
        if(!toState.authenticate){
          CitizenSession.selectedState = '';
          CitizenSession.selectedYear = '';
          CitizenSession.viewPage = 'back';
          $state.transitionTo("citizen.citizen-state");
          event.preventDefault();
        }else if((toState.name === 'citizen.citizen-view-general' || toState.name === 'citizen.citizen-view-detail' )&& CitizenSession.userLogged === false){
          $state.transitionTo("citizen.citizen-state");
          event.preventDefault();
        }
      }

    });
  })
  .controller('mainCtrl',[
    '$scope',
    'textModal',
    'CitizenInactive',
    mainCtrl]);
