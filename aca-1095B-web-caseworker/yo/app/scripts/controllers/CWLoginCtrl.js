/**
 * Created by sdalavi on 10/12/2015.
 */
'use strict';

(function () {
  var aca1095BUiAppCW = angular.module('aca1095BUiAppCW');

  aca1095BUiAppCW.controller('CWLoginCtrl', ['$scope', '$state', '$stateParams', '$log', '$timeout', 'UserService',
      function($scope, $state, $stateParams, $log, $timeout, UserService) {
    var state = 'ar';
    $timeout(function(){
      $('.email').focus();
    }, 5);
    $scope.ariaLabel = {
      "userName" : "Welcome to LaborWise portal. Please login by entering your username.",
      "password" : "Please enter your password.",
      "signIn" : "Press enter to log in the system using the credentials provided"
    };
    // login
    $scope.login = {
      errorMessage: null,
      userNotFound:false,
      smquerydata: '',
      smauthreason: '',
      smagentname: '',
      target: ''
    };

    if(UserService.getLoginErrorFlag()){
      $scope.login.userNotFound = true;
      $scope.login.errorMessage = 'Please provide valid credentials';
    }
    $scope.fnEvaluateParameters = function () {
        var queryString, queryStringParams, SMQUERYDATA, SMAUTHREASON;
        if (location.href.indexOf('SMQUERYDATA') !== -1) {
          queryString = location.href.split('?')[1];
          queryStringParams = queryString.split('&');
          SMQUERYDATA = queryStringParams[0].split('SMQUERYDATA=')[1];
          SMAUTHREASON = 0;
          SMQUERYDATA = SMQUERYDATA.substr(4);
          SMQUERYDATA = decodeURIComponent(SMQUERYDATA);
          $scope.login.smquerydata = SMQUERYDATA;
          document.getElementById('loginDiv').action += '?SMQUERYDATA=' + SMQUERYDATA;
        } else {
          queryString = location.href.split('?')[1];
          if(queryString) {
            queryStringParams = queryString.split('&');
            SMQUERYDATA = '';
            SMAUTHREASON = queryStringParams[3].split('SMAUTHREASON=')[1];
            //var TYPE = queryStringParams[0].split('TYPE=')[1];
            //var REALMOID = queryStringParams[1].split('REALMOID=')[1];
            //var GUID = queryStringParams[2].split('GUID=')[1] || '';
            //var METHOD = queryStringParams[4].split('METHOD=')[1];
            var SMAGENTNAME = queryStringParams[5].split('SMAGENTNAME=')[1];
            var TARGET = queryStringParams[6].split('TARGET=')[1];
            var targetDecoded = '';
            if (TARGET.charAt(0) === '-') {
              targetDecoded = decodeURIComponent(TARGET).replace(/-/g, '').substr(2);
            } else if (TARGET.charAt(0) === '$') {
              targetDecoded = decodeURIComponent(TARGET).replace(/$/g, '').substr(2);
            }
            var targetDecodedParams = targetDecoded.split('?SAMLRequest=');
            var targetDecodedRequiredParamsSplited = targetDecodedParams[1].split('&SMPORTALURL=');
            var affWebServiceURL = targetDecodedParams[0];
            var SAMLRequest = targetDecodedRequiredParamsSplited[0];
            SAMLRequest = encodeURIComponent(SAMLRequest);
            var SMPORTALURL = targetDecodedRequiredParamsSplited[1];
            SMPORTALURL = encodeURIComponent(SMPORTALURL);
            var requiredTargetURL = affWebServiceURL + '?SAMLRequest=' + SAMLRequest + '&SMPORTALURL=' + SMPORTALURL;
            $scope.login.smquerydata = SMQUERYDATA;
            $scope.login.smauthreason = SMAUTHREASON;
            $scope.login.smagentname = SMAGENTNAME;
            $scope.login.target = requiredTargetURL;
          }
        }
    };
    $scope.fnEvaluateParameters();

  }]);
}());
