/**
 * Created by pchavan on 11/2/2015.
 */

'use strict';
(function(){

  function confirmationDialog($modal, $timeout){
    var modalService = {};

    document.onkeydown = function (evt) {
      evt = evt || window.event;
      if (evt.keyCode == 27) {
        if (modalService.modalBox)
          modalService.modalBox = false;
      }
    };

    modalService.modalController = function($scope){
      $scope.label = modalService.label;
      $scope.label.loading = $scope.label.loading || false;
      $scope.yesFunction = function(){
        modalService.yesFunction();
      };
      $scope.noFunction = function(){
        modalService.noFunction();
      };
      $scope.closeFunction = function(){
        modalService.closeFunction();
      };
    };

    /** Open Modal **/
    modalService.openModal = function(modalAttribute, yesFunction, noFunction, closeFunction){
      modalService.label = modalAttribute.label;
      modalService.label.loading = modalService.label.loading || false;
      modalAttribute.controller = 'confirmationDialogController';
      modalService.modalBx = $modal.open(modalAttribute);
      $(".modal-backdrop").show();
      modalService.yesFunction = function(){
        yesFunction();
      };
      modalService.noFunction = function(){
        noFunction();
      };
      modalService.closeFunction = function(){
        closeFunction();
      };
      modalService.modalBx.rendered.then(function(){
        if(modalAttribute.modalSize === 'large'){
          $(".modal-dialog").addClass("large-modal");
        }
        $timeout(function(){
          $(".title-dialog").focus();
        }, 1000);
      },function(){
      });

      modalService.modalBx.result.then(function(){
      }, function(){
      });
    };

    modalService.modalBox = false;

    return modalService;
  }

  function confirmationDialogController($scope,confirmationDialog){
    $scope.label = confirmationDialog.label;
    $scope.yesFunction = function(){
      confirmationDialog.yesFunction();
    };
    $scope.noFunction = function(){
      confirmationDialog.noFunction();
    };
    $scope.closeFunction = function(){
      confirmationDialog.closeFunction();
    };

  }

  angular.module('aca1095BUiAppCW')
    .service('confirmationDialog', [
      '$modal' ,
      '$timeout',
      confirmationDialog])

    .controller('confirmationDialogController', [
      '$scope',
      'confirmationDialog',
      confirmationDialogController]);

}());
