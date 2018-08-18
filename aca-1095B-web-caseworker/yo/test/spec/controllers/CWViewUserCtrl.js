'use strict';

describe('Controller: CWViewUser', function () {

  var cwviewuser,
    scope,
    rootscope,
    state,
    confirmationDialog,
    CWService;

  beforeEach(function () {
    module('aca1095BUiAppCW');
  });

  beforeEach(function () {
    module(function ($provide) {

      var mockCWService = {};
      //Inject the services used
      $provide.value('mockCWService', {

        constants: function () {

          var const_values = {
            "readyToMail": "READY_TO_MAIL",
            "mailPending": "MAIL_PENDING",
            "mailComplete": "MAILED",
            "generatedPdf": "GENERATED",
            "regeneratePdf": "REGENERATE",
            "generationFailed": "GENERATION_FAILED",
            "addressInvalid": "ADDRESS_INVALID"
          };

          return const_values;
        },

        getFormIdforView: function () {
          var data = "ARDHSDSS_191020333";
          return {
            then: function (callback) {
              return callback(data);
            }
          };
        },

        cViewSet: function (view, formId) {
          mockCWService.viewSourceData = {
            formId: formId
          };
        },

        setCurrentData: function (currentForm, filerInfo) {
          mockCWService.currentFormView = {
            "currentForm": currentForm,
            "filerInfo": filerInfo
          };
        },

        getCurrentData: function () {
          return mockCWService.currentFormView;
        },

        viewDetail: function (formId) {
          var data = {};

          data = {
            "coveredFilers": [
              {
                "filerID": "ARDHSDSS_2000201240",
                "firstName": "Roger",
                "lastName": "Moore",
                "uidNumber": "xxx-xx-7682",
                "uidType": "SSN",
                "taxYear": "2015",
                "lastModifiedDate": "01/09/2016",
                "filerStatus": "R",
                "dob": "10/25/1966",
                "printStatus": "Resend",
                "status": "iNactive"
              },
              {
                "filerID": "ARDHSDSS_2000201240",
                "firstName": "Roger-1",
                "lastName": "Moore",
                "uidNumber": "xxx-xx-7682",
                "uidType": "SSN",
                "taxYear": "2015",
                "lastModifiedDate": "01/09/2016",
                "filerStatus": "R",
                "dob": "10/25/1966",
                "printStatus": "MAIL_PENDiNG"
              },
              {
                "filerID": "ARDHSDSS_2000201240",
                "firstName": "Roger-2",
                "lastName": "Moore",
                "uidNumber": "xxx-xx-7682",
                "uidType": "SSN",
                "taxYear": "2015",
                "lastModifiedDate": "01/09/2016",
                "filerStatus": "R",
                "dob": "10/25/1966",
                "printStatus": "maIL_COMPLETE"
              },
              {
                "filerID": "ARDHSDSS_2000201240",
                "firstName": "Roger-3",
                "lastName": "Moore",
                "uidNumber": "xxx-xx-7682",
                "uidType": "SSN",
                "taxYear": "2015",
                "lastModifiedDate": "01/09/2016",
                "filerStatus": "R",
                "dob": "10/25/1966",
                "printStatus": "MAIL_PENDING"
              },
              {
                "filerID": "ARDHSDSS_2000201240",
                "firstName": "Roger-4",
                "lastName": "Moore",
                "uidNumber": "xxx-xx-7682",
                "uidType": "SSN",
                "taxYear": "2015",
                "lastModifiedDate": "01/09/2016",
                "filerStatus": "R",
                "dob": "10/25/1966",
                "printStatus": "ready_to_mail"
              }
            ],
            "currentForm": {
              "formID": "ARDHSDSS_2000201240",
              "generatedBy": "ar_stateuser",
              "lastModifiedDate": "01/09/2016 07:04:53 PM",
              "addressLine1": "325EdenPlace325EdenPlace325EdenPlace325EdenPlace325EdenPlace",
              "addressLine2": "Apt # 3251 NEApt # 3251 NEApt # 3251 NEApt # 3251 NE",
              "city": "Crossett Crossett Crossett Crossett",
              "state": "AR",
              "zipcode": "71635",
              "lastMailRequestedDate": "01/10/2016 07:04:53 PM",
              "printDate": "01/11/2016 07:04:53 PM",
              "updatedDate": "01/09/2016 07:04:53 PM",
              "mailStatus": "MAIL_COMPLETE",
              "pdfStatus": "REGENERATE",
              "comments": "changed address line 2",
              "pdfAvailable": true,
              "formStatus": "GENERATED",
              "printStatus": ""
            },
            "historicForms": [
              {
                "formID": "ARDHSDSS_2000201240",
                "generatedBy": "System",
                "lastModifiedDate": "01/06/2016 11:59:43 AM",
                "addressLine1": "325EdenPlace325EdenPlace325EdenPlace325EdenPlace325EdenPlace",
                "addressLine2": "Apt # 3251 NEApt # 3251 NEApt # 3251 NEApt # 3251 NE",
                "city": "Crossett Crossett Crossett Crossett",
                "state": "AR",
                "zipcode": "71635",
                "comments": "",
                "auditSequenceNo": "183579",
                "pdfAvailable": false
              },
              {
                "formID": "ARDHSDSS_2000201240",
                "generatedBy": "System",
                "lastModifiedDate": "01/07/2016 01:02:20 PM",
                "addressLine1": "325EdenPlace325EdenPlace325EdenPlace325EdenPlace325EdenPlace",
                "addressLine2": "Apt # 3251 NEApt # 3251 NEApt # 3251 NEApt # 3251 NE",
                "city": "Crossett",
                "state": "AR",
                "zipcode": "71635",
                "comments": "",
                "auditSequenceNo": "203343",
                "pdfAvailable": false
              },
              {
                "formID": "ARDHSDSS_2000201240",
                "generatedBy": "System",
                "lastModifiedDate": "01/07/2016 01:02:20 PM",
                "addressLine1": "325EdenPlace325EdenPlace325EdenPlace325EdenPlace325EdenPlace",
                "addressLine2": "Apt # 3251",
                "city": "Crossett",
                "state": "AR",
                "zipcode": "71635",
                "comments": "",
                "auditSequenceNo": "203350",
                "pdfAvailable": false
              },
              {
                "formID": "ARDHSDSS_2000201240",
                "generatedBy": "ar_stateuser",
                "lastModifiedDate": "01/09/2016 04:42:55 PM",
                "addressLine1": "325EdenPlace325EdenPlace325EdenPlace325EdenPlace325EdenPlace",
                "addressLine2": "Apt # 3251",
                "city": "Crossett",
                "state": "AR",
                "zipcode": "71635",
                "comments": "",
                "auditSequenceNo": "226958",
                "pdfAvailable": true
              },
              {
                "formID": "ARDHSDSS_2000201240",
                "generatedBy": "ar_stateuser",
                "lastModifiedDate": "01/09/2016 04:42:55 PM",
                "addressLine1": "325EdenPlace325EdenPlace325EdenPlace325EdenPlace325EdenPlace",
                "addressLine2": "Apt # 3251",
                "city": "Crossett",
                "state": "AR",
                "zipcode": "71635",
                "comments": "",
                "auditSequenceNo": "226958",
                "pdfAvailable": true
              },
              {
                "formID": "ARDHSDSS_2000201240",
                "generatedBy": "ar_stateuser",
                "lastModifiedDate": "01/09/2016 04:42:55 PM",
                "addressLine1": "325EdenPlace325EdenPlace325EdenPlace325EdenPlace325EdenPlace",
                "addressLine2": "Apt # 3251",
                "city": "Crossett",
                "state": "AR",
                "zipcode": "71635",
                "comments": "",
                "auditSequenceNo": "226958",
                "pdfAvailable": true
              },
              {
                "formID": "ARDHSDSS_2000201240",
                "generatedBy": "ar_stateuser",
                "lastModifiedDate": "01/09/2016 04:42:55 PM",
                "addressLine1": "325EdenPlace325EdenPlace325EdenPlace325EdenPlace325EdenPlace",
                "addressLine2": "Apt # 3251",
                "city": "Crossett",
                "state": "AR",
                "zipcode": "71635",
                "comments": "",
                "auditSequenceNo": "226958",
                "pdfAvailable": true
              },
              {
                "formID": "ARDHSDSS_2000201240",
                "generatedBy": "ar_stateuser",
                "lastModifiedDate": "01/09/2016 04:42:55 PM",
                "addressLine1": "325EdenPlace325EdenPlace325EdenPlace325EdenPlace325EdenPlace",
                "addressLine2": "Apt # 3251112Apt # 3251112Apt # 3251112Apt # 3251112Apt # 3251112",
                "city": "Crossett",
                "state": "AR",
                "zipcode": "71635",
                "comments": "",
                "auditSequenceNo": "226958",
                "pdfAvailable": true
              }
            ]
          };

          return {
            success: function (callback) {
              return callback(data);
            }
          };
        }

      });

      $provide.value('mockCDService', {
        openModal: function (modalAttribute, yesFunction, noFunction, closeFunction) {
        }
      });

    });
  });

  beforeEach(inject(function ($controller, $rootScope, mockCWService, mockCDService) {
    scope = $rootScope.$new();
    rootscope = $rootScope;
    state = jasmine.createSpyObj('$state', ['go']);
    CWService = mockCWService;
    confirmationDialog = mockCDService;

    cwviewuser = $controller('CWViewUser', {
      $scope: scope,
      $state: state,
      CWService: CWService,
      confirmationDialog: confirmationDialog
    });

  }));

  describe('test the screen labels', function () {

    console.log('Testing CWViewUser');
    it('check the screen labels', function () {
      console.log('check the screen labels');

      expect(scope.label.titleAction).toEqual('View');
      expect(scope.label.titleDescription).toEqual('1095-B Form');

      expect(scope.label.name).toEqual('Name');
      expect(scope.label.dob).toEqual('DOB');
      expect(scope.label.ssn).toEqual('SSN');
      expect(scope.label.tin).toEqual('TIN');

      expect(scope.label.year).toEqual('Year');
      expect(scope.label.email).toEqual('Email');
      expect(scope.label.primary).toEqual('Primary');
      expect(scope.label.dependents).toEqual('Dependents');
      expect(scope.label.noSsnTin).toEqual('No SSN / TIN available');
      expect(scope.label.lastMailed).toEqual('Last Mailed');

      expect(scope.label.mailRequested).toEqual('Mail Requested');
      expect(scope.label.mailProcessing).toEqual('Mail Processed');
      expect(scope.label.lastModified).toEqual('Last Modified');
      expect(scope.label.status).toEqual('Status');
      expect(scope.label.formActions).toEqual('Form Actions');
      expect(scope.label.address).toEqual('Address');

      expect(scope.label.generatedBy).toEqual('Generated By');
      expect(scope.label.comments).toEqual('Comments');
      expect(scope.label.historicForms).toEqual('Historic Forms');
      expect(scope.label.genDate).toEqual('Generated Date');
      expect(scope.label.city).toEqual('City');
      expect(scope.label.state).toEqual('State');

      expect(scope.label.zipCode).toEqual('Zipcode');
      expect(scope.label.viewPdfBtn).toEqual('View PDF');
      expect(scope.label.noPreviewAvailable).toEqual('No Preview Available');
      expect(scope.label.sendMailBtn).toEqual('Send my 1095-B by mail');
      expect(scope.label.returned).toEqual('Returned:');
      expect(scope.label.returnedAddress).toEqual('Address Invalid');

    });
  });

  describe('check controller methods', function () {

    it('on Recipient name link navigate to Recipient Data screen', function () {
      console.log('on click of Recipient name link, navigate to Recipient Data screen');
      rootscope.$apply();
      spyOn(CWService, 'cViewSet');
      scope.goToSourceData();
      expect(CWService.cViewSet).toHaveBeenCalled();
      expect(state.go).toHaveBeenCalledWith('state.cw-view-source-data');
    });

    it('on file link check whether data is being set', function () {
      console.log('on click of file icon, check whether data is being set');
      rootscope.$apply();
      spyOn(CWService, 'setCurrentData');
      spyOn(CWService, 'getCurrentData');
      scope.viewDocuments();
      expect(CWService.setCurrentData).toHaveBeenCalled();
      expect(CWService.getCurrentData).toHaveBeenCalled();
    });

    it('on mail icon show confirmation dialog', function () {
      console.log('on click of mail icon, show confirmation popup');
      rootscope.$apply();
      spyOn(confirmationDialog, 'openModal');
      scope.sendByMailConfirm();
      expect(confirmationDialog.openModal).toHaveBeenCalled();
    });

  });
});
