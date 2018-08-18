/**
 * Created by pchavan on 4/26/2016.
 */

'use strict';

describe('Controller: operationsCtrl', function () {
  // load the controller's module
  beforeEach(module('aca1095BUiAppCW'));

  var operationsCtrl,
    scope, rootScope, mockOperationsPortal, mockConfirmationDialog;

  beforeEach(function(){
    module(function($provide){
      $provide.value('sampleOperationsPortal', {
        showDropdown : false,
        unsavedChanges : false
      });
      $provide.value('sampleConfirmationDialog', {
        openModal : function(modalAttribute, yesFunction, noFunction, closeFunction){

        },
        modalBx : {}
       /* modalBx.close : function(){

        }*/
      });
    });
  });
  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope, _sampleOperationsPortal_, _sampleConfirmationDialog_) {
    scope = $rootScope.$new();
    rootScope = $rootScope;
    mockOperationsPortal = _sampleOperationsPortal_;
    mockConfirmationDialog = _sampleConfirmationDialog_;
    operationsCtrl = $controller('operationsCtrl', {
      $scope: scope,
      // place here mocked dependencies
      operationsPortal : mockOperationsPortal,
      confirmationDialog : mockConfirmationDialog
    });
  }));

  describe('Check controller', function() {
    it('Check initializations', function () {
      expect(operationsCtrl.inactivityTime).toBe(900000);
      expect(operationsCtrl.applicationLogout).toBeDefined();
      expect(operationsCtrl.timeOutCancel).toBeDefined();
    });
  });

  describe('Check functionality', function() {
    it('Check applicationLogoutConfirm, closeModalBox', function () {
      spyOn(operationsCtrl, 'applicationLogout');
      spyOn(mockConfirmationDialog, 'openModal');
      //spyOn(mockConfirmationDialog, 'modalBx.close');
      var mockModalAttribute = {
        windowClass: 'center-modal',
        animation: false,
        templateUrl: 'views/templates/cautionDialog.html',
        backdrop: 'static',
        keyboard: false,
        label : {
          title: 'Are you sure ?',
          noAction: 'Cancel',
          yesAction: 'OK',
          loading: false,
          description: 'Are you sure you want to navigate away from this page? If you continue, your unsaved changes will be lost. Press OK to exit or cancel to stay on the current page. '
        }
      };
      mockOperationsPortal.unsavedChanges = false;
      operationsCtrl.applicationLogoutConfirm();
      expect(operationsCtrl.applicationLogout).toHaveBeenCalled();

      mockOperationsPortal.unsavedChanges = true;
      operationsCtrl.applicationLogoutConfirm();
      expect(mockConfirmationDialog.openModal).toHaveBeenCalledWith(mockModalAttribute, operationsCtrl.applicationLogout,operationsCtrl.closeModalBox, operationsCtrl.closeModalBox);
    });
  });

});
