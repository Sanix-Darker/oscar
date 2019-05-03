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
Example_clientkey = "5cbc654dc3355e382201615c"

# The app name need to be in minuscul letters
AppName = "test_app"

#  set the clientkey to Oscartoki
Oscartoki.setClientkey(Example_clientkey)

# set the app name
Oscartoki.setAppName(AppName)

# Set the OscarURL
Oscartoki.setOscarURL("http://oscarurl.com")

# Set the PeerURL
Oscartoki.setPeerURL("http://peerurl.com")

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


""" 
 * ******************************************************************************
 * EXAMPLE:
 * A COMPLETE EXCHANGE WITH CLIENT AND GENERATION OF KEYS
 * IN A RESTFULL API APPLICATION
 * THIS APPLICATION TAKE 2 parameters a and b to sum thems and return the result
 * ******************************************************************************
 * """
from flask import Flask, jsonify, request

app = Flask(__name__)
# app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER


# First: import the Oscartoki Py module
# ---------------------------------------------------------
import Oscartokiclass

# Let's declare or BETTER take from a onfig file theese information:
Example_clientkey = "5cbc654dc3355e382201615c"

# The app name need to be in minuscul letters
AppName = "test_app"

# set the app name
Oscartoki.setAppName(AppName)

# Set the OscarURL
Oscartoki.setOscarURL("http://oscarurl.com")

#  Set the debug Mode to True or False [OPTIONAL], default, debugmode is False.
Oscartoki = Oscartokiclass.Oscartokiclass(True)

@app.route('/', methods=['GET'])
def index():

    #  set the clientkey to Oscartoki
    Oscartoki.setClientkey(Example_clientkey)

    try:
        # SECOND CONNEXION
        # EXCHANGE OF TOKI AND VERIFICATION OF TOKIKEY
        print("The Toki and the tokikey are presents")
        Example_toki = request.headers.get('oscartoki')
        Example_tokikey = request.headers.get('oscartokikey')

        print("Toki received: ", Example_toki)
        print("Tokikey received: ", Example_tokikey)
        
        # Third: Verify the oscartoki content in the header
        # ---------------------------------------------------------
        #  If the Toki is valid
        if (Oscartoki.verifyTokiKey(Example_toki, Example_tokikey) == True):

            a = int(request.args.get('a'))
            b = int(request.args.get('b'))

            response = jsonify({ 'status':'success', 'message': str(a+b) })

            print("This is a Valid Tokiand valid tokikey!")

        else: #  the toki is not valid
            response = jsonify({ 'status':'error', 'message': 'The toki sent is not valid or it\'s key is not matching.' })
            print("Oops! This Toki is not valid!")

    except:
        # FIRST CONNEXION
        # AND EXCHANGING OF TOKIs
        try:
            print("The Toki is presents")

            Example_toki = request.headers.get('oscartoki')

            response = jsonify({ 'status':'success', 'tokikey': str(Oscartoki.generateTokiKey(Example_toki)) })
        except:
            response = jsonify({ 'status':'error', 'message': 'The toki sent is not valid or it\'s key is not matching.' })
            print("2Oops! This Toki is not valid!")


    print("Sending response!")
    # Let's allow all Origin requests
    response.headers.add('Access-Control-Allow-Origin', '*')


    # Fith: Proceed and send a response.
    # Generate a valid toki and send
    # ---------------------------------------------------------
    Oscartoki.generateToki()
    toki_to_send = Oscartoki.getToki()
    print("toki_to_send: ", toki_to_send)

    response.headers.add('oscartoki', str(toki_to_send))

    return response

if __name__ == "__main__":
    app.run(host='0.0.0.0', debug=False, port=1234)