const Oscartokiclass = require('./index');
const axios = require("axios");

// Set the debug Mode to true or false [OPTIONAL], default, debugmode is false.
let Oscartoki = new Oscartokiclass(true);

/** 
 * *****************************************************************************
 * EXAMPLE:
 * FOR THE GENERATION OF THE TOKI TO ADD IN HEADER OFF EACH REQUESTS.
 * IN THE PARAMETER: "oscartoki"
 * *****************************************************************************
 * */
// It's important to read this variable fomr a config file and not to 
// put it hard in the code like this[FOR SECURITY], it's just a quick example 
const clientkey = "5cbc654dc3355e382201615c";
// set the clientkey to Oscartoki
Oscartoki.setClientkey(clientkey);

// IMPORTANT, SPECIFY THE URL of the peer
// Set the peerURL if you are emitting
const peerURL = Microservice_Url
Oscartoki.setPeerURL(peerURL);

// Set the oscar URL
const oscarURL = "http://oscarurl.com";
Oscartoki.setOscarURL(oscarURL);

// Set the app name
const appName = "app_test"
Oscartoki.setAppName(appName);

// Generate the Toki
Oscartoki.generateToki();

// Get Toki generated
Oscartoki.tokiPrint("Toki: '" + Oscartoki.getToki() +"'");


/** 
 * *****************************************************************************
 * EXAMPLE:
 * RECEIVE A REQUEST AND VERIFY TOKI BY CHECKING THE PEER
 * *****************************************************************************
 * */
// We send the request/response Empty first but with in the Header the toki
// The response if the toki is valid will be a ckey, we verify the toki and the key
// Then we send the response/request

// oscar_result[0] is the status of the process if it have won it's true
// if not it's false
const checkkToki = Oscartoki.checkToki();
// And oscar_result[1] will be the key if the proces  won or the error message if the process failed
checkkToki.then((oscar_result) => {

    Oscartoki.tokiPrint("Stating tokiCheck...");
    console.log("oscar_result: ", oscar_result);
    if(oscar_result[0] === true){

        // Now you have the Key (toki and you can request with that now by adding it in headers)
        // oscartokikey: value
        const oscartokikey = oscar_result[1];
        Oscartoki.tokiPrint("oscartokikey: "+ oscartokikey)
        axios({
            method: 'get',
            url: peerURL+"?a=1&b=3", 
            headers: {
                'oscartoki': Oscartoki.getToki(),
                'oscartokikey': oscartokikey,
            }
        }).then(response => {
            console.log(">>response.data: ", response.data);

            ///////////////////////////////////////////////////////////////////////
            //////--------------------------------------------------------------///
            ////// DO YOUR STUFF HERE CUZ THE TOKI AND IT's KEY ARE BOTH VALIDS  //
            //////--------------------------------------------------------------///
            ///////////////////////////////////////////////////////////////////////

        }).catch(error => {

            Oscartoki.tokiPrint("error: "+ error);

        });

    }else{
        // Let's print the error
        Oscartoki.tokiPrint("Error: "+oscar_result[1])
    }

});



/** 
 * *****************************************************************************
 * EXAMPLE:
 * GENERATE A KEY FROM A GIVING TOKI
 * *****************************************************************************
 * */
Example_toki = Oscartoki.getToki();
Oscartoki.tokiPrint("Toki :" + Example_toki);
Oscartoki.tokiPrint("TokiKey generated:" + Oscartoki.generateTokiKey(Example_toki));

Example_tokikey = Oscartoki.generateTokiKey(Example_toki);
Oscartoki.tokiPrint("verifyTokiKey(Example_toki, Example_tokikey): ", Oscartoki.verifyTokiKey(Example_toki, Example_tokikey));



/** 
 * *****************************************************************************
 * EXAMPLE:
 * A SIMPLE EXAMPLE TO VERIFY A TOKI
 * *****************************************************************************
 * */
Example_toki = "7674707094551|79f041e75fcdec730f4a1a48099fdefc2e301acccc7057765aaae11ced752afe|b313c1b16118e8e";
// If the Toki is valid
if (Oscartoki.verifyToki(Example_toki) === true){
    /**
     * 
     * NOW!!! 
     * 
     * YOU CAN DO YOUR STUFF HERE CUZ THE TOKI IS VALID
     * 
     */
    Oscartoki.tokiPrint("This is a Valid Toki!");

}else{ // the toki is not valid

    Oscartoki.tokiPrint("Oops! This Toki is not valid!");
}
