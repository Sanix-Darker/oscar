"""
 * AN
 * SEVER PY IMPLEMENTATION OF OSCARTOKI
 * 
 * Implementation of OscarToki receiving-emitting
 * this script will receive a request from the Js Client
 * get and verify the generated oscartoki content in header.
 * And finaly proceed and send a response to je client..
 * 
 * By Sanix-darker
"""
from flask import Flask, jsonify, request

app = Flask(__name__)
# app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER


# First: import the Oscartoki Py module
# ---------------------------------------------------------
from Oscartoki import Oscartokiclass

# Let's declare or BETTER take from a onfig file theese information:
Example_clientkey = "aess3212-kj321gyu-gsad76-dsa687-21y873"

#  Set the debug Mode to True or False [OPTIONAL], default, debugmode is False.
Oscartoki = Oscartokiclass.Oscartokiclass(True)


@app.route('/', methods=['GET'])
def index():

    #  set the clientkey to Oscartoki
    Oscartoki.setClientkey(Example_clientkey)

    try:
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
        print("11Oops!!")
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