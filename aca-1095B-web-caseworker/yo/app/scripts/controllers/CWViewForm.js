'use strict';

(function () {

  function CWViewForm($scope, $state, $log, $sce, $timeout, $interval, CWService, confirmationDialog) {
    var vm = this;

    vm.cwData = CWService.getFormData();

    vm.goBack = function () {
      $state.go(CWService.previousState);
    };

    vm.printDiv = function (divClass) {
      CWService.printDiv(divClass);
    };

    vm.initializeLabels = function () {

      vm.label = {
        titleAction: vm.formOptionTitle, titleDescription: "1095-B Form",
        name: "Name", dob: "DOB",
        ssn: "SSN", year: "Year",
        form: "Form", genDate: "Generated Date",
        address: "Address", city: "City",
        state: "State", zipCode: "Zipcode"
      };

      vm.ariaLabel = {
        title: "From this screen you can view/print your ten 95 B form",
        name: "You are currently viewing information for" + vm.cwData.recipientFirstName + " " + vm.cwData.recipientLastName,
        dob: "The date of birth on our record is" + vm.cwData.recipientDOB,
        ssn: "",
        year: "You are viewing form for year " + vm.cwData.taxYear
      };

      vm.ariaLabel.ssn = vm.cwData.uidNumber !== null ? "The last four digits of your " + vm.cwData.uidType + " are" + vm.cwData.uidNumber.substr(vm.cwData.uidNumber.length - 4) : "";

      var timerFocus = $interval(function(){
         $('.cw-view-detail-container .title').eq(0).focus();
         $interval.cancel(timerFocus);
      }, 200);

    };

    vm.initializeLabels();

  }

  angular.module('aca1095BUiAppCW').controller('CWViewForm', ['$scope', '$state', '$log', '$sce', '$timeout', '$interval', 'CWService', 'confirmationDialog', CWViewForm]);
}());
