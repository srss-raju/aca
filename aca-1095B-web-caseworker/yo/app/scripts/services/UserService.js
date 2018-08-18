/**
 * Created by sdalavi on 11/13/2015.
 */
'use strict';

angular.module('aca1095BUiAppCW')
  .factory('UserService', ['$http', '$log', 'ENV',  '$q', 'UrlService', function ($http, $log, ENV, $q, UrlService) {
    var _userInfo = null;
    var _show = false;
    var _loginError = false;
    function _checkLogin() {
      return $http({
        method: 'GET',
        url: UrlService.getService('GET_USER_SESSION'),
        headers: {
          'Content-Type': 'application/json'
        }
      })
        .then(function (response) {
          _userInfo = response.data;
          _show = true;
          return _userInfo;
        }, function(error) {
          //Temp-Site
          location.href = ENV.login;
        });
    }
    var identifyErrorMessage=function(status, message){
      if(status===403){
        message='Access is denied !';
      }else if(status===405){
        message='Bad Request !';
      }
      return message;
    };
    var getRoles = function(){
      var deferred = $q.defer();
      var headers = {'content-type': 'application/json'};
      var url = UrlService.getService('GET_ROLES')+ENV.unicornApplicationName+'&time='+ new Date().getTime();
      $http({'method': 'POST', 'url': url, 'data': {},'headers':headers })
        .success(function (response) {
          try{
            if (response.status === '200') {
              var currentApplication = _.findWhere(response.result.applications,{'appName':ENV.unicornApplicationName});
              var requiredRoles = _.where(currentApplication.appRoles,{'roleStatus':'Active','roleOUType':ENV.unicornRolesFilter});
              var roles = _.map(_.pluck(requiredRoles,'roleName'), function(data){return {actualRole : data, 'value' : data.split(ENV.unicornRolePreFix)[1]};});
              deferred.resolve({'data': roles, 'message': ''});
            } else {
              deferred.resolve({'error': 'ok', 'message': response.errormsg});
            }
          }catch(e){
            deferred.resolve({'error': 'ok', 'message': 'Please try again get roles !'});
          }
        })
        .error(function (response,status) {
          deferred.resolve({'error': 'ok', 'message': identifyErrorMessage(status, 'Please try again get roles !'),'response':response});
        });
      return deferred.promise;
    };
    var createUser = function (payload) {
      var deferred = $q.defer();
      var headers = {'content-type': 'application/json'};
      $http({'method': 'POST', 'url': UrlService.getService('CREATE_USER'), 'data': payload,'headers':headers })
        .success(function (response) {
          try{
            if (response.status === '200') {
              deferred.resolve({'data': response, 'message': ''});
            } else {
              deferred.resolve({'error': 'ok', 'message': response.errormsg});
            }
          }catch(e){
            deferred.resolve({'error': 'ok', 'message': 'Please try again to create user !'});
          }
        })
        .error(function (response,status) {
          deferred.resolve({'error': 'ok', 'message': identifyErrorMessage(status, 'Please try again to create user !'),'response':response});
        });
      return deferred.promise;
    };
    var assignRoles = function (payload) {
      var deferred = $q.defer();
      var headers = {'content-type': 'application/json'};
      $http({'method': 'POST', 'url': UrlService.getService('ASSIGN_ROLES'), 'data': payload,'headers':headers })
        .success(function (response) {
          try{
            if (response.status === '200') {
              deferred.resolve({'data': response.data, 'message': ''});
            } else {
              deferred.resolve({'error': 'ok', 'message': response.errormsg});
            }
          }catch(e){
            deferred.resolve({'error': 'ok', 'message': 'Assigning role failed !'});
          }
        })
        .error(function (response,status) {
          deferred.resolve({'error': 'ok', 'message': identifyErrorMessage(status, 'Assigning role failed !'),'response':response});
        });
      return deferred.promise;
    };
    var updateUserInformation = function (payload) {
      var deferred = $q.defer();
      var headers = {'content-type': 'application/json'};
      $http({'method': 'POST', 'url': UrlService.getService('UPDATE_USER_BY_ADMIN'), 'data': payload,'headers':headers })
        .success(function (response) {
          try{
            if (response.status === '200') {
              deferred.resolve({'data': response.data, 'message': ''});
            } else {
              deferred.resolve({'error': 'ok', 'message': response.errormsg});
            }
          }catch(e){
            deferred.resolve({'error': 'ok', 'message': 'Updating user information failed !'});
          }
        })
        .error(function (response,status) {
          deferred.resolve({'error': 'ok', 'message': identifyErrorMessage(status, 'Updating user information failed !'),'response':response});
        });
      return deferred.promise;
    };
    var updateUserRoles = function (payload) {
      var deferred = $q.defer();
      var headers = {'content-type': 'application/json'};
      $http({'method': 'POST', 'url': UrlService.getService('UPDATE_USER_ROLES_BY_ADMIN'), 'data': payload,'headers':headers })
        .success(function (response) {
          try{
            if (response.status === '200') {
              deferred.resolve({'data': response.data, 'message': ''});
            } else {
              deferred.resolve({'error': 'ok', 'message': response.errormsg});
            }
          }catch(e){
            deferred.resolve({'error': 'ok', 'message': 'Updating user roles failed !'});
          }
        })
        .error(function (response,status) {
          deferred.resolve({'error': 'ok', 'message': identifyErrorMessage(status, 'Updating user roles failed !'),'response':response});
        });
      return deferred.promise;
    };
    var updateUserStatus = function (email,enable) {
      var url = UrlService.getService('ENABLE_USER_BY_ADMIN');
      url = url + '?email='+email;
      if(_.isBoolean(enable) && enable===false){
        url = UrlService.getService('DISABLE_USER_BY_ADMIN');
        url = url + '?emailId='+email;
      }
      var deferred = $q.defer();
      var headers = {'content-type': 'application/json'};
      $http({'method': 'GET', 'url': url,'headers':headers })
        .success(function (response) {
          try{
            if (response.status === '200') {
              deferred.resolve({'data': response.result, 'message': ''});
            } else {
              deferred.resolve({'error': 'ok', 'message': response.errormsg});
            }
          }catch(e){
            deferred.resolve({'error': 'ok', 'message': 'Updating user status failed !'});
          }
        })
        .error(function (response,status) {
          deferred.resolve({'error': 'ok', 'message': identifyErrorMessage(status, 'Updating user status failed !'),'response':response});
        });
      return deferred.promise;
    };
    function _returnshow() {
      return _show;
    }
    function getUserInfo(){
      return _userInfo;
    }
    function setLoginErrorFlag(){
      _loginError = true;
    }
    function getLoginErrorFlag(){
      return _loginError;
    }
    return {
      checkLogin: _checkLogin,
      getUserInfo : getUserInfo,
      show: _returnshow,
      getRoles : getRoles,
      createUser : createUser,
      assignRole : assignRoles,
      updateUserInformation : updateUserInformation,
      updateUserRoles : updateUserRoles,
      updateUserStatus : updateUserStatus,
      identifyErrorMessage : identifyErrorMessage,
      setLoginErrorFlag:setLoginErrorFlag,
      getLoginErrorFlag:getLoginErrorFlag
    };
  }]);
