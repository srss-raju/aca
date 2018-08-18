/**
 * Created by pchavan on 12/21/2015.
 */

'use strict';

(function () {
  function dateFilter($filter){
    return function(input, format, type) {
      if (input === null || input === "") {
        if(type){
          return "No Data";
        }else{
          return "";
        }

      }
      return $filter('date')(new Date(input), 'MM/dd/yyyy');

    };
  }

  angular.module('aca1095BUiAppCW').filter('dateFilter', ['$filter',dateFilter]);
}());
