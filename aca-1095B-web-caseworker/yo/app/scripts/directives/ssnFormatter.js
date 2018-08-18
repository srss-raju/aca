/**
 * Created by sdalavi on 10/7/2015.
 */

'use strict';

angular.module('aca1095BUiAppCW').directive('ssnFormatter', [function() {
  return {
    require: 'ngModel',
    restrict: 'AE',
    scope: {
      ngModel: "@"
    },
    link: function(scope, elem, attrs, ngModelCtrl) {
      var toModel = function(val) {
        val = val.replace(/[^0-9]/g, "");
        var value;
        if (/^\d{10}$/.test(val)) {
          value = val.length > 9 ? val.slice(0, 9) : val;
        } else {
          value = val;
          ngModelCtrl.$rollbackViewValue();
        }
        ngModelCtrl.$viewValue = value;
        ngModelCtrl.$commitViewValue();
        return value;
      };

      var ssnFormat = function(val) {
      };
      ngModelCtrl.$parsers.push(toModel);

      elem.on('focus', function() {
        elem.val("");
        ngModelCtrl.$viewValue = "";
        ngModelCtrl.$commitViewValue();
      });


    }
  };
}]);
