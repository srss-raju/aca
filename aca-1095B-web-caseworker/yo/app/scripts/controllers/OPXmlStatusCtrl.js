/**
 * Created by pchavan on 3/28/2016.
 */

'use strict';

(function () {
  function OPXmlStatusCtrl($scope, $timeout, operationServices, operationsPortal) {
    var vm = this;
    /*** INITIALIZATION ***/
    vm.directiveLoad = false;
    vm.label = {
      titleAction: "Operations", titleDescription: "Team Portal",
      visibleRadio: "visible", hiddenRadio: "hidden", allRadio: "all",
      transferDate: "Transfer Date", transmissionFileName: "File Name",
      transmissionAckDate: "Transmission ACK Date", transmissionReceiptId: "Receipt ID",
      transmissionAckStatus: "Status", actions: "Actions"
    };
    vm.dataSearch = null;
    vm.selectedState = {"stateName" : null, "stateCode" : null};
    vm.selectedYear = {"taxYear": null};
    vm.confirmedState = {"stateName" : null, "stateCode" : null};
    vm.confirmedYear = {"taxYear": null};
    vm.dataLoad = false;
    vm.showSuccessMessage = false;
    vm.showErrorMessage = false;
    vm.successMessage = "Data received";
    vm.errorMessage = "Data not fetched";
    vm.errorMessageList = {
      noRecordFound : "No records have been found in your search result. Please try again.",
      transmissionStatusFailure : "GET : Transmission Status API call failure",
      saveFailure : "An error occurred while saving your changes. Please try again.",
      receiptIdFailure: "The receipt ID you entered is not unique. Please enter a unique receipt ID.",
      rejectResendFailure : "An error occurred while Resend Rejected record. Please try again.",
      correctedResendFailure : "An error occurred while Resend Corrected record. Please try again.",
      rejectResendDecline : "You cannot resend the XML file because data has been corrected."
    };
    vm.successMessageList = {
      savedSuccessfully : "Your data has been successfully updated.",
      transmissionStatusFailure : "GET : Transmission Status API call failure",
      rejectResendSuccessfully : "Record has been successfully Resend Rejected.",
      correctedResendSuccessfully : "Record has been successfully Resend Corrected."
    };
    vm.stateList = [];
    vm.yearList = [];
    vm.transmissionData = [];
    vm.filteredTransmissionData = [];
    vm.transmissionStatusList = [];
    vm.editModeReference = false;
    vm.unsavedModeReference = false;
    vm.orderTracker = {
      transferDate : false,
      transmissionDate : false,
      transmissionAckStatus : true
    };
    /** timeout error ***/
    vm.errorDisplayTimeout = 3000;

    /*** get state list ***/
    vm.getStateList = function () {
      operationServices.getStates()
        .then(function (response) {
          /*$log.info("success:  ", response);*/
          vm.stateList = response;
        }, function () {
          vm.showSuccessMessage = false;
          vm.showErrorMessage = true;
          vm.errorMessage = "State list not fetched.";
        });
    };
    vm.getStateList();

    /*** get year list ***/
    vm.getYearList = function () {
      operationServices.getYears()
        .then(function (response) {
          vm.yearList = response;
          if (vm.yearList[0].taxYear)
            vm.selectedYear = vm.yearList[0];
        }, function () {
          vm.showSuccessMessage = false;
          vm.showErrorMessage = true;
          vm.errorMessage = "Year list not fetched.";
        });
    };
    vm.getYearList();

    vm.directiveLoad = true;

    /** get transmission statuses for edit mode **/
    vm.getTransmissionStatuses = function(){
      operationServices.getTransmissionStatuses()
        .then(function(response){
          vm.transmissionStatusList = response;
        }, function(){
          vm.showSuccessMessage = false;
          vm.showErrorMessage = true;
          vm.errorMessage = vm.errorMessageList.transmissionStatusFailure;
        });
    };

    /** Radio button flags **/
    vm.radioButton = {
      visible: true,
      hidden: false,
      all: false,
      selected: 'visible'
    };

    /*** On radio button click ***/
    vm.radioToggle = function (radioType) {
      if (vm.confirmedState !== null) {
        switch (radioType) {
          case 'visible':
          {
            vm.radioButton.visible = true;
            vm.radioButton.hidden = false;
            vm.radioButton.all = false;
            vm.radioButton.selected = radioType;
          }
            break;
          case 'hidden':
          {
            vm.radioButton.visible = false;
            vm.radioButton.hidden = true;
            vm.radioButton.all = false;
            vm.radioButton.selected = radioType;
          }
            break;
          case 'all':
          {
            vm.radioButton.visible = false;
            vm.radioButton.hidden = false;
            vm.radioButton.all = true;
            vm.radioButton.selected = radioType;
          }
            break;
          default:
          {
            vm.radioButton.visible = true;
            vm.radioButton.hidden = false;
            vm.radioButton.all = false;
            vm.radioButton.selected = 'visible';
          }
            break;
        }
        vm.editModeReference = false;
      }
    };

    /** on enter or update button click **/
    vm.fnStateSelect = function () {
      vm.dataLoad = false;
      vm.confirmedState = angular.copy(vm.selectedState);
      vm.confirmedYear = angular.copy(vm.selectedYear);
      vm.getTransmissionStatuses();
      operationServices.getTransmissionRecords(vm.confirmedState, vm.confirmedYear)
        .then(function (response) {
          /*$log.info("success:  ", response);*/
          vm.transmissionData = response;
          vm.dataLoad = true;
          vm.editModeReference = false;
          vm.radioButton = {
            visible: true,
            hidden: false,
            all: false,
            selected: 'visible'
          };
        }, function () {
          vm.dataLoad = true;
          vm.showSuccessMessage = false;
          vm.showErrorMessage = true;
          vm.errorMessage = vm.errorMessageList.noRecordFound;
        });
    };

    /** Tracking if any row is in edit mode ***/
    vm.setEditMode = function(){
      vm.editModeReference = true;
    };
    vm.resetEditMode = function(){
      vm.editModeReference = false;
    };

    vm.editDestroy = function(){

    };

    /*** Save transmission record success **/
    vm.saveRecord = function(){
      vm.showSuccessMessage = true;
      vm.showErrorMessage = false;
      vm.successMessage = vm.successMessageList.savedSuccessfully;
      operationServices.getTransmissionRecords(vm.confirmedState, vm.confirmedYear)
        .then(function (response) {
          /*$log.info("success:  ", response);*/
          vm.transmissionData = response;
          vm.dataLoad = true;
          vm.editModeReference = false;
          vm.radioButton = {
            visible: true,
            hidden: false,
            all: false,
            selected: 'visible'
          };
        }, function () {
          vm.dataLoad = true;
          vm.showSuccessMessage = false;
          vm.showErrorMessage = true;
          vm.errorMessage = vm.errorMessageList.noRecordFound;
        });
      /** 3 second showing notification **/
      $timeout(function(){
        vm.showSuccessMessage = false;
      }, vm.errorDisplayTimeout);
    };
    /*** Save transmission record failure **/
    vm.saveRecordFailure = function(){
      vm.showSuccessMessage = false;
      vm.showErrorMessage = true;
      vm.errorMessage = vm.errorMessageList.saveFailure;
      /** 3 second showing notification **/
      $timeout(function(){
        vm.showErrorMessage = false;
      },  vm.errorDisplayTimeout);
    };
    /** Save Transmission Failed : Duplicate Receipt ID in database ***/
    vm.receiptDuplicateError = function(){
      vm.showSuccessMessage = false;
      vm.showErrorMessage = true;
      vm.errorMessage = vm.errorMessageList.receiptIdFailure;
      /** 3 second showing notification **/
      $timeout(function(){
        vm.showErrorMessage = false;
      },  vm.errorDisplayTimeout);
    };
    /*** Resend Reject transmission record success **/
    vm.rejectResendSuccess = function(){
      vm.showSuccessMessage = true;
      vm.showErrorMessage = false;
      vm.successMessage = vm.successMessageList.rejectResendSuccessfully;
      /** 3 second showing notification **/
      $timeout(function(){
        vm.showSuccessMessage = false;
      },  vm.errorDisplayTimeout);
    };
    /*** Resend Reject transmission record failure **/
    vm.rejectResendFailure = function(){
      vm.showSuccessMessage = false;
      vm.showErrorMessage = true;
      vm.errorMessage = vm.errorMessageList.rejectResendFailure;
      /** 3 second showing notification **/
      $timeout(function(){
        vm.showErrorMessage = false;
      },  vm.errorDisplayTimeout);
    };
    /*** Resend Corrected transmission record success **/
    vm.correctedResendSuccess = function(){
      vm.showSuccessMessage = true;
      vm.showErrorMessage = false;
      vm.successMessage = vm.successMessageList.correctedResendSuccessfully;
      /** 3 second showing notification **/
      $timeout(function(){
        vm.showSuccessMessage = false;
      },  vm.errorDisplayTimeout);
    };
    /*** Resend Corrected transmission record failure **/
    vm.correctedResendFailure = function(){
      vm.showSuccessMessage = false;
      vm.showErrorMessage = true;
      vm.errorMessage = vm.errorMessageList.correctedResendFailure;
      /** 3 second showing notification **/
      $timeout(function(){
        vm.showErrorMessage = false;
      },  vm.errorDisplayTimeout);
    };

    $scope.$watch('vm.unsavedModeReference', function(){
      operationsPortal.unsavedChanges = angular.copy(vm.unsavedModeReference);
    });

  }

  angular.module('aca1095BUiAppCW')
    .controller('OPXmlStatusCtrl', ['$scope', '$timeout', 'operationServices', 'operationsPortal', OPXmlStatusCtrl]);
}());
