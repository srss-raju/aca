/**
 * Created by pchavan on 11/23/2015.
 */

'use strict';
(function(){

  function textModal($modal, $interval){
    var modalService = {};

    /** Open Modal **/
    modalService.openModal = function(modalAttribute, closeFunction){
      modalService.label = modalAttribute.label;
      modalAttribute.controller = 'textModalController';
      modalAttribute. templateUrl= 'views/templates/textModal.html';
      modalService.modalBx = $modal.open(modalAttribute);
      $(".modal-backdrop").show();
      modalService.yesFunction = function(){
        yesFunction();
      };
      modalService.noFunction = function(){
        noFunction();
      };
      modalService.closeFunction = function(){
        if(typeof closeFunction === 'undefined'){
          modalService.modalBx.close();
        }else{
          closeFunction();
        }
      };
      modalService.modalBx.rendered.then(function(){
        if(modalAttribute.modalSize === 'large'){
          $(".modal-dialog").addClass("large-modal");
        }
        if(modalAttribute.modalSize === 'larger'){
          $(".modal-dialog").addClass("larger-modal");
        }
        if(modalAttribute.modalHeight === 'fullscreen'){
          $(".text-modal").addClass("fullscreen");
        }
        var newTimerModal = $interval(function(){
          if($(".title").length){
            $(".title").focus();
            $interval.cancel(newTimerModal);
          }
        },20);
      },function(){
      });

      modalService.modalBx.result.then(function(){
      }, function(){
      });
    };

    return modalService;
  }

  function textModalController($scope,textModal){
    $scope.label = textModal.label;
    $scope.closeFunction = function(){
      textModal.closeFunction();
    };
  }

  angular.module('aca1095BUiAppCitizen')
    .service('textModal', [
      '$modal' ,
      '$interval',
      textModal])

    .controller('textModalController', [
      '$scope',
      'textModal',
      textModalController]);

}());
