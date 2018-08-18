/**
 * Created by pchavan on 1/14/2016.
 */

(function () {
  'use strict';

  function acaDropDown($log){
    return {
      restrict: "EA",
      scope:{
        acaDropDown : "=acaDropDown"
      },
      templateUrl: 'views/templates/acaDropDown.html',
      link : function(scope,elem,attr){
      }
    }
  }

  angular.module('aca1095BUiAppCW')
    .directive("acaDropDown", ['$log' , acaDropDown]);
}());
