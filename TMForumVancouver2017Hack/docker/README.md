## Description
contains docker configuration for the demo including container specific includes

## Prerequisites
You need docker, docker-compose and maven installed in the host machine. To install on Centos 7:

[How To Install and Use Docker on CentOS 7](https://www.digitalocean.com/community/tutorials/how-to-install-and-use-docker-on-centos-7)

[How To Install and Use Docker Compose on CentOS 7 ](https://www.digitalocean.com/community/tutorials/how-to-install-and-use-docker-compose-on-centos-7 )

[How To Install Maven ](https://maven.apache.org/install.html )

## container definitions
each container is mapped to a ddfernt port rance. Note max port number is 65535

## To run containers
```
docker-compose -f docker-compose.yml up -d
```
Will start all of the containers. 
This should bring up all the containers described in docker-compose.xml. 
This will take a little while particularly for the first start of Karaf and Nexus. 

Shut down using
```
docker-compose -f docker-compose.yml down
```
Each container can be accessed through a bash session 
```
docker-compose exec <continer name>  /bin/bash

e.g.

docker-compose exec karaf1  /bin/bash
```
Or directly with docker without container name. You can list the ids of all the running containers using 
```
docker ps -a

and log in using

docker exec -ti <container id> /bin/bash
```
## Container description

### Sonotype Nexus

Nexus is a maven repository manager which is used here as a proxy for the public Maven repositories and also for local plugins.
When running Nexus can be accessed here:

```
http://localhost:28081/nexus/  (default username: admin password: admin123)
```


#### Configure Nexus
For the demo to run, you need to set up some repositories in nexus. These should be called:
```
osgi-plugins
osgi-plugins-snapshots
osgi-plugins-licence-specs
osgi-plugins-licence-specs-snapshots
```
You can do this manually but there is a script which uses the Nexus rest api to create these repositories. 
To run this
```
cd docker
sh sonotype-init.sh

```
log in to the nexus ui and check these are created. 

### karaf containers
4 karaf containers are defined with mapping to the external host ports
```
karaf1 18101 ssh consol
karaf1 18181 web consol http://localhost:18181/system/console
karaf1 1883  active mq mqtt

karaf2 28101 ssh consol
karaf2 28181 web consol http://localhost:28181/system/console

karaf3 38101 ssh consol
karaf3 38181 web consol http://localhost:38181/system/console

karaf4 48101 ssh consol
karaf4 48181 web consol http://localhost:48181/system/console
```

### Karaf1 ACtiveMQ MQTT broker
Once container is running login and install ACtiveMQ using
```
docker-compose exec karaf1  /bin/bash

ssh karaf@localhost -p 8081
(password karaf)

feature:repo-add activemq 5.14.5
feature:install activemq-broker
feature:install feature:install activemq-web-console  (optional - runs at http://localhost:18181/activemqweb/)

You could also install karaf web consol which can also give you a local ssh consol session
feature:install webconsole (optional - runs at http://localhost:18181/system/console)

(note to access any karaf from another karaf container the command is ssh karaf@karaf1 -p 8081    (or karaf2,karaf3,karaf4 etc))

```

The MQTT configuration for ActiveMQ is held in activemq.xml

The MQTT broker is internally present on port 1883

The simpleAuthenticationPlugin is used for authenticating the MQTT clients with a simple password
username="mqtt-user" password="mqtt-password"

