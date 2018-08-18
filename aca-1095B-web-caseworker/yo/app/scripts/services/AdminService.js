/**
 * Created by speta on 11/16/2015.
 */

'use strict';
  angular.module('aca1095BUiAppCW').factory('AdminService',function($http,$modal,ENV,$q,UrlService,UserService){
    var adminService={};

    adminService.getUserDetails = function(){
      var deferred = $q.defer();
      var headers = {'content-type': 'application/json'};
      $http({'method': 'GET', 'url': UrlService.getService('GET_COMPANY_USERS')+'?time='+ new Date().getTime(),'headers':headers })
        .success(function (response) {
          try{
            if (response.status === '200') {
              deferred.resolve({'data': _.findWhere(response.result.Result,{'name':'All'}).List, 'message': ''});
            } else {
              deferred.resolve({'error': 'ok', 'message': response.errormsg});
            }
          }catch(e){
            deferred.resolve({'error': 'ok', 'message': 'Getting state users failed !'});
          }
        })
        .error(function (response,status) {
          deferred.resolve({'error': 'ok', 'message': UserService.identifyErrorMessage(status, 'Getting state users failed !'),'response':response});
        });
      return deferred.promise;
    };

    adminService.openModal = function(option){
      adminService.modalBx = $modal.open(option);
    };

    adminService.cancel_modal = function(){
      adminService.modalBx.dismiss('cancel');
    };

    return adminService;
  });
