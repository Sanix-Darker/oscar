# OscarToki-Java(Android)

This is an implementation of Oscar-Toki in Java.

## Requirements

Nothing is required to use OscarToki in Java.

## Introduction

OscarToki Protocol is a simple way to generate a kind of a token, that have:

- A lifetime of 40 seconds.
- Can only be compute with a peer that have a "valid" Clientkey.
- In each request just add in the header the parameter `oscar_toki` that content that toki.

## How to use:

- First import the class
- Second Just follow this example to use it on your Java(Android) client:
```java

    // Instantiate the OscarToki
    OscarToki OscarToki = new OscarToki();
    // Set the debug Mode to true or false [OPTIONAL], default, debugmode is false.
    OscarToki.setDebugmode(true);

    /** 
     * *****************************************************************************
     * EXAMPLE:
     * FOR THE GENERATION OF THE TOKI TO ADD IN HEADER OFF EACH REQUESTS.
     * IN THE PARAMETER: "oscar_toki"
     * *****************************************************************************
     * */
    
    // It's important to read this variable fomr a config file and not to 
    // put it hard in the code like this[FOR SECURITY], it's just a quick example 
    String Example_clientkey = "aess3212-kj321gyu-gsad76-dsa687-21y873";
    // set the clientkey to OscarToki
    OscarToki.setClientkey(Example_clientkey);
    // Generate the Toki
    OscarToki.generateToki();

    // Get Toki generated
    System.out.println("Toki: '" + OscarToki.getToki()+"'");

    // Here you can now add it on your header
    // [oscar_toki]

    /**
     * You can Get a newly generated Toki by calling OscarToki.getToki()
    */


    /** 
     * *****************************************************************************
     * EXAMPLE:
     * FOR VEFY THE TOKI RECEIVED 
     * *****************************************************************************
     * */
    String Example_toki = "7674707094551|79f041e75fcdec730f4a1a48099fdefc2e301acccc7057765aaae11ced752afe|b313c1b16118e8e";

    // If the Toki is valid
    if (OscarToki.verifyToki(Example_toki) == true){
        /**
         * 
         * NOW!!! 
         * 
         * YOU CAN DO YOUR STUFF HERE CUZ THE TOKI IS VALID
         * 
         */
        System.out.println("This is a Valid Toki!");

    }else{ // the toki is not valid

        System.out.println("Oops! This Toki is not valid!");

    }
```

## How to test:

You can Run Test.java(Available on ./Oscartoki/Test.java) to see what happens!

## To build a new Jar

Just hit this command in the CLI after Generate ".class" of Oscartoki.java by running/Build it:
```shell
jar cf ./dist/Oscartoki.jar ./Oscartoki/Oscartoki.class
```

## IMPORTANT NOTE:

IN PRODUCTION, YOU "CAN" USE THE COMPILED VERSION "JAR" AVAILABLE IN (./dist/Oscartoki.jar) AS A LIBRARY TO IMPORT IN THE PROJECT.


## Author

- Sanix darker (Ange SAADJIO).