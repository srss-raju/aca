/**
 * Created by sdalavi on 9/25/2015.
 */
'use strict';

(function () {
  function CitizenStateCtrl($scope, $element , $state, CitizenService, CitizenSession) {
    var vm = this;
    CitizenSession.setStateCode("");
    CitizenSession.setStateYear("");
    CitizenSession.setViewPage("");
    CitizenSession.setStaticState("");
    CitizenSession.userLogged = false;

    vm.label = {
      "stateTitle" : "Type or choose your state",
      "yearTitle" : "Type or choose tax year..."
    };

    vm.ariaLabel = {
      "titleAction" : "Start the process to obtain your ten 95 B form here. This website has been designed to operate with Google Chrome version 46 and above, Mozilla Firefox version 41 and above and Internet Explorer version 11 and above. Press Tab to move to the next step.",
      "yearTitle" : "Type the tax year now",
      "arrowIcon": "Press Enter or Click on the Arrow icon to proceed to next step",
      "enterButton": "Press Enter or Click on Enter button to access citizen portal for the state of",
      "goBack": "Press Enter or Click on go Back button to reselect the state and year"
    };

    vm.userInput = {"selectedEntry": null, "selectedYearEntry": null};
    vm.states = [];
    vm.stateLoad = false;

    vm.getStates = function(){

      CitizenService.getSelectedState()
        .then(function(data){
          vm.states = data;
          vm.stateLoad = true;
          $('.form-title').focus();
        },
        function(){
          /*Error: Setting to default value");*/
          vm.states= [
            {"stateName" : "Indiana", "stateCode" : "IN", "stateInfo" : [{"year": 2015, "staticStatus": "dynamic"}, {"year": 2016, "staticStatus": "static"}] },
            {"stateName" : "Arkansas", "stateCode" : "AR", "stateInfo" : [{"year": 2015, "staticStatus": "dynamic"}, {"year": 2016, "staticStatus": "static"}] },
            {"stateName" : "Oregon", "stateCode" : "OR", "stateInfo" : [{"year": 2015, "staticStatus": "dynamic"}, {"year": 2016, "staticStatus": "static"}] }
          ];
          vm.stateLoad = true;
          $('.form-title').focus();
        });


      vm.stateLoad = true;

    };

    vm.goToLogin = function() {

      if (vm.userInput.selectedYearEntry.staticStatus.toLowerCase() === 'dynamic') {
        CitizenSession.setStateCode(vm.userInput.selectedEntry.stateCode);
        CitizenSession.setStateYear(vm.userInput.selectedYearEntry.year);
        CitizenSession.setViewPage('dynamic');
        $state.go('citizen.citizen-form', { selectedState : vm.userInput.selectedEntry.stateCode, selectedYear: vm.userInput.selectedYearEntry.year });

      } else if (vm.userInput.selectedYearEntry.staticStatus.toLowerCase() === 'static') {
        CitizenSession.setStateCode('');
        CitizenSession.setStateYear('');
        CitizenSession.setViewPage('static');
        CitizenSession.setStaticState(vm.userInput.selectedEntry.stateCode);
        $state.go('citizen.citizen-static');
      }

    };

    vm.getStates();

  }
  angular.module('aca1095BUiAppCitizen').controller('CitizenStateCtrl', ['$scope', '$element', '$state', 'CitizenService', 'CitizenSession', CitizenStateCtrl]);
}());
