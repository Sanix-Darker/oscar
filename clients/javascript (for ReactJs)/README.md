# Oscar-Toki-Javascript [ReactJs]

This is an implementation of Oscar-Toki in JavaScript.


## Requirements

For this Client, i implemented theese librairies:
- js-sha256
- axios

## Introduction

OscarToki Protocol is a simple way to generate a kind of a token, that have:

- A lifetime of 40 seconds.
- Can only be compute with a peer that have a "valid" Clientkey.
- In each request just add in the header the parameter `oscartoki` that content that toki.

## How to install

This step is optionnal, because i volontary added the "./node_modules" to the git tree(Because of his 2dependencies that bring only 3 packages)
,but if you want to update packages, you can run:
```shell
cd to/oscartoki
npm install
```

## How to use:

- First import the class(available in Oscartoki) in your Js script.
- IMPORTANT: Dependant on ES5 or ES6, you can update the class Code as you want to export properly the class.
- Second Just follow this example to use it on your Java(Android) client:

```javascript
// Fist import the Class
const Oscartokiclass = require('./Oscartoki/index');

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
Example_clientkey = "5cbc654dc3355e382201615c";
// set the clientkey to Oscartoki
Oscartoki.setClientkey(Example_clientkey);
// Generate the Toki
Oscartoki.generateToki();
// Get Toki generated
console.log("Toki: '" + Oscartoki.getToki()+"'");

/** 
 * *****************************************************************************
 * EXAMPLE:
 * FOR VEFY THE TOKI RECEIVED 
 * *****************************************************************************
 * */
const peerURL = Microservice_Url
Oscartoki.setPeerURL(peerURL);
// oscar_result[0] is the status of the process if it have won it's true
// if not it's false
const checkkToki = Oscartoki.checkToki();
// And oscar_result[1] will be the key if the proces  won or the error message if the process failed
checkkToki.then((oscar_result) => {
    Oscartoki.tokiPrint("Stating tokiCheck...")
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
```

## How to test:

You can Run test.js to see what happens!
Or just go to see implementations on "example_implementation_tests" directory.

## IMPORTANT NOTE:

THIS CODE WILL BE DOWNLOAD AS READABLE IN THE CLIENT's BROWSER, 
IN PRODUCTION, USE ONLY THE MIN FILE AVAILABLE IN (./dist/Oscartoki.min.js), IT's A VERSION OF THE CLASS MINIFIED AND OBFUSCATE.

## Author

- Sanix darker (Ange SAADJIO).