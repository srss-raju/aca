/**
 * Created by pchavan on 3/30/2016.
 */

'use strict';

describe('operationServices', function () {
  var operationService,
    httpBackend, mockENV;

  beforeEach(function () {
    // load service's module.
    module('aca1095BUiAppCW');

    // inject httpBackend
    inject(function ($httpBackend, _operationServices_, $injector) {
      operationService = _operationServices_;
      httpBackend = $httpBackend;
      mockENV = $injector.get('ENV');
    });
  });

  afterEach(function () {
    httpBackend.verifyNoOutstandingExpectation();
    httpBackend.verifyNoOutstandingRequest();
  });

  describe('Check setup', function () {
    var test1 = it('Check setup for service : OPService', function () {
      console.log(test1.description);
      expect(operationService.url).toBe(mockENV.endPoint);
    });
  });

  describe('GET: Check getStates', function () {
    var test1 = it('GET: Check getStates', function () {
      console.log(test1.description);
      var returnData = [{"stateName": "Arkansas", "stateCode": "AR"}, {
        "stateName": "Indiana",
        "stateCode": "IN"
      }, {"stateName": "Louisiana", "stateCode": "LA"}];

      httpBackend.expectGET(operationService.url + '/opsportal/getStates').respond(returnData);

      var returnedPromise = operationService.getStates();

      var result;
      returnedPromise.then(function (response) {
        result = response;
      });

      httpBackend.flush();
      expect(result).toEqual(returnData);

    });
  });

  describe('GET: Check getTransmissionStatuses', function () {
    var test1 = it('GET: Check getTransmissionStatuses', function () {
      console.log(test1.description);
      var returnData = [{"statusCd": "PR", "statusDesc": "Processing"}, {"statusCd": "AC", "statusDesc": "Accepted"},
        {"statusCd": "AE", "statusDesc": "Accepted with Errors"}, {
          "statusCd": "PA",
          "statusDesc": "Partially Accepted"
        },
        {"statusCd": "NF", "statusDesc": "Not Found"}, {"statusCd": "RJ", "statusDesc": "Rejected"}];

      httpBackend.expectGET(operationService.url + '/opsportal/getTransmissionStatuses').respond(returnData);

      var returnedPromise = operationService.getTransmissionStatuses();

      var result;
      returnedPromise.then(function (response) {
        result = response;
      });

      httpBackend.flush();
      expect(result).toEqual(returnData);

    });
  });

  /*describe('GET: Check getTransmissionRecords', function () {
    var test1 = it('GET: Check getTransmissionRecords', function () {
      console.log(test1.description);
      var stateSelected = {"stateName": "Arkansas", "stateCode": "AR"};
      var returnData = [{"statusCd": "PR", "statusDesc": "Processing"}, {"statusCd": "AC", "statusDesc": "Accepted"},
        {"statusCd": "AE", "statusDesc": "Accepted with Errors"}, {
          "statusCd": "PA",
          "statusDesc": "Partially Accepted"
        },
        {"statusCd": "NF", "statusDesc": "Not Found"}, {"statusCd": "RJ", "statusDesc": "Rejected"}];

      httpBackend.expectGET(operationService.url + '/opsportal/getTransmissionRecords?stateCd=' + stateSelected.stateCode).respond(returnData);

      var returnedPromise = operationService.getTransmissionStatuses();

      var result;
      returnedPromise.then(function (response) {
        result = response;
      });

      httpBackend.flush();
      expect(result).toEqual(returnData);

    });
  });*/
});
