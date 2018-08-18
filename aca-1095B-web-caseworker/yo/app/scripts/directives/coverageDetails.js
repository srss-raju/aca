/**
 * Created by speta on 11/9/2015.
 */
'use strict';

angular.module('aca1095BUiAppCW')
  .directive('coverageDetails',function(){
  return{
    restrict:'EA',
    scope:{
      cinfo:"=",
      isClickable:"=",
      fnClick:"&",
      source : "=",
      tabIndex:"@"
    },
    templateUrl:'views/templates/coverageDetails.html',
    link: function(scope){
      if(scope.tabIndex === 'undefined'){
        scope.tabIndex2 = -1;
      }
      //scope.tabIndex2 = parseInt(scope.tabIndex);
    },
    controller:function($scope) {
      $scope.showhover=false;
      $scope.ariaLabel = {
        coverageSource : "Coverage Details,",
        coverageMonthly : [{"month": "January", "covered" : "Not covered"},{"month": "February", "covered" : "Not covered"},{"month": "March", "covered" : "Not covered"},
          {"month": "April", "covered" : "Not covered"},{"month": "May", "covered" : "Not covered"},{"month": "June", "covered" : "Not covered"},
          {"month": "July", "covered" : "Not covered"},{"month": "August", "covered" : "Not covered"},{"month": "September", "covered" : "Not covered"},
          {"month": "October", "covered" : "Not covered"},{"month": "November", "covered" : "Not covered"},{"month": "December", "covered" : "Not covered"}
        ]
      };
      $scope.updateAria = function(){
        $scope.ariaLabel.coverageSource = $scope.ariaLabel.coverageSource + $scope.source ? "  coverage from source " + $scope.source + " " : "";
        var coveredMonths = "Coverage available for months of";
        var allCovered = 0;   var notCovered = 0;
        angular.forEach($scope.cinfo, function (value, key) {
          //if(value.covered === '1'){
          switch(value.month){
            case 'Jan' : {
              if(value.covered === '1'){
                coveredMonths = coveredMonths +  'January' + ", ";
                allCovered ++ ;
                $scope.ariaLabel.coverageMonthly[0].covered = "Covered";
              }else{
                notCovered ++;
                $scope.ariaLabel.coverageMonthly[0].covered = "Not covered";
              }
            }
              break;
            case 'Feb' : {
              if(value.covered === '1'){
                coveredMonths = coveredMonths +  'February' + ", ";
                allCovered ++ ;
                $scope.ariaLabel.coverageMonthly[1].covered = "Covered";
              }else{
                notCovered ++;
                $scope.ariaLabel.coverageMonthly[1].covered = "Not covered";
              }
            }
              break;
            case 'Mar' :{
              if(value.covered === '1'){
                coveredMonths = coveredMonths +  'March' + ", ";
                allCovered ++ ;
                $scope.ariaLabel.coverageMonthly[2].covered = "Covered";
              }else{
                notCovered ++;
                $scope.ariaLabel.coverageMonthly[2].covered = "Not covered";
              }
            }
              break;
            case 'Apr' :{
              if(value.covered === '1'){
                coveredMonths = coveredMonths +  'April' + ", ";
                allCovered ++ ;
                $scope.ariaLabel.coverageMonthly[3].covered = "Covered";
              }else{
                notCovered ++;
                $scope.ariaLabel.coverageMonthly[3].covered = "Not covered";
              }
            }
              break;
            case 'May' :{
              if(value.covered === '1'){
                coveredMonths = coveredMonths +  'May' + ", ";
                allCovered ++ ;
                $scope.ariaLabel.coverageMonthly[4].covered = "Covered";
              }else{
                notCovered ++;
                $scope.ariaLabel.coverageMonthly[4].covered = "Not covered";
              }
            }
              break;
            case 'Jun' :{
              if(value.covered === '1'){
                coveredMonths = coveredMonths +  'June' + ", ";
                allCovered ++ ;
                $scope.ariaLabel.coverageMonthly[5].covered = "Covered";
              }else{
                notCovered ++;
                $scope.ariaLabel.coverageMonthly[5].covered = "Not covered";
              }
            }
              break;
            case 'Jul' :{
              if(value.covered === '1'){
                coveredMonths = coveredMonths +  'July' + ", ";
                allCovered ++ ;
                $scope.ariaLabel.coverageMonthly[6].covered = "Covered";
              }else{
                notCovered ++;
                $scope.ariaLabel.coverageMonthly[6].covered = "Not covered";
              }
            }
              break;
            case 'Aug' : {
              if(value.covered === '1'){
                coveredMonths = coveredMonths +  'August' + ", ";
                allCovered ++ ;
                $scope.ariaLabel.coverageMonthly[7].covered = "Covered";
              }else{
                notCovered ++;
                $scope.ariaLabel.coverageMonthly[7].covered = "Not covered";
              }
            }
              break;
            case 'Sep' : {
              if(value.covered === '1'){
                coveredMonths = coveredMonths +  'September' + ", ";
                allCovered ++ ;
                $scope.ariaLabel.coverageMonthly[8].covered = "Covered";
              }else{
                notCovered ++;
                $scope.ariaLabel.coverageMonthly[8].covered = "Not covered";
              }
            }
              break;
            case 'Oct' :{
              if(value.covered === '1'){
                coveredMonths = coveredMonths +  'October' + ", ";
                allCovered ++ ;
                $scope.ariaLabel.coverageMonthly[9].covered = "Covered";
              }else{
                notCovered ++;
                $scope.ariaLabel.coverageMonthly[9].covered = "Not covered";
              }
            }
              break;
            case 'Nov' : {
              if(value.covered === '1'){
                coveredMonths = coveredMonths +  'November' + ", ";
                allCovered ++ ;
                $scope.ariaLabel.coverageMonthly[10].covered = "Covered";
              }else{
                notCovered ++;
                $scope.ariaLabel.coverageMonthly[10].covered = "Not covered";
              }
            }
              break;
            case 'Dec' : {
              if(value.covered === '1'){
                coveredMonths = coveredMonths +  'and December' + ", ";
                allCovered ++ ;
                $scope.ariaLabel.coverageMonthly[11].covered = "Covered";
              }else{
                notCovered ++;
                $scope.ariaLabel.coverageMonthly[11].covered = "Not covered";
              }
            }
              break;
            default : {
              if(value.covered === '1'){
                coveredMonths = coveredMonths +  'No Month Error' + ", ";
              }
            }
              break;
          }
          if(allCovered === 12){
            coveredMonths = "Coverage available for all the 12 months";
          }
          //}
        });
        $scope.ariaLabel.coverageSource = $scope.ariaLabel.coverageSource + coveredMonths;
      };
      $scope.updateAria();
      if($scope.isClickable){
        $scope.change_coverage = function (monthRecord) {
          monthRecord.covered = monthRecord.covered === '1' ? '0' : '1';
          $scope.updateAria();
          $scope.fnClick({month:monthRecord.month});

        };
      }
    }
  };
});
