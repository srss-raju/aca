/**
 * Created by speta on 11/24/2015.
 */

'use strict';

angular.module('aca1095BUiAppCW')
  .factory('CommonService',function(){
  var commonService={};
    commonService.changePasswordFlag = false;
    commonService.userInfo = {};
    commonService.setSuccessMessage = function(message){
      commonService.successMessage = message;
    };

    commonService.getSuccessMessage = function(){
      return commonService.successMessage;
    };

    return commonService;
});
