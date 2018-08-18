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
        dataLabel: '=?dataLabel'
      },
      templateUrl: 'views/templates/acaToggleSwitch.html',
      link : function(scope,elem,attr){
        /*$log.info(scope.checkedFlg, "TRacking");*/
        scope.dataLabel = scope.left.label + " selected. Enter or click to change to " + scope.right.label;
        scope.checkedFlg = scope.defaultFlg || true;
        scope.actionFollow = function(){
          scope.action();
          if(!scope.checkedFlg){
            scope.dataLabel = scope.left.label + " selected.  Enter or click to change to " + scope.right.label;
          }else{
            scope.dataLabel = scope.right.label + " selected. Enter or check for " + scope.left.label;
          }
        };
        $('.switch-input').on('keyup', function(e){
          if(e.keyCode === 13){
            scope.actionFollow();
          }
        });

      }
    }
  }

  angular.module('aca1095BUiAppCW')
    .directive("acaToggleSwitch", [acaToggleSwitch]);
}());
