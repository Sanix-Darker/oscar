//'use strict';
const sha256 = require('js-sha256');
const axios = require("axios");

class Oscartokiclass {

    constructor(debugMode = false) {
        this.clientkey = "";
        this.debugMode = debugMode;
        this.toki = "";
        this.lifetime_of_toki = 3;
        this.peerURL = ""
    }
  
    /**
     * A Setter for the clientkey param
     * @return
     */
    setClientkey(m){
        this.clientkey = m;
    }
    /**
     * A getter for the clientkey param
     * @return
     */
    getClientkey(){
        return this.clientkey;
    }
  

    /**
     * A Setter for the peerURL param
     * @return
     */
    setPeerURL(m){
        this.peerURL = m;
    }
    /**
     * A getter for the peerURL param
     * @return
     */
    getPeerURL(){
        return this.peerURL;
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
        try{
            if(this.clientkey.length > 5){
                // Now let manage about the core of the toki
                const TokiCore = this.getSHA(this.getSHA(this.clientkey + " - " + timeAsString + " - " + this.clientkey));

                const brouillage = this.getSHA(timeAsString + "Brouillage de piste").substring(0, 15);
                //System.out.println("Time in Milliseconds: " + time);
                // get first 9 digit of the time
                const toki_generated = this.reverseString(timeAsString) + "|" + this.reverseString(TokiCore) + "|" + brouillage;

                this.tokiPrint("Toki generated: "+toki_generated);

                this.setToki(toki_generated);
            }else{
                console.log("Some Error Occurs When Generating the toki, please verify the clientKey you provided.")
            }
        }
        catch(err){
            console.log("Some Error Occurs When Generating the toki, please verify the clientKey you provided.")
        }
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

        if(toki.length > 5){

            const arrOfElt = toki.split("|");
            const get_toki_time = arrOfElt[0];
            const get_toki_time_sb = this.reverseString(get_toki_time);
        
            const toki_time_tocompare = parseInt(get_toki_time_sb.substring(0, 9), 10);
            const original_toki_time = parseInt(get_toki_time_sb, 10);

            this.tokiPrint("\n------------------------------------------------");        
            this.tokiPrint("Starting Verification.");
            // this.tokiPrint("current_time: " + current_time);
            // this.tokiPrint("toki_time_tocompare: " + toki_time_tocompare);
        
            // S'il y'a une difference de plus de 100seconde, le toki n'est plus valide
            if(current_time-toki_time_tocompare > this.lifetime_of_toki){
        
                this.tokiPrint("Toki is too old!");
                this.tokiPrint("Toki is invalid!");
                return false;
        
            }else{
                this.tokiPrint("Toki is in time!");
        
                const TokiCore = this.getSHA(this.getSHA(this.clientkey + " - " + get_toki_time_sb + " - " + this.clientkey));
                const reComputed = this.reverseString(TokiCore);
                const given = arrOfElt[1];
        
                this.tokiPrint("Toki given: '" + given + "'");
                this.tokiPrint("Toki reComputed: '" + reComputed+"'");
        
                if (given === reComputed){
                    this.tokiPrint("Toki is valid!");
                return true;
                }else{
                    this.tokiPrint("Toki is invalid!");
                return false;
                }
        
            }

        }else{
            console.log("There is an error for the toki you have sent, maybe it's not Valid, please Check it again!")
            return false;
        }

    }

    /**
     * Take the toki in param and generate a uniue queue
     * @param {*} toki 
     */
    generateTokiKey(toki){

        if(this.verifyToki(toki) === true){

            const timeAsString = new Date().getTime().toString().substring(0, 7);

            this.tokiPrint("In generateTokiKey, TokiKey generated successfully!");

            return this.getSHA( this.getSHA( timeAsString + this.getSHA(toki) + this.getClientkey() ) ) + "o_o" + this.reverseString(timeAsString);

        }else{

            this.tokiPrint("In generateTokiKey, Toki is invalid!");
            return "IMVALID TOKI";

        }

    }

    /**
     * This method take the toki and the kwy in param and anayse it to verify it's a good key
     * @param {*} toki 
     * @param {*} key 
     */
    verifyTokiKey(toki, key){

        if(this.verifyToki(toki) === true){

            const timeAsString = this.reverseString(key.split("o_o")[1]);
            const recomputeKey = this.getSHA( this.getSHA( timeAsString + this.getSHA(toki) + this.getClientkey() ) ) + "o_o" + this.reverseString(timeAsString);

            this.tokiPrint("In verifyTokiKey!");
            this.tokiPrint("TokiKey: " + key);
            this.tokiPrint("TokiKey Recompute: " + recomputeKey);

            if (recomputeKey === key){
                
                this.tokiPrint("In verifyTokiKey, TokiKey is valid!");
                return true;

            }else{

                this.tokiPrint("In verifyTokiKey, TokiKey is invalid!");
                return false;

            }

        }else{

            this.tokiPrint("In verifyTokiKey, TokiKey is invalid!");
            return false;

        }

    }

    /**
     * A Simple request that send first a valid Toki,
     * then get a key generated by the peer and return it to be send again with the toki
     * So that both authentificated each other and exchange informations
     */
    checkToki(){
            return new Promise((resolve, reject) => {

                axios({
                    method: 'get',
                    url: this.peerURL, 
                    headers: {'oscartoki': this.getToki() }
                }).then(response => {
                    this.tokiPrint("Toki sending the Request===================================!");
                    if(response){
                        //console.log(">>response.data: ", response.data);

                        // We verify the key respond by the peer
                        if(typeof response.data.tokikey !== "undefined" && response.data.tokikey !== null && this.verifyTokiKey(this.getToki(), response.data.tokikey) === true){
                            
                            this.tokiPrint("TokiKey receive on check is valid!");
                            resolve([true, response.data.tokikey]);
                            
                        }else{

                            this.tokiPrint("TokiKey receive on check is invalid!");
                            resolve([false, "TokiKey receive on check is invalid!"]);

                        }

                    }else{
                        
                        this.tokiPrint("Any respinse provided!");
                        resolve([false, "Any respinse provided!"]);

                    }

                })
                .catch(error => {

                    resolve([false, error]);
                    console.log("error: ", error);

                });

        });
    }
}

module.exports = Oscartokiclass;
