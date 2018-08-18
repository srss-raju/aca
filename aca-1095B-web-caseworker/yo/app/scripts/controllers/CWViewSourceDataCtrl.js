/**
 * Created by pchavan on 10/15/2015.
 */
'use strict';

(function () {

  function CWViewSourceData ($scope, $state, $log,ENV, CWService,$timeout, $interval, confirmationDialog){

    /** Screen Labels **/
    $scope.label = {
      titleAction : "Recipient",
      titleDescription : "Data",
      fName:"First Name:",
      lName:"Last Name:",
      address:"Address:",
      dob:"DOB:",
      gender:"Gender:",
      city:"City:",
      state:"State:",
      ssn: "SSN:",
      tin: "TIN:",
      email: "Email:",
      noSsnTin: "No SSN / TIN available",
      zipcode:"Zip:",
      coverage:"Coverage:",
      comment:"Comments:",
      recipientHeading: "Recipient Information",
      currentCoverage: "Current Coverage",
      coverageSource: "Coverage Source(s)",
      source:"Source:",
      lastmodified:"Last Modified",
      caseid:"Case ID:",
      pdfstatus:"Form Status:",
      jan:"Jan",
      feb:"Feb",
      mar:"Mar",
      apr:"Apr",
      may:"May",
      jun:"Jun",
      jul:"Jul",
      aug:"Aug",
      sep:"Sep",
      oct:"Oct",
      nov:"Nov",
      dec:"Dec"
    };

    $scope.spinnerOn = true;

    $scope.$watch(function () {
      return confirmationDialog.modalBox
    }, function (newVal, oldVal) {
      if (typeof newVal !== 'undefined') {
        $scope.modalBox = confirmationDialog.modalBox;
      }
    });

    $scope.iconstatus={
      editiconstatus:false,
      editiconenable:false,
      pdficonstatus:false,
      pdfprocessstatus:false,
      pdfstatuscolor:'green',
      mailiconstatus:false,
      mailiconenable:false
    };

    $scope.editXmlCorrection = false;

    var form=null,
      role=null,
      userrole=null;
    form = CWService.cViewGet();

    if(form){
    CWService.getSourceData(form.formId)
        .success(function(data){
          $scope.searchUserDetails = data;
          $scope.spinnerOn = false;
          switch(data.currentForm.pdfStatus){
            case'GENERATED':
              $scope.iconstatus={
                editiconstatus:true,
                editiconenable:true,
                pdficonstatus:true,
                pdficonenable:true,
                pdfprocessstatus:false,
                mailiconstatus:true,
                mailiconenable:true
              };
              break;
            case'REGENERATED':
              $scope.iconstatus={
                editiconstatus:true,
                editiconenable:true,
                pdficonstatus:false,
                pdfprocessstatus:true,
                mailiconstatus:true,
                mailiconenable:false
              };
              break;
            case'GENERATION_FAILED':
              $scope.iconstatus={
                editiconstatus:true,
                editiconenable:true,
                pdficonstatus:false,
                pdfprocessstatus:true,
                mailiconstatus:true,
                mailiconenable:false
              };
              break;
            case'ADDRESS_INVALID':
              $scope.iconstatus={
                editiconstatus:true,
                editiconenable:true,
                pdficonstatus:true,
                pdficonenable:true,
                pdfprocessstatus:false,
                mailiconstatus:true,
                mailiconenable:false
              };
              break;
            default:
              $scope.icon={
                editiconstatus:false,
                editiconenable:false,
                pdficonstatus:false,
                pdficonenable:false,
                pdfprocessstatus:false,
                mailiconstatus:false,
                mailiconenable:false
              };
              break;
          }

          if($scope.user){
            role = angular.lowercase($scope.user.role);
            userrole= ENV.unicornRolePreFix + ENV.caseworker_read_write;
            userrole = angular.lowercase(userrole);
            $scope.iconstatus.editiconenable = (role === userrole);
            $scope.editXmlCorrection = role===userrole && $scope.searchUserDetails.currentForm.irsTransmissionStatusCd === 'XC';
          }

          if (data.filerInfo.status.toLowerCase() == 'inactive') {
            $scope.iconstatus.editiconenable = false;
          }

          /** Aria Label **/
          $scope.ariaLabel = {
            title : "You currently viewing demographic and coverage details for the filer, you can view / edit coverage, view PDF version of the form and request to send a mailed copy to the filer"
          };

          var timerFocus = $interval(function(){
              $('.view-source-container .screen-title').eq(0).focus();
              $interval.cancel(timerFocus);
          }, 200);

        })
        .error(function() {

        });
    }

    /* error and success message */
    $scope.errroflag = false;
    $scope.successflag = false;
    $scope.errorMessage = "Sorry, no record has been found.";
    $scope.successMesasge = "Your Correction to the 1095-B form has been successfully processed to be sent by mail";

    /*disable the buttons*/
    $scope.buttondisable = false;
    $scope.buttonvalue='sourceData';


    $scope.edit_customer_data = function(){
      $state.go('state.cw-correct-data');
    };

    $scope.view_pdf = function(){
      CWService.setCurrentData($scope.searchUserDetails.currentForm, $scope.searchUserDetails.filerInfo);
      $scope.currentFormView = CWService.getCurrentData();

      if (typeof $scope.currentFormView !== 'undefined') {

        CWService.getCurrentDataPdf($scope.currentFormView.currentForm.formID)
          .then(function (data) {
            if (data.errorMessage === undefined || data.errorMessage === null || data.errorMessage === '') {
              CWService.setFormData(data);
              CWService.setPreviousState('state.cw-view-source-data');
              $state.go('state.cw-view-form');
            } else {
              /** user not found **/
            }
          }, function () {
          });
      }
    };

    $scope.regenerate_pdf = function(){

    };


    /** Send by mail **/
    $scope.sendMailMsg = {
      "successLabel" : "Your 1095-B form has been successfully processed to be sent by mail.",
      "errorLabel": "Error in mailing request."
    };

    $scope.mail_form_confirm = function(){
      var currentForm = $scope.searchUserDetails.currentForm;
      var modalAttribute = {
        windowClass: 'center-modal',
        animation: true,
        templateUrl: 'views/templates/confirmationDialog.html',
        label : {
          title: 'Mail 1095-B Form',
          noAction: 'No',
          yesAction: 'Proceed',
          description: 'Are you sure you want to mail the form?'
        },
        modalSize: 'small'
      };

      if (!confirmationDialog.modalBox) {
        confirmationDialog.openModal(modalAttribute, $scope.mail_form, $scope.closeModal, $scope.closeModal);
        confirmationDialog.modalBox = true;
      }

    };

    $scope.mail_form = function(){
      CWService.mailRequest($scope.searchUserDetails.currentForm.formID).success(function(data){
          if(data=== CWService.constants.readyToMail){
            $scope.sendMailSuccess = true;
            $scope.sendMailFail = false;
            $scope.searchUserDetails.currentForm.mailStatus = data;
            $timeout(function () {
              $scope.sendMailSuccess = false;
            }, 5000);
          }
          else{
            $scope.sendMailSuccess = false;
            $scope.sendMailFail = true;
            $timeout(function () {
              $scope.sendMailFail = false;
            }, 5000);
          }
          confirmationDialog.modalBx.close();
          confirmationDialog.modalBox = false;
        }
      ).error(function(){
          $scope.sendMailSuccess = false;
          $scope.sendMailFail = true;
          $timeout(function () {
            $scope.sendMailFail = false;
          }, 5000);
          confirmationDialog.modalBx.close();
          confirmationDialog.modalBox = false;
        });
    };

    $scope.closeModal = function(){
      confirmationDialog.modalBx.close();
      confirmationDialog.modalBox = false;
    };

    var savedflag = CWService.getSaveFlag();
    if(savedflag){
      $scope.successflag = true;
      $scope.successMessage = "Data has been successfully updated";
      $timeout(function(){
        $scope.successflag = false;
      },5000);
      CWService.setSaveFlag(false);
    }
    else{
      $scope.successflag = false;
    }
  }

  angular.module('aca1095BUiAppCW').controller('CWViewSourceDataCtrl', ['$scope', '$state', '$log','ENV','CWService','$timeout', '$interval', 'confirmationDialog', CWViewSourceData]);
}());
