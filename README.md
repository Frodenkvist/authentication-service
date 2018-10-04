For this repo you need to have postgres installed and create an user (if it does not already exist) and database with the following commands

`create role -U postgres fruktmannen`

`createdb -U postgres -O fruktmannen fruktkorg_authentication_service`

You also need to install [RabbitMQ](https://www.rabbitmq.com/).
If you don't have it auto configured to run on startup, you can start it with `rabbitmq-server -detached`.