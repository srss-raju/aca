/**
 * Created by pchavan on 10/16/2015.
 */

(function () {
  'use strict';

  function successBanner() {
    return{
      restrict: "EA",
      scope: {
        showFlag: "=",
        successMessage: "="
      },
      templateUrl: 'views/templates/successBanner.html',
      link: function (scope, element) {
      }
    };
  }

  angular.module('aca1095BUiAppCW').directive("successBanner", successBanner);
}());
