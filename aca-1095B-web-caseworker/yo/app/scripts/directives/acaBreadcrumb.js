/**
 * Created by pchavan on 10/16/2015.
 */

(function () {
  'use strict';

  function acaBreadcrumb() {
    return{
      restrict: "EA",
      scope: {
        isParent : "="
      },
      templateUrl: 'views/templates/acaBreadcrumb.html',
      controller: function ($scope, $element, $rootScope, $state, acaBreadcrumbService, $log, StateService, confirmationDialog) {
        /** initialize breadcrumb list **/
        $scope.listBreadcrumb = acaBreadcrumbService.listBreadcrumb;

        $scope.$watch(function () {
          return confirmationDialog.modalBox
        }, function (newVal, oldVal) {
          if (typeof newVal !== 'undefined') {
            $scope.modalBox = confirmationDialog.modalBox;
          }
        });

        /** if new parent : reset bread crumb **/
        if($scope.isParent){
          acaBreadcrumbService.resetBreadcrumb($state.current);
        }
        $rootScope.$on('$stateChangeSuccess', function (ev, to, toParams, from, fromParams) {
          //assign the "from" parameter to something
         /* $log.info("ev : ", ev);
          $log.info("to :", to);
          $log.info("toParams :", toParams);
          $log.info("from :",from);
          $log.info("fromParams :",fromParams);*/
          StateService.showDropDown = false;
          if(!to.data.isParent){
            acaBreadcrumbService.addNewState(to);
          }else{
            acaBreadcrumbService.resetBreadcrumb(to);
          }
         /* $log.info("list :", acaBreadcrumbService.listBreadcrumb);*/
        });

      }
    };
  }

  function acaBreadcrumbService ($log){
    var service = {};
    service.listBreadcrumb = [];

    /** resets when parent view of breadcrumb loaded **/
    service.resetBreadcrumb = function(stateCurrent){
      /** current parent state not present or bread crumb not defined **/
      service.listBreadcrumb = [];
      /** current parent state pushed **/
      service.listBreadcrumb.push(stateCurrent.data);
    };

    /** add new state to breadcrumb **/
    service.addNewState = function(toState){
      /** logic to take care of even of multiple firing of $stateChangeSuccess event **/
      var pathExist = service.listBreadcrumb.filter(function(a){ return a.id === toState.data.id; });
      if(pathExist.length){
        /*$log.info("path  existing");*/
        var numberBreadcrumb = service.listBreadcrumb.indexOf(pathExist[0]);
        /** Remove from bread crumb list next elements **/
        if(numberBreadcrumb < service.listBreadcrumb.length - 1){
          service.listBreadcrumb.splice(numberBreadcrumb);
        }
      }else{
        /*$log.info("path not existing");*/
        service.listBreadcrumb.push(toState.data);
      }
    };

    return service;
  }
  angular.module('aca1095BUiAppCW')
    .directive("acaBreadcrumb", acaBreadcrumb)
    .service("acaBreadcrumbService", acaBreadcrumbService);
}());
