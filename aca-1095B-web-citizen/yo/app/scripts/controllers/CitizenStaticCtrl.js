
'use strict';

(function () {
  function CitizenStaticCtrl($scope, $element , $state, CitizenService, CitizenSession) {
    $scope.state = CitizenSession.staticState;

    $scope.getData = function(){
      CitizenService.getStaticContent($scope.state).success(
        function(data){
          $scope.detail = data;
        }
      );
    };

    $scope.stateBgImgClass = angular.lowercase($scope.state) + '-state-bg-image';
    $scope.detail = {
      title: "We're sorry. Access to this page is unavailable until 00/00/2017"
    };

    $scope.getData();

  }
  angular.module('aca1095BUiAppCitizen').controller('CitizenStaticCtrl', ['$scope', '$element', '$state', 'CitizenService', 'CitizenSession', CitizenStaticCtrl]);
}());
