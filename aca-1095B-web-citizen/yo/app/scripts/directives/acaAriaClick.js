/**
 * Created by pchavan on 11/30/2015.
 */
(function () {
  'use strict';

  function acaAriaClick($log) {
    return {
      restrict: "EA",
      scope: {
        acaAriaClick : "&acaAriaClick"
      },
      replace : false,
      link: function(scope, elem, attr){
        elem.on('click', function($event){
          scope.acaAriaClick();
        });

        elem.on('keyup', function($event){
          if($event.keyCode === 13){
            scope.acaAriaClick();
            $event.preventDefault();
          }
        });
      }
    };
  }

  angular.module('aca1095BUiAppCitizen')
    .directive("acaAriaClick", ['$log', acaAriaClick]);
}());
