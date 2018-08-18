/**
 * Created by speta on 3/16/2016.
 */


'use strict';

  describe("Controller: termsAndConditionsCtrl", function() {

    var tandccontroller,
      scope,
      rootscope,
      state,
      env,
      mlocation;


    //initializing the controller's module, controller
      beforeEach( module('aca1095BUiAppCW'));

       beforeEach(inject(function ($controller,$rootScope,$injector,$location) {
          scope = $rootScope.$new();
          state = jasmine.createSpyObj('$state', ['go']);
          env = $injector.get('ENV');
          rootscope = $rootScope;
          mlocation = $location;
          tandccontroller = $controller('termsAndConditionsCtrl', {
            $scope : scope,
            $state : state,
            ENV:env,
            $location: mlocation
          });
      }));

    describe('check labels',function(){
      it('aria label check', function(){
        expect(tandccontroller.ariaLabel.title).toEqual('Terms and Conditions');
        expect(tandccontroller.ariaLabel.agree).toEqual('I agree to the Terms and conditions');
        expect(tandccontroller.ariaLabel.cancel).toEqual('I do not agree to the Terms and conditions');
      });
    });

    describe('check controller methods',function(){
      it('on click of cancel should logout from the application', function() {
        /*spyOn(mlocation,'path');
        tandccontroller.cancel();
        rootscope.$digest();
        expect(mlocation.path).toHaveBeenCalledWith(env.logout);*/
      });

      it('should transition to new profile registration', function() {
        tandccontroller.goToNewProfile();
        expect(state.go).toHaveBeenCalledWith('state.new-profile-registration');
      });
    });

  });
