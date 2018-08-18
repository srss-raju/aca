/**
 * Created by pchavan on 5/4/2016.
 */
'use strict';

describe('directive: aca-edit-transmission-record', function () {
  var element, scope, acaEdit;
  var $compile, $rootScope, template;

  // load the directive's module
  //beforeEach(module('aca1095BUiAppCW', 'views/templates/acaEditTransmissionRecord.html'));

  beforeEach(module('aca1095BUiAppCW'));
  // load the templates
 /* beforeEach(module('views/templates/acaEditTransmissionRecord.html'));

  beforeEach(inject(function ($templateCache) {
    var templateUrl = 'views/templates/acaEditTransmissionRecord.html';
    var asynchronous = false;
    var req = new XMLHttpRequest();
    req.onload = function () {
      $templateCache.put(templateUrl, this.responseText);
    };
    req.open('get', '/base' + templateUrl, asynchronous);
    req.send();
  }));
*/

  beforeEach(inject(function($templateCache,_$compile_,_$rootScope_) {

    //assign the template to the expected url called by the directive and put it in the cache
    template = $templateCache.get('views/templates/acaEditTransmissionRecord.html');
    //$templateCache.put('/myApp/templates/acaEditTransmissionRecord.html',template);

    $compile = _$compile_;
    $rootScope = _$rootScope_;
  }));

  it("should display 3 text input fields, populated with ssn data", function() {
    //create the element angularjs will replace with the directive template
    var formElement = '<div aca-edit-transmission-record record-data="record"></div>';
    var element = $compile(formElement)($rootScope);
    //$rootScope.$digest();

    /*expect(element.find('input').length).toEqual(3);

    //use jquery to find the sub elements.
    expect($('input:first-child',element).val()).toEqual(ssn1);
    expect($('input:nth(1)',element).val()).toEqual(ssn2);
    expect($('input:nth(2)',element).val()).toEqual(ssn3);*/



  });


 /* beforeEach(inject(function ($rootScope, $injector, $compile) {
    scope = $rootScope.$new();
    element = '<div aca-edit-transmission-record record-data="record" record-index="$index" status-list="vm.transmissionStatusList" edit-mode-reference={{vm.editModeReference}} edit-set="vm.setEditMode()" edit-reset="vm.resetEditMode()" unsaved-mode-reference="vm.unsavedModeReference" save-record="vm.saveRecord()" save-record-fail="vm.saveRecordFailure()" reject-resend-success="vm.rejectResendSuccess()" reject-resend-failure="vm.rejectResendFailure()" corrected-resend-success="vm.correctedResendSuccess()" corrected-resend-failure="vm.correctedResendFailure()" />';
    acaEdit = $compile(element)(scope);
    scope.$digest();
  }));

  describe('Check attributes', function () {
    it("Check isolated scope", function () {
    /!*  var isolated = acaEdit.isolateScope();
      console.log(acaEdit, element);*!/
      expect(true).toBeTruthy();
      /!*  expect(isolated.recordData).toBeDefined();
       expect(isolated.recordIndex).toBeDefined();
       expect(isolated.statusList).toBeDefined();*!/
      //expect(isolated.testPattern).toBeDefined();
    });
  });*/
});
