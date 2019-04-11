# Python-Javascript

A simple implementation of a test of communication between a microservice and a client using Oscartoki protocol

## Requirements

- For the client, we need : `xmlhttprequest`
- For the microservie, we need: `flask`

## How to test

### Install first the tool that will allow you to do http requests

This step is optionnal because i volontary leave the node_modules at the root of this project and,
its directly contain xmlhttprequest, but if you want to reDownload it run:

```shell

# Let's get ressources for the client to work well
cd to/python-javascaript/
npm install xmlhttprequest --save

# Now Installing requirement for the microservice in python
pip install -r requirements.txt
```

### Run The test-Communication:

- Fist you need to start the Python server, just run this command:
```shell
python fake_microservice.py
```

- Second Launch the fake_client.js by running this command on another CLI:
```shell
node fake_client.js
```

### What will be done

- The client will sent a request to the microservice with a generated oscartoki in header.
- The microservice will get the oscartoki parameter, will verify it before proceed and generate it's own toki key to send to the client.
- The client receive the toki key, Verify it before proceed.

That's all, in fact it's most compilcated than tis explanation but that's basics to understand what is done in this example of implementation.

## Author

- Sanix darker (Ange SAADJIO)