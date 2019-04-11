# Oscar-Toki-Javascript [NodeJS]

This is an implementation of Oscar-Toki in JavaScript.

## Requirements

For this Client, i implemented theese librairies:
- js-sha256
- axios

## Introduction

OscarToki Protocol is a simple way to generate a kind of a token, that have:

- A lifetime of 40 seconds.
- Can only be compute with a peer that have a "valid" Clientkey.
- In each request just add in the header the parameter `oscar_toki` that content that toki.

## How to install

```shell
cd to/oscartoki
npm install
```

## How to use:

- First import the class(available in Oscartoki) in your Js script.
- IMPORTANT: Dependant on ES5 or ES6, you can update the class Code as you want to export properly the class.
- Second Just follow this example to use it on your Java(Android) client:
```JavaScript

// Set the debug Mode to true or false [OPTIONAL], default, debugmode is false.
let Oscartoki = new Oscartokiclass(true);

/** 
 * *****************************************************************************
 * EXAMPLE:
 * FOR THE GENERATION OF THE TOKI TO ADD IN HEADER OFF EACH REQUESTS.
 * IN THE PARAMETER: "oscar_toki"
 * *****************************************************************************
 * */
// It's important to read this variable fomr a config file and not to 
// put it hard in the code like this[FOR SECURITY], it's just a quick example 
Example_clientkey = "aess3212-kj321gyu-gsad76-dsa687-21y873";
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
    console.log("This is a Valid Toki!");

}else{ // the toki is not valid

    console.log("Oops! This Toki is not valid!");
}
```

## How to test:

You can Run index.js (in "./Oscartoki") to see what happens!

## Author

- Sanix darker (Ange SAADJIO).