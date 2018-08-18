/**
 * Created by speta on 11/12/2015.
 */

'use strict';

angular.module('aca1095BUiAppCW').controller('ADashboardCtrl',['$scope','$state','$element',function($scope,$state,$element){

  $('.admindashtitle').focus();

  /*screen labels*/
  $scope.label={
    titleAction : "Administration",
    titleDescription : "Dashboard",
    search:"Search",
    userdatabase:"User Database",
    create:"Create",
    newuser:"New User",
    searchinfo:"Search for existing users in the Caseworker portal",
    createinfo:"Add new users by role to the Caseworker portal"
  };

  $scope.arialabel={
    title:"Administration Dashboard allows to search for existing user and create a new user",
    search:"Click to Search for the existing User",
    create:"Click to Create a New User"
  };

  $scope.goto_create_user = function(){
    $state.go('state.a-create-user');
  };

  $scope.goto_search_user = function(){
    $state.go('state.a-search-user');
  };

  $element.on('$destroy', function() {
    $scope = "";
    $('.circle-dashboard').eq(0).blur();
    $('.circle-dashboard').eq(1).blur();
  });

}]);
