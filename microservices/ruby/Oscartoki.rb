# from hashlib import sha256
# import time

require 'digest'

# Compute a complete digest
Digest::SHA2.new(256).hexdigest 'abc' # => "ba7816bf8..."

class Oscartokiclass:

    def __init__(self, debugMode=False)
        self.clientkey = ""
        self.debugMode = debugMode
        self.toki = ""
        self.life_time_of_toki = 3

    def setClientkey(self, m)
        """ A Setter for ClientKey"""
        self.clientkey = m
    def getClientkey(self)
        """ A Getter for ClientKey"""
        return self.clientkey


    def setToki(self, t)
        """ A Setter for Toki"""
        self.toki = t
    def getToki(self)
        """ A getter for Toki"""
        return self.toki


    def setDebugmode(self, d)
        """ A Setter for Toki"""
        self.debugMode = d
    def getDebugmode(self)
        """ A getter for debugmode"""
        return self.debugMode


    def tokiPrint(self, toprintt)
        """ A personnal puts for Oscartoki will show what happens
        when the debug mode is true and hide when the debugmode is false"""
        puts("[ DEBUG-OSCARTOKI ] > " + str(toprintt))


    def getSHA(self, stringg)
        return sha256(stringg.encode()).hexdigest()

    def reverseString(self, stringg)
        return stringg[::-1]

    def generateToki(self)
        """ This method is the generator of the toki. """

        if(len(self.clientkey) > 5)

            self.tokiPrint("Starting Generation of the Toki.")

            # Let manae about the _time
            _time = time.time()
            _timeAsString = str(_time)

            # Now let manage about the core of the toki
            TokiCore = self.getSHA(self.getSHA(self.clientkey + " - " + _timeAsString + " - " + self.clientkey))

            brouillage = self.getSHA(_timeAsString + "Brouillage de piste")[:15] # Substring
            # System.out.println("_time in Milliseconds: " + _time)
            # get first 9 digit of the _time
            toki_generated = self.reverseString(_timeAsString) + "|" + self.reverseString(TokiCore) + "|" + brouillage

            self.tokiPrint("Toki generated: "+toki_generated)

            self.setToki(toki_generated)

        else
            puts("Some Error Occurs When Generating the toki, please verify the clientKey you provided.")


    def verifyToki(self, toki)
        """ This method verify a lamda toki and return true or false if it's good or not """
      # 9 [0, 8] premiers chiffres du _timestamp s'il y'a une difference de 30

        current__time = time.time()
        current__time_string = str(current__time)
        current__time = int(current__time_string[:9], 10)
        if(len(toki) > 5)

            arrOfElt = toki.split("|")
            get_toki__time = arrOfElt[0]
            get_toki__time_sb = self.reverseString(get_toki__time)

            toki__time_tocompare = int(get_toki__time_sb[:9], 10)
            original_toki__time = int(get_toki__time_sb, 10)

            self.tokiPrint("Starting Verification.")
            self.tokiPrint("current__time: " + str(current__time))
            self.tokiPrint("toki__time_tocompare: " + str(toki__time_tocompare))

        # S'il y'a une difference de plus de 100seconde, le toki n'est plus valide
            if(current__time-toki__time_tocompare > self.life_time_of_toki)

                self.tokiPrint("Toki is too old!")
                self.tokiPrint("Toki is invalid!")
                return False

            else
                self.tokiPrint("Toki is in _time!")

                TokiCore = self.getSHA(self.getSHA(self.clientkey + " - " + str(original_toki__time) + " - " + self.clientkey))

                self.tokiPrint("TokiCore: "+ TokiCore)

                reComputed = self.reverseString(TokiCore)
                given = arrOfElt[1]

                self.tokiPrint("Toki given: '" + str(given) + "'")
                self.tokiPrint("Toki reComputed: '" + str(reComputed) +"'")

            if (given == reComputed)
                self.tokiPrint("Toki is valid!")
                return True
            else
                self.tokiPrint("Toki is invalid!")
                return False
        else
            puts("Here--------")
            puts("There is an error for the toki you have sent, maybe it's not Valid, please Check it again!")
            return False

    #
    # Take the toki in param and generate a uniue queue
    # @param {*} toki 
    #
    def generateTokiKey(self, toki)

        if(self.verifyToki(toki) == True)

            current__time = time.time()
            timeAsString = str(current__time)[0:7]

            self.tokiPrint("In generateTokiKey, TokiKey generated successfully!")

            return self.getSHA( self.getSHA( timeAsString + self.getSHA(toki) + self.getClientkey() ) ) + "o_o" + self.reverseString(timeAsString)

        else

            self.tokiPrint("In generateTokiKey, Toki is invalid!")
            return "IMVALID TOKI"

    #
    # This method take the toki and the kwy in param and anayse it to verify it's a good key
    # @param {*} toki 
    # @param {*} key 
    #
    def verifyTokiKey(self, toki, key)

        if(self.verifyToki(toki) == True)

            timeAsString = self.reverseString(key.split("o_o")[1])
            recomputeKey = self.getSHA( self.getSHA( timeAsString + self.getSHA(toki) + self.getClientkey() ) ) + "o_o" + self.reverseString(timeAsString)

            self.tokiPrint("In verifyTokiKey!")
            self.tokiPrint("TokiKey: " + key)
            self.tokiPrint("TokiKey Recomputed: " + recomputeKey)

            if (recomputeKey == key)
                
                self.tokiPrint("In verifyTokiKey, TokiKey is valid!")
                return True

            else

                self.tokiPrint("In verifyTokiKey, TokiKey is invalid!")
                return False

        else

            self.tokiPrint("In verifyTokiKey, TokiKey is invalid!")
            return False

    #
    # A Simple request that send first a valid Toki,
    # then get a key generated by the peer and return it to be send again with the toki
    # So that both authentificated each other and exchange informations
    #
    def checkToki(self)

            session_requests = requests.session()

            # Get login csrf token
            result = session_requests.get(self.peerURL, headers={'oscartoki': self.getToki()})
            response = json.load(result.content)

            if(response)

                # We verify the key respond by the peer
                if(response.tokikey != None and self.verifyTokiKey(self.getToki(), response.tokikey) == True)
                    
                    self.tokiPrint("TokiKey receive on check is valid!")
                    return [True, response.data.tokikey]
                    
                else

                    self.tokiPrint("TokiKey receive on check is invalid!")
                    return [False, "TokiKey receive on check is invalid!"]

            else
                
                self.tokiPrint("Any respinse provided!")
                return [False, "Any respinse provided!"]