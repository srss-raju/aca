/**
 * Created by pchavan on 4/13/2016.
 */

(function () {
  'use strict';
  function acaDateParser($log){
    return {
      restrict: 'A',
      require: 'ngModel',
      replace: false,
      scope: {
        invalidClass: "=invalidClass",
        dateFormat: "=dateFormat"
      },
      link: function(scope, element, attrs, ngModel) {
        switch(scope.dateFormat){
          case 'MM/dd/YYYY' :
            scope.testPattern = '(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)[0-9]{2}';
            break;
          case 'MM/dd/YYYY HH:MM:SS AM/PM' :
            scope.testPattern = '(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19[0-9]{2}|20[0-9]{2})[ ](0[1-9]|[1][0-9])[ :](0[0-9]|[12345][0-9])[ :](0[0-9]|[12345][0-9])[ ](PM|AM)';
            break;
          default :
            scope.testPattern = '(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)[0-9]{2}';
        }

        if (ngModel) { // Don't do anything unless we have a model
          scope.dateParser = function(value) {
            var regex = new RegExp(scope.testPattern, 'g');
            var val = value.match(regex);
            if (value.match(regex)) {
                element.removeClass(scope.invalidClass);
                var tempDate = new Date(value);
                  if (tempDate instanceof Date && value === val[0]) {
                      ngModel.$valid = true;
                      var tempDateStamp = tempDate.getTime();
                      if(!!tempDateStamp){
                        return tempDateStamp;
                      } else {
                        element.addClass(scope.invalidClass);
                        ngModel.$invalid = true;
                        return null;
                      }
                  }else {
                    element.addClass(scope.invalidClass);
                    ngModel.$invalid = true;
                    return null;
                  }
            }else {
              element.addClass(scope.invalidClass);
              ngModel.$invalid = true;
              return null;
            }

          };
          scope.dateFormatter = function(value) {
            if (new Date(value) instanceof Date && !!value) {
              var tempDate = new Date(value);
              var tempDateFormatMM = tempDate.getMonth() < 10 ? "0" + (tempDate.getMonth() + 1) : (tempDate.getMonth() + 1);
              var tempDateFormatDD = tempDate.getDate() < 10 ? "0" + tempDate.getDate() : tempDate.getDate();
              var tempDateFormatHH = (tempDate.getHours() > 12) ?
                ((tempDate.getHours() - 12) > 9 ? (tempDate.getHours() - 12) : '0' + (tempDate.getHours() - 12))
                :  (tempDate.getHours() > 9 ? tempDate.getHours() : '0' + tempDate.getHours()) ;
              var tempDateFormatMin = (tempDate.getMinutes() < 10) ? '0' + tempDate.getMinutes() : tempDate.getMinutes();
              var tempDateFormatSS = (tempDate.getSeconds() < 10) ? '0' + tempDate.getSeconds() : tempDate.getSeconds();
              tempDateFormatHH = tempDateFormatHH < 0 ? (tempDateFormatHH * -1) : tempDateFormatHH;
              tempDateFormatHH = tempDateFormatHH == "00" ? 12 : tempDateFormatHH;
              return tempDateFormatMM + "/" + tempDateFormatDD + "/" + tempDate.getFullYear() + " " + tempDateFormatHH + ":" + tempDateFormatMin + ":" + tempDateFormatSS + " " + (tempDate.getHours() > 11 ? 'PM' : 'AM');
            } else {
              return value;
            }
           /* if (new Date(value) instanceof Date && !!value) {
              var tempDate = new Date(value);
              var tempDateFormatMM = tempDate.getMonth() < 10 ? "0" + (tempDate.getMonth() + 1) : (tempDate.getMonth() + 1);
              var tempDateFormatDD = tempDate.getDate() < 10 ? "0" + tempDate.getDate() : tempDate.getDate();
              return tempDateFormatMM + "/" + tempDateFormatDD + "/" + tempDate.getFullYear();
            } else {
              return value;
            }*/
          };
          ngModel.$parsers.push(scope.dateParser);
          ngModel.$formatters.push(scope.dateFormatter);
        }
      }
    };
  }

  angular.module('aca1095BUiAppCW')
    .directive("acaDateParser", ['$log', acaDateParser]);
}());
