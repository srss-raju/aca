

(function () {
  'use strict';

  angular.module('aca1095BUiAppCitizen')
    .directive('acaSelectYear', function($animate,$timeout) {

      return {

          scope: {
            userYear: "=userYear",
            options: "=options"
          },
          link: function(scope, elem, attr) {

            function checkExistence(){

              if(scope.userYear){
                for (var i=0; i < scope.options.length; i++) {
                  if (scope.options[i]['year'] === scope.userYear) {
                    return true;
                  }
                }
              }
              return false;

            }

            function clickAction() {
              $('#state-dropdown').hide();
              $('.arrow-icon').hide();
              $('.animate-hide').addClass("scroll-up");

              if (checkExistence()) {
                $('.progress-bar').css('width', '100%');
                $('.enter-button').addClass('enable');
              }
              else {
                $('.progress-bar').css('width', '50%');
              }

              $('#year-dropdown').show();
              $('.enter-button').show();
              $('.user-back').show();
            }

            elem.bind('click', function () {
              clickAction();
            });

            elem.on('keyup', function ($event) {
              if ($event.keyCode === 13) {
                clickAction();
                $event.preventDefault();
              }
            });


          }
      };

    });

}());
