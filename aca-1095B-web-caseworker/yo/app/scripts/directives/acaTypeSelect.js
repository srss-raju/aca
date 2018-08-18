/**
 * Created by pchavan on 11/6/2015.
 */


(function () {
  'use strict';

  function acaTypeSelect(){
    return{
      restrict: "EA",
      scope: {
        ngModel : '=ngModel',
        defaultOption : '=defaultOption',
        options : '=options',
        view : '=view', model : '=model',
        tabindex : '=tabindex',
        dataAriaDesc : '=dataAriaDesc', enable : '=enable',
        floatingValidation: '=floatingValidation'
      },
      templateUrl: 'views/templates/acaTypeSelect.html',
      link : function(scope, elem, attr){
        scope.showList = false;
        scope.searchQuery = "";
        var i = -1;
        scope.listItemLength = $('.listItem').length;
        elem.on('keydown', function(e){
          if(e.keyCode === 40){
           /* $log.info("keydown event", e.keyCode);*/
            if(i <  $('.listItem').length - 1){
              i = i + 1;
            }else{
              i = 0;
            }

            $('.listItem').eq(i).focus();
          }else if(e.keyCode === 38){
          /*  $log.info("keydown event", e.keyCode);*/
            if(i > 0){
              i = i - 1;
            }else{
              i = $('.listItem').length - 1;
            }
            $('.listItem').eq(i).focus();
          }
        });

        scope.fnShowList = function(){
          scope.showList = true;
          scope.searchQuery = "";
        };

        scope.fnHideList = function(){
          scope.showList = false;
        };

        scope.setValue = function(option){
          scope.searchQuery = option.stateName;
          scope.showList = false;
          scope.ngModel = option;
          $(scope.enable).focus();
        };
      }
    };
  }
  angular.module('aca1095BUiAppCW')
    .directive("acaTypeSelect", [acaTypeSelect]);
}());
