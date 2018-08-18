/**
 * Created by speta on 10/29/2015.
 */

'use strict';

angular.module('aca1095BUiAppCW').directive('customerData',function(CWService){
  return{
    restrict:'EA',
    scope:{
      label:"=",
      iconstatus:"=",
      filerinfo:"=",
      currentform:"=",
      editform:"&",
      xmlCorrection:"=",
      viewpdf:"&",
      regenerate:"&",
      mail:"&",
      tabindex : "@"
    },
    templateUrl:'views/templates/customerData.html',
    link:function(scope,element,attrs){
      scope.statusConstants = CWService.constants;
      if(typeof scope.label === 'undefined'){
        scope.label = {
          pdfstatus:"Form Status:",
          fName:"First Name:",
          lName:"Last Name:",
          dob:"DOB:",
          ssn: "SSN:",
          tin: "TIN:",
          noSsnTin: "No SSN / TIN available",
          address:"Address:",
          city:"City:",
          state:"State:",
          zipcode:"Zip:",
          comment: "Comments:",
          recipientHeading: "Recipient Information",
          email: "Email:"
        }
      }
    },
    controller: function ($scope, $state, CWService, confirmationDialog) {

    /*  $('.tooltip-icon').popover();*/
      $scope.popoverData = {
        mailRegenerate: "Mail action is disabled because you have not generated a new PDF with your recipient data changes.",
        mail : "Click to send the form by mail.",
        addressInvalid : "Mail action is disabled because address reported is invalid.",
        edit: "Edit",
        pdf : "Click to view PDF version of the form.",
        regenerate : "Click to regenerate the PDF.",
        template: "<div class='popover popoverShape' role='tooltip'><div class='arrow'></div><h3 class='popover-title'></h3><div class='popover-content popoverDisabledText'></div></div>",
        templateSmall: "<div class='popover popoverShapeSmall' role='tooltip'><div class='arrow'></div><h3 class='popover-title'></h3><div class='popover-content popoverDisabledText'></div></div>"
      };
      $scope.spin=true;
      $scope.pdfDisplayVal = null;
      $scope.class= 'status-';
      /*pdf status color*/
      $scope.$watch('currentform.pdfStatus',function(){
        switch($scope.currentform.pdfStatus){
          case 'GENERATED':
            $scope.pdfDisplayVal = 'Form Available';
            $scope.classname= $scope.class + 'green';
            break;
          case 'REGENERATED':
            $scope.pdfDisplayVal = 'Data Updated - Regenerate Form';
            $scope.classname= $scope.class + 'amber';
            break;
          case 'GENERATION_FAILED':
            $scope.pdfDisplayVal = 'Data Updated - Regenerate Form';
            $scope.classname= $scope.class + 'amber';
            break;
          case 'ADDRESS_INVALID':
            $scope.pdfDisplayVal = 'Address Invalid - Returned to Sender';
            $scope.classname= $scope.class + 'red';
            break;
          default :
            $scope.pdfDisplayVal = 'Not Available';
            $scope.classname= $scope.class + 'black';
            break;
        }

        $scope.ariaLabel = {
          customerData : "Viewing information for " + $scope.filerinfo.firstName + " " + $scope.filerinfo.lastName +
            "The form status is " + $scope.pdfDisplayVal +
            ". The " + $scope.filerinfo.uidType + " on record has last four digits as "+ $scope.filerinfo.uidNumber.substr( $scope.filerinfo.uidNumber.length - 4) +
            "The date of birth is " + CWService.ariaCompliantDate($scope.filerinfo.dob) +
            "The address is " + $scope.currentform.addressLine1 + " " +  $scope.currentform.addressLine2 +
            "City " + $scope.currentform.city + " State " + $scope.currentform.state + " zip code " + $scope.currentform.zipcode,

          edit : $scope.iconstatus.editiconenable ? "Press enter to edit information" : "You cannot edit information",
          pdf: $scope.iconstatus.pdficonenable && !$scope.iconstatus.pdfprocessstatus ? "Press enter to view current form on screen" : "No form has been generated for this user",
          mail : $scope.iconstatus.mailiconenable ? "Press enter to request mailed copy of the form to send to the citizen" : "No form has been generated for this user",
          mailRegenerate: !$scope.iconstatus.mailiconenable && $scope.iconstatus.pdfprocessstatus ? "Mail action is disabled because you have not generated a new PDF with your recipient data changes." : "",
          addressInvalid : !$scope.iconstatus.mailiconenable &&  $scope.currentform.pdfStatus === 'ADDRESS_INVALID' ? "Mail action is disabled because address reported is invalid." : "",
          regenerate : $scope.iconstatus.pdfprocessstatus && !$scope.iconstatus.pdficonstatus ? "Press enter to regenerate for with the updates to the record" : "No form has been generated for this user"
        };
      });

      /* on click of edit icon leads to edit_customer_data method */
      $scope.edit_customer_data = function(){
        $scope.editform();
      };

      $scope.closeModal = function(){
        confirmationDialog.modalBx.close();
      };

      $scope.edit_reason =function(){
        if($scope.xmlCorrection){
          /** caseworker Read Write mode but record correction not allowed in XML cycle**/
          var modalAttribute = {
            windowClass: 'center-modal',
            animation: true,
            templateUrl: 'views/templates/cautionDialog.html',
            label : {
              title: 'Edit Not Allowed',
              noAction: '',
              yesAction: '',
              isHTML : true,
              description: 'This record cannot be modified until it is processed by the IRS.'
            }
          };
          confirmationDialog.openModal(modalAttribute,$scope.closeModal , $scope.closeModal, $scope.closeModal);
        }else{
          /** to be executed for readonly caseworker **/
        }
      };

      /*on click of view pdf icon leads to the view_pdf methods*/
      $scope.view_pdf = function(){
        $scope.viewpdf();
      };

        /*on click of regenerate pdf icon leads to regenerate_pdf method*/
        $scope.regenerate_pdf = function(){
            $scope.spin = false;
            CWService.regeneratePdf($scope.currentform.formID)
              .success(function(data){
                if(data==="GENERATED"){
                  $scope.currentform.pdfStatus = data;
                  $scope.pdfDisplayVal = data;
                  $scope.spin = true;
                  $scope.iconstatus.pdfprocessstatus = false;
                  $scope.iconstatus.pdficonstatus = true;
                  $scope.iconstatus.pdficonenable = true;
                  $scope.iconstatus.mailiconenable = true;
                }
              })
              .error(function(data){
              });
        };

      /*on click on mail icon leads to mail_form method*/
      $scope.mailForm = function () {
        $scope.mail();
      };

      $scope.viewForm = function () {
        //CWService.setPreviousState('state.cw-view-source-data');
        //$state.go('state.cw-view-form');
        $scope.viewpdf();
      };

    }
  };
});
