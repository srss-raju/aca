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
        searchText : '=searchText',
        type: '=type'
      },
      templateUrl: 'views/templates/acaTypeSelect.html',
      link : function(scope, elem, attr){
        scope.showList = false;
        //scope.searchText = "";
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
          scope.searchText = "";
          scope.showList = true;
        };

        scope.fnHideList = function(){
          scope.showList = false;
        };

        scope.setValue = function(option){

          scope.showList = false;
          scope.ngModel = option;

          if(scope.type === 'state'){
            scope.searchText = option.stateName;
          }
          else if(scope.type === 'year'){
            scope.searchText = option.year;
            $('.progress-bar').css('width','100%');
            $('.enter-button').addClass('enable');
          }

        };

        function checkExistence(type){

          for (var i=0; i < scope.options.length; i++) {
            if ( (type === 'stateName') && (scope.options[i][type].toLowerCase() === scope.searchText.toLowerCase()) ) {
              scope.ngModel = scope.options[i];
              return true;
            }
            else if ( (type === 'year') && (scope.options[i][type].toString() === scope.searchText) ) {
              scope.ngModel = scope.options[i];
              return true;
            }
          }
          return false;

        }

        scope.$watch('searchText', function(newValue, oldValue) {

          var type;

          if (newValue) {

            if(scope.type === 'state'){
              type = 'stateName';
              if(checkExistence(type)){
                scope.showList = false;
                $('.arrow-icon').show();
              }
              else{
                scope.showList = true;
                $('.progress-bar').css('width','0%');
                $('.arrow-icon').hide();
              }
            }
            else if(scope.type === 'year'){
              type = 'year';
              if(checkExistence(type)){
                scope.showList = false;
                $('.progress-bar').css('width','100%');
                $('.enter-button').addClass('enable');
              }
              else{
                scope.showList = true;
                $('.progress-bar').css('width','50%');
                $('.enter-button').removeClass('enable');
              }
            }

          }
          else if(newValue == ''){
            if(scope.type === 'state'){
              $('.progress-bar').css('width','0%');
              $('.arrow-icon').hide();
            }
            else if(scope.type === 'year'){
              $('.progress-bar').css('width','50%');
              $('.enter-button').removeClass('enable');
            }

          }
        }, true);

      }
    };
  }
  angular.module('aca1095BUiAppCitizen')
    .directive("acaTypeSelect", [acaTypeSelect]);
}());
