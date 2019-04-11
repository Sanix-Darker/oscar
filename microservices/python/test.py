import sys
sys.path.append('/Oscartoki')

from Oscartoki import Oscartokiclass

#  Set the debug Mode to True or False [OPTIONAL], default, debugmode is False.
Oscartoki = Oscartokiclass.Oscartokiclass(True)

""" 
 * *****************************************************************************
 * EXAMPLE:
 * FOR THE GENERATION OF THE TOKI TO ADD IN HEADER OFF EACH REQUESTS.
 * IN THE PARAMETER: "oscar_toki"
 * *****************************************************************************
 * """
#  It's important to read this variable fomr a config file and not to 
#  put it hard in the code like this[FOR SECURITY], it's just a quick example 
Example_clientkey = "aess3212-kj321gyu-gsad76-dsa687-21y873"
#  set the clientkey to Oscartoki
Oscartoki.setClientkey(Example_clientkey)
#  Generate the Toki
Oscartoki.generateToki()

#  Get Toki generated
print("Toki: '" + Oscartoki.getToki()+"'")


""" 
 * *****************************************************************************
 * EXAMPLE:
 * FOR VEFY THE TOKI RECEIVED 
 * *****************************************************************************
 * """
Example_toki = "7674707094551|79f041e75fcdec730f4a1a48099fdefc2e301acccc7057765aaae11ced752afe|b313c1b16118e8e"

#  If the Toki is valid
if (Oscartoki.verifyToki(Example_toki) == True):
    """
    * 
    * NOW!!! 
    * 
    * YOU CAN DO YOUR STUFF HERE CUZ THE TOKI IS VALID
    * 
    """
    print("This is a Valid Toki!")

else: #  the toki is not valid

    print("Oops! This Toki is not valid!")


