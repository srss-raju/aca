/**
 * Created by pchavan on 12/14/2015.
 */


'use strict';

(function () {

  function CitizenHeaderCtrl($scope, $log, $element, $rootScope, CitizenSession, CitizenInactive){

    $scope.selectedState = CitizenSession.selectedState;
    $scope.selectedYear = CitizenSession.selectedYear;
    $scope.CitizenSession = CitizenSession;

    $scope.$watch(function () { return CitizenInactive.modalBox }, function (newVal, oldVal) {
        if (typeof newVal !== 'undefined') {
            $scope.modalBox = CitizenInactive.modalBox;
        }
    });

  }


angular.module('aca1095BUiAppCitizen')
  .controller('CitizenHeaderCtrl', [
    '$scope' ,
    '$log',
    '$element',
    '$rootScope',
    'CitizenSession',
    'CitizenInactive',
    CitizenHeaderCtrl]);

}());
