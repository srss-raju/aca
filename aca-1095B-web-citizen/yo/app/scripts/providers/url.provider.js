/**
 * Created by speta on 12/17/2015.
 */
'use strict';
angular.module('aca1095BUiAppCitizen').provider('UrlService', function () {
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
