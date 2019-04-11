from hashlib import sha256
import time

class Oscartokiclass:

    def __init__(self, debugMode=False):
        self.clientkey = ""
        self.debugMode = debugMode
        self.toki = ""
        self.life_time_of_toki = 3

    def setClientkey(self, m):
        """ A Setter for ClientKey"""
        self.clientkey = m
    def getClientkey(self):
        """ A Getter for ClientKey"""
        return self.clientkey


    def setToki(self, t):
        """ A Setter for Toki"""
        self.toki = t
    def getToki(self):
        """ A getter for Toki"""
        return self.toki


    def setDebugmode(self, d):
        """ A Setter for Toki"""
        self.debugMode = d
    def getDebugmode(self):
        """ A getter for debugmode"""
        return self.debugMode


    def tokiPrint(self, toprintt):
        """ A personnal print for Oscartoki will show what happens
        when the debug mode is true and hide when the debugmode is false"""
        print("[ DEBUG-OSCARTOKI ] > " + str(toprintt))


    def getSHA(self, stringg):
        return sha256(stringg.encode()).hexdigest()

    def reverseString(self, stringg):
        return stringg[::-1]

    def generateToki(self):
        """ This method is the generator of the toki. """

        if(len(self.clientkey) > 5):

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

        else:
            print("Some Error Occurs When Generating the toki, please verify the clientKey you provided.")


    def verifyToki(self, toki):
        """ This method verify a lamda toki and return true or false if it's good or not """
      # 9 [0, 8] premiers chiffres du _timestamp s'il y'a une difference de 30

        current__time = time.time()
        current__time_string = str(current__time)
        current__time = int(current__time_string[:9], 10)
        if(len(toki) > 5):

            arrOfElt = toki.split("|")
            get_toki__time = arrOfElt[0]
            get_toki__time_sb = self.reverseString(get_toki__time)

            toki__time_tocompare = int(get_toki__time_sb[:9], 10)
            original_toki__time = int(get_toki__time_sb, 10)

            self.tokiPrint("Starting Verification.")
            self.tokiPrint("current__time: " + str(current__time))
            self.tokiPrint("toki__time_tocompare: " + str(toki__time_tocompare))

        # S'il y'a une difference de plus de 100seconde, le toki n'est plus valide
            if(current__time-toki__time_tocompare > self.life_time_of_toki):

                self.tokiPrint("Toki is too old!")
                self.tokiPrint("Toki is invalid!")
                return False

            else:
                self.tokiPrint("Toki is in _time!")

                TokiCore = self.getSHA(self.getSHA(self.clientkey + " - " + str(original_toki__time) + " - " + self.clientkey))

                self.tokiPrint("TokiCore: "+ TokiCore)

                reComputed = self.reverseString(TokiCore)
                given = arrOfElt[1]

                self.tokiPrint("Toki given: '" + str(given) + "'")
                self.tokiPrint("Toki reComputed: '" + str(reComputed) +"'")

            if (given == reComputed):
                self.tokiPrint("Toki is valid!")
                return True
            else:
                self.tokiPrint("Toki is invalid!")
                return False
        else:
            print("Here--------")
            print("There is an error for the toki you have sent, maybe it's not Valid, please Check it again!")
            return False