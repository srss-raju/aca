/**
 * Created by pchavan on 10/9/2015.
 */

'use strict';
angular.module('aca1095BUiAppCW').directive("displayFile", function () {

  var updateElem = function (element) {
    return function (displayFile) {
      element.empty();

      var objectElem = {};
      var embedElem = {};
      if (displayFile && displayFile.type !== "") {
        if(window.navigator.msSaveOrOpenBlob){
          var message = angular.element('<div class="row margin-left-25px grey" tabindex="25" aria-label="Please use ALT plus O to open PDF file in Adobe Reader">The pdf version is downloaded. Select open or save.</div>');
          element.append(message);
          var iframe = angular.element('<iframe></iframe>');
          iframe[0].style.display = "none";
          element.append(iframe);
          iframe[0].contentWindow.navigator.msSaveOrOpenBlob(displayFile.filePDF, 'form.pdf');
        }else{
          if (displayFile.type === "pdf") {
            objectElem = angular.element(document.createElement("iframe"));
            objectElem.attr("src", displayFile.fileUrl);
            objectElem.attr("type", "application/pdf");
            objectElem.attr("id", "myFrame");
            objectElem.attr("width", "100%");
            objectElem.attr("seamless", "seamless");
            //objectElem.attr("sandbox", "allow-scripts");
            objectElem.attr("wmode", "transparent");
          }
          else {
            objectElem = angular.element(document.createElement("img"));
            objectElem.attr("src", displayFile.fileUrl);
          }
        }
        }

      element.append(objectElem);
    };
  };

  return {
    restrict: "EA",
    scope: {
      displayFile: "=",
      fileCreated : "="
    },
    link: function (scope, element) {
      scope.$watch("displayFile", updateElem (element));
    }
  };
});
