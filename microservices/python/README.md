# OscarToki-Python

This is an implementation of Oscar-Toki in Python.

## Requirements

Nothing is required to use OscarToki in Python.

## Introduction

OscarToki Protocol is a simple way to generate a kind of a token, that have:

- A lifetime of 40 seconds.
- Can only be compute with a peer that have a "valid" Clientkey.
- In each request just add in the header the parameter `oscartoki` that content that toki.

## How to use:

- First import the module in the script
- Second Just follow this example to use it on your python client:
```python
import sys
sys.path.append('/Oscartoki')

from Oscartoki import Oscartokiclass

#  Set the debug Mode to True or False [OPTIONAL], default, debugmode is False.
Oscartoki = Oscartokiclass.Oscartokiclass(True)

""" 
 * *****************************************************************************
 * EXAMPLE:
 * FOR THE GENERATION OF THE TOKI TO ADD IN HEADER OFF EACH REQUESTS.
 * IN THE PARAMETER: "oscartoki"
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

```

## How to test:

You can Run Test.py(Available on ./Test.py) to see what happens!


## IMPORTANT NOTE:

IN PRODUCTION, YOU "CAN" USE THE OBFUSCATE VERSION "Py" AVAILABLE IN (./dist/Oscartokimin.py) AS A LIBRARY TO IMPORT IN THE PROJECT, Or Customize and build your own.


## Author

- Sanix darker (Ange SAADJIO).