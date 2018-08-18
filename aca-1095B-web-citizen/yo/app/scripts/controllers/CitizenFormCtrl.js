/**
 * Created by sdalavi on 9/25/2015.
 */
'use strict';

(function () {
  angular.module('aca1095BUiAppCitizen').controller('CitizenFormCtrl', ['$scope', '$element' , '$log',  '$interval', '$state', '$stateParams','CitizenService', 'CitizenSession',
    function($scope, $element , $log, $interval, $state, $stateParams, CitizenService, CitizenSession) {
      var state = $stateParams.selectedState;
      var year = $stateParams.selectedYear;

      var vm = this;
      vm.goToStateSelect = false;
      vm.screenLoad = vm.screenLoad || false;
      vm.states = {};

      /*** Aria Labels Initialize ***/
      vm.optional = "This is an optional field.";
      vm.required = "This is required to successfully identify you.";
      vm.formSubText = "";
      vm.stateName="";
      vm.statelabel = "Type the first letter of your state or arrow up and arrow down keys to select your state or Select your state from the dropdown." + vm.required;
      vm.checkBoxlabel = "Press Enter to check this box. By checking this box you are consenting to have your I R S Form 1095-B delivered by mail or electronically through this portal.";
      vm.user = {
        userFname: null,
        userLname: null,
        uidNumber: null,
        uidType: 'SSN',
        dob: null,
        streetAddress1: null,
        city: null,
        state: null,
        zipcode: null,
        gRecaptchaResponse : null
      };
      vm.ariaLabel = {
        "titleAction" : null,
        "errorMessage" : "Error has occurred",
        "lastName" : null,
        "ssnTin" : null,
        "address" : null,
        "city" : null,
        "state" : null,
        "zipcode" : null,
        "signin" :null
      };

      vm.getLabel = function(){
        switch(vm.selectedState){
          case 'AR':
          {
            vm.formSubText = "Enter data as reported to Department of Human Services. ";
            vm.stateName = "Arkansas";
          }
            break;
          case 'IN':
          {
            vm.formSubText = "";
            vm.stateName = "Indiana";
          }
            break;
          default :
          {
            vm.formSubText = "";
            vm.stateName = "";
          }
        }
        /** Labels **/
        vm.label = {
          "formTitle" : "Please Identify Yourself",
          "formSubtext" : vm.formSubText,
          "securityCheck" : "Security Check",
          "signIn" : "Sign In",
          "eConsent": "By checking this box, I consent to have my 1095-B form delivered by mail or electronically through this portal and agree to the Terms of Use and Privacy Statement associated with this site."
        };
        /*** Aria Labels ***/
        vm.ariaLabel = {
          "titleAction" : "You are currently viewing citizen portal for the state of " + vm.stateName +
          "Please enter the following mandatory details Last Name, SSN or TIN number, date of birth, " +
          "city, state and zipcode to identify yourself. " + vm.formSubText + "Type your first name. " + vm.optional,
          "errorMessage" : "Error has occurred",
          "lastName" : "Type your last name." + vm.required,
          "ssnTin" : "You have currently selected Social Security Number " + vm.required,
          "address" : "Type your street address." + vm.optional,
          "city" : "Type your city." + vm.required,
          "state" : vm.statelabel,
          "zipcode" : "Type your 5 digit zipcode." + vm.required,
          "signin" : "Press Enter or click on Sign In button to authenticate yourself and get your ten 95 B form"
        };
        vm.errorMessage = "Sorry, no record has been found. Please try again or call 1-xxx-yyy-zzzz to speak with a state representative.";
      };

      vm.initController = function(){
        vm.captchaFlag = false;
        vm.userNotFound = false;    /** Set to false by default : error message not displayed**/
        vm.uidTypePlaceHolder = "Social Security Number";
        vm.toggleOn = true;
        vm.dataLabel = "SSN selected. Push Enter to use a TIN #";
        vm.eConsent = false;
        /*** Check if all required fields are entered ***/
        vm.disabledSignIn = true;
        vm.user = {
          userFname: null,
          userLname: null,
          uidNumber: null,
          uidType: 'SSN',
          dob: null,
          streetAddress1: null,
          city: null,
          state: null,
          zipcode: null,
          gRecaptchaResponse : null
        };
        vm.getLabel();
      };


      /** getStates service call **/
      vm.getStates = function(){
        CitizenService.getStates().then(function(response) {
          if(vm.goToStateSelect === false){
            /*$scope.citizenform.$invalid = false;*/
            vm.states = response;
            /* $('.g-recaptcha').remove();*/
            vm.initController();
            vm.screenLoad = true;
            CitizenSession.setStateCode($stateParams.selectedState);
            CitizenSession.setStateYear($stateParams.selectedYear);
            var timerFocus = $interval(function(){
              if($('.g-recaptcha').length){
                $('.form-control').eq(0).focus();
                $interval.cancel(timerFocus);
              }
            }, 200);
          }
        });
      };

      /** Check if user is coming to citizen form for the first time **/
      vm.checkUserState = function(){
        if( ($stateParams.selectedState === CitizenSession.selectedState) && ($stateParams.selectedYear === CitizenSession.selectedYear) ){
          vm.getStates();
          vm.goToStateSelect = false;
          vm.selectedState = state;
          vm.selectedYear = year;
          vm.toggleOn = true;
          vm.eConsent = false;
          vm.ariaCheckBox = vm.checkBoxlabel;
        }else{
          vm.goToStateSelect = true;
          CitizenSession.selectedState = '';
          CitizenSession.selectedYear = '';
          CitizenSession.viewPage = 'back';
          $state.go('citizen.citizen-state');
        }
      };

      vm.checkUserState();

      vm.captchaResponse = function(){
        vm.captchaFlag = true;
      };

      /** SSN / TIN Switch**/
      vm.switchSSN_TIN = function(){
        if(vm.user.uidType === 'SSN'){
          vm.user.uidType = 'TIN';
          vm.user.uidNumber = null;
          vm.toggleOn = false;
          vm.uidTypePlaceHolder = "Tax Identification Number";
          vm.dataLabel = "TIN selected. Push Enter to use a SSN #";
        }else{
          vm.user.uidType = 'SSN';
          vm.user.uidNumber = null;
          vm.toggleOn = true;
          vm.uidTypePlaceHolder = "Social Security Number";
          vm.dataLabel = "SSN selected. Push Enter to use a TIN #";
        }
        vm.ariaLabel.ssnTin = "You have currently selected" + vm.uidTypePlaceHolder + vm.required;
        if(!$scope.$$phase) {
          $scope.$apply();
        }
      };

      /*** E consent ***/
      vm.eConsentCheck = function(){
        /*$scope.user.eConsent = !$scope.user.eConsent;*/
        vm.eConsent = !vm.eConsent;
        if(vm.eConsent){
           vm.ariaCheckBox = "Enter to uncheck";
        }
        else{
           vm.ariaCheckBox = vm.checkBoxlabel;
        }
        if(!$scope.$$phase) {
          $scope.$apply();
        }
        vm.requiredChecked();
      };
      /*** Check if all required fields are entered ***/
      vm.requiredChecked = function(){
        vm.disabledSignIn = vm.citizenForm.$invalid  || !vm.captchaFlag || !vm.eConsent;
        if(!$scope.$$phase){
          $scope.$apply();
        }
      };

      $scope.$watch(function () { return vm.user.state }, function (newVal, oldVal) {
          if (newVal > "") {
             vm.ariaLabel.state = vm.user.state;
          }
          else{
             vm.ariaLabel.state = vm.statelabel;
          }
      });

      $scope.$watch(function () { return vm.disabledSignIn }, function (newVal, oldVal) {
          if (newVal == false) {
             var timerFocus = $interval(function(){
                $('.citizen-form-submit').eq(0).focus();
                $interval.cancel(timerFocus);
             }, 200);
          }
      });


      /**go to view page **/
      vm.goToView = function() {
        vm.user.gRecaptchaResponse = grecaptcha.getResponse();
        vm.user.userSelectedState = CitizenSession.selectedState;
        vm.user.userSelectedTaxYear = CitizenSession.selectedYear;

        CitizenService.identifyCitizen(vm.user)
          .then(function(data){
            if(data.errorMessage === undefined ||
              data.errorMessage === null || data.errorMessage === ''){
              /** user found **/
              vm.userNotFound = false;
              CitizenSession.userLoggedIn();
              /** Sets data in the service to move to the View/Print Screen **/
              CitizenService.setData(data);
              $state.go('citizen.citizen-view-general' , {}, {location : false});
            }else{
              /** user not found **/
              vm.userNotFound = true;
              vm.errorMessage = data.errorMessage;
              $('.errorMessageLabel').focus();
              grecaptcha.reset();
              vm.captchaFlag = false;
              vm.requiredChecked();
            }
          }, function(){
            vm.userNotFound = true;
          });

      };

  }]);
}());
