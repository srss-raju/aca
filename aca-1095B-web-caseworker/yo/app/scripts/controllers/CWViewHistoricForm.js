/**
 * Created by pchavan on 12/3/2015.
 */

'use strict';

(function () {

  function CWViewHistoricForm ($scope, $state, $log, $sce, $timeout, CWService){

    /** Screen Labels **/
    $scope.label = {
      titleAction : "View",
      titleDescription : "Historic Form",

      lastModified : "Last Modified", comments : "Comments",
      generatedBy : "Generated By"
    };

    $scope.dataLoad = false;
    $scope.pdfLoad = false;
    $scope.initController = function(){
      $scope.historicData  = CWService.getHistoricData();
      if(typeof $scope.historicData !== 'undefined'){
        $scope.dataLoad = true;
        $scope.ariaLabel = {
            title : "You are viewing historic PDF version of the form",
            detail : "The archived record was last modified on " + CWService.ariaCompliantDate($scope.historicData.lastModifiedDate) +
                      " .This update was done by " + $scope.historicData.generatedBy +
                      " .Following are the comments entered " + $scope.historicData.comments
        };
        CWService.getHistoricDataPdf($scope.historicData.formID, $scope.historicData.auditSequenceNo)
          .success(function(data){
            var file = new Blob([data], {type: 'application/pdf'});
            if(window.navigator.msSaveOrOpenBlob){
              $scope.fileObject = {
                fileUrl: null,
                type: "pdf",
                filePDF : file
              };
            }
            else{
              var fileURL = window.URL.createObjectURL(file);
              $scope.pdfUrl = $sce.trustAsResourceUrl(fileURL);
              $scope.fileObject = {
                fileUrl: $scope.pdfUrl,
                type: "pdf",
                filePDF : null
              };
            }
            $scope.pdfLoad = true;
          })
          .error(function(data){
            $scope.pdfLoad = false;
          })
      }
    };

    $scope.initController();
  }

  angular.module('aca1095BUiAppCW').controller('CWViewHistoricForm', ['$scope', '$state', '$log', '$sce', '$timeout', 'CWService', CWViewHistoricForm]);
}());
