# system-control
Simple Java Webapp that can executes (configurable) actions to a set of (configurable) systems filtered by tags

## Getting Started


    # clone the repo
    git clone https://github.com/synyx/system-control.git
    # go to root
    cd system-control
    # launch using spring-boot maven-plugin
    mvn spring-boot:run
    
Then browse to http://localhost:8080/

## Configure

You can use System-Properties or other "Spring-Boot" ways to override the resources from where systems and actions are read. 
Take a look at the sample-files systems-sample.json and actions-sample.json located at src/main/resources/


