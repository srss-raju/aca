/**
 * Created by pchavan on 12/1/2015.
 */

(function () {
  'use strict';

  function getKeys() {
    var captchaKeys = {};
    captchaKeys.siteKey = '6Lf5GRITAAAAAEXlH5gp2INuPMDVriqX3ARHOet_';
    captchaKeys.secretKey = '6Lf5GRITAAAAAFIyDx_tEIcT6FbRKtAyZBklUe5i';
    return captchaKeys;
  }

  function recaptchaGoogle($http) {
    var service = {};
    service.verifyResponse = function(responseObject){
      return $http.post('https://www.google.com/recaptcha/api/siteverify', JSON.stringify(responseObject), {header : 'Content-Type: application/json'});
    };

    return service;
  }

  function acaRecaptcha($log, getKeys, recaptchaGoogle) {
    return {
      restrict: "EA",
      scope: {
        fnCallback: "&fnCallback",
        response : "&response"
      },
      templateUrl : 'views/templates/recaptcha.html',
      link: function(scope, elem, attr){
        /** loads recaptch api only on load of this screen**/
        $.getScript("https://www.google.com/recaptcha/api.js");
        scope.getKeys = getKeys;


        //Setting the tabindex for google recaptcha iframe
        Array.prototype.forEach.call(document.getElementsByClassName('g-recaptcha'), function (element) {
          element.addEventListener('load', function (e) {
            var tabindex = e.currentTarget.getAttribute('data-tabindex');
            if (tabindex) {
              e.target.tabIndex = tabindex;
            }
          }, true);
        });


        var responseObject = {};
        /** Service call Front End and google server**/
        scope.responseGoogle = function(responseRecvd){
          if(responseRecvd){
            /** Set flag or execute function when captcha response is received **/
            scope.response();
            scope.fnCallback();
          }
        };

        window.responseCallback =  scope.responseGoogle;

      }

    }
  }

  angular.module('aca1095BUiAppCitizen')
  .service("getKeys", getKeys)
  .service("recaptchaGoogle", ['$http', recaptchaGoogle])
  .directive("acaRecaptcha", ['$log', 'getKeys', 'recaptchaGoogle', acaRecaptcha]);
}());
