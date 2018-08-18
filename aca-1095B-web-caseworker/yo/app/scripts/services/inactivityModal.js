/**
 * Created by pchavan on 10/9/2015.
 */

'use strict';
(function(){

  function inactivityModal($modal){
    var modalService = {};
    /** Open Modal **/
    modalService.openModal = function(modalAttribute, buttonAction, closeRender, openRender, onClose){
      modalService.label = modalAttribute.label;
      if(typeof closeRender === 'undefined'){
        closeRender = function(){

        };
      }
      if(typeof openRender === 'undefined'){
        openRender = function(){

        };
      }
      if(typeof onClose === 'undefined'){
        onClose = function(){

        };
      }
      modalAttribute.controller = 'inactivityModalController';
      modalService.modalBx = $modal.open(modalAttribute);
      $(".modal-backdrop").show();
      modalService.buttonAction = function(){
        buttonAction();
      };
      modalService.modalBx.rendered.then(function(){
        closeRender();
      },function(){
        openRender();
      });

      modalService.modalBx.result.then(function(){
        onClose();
      }, function(){
        onClose();
      });
    };


    return modalService;
  }

  function inactivityModalController($scope,inactivityModal){
    $scope.label = inactivityModal.label;
    $scope.buttonAction = function(){
      inactivityModal.buttonAction();
    };
  }

  angular.module('aca1095BUiAppCW')
    .service('inactivityModal', [
      '$modal' ,
      inactivityModal])

    .controller('inactivityModalController', [
      '$scope',
      'inactivityModal',
      inactivityModalController]);

}());
