# Javascript - Javascript

A simple implementation between ReactJs app and nodeJs microservice.

## Requirements

- For the client, we need : `axios`

## How to test

### Install first the tool that will allow you to do http requests


### Run The test-Communication:

 
### What will be done

- The client will sent a request to the microservice with a generated oscartoki in header.
- The microservice will get the oscartoki parameter, will verify it before proceed and generate it's own toki key to send to the client.
- The client receive the toki key, Verify it and send the real request to the microservice.
- The Microservice receive the request with tokikey and tokis generated and proceed.

That's all, in fact it's most compilcated than tis explanation but that's basics to understand what is done in this example of implementation.


# Author

- Sanix-darker
