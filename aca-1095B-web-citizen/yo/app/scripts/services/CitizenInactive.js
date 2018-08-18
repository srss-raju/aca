
'use strict';

angular.module('aca1095BUiAppCitizen').factory('CitizenInactive', function($interval, $document, inactivityModal, CitizenSession) {
  var service = {};
  var timer;
  service.modalBox = false;

  document.onkeydown = function (evt) {
    evt = evt || window.event;
    if (evt.keyCode == 27) {
      if (service.modalBox)
        service.modalBox = false;
    }
  };

  function inactivitySignIn(){
    service.modalBox = false;
    inactivityModal.modalBx.close();
    unbindActions();
    CitizenSession.userLoggedOut();
  }

  function closeRender(){
    $interval.cancel(timer);
  }

  function openRender(){
    startTimeout();
  }

  function onClose(){
    if (timer) {
      $interval.cancel(timer);
    }
    CitizenSession.userLoggedOut();
  }

  function inactiveModal(){

    var modalAttribute = {
      windowClass: 'center-modal',
      animation: true,
      backdrop: 'static',
      keyboard: false,
      templateUrl: 'views/templates/inactivityModal.html',
      label : {
        "title": "Session Inactive",
        "message": "You have been signed out. Please sign back in to start a new session.",
        "buttonAction" : "Sign In"
      }
    };
    service.modalBox = true;
    inactivityModal.openModal(modalAttribute, inactivitySignIn, closeRender, openRender, onClose);
  }

  function startTimeout(){
    timer = $interval(function(){
      if (!service.modalBox)
        inactiveModal();
    }, 60000);
  }

  function eventBindings(){
    if (timer) {
      $interval.cancel(timer);
    }
    startTimeout();
  }

  function unbindActions(){
    var bodyElement = angular.element($document);

    angular.forEach(['keydown', 'keyup', 'click', 'mousemove', 'DOMMouseScroll', 'mousewheel', 'mousedown', 'touchstart', 'touchmove', 'scroll', 'focus'],
      function(EventName) {
        bodyElement.off(EventName, eventBindings);
      }
    );
  }

  function bindActions(){
    var bodyElement = angular.element($document);

    angular.forEach(['keydown', 'keyup', 'click', 'mousemove', 'DOMMouseScroll', 'mousewheel', 'mousedown', 'touchstart', 'touchmove', 'scroll', 'focus'],
      function(EventName) {
        bodyElement.on(EventName, eventBindings);
      }
    );
  }

  service.initializeTimer = function(){
    service.modalBox = false;
    if (timer) {
      $interval.cancel(timer);
    }
    startTimeout();
    bindActions();
  };

  return service;
});
