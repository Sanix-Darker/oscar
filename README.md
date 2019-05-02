# oscartokis

OscarToki is a kind of protocol that consist to generate a specific toki(Not realy a token), 
that will be a kind of password that microservice and client will exchange each time when communicate each other,
by roling between Server - Client and Client - Server.

## How it's works

- When a client want to use a ressource available on a microservice, he will use Oscartoki Class to generate a valid toki which have many feature and specification on it's content.[ by the way, i wrote a list of clients and microservices [ Server < - > client Emissions and receptions] in (python, ruby, java, javascript, swift)].
- The client put this toki in header of each request, we can distinct two type here:
    -In the case it's a Simple REST communication, he will create a header parameter containing the toki like this: `oscartoki` = "a23asd..."
    -In a case it's a RabbitMq Communication or another gateWay to exchange informations, the parameter `oscartoki` need to be present.
- When the request arrives to the microservice, its verify it:
    - If it's valid.
    - If it's not yet Old.
- If the microservice authentificateit, it's response to the client with a generate toki too and a secretKey generated from the precedent toki sent.
- The client will verify First the toki before accepting the response and verify the secretKey to resend the real request.

`NOTE:` When all this process is running, Oscar-Backend will get instantly the current activity between microservices's on a client and all users connected to him.

## Available Clients-Server and Server-Client

This is the list of available oscartoki clients implementations.
- For user's app:
    - Android Microservice of Oscar-Toki wrote in Java. [Need improvement]
    - ReactJs Microservice wrote in JavaScript [Work fine]
    - Swift Microservice wrote in Swift [in development]

- For client's app:
    - Python Microservice of Oscar-Toki. [Work fine]
    - NodeJs Microservice inspired on [ReactJs Source code up here]. [Work fine]
    - Ruby Microservice. [not hardly tested]

## Author

- Sanix darker (Ange SAADJIO).