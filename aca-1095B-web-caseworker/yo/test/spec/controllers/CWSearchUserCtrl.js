'use strict';

describe('Controller: CWSearchUserCtrl',function(){

  var cwsearchuser,
      scope,
      rootscope,
      state,
      CWService;

  beforeEach(function(){
    module('aca1095BUiAppCW');
  });

  beforeEach(function(){
    module(function($provide){
      $provide.value('mockCWService', {
        setFormIdforView: function (formId) {
          console.log(formId);
        },
        getDetail:function(searchmodal){
          var data = {};
          var testcase1 = {userFname:null, userLname:null, ssn:'000000601', tin:null, dob:null, taxYear:'2015'},
              testcase2 = {userFname:null, userLname:null, ssn:null, tin:'987654321', dob:null, taxYear:'2015'},
              testcase3 = {userFname:null, userLname:'Nichols', ssn:null, tin:null, dob:'1977-03-15', taxYear:'2015'},
              testcase4 = {userFname:'', userLname:'Nichols', ssn:null, tin:null, dob:'1976-03-15', taxYear:'2015'},
              testcase5 = {userFname:null, userLname:null, ssn:'000000611', tin:null, dob:null, taxYear:'2015'},
              testcase6 = {userFname:null, userLname:null, ssn:null, tin:'987654331', dob:null, taxYear:'2015'},
              testcase7 = {userFname:'Maria', userLname:'Nichols', ssn:'000000601', tin:'987654321', dob:'1977-03-15', taxYear:'2015'};

          if(searchmodal.userFname === testcase1.userFname && searchmodal.userLname === testcase1.userLname && searchmodal.ssn === testcase1.ssn &&
             searchmodal.tin === testcase1.tin && searchmodal.dob === testcase1.dob && searchmodal.taxYear === testcase1.taxYear){
            data = [{"filerID": "ARDHSDSS_191020333", "recipientFirstName": "Maria", "recipientLastName": "Nichols", "recipientDob": "03/15/1977", "recipientSsn": "xxx-xx-0601", "recipientTin": "xxx-xx-4321", "recepientState": "AR", "filerStatus": "R"}];
          }else if(searchmodal.userFname === testcase2.userFname && searchmodal.userLname === testcase2.userLname && searchmodal.ssn === testcase2.ssn &&
            searchmodal.tin === testcase2.tin && searchmodal.dob === testcase2.dob && searchmodal.taxYear === testcase2.taxYear){
            data = [{"filerID": "ARDHSDSS_191020333", "recipientFirstName": "Maria", "recipientLastName": "Nichols", "recipientDob": "03/15/1977", "recipientSsn": "xxx-xx-0601", "recipientTin": "xxx-xx-4321", "recepientState": "AR", "filerStatus": "R"}];
          }else if(searchmodal.userFname === testcase3.userFname && searchmodal.userLname === testcase3.userLname && searchmodal.ssn === testcase3.ssn &&
            searchmodal.tin === testcase3.tin && searchmodal.dob === testcase3.dob && searchmodal.taxYear === testcase3.taxYear){
            data = [{"filerID": "ARDHSDSS_191020333", "recipientFirstName": "Maria", "recipientLastName": "Nichols", "recipientDob": "03/15/1977", "recipientSsn": "xxx-xx-0601", "recipientTin": "xxx-xx-4321", "recepientState": "AR", "filerStatus": "R"}];
          }else if(searchmodal.userFname === testcase4.userFname && searchmodal.userLname === testcase4.userLname && searchmodal.ssn === testcase4.ssn &&
            searchmodal.tin === testcase4.tin && searchmodal.dob === testcase4.dob && searchmodal.taxYear === testcase4.taxYear){
            data = "";
          }else if(searchmodal.userFname === testcase5.userFname && searchmodal.userLname === testcase5.userLname && searchmodal.ssn === testcase5.ssn &&
            searchmodal.tin === testcase5.tin && searchmodal.dob === testcase5.dob && searchmodal.taxYear === testcase5.taxYear){
            data = "";
          }else if(searchmodal.userFname === testcase6.userFname && searchmodal.userLname === testcase6.userLname && searchmodal.ssn === testcase6.ssn &&
            searchmodal.tin === testcase6.tin && searchmodal.dob === testcase6.dob && searchmodal.taxYear === testcase6.taxYear){
            data = "";
          }else if(searchmodal.userFname === testcase7.userFname && searchmodal.userLname === testcase7.userLname && searchmodal.ssn === testcase7.ssn &&
            searchmodal.tin === testcase7.tin && searchmodal.dob === testcase7.dob && searchmodal.taxYear === testcase7.taxYear){
            data = [{"filerID": "ARDHSDSS_191020333", "recipientFirstName": "Maria", "recipientLastName": "Nichols", "recipientDob": "03/15/1977", "recipientSsn": "xxx-xx-0601", "recipientTin": "xxx-xx-4321", "recepientState": "AR", "filerStatus": "R"}];
          }else{
            data = "";
          }
          return {
            then: function(callback) {return callback(data);}
          };
        }
      });
    });
  });

  beforeEach(inject(function($controller,$rootScope,mockCWService){
      scope = $rootScope.$new;
      rootscope =$rootScope;
      state = jasmine.createSpyObj('$state', ['go']);
      CWService = mockCWService;
      cwsearchuser = $controller('CWSearchUserCtrl', {
          $scope : scope,
          $state : state,
          CWService : CWService
        });

    cwsearchuser.searchuserform = {
        $valid: true,
        $pristine: false,
        $dirty: true,
        $setPristine: function () {
          this.$dirty = false;
          this.$valid = true;
        },
        ssn:{
          $valid: true,
          $dirty: true
        },
        tin:{
          $valid: true,
          $dirty: true
        },
        year:{
          $valid: true,
          $dirty: true
        },
        lastName:{
          $valid: true,
          $dirty: true
        },
        dob:{
          $valid: true,
          $dirty: true
        }
      };
    cwsearchuser.searchmodal = {
      userFname:null,
      userLname:null,
      ssn:null,
      tin:null,
      dob:null,
      taxYear:null
    };

    }));

  describe('test the screen labels and aria labels and intialization of variables',function(){
    it('check the screen labels',function(){
      console.log('check the screen labels');
      expect(cwsearchuser.label.titleAction).toEqual('Search');
      expect(cwsearchuser.label.titleDescription).toEqual('Recipient');
      expect(cwsearchuser.label.infomessage).toEqual('The 3 required field combinations are SSN + Tax Year or TIN + Tax Year or Last Name + Date of Birth + Tax Year');
      expect(cwsearchuser.label.name).toEqual('Name');
      expect(cwsearchuser.label.ssn).toEqual('SSN');
      expect(cwsearchuser.label.dob).toEqual('DOB');
      expect(cwsearchuser.label.tin).toEqual('TIN');
      expect(cwsearchuser.label.form).toEqual('1095-B Form');
      expect(cwsearchuser.label.view).toEqual('View');
      expect(cwsearchuser.label.viewHover).toEqual('View 1095-B form is unavailable because user is a dependent');
    });

    it('check the aria labels',function(){
      console.log('check the aria labels');
      expect(cwsearchuser.cwSearchUser.title).toEqual('Search for the recipient by entering 3 required field combinations, which are SSN + Tax Year or TIN + Tax Year or Last Name + Date of Birth + Tax Year');
      expect(cwsearchuser.cwSearchUser.finduser).toEqual('Find the record of');
      expect(cwsearchuser.cwSearchUser.userrecord).toEqual('The record of');
      expect(cwsearchuser.cwSearchUser.disableuser).toEqual('is unavailable because user is a dependent');
      expect(cwsearchuser.cwSearchUser.firstName).toEqual('Enter the First Name, this is an optional field');
      expect(cwsearchuser.cwSearchUser.lastName).toEqual('Enter the Last Name');
      expect(cwsearchuser.cwSearchUser.ssn).toEqual('Enter the Social Security Number');
      expect(cwsearchuser.cwSearchUser.dob).toEqual('Enter the Date of Birth');
      expect(cwsearchuser.cwSearchUser.tin).toEqual('Enter the Tax Identification Number');
      expect(cwsearchuser.cwSearchUser.year).toEqual('Select Tax Year, use arrow up and arrow down keys to select the year');
    });

    it('check the intialization of the variables',function(){
      console.log('check the intialization of the variables');
      expect(cwsearchuser.disableSearch).toBeTruthy();
      expect(cwsearchuser.showDetails).toBeFalsy();
      expect(cwsearchuser.searchmodel.userFname).toBeNull();
      expect(cwsearchuser.searchmodel.userLname).toBeNull();
      expect(cwsearchuser.searchmodel.ssn).toBeNull();
      expect(cwsearchuser.searchmodel.tin).toBeNull();
      expect(cwsearchuser.searchmodel.dob).toBeNull();
      expect(cwsearchuser.searchmodel.taxYear).toEqual(cwsearchuser.taxYears[0].value);
      expect(cwsearchuser.taxYears).toEqual([{"value": "2016"}, {"value": "2015"}]);
      expect(cwsearchuser.userNotFound).toBeFalsy();
      expect(cwsearchuser.errorMessage).toEqual('Sorry, no record has been found.');
      expect(cwsearchuser.spinnerShow).toBeFalsy();
    });
  });

  describe('check controller methods',function(){

    it('check the clear search functionality',function(){
      console.log('check the clear search functionality');
        rootscope.$apply();
        spyOn(cwsearchuser.searchuserform,'$setPristine');
        cwsearchuser.clearSearch();
        expect(cwsearchuser.searchmodel.userFname).toBeNull();
        expect(cwsearchuser.searchmodel.userLname).toBeNull();
        expect(cwsearchuser.searchmodel.ssn).toBeNull();
        expect(cwsearchuser.searchmodel.tin).toBeNull();
        expect(cwsearchuser.searchmodel.dob).toBeNull();
      expect(cwsearchuser.searchmodel.taxYear).toEqual(cwsearchuser.taxYears[0].value);
        expect(cwsearchuser.caseworkerData).toEqual({});
        expect(cwsearchuser.showDetails).toBeFalsy();
        expect(cwsearchuser.disableSearch).toBeTruthy();
        expect(cwsearchuser.searchuserform.$setPristine).toHaveBeenCalledWith(true);
        expect(cwsearchuser.userNotFound).toBeFalsy();
    });

    describe('Check getDetail service',function(){
      console.log('Check getDetail service');
      it('Check search functionality by passing ssn and taxYear',function(){
        var searchmodal = {userFname:null, userLname:null, ssn:'000000601', tin:null, dob:null, taxYear:'2015'};
        spyOn(CWService,'getDetail').and.callThrough();
        cwsearchuser.getDetail(searchmodal);
        expect(CWService.getDetail).toHaveBeenCalled();
        expect(cwsearchuser.spinnerShow).toBeFalsy();
        expect(cwsearchuser.userNotFound).toBeFalsy();
        expect(cwsearchuser.showDetails).toBeTruthy();
      });

      it('Check search functionality by passing tin and taxYear',function(){
        var searchmodal = {userFname:null, userLname:null, ssn:null, tin:'987654321', dob:null, taxYear:'2015'};
        spyOn(CWService,'getDetail').and.callThrough();
        cwsearchuser.getDetail(searchmodal);
        expect(CWService.getDetail).toHaveBeenCalled();
        expect(cwsearchuser.spinnerShow).toBeFalsy();
        expect(cwsearchuser.userNotFound).toBeFalsy();
        expect(cwsearchuser.showDetails).toBeTruthy();
      });

      it('Check search functionality by passing user last name,dob and taxYear',function(){
        var searchmodal = {userFname:null, userLname:'Nichols', ssn:null, tin:null, dob:'1977-03-15', taxYear:'2015'};
        spyOn(CWService,'getDetail').and.callThrough();
        cwsearchuser.getDetail(searchmodal);
        expect(CWService.getDetail).toHaveBeenCalled();
        expect(cwsearchuser.spinnerShow).toBeFalsy();
        expect(cwsearchuser.userNotFound).toBeFalsy();
        expect(cwsearchuser.showDetails).toBeTruthy();
      });

      it('Check search functionality by passing user last name,incorrect dob and taxYear',function(){
        var searchmodal = {userFname:'', userLname:'Nichols', ssn:null, tin:null, dob:'1976-03-15', taxYear:'2015'};
        spyOn(CWService,'getDetail').and.callThrough();
        cwsearchuser.getDetail(searchmodal);
        expect(CWService.getDetail).toHaveBeenCalled();
        expect(cwsearchuser.spinnerShow).toBeFalsy();
        expect(cwsearchuser.userNotFound).toBeTruthy();
        expect(cwsearchuser.showDetails).toBeFalsy();
      });

      it('Check search functionality by passing incorrect ssn and taxYear',function(){
        var searchmodal = {userFname:null, userLname:null, ssn:'000000611', tin:null, dob:null, taxYear:'2015'};
        spyOn(CWService,'getDetail').and.callThrough();
        cwsearchuser.getDetail(searchmodal);
        expect(CWService.getDetail).toHaveBeenCalled();
        expect(cwsearchuser.spinnerShow).toBeFalsy();
        expect(cwsearchuser.userNotFound).toBeTruthy();
        expect(cwsearchuser.showDetails).toBeFalsy();
      });

      it('Check search functionality by passing user incorrect tin and taxYear',function(){
        var searchmodal = {userFname:null, userLname:null, ssn:null, tin:'987654331', dob:null, taxYear:'2015'};
        spyOn(CWService,'getDetail').and.callThrough();
        cwsearchuser.getDetail(searchmodal);
        expect(CWService.getDetail).toHaveBeenCalled();
        expect(cwsearchuser.spinnerShow).toBeFalsy();
        expect(cwsearchuser.userNotFound).toBeTruthy();
        expect(cwsearchuser.showDetails).toBeFalsy();
      });

      it('Check search functionality by passing user firstname,lastname, ssn, tin, dob and taxYear',function(){
        var searchmodal = {userFname:'Maria', userLname:'Nichols', ssn:'000000601', tin:'987654321', dob:'1977-03-15', taxYear:'2015'};
        spyOn(CWService,'getDetail').and.callThrough();
        cwsearchuser.getDetail(searchmodal);
        expect(CWService.getDetail).toHaveBeenCalled();
        expect(cwsearchuser.spinnerShow).toBeFalsy();
        expect(cwsearchuser.userNotFound).toBeFalsy();
        expect(cwsearchuser.showDetails).toBeTruthy();
      });
    });

    describe('save button enable functionality check',function(){

      describe('ssn year check',function(){

        it('valid form,valid ssn and valid year check',function(){
          console.log('valid form,valid ssn and valid year check');
          cwsearchuser.searchuserform = {
            $valid: true, $dirty: true,
            ssn: {$valid: true, $dirty: true, $modelValue: true},
            tin:{$valid: null, $dirty: null},
            dob:{$valid: null, $dirty: null},
            lastName:{$valid: null, $dirty: null},
            year:{$valid: true, $dirty: true}};
          cwsearchuser.changeField();
          expect(cwsearchuser.disableSearch).toBeFalsy();
        });

        it('valid form,valid ssn and invalid year check',function(){
          console.log('valid form,valid ssn and invalid year check');
          cwsearchuser.searchuserform = {
            $valid: true, $dirty: true,
            ssn:{$valid: true, $dirty: true},
            tin:{$valid: null, $dirty: null},
            dob:{$valid: null, $dirty: null},
            lastName:{$valid: null, $dirty: null},
            year:{$valid: false, $dirty: true}  };
          cwsearchuser.changeField();
          expect(cwsearchuser.disableSearch).toBeTruthy();
        });

        it('valid form,invalid ssn and valid year check',function(){
          console.log('valid form,invalid ssn and valid year check');
          cwsearchuser.searchuserform = {
            $valid: true, $dirty: true,
            ssn:{$valid: false, $dirty: true},
            tin:{$valid: null, $dirty: null},
            dob:{$valid: null, $dirty: null},
            lastName:{$valid: null, $dirty: null},
            year:{$valid: true, $dirty: true}};
          cwsearchuser.changeField();
          expect(cwsearchuser.disableSearch).toBeTruthy();
        });

        it('invalid form,valid ssn and valid year check',function(){
          console.log('invalid form,valid ssn and valid year check');
          cwsearchuser.searchuserform = {
          $valid: false, $dirty: true,
          ssn:{$valid: true, $dirty: true},
          tin:{$valid: null, $dirty: null},
          dob:{$valid: null, $dirty: null},
          lastName:{$valid: null, $dirty: null},
          year:{$valid: true, $dirty: true}};
          cwsearchuser.changeField();
          expect(cwsearchuser.disableSearch).toBeTruthy();
        });

        it('invalid form,invalid ssn and valid year check',function(){
          console.log('invalid form,invalid ssn and valid year check');
          cwsearchuser.searchuserform = {
            $valid: false, $dirty: true,
            ssn:{$valid: false, $dirty: true},
            tin:{$valid: null, $dirty: null},
            dob:{$valid: null, $dirty: null},
            lastName:{$valid: null, $dirty: null},
            year:{$valid: true, $dirty: true}};
          cwsearchuser.changeField();
          expect(cwsearchuser.disableSearch).toBeTruthy();
        });

        it('invalid form,valid ssn and invalid year check',function(){
          console.log('invalid form,valid ssn and invalid year check');
          cwsearchuser.searchuserform = {
            $valid: false, $dirty: true,
            ssn:{$valid: true, $dirty: true},
            tin:{$valid: null, $dirty: null},
            dob:{$valid: null, $dirty: null},
            lastName:{$valid: null, $dirty: null},
            year:{$valid: false, $dirty: true}};
          cwsearchuser.changeField();
          expect(cwsearchuser.disableSearch).toBeTruthy();
        });

        it('valid form,invalid ssn and invalid year check',function(){
          console.log('valid form,invalid ssn and invalid year check');
          cwsearchuser.searchuserform = {
            $valid: true, $dirty: true,
            ssn:{$valid: false, $dirty: true},
            tin:{$valid: null, $dirty: null},
            dob:{$valid: null, $dirty: null},
            lastName:{$valid: null, $dirty: null},
            year:{$valid: false, $dirty: true}};
          cwsearchuser.changeField();
          expect(cwsearchuser.disableSearch).toBeTruthy();
        });

        it('invalid form,invalid ssn and invalid year check',function(){
          console.log('invalid form,invalid ssn and invalid year check');
          cwsearchuser.searchuserform = {
            $valid: false, $dirty: true,
            ssn:{$valid: false, $dirty: true},
            tin:{$valid: null, $dirty: null},
            dob:{$valid: null, $dirty: null},
            lastName:{$valid: null, $dirty: null},
            year:{$valid: false, $dirty: true}};
          cwsearchuser.changeField();
          expect(cwsearchuser.disableSearch).toBeTruthy();
        });

      });

      describe('tin year check',function(){

        it('valid form,valid tin and valid year check',function(){
          console.log('valid form,valid tin and valid year check');
          cwsearchuser.searchuserform = {
            $valid: true, $dirty: true,
            ssn:{$valid:null, $dirty:null},
            year:{$valid: true, $dirty: true},
            tin: {$valid: true, $dirty: true, $modelValue: true},
            dob:{$valid: null, $dirty: null},
            lastName:{$valid: null, $dirty: null},};
          cwsearchuser.changeField();
          expect(cwsearchuser.disableSearch).toBeFalsy();
        });

        it('valid form,valid tin and invalid year check',function(){
          console.log('valid form,valid tin and invalid year check');
          cwsearchuser.searchuserform = {
            $valid: true, $dirty: true,
            ssn:{$valid: null, $dirty: null},
            tin:{$valid: true, $dirty: true},
            dob:{$valid: null, $dirty: null},
            lastName:{$valid: null, $dirty: null},
            year:{$valid: false, $dirty: true}  };
          cwsearchuser.changeField();
          expect(cwsearchuser.disableSearch).toBeTruthy();
        });

        it('valid form,invalid tin and valid year check',function(){
          console.log('valid form,invalid tin and valid year check');
          cwsearchuser.searchuserform = {
            $valid: true, $dirty: true,
            ssn:{$valid: null, $dirty: null},
            tin:{$valid: false, $dirty: true},
            dob:{$valid: null, $dirty: null},
            lastName:{$valid: null, $dirty: null},
            year:{$valid: true, $dirty: true}};
          cwsearchuser.changeField();
          expect(cwsearchuser.disableSearch).toBeTruthy();
        });

        it('invalid form,valid tin and valid year check',function(){
          console.log('invalid form,valid tin and valid year check');
          cwsearchuser.searchuserform = {
            $valid: false, $dirty: true,
            ssn:{$valid: null, $dirty: null},
            tin:{$valid: true, $dirty: true},
            dob:{$valid: null, $dirty: null},
            lastName:{$valid: null, $dirty: null},
            year:{$valid: true, $dirty: true}};
          cwsearchuser.changeField();
          expect(cwsearchuser.disableSearch).toBeTruthy();
        });

        it('invalid form,invalid tin and valid year check',function(){
          console.log('invalid form,invalid tin and valid year check');
          cwsearchuser.searchuserform = {
            $valid: false, $dirty: true,
            ssn:{$valid: null, $dirty: null},
            tin:{$valid: false, $dirty: true},
            dob:{$valid: null, $dirty: null},
            lastName:{$valid: null, $dirty: null},
            year:{$valid: true, $dirty: true}};
          cwsearchuser.changeField();
          expect(cwsearchuser.disableSearch).toBeTruthy();
        });

        it('invalid form,valid tin and invalid year check',function(){
          console.log('invalid form,valid tin and invalid year check');
          cwsearchuser.searchuserform = {
            $valid: false, $dirty: true,
            ssn:{$valid: null, $dirty: null},
            tin:{$valid: true, $dirty: true},
            dob:{$valid: null, $dirty: null},
            lastName:{$valid: null, $dirty: null},
            year:{$valid: false, $dirty: true}};
          cwsearchuser.changeField();
          expect(cwsearchuser.disableSearch).toBeTruthy();
        });

        it('valid form,invalid tin and invalid year check',function(){
          console.log('valid form,invalid tin and invalid year check');
          cwsearchuser.searchuserform = {
            $valid: true, $dirty: true,
            ssn:{$valid: null, $dirty: null},
            tin:{$valid: false, $dirty: true},
            dob:{$valid: null, $dirty: null},
            lastName:{$valid: null, $dirty: null},
            year:{$valid: false, $dirty: true}};
          cwsearchuser.changeField();
          expect(cwsearchuser.disableSearch).toBeTruthy();
        });

        it('invalid form,invalid tin and invalid year check',function(){
          console.log('invalid form,invalid tin and invalid year check');
          cwsearchuser.searchuserform = {
            $valid: false, $dirty: true,
            ssn:{$valid: null, $dirty: null},
            tin:{$valid: false, $dirty: true},
            dob:{$valid: null, $dirty: null},
            lastName:{$valid: null, $dirty: null},
            year:{$valid: false, $dirty: true}};
          cwsearchuser.changeField();
          expect(cwsearchuser.disableSearch).toBeTruthy();
        });

      });

      describe('lastname,dob,year check',function(){

        it('valid form,valid lastname, valid dob,valid taxYear check',function(){
         console.log('valid form,valid lastname, valid dob,valid taxyear check');
         cwsearchuser.searchuserform = {
           $valid: true, $dirty: true,
           ssn:{$valid: null, $dirty: null},
           tin:{$valid: null, $dirty: null},
           dob: {$valid: true, $dirty: true, $modelValue: true},
           lastName: {$valid: true, $dirty: true, $modelValue: true},
           year:{$valid: true, $dirty: true}};
          cwsearchuser.changeField();
          expect(cwsearchuser.disableSearch).toBeFalsy();
       });

        it('valid form,valid lastname,valid dob,invalid taxYear check',function(){
         console.log('valid form,valid lastname,valid dob,invalid taxYear check');
         cwsearchuser.searchuserform = {
           $valid: true, $dirty: true,
           ssn:{$valid: null, $dirty: null},
           tin:{$valid: null, $dirty: null},
           dob:{$valid: true, $dirty: true},
           lastName:{$valid: true, $dirty: true},
           year:{$valid: false, $dirty: true
           }
         };
         cwsearchuser.changeField();
         expect(cwsearchuser.disableSearch).toBeTruthy();
       });

        it('valid form,valid lastname,invalid dob,valid taxYear check',function(){
          console.log('valid form,valid lastname, invalid dob,valid taxYear check');
          cwsearchuser.searchuserform = {
            $valid: true, $dirty: true,
            ssn:{$valid: null, $dirty: null},
            tin:{$valid: null, $dirty: null},
            dob:{$valid: false, $dirty: true},
            lastName:{$valid: true, $dirty: true},
            year:{$valid: true, $dirty: true
            }
          };
          cwsearchuser.changeField();
          expect(cwsearchuser.disableSearch).toBeTruthy();
        });

        it('valid form,invalid lastname,valid dob,valid taxYear check', function () {
        console.log('valid form,invalid lastname,valid dob,valid taxYear check');
        cwsearchuser.searchuserform = {
          $valid: true, $dirty: true,
          ssn:{$valid: null, $dirty: null},
          tin:{$valid: null, $dirty: null},
          dob:{$valid:true, $dirty: true},
          lastName:{$valid: false, $dirty: true},
          year:{$valid: true, $dirty: true
          }
        };
        cwsearchuser.changeField();
        expect(cwsearchuser.disableSearch).toBeTruthy();
       });

        it('invalid form,valid lastname,valid dob,valid taxYear check',function(){
          console.log('invalid form,valid lastname,valid dob,valid taxYear check');
          cwsearchuser.searchuserform = {
            $valid: false, $dirty: true,
            ssn:{$valid: null, $dirty: null},
            tin:{$valid: null, $dirty: null},
            dob:{$valid:true, $dirty: true},
            lastName:{$valid: true, $dirty: true},
            year:{$valid: true, $dirty: true
            }
          };
          cwsearchuser.changeField();
          expect(cwsearchuser.disableSearch).toBeTruthy();
       });

        it('valid form,valid lastname,invalid dob,invalid taxYear check',function(){
         console.log('valid form,valid lastname,invalid dob,invalid taxYear check');
         cwsearchuser.searchuserform = {
           $valid: true, $dirty: true,
           ssn:{$valid: null, $dirty: null},
           tin:{$valid: null, $dirty: null},
           dob:{$valid: false, $dirty: true},
           lastName:{$valid: true, $dirty: true},
           year:{$valid: false, $dirty: true
          }
         };
         cwsearchuser.changeField();
         expect(cwsearchuser.disableSearch).toBeTruthy();
       });

        it('valid form,invalid lastname, valid dob,invalid taxYear check',function(){
         console.log('valid form,invalid lastname, valid dob,invalid taxYear check');
         cwsearchuser.searchuserform = {
           $valid: true, $dirty: true,
           ssn:{$valid: null, $dirty: null},
           tin:{$valid: null, $dirty: null},
           dob:{$valid:true, $dirty: true},
           lastName:{$valid: false, $dirty: true},
           year:{$valid: false, $dirty: true
          }};
         cwsearchuser.changeField();
         expect(cwsearchuser.disableSearch).toBeTruthy();
       });

        it('invalid form,valid lastname, valid dob,invalid taxYear check',function(){
         console.log('invalid form,valid lastname, valid dob,invalid taxYear check');
         cwsearchuser.searchuserform = {
           $valid: false, $dirty: true,
           ssn:{$valid: null, $dirty: null},
           tin:{$valid: null, $dirty: null},
           dob:{$valid:true, $dirty: true},
           lastName:{$valid: true, $dirty: true},
           year:{$valid: false, $dirty: true}
         };
         cwsearchuser.changeField();
         expect(cwsearchuser.disableSearch).toBeTruthy();
       });

        it('valid form,invalid lastname, invalid dob,valid taxYear check',function(){
         console.log('valid form,invalid lastname, invalid dob,valid taxYear check');
         cwsearchuser.searchuserform = {
           $valid: true, $dirty: true,
           ssn:{$valid: null, $dirty: null},
           tin:{$valid: null, $dirty: null},
           dob:{$valid:false, $dirty: true},
           lastName:{$valid: false, $dirty: true},
           year:{$valid:true, $dirty: true}
         };
         cwsearchuser.changeField();
         expect(cwsearchuser.disableSearch).toBeTruthy();});

        it('invalid form,valid lastname, invalid dob,valid taxYear check',function(){
         console.log('invalid form,valid lastname, invalid dob,valid taxYear check');
         cwsearchuser.searchuserform = {
           $valid: false, $dirty: true,
           ssn:{$valid: null, $dirty: null},
           tin:{$valid: null, $dirty: null},
           dob:{$valid:false, $dirty: true},
           lastName:{$valid: true, $dirty: true},
           year:{$valid:true, $dirty: true}};
         cwsearchuser.changeField();
         expect(cwsearchuser.disableSearch).toBeTruthy();});

        it('invalid form,invalid lastname,valid dob,valid taxYear check', function () {
         console.log('invalid form,invalid lastname,valid dob,valid taxYear check');
         cwsearchuser.searchuserform = {
           $valid: false, $dirty: true,
           ssn:{$valid: null, $dirty: null},
           tin:{$valid: null, $dirty: null},
           dob:{$valid:true, $dirty: true},
           lastName:{$valid: false, $dirty: true},
           year:{$valid: true, $dirty: true
         }
       };
         cwsearchuser.changeField();
         expect(cwsearchuser.disableSearch).toBeTruthy();});

        it('valid form,invalid lastname,invalid dob,invalid taxYear check', function () {
          console.log('valid form,invalid lastname,invalid dob,invalid taxYear check');
          cwsearchuser.searchuserform = {
            $valid: true, $dirty: true,
            ssn:{$valid: null, $dirty: null},
            tin:{$valid: null, $dirty: null},
            dob:{$valid: false, $dirty: true},
            lastName:{$valid: false, $dirty: true},
            year:{$valid: false, $dirty: true
            }
          };
          cwsearchuser.changeField();
          expect(cwsearchuser.disableSearch).toBeTruthy();
       });
       it('invalid form,valid lastname,invalid dob,invalid taxYear check',function(){
          console.log('invalid form,valid lastname,invalid dob,invalid taxYear check');
          cwsearchuser.searchuserform = {
            $valid:false, $dirty: true,
            ssn:{$valid: null, $dirty: null},
            tin:{$valid: null, $dirty: null},
            dob:{$valid: false, $dirty: true},
            lastName:{$valid:true, $dirty: true},
            year:{$valid: false, $dirty: true
            }
          };
          cwsearchuser.changeField();
          expect(cwsearchuser.disableSearch).toBeTruthy();
        });
       it('invalid form,invalid lastname,valid dob,invalid taxYear check',function(){
          console.log('invalid form,invalid lastname,valid dob,invalid taxYear check');
          cwsearchuser.searchuserform = {
            $valid:false, $dirty: true,
            ssn:{$valid: null, $dirty: null},
            tin:{$valid: null, $dirty: null},
            dob:{$valid: true, $dirty: true},
            lastName:{$valid:false, $dirty: true},
            year:{$valid: false, $dirty: true
            }
          };
          cwsearchuser.changeField();
          expect(cwsearchuser.disableSearch).toBeTruthy();
        });
       it('invalid form,invalid lastname,invalid dob,valid taxYear check',function(){
          console.log('invalid form,invalid lastname,invalid dob,valid taxYear check');
          cwsearchuser.searchuserform = {
            $valid:false, $dirty: true,
            ssn:{$valid: null, $dirty: null},
            tin:{$valid: null, $dirty: null},
            dob:{$valid: false, $dirty: true},
            lastName:{$valid:false, $dirty: true},
            year:{$valid: true, $dirty: true
            }
          };
          cwsearchuser.changeField();
          expect(cwsearchuser.disableSearch).toBeTruthy();
        });
        it('invalid form,invalid lastname,invalid dob,invalid taxYear check',function(){
          console.log('invalid form,valid lastname,invalid dob,invalid taxYear check');
          cwsearchuser.searchuserform = {
            $valid:false, $dirty: true,
            ssn:{$valid: null, $dirty: null},
            tin:{$valid: null, $dirty: null},
            dob:{$valid: false, $dirty: true},
            lastName:{$valid:false, $dirty: true},
            year:{$valid: false, $dirty: true
            }
          };
          cwsearchuser.changeField();
          expect(cwsearchuser.disableSearch).toBeTruthy();
        });
      });
    });

    it('on View Details link navigate to view user screen',function(){
      console.log('on View Details link navigate to view user screen');
        rootscope.$apply();
        spyOn(CWService,'setFormIdforView');
        cwsearchuser.goToViewUser();
        expect(CWService.setFormIdforView).toHaveBeenCalled();
        expect(state.go).toHaveBeenCalledWith('state.cw-view-user');
    });

  });
});
