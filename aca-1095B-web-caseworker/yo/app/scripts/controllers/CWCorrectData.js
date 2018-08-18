/**
 * Created by pchavan on 10/16/2015.
 */
'use strict';

(function () {


  function CWCorrectData($scope, $state, $log, $timeout, $interval, ENV, CWService, UserService) {

    var states = [],
      userrole = null,
      form = null,
      role = null;

    /** Screen Labels **/
    $scope.label = {
      titleAction: "Edit",
      titleDescription: "Recipient Data",
      coverage: "Coverage",
      source: "Source:",
      lastmodified: "Last Modified",
      caseid: "Case ID:",
      pdfstatus: "Form Status:",
      save: "Save",
      cancel: "Cancel",
      recipientHeading: "Recipient Information",
      currentCoverage: "Current Coverage",
      coverageSource: "Coverage Source(s)",
      jan: "Jan",
      feb: "Feb",
      mar: "Mar",
      apr: "Apr",
      may: "May",
      jun: "Jun",
      jul: "Jul",
      aug: "Aug",
      sep: "Sep",
      oct: "Oct",
      nov: "Nov",
      dec: "Dec"
    };

    CWService.getStateData()
      .success(function (data) {
        $scope.states = data.list;
      });

    $scope.spinnerOn = false;
    $scope.savestatus = true;
    $scope.errorflag = false;
    $scope.errorMessage = "Unable to save the data";

    form = CWService.cViewGet();

    if(form){
      CWService.getSourceData(form.formId)
        .success(function (data) {
          if (data) {
            data.currentForm.comments = "";
            var coverageSourceARIA = [];
            angular.forEach(data.coverageSources, function (val, key) {
              val.comments = "";
              coverageSourceARIA[key] = {
                "sourceName": "Listed coverage source " + key + 1 + " is " + val.source +
                              "It was last modified on" + val.lastModifiedDate.split(' ')[0]
              }
            });
            $scope.correctUserDetails_master = angular.copy(data);
            $scope.correctUserDetails = angular.copy(data);
            $scope.correctdataform.firstname.$setDirty("firstname", true);
            $scope.correctdataform.lastname.$setDirty("lastname", true);
            $scope.correctdataform.dob.$setDirty("dob", true);
            $scope.correctdataform.uid.$setDirty("uid", true);
            $scope.correctdataform.addressl1.$setDirty("addressl1", true);
            $scope.correctdataform.addressl2.$setDirty("addressl2", true);
            $scope.correctdataform.city.$setDirty("city", true);
            $scope.correctdataform.state.$setDirty("state", true);
            $scope.correctdataform.zipcode.$setDirty("zipcode", true);
            $scope.correctdataform.comments.$setDirty("comments", true);

            /*set the color of the pdf status*/
            $scope.class = 'status-';

            switch ($scope.correctUserDetails.currentForm.pdfStatus) {
              case 'GENERATED':
                $scope.pdfDisplayVal = 'Form Available';
                $scope.classname = $scope.class + 'green';
                break;
              case 'REGENERATED':
                $scope.pdfDisplayVal = 'Data Updated - Regenerate Form';
                $scope.classname = $scope.class + 'amber';
                break;
              case 'GENERATION_FAILED':
                $scope.pdfDisplayVal = 'Data Updated - Regenerate Form';
                $scope.classname = $scope.class + 'amber';
                break;
              case 'ADDRESS_INVALID':
                $scope.pdfDisplayVal = 'Address Invalid - Returned to Sender';
                $scope.classname = $scope.class + 'red';
                break;
              default :
                $scope.pdfDisplayVal = 'Not Available';
                $scope.classname = $scope.class + 'black';
                break;
            }

            /** Aria Label **/
            var coveredIndividual = 'You can edit first name and last name for the citizen' + $scope.correctUserDetails.filerInfo.firstName +
                                    " " + $scope.correctUserDetails.filerInfo.lastName + ".";

            var responsibleIndividual = 'You can edit first name and last name for the citizen' + $scope.correctUserDetails.filerInfo.firstName +
              " " + + $scope.correctUserDetails.filerInfo.lastName +
              "You can also update the address for this citizen. This is the address to which the mailed copy of the form will be sent.";

            var addressUpdate = $scope.correctUserDetails.filerInfo.filerStatus === 'C' ? coveredIndividual : responsibleIndividual;

            var coverageUpdate = $scope.correctUserDetails.filerInfo.filerStatus === 'N' ? "" : " You can also edit the coverage information.";
            $scope.ariaLabel = {
              title: "You are currently in Edit Recipient Data mode. You can save your changes using Alt plus S key in IE and Chrome and Shift plus Alt plus S in Firefox.  You can use Backspace key to cancel the edit mode" +
                      addressUpdate + coverageUpdate,
              fname: "First Name",
              lname: "Last Name",
              aline1: "Address Line 1",
              aline2: "Address Line 2",
              city: "City",
              state: "State",
              zipcode: "Zipcode",
              cancel: "Cancel Button",
              save: "Save Button",
              comments: "Comments",
              coverageSource : coverageSourceARIA
            };

            var timerFocus = $interval(function(){
                $('.correct-data-view .screen-title').eq(0).focus();
                $interval.cancel(timerFocus);
            }, 200);

          }
        })
        .error(function (data) {

        });
    }

    /*change the aggregated coverage depending on the individual source coverage*/
    $scope.change_main_coverage = function(month){
      var coverageObject,coverageValue,actualValue=null;
      angular.forEach($scope.correctUserDetails.coverageSources, function (value, key) {
        coverageObject = value;
        angular.forEach(coverageObject, function(value,key){
          if(key==='filerSourceInfo'){
            coverageValue = value;
            angular.forEach(coverageValue,function(value,key){
              if(value['month']===month){
                if(actualValue===null || actualValue==='0'){
                  actualValue = value['covered'];
                }
              }
            });
          }
        });
      });

      angular.forEach($scope.correctUserDetails.filerCoverageInfo, function (value, key) {
        if(value['month']===month){
          value['covered']=actualValue;
        }
      });
      coverageObject="";
      coverageValue="";
      actualValue="";
    };

    /*enable save button on change of data*/
    $scope.$watch('correctUserDetails', function(){
      var x=0,y=0;
      if($scope.user){
        role = angular.lowercase($scope.user.role);
        userrole = ENV.unicornRolePreFix + ENV.caseworker_read_write;
        userrole = angular.lowercase(userrole);
        if (role === userrole) {
          if (angular.equals($scope.correctUserDetails, $scope.correctUserDetails_master)) {
            $scope.savestatus = true;
          }
          else {
            if($scope.correctUserDetails.filerInfo.firstName != $scope.correctUserDetails_master.filerInfo.firstName ||
              $scope.correctUserDetails.filerInfo.lastName != $scope.correctUserDetails_master.filerInfo.lastName ||
              $scope.correctUserDetails.currentForm.addressLine1 != $scope.correctUserDetails_master.currentForm.addressLine1 ||
              $scope.correctUserDetails.currentForm.addressLine2 != $scope.correctUserDetails_master.currentForm.addressLine2 ||
              $scope.correctUserDetails.currentForm.city != $scope.correctUserDetails_master.currentForm.city ||
              $scope.correctUserDetails.currentForm.state != $scope.correctUserDetails_master.currentForm.state ||
              $scope.correctUserDetails.currentForm.zipcode != $scope.correctUserDetails_master.currentForm.zipcode
              )
              {
                x+=1;
              }
            else{
              x-=1;
            }
             angular.forEach($scope.correctUserDetails.coverageSources,function(key,value){
               if(angular.equals(key.filerSourceInfo,$scope.correctUserDetails_master.coverageSources[value].filerSourceInfo)){
                 if(!y===0){
                   y-=1;
                 }
               }else{
                 y+=1;
               }
              });

              if ($scope.correctdataform.$valid && (x>0 || y>0)) {
                  $scope.savestatus = false;
              }
              else {
                $scope.savestatus = true;
              }
            }
        }
        else {
          $scope.savestatus = true;
        }
      }
    }, true);

    $scope.assign_class = function (comments) {

    };

    $scope.backToView = function () {
      $state.go('state.cw-view-source-data');
    };

    $scope.saveData = function () {

      if (!angular.equals($scope.correctUserDetails.coverageSources, $scope.correctUserDetails_master.coverageSources)) {
        var concat = "";
        if ($scope.correctUserDetails.currentForm.comments) {
          concat = ' and ';
        }
        if ($scope.correctUserDetails.filerInfo.filerStatus === 'C') {
          $scope.correctUserDetails.currentForm.comments = $scope.correctUserDetails.currentForm.comments + concat + "Edited Covered person's coverage details";
        }
        else {
          $scope.correctUserDetails.currentForm.comments = $scope.correctUserDetails.currentForm.comments + concat + "Edited coverage details";
        }
        $scope.correctUserDetails.coverageUpdated = true;
      }
      ;

      CWService.saveEditedData($scope.correctUserDetails)
        .success(function (data) {
          if (!data.error) {
            CWService.setSaveFlag(true);
            $state.go('state.cw-view-source-data');
          }
          else {
            $scope.errorflag = data.error;
            $scope.errorMessage = "Unable to save the data";
          }

        })
        .error(function (data) {
          $scope.errorflag = data.error;
          $scope.errorMessage = "Unable to save the data";
        });
    };

  }

  angular.module('aca1095BUiAppCW').controller('CWCorrectData', ['$scope', '$state', '$log', '$timeout', '$interval', 'ENV', 'CWService', 'UserService', CWCorrectData]);
}());

