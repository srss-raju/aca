/**
 * Created by pchavan on 4/19/2016.
 */

'use strict';

describe('Controller: CitizenFormCtrl', function () {

  // load the controller's module
  beforeEach(module('aca1095BUiAppCitizen'));

  var CitizenFormCtrl,
    scope, rootScope, element, state, stateParams;
  var mockCitizenSession, mockCitizenService, gRecaptcha;

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
        getStates: function() {
          var responseObject = [];
          responseObject = {"list":["AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"]};
          return {
            then: function(callback) {return callback(responseObject);}
          };
        },
        identifyCitizen: function(user){
          var responseObject = [];
          if(user){
            responseObject = {"bFormView":false,
              "firstName": "William",
              "lastName": "Smith",
              "errorMessage": null,
              "ssnValue": "xxx-xx-1123",
              "taxYear": "2015",
              "dob": "03/28/1997",
              "listFormInfos": [
                {
                  "generatedDate": "Temp",
                  "address1": "Building1",
                  "address2": "",
                  "city": "Frankort",
                  "state": "AR",
                  "zipcode": "",
                  "docID": "78F6D1D7F0797373E81391A6DD5BF393_ARHSSSIE11042015_01_31000012"
                }
              ],
              "mailReqDate": "10/31/2015",
              "message": "Please check the data on the 1095B form if it requires updates, please call 1-800-yyyy or visit your local office caseworker"
            };
          }
          return {
            then: function(callback) {return callback(responseObject);}
          };
        },
        setData : function(data){
          this.data = data;
        }
      });

      $provide.value('sampleCitizenSession', {
        selectedState : "",
        selectedYear : "",
        viewPage : "",
        staticState : "",
        userLogged : false,

        setStateCode : function(selectedState){
           this.selectedState = selectedState;
        },

        setStateYear : function(selectedYear){
           this.selectedYear = selectedYear;
        },

        setViewPage : function(type){
           this.viewPage = type;
        },

        setStaticState : function(state){
           this.staticState = state;
        },

        resetStateCode : function(){
           this.selectedState = "";
        },

        resetStateYear : function(){
           this.selectedYear = "";
        },

        resetViewPage : function(){
           this.viewPage = "";
        },

        resetStaticState : function(){
           this.staticState = "";
        },

        reset : function(){
           this.resetStateCode();
           this.resetStateYear();
           this.resetViewPage();
           this.resetStaticState();
        },

        userLoggedIn : function(){
            this.userLogged = true;
        },

        userLoggedOut : function(){
            this.userLogged = false;
            this.reset();
            $state.go('citizen.citizen-state');
        }

      });

      $provide.value('sampleState', {
        go : function(state){
          if(state){

          }
        }
      });

      $provide.value('sampleRecaptcha', {
        getResponse : function(){
          return "responsereceivedfromg00gl3";
        }
      });
    });
  });

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope, $injector, $location, _sampleState_, _$stateParams_, _sampleCitizenService_, _sampleCitizenSession_) {
    scope = $rootScope.$new();
    rootScope = $rootScope;
    element = jasmine.createSpy('$element');
    state = _sampleState_;
    stateParams = _$stateParams_;
    gRecaptcha = jasmine.createSpy('grecaptcha');
    mockCitizenService = _sampleCitizenService_;
    mockCitizenSession = _sampleCitizenSession_;
    CitizenFormCtrl = $controller('CitizenFormCtrl', {
      $scope: scope,
      $element : element,
      $state: state,
      // place here mocked dependencies
      CitizenService : mockCitizenService,
      CitizenSession : mockCitizenSession
    });
  }));

  describe('Check for initialization.', function () {
    var test1 = it('Check for selected param and state to be different', function () {
      console.log(test1.description);
      spyOn(state, 'go');
      stateParams.selectedState = "AR";
      CitizenFormCtrl.checkUserState();
      //rootScope.$digest();
      expect(CitizenFormCtrl.goToStateSelect).toBeTruthy();
      expect(CitizenFormCtrl.screenLoad).toBeFalsy();
      expect(state.go).toHaveBeenCalledWith('citizen.citizen-state');
    });

    var test2 = it('Check for selected param and state to be same : getStates : Positive Path', function () {
      console.log(test2.description);
      spyOn(CitizenFormCtrl, 'getStates');
      stateParams.selectedState = "AR";
      stateParams.selectedYear = "2015";
      mockCitizenSession.selectedState = "AR";
      mockCitizenSession.selectedYear = "2015";
      CitizenFormCtrl.checkUserState();
      //rootScope.$digest();
      expect(CitizenFormCtrl.getStates).toHaveBeenCalled();
      expect(CitizenFormCtrl.goToStateSelect).toBeFalsy();
      expect(CitizenFormCtrl.toggleOn).toBeTruthy();
      expect(CitizenFormCtrl.eConsent).toBeFalsy();
      expect(CitizenFormCtrl.ariaCheckBox).toBe("Press Enter to check this box. By checking this box you are consenting to have your I R S Form 1095-B delivered by mail or electronically through this portal.");
    });

    var test3 = it('Check for getStates : Positive Path', function () {
      console.log(test3.description);

      spyOn(mockCitizenService, 'getStates').and.callThrough();
      spyOn(mockCitizenSession, 'setStateYear').and.callThrough();
      CitizenFormCtrl.goToStateSelect = false;
      CitizenFormCtrl.getStates();
      //rootScope.$digest();
      expect(CitizenFormCtrl.goToStateSelect).toBeFalsy();
      expect(mockCitizenService.getStates).toHaveBeenCalled();
      expect(CitizenFormCtrl.screenLoad).toBeTruthy();
      expect(mockCitizenSession.setStateYear).toHaveBeenCalled();
    });

    var test4 = it('Check for initController', function () {
      console.log(test4.description);
      spyOn(CitizenFormCtrl, 'getLabel');
      CitizenFormCtrl.initController();
      //rootScope.$digest();
      expect(CitizenFormCtrl.captchaFlag).toBeFalsy();
      expect(CitizenFormCtrl.userNotFound).toBeFalsy();
      expect(CitizenFormCtrl.uidTypePlaceHolder).toBe("Social Security Number");
      expect(CitizenFormCtrl.toggleOn).toBeTruthy();
      expect(CitizenFormCtrl.eConsent).toBeFalsy();
      expect(CitizenFormCtrl.disabledSignIn).toBeTruthy();
      expect(CitizenFormCtrl.user).toEqual({userFname: null, userLname: null, uidNumber: null, uidType: 'SSN', dob: null, streetAddress1: null, city: null, state: null, zipcode: null, gRecaptchaResponse : null});
      expect(CitizenFormCtrl.getLabel).toHaveBeenCalled();
    });

    var test5 = it('Check for getLabel', function () {
      console.log(test5.description);
      CitizenFormCtrl.selectedState = "AR";
      CitizenFormCtrl.getLabel();
      //rootScope.$digest();
      expect(CitizenFormCtrl.formSubText).toBe("Enter data as reported to Department of Human Services. ");
      expect(CitizenFormCtrl.stateName).toBe("Arkansas");
      CitizenFormCtrl.selectedState = "IN";
      CitizenFormCtrl.getLabel();
      //rootScope.$digest();
      expect(CitizenFormCtrl.formSubText).toBe("");
      expect(CitizenFormCtrl.stateName).toBe("Indiana");
      CitizenFormCtrl.selectedState = "LA";
      CitizenFormCtrl.getLabel();
      //rootScope.$digest();
      expect(CitizenFormCtrl.formSubText).toBe("");
      expect(CitizenFormCtrl.stateName).toBe("");
      expect(CitizenFormCtrl.label.formTitle).toBe("Please Identify Yourself");
      expect(CitizenFormCtrl.label.formSubtext).toBe(CitizenFormCtrl.formSubText);
      expect(CitizenFormCtrl.label.securityCheck).toBe("Security Check");
      expect(CitizenFormCtrl.label.signIn).toBe("Sign In");
      expect(CitizenFormCtrl.label.eConsent).toBe("By checking this box, I consent to have my 1095-B form delivered by mail or electronically through this portal and agree to the Terms of Use and Privacy Statement associated with this site.");

      expect(CitizenFormCtrl.ariaLabel.titleAction).toBe("You are currently viewing citizen portal for the state of " + CitizenFormCtrl.stateName +
       "Please enter the following mandatory details Last Name, SSN or TIN number, date of birth, " +
       "city, state and zipcode to identify yourself. " + CitizenFormCtrl.formSubText + "Type your first name. " + CitizenFormCtrl.optional);
      expect(CitizenFormCtrl.ariaLabel.errorMessage).toBe("Error has occurred");
      expect(CitizenFormCtrl.ariaLabel.lastName).toBe("Type your last name." + CitizenFormCtrl.required);
      expect(CitizenFormCtrl.ariaLabel.ssnTin).toBe("You have currently selected Social Security Number " + CitizenFormCtrl.required);
      expect(CitizenFormCtrl.ariaLabel.address).toBe("Type your street address." + CitizenFormCtrl.optional);
      expect(CitizenFormCtrl.ariaLabel.city).toBe("Type your city." + CitizenFormCtrl.required);
      expect(CitizenFormCtrl.ariaLabel.state).toBe("Type the first letter of your state or arrow up and arrow down keys to select your state or Select your state from the dropdown." + CitizenFormCtrl.required);
      expect(CitizenFormCtrl.ariaLabel.zipcode).toBe("Type your 5 digit zipcode." + CitizenFormCtrl.required);
      expect(CitizenFormCtrl.ariaLabel.signin).toBe("Press Enter or click on Sign In button to authenticate yourself and get your ten 95 B form");
    });

    var test6 = it('Check for captchaResponse', function () {
      console.log(test6.description);
      CitizenFormCtrl.captchaResponse();
      //rootScope.$digest();
      expect(CitizenFormCtrl.captchaFlag).toBeTruthy();
    });

  });

});
