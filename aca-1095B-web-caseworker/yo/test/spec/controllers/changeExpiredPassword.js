'use strict';

describe('Controller: changeExpiredPassword', function () {

  // load the controller's module
  beforeEach(module('aca1095BUiAppCW'));

  var changeExpiredPassword,
    scope, rootScope, element, $compile, mockLocation,
    mockCommonService, mockLog;

  var mockENV, mockUnicornServices;
  beforeEach(function(){
    module(function($provide){
      $provide.value('sampleUnicornService', {
        updateExpiredPassword: function(data) {
          var responseObject = {};
          if(data.emailId === 'error01@error.com'){
            responseObject = {"status": "500", "result": {"emailId": "mock_ar_user@ar.com"}, "errormsg": "Unknown error occurred. Please logout and try later."};
          }else if(data.emailId === 'invalid@invalid.com'){
            responseObject = {"status": "123", "result": {"emailId": "mock_ar_user@ar.com"}, "errormsg": "Unknown error occurred. Please logout and try later."};
          }else if(data.emailId === 'invalidcall@invalid.com'){
            responseObject = {};
          }else{
            responseObject = {"status": "200", "result": {"emailId": "mock_ar_user@ar.com"}, "errormsg": null};
          }
          return {
            then: function(callback) {return callback(responseObject);}
          };
        }
      });
      $provide.value('sampleLocation', {

      });
    });
  });



  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope, $injector, _$compile_, $location, _sampleUnicornService_) {
    scope = $rootScope.$new();
    rootScope = $rootScope;
    element = jasmine.createSpy('$element');
    mockENV = $injector.get('ENV');
    mockCommonService = $injector.get('CommonService');
    mockLog = $injector.get('$log');
    $compile = _$compile_;
    mockLocation = $location;
    mockUnicornServices = _sampleUnicornService_;
    changeExpiredPassword = $controller('changeExpiredPassword', {
      $scope: scope,
      $element : element,
      // place here mocked dependencies
      $log : mockLog,
      CommonService : mockCommonService,
      ENV : mockENV,
      unicornServices : mockUnicornServices,
      $location : mockLocation
    });
  }));


  describe('Check for all the label, aria labels, variables and functions defined', function(){

    var test1 = it('Check Labels defined', function(){
      console.log(test1.description);
      expect(changeExpiredPassword.label).toBeDefined();
      expect(changeExpiredPassword.label).toEqual(jasmine.any(Object));
      expect(changeExpiredPassword.label.titleAction).toBe("Change");
      expect(changeExpiredPassword.label.titleDescription).toBe("Expired Password");
      expect(changeExpiredPassword.label.newPassword).toBe("New Password");
      expect(changeExpiredPassword.label.confirmPassword).toBe("Confirm Password");
      expect(changeExpiredPassword.label.saveButton).toBe("Save");
      expect(changeExpiredPassword.label.cancelButton).toBe("Cancel");
      expect(changeExpiredPassword.label.passwordPolicy).toBe("At least 8 characters and containing at least one uppercase letter,one lowercase letter,one number and one special character.");
    });

    var test2 = it('Check Aria Labels defined', function(){
      console.log(test2.description);
      expect(changeExpiredPassword.ariaLabel).toBeDefined();
      expect(changeExpiredPassword.ariaLabel).toEqual(jasmine.any(Object));
      expect(changeExpiredPassword.ariaLabel.title).toBe("Please change the password since your password has expired. ");
      expect(changeExpiredPassword.ariaLabel.newPassword).toBe("Enter the new password with at least 8 characters and containing at least one uppercase letter,one lowercase letter,one number and one special character.");
      expect(changeExpiredPassword.ariaLabel.confirmPassword).toBe("Enter the confirmed  password.");
    });

    var test3 = it('Check Variables defined', function(){
      console.log(test3.description);
      expect(changeExpiredPassword.userData).toBeDefined();
      expect(changeExpiredPassword.userData).toEqual(jasmine.any(Object));
      expect(changeExpiredPassword.userData.newPassword).toBeNull();
      expect(changeExpiredPassword.userData.confirmPassword).toBeNull();
      expect(changeExpiredPassword.userData.emailId).toBeDefined();
      expect(changeExpiredPassword.successData).toEqual(jasmine.any(Object));
      expect(changeExpiredPassword.successData).toBeDefined();
      expect(changeExpiredPassword.successMessage).toBeDefined();
      expect(changeExpiredPassword.errorMessage).toBeDefined();
      expect(changeExpiredPassword.showSuccessMessage).toBeFalsy();
      expect(changeExpiredPassword.showErrorMessage).toBeFalsy();
      expect(changeExpiredPassword.passwordsNotMatch).toBeFalsy();
      expect(changeExpiredPassword.passwordNotCompliant).toBeFalsy();
      expect(changeExpiredPassword.emptyFields).toBeTruthy();
     /* expect(changeExpiredPassword.disableSave).toBeTruthy();*/
    });

    var test4 = it('Check functions defined', function(){
      console.log(test4.description);
      expect(changeExpiredPassword.validate).toBeDefined();
      expect(changeExpiredPassword.validate).toEqual(jasmine.any(Object));
      expect(changeExpiredPassword.validate.validateConfirmPassword).toBeDefined();
      expect(changeExpiredPassword.validate.validatePassword).toBeDefined();
      expect(changeExpiredPassword.validate.checkOnUpdate).toBeDefined();
      expect(changeExpiredPassword.enableSavePassword).toBeDefined();
      expect(changeExpiredPassword.cancel).toBeDefined();
    });
  });

  describe('Check for validateConfirmPassword', function () {
    var test1 = it('Check for function definition for validateConfirmPassword', function () {
      console.log(test1.description);
      expect(changeExpiredPassword.validate.validateConfirmPassword).toBeDefined();
    });

    var test2 = it('Check for test scenarios for validateConfirmPassword', function () {
      console.log(test2.description);
      var mockNewPassword = ["test1@password", "test1@password"];
      var mockConfirmPassword = ["test1@password", "test1@passwordd"];
      console.log("Positive Path : ");
      expect(changeExpiredPassword.validate.validateConfirmPassword(mockNewPassword[0], mockConfirmPassword[0]))
        .toBeTruthy();
      console.log("password length : ");
      expect(changeExpiredPassword.validate.validateConfirmPassword(mockNewPassword[1], mockConfirmPassword[1]))
        .toBeFalsy();
    });
  });

  describe('Check for validatePassword', function () {
    var test1 = it('Check for function definition for validatePassword', function () {
      console.log(test1.description);
      expect(changeExpiredPassword.validate.validatePassword).toBeDefined();
    });

    var test2 = it('Check for test scenarios for validatePassword', function () {
      console.log(test2.description);
      var mockNewPassword = [
        "Test1@password",
        "L3ss@8",
        "P@sswordmorethan15s",
        "Test1password",
        "Test@password",
        "test1@password",
        "TEST1@PASSWORD",
        "Testpassword",
        "test1password",
        "TEST1PASSWORD",
        "test@password",
        "TEST@PASSWORD",
        "@@@@@12345",
        "testpassword",
        "TESTPASSWORD",
        "@@#@#@#@@",
        "12345678"
      ];
      console.log("Positive Path");
      expect(changeExpiredPassword.validate.validatePassword(mockNewPassword[0]))
        .toBeTruthy();

      console.log('password length : less than 8');
      expect(changeExpiredPassword.validate.validatePassword(mockNewPassword[1]))
        .toBeFalsy();

      console.log('password length : more than 15');
      expect(changeExpiredPassword.validate.validatePassword(mockNewPassword[2]))
        .toBeFalsy();

      console.log('password missing atleast one special character');
      expect(changeExpiredPassword.validate.validatePassword(mockNewPassword[3]))
        .toBeFalsy();

      console.log('password missing atleast one number');
      expect(changeExpiredPassword.validate.validatePassword(mockNewPassword[4]))
        .toBeFalsy();

      console.log("password missing atleast one uppercase letter");
      expect(changeExpiredPassword.validate.validatePassword(mockNewPassword[5]))
        .toBeFalsy();

      console.log("password missing atleast one lowercase letter");
      expect(changeExpiredPassword.validate.validatePassword(mockNewPassword[6]))
        .toBeFalsy();


      console.log("password missing atleast one special character and one uppercase letter");
      expect(changeExpiredPassword.validate.validatePassword(mockNewPassword[8]))
        .toBeFalsy();

      console.log("password missing atleast one special character and one lowercase letter");
      expect(changeExpiredPassword.validate.validatePassword(mockNewPassword[9]))
        .toBeFalsy();

      console.log("password missing atleast one number and one uppercase letter");
      expect(changeExpiredPassword.validate.validatePassword(mockNewPassword[10]))
        .toBeFalsy();

      console.log("password missing atleast one number and one lowercase letter");
      expect(changeExpiredPassword.validate.validatePassword(mockNewPassword[11]))
        .toBeFalsy();

      console.log("password missing atleast one uppercase letter and one lowercase letter");
      expect(changeExpiredPassword.validate.validatePassword(mockNewPassword[12]))
        .toBeFalsy();

      console.log("password missing atleast one uppercase letter, atleast one special character and atleast one number");
      expect(changeExpiredPassword.validate.validatePassword(mockNewPassword[13]))
        .toBeFalsy();

      console.log("password missing atleast one lowercase letter, atleast one special character and atleast one number");
      expect(changeExpiredPassword.validate.validatePassword(mockNewPassword[14]))
        .toBeFalsy();

      console.log("password missing atleast one lowercase letter, atleast one uppercase letter and atleast one number");
      expect(changeExpiredPassword.validate.validatePassword(mockNewPassword[15]))
        .toBeFalsy();

      console.log("password missing atleast one lowercase letter, atleast one uppercase letter and atleast one special character");
      expect(changeExpiredPassword.validate.validatePassword(mockNewPassword[16]))
        .toBeFalsy();

    });

  });

  describe('Check for checkOnUpdate', function (){
    var test1 = it('Check for function definition for checkOnUpdate', function () {
      console.log(test1.description);
      expect(changeExpiredPassword.validate.checkOnUpdate).toBeDefined();
    });

    var mockNewPassword = ["Test1@password", "Test1@password", "testPassword1"];
    var mockConfirmPassword = ["Test1@password", "Test1@passwordd", "testPassword1"];

    var test2 = it('Check for test scenarios for checkOnUpdate : Password Matching and Compliance', function () {
      console.log(test2.description);
      var userData = {
        newPassword : mockNewPassword[0],   confirmPassword : mockConfirmPassword[0]
      };
      changeExpiredPassword.validate.checkOnUpdate(userData);
      rootScope.$digest();
      expect(changeExpiredPassword.passwordNotCompliant).toBeFalsy();
      expect(changeExpiredPassword.passwordsNotMatch).toBeFalsy();
    });

    var test3 = it('Check for test scenarios for checkOnUpdate : Password Mismatching', function () {
      console.log(test3.description);
      var userData = {
        newPassword : mockNewPassword[1],   confirmPassword : mockConfirmPassword[1]
      };
      changeExpiredPassword.validate.checkOnUpdate(userData);
      rootScope.$digest();
      expect(changeExpiredPassword.passwordNotCompliant).toBeFalsy();
      expect(changeExpiredPassword.passwordsNotMatch).toBeTruthy();
    });

    var test4 = it('Check for test scenarios for enableSavePassword : Password Compliance not met', function () {
      console.log(test4.description);
      var userData = {
        newPassword : mockNewPassword[2],   confirmPassword : mockConfirmPassword[2]
      };
      changeExpiredPassword.validate.checkOnUpdate(userData);
      rootScope.$digest();
      expect(changeExpiredPassword.passwordNotCompliant).toBeTruthy();
      expect(changeExpiredPassword.passwordsNotMatch).toBeFalsy();
    });

  });

  describe('Check for enableSavePassword', function (){
    var test1 = it('Check for function definition for enableSavePassword', function () {
      console.log(test1.description);
      expect(changeExpiredPassword.enableSavePassword).toBeDefined();
    });

    var mockNewPassword = ["test1@password", "", null];
    var mockConfirmPassword = ["test1@password", "", null];

    var test2 = it('Check if emptyfield is set : password exist combinations',function(){
      console.log(test2.description);
      var userData = {"newPassword" : mockNewPassword[0], "confirmPassword" : mockConfirmPassword[0]};
      changeExpiredPassword.enableSavePassword(userData);
      rootScope.$digest();
      expect(changeExpiredPassword.emptyFields).toBeFalsy();

      userData = {"newPassword" : mockNewPassword[0], "confirmPassword" : mockConfirmPassword[1]};
      changeExpiredPassword.enableSavePassword(userData);
      rootScope.$digest();
      expect(changeExpiredPassword.emptyFields).toBeTruthy();

      userData = {"newPassword" : mockNewPassword[0], "confirmPassword" : mockConfirmPassword[2]};
      changeExpiredPassword.enableSavePassword(userData);
      rootScope.$digest();
      expect(changeExpiredPassword.emptyFields).toBeTruthy();

      userData = {"newPassword" : mockNewPassword[1], "confirmPassword" : mockConfirmPassword[0]};
      changeExpiredPassword.enableSavePassword(userData);
      rootScope.$digest();
      expect(changeExpiredPassword.emptyFields).toBeTruthy();

      userData = {"newPassword" : mockNewPassword[1], "confirmPassword" : mockConfirmPassword[1]};
      changeExpiredPassword.enableSavePassword(userData);
      rootScope.$digest();
      expect(changeExpiredPassword.emptyFields).toBeTruthy();

      userData = {"newPassword" : mockNewPassword[1], "confirmPassword" : mockConfirmPassword[2]};
      changeExpiredPassword.enableSavePassword(userData);
      rootScope.$digest();
      expect(changeExpiredPassword.emptyFields).toBeTruthy();

      userData = {"newPassword" : mockNewPassword[2], "confirmPassword" : mockConfirmPassword[0]};
      changeExpiredPassword.enableSavePassword(userData);
      rootScope.$digest();
      expect(changeExpiredPassword.emptyFields).toBeTruthy();

      userData = {"newPassword" : mockNewPassword[2], "confirmPassword" : mockConfirmPassword[1]};
      changeExpiredPassword.enableSavePassword(userData);
      rootScope.$digest();
      expect(changeExpiredPassword.emptyFields).toBeTruthy();

      userData = {"newPassword" : mockNewPassword[2], "confirmPassword" : mockConfirmPassword[2]};
      changeExpiredPassword.enableSavePassword(userData);
      rootScope.$digest();
      expect(changeExpiredPassword.emptyFields).toBeTruthy();
    });

  });

  describe('Check for cancel', function (){
    var test1 = it('Check for function definition for cancel', function () {
      console.log(test1.description);
      expect(changeExpiredPassword.cancel).toBeDefined();
    });

    /*var test2 = it('Check if cancel redirects to login screen',function(){
      console.log(test2.description);
      /!*spyOn(mockLocation, 'path');*!/
      changeExpiredPassword.cancel();
      rootScope.$digest();
      /!*expect(mockLocation.path).toHaveBeenCalledWith(mockENV.logout);*!/
    });*/
  });

  describe('Check for updateExpiredPassword', function (){

    var test1 = it('Check for function definition for updateExpiredPassword', function () {
      console.log(test1.description);
      expect(changeExpiredPassword.updateExpiredPassword).toBeDefined();
    });

    var test2 = it('Check if success scenario is handled : valid status',function(){
      console.log(test2.description);
      var userData = {
        newPassword : "Test1@password",
        confirmPassword : "Test1@password",
        emailId : "success@success.com"
      };
      spyOn(mockUnicornServices, 'updateExpiredPassword').and.callThrough();
     /* spyOn(mockLocation, 'path');*/
      changeExpiredPassword.updateExpiredPassword(userData);
      rootScope.$digest();
      expect(mockUnicornServices.updateExpiredPassword).toHaveBeenCalled();
      expect(changeExpiredPassword.successData.status).toBe("200");
      expect(changeExpiredPassword.showSuccessMessage).toBeTruthy();
      expect(changeExpiredPassword.showErrorMessage).toBeFalsy();
      expect(changeExpiredPassword.successMessage).toBe("Password updated successfully.");
      /*expect(mockLocation.path).toHaveBeenCalledWith(mockENV.logout);*/
    });

    var test3 = it('Check if error scenario is handled : valid status',function(){
      console.log(test3.description);
      var userData = {
        newPassword : "Test1@password",
        confirmPassword : "Test1@password",
        emailId : "error01@error.com"
      };

      spyOn(mockUnicornServices, 'updateExpiredPassword').and.callThrough();
      changeExpiredPassword.updateExpiredPassword(userData);
      rootScope.$digest();
      expect(mockUnicornServices.updateExpiredPassword).toHaveBeenCalled();
      expect(changeExpiredPassword.successData.status).toBe("500");
      expect(changeExpiredPassword.showSuccessMessage).toBeFalsy();
      expect(changeExpiredPassword.showErrorMessage).toBeTruthy();
      expect(changeExpiredPassword.errorMessage).toBe("Unknown error occurred. Please logout and try later.");
    });

    var test4 = it('Check if error scenario is handled : invalid status',function(){
      console.log(test4.description);
      var userData = {
        newPassword : "Test1@password",
        confirmPassword : "Test1@password",
        emailId : "invalid@invalid.com"
      };
      spyOn(mockUnicornServices, 'updateExpiredPassword').and.callThrough();
      changeExpiredPassword.updateExpiredPassword(userData);
      rootScope.$digest();
      expect(mockUnicornServices.updateExpiredPassword).toHaveBeenCalled();
      expect(changeExpiredPassword.successData.status).not.toBe("500");
      expect(changeExpiredPassword.successData.status).not.toBe("200");
      expect(changeExpiredPassword.showSuccessMessage).toBeFalsy();
      expect(changeExpiredPassword.showErrorMessage).toBeTruthy();
      expect(changeExpiredPassword.errorMessage).toBe("Unknown error occurred. Please logout and try later.");
    });

    var test5 = it('Check if error scenario is handled : unknown condition',function(){
      console.log(test5.description);
      var userData = {
        newPassword : "Test1@password",
        confirmPassword : "Test1@password",
        emailId : "invalidcall@invalid.com"
      };
      spyOn(mockUnicornServices, 'updateExpiredPassword').and.callThrough();
      changeExpiredPassword.updateExpiredPassword(userData);
      rootScope.$digest();
      expect(mockUnicornServices.updateExpiredPassword).toHaveBeenCalled();
      expect(changeExpiredPassword.successData).toEqual({});
      expect(changeExpiredPassword.showSuccessMessage).toBeFalsy();
      expect(changeExpiredPassword.showErrorMessage).toBeTruthy();
      expect(changeExpiredPassword.errorMessage).toBe("Unknown error occurred. Please logout and try later.");
    });

    var test6 = it('Check if error scenario is handled : Password Compliance',function(){
      console.log(test6.description);
      var userData = {
        newPassword : "test1@password",
        confirmPassword : "test1@password",
        emailId : "invalidcall@invalid.com"
      };
      spyOn(mockUnicornServices, 'updateExpiredPassword').and.callThrough();
      changeExpiredPassword.updateExpiredPassword(userData);
      rootScope.$digest();
      expect(mockUnicornServices.updateExpiredPassword).not.toHaveBeenCalled();
      expect(changeExpiredPassword.successData).toEqual({});
      expect(changeExpiredPassword.showSuccessMessage).toBeFalsy();
      expect(changeExpiredPassword.showErrorMessage).toBeTruthy();
      expect(changeExpiredPassword.passwordNotCompliant).toBeTruthy();
      expect(changeExpiredPassword.errorMessage).toBe("Please enter valid password in accordance to the password policy.");
     });

    var test7 = it('Check if error scenario is handled : Password Mismatch',function(){
      console.log(test7.description);
      var userData = {
        newPassword : "Test1@password",
        confirmPassword : "test1@password",
        emailId : "invalidcall@invalid.com"
      };
      spyOn(mockUnicornServices, 'updateExpiredPassword').and.callThrough();
      changeExpiredPassword.updateExpiredPassword(userData);
      rootScope.$digest();
      expect(mockUnicornServices.updateExpiredPassword).not.toHaveBeenCalled();
      expect(changeExpiredPassword.successData).toEqual({});
      expect(changeExpiredPassword.showSuccessMessage).toBeFalsy();
      expect(changeExpiredPassword.showErrorMessage).toBeTruthy();
      expect(changeExpiredPassword.passwordNotCompliant).toBeFalsy();
      expect(changeExpiredPassword.passwordsNotMatch).toBeTruthy();
      expect(changeExpiredPassword.errorMessage).toBe("Please enter same new and confirm password.");
    });
  });

});
