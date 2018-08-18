/**
 * Created by fasaleem on 1/4/16.
 */

'use strict';

angular.module('aca1095BUiAppCW')
  .controller('termsAndConditionsCtrl', ['$scope', '$state','ENV', function ($scope, $state,ENV) {

    var tc = this;

      tc.ariaLabel = {
        title:'Terms and Conditions',
        agree : 'I agree to the Terms and conditions',
        cancel: 'I do not agree to the Terms and conditions'
      };

      $('.pagetitle').focus();

      tc.cancel = function() {
        location.href = ENV.logout;
      };

      tc.goToNewProfile = function() {
        $state.go('state.new-profile-registration');
      };
}]);
