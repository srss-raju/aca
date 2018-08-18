/**
 * Created by pchavan on 3/31/2016.
 */

(function () {
  'use strict';

  function acaSortColumn() {
    return {
      restrict: "EA",
      scope: {
        columnName : "=columnName",
        label : "=label",
        tableData : "=tableData"
      },
      templateUrl: 'views/templates/acaSortColumn.html',
      link: function(scope){
        scope.ordered = false;
        scope.tableData = scope.tableData || [];
      },
      controller: function($scope){
        $scope.statusSort = function() {
          $scope.ordered = !$scope.ordered;
          $scope.tableData.sort(function(a, b) {
            if ((!a.transmissionAckStatus && b.transmissionAckStatus) || (!a.transmissionAckStatus && !b.transmissionAckStatus)) {
              return $scope.ordered ? -1 : 1;
            }else if(a.transmissionAckStatus && !b.transmissionAckStatus){
              return $scope.ordered ? 1 : -1;
            }else if (a.transmissionAckStatus.toLowerCase() > b.transmissionAckStatus.toLowerCase()) {
              return $scope.ordered ? 1 : -1;
            } else if (a.transmissionAckStatus.toLowerCase() < b.transmissionAckStatus.toLowerCase()) {
              return $scope.ordered ? -1 : 1;
            } else {
              return 0;
            }
          });
        };
        $scope.dateSort = function(dateType) {
          $scope.ordered = !$scope.ordered;
          $scope.tableData.sort(function(a, b) {
            var a_date = new Date(a[dateType]); var b_date = new Date(b[dateType]);
            if (a_date > b_date) {
              return $scope.ordered ? 1 : -1;
            } else if (a_date < b_date) {
              return $scope.ordered ? -1 : 1;
            } else {
              return 0;
            }
          });
        };
        /* click event sort */
        $scope.sortFn = function(selColumn) {
          //alert(selColumn);
          switch (selColumn) {
            case 'transmissionAckStatus':
              $scope.statusSort();
              break;
            case 'transferDate':
              $scope.dateSort('transferDate');
              break;
            case 'transmissionDate':
              $scope.dateSort('transmissionDate');
              break;
            case 'transmissionAckDate':
              $scope.dateSort('transmissionAckDate');
              break;
            default:
            {

            }
              break;
          }
        };

        $scope.$watch('tableData', function(value){
          $scope.tableData = value;
        });
      }
    };
  }

  angular.module('aca1095BUiAppCW')
    .directive('acaSortColumn', acaSortColumn);
}());
