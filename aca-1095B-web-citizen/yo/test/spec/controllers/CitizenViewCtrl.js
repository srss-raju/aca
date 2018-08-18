/**
 * Created by pchavan on 5/6/2016.
 */
'use strict';

describe('Controller: CitizenViewCtrl', function () {
  // load the controller's module
  beforeEach(module('aca1095BUiAppCitizen'));

  var CitizenViewCtrl,
    scope, rootScope, element, interval;

  var mockCitizenSession, mockCitizenService, mockInactivityModal;

  beforeEach(function(){
    module(function($provide){
      $provide.value('sampleCitizenService', {
        getSelectedState: function() {
          var responseObject = [];
          responseObject = [{"stateName":"Arkansas","stateCode":"AR"},{"stateName":"Indiana","stateCode":"IN"}];
          return {
            then: function(callback) {return callback(responseObject);}
          };
        },
        data : {
          "bFormView":true,
          "firstName":"William",
          "lastName":"Smith",
          "errorMessage":null,
          "uidType" : 'SSN',
          "uidNumber":"xxx-xx-1123",
          "taxYear":"2015",
          "dob":"03/28/1997",
          "listFormInfos":[
            {
              "generatedDate":"Temp",
              "address1":"Building1",
              "address2":"",
              "city":"Frankort",
              "state":"AR",
              "zipcode":"",
              "docID":"78F6D1D7F0797373E81391A6DD5BF393_ARHSSSIE11042015_01_31000012"
            }
          ],
          "mailReqDate":"10/31/2015",
          "message":"Please check the data on the 1095B form if it requires updates, please call 1-800-yyyy or visit your local office caseworker"
        },
        getDetail : function(){
          return  this.data;
        },
        sendByMail : function(docId){
          var responseObject = [];
          if(docId === '1234Success'){
            responseObject = {"errorMessage":"","successMessage":"your mail request will be processed on provided address."};
          }else{
            responseObject = {"errorMessage":"Error","successMessage":""};
          }
          return {
            then: function(callback) {return callback(responseObject);}
          };
        },
        getPdf : function(docId){
          var responseObject = [];
          if(docId === '1234Success'){
            responseObject = decodeURIComponent('<p>Document to be shown!</p>');
          }else{
            responseObject = {"errorMessage":"Error","successMessage":""};
          }
          return {
            then: function(callback) {return callback(responseObject);}
          };
        }
      });
      $provide.value('sampleCitizenSession', {
        selectedState : "",
        setStateYear : function(_selectedState_){
          this.selectedState = _selectedState_;
        },
        userLogged : false
      });
      $provide.value('sampleInactivityModal', {
        selectedState : "",
        openModal : function(_selectedState_){
          this.selectedState = _selectedState_;
        },
        userLogged : false
      });
    });
  });

  beforeEach(inject(function ($controller, $rootScope, $injector, $location, _$interval_, _inactivityModal_, _sampleCitizenService_, _sampleCitizenSession_) {
    scope = $rootScope.$new();
    rootScope = $rootScope;
    element = jasmine.createSpy('$element');
    interval = _$interval_;
    mockCitizenService = _sampleCitizenService_;
    mockCitizenSession = _sampleCitizenSession_;
    mockInactivityModal = _inactivityModal_;
    CitizenViewCtrl = $controller('CitizenViewGeneralCtrl', {
      $scope: scope,
      $element : element,
      $interval: interval,
      inactivityModal : mockInactivityModal,
      // place here mocked dependencies
      CitizenService : mockCitizenService,
      CitizenSession : mockCitizenSession
    });
  }));

  var mockCitizenData = {
    "bFormView":true,
    "firstName":"William",
    "lastName":"Smith",
    "errorMessage":null,
    "uidType" : 'SSN',
    "uidNumber":"xxx-xx-1123",
    "taxYear":"2015",
    "dob":"03/28/1997",
    "listFormInfos":[
      {
        "generatedDate":"Temp",
        "address1":"Building1",
        "address2":"",
        "city":"Frankort",
        "state":"AR",
        "zipcode":"",
        "docID":"78F6D1D7F0797373E81391A6DD5BF393_ARHSSSIE11042015_01_31000012"
      }
    ],
    "mailReqDate":"10/31/2015",
    "message":"Please check the data on the 1095B form if it requires updates, please call 1-800-yyyy or visit your local office caseworker"
  };

  describe('Check if getDetail Service is handling the exceptions and positive path.', function () {
    beforeEach(function() {
      var elementOn = jasmine.createSpy('$element', ['on']);
    });
    it('Check if getDetail Service is handling the positive path.', function () {
      expect(CitizenViewCtrl.citizenData).toEqual(mockCitizenData);
      expect(mockCitizenService.data).toEqual(jasmine.any(Object));
    });
  });

  describe('Check Default initialization of flags and variables.', function () {
    it('Check title based on bFormView : true', function () {
      expect(CitizenViewCtrl.citizenData.bFormView).toBeTruthy();
      expect(CitizenViewCtrl.label.titleDescription).toBe("1095-B Form");
      expect(CitizenViewCtrl.label.name).toBe("Name");
      expect(CitizenViewCtrl.label.dob).toBe("DOB");
      expect(CitizenViewCtrl.label.ssn).toBe("SSN");
      expect(CitizenViewCtrl.label.year).toBe("Year");
      expect(CitizenViewCtrl.label.form).toBe("Form");
      expect(CitizenViewCtrl.label.genDate).toBe("Generated Date");
      expect(CitizenViewCtrl.label.address).toBe("Address");
      expect(CitizenViewCtrl.label.city).toBe("City");
      expect(CitizenViewCtrl.label.state).toBe("State");
      expect(CitizenViewCtrl.label.zipCode).toBe("Zipcode");

      expect(CitizenViewCtrl.viewingPdf).toBeFalsy();
      expect(CitizenViewCtrl.sendMailSuccess).toBeFalsy();
      expect(CitizenViewCtrl.sendMailFail).toBeFalsy();
      expect(CitizenViewCtrl.dateMailedInterval).toBeFalsy();
    });

  });

});
