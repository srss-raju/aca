/**
 * Created by pchavan on 3/30/2016.
 */

'use strict';

describe('Controller: OPXmlStatusCtrl', function () {
  // load the controller's module
  beforeEach(module('aca1095BUiAppCW'));

  var OPXmlStatusCtrl,
    scope, rootScope;

  var mockOPService;
  // mock service
  beforeEach(function(){
    module(function($provide){
      $provide.value('sampleOPService', {
        getStates: function() {
          var responseObject = {};
          responseObject = [{"stateName":"Arkansas","stateCode":"AR"},{"stateName":"Indiana","stateCode":"IN"},{"stateName":"Louisiana","stateCode":"LA"}];
          return {
            then: function(callback) {return callback(responseObject);}
          };
        },
        getTransmissionStatuses: function() {
          var responseObject = {};
          responseObject = [{"statusCd": "PR","statusDesc": "Processing"},{"statusCd": "AC", "statusDesc": "Accepted"},
                            {"statusCd": "AE","statusDesc": "Accepted with Errors"}, {"statusCd": "PA", "statusDesc": "Partially Accepted"},
                            {"statusCd": "NF","statusDesc": "Not Found"}, {"statusCd": "RJ","statusDesc": "Rejected"}];
          return {
            then: function(callback) {return callback(responseObject);}
          };
        },
        getTransmissionRecords: function(stateSelected) {
          var responseObject = {};
          if(stateSelected){
            responseObject = [{"transmissionId": 215, "transferDate": 1459785166217, "transmissionFileName": "1094B_Request_BB9RB_20160404T115246215Z.xml", "transmissionReceiptId": null, "transmissionDate": null, "transmissionAckStatus": null, "transmissionAckDate": null, "updatedDate": 1459785166217, "updatedBy": "Step3Form109495Writer", "recordVisible": true, "rejectedStatusCorrection": false, "rejectedStatusResend": false}];
          }
          return {
            then: function(callback) {return callback(responseObject);}
          };
        }
      });
    });
  });

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope, _sampleOPService_) {
    scope = $rootScope.$new();
    rootScope = $rootScope;
    mockOPService = _sampleOPService_;
    OPXmlStatusCtrl = $controller('OPXmlStatusCtrl', {
      $scope: scope,
      // place here mocked dependencies
      operationServices : mockOPService
    });
  }));


  describe('Check labels and initializations', function(){
    var test1 = it('Check labels', function(){
      console.log(test1.description);
      expect(OPXmlStatusCtrl.label.titleAction).toBe("Operations");
      expect(OPXmlStatusCtrl.label.titleDescription).toBe("Team Portal");
      expect(OPXmlStatusCtrl.label.visibleRadio).toBe("visible");
      expect(OPXmlStatusCtrl.label.hiddenRadio).toBe("hidden");
      expect(OPXmlStatusCtrl.label.allRadio).toBe("all");
      expect(OPXmlStatusCtrl.label.transferDate).toBe("Transfer Date");
      expect(OPXmlStatusCtrl.label.transmissionFileName).toBe("File Name");
      expect(OPXmlStatusCtrl.label.transmissionAckDate).toBe("Transmission ACK Date");
      expect(OPXmlStatusCtrl.label.transmissionReceiptId).toBe("Receipt ID");
      expect(OPXmlStatusCtrl.label.transmissionAckStatus).toBe("Status");
      expect(OPXmlStatusCtrl.label.actions).toBe("Actions");
    });

    var test2 = it('Check initializations', function(){
      console.log(test2.description);
      expect(OPXmlStatusCtrl.dataSearch).toBeNull();
      expect(OPXmlStatusCtrl.selectedState).toEqual(jasmine.any(Object));
      expect(OPXmlStatusCtrl.selectedState.stateCode).toBeNull();
      expect(OPXmlStatusCtrl.selectedState.stateName).toBeNull();
      expect(OPXmlStatusCtrl.confirmedState).toEqual(jasmine.any(Object));
      expect(OPXmlStatusCtrl.confirmedState.stateCode).toBeNull();
      expect(OPXmlStatusCtrl.confirmedState.stateName).toBeNull();
      expect(OPXmlStatusCtrl.dataLoad).toBeFalsy();
      expect(OPXmlStatusCtrl.showSuccessMessage).toBeFalsy();
      expect(OPXmlStatusCtrl.showErrorMessage).toBeFalsy();
      expect(OPXmlStatusCtrl.successMessage).toBe("Data received");
      expect(OPXmlStatusCtrl.errorMessage).toBe("Data not fetched");
      expect(OPXmlStatusCtrl.errorMessageList.noRecordFound).toBe("No records have been found in your search result. Please try again.");
      expect(OPXmlStatusCtrl.errorMessageList.transmissionStatusFailure).toBe("GET : Transmission Status API call failure");
      expect(OPXmlStatusCtrl.errorMessageList.saveFailure).toBe("An error occurred while saving your changes. Please try again.");
      expect(OPXmlStatusCtrl.errorMessageList.rejectResendFailure).toBe("An error occurred while Resend Rejected record. Please try again.");
      expect(OPXmlStatusCtrl.errorMessageList.correctedResendFailure).toBe("An error occurred while Resend Corrected record. Please try again.");
      expect(OPXmlStatusCtrl.errorMessageList.rejectResendDecline).toBe("You cannot resend the XML file because data has been corrected.");
      expect(OPXmlStatusCtrl.successMessageList.savedSuccessfully).toBe("Your data has been successfully updated.");
      expect(OPXmlStatusCtrl.successMessageList.transmissionStatusFailure).toBe("GET : Transmission Status API call failure");
      expect(OPXmlStatusCtrl.successMessageList.rejectResendSuccessfully).toBe("Record has been successfully Resend Rejected.");
      expect(OPXmlStatusCtrl.successMessageList.correctedResendSuccessfully).toBe("Record has been successfully Resend Corrected.");
      expect(OPXmlStatusCtrl.stateList).toEqual(jasmine.any(Array));
      expect(OPXmlStatusCtrl.transmissionData).toEqual(jasmine.any(Array));
      expect(OPXmlStatusCtrl.filteredTransmissionData).toEqual(jasmine.any(Array));
      expect(OPXmlStatusCtrl.transmissionStatusList).toEqual(jasmine.any(Array));
      expect(OPXmlStatusCtrl.editModeReference).toBeFalsy();
      expect(OPXmlStatusCtrl.unsavedModeReference).toBeFalsy();
      expect(OPXmlStatusCtrl.radioButton.visible).toBeTruthy();
      expect(OPXmlStatusCtrl.radioButton.hidden).toBeFalsy();
      expect(OPXmlStatusCtrl.radioButton.all).toBeFalsy();
      expect(OPXmlStatusCtrl.radioButton.selected).toBe('visible');  // default condition check
    });
  });

  describe('Check Radio Toggle', function(){
    var test1 = it('Check radio toggle function definition', function(){
      console.log(test1.description);
      expect(OPXmlStatusCtrl.radioToggle).toBeDefined();
    });
    var radioType = ['visible', 'hidden', 'all', '', null, 'default'];
    var test2 = it('Check radio toggle function with scenarios', function(){
      OPXmlStatusCtrl.confirmedState = 'Arkansas';
      console.log(test2.description);
      OPXmlStatusCtrl.radioToggle(radioType[0]);
      expect(OPXmlStatusCtrl.radioButton.visible).toBeTruthy();
      expect(OPXmlStatusCtrl.radioButton.hidden).toBeFalsy();
      expect(OPXmlStatusCtrl.radioButton.all).toBeFalsy();
      expect(OPXmlStatusCtrl.radioButton.selected).toBe('visible');
      OPXmlStatusCtrl.radioToggle(radioType[1]);
      expect(OPXmlStatusCtrl.radioButton.visible).toBeFalsy();
      expect(OPXmlStatusCtrl.radioButton.hidden).toBeTruthy();
      expect(OPXmlStatusCtrl.radioButton.all).toBeFalsy();
      expect(OPXmlStatusCtrl.radioButton.selected).toBe('hidden');
      OPXmlStatusCtrl.radioToggle(radioType[2]);
      expect(OPXmlStatusCtrl.radioButton.visible).toBeFalsy();
      expect(OPXmlStatusCtrl.radioButton.hidden).toBeFalsy();
      expect(OPXmlStatusCtrl.radioButton.all).toBeTruthy();
      expect(OPXmlStatusCtrl.radioButton.selected).toBe('all');
      OPXmlStatusCtrl.radioToggle(radioType[3]);
      expect(OPXmlStatusCtrl.radioButton.visible).toBeTruthy();
      expect(OPXmlStatusCtrl.radioButton.hidden).toBeFalsy();
      expect(OPXmlStatusCtrl.radioButton.all).toBeFalsy();
      expect(OPXmlStatusCtrl.radioButton.selected).toBe('visible');
      OPXmlStatusCtrl.radioToggle(radioType[4]);
      expect(OPXmlStatusCtrl.radioButton.visible).toBeTruthy();
      expect(OPXmlStatusCtrl.radioButton.hidden).toBeFalsy();
      expect(OPXmlStatusCtrl.radioButton.all).toBeFalsy();
      expect(OPXmlStatusCtrl.radioButton.selected).toBe('visible');
      OPXmlStatusCtrl.radioToggle(radioType[5]);
      expect(OPXmlStatusCtrl.radioButton.visible).toBeTruthy();
      expect(OPXmlStatusCtrl.radioButton.hidden).toBeFalsy();
      expect(OPXmlStatusCtrl.radioButton.all).toBeFalsy();
      expect(OPXmlStatusCtrl.radioButton.selected).toBe('visible');
    });
  });

  describe('Check for GET State list', function(){
    var test1 = it('Check for success of getStates service', function(){
      console.log(test1.description);
      spyOn(mockOPService, 'getStates').and.callThrough();
      OPXmlStatusCtrl.getStateList();
      rootScope.$digest();
      expect(mockOPService.getStates).toHaveBeenCalled();
      expect(OPXmlStatusCtrl.stateList).toEqual([{"stateName":"Arkansas","stateCode":"AR"},{"stateName":"Indiana","stateCode":"IN"},{"stateName":"Louisiana","stateCode":"LA"}]);
    });
  });

  describe('Check for GET Transmission Statuses list', function(){
    var test1 = it('Check for success of getTransmissionStatuses service', function(){
      console.log(test1.description);
      spyOn(mockOPService, 'getTransmissionStatuses').and.callThrough();
      OPXmlStatusCtrl.getTransmissionStatuses();
      rootScope.$digest();
      expect(mockOPService.getTransmissionStatuses).toHaveBeenCalled();
      expect(OPXmlStatusCtrl.transmissionStatusList).toEqual([{"statusCd": "PR","statusDesc": "Processing"},{"statusCd": "AC", "statusDesc": "Accepted"}, {"statusCd": "AE","statusDesc": "Accepted with Errors"}, {"statusCd": "PA", "statusDesc": "Partially Accepted"}, {"statusCd": "NF","statusDesc": "Not Found"}, {"statusCd": "RJ","statusDesc": "Rejected"}]);
    });
  });

  describe('Check for select state and retrieve transmission records', function(){
    var test1 = it('Check for select state and retrieve transmission records', function(){
      console.log(test1.description);
      var mockState = {"stateName":"Arkansas","stateCode":"AR"};
      spyOn(mockOPService, 'getTransmissionStatuses').and.callThrough();
      spyOn(mockOPService, 'getTransmissionRecords').and.callThrough();
      spyOn(OPXmlStatusCtrl, 'getTransmissionStatuses').and.callThrough();
      expect(OPXmlStatusCtrl.dataLoad).toBeFalsy();
      OPXmlStatusCtrl.selectedState = mockState;
      OPXmlStatusCtrl.fnStateSelect();
      rootScope.$digest();
      expect(OPXmlStatusCtrl.confirmedState).toEqual(mockState);
      expect(OPXmlStatusCtrl.getTransmissionStatuses).toHaveBeenCalled();
      expect(mockOPService.getTransmissionStatuses).toHaveBeenCalled();
      expect(mockOPService.getTransmissionRecords).toHaveBeenCalledWith(OPXmlStatusCtrl.confirmedState);
      expect(OPXmlStatusCtrl.transmissionData).toEqual([{"transmissionId": 215, "transferDate": 1459785166217, "transmissionFileName": "1094B_Request_BB9RB_20160404T115246215Z.xml", "transmissionReceiptId": null, "transmissionDate": null, "transmissionAckStatus": null, "transmissionAckDate": null, "updatedDate": 1459785166217, "updatedBy": "Step3Form109495Writer", "recordVisible": true, "rejectedStatusCorrection": false, "rejectedStatusResend": false}]);
      expect(OPXmlStatusCtrl.dataLoad).toBeTruthy();
      expect(OPXmlStatusCtrl.editModeReference).toBeFalsy();
      expect(OPXmlStatusCtrl.radioButton.visible).toBeTruthy();
      expect(OPXmlStatusCtrl.radioButton.hidden).toBeFalsy();
      expect(OPXmlStatusCtrl.radioButton.all).toBeFalsy();
      expect(OPXmlStatusCtrl.radioButton.selected).toBe('visible');
    });
  });

  describe('Check for set rest edit mode', function(){
    var mockData = [true, false];
    var test1 = it('Check for set edit mode', function(){
      console.log(test1.description);
      OPXmlStatusCtrl.editModeReference = mockData[1];
      expect(OPXmlStatusCtrl.editModeReference).toBeFalsy();
      OPXmlStatusCtrl.setEditMode();
      rootScope.$digest();
      expect(OPXmlStatusCtrl.editModeReference).toBeTruthy();
    });
    var test2 = it('Check for reset edit mode', function(){
      console.log(test2.description);
      OPXmlStatusCtrl.editModeReference = mockData[0];
      expect(OPXmlStatusCtrl.editModeReference).toBeTruthy();
      OPXmlStatusCtrl.resetEditMode();
      rootScope.$digest();
      expect(OPXmlStatusCtrl.editModeReference).toBeFalsy();
    });
  });

  describe('Check for save transmission record', function(){
    var test1 = it('Check for success function', function(){
      console.log(test1.description);
      OPXmlStatusCtrl.saveRecord();
      rootScope.$digest();
      expect(OPXmlStatusCtrl.showSuccessMessage).toBeTruthy();
      expect(OPXmlStatusCtrl.showErrorMessage).toBeFalsy();
      expect(OPXmlStatusCtrl.successMessage).toEqual(OPXmlStatusCtrl.successMessageList.savedSuccessfully);
    });
    var test2 = it('Check for failure function', function(){
      console.log(test2.description);
      OPXmlStatusCtrl.saveRecordFailure();
      rootScope.$digest();
      expect(OPXmlStatusCtrl.showSuccessMessage).toBeFalsy();
      expect(OPXmlStatusCtrl.showErrorMessage).toBeTruthy();
      expect(OPXmlStatusCtrl.errorMessage).toEqual(OPXmlStatusCtrl.errorMessageList.saveFailure);
    });
  });

  describe('Check for Resend Reject record', function(){
    var test1 = it('Check for success function', function(){
      console.log(test1.description);
      OPXmlStatusCtrl.rejectResendSuccess();
      rootScope.$digest();
      expect(OPXmlStatusCtrl.showSuccessMessage).toBeTruthy();
      expect(OPXmlStatusCtrl.showErrorMessage).toBeFalsy();
      expect(OPXmlStatusCtrl.successMessage).toEqual(OPXmlStatusCtrl.successMessageList.rejectResendSuccessfully);
    });
    var test2 = it('Check for failure function', function(){
      console.log(test2.description);
      OPXmlStatusCtrl.rejectResendFailure();
      rootScope.$digest();
      expect(OPXmlStatusCtrl.showSuccessMessage).toBeFalsy();
      expect(OPXmlStatusCtrl.showErrorMessage).toBeTruthy();
      expect(OPXmlStatusCtrl.errorMessage).toEqual(OPXmlStatusCtrl.errorMessageList.rejectResendFailure);
    });
  });

  describe('Check for Resend Corrected record', function(){
    var test1 = it('Check for success function', function(){
      console.log(test1.description);
      OPXmlStatusCtrl.correctedResendSuccess();
      rootScope.$digest();
      expect(OPXmlStatusCtrl.showSuccessMessage).toBeTruthy();
      expect(OPXmlStatusCtrl.showErrorMessage).toBeFalsy();
      expect(OPXmlStatusCtrl.successMessage).toEqual(OPXmlStatusCtrl.successMessageList.correctedResendSuccessfully);
    });
    var test2 = it('Check for failure function', function(){
      console.log(test2.description);
      OPXmlStatusCtrl.correctedResendFailure();
      rootScope.$digest();
      expect(OPXmlStatusCtrl.showSuccessMessage).toBeFalsy();
      expect(OPXmlStatusCtrl.showErrorMessage).toBeTruthy();
      expect(OPXmlStatusCtrl.errorMessage).toEqual(OPXmlStatusCtrl.errorMessageList.correctedResendFailure);
    });
  });


});
