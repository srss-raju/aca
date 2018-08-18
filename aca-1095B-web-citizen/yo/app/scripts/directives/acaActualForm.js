

(function () {
  'use strict';

  angular.module('aca1095BUiAppCitizen')
    .directive('acaActualForm', function() {

      return {
        restrict: 'E',
        templateUrl: 'views/templates/acaActualForm.html',
        scope:{
          citizenData: '=',
          modalBox: '='
        },
        link: function(scope, elem, attr) {
        },
        controller: function(){
          this.emptyArray = function(n){
            return new Array(n);
          };
        },
        controllerAs: 'vm',
        bindToController: true
      };

    });

}());
