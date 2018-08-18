/**
 * Created by sdalavi on 10/12/2015.
 */
'use strict';

(function () {

  angular.module('aca1095BUiAppCW').controller('CWSearchUserCtrl', ['$scope', '$state', '$log','$filter','$timeout', '$interval','CWService', function($scope, $state, $log,$filter,$timeout, $interval,CWService) {

    var su = this;

    $('.initial-focus').focus();
    /** Screen Labels **/
    su.label = {
      titleAction : "Search",
      titleDescription: "Recipient",
      infomessage:"The 3 required field combinations are SSN + Tax Year or TIN + Tax Year or Last Name + Date of Birth + Tax Year",
      name:"Name",
      ssn:"SSN",
      dob:"DOB",
      tin:"TIN",
      form:"1095-B Form",
      status: "Status",
      view:"View",
      viewHover:"View 1095-B form is unavailable because user is a dependent"
    };

    /*search button disabled initially*/
    su.disableSearch = true;
    su.showDetails=false;
    su.taxYears=[{"value":"2016"},{"value":"2015"}];
    su.searchmodel={
      userFname:null,
      userLname:null,
      ssn:null,
      tin:null,
      dob:null,
      taxYear:su.taxYears[0].value
    };

    su.userNotFound = false;    /** Set to false by default : error message not displayed**/
    su.errorMessage = "Sorry, no record has been found.";
    su.spinnerShow = false;


    /** Aria Label **/
    su.cwSearchUser = {
      title: "Search for the recipient by entering 3 required field combinations, which are SSN + Tax Year or TIN + Tax Year or Last Name + Date of Birth + Tax Year",
      finduser:"Find the record of",
      userrecord:"The record of",
      disableuser:"is unavailable because user is a dependent",
      firstName:"Enter the First Name, this is an optional field",
      lastName:"Enter the Last Name",
      ssn:"Enter the Social Security Number",
      dob:"Enter the Date of Birth",
      tin:"Enter the Tax Identification Number",
      year:"Select Tax Year, use arrow up and arrow down keys to select the year",
      view: "View the 1095-B form"
    };

    /*on click function for clear search*/
    su.clearSearch = function(){
      su.searchmodel={
        userFname:null,
        userLname:null,
        ssn:null,
        tin:null,
        dob:null,
        taxYear: su.taxYears[0].value
      };
      su.caseworkerData = {};
      su.showDetails = false;
      su.disableSearch = true;
      su.searchuserform.$setPristine(true);
      su.userNotFound=false;
    };

    /*on click function for search button*/
    su.searchUser = function(){
      var date;
      su.searchmodal_copy = angular.copy(su.searchmodel);
      date = $filter('date')(su.searchmodel.dob, "MM/dd/yyyy");
      su.searchmodal_copy.dob = date;
      su.spinnerShow = true;
      su.disableSearch = true;
      su.showDetails = false;
      su.getDetail(su.searchmodal_copy);
    };

    su.getDetail = function(searchmodal) {
      CWService.getDetail(searchmodal)
        .then(function (data) {
          if (angular.isObject(data)) {
            su.spinnerShow = false;
            su.userNotFound = false;
            su.showDetails = true;
            su.caseworkerData = data;
            $timeout(function () {
              $('.viewhover').popover();
            }, 5);
            var testInterval = $interval(function () {
              if ($('.viewLink').eq(0).length) {
                $('.viewLink').eq(0).focus();
                $interval.cancel(testInterval)
              }
            }, 50);
          }
          else {
            /** user not found **/
            su.spinnerShow = false;
            su.userNotFound = true;
            /*hide the details*/
            su.showDetails = false;
            su.errorfocus();
          }
        }, function () {
          /** user not found **/
          su.spinnerShow = false;
          su.userNotFound = true;
          /*hide the details*/
          su.showDetails = false;
          su.errorfocus();
        });
    };



    su.errorfocus = function(){
      $timeout(function(){
        $('.errorfocus').focus();
      },100);
      $timeout(function(){
        $('.initial-focus').focus();
      },5000);
    };

    /*gets triggered on any change in the form fields*/
    su.changeField = function(){
      if (su.searchuserform.$valid && su.searchuserform.ssn.$valid && su.searchuserform.ssn.$modelValue)
      {
        su.disableSearch = false;
      }
      else if (su.searchuserform.$valid && su.searchuserform.tin.$valid && su.searchuserform.tin.$modelValue)
      {
        su.disableSearch = false;
      }
      else if (su.searchuserform.$valid && su.searchuserform.lastName.$valid && su.searchuserform.dob.$valid && su.searchuserform.lastName.$modelValue && su.searchuserform.dob.$modelValue) {
        su.disableSearch = false;
      }
      else{
        su.disableSearch = true;
      }
    };

    su.goToViewUser = function(sourceId){
      CWService.setFormIdforView(sourceId);
      $state.go('state.cw-view-user');
    };
  }]);
}());
