(function () {
  'use strict';

  angular.module('aca1095BUiAppCW')
    .directive('acaActualForm', function () {

      return {
        restrict: 'E',
        templateUrl: 'views/templates/acaActualForm.html',
        scope: {
          cwData: '='
        },
        link: function (scope, elem, attr) {
        },
        controller: function () {
          this.emptyArray = function (n) {
            return new Array(n);
          };
        },
        controllerAs: 'vm',
        bindToController: true
      };

    });

}());
