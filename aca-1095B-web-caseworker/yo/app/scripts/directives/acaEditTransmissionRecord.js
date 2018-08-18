/**
 * Created by pchavan on 4/1/2016.
 */

(function () {
  'use strict';
  function acaEditTransmissionRecord(){
    return {
      restrict: "EA",
      scope: {
        recordData: "=recordData",
        recordIndex : "=recordIndex",
        statusList: "=statusList",
        editModeReference : "@editModeReference",
        editSet : "&editSet",
        editReset : "&editReset",
        unsavedModeReference : "=unsavedModeReference",
        /** service API calls **/
        fnSaveRecord : "&saveRecord",
        fnSaveRecordFail : "&saveRecordFail",
        fnReceiptDuplicate : "&receiptDuplicateError",
        fnRejectResendSuccess : "&rejectResendSuccess",
        fnRejectResendFailure : "&rejectResendFailure",
        fnCorrectedResendSuccess : "&correctedResendSuccess",
        fnCorrectedResendFailure : "&correctedResendFailure"
      },
      replace: true,
      templateUrl: 'views/templates/acaEditTransmissionRecord.html',
      link : function(scope){
        scope.mapStatus = function(statusCode){
          switch(statusCode){
            case 'PR' :
            {
              scope.statusSelected = {
                dropdown : "Processing",
                hoverMsg : "Processing",
                nonEdit : "Processing"
              };
            }break;
            case 'AC' :
            {
              scope.statusSelected = {
                dropdown : "Accepted",
                hoverMsg : "Accepted",
                nonEdit : "Accepted"
              };
            }break;
            case 'AE' :
            {
              scope.statusSelected = {
                dropdown : "Errors",
                hoverMsg : "Accepted with Errors",
                nonEdit : "Accepted with Errors"
              };
            }break;
            case 'PA' :
            {
              scope.statusSelected = {
                dropdown : "Partially",
                hoverMsg : "Partially Accepted",
                nonEdit : "Partially Accepted"
              };
            }break;
            case 'NF' :
            {
              scope.statusSelected = {
                dropdown : "Not Found",
                hoverMsg : "Not Found",
                nonEdit : "Not Found"
              };
            }break;
            case 'RJ' :
            {
              scope.statusSelected = {
                dropdown : "Rejected",
                hoverMsg : "Rejected",
                nonEdit : "Rejected"
              };
            }break;
            default :
            {
              scope.statusSelected = {
                dropdown : null,
                hoverMsg : null,
                nonEdit : null
              };
            }
          }
        };
        scope.editMode = false;
        scope.hideMode = angular.copy(scope.recordData.recordVisible);
        scope.showStatus = false;
        scope.statusSelected = {
          dropdown : null,
          hoverMsg : null,
          nonEdit : null
        };
        scope.activeAction = false;
        scope.dataChanged = false;
        scope.unsavedModeReference = angular.copy(scope.dataChanged);
        scope.mapStatus(scope.recordData.transmissionAckStatus);
        scope.moreInfo = false;
        scope.showMoreInfo = scope.statusSelected.nonEdit !== null;
        scope.rejectedMode = scope.recordData.transmissionAckStatus === "RJ";
        scope.popover = {
          templateBig: "<div class='popover popover-xml popover-xml-long' role='tooltip'><div class='arrow'></div><h3 class='popover-title'></h3><div class='popover-content'></div></div>",
          templateSmall: "<div class='popover popover-xml popover-xml-short' role='tooltip'><div class='arrow'></div><h3 class='popover-title'></h3><div class='popover-content'></div></div>",
          templateMedium: "<div class='popover popover-xml popover-xml-medium' role='tooltip'><div class='arrow'></div><h3 class='popover-title'></h3><div class='popover-content'></div></div>"
        };
      },
      controller : function($scope, confirmationDialog, operationServices){
        $scope.saveException_NF = function(){
          /** some status is entered **/
          if(!!$scope.editableFields.transmissionAckStatus && $scope.editableFields.transmissionAckStatus === 'NF'){
            $scope.activeAction = !!$scope.editableFields.transmissionAckStatus && !!$scope.editableFields.transmissionAckDate;
          }
          /** no status is selected **/
          else{
            $scope.activeAction = !!$scope.editableFields.transmissionAckStatus && !!$scope.editableFields.transmissionReceiptId && !!$scope.editableFields.transmissionAckDate;
          }
        };
        $scope.editRecord = function(){
          if($scope.editModeReference === "false" ){
            $scope.editMode = true;
            $scope.editableFields = angular.copy($scope.recordData);
            $scope.hideMode = angular.copy($scope.recordData.recordVisible);
            $scope.mapStatus($scope.recordData.transmissionAckStatus);
            /** Default : Resend status to false **/
            $scope.editableFields.rejectedStatusCorrection = false;
            $scope.editableFields.rejectedStatusResend = false;
            /** save exception for not found **/
            $scope.saveException_NF();
            $scope.editSet();
          }
        };

        $scope.fnShowStatus = function(){
          $scope.showStatus = true;
         // event.stopPropagation();
        };

        $scope.activateAction = function(){
          /** save exception for not found **/
          $scope.saveException_NF();
          $scope.dataChanged =  true;
          $scope.unsavedModeReference = angular.copy($scope.dataChanged);
        };

        $scope.setStatus = function(status){
          $scope.editableFields.transmissionAckStatus = status.statusCd;
          if(status.statusCd === 'AE'){
            $scope.statusSelected.dropdown = 'Errors';
          }else if(status.statusCd === 'PA'){
            $scope.statusSelected.dropdown = 'Partially';
          }else{
            $scope.statusSelected.dropdown = status.statusDesc;
          }
          $scope.statusSelected.hoverMsg = status.statusDesc;
          $scope.activateAction();
          $scope.showStatus = false;
        };

        $(document).on('click', function(){
          $scope.showStatus = false;
        });

        $scope.closeEditRecord = function(){
          $scope.editMode = false;
          $scope.editReset();
          event.stopPropagation();
        };

        $scope.closeModal = function(){
          confirmationDialog.modalBx.close();
        };

        $scope.discardChanges = function(){
          $scope.dataChanged =  false;
          $scope.unsavedModeReference = angular.copy($scope.dataChanged);
          $scope.closeEditRecord();
          $scope.closeModal();
        };

        $scope.closeEditRecordConfirm = function(){
          var modalAttribute = {
            windowClass: 'center-modal',
            animation: false,
            templateUrl: 'views/templates/cautionDialog.html',
            label : {
              title: 'Caution',
              noAction: 'Cancel',
              yesAction: 'OK',
              description: 'Are you sure you want to cancel this entry? If you continue, your unsaved changes will be lost.'
            }
          };
          confirmationDialog.openModal(modalAttribute, $scope.discardChanges, $scope.closeModal, $scope.closeModal);
        };

        $scope.hideShowRecord = function(){
          $scope.hideMode = !$scope.hideMode;
          $scope.activateAction();
          //event.stopPropagation();
        };

        $scope.saveProcessing = function(){
          var modalAttribute = {
            windowClass: 'center-modal',
            animation: false,
            templateUrl: 'views/templates/cautionDialog.html',
            backdrop: 'static',
            keyboard: false,
            label : {
              title: 'Caution',
              noAction: 'Cancel',
              yesAction: 'OK',
              loading: true,
              description: 'Please do not page back, page forward,or refresh the page during the loading process.'
            }
          };
          $scope.closeModal();
          var tempDate = new Date($scope.editableFields.transmissionAckDate);
          $scope.editableFields.transmissionAckDate = tempDate.getTime();
          $scope.editableFields.recordVisible = angular.copy($scope.hideMode);
          confirmationDialog.openModal(modalAttribute, $scope.saveProcessing, $scope.closeModal, $scope.closeModal);

          operationServices.saveTransmissionRecord($scope.editableFields)
            .then(function (response) {
              if(response.count > 0){
                $scope.closeModal();
                $scope.fnReceiptDuplicate();
              }else{
                $scope.closeEditRecord();
                $scope.closeModal();
                $scope.fnSaveRecord();
              }
            }, function () {
              $scope.closeModal();
              $scope.fnSaveRecordFail();
            });
        };

        $scope.saveRecordConfirm = function(){
          var modalAttribute = {
            windowClass: 'center-modal',
            animation: false,
            templateUrl: 'views/templates/confirmationDialog.html',
            label : {
              title: 'Save Changes',
              noAction: 'No',
              yesAction: 'Proceed',
              isHTML : true,
              description: '<div>Are you sure you want to save the status <span class="bold">'+ $scope.statusSelected.hoverMsg + '</span> for<div> Receipt ID <span class="bold">'+ $scope.editableFields.transmissionReceiptId +'</span>.</div><div> Do you want to proceed?</div>'
            }
          };
          confirmationDialog.openModal(modalAttribute, $scope.saveProcessing, $scope.closeModal, $scope.closeModal);
        };

        $scope.switchToMoreInfo = function(){
          $scope.moreInfo = !$scope.moreInfo;
        };

        /** Resend : Reject **/
        $scope.disableResendReject = function(){
          var modalAttribute = {
            windowClass: 'center-modal',
            animation: true,
            templateUrl: 'views/templates/cautionDialog.html',
            label : {
              title: 'Resend Not Allowed',
              noAction: '',
              yesAction: '',
              isHTML : true,
              description: 'You cannot resend the XML file because data has been corrected'
            }
          };

          confirmationDialog.openModal(modalAttribute,$scope.closeModal , $scope.closeModal, $scope.closeModal);

        };
        $scope.resendRejected = function(){
          $scope.editableFields.rejectedStatusResend = true;
          $scope.editableFields.rejectedStatusCorrection = false;
          operationServices.rejectResendCorrectionRecord($scope.editableFields)
            .then(function(response){
              if(response.statusCode > 0){
                $scope.closeModal();
                $scope.editableFields.rejectedStatusResend = false;
                $scope.editableFields.rejectedStatusCorrection = false;
                $scope.disableResendReject();
              }else{
                $scope.closeEditRecord();
                $scope.closeModal();
                $scope.fnRejectResendSuccess();
              }
            }, function(){
              $scope.closeModal();
              $scope.fnRejectResendFailure();
            });
        };

        /** Resend : Corrected **/
        $scope.resendCorrected = function(){
          $scope.editableFields.rejectedStatusResend = false;
          $scope.editableFields.rejectedStatusCorrection = true;
          operationServices.rejectResendCorrectionRecord($scope.editableFields)
            .then(function(response){
              $scope.closeEditRecord();
              $scope.closeModal();
              $scope.fnCorrectedResendSuccess();
            }, function(){
              $scope.closeModal();
              $scope.fnCorrectedResendFailure();
            });
        };

        /** Resend Confirmation : Reject / Corrected **/
        $scope.resendConfirm = function(typeResend){
          /** typeResend : false for Resend Reject and true for Resend Corrected **/
          var modalAttribute = {
            windowClass: 'center-modal',
            animation: true,
            templateUrl: 'views/templates/confirmationDialog.html',
            label : {
              title: typeResend ? 'Resend Corrected' : 'Resend Rejected',
              noAction: 'No',
              yesAction: 'Proceed',
              isHTML : true,
              description: '<div>Are you sure you want to <span class="bold">'+ (typeResend ? 'Resend Corrected ' : 'Resend Rejected ') + '</span> record for<div> Receipt ID <span class="bold">'+ $scope.editableFields.transmissionReceiptId +'</span>.</div><div> Do you want to proceed?</div>'
            }
          };
          confirmationDialog.openModal(modalAttribute, typeResend ? $scope.resendCorrected : $scope.resendRejected, $scope.closeModal, $scope.closeModal);
        };


        $scope.$watch('editModeReference', function(newValue){
          if(!newValue || newValue === "false"){
            $scope.editMode = false;
          }
        });
      }

    };
  }
  angular.module('aca1095BUiAppCW')
    .directive('acaEditTransmissionRecord', acaEditTransmissionRecord);
}());
