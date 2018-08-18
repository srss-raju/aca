
'use strict';

angular.module('aca1095BUiAppCitizen').factory('CitizenSession', function($http, $q, $state) {
  var service = {};

  service.selectedState = "";
  service.selectedYear = "";
  service.viewPage = "";
  service.staticState = "";
  service.userLogged = false;

  service.setStateCode = function(selectedState){
    service.selectedState = selectedState;
  };

  service.setStateYear = function(selectedYear){
    service.selectedYear = selectedYear;
  };

  service.setViewPage = function(type){
    service.viewPage = type;
  };

  service.setStaticState = function(state){
    service.staticState = state;
  };

  service.resetStateCode = function(){
    service.selectedState = "";
  };

  service.resetStateYear = function(){
    service.selectedYear = "";
  };

  service.resetViewPage = function(){
    service.viewPage = "";
  };

  service.resetStaticState = function(){
    service.staticState = "";
  };

  service.reset = function(){
    service.resetStateCode();
    service.resetStateYear();
    service.resetViewPage();
    service.resetStaticState();
  };

  /** User Logged In Session **/
  service.userLoggedIn = function(){
    service.userLogged = true;
  };
  /** User Logged Out Session **/
  service.userLoggedOut = function(){
    service.userLogged = false;
    service.reset();
    $state.go('citizen.citizen-state');
  };

  return service;
});
