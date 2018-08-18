
'use strict';

var aca1095BUiAppCitizen = angular.module('aca1095BUiAppCitizen');

aca1095BUiAppCitizen.factory('CitizenService', function($http, $q, ENV) {

  var myService = {};
  var apiUrl = ENV.endPoint + ENV.api;


  /** Landing page - Citizen State **/
  myService.getSelectedState = function(){
   //return $http.get('stubs/getStates.json')
    return $http.get(apiUrl + '/getavailablestates')
      .then(function(response) {
        if (typeof response.data === 'object') {
          return response.data;
        } else {
          return $q.reject(response.data);
        }
      }, function(response) {
        return $q.reject(response.data);
      });
  };


  /** Citizen Form **/
  myService.getStates = function() {
    //return $http.get('stubs/address-states.json')
    return  $http.get(ENV.endPoint + '/stubs/address-states.json')
      .then(function(response) {
        if (typeof response.data === 'object') {
          return response.data.list;
        } else {
          return $q.reject(response.data);
        }
      }, function(response) {
        return $q.reject(response.data);
      });
  };


  myService.identifyCitizen = function(userData){
    var requestData = angular.copy(userData);
    requestData.dob = new Date(requestData.dob);
    requestData.dob = (requestData.dob.getMonth()+1)+"/"+(requestData.dob.getDate())+"/"+(requestData.dob.getFullYear());

    //return $http.get('stubs/citizenData.json')
    return $http.post(apiUrl + '/getauthenticationdata', JSON.stringify(requestData))
      .then(function(response) {
        if (typeof response.data === 'object') {
          return response.data;
        } else {
          return $q.reject(response.data);
        }
      }, function(response) {
        return $q.reject(response.data);
      });
  };


  myService.setData =function(data){
    myService.data = data;
  };


  /** Citizen View / Print Screen **/
  myService.getDetail = function(){
    return  myService.data;
  };


  myService.getStaticContent = function(state){
    switch(state){
      case 'AR':
        return $http.get('stubs/AR-static-content.json');
      case 'IN':
        return $http.get('stubs/IN-static-content.json');
      case 'LA':
        return $http.get('stubs/LA-static-content.json');
      default:
        return $http.get('stubs/Default-static-content.json');
    }
  };


  myService.printDiv = function(divClass) {

    jQuery.get('styles/main.css', function (maincss) {
      jQuery.get('styles/vendor.css', function (vendorcss) {
        window.frames["print_frame"].document.body.innerHTML = '<style>' + vendorcss + maincss + '</style>' + document.getElementsByClassName(divClass)[0].innerHTML;
        //console.log(window.frames["print_frame"].document.body.innerHTML);
        window.frames["print_frame"].window.focus();
        window.frames["print_frame"].window.print();
      });
    });

  };

  return myService;
});
