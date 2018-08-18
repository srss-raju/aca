'use strict';
angular.module('aca1095BUiAppCW')
  .filter('filters', function () {
    /* array is first argument, each addiitonal argument is prefixed by a ":" in filter markup*/
    return function (dataArray, searchTerm, properties) {
      if (!dataArray) {
        return;
      }
      /* when term is cleared, return full array*/
      if (!searchTerm) {
        return dataArray;
      } else {
        /* otherwise filter the array */
        var term = searchTerm.toLowerCase();
        return dataArray.filter(function (item) {
          var status=false;
          for(var i=0;i<properties.length;i++){
            var matchIndex = item[properties[i]].toLowerCase().indexOf(term);
            if(matchIndex>-1){
              status=true;
              break;
            }
          }
          if(status){
            return true;
          }else{
            return false;
          }
        });
      }
    }
  });
