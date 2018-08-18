/**
 * Created by pchavan on 4/6/2016.
 */
'use strict';
angular.module('aca1095BUiAppCW')
  .filter('searchFilter', function () {
    /* array is first argument, each addiitonal argument is prefixed by a ":" in filter markup*/
    function radioButtonSelect(filteredArray, radioButton){
      if(!radioButton){
        return filteredArray;
      }else{
        return filteredArray.filter(function (item) {
          return (item.recordVisible === radioButton.recordVisible);
        });
      }
    }
    function getFilteredData(dataArray, searchTerm, properties){
     /* console.log("getFilteredData",dataArray, searchTerm, properties);*/
      //return dataArray;
      var term = null;
      var isDate = false;
      if(searchTerm.indexOf('/') > -1){
        term = new Date(searchTerm);
        isDate = term instanceof Date;
      }else{
        term = angular.copy(searchTerm);
      }
      return dataArray.filter(function (item) {
        var status=false;
        for(var i=0;i<properties.length;i++){
          if(!item[properties[i]]){
            status=false;
            //break;
          }else{
            var matchIndex = null;
            if(isDate){
              var itemDate = new Date(item[properties[i]]);
              if(itemDate.getMonth() === term.getMonth() && itemDate.getYear() === term.getYear() && itemDate.getDate() === term.getDate()){
                status=true;
                break;
              }
            }else{
              if(properties[i].indexOf('Date') < 0){
                matchIndex = item[properties[i]].indexOf(term);
                if(matchIndex>-1){
                  status=true;
                  break;
                }
              }
            }
          }
        }
        return status;
      });
    }
    return function (dataArray, searchTerm, properties, radioButton) {
      if (!dataArray) {
        return;
      }
      /* when term is cleared, return full array*/
      if (!searchTerm) {
        return radioButtonSelect(dataArray, radioButton);
      } else {
        /* otherwise filter the array */
        var filteredArray = getFilteredData(dataArray, searchTerm, properties);
        return radioButtonSelect(filteredArray, radioButton);
      }
    };
  });
