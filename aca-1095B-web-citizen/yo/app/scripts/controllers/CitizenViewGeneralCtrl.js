
'use strict';

(function () {

  function CitizenViewGeneralCtrl($scope, $log, $element, $sce, $interval, $document, $state, CitizenService, CitizenInactive, inactivityModal, CitizenSession){

    var vm = this;

    vm.citizenData = CitizenService.getDetail();

    vm.printForm = function() {
      CitizenService.printDiv('citizen-view-form-actual');
    };

    vm.goToDetail = function(){
      $state.go('citizen.citizen-view-detail');
    };

    vm.initializeLabels = function(){

      vm.label = {
        titleAction : vm.formOptionTitle, titleDescription: "1095-B Form",
        name : "Name",   dob : "DOB",
        ssn : "SSN",  year : "Year",
        form : "Form",   genDate : "Generated Date",
        address : "Address",  city : "City",
        state : "State", zipCode : "Zipcode"
      };

      vm.ariaLabel = {
        title : "From this screen you can view/print your ten 95 B form here.",
        name : "You are currently viewing information for" + vm.citizenData.recipientFirstName + " " + vm.citizenData.recipientLastName,
        dob : "The date of birth on our record is" + vm.citizenData.recipientDOB,
        ssn : "",
        year : "You are viewing form for year " + vm.citizenData.taxYear
      };

      vm.ariaLabel.ssn = vm.citizenData.uidNumber !== null ? "The last four digits of your "+  vm.citizenData.uidType + " are" + vm.citizenData.uidNumber.substr(vm.citizenData.uidNumber.length - 4) : "";

      var timerFocus = $interval(function(){
          $('.citizen-view-general-container .title').eq(0).focus();
          $interval.cancel(timerFocus);
      }, 200);

    };

    vm.initializeLabels();
    CitizenInactive.initializeTimer();

    $scope.$watch(function () { return CitizenInactive.modalBox }, function (newVal, oldVal) {
        if (typeof newVal !== 'undefined') {
            vm.modalBox = CitizenInactive.modalBox;
        }
    });

  }

  angular.module('aca1095BUiAppCitizen')
    .controller('CitizenViewGeneralCtrl', [
      '$scope' ,
      '$log',
      '$element',
      '$sce',
      '$interval',
      '$document',
      '$state',
      'CitizenService',
      'CitizenInactive',
      'inactivityModal',
      'CitizenSession',
      CitizenViewGeneralCtrl]);


}());
