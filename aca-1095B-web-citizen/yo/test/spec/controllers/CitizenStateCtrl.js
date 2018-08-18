
'use strict';

describe('Controller: CitizenStateCtrl', function () {

  var CitizenStateCtrl,
      scope,
      rootScope,
      element,
      state,
      CPService,
      CPSession;

  beforeEach(function(){
    module('aca1095BUiAppCitizen');
  });

  beforeEach(function(){
    module(function($provide){

      $provide.value('mockCPService', {

        getSelectedState: function() {
            var responseObject = [];
            responseObject = [{"stateName" : "Indiana", "stateCode" : "IN", "stateInfo" : [{"year": 2015, "staticStatus": "dynamic"}, {"year": 2016, "staticStatus": "static"}] },
                              {"stateName" : "Arkansas", "stateCode" : "AR", "stateInfo" : [{"year": 2015, "staticStatus": "dynamic"}, {"year": 2016, "staticStatus": "static"}] },
                              {"stateName" : "Oregon", "stateCode" : "OR", "stateInfo" : [{"year": 2015, "staticStatus": "dynamic"}, {"year": 2016, "staticStatus": "static"}] }
                             ];
            return {
              then: function(callback) {return callback(responseObject);}
            };
        }

      });

      $provide.value('mockCPSession', {
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

    });
  });

  beforeEach(inject(function($controller,$rootScope,mockCPService,mockCPSession){
        scope = $rootScope.$new;
        rootScope =$rootScope;
        element = jasmine.createSpy('$element');
        state = jasmine.createSpyObj('$state', ['go']);
        CPService = mockCPService;
        CPSession = mockCPSession;

        CitizenStateCtrl = $controller('CitizenStateCtrl', {
            $scope : scope,
            $element: element,
            $state : state,
            CitizenService : CPService,
            CitizenSession : CPSession
        });
  }));

  describe('Check Default initialization of flags and variables.', function () {
    var test1 = it('Check for labels and aria labels', function () {
      console.log(test1.description);
      expect(CitizenStateCtrl.label.stateTitle).toBe("Type or choose your state");
      expect(CitizenStateCtrl.label.yearTitle).toBe("Type or choose tax year...");

      expect(CitizenStateCtrl.ariaLabel.titleAction).toBe("Start the process to obtain your ten 95 B form here. This website has been designed to operate with Google Chrome version 46 and above, Mozilla Firefox version 41 and above and Internet Explorer version 11 and above. Press Tab to move to the next step.");
      expect(CitizenStateCtrl.ariaLabel.yearTitle).toBe("Type the tax year now");
      expect(CitizenStateCtrl.ariaLabel.arrowIcon).toBe("Press Enter or Click on the Arrow icon to proceed to next step");
      expect(CitizenStateCtrl.ariaLabel.enterButton).toBe("Press Enter or Click on Enter button to access citizen portal for the state of");
      expect(CitizenStateCtrl.ariaLabel.goBack).toBe("Press Enter or Click on go Back button to reselect the state and year");

    });

    var test2 = it('Check for variable initialization', function () {
      console.log(test2.description);

      expect(CitizenStateCtrl.userInput).toEqual({"selectedEntry": null, "selectedYearEntry": null});
      expect(CitizenStateCtrl.states).toEqual([
        {"stateName" : "Indiana", "stateCode" : "IN", "stateInfo" : [{"year": 2015, "staticStatus": "dynamic"}, {"year": 2016, "staticStatus": "static"}] },
        {"stateName" : "Arkansas", "stateCode" : "AR", "stateInfo" : [{"year": 2015, "staticStatus": "dynamic"}, {"year": 2016, "staticStatus": "static"}] },
        {"stateName" : "Oregon", "stateCode" : "OR", "stateInfo" : [{"year": 2015, "staticStatus": "dynamic"}, {"year": 2016, "staticStatus": "static"}] }
      ]);

      expect(CitizenStateCtrl.stateLoad).toBeTruthy();
      expect(CPSession.userLogged).toBeFalsy();
      expect(CPSession.selectedState).toBe("");
      expect(CPSession.selectedYear).toBe("");
      expect(CPSession.viewPage).toBe("");
      expect(CPSession.staticState).toBe("");

    });

  });

  describe('Check if getAvailableStates service is handling the exceptions and positive path.', function () {
    var test1 = it('Check for function definition getStates', function () {
      console.log(test1.description);
      expect(CitizenStateCtrl.getStates).toBeDefined();
    });

    var test2 = it('Check for function getStates', function () {
      console.log(test2.description);
      spyOn(CPService, 'getSelectedState').and.callThrough();
      CitizenStateCtrl.getStates();
      expect(CPService.getSelectedState).toHaveBeenCalled();
    });

  });

  describe('Check on selection of state and clicking enter whether user is taken to citizen form screen or static page.',function(){
      var test1 = it('Check for function goToLogin', function () {
        console.log(test1.description);
        CitizenStateCtrl.userInput = {"selectedEntry": {"stateName" : "Indiana", "stateCode" : "IN", "stateInfo" : [{"year": 2015, "staticStatus": "dynamic"}, {"year": 2016, "staticStatus": "static"}] }, "selectedYearEntry": {"year": 2015, "staticStatus": "dynamic"}};;
        spyOn(CPSession, 'setStateYear').and.callThrough();
        CitizenStateCtrl.goToLogin();

        expect(CPSession.setStateYear).toHaveBeenCalled();
        expect(CPSession.selectedState).toBe("IN");
        expect(state.go).toHaveBeenCalledWith('citizen.citizen-form', { selectedState : "IN", selectedYear: 2015 });
      });

      var test2 = it('Check for static page', function () {
        console.log(test2.description);
        CitizenStateCtrl.userInput = {"selectedEntry": {"stateName" : "Indiana", "stateCode" : "IN", "stateInfo" : [{"year": 2015, "staticStatus": "dynamic"}, {"year": 2016, "staticStatus": "static"}] }, "selectedYearEntry": {"year": 2016, "staticStatus": "static"}};;
        spyOn(CPSession, 'setStateYear').and.callThrough();
        CitizenStateCtrl.goToLogin();

        expect(CPSession.setStateYear).toHaveBeenCalled();
        expect(CPSession.selectedState).toBe("");
        expect(state.go).toHaveBeenCalledWith('citizen.citizen-static');
      });

  });



});
