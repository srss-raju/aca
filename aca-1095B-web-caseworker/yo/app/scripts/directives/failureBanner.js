/**
 * Created by pchavan on 10/13/2015.
 */

(function () {
  'use strict';

  function failureBanner() {
    return{
      restrict: "EA",
      scope: {
        showFlag: "=",
        errorMessage: "="
      },
      templateUrl: 'views/templates/failureBanner.html'
      /*link: function (scope, element) {
        /!*console.log("failure banner");*!/
      }*/
    };
  }

  angular.module('aca1095BUiAppCW').directive("failureBanner", failureBanner);
}());
