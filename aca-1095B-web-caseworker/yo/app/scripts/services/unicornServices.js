/**
 * Created by pchavan on 3/15/2016.
 */
'use strict';
(function(){

  function unicornServices($http, ENV, $q){
    var unicornService = {};
    var url = ENV.domain;
    var deferred = $q.defer();
    unicornService.updateExpiredPassword = function(data){
      //return $http.get('stubs/unicornUpdateExpiredPassword.json')
      return $http.post(url + '/unicorn/api/idm/user/resetExpiredPswd', JSON.stringify(data), {header : 'Content-Type: application/json'})
        .then(function(response) {
          if (typeof response.data === 'object') {
            return response.data;
          } else {
            // invalid response
            return $q.reject(response.data);
          }
        }, function(response) {
          // something went wrong
          return $q.reject(response.data);
        });
    };

    return unicornService;
  }

  angular.module('aca1095BUiAppCW')
    .service('unicornServices', ['$http', 'ENV', '$q', unicornServices]);

}());
