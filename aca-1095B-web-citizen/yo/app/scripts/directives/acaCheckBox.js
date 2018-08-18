/**
 * Created by pchavan on 12/8/2015.
 */

(function () {
  'use strict';

  function acaCheckBox(){
    return {
      restrict: "EA",
      scope:{
        checkedFlg : '=?checkedFlg',
        action : '&action',
        label : '=label'
      },
      templateUrl: 'views/templates/acaCheckBox.html',
      link : function(scope,elem,attr){
        //scope.label = attr.label;
      }
    }
  }

  angular.module('aca1095BUiAppCitizen')
    .directive("acaCheckBox", [acaCheckBox]);
}());
