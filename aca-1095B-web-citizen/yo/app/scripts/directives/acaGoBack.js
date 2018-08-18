

(function () {
  'use strict';

  angular.module('aca1095BUiAppCitizen')
    .directive('acaGoBack', function($animate,$timeout) {
      return function(scope, elem, attr) {

        function clickAction() {
          $('#year-dropdown').hide();
          $('.enter-button').hide();
          $('.enter-button').removeClass('enable');
          $('.user-back').hide();
          $('.progress-bar').css('width', '0%');
          $('.animate-hide').removeClass("scroll-up");

          $('#state-dropdown').show();
          $('.arrow-icon').show();
        }

        elem.bind('click', function() {
          clickAction();
        });

        elem.on('keyup', function ($event) {
          if ($event.keyCode === 13) {
            clickAction();
            $event.preventDefault();
          }
        });

      };
    });

}());
