/**
 * Created by speta on 11/10/2015.
 */
'use strict';

angular.module('aca1095BUiAppCW').directive('ellipsisPopover',function(){
  return{
    restrict:'EA',
    scope:{
      display:'=',
      hoverlength:"="
    },
    templateUrl:'views/templates/ellipsisPopover.html',
    link:function(scope,elem,attr) {
      var text = angular.copy(scope.display);
      scope.displayvalue="";
      scope.hoverFlag=false;
      if(text.length>scope.hoverlength){
        scope.displayvalue = text.substr(0,scope.hoverlength) + " ...";
        elem.on('mouseenter',function(){
          scope.hoverFlag=true;
          if(!scope.$$phase){
            scope.$apply();
          }
        });
        elem.on('mouseleave',function(){
          scope.hoverFlag=false;
          if(!scope.$$phase){
            scope.$apply();
          }
        });
      }
      else{
        scope.hoverFlag=false;
        scope.displayvalue = text;
      }
    }
  };
});
