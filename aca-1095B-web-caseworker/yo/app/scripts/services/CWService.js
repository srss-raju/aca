/**
 * Created by speta on 10/27/2015.
 */
'use strict';

angular.module('aca1095BUiAppCW').factory('CWService', function($http, ENV,$q) {
  var cwService = {};

  var url = ENV.endPoint + ENV.api;

  /*** CONSTANTS ***/
  cwService.constants = {
    "readyToMail" : "READY_TO_MAIL",
    "mailPending" : "MAIL_PENDING",
    "mailComplete": "MAILED",
    "generatedPdf" : "GENERATED",
    "regeneratePdf": "REGENERATE",
    "generationFailed" : "GENERATION_FAILED",
    "addressInvalid" : "ADDRESS_INVALID"
  };

  /*** Search Result Service ***/
  cwService.getDetail = function(searchmodel){
    //return $http.get('stubs/searchUserCaseWorker.json')
    return $http.post(url + '/caseworkerportal/searchFiler', JSON.stringify(searchmodel), {header: 'Content-Type: application/json'})
    .then(function(response) {
      if (typeof response.data === 'object') {
        return response.data;
      } else {
        return $q.reject(response.data);
      }
    }, function(response) {
      return $q.reject(response.data);
    });
  };

  /*** Set customerId : Saves customerId or citizen ID on click View on CW Search Screen***/
  cwService.setFormIdforView= function(formId){
    cwService.formId = formId;
  };

  /*** Get customerId : Gets customerId or citizen ID on load CW View 1095B form screen***/
  cwService.getFormIdforView= function(){
    return cwService.formId;
  };

  /*** View 1095B form service***/
  cwService.viewDetail = function(formId){
    //return $http.get('stubs/cw-view-data.json');
    return $http.post(url + '/caseworkerportal/getForm?formID=' + formId, {}, {header: 'Content-Type: application/json'});
  };

  /*** Sets View on Source Data : Defines if user has clicked on pdf icon to view pdf or chevron to view source data ***/
  /*** Sets formId on Source Data ***/
  cwService.cViewSet = function(view, formId){
    cwService.viewSourceData = {
      formId : formId
    };
  };

  /*** Gets View on Source Data : Defines if user has clicked on pdf icon to view pdf or chevron to view source data ***/
  /*** Gets formId on Source Data ***/
  cwService.cViewGet = function(){
    return cwService.viewSourceData;
  };

  /*gets the customer data based on the form id when the name on View 1095B is clicked*/
  cwService.getSourceData = function(formID){
    //return $http.get('stubs/viewSourceData2.json');
    return $http.post(url + '/caseworkerportal/viewCustomerExistingData?customerID=' + formID, {header: 'Content-Type: application/json'});
  };

  /*save the edited data*/
  cwService.saveEditedData = function(editedData){
    return $http.post(url + '/caseworkerportal/saveFilerDemographicsData',JSON.stringify(editedData),{header : 'Content-Type: application/json'});
  };

  /*set save flag*/
  cwService.setSaveFlag = function(val){
    cwService.saveFlag = val;
  };

  /*get save flag*/
  cwService.getSaveFlag = function(){
    return cwService.saveFlag;
  };

  /** Current PDF Data and pdf **/
  cwService.setCurrentData = function(currentForm, filerInfo){
    cwService.currentFormView = {
      "currentForm" : currentForm,
      "filerInfo" : filerInfo
    };
  };

  cwService.getCurrentData = function(){
    return cwService.currentFormView;
  };

  cwService.getCurrentDataPdf = function(formId){
    return $http.post(url + '/caseworkerportal/viewCustomerPdf?pdfID=' + formId, {})
      .then(function (response) {
        if (typeof response.data === 'object') {
          return response.data;
        } else {
          return $q.reject(response.data);
        }
      }, function (response) {
        return $q.reject(response.data);
      });
  };

  cwService.setFormData = function (data) {
    cwService.formData = data;
  };

  cwService.getFormData = function () {
    return cwService.formData;
  };

  /** Historic PDF Data and pdf **/
  cwService.setHistoricData = function(historicData){
    cwService.historicData = historicData;
  };

  cwService.getHistoricData = function(){
    return cwService.historicData;
  };

  cwService.getHistoricDataPdf = function(formId, auditSeqNo){
    return $http.post(url + '/caseworkerportal/viewCustomerPdf?pdfID=' + formId + '&auditSequenceNo=' + auditSeqNo, {})
      .then(function (response) {
        if (typeof response.data === 'object') {
          return response.data;
        } else {
          return $q.reject(response.data);
        }
      }, function (response) {
        return $q.reject(response.data);
      });
  };

  cwService.mailRequest = function(formID){
    //return $http.get('stubs/sendByMail2.json');
    return $http.post(url + '/caseworkerportal/requestMail?customerID=' + formID, {header: 'Content-Type: application/json'});
  };

  cwService.getStateData = function () {
    return $http.get('stubs/address-states.json');
  };

  cwService.ariaCompliantDate = function(dateRecvd){
    var dateObj = new Date(dateRecvd);
    var dateLabel = {
      month : "January", year : "2016", date : "9"
    };
    switch(dateObj.getMonth()){
      case 0 : dateLabel.month = "January"; break;  case 1 : dateLabel.month = "February"; break;  case 2 : dateLabel.month = "March"; break; case 3 : dateLabel.month = "April"; break;
      case 4 : dateLabel.month = "May"; break;  case 5 : dateLabel.month = "June"; break;  case 6 : dateLabel.month = "July"; break; case 7 : dateLabel.month = "August"; break;
      case 8 : dateLabel.month = "September"; break;  case 9 : dateLabel.month = "October"; break;  case 10 : dateLabel.month = "November"; break; case 11 : dateLabel.month = "December"; break;
      default : dateLabel.month = "January";
    }

    dateLabel.date = dateObj.getDate()+"";
    dateLabel.year = dateObj.getFullYear()+"";

    return dateLabel.month + " " + dateLabel.date + " "+ dateLabel.year;
  };

  cwService.printDiv = function (divClass) {

    jQuery.get('styles/main.css', function (maincss) {
      jQuery.get('styles/vendor.css', function (vendorcss) {
        window.frames["print_frame"].document.body.innerHTML = '<style>' + vendorcss + maincss + '</style>' + document.getElementsByClassName(divClass)[0].innerHTML;
        window.frames["print_frame"].window.focus();
        window.frames["print_frame"].window.print();
      });
    });

  };

  cwService.setPreviousState = function (state) {
    cwService.previousState = state;
  };

  return cwService;
});
