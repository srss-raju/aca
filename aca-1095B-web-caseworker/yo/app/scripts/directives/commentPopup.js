/**
 * Created by pchavan on 10/28/2015.
 */

(function () {
  'use strict';

  function commentPopup(){
    return {
      restrict: "EA",
      scope:{
        detail : '='
      },
      templateUrl: 'views/templates/commentPopup.html',
      link : function(scope,elem,attr){
        var text = angular.copy(scope.detail);
        scope.detailDisplayed = ""; scope.detailHover = "";
        if(text.length > 107){
          scope.detailDisplayed = text.substr(0,103) + " ...";
          scope.detailHover = "... " + text.substr(104, text.length);
        }else{
          scope.detailDisplayed = text;
        }
        elem.on('mouseover', function(){
          if(scope.detailHover != ""){
            $(elem[0].childNodes[0]).popover({
              html: true,
              placement: 'bottom',
              template: '<div class="popover" role="tooltip" style="margin-top:10px;">'+
                            '<div class="arrow" style="margin-left: 30px"></div><h3 class="popover-title"></h3><div class="popover-content">Testing</div></div>'
            });
          }
        });
      }
    }
  }

  angular.module('aca1095BUiAppCW')
    .directive("commentPopup", commentPopup);
}());
