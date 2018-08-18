/**
 * Created by pchavan on 4/14/2016.
 */
'use strict';

describe('directive: aca-date-parser', function() {
  var element, scope;

  // load the directive's module
  beforeEach(module('aca1095BUiAppCW'));

  beforeEach(inject(function($rootScope, $compile) {
    scope = $rootScope.$new();

    element = '<input aca-date-parser date-format="\'MM/dd/YYYY HH:MM:SS AM/PM\'" invalid-class="\'invalid-input\'" placeholder="mm/dd/yyyy" data-ng-model="dateField" data-ng-change="activateAction()"/>';
      /*'<svg-circle size="{{size}}" stroke="black" fill="blue"></svg-circle>';*/

    scope.dateField = 1459872743960;   //"04/05/2016"

    element = $compile(element)(scope);
    scope.$digest();
  }));

  describe('Check attributes', function(){
    it("check isolated scope", function() {
      var isolated = element.isolateScope();
      expect(isolated.invalidClass).toBeDefined();
      expect(isolated.dateFormat).toBeDefined();
      isolated.dateFormat ="MM/dd/YYYY";
      expect(isolated.testPattern).toBeDefined();
      expect(element[0].value).toBe("04/05/2016 09:42:23 PM");
    });

    it("check dateFormatter", function() {
      var mockData = 1463563860000; // 05/18/2016 05:31:00 AM
      var isolated = element.isolateScope();
      var result = isolated.dateFormatter(mockData);
      expect(result).toBe("05/18/2016 03:01:00 PM");
    });

    it("check dateParser", function() {
      var isolated = element.isolateScope();
      var mockData = ["01/01/201" , "abc", "01/01/201666", "01/01/2016ab", "01/01/2016", "01/01/2016 00:00:00 AM", "01/01/2016 12:00:00 AM"];
      var result = "";
      result = isolated.dateParser(mockData[0]);
      expect(result).toBeNull();
      expect(element.hasClass(isolated.invalidClass)).toBeTruthy();
      result = isolated.dateParser(mockData[1]);
      expect(result).toBeNull();
      expect(element.hasClass(isolated.invalidClass)).toBeTruthy();
      result = isolated.dateParser(mockData[2]);
      expect(result).toBeNull();
      expect(element.hasClass(isolated.invalidClass)).toBeTruthy();
      result = isolated.dateParser(mockData[3]);
      expect(result).toBeNull();
      expect(element.hasClass(isolated.invalidClass)).toBeTruthy();
      result = isolated.dateParser(mockData[4]);
      expect(result).toBeNull();
      expect(element.hasClass(isolated.invalidClass)).toBeTruthy();
      result = isolated.dateParser(mockData[5]);
      expect(result).toBeNull();
      expect(element.hasClass(isolated.invalidClass)).toBeTruthy();
      result = isolated.dateParser(mockData[6]);
      expect(result).toBe(1451586600000);
      expect(element.hasClass(isolated.invalidClass)).toBeFalsy();
    });
  });
});
