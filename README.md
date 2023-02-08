# Meli Quasar

This is a simple application build with java, springboot, docker and dynamodb.

## Consumming from cloud
It is deployed in the AWS cloud, and it's available using [an API Gateway endpoint](https://q3fi163dv4.execute-api.us-east-1.amazonaws.com/prod/secret)

Be aware that lambda deployment uses a **custom header** validation with name **authorizationToken**
and values
- allow (this is the valid one!)
- deny
- unauthorized

Inside /docs folder it's an already configured postman project

[postman project](docs/meli-quasar.postman_collection.json)


## Code Structure
Code follows hexagonal architecture pattern and has the following structure (Some images folders not opened!)

[Folder Structure](docs/001-structure.png)

## Running the project 
Import the project as maven in any IDE and try to run it.

App runs using default port 8080

### Maven 

It's also  possible to create a runnable jar  

    mvn clean install

### Docker
The project contains the [docker-maven-plugin](https://github.com/fabric8io/docker-maven-plugin) that adds custom maven goals to work with docker

#### Create an image
Next command will create an image in your docker with name camilourrego/meli-quasar
    
    mvn clean install docker:build

[docker-image](docs/002-docker-image.png)

#### Build and run image from maven
It's also possible to run the docker image immediately using the next command

    mvn clean install docker:run

## Swagger
To take a look at available rest methods go to 

    http://localhost:8080/swagger-ui/index.html




