//create onConfirmExit for confirming with user to close/reload the page

             var myEvent = window.attachEvent || window.addEventListener;
             var chkevent = window.attachEvent ? 'onbeforeunload' : 'beforeunload'; 
             myEvent(chkevent, function(e) { // For IE7, Chrome, Firefox
             var confirmationMessage = ' ';  // a space
             (e || window.event).returnValue = confirmationMessage;
             return confirmationMessage;
             });
//create alert confirming to the user that their current progress since last save will not be saved

              window.confirm(" Your current progress since last save will not be saved!!"); 


//create function that will print argument to the console



