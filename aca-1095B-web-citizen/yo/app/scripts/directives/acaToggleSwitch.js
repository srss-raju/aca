/**
 * Created by pchavan on 10/26/2015.
 */

(function () {
  'use strict';

  function acaToggleSwitch(){
    return {
      restrict: "EA",
      scope:{
        checkedFlg : '=checkedFlg',
        action : '&action',
        left : '=?left',
        right : '=?right',
        defaultFlg: '=?default',
        readLabel: '=readLabel'
      },
      templateUrl: 'views/templates/acaToggleSwitch.html',
      link : function(scope,elem,attr){
        scope.checkedFlg = scope.defaultFlg || true;
        scope.actionFollow = function(){
          scope.action();
        };
      }
    }
  }

  angular.module('aca1095BUiAppCitizen')
    .directive("acaToggleSwitch", [acaToggleSwitch]);
}());
