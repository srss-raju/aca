/**
 * Created by pchavan on 4/18/2016.
 */
'use strict';
(function(){

  function operationServices($http, ENV, $q){
    var operationService = {};
    operationService.url = ENV.endPoint;

    /** GET : for the states available dropdown on XML Status screen**/
    operationService.getStates = function(){
      //return $http.get('stubs/getStates.json')
      return $http.get(operationService.url + '/opsportal/getStates')
        .then(function(response) {
          if (typeof response.data === 'object') {
            return response.data;
          } else {
            // invalid response
            return $q.reject(response.data);
          }
        }, function(response) {
          // something went wrong
          return $q.reject(response.data);
        });
    };

    /** GET : for the years available dropdown on XML Status screen**/
    operationService.getYears = function () {
      //return $http.get('stubs/getYears.json')
      return $http.get(operationService.url + '/opsportal/getYears')
        .then(function (response) {
          if (typeof response.data === 'object') {
            return response.data;
          } else {
            // invalid response
            return $q.reject(response.data);
          }
        }, function (response) {
          // something went wrong
          return $q.reject(response.data);
        });
    };

    /** GET : for the status to be set in edit mode on XML Status screen**/
    operationService.getTransmissionStatuses = function(){
      //return $http.get('stubs/getStatuses.json')
      return $http.get(operationService.url + '/opsportal/getTransmissionStatuses')
        .then(function(response) {
          if (typeof response.data === 'object') {
            return response.data;
          } else {
            // invalid response
            return $q.reject(response.data);
          }
        }, function(response) {
          // something went wrong
          return $q.reject(response.data);
        });
    };

    /** GET : for the transmission records shown on XML Status screen **/
    operationService.getTransmissionRecords = function (stateSelected, yearSelected) {
      //return $http.get('stubs/getTransmissionRecords.json')
      return $http.get(operationService.url + '/opsportal/getTransmissionRecords?stateCd=' + stateSelected.stateCode + '&taxYear=' + yearSelected.taxYear)
        .then(function(response) {
          if (typeof response.data === 'object') {
            return response.data;
          } else {
            // invalid response
            return $q.reject(response.data);
          }
        }, function(response) {
          // something went wrong
          return $q.reject(response.data);
        });
    };

    /** POST : for saving the transmission record in edit mode shown on XML Status screen **/
    operationService.saveTransmissionRecord = function(recordData){
      //return $http.get('stubs/saveTransmissionRecords.json')
      return $http.post(operationService.url + '/opsportal/saveTransmissionRecord', JSON.stringify(recordData), {header : 'Content-Type: application/json'})
        .then(function(response) {
          if (typeof response.data === 'object') {
            return response.data;
          } else {
            // invalid response
            return $q.reject(response.data);
          }
        }, function(response) {
          // something went wrong
          return $q.reject(response.data);
        });
    };

    /** POST : for saving the transmission record in edit mode shown on XML Status screen **/
    operationService.rejectResendCorrectionRecord = function(recordData){
      //return $http.get('stubs/saveTransmissionRecords.json')
      return $http.post(operationService.url + '/opsportal/rejectResendCorrection', JSON.stringify(recordData), {header : 'Content-Type: application/json'})
        .then(function(response) {
          if (typeof response.data === 'object') {
            return response.data;
          } else {
            // invalid response
            return $q.reject(response.data);
          }
        }, function(response) {
          // something went wrong
          return $q.reject(response.data);
        });
    };

    return operationService;
  }

  angular.module('aca1095BUiAppCW')
    .service('operationServices', ['$http', 'ENV', '$q', operationServices]);

}());
