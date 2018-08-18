/**
 * Created by pchavan on 11/5/2015.
 */

(function () {
  'use strict';

  function acaDatePickerFloatingLabel($animate, $log){
    return{
      restrict: "EA",
      scope: {
        ngModel : '=ngModel',
        placeholder : '=',
        fnChange:"&",
        clear:"=clear"
      },
      require: 'ngModel',
      templateUrl :  'views/templates/acaDatePickerFloatingLabel.html',
      link: function(scope, elem, attrs, ngModelCtrl){
        var today = new Date();
        scope.datePicker = {
          open: false,
          showWeeks: false,
          maxDate: new Date(today.getFullYear(), today.getMonth(), today.getDate() - 1),
          minDate: new Date('01, 01, 1900')
        };
        scope.ngModelCtrl = ngModelCtrl;
        if(typeof scope.ngModelCtrl.$viewValue !== 'object'){
          scope.ngModel = null;
        }
        scope.dataAriaLabel = "Enter the date of birth in MM/DD/YYYY format";
        var $float = jQuery('<label for="'+attrs.id+'" class="float">'+scope.placeholder+'</span>');
        var errorMessage = "Enter date as MM/DD/YYYY";

        scope.showHide = function(show){
          if(show){
            if(!$float.hasClass('top')){
              elem.after($float);
              $animate.addClass($float, 'top');
            }
          }else {
            $animate.removeClass($float, 'top');
          }
        };

        scope.showErrors = function(){
          $float.text(errorMessage);
          scope.ngModelCtrl.$valid = false;
          scope.ngModelCtrl.$invalid = true;
          scope.showHide(true);
        };

        scope.$watch('ngModelCtrl.$modelValue', function (newValue) {
          if(newValue){
            var dateInput = ngModelCtrl.$modelValue;
            if(typeof dateInput !== 'string'){
              var dayInput = dateInput.getDate() < 10 ? "0" + (dateInput.getDate()) : dateInput.getDate();
              ngModelCtrl.$modelValue = (dateInput.getMonth()+1) +"/"+ (dayInput) +"/"+ (dateInput.getFullYear());
            }
            $float.text(scope.placeholder);
            scope.showHide(true);
            scope.ngModelCtrl.$setDirty();
            scope.ngModelCtrl.$setTouched();
          }
          scope.fnChange();
          if(newValue===null && scope.clear==='clear'){
            scope.showHide(false);
            elem.find("input").addClass('ng-pristine');
          }
        });

        scope.$watch('ngModelCtrl.$pristine', function (newValue) {
          if(newValue && scope.ngModelCtrl.$touched){
            scope.showHide(false);
            scope.ngModelCtrl.$setUntouched();
            //immediately reset no debounce
            scope.ngModelCtrl.$setViewValue(undefined, scope.ngModelCtrl.$options.updateOn);
            scope.ngModelCtrl.$setPristine();
          }
        });

        scope.$watch('ngModelCtrl.$viewValue', function (newValue) {
          if(!!newValue){
            if(typeof newValue !== 'object'){
              $float.addClass('red');
              scope.showErrors();
            }else{
              $float.removeClass('red');
            }
          }else if(newValue == undefined && typeof newValue !== 'object'){
            $float.addClass('red');
            scope.showErrors();
          }else{
            $float.removeClass('red');
          }
        });
      }
    }
  }
  angular.module('aca1095BUiAppCW')
    .directive("acaDatePickerFloatingLabel", ['$animate', '$log', acaDatePickerFloatingLabel]);
}());
