//'use strict';
var sha256 = require('js-sha256');

class Oscartokiclass {

    constructor(debugMode = false) {
        this.microservicekey = "";
        this.debugMode = debugMode;
        this.toki = "";
        this.lifetime_of_toki = 3;
    }
  
    /**
     * A Setter for the microservicekey param
     * @return
     */
    setMicroservicekey(m){
        this.microservicekey = m;
    }
    /**
     * A getter for the microservicekey param
     * @return
     */
    getMicroservicekey(){
        return this.microservicekey;
    }
  

    /**
     * A Setter for the toki param
     * @return
     */
    setToki(t){
        this.toki = t;
    }
    /**
     * A getter for the toki param
     * @return
     */
    getToki(){
        return this.toki;
    }
  

    /**
     * A Setter for the debugMode param
     * @return
     */
    setDebugMode(d){
        this.debugMode = d;
    }
    /**
     * A getter for the debugMode param
     * @return
     */
    getDebugMode(){
        return this.debugMode;
    }

  
    tokiPrint(toprintt){
        if (this.debugMode == true){
          console.log("[ DEBUG-OSCARTOKI ] > "+toprintt);
        }
    }
  
    getSHA(input){
        return sha256(input);
    }


    reverseString(str) {
        return str.split("").reverse().join("");
    }

    /**
     * This method is the generator of the toki.
     * @return
     */
    generateToki(){
        this.tokiPrint("Starting Generation of the Toki.");

        // Let manae about the time
        const date= new Date();
        const time = date.getTime();
        const timeAsString = time.toString();

        // Now let manage about the core of the toki
        const TokiCore = this.getSHA(this.getSHA(this.microservicekey + " - " + timeAsString + " - " + this.microservicekey));

        const brouillage = this.getSHA(timeAsString + "Brouillage de piste").substring(0, 15);
        //System.out.println("Time in Milliseconds: " + time);
        // get first 9 digit of the time
        const toki_generated = this.reverseString(timeAsString) + "|" + this.reverseString(TokiCore) + "|" + brouillage;

        this.tokiPrint("Toki generated: "+toki_generated);

        this.setToki(toki_generated);
    }
    /**
     * This method verify a lamda toki and return true or false if it's good or not
     * @param toki
     * @return
     */
    verifyToki(toki){
      // 9 [0, 8] premiers chiffres du timestamp s'il y'a une difference de 30

      const date= new Date();
      let current_time = date.getTime();
      const current_time_string = current_time.toString();
      current_time = parseInt(current_time_string.substring(0, 9), 10);

      const arrOfElt = toki.split("|");
      const get_toki_time = arrOfElt[0];
      const get_toki_time_sb = this.reverseString(get_toki_time);

      const toki_time_tocompare = parseInt(get_toki_time_sb.substring(0, 9), 10);
      const original_toki_time = parseInt(get_toki_time_sb, 10);

      this.tokiPrint("Starting Verification.");
      this.tokiPrint("current_time: " + current_time);
      this.tokiPrint("toki_time_tocompare: " + toki_time_tocompare);

      // S'il y'a une difference de plus de 100seconde, le toki n'est plus valide
      if(current_time-toki_time_tocompare > this.lifetime_of_toki){

        this.tokiPrint("Toki is too old!");
        this.tokiPrint("Toki is invalid!");
        return false;

      }else{
        this.tokiPrint("Toki is in time!");

        const TokiCore = this.getSHA(this.getSHA(this.microservicekey + " - " + original_toki_time + " - " + this.microservicekey));
        const reComputed = this.reverseString(TokiCore);
        const given = arrOfElt[1];

        this.tokiPrint("Toki given: '" + given + "'");
        this.tokiPrint("Toki reComputed: '" + reComputed+"'");

        if (given.equals(reComputed)){
            this.tokiPrint("Toki is valid!");
          return true;
        }else{
            this.tokiPrint("Toki is invalid!");
          return false;
        }

      }
    }
}


// Set the debug Mode to true or false [OPTIONAL], default, debugmode is false.
let Oscartoki = new Oscartokiclass(true);

/** 
 * *****************************************************************************
 * EXAMPLE:
 * FOR THE GENERATION OF THE TOKI TO ADD IN HEADER OFF EACH REQUESTS.
 * IN THE PARAMETER: "oscar-toki"
 * *****************************************************************************
 * */
// It's important to read this variable fomr a config file and not to 
// put it hard in the code like this[FOR SECURITY], it's just a quick example 
Example_microservicekey = "aess3212-kj321gyu-gsad76-dsa687-21y873";
// set the microservicekey to Oscartoki
Oscartoki.setMicroservicekey(Example_microservicekey);
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

