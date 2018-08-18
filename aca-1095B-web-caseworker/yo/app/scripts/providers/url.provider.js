/**
 * Created by speta on 12/17/2015.
 */
'use strict';
angular.module('aca1095BUiAppCW').provider('UrlService', function () {
  var _options = {
    'Environment':null
  };
  return {
    'setOptions': function (options) {
      if (options) {
        if (options.hasOwnProperty('Environment')) {
          _options.Environment = options.Environment;
        }
      }
    },
    '$get': function () {
      var url = function (data) {
        angular.extend(this, data);
      };

      url.fixtures = {
      };
      url.partials = {
      };
      url.services = {
        "ENTITYURL":_options.Environment.domain + _options.Environment.unicornContext + _options.Environment.entity_url,
        "SESSIONURL":_options.Environment.domain + _options.Environment.unicornContext + "/api/idm/user/getUserDetails?emailId=",
        "USERSESSIONURL":_options.Environment.domain + _options.Environment.unicornContext + "/api/idm/user/getSessionUserDetails",
        "GETFTLURL":_options.Environment.domain + _options.Environment.unicornContext + _options.Environment.api + "/idm/user/getUserDetails?emailId=",
        "UPDATEFTLURL":_options.Environment.domain + _options.Environment.unicornContext + _options.Environment.api +"/idm/user/fnmodifyUser",
        "GETPROFILEURL":_options.Environment.domain + _options.Environment.unicornContext +"/api/idm/user/recoverPwd?emailId=",
        "GETSECURITYQUESTIONSURL":_options.Environment.domain + _options.Environment.unicornContext +"/api/idm/user/validateUser",
        "UPDATEPASSWORDURL":_options.Environment.domain + _options.Environment.unicornContext +"/api/idm/user/fogotPasswordReset",
        "CREATE_USER":_options.Environment.domain + _options.Environment.unicornContext +"/api/idm/user/createUser",
        "ASSIGN_ROLES":_options.Environment.domain + _options.Environment.unicornContext +"/api/idm/user/createUserRole",
        "GET_ROLES":_options.Environment.domain + _options.Environment.unicornContext +"/api/idm/user/getAllAppsWithRoles?appName=",
        "GET_COMPANY_USERS":_options.Environment.domain + _options.Environment.unicornContext +"/api/idm/user/getUsersforCompany",
        "UPDATE_USER_BY_ADMIN":_options.Environment.domain + _options.Environment.unicornContext +"/api/idm/user/editUserDetailsAdmin",
        "UPDATE_USER_ROLES_BY_ADMIN":_options.Environment.domain + _options.Environment.unicornContext +"/api/idm/user/editManageUser",
        "ENABLE_USER_BY_ADMIN":_options.Environment.domain + _options.Environment.unicornContext +"/api/idm/user/restore",
        "DISABLE_USER_BY_ADMIN":_options.Environment.domain + _options.Environment.unicornContext +"/api/idm/user/disableUser",
        "GET_USER_SESSION":_options.Environment.endPoint + _options.Environment.api +"/user/getUserSession",
        "GETCHANGESECURITYQUESTIONSURL":_options.Environment.domain + _options.Environment.unicornContext + "/api/idm/user/getUserDetails?emailId=",
        "SUBMITSECURITYQUESTIONSURL":_options.Environment.domain + _options.Environment.unicornContext +"/api/idm/user/modifyUserQuestions",
        "MODIFYUSERPASSWORD":_options.Environment.domain + _options.Environment.unicornContext + "/api/idm/user/modifyUserPswd"
      };

      url.getService = function (urlKey) {
        return angular.copy(url.services[urlKey]);
      };
      url.getView = function (partialkey) {
        return angular.copy(url.partials[partialkey]);
      };
      url.getFixture = function (fixtureKey) {
        return angular.copy(url.fixtures[fixtureKey]);
      };
      return url;
    }
  };
});
