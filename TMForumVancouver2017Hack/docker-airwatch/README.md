ActiveMQ installation
karaf@root()> feature:repo-add activemq
Adding feature url mvn:org.apache.activemq/activemq-karaf/LATEST/xml/features
karaf@root()> feature:install activemq-broker

admin@root()> feature:repo-add activemq 5.14.5
Adding feature url mvn:org.apache.activemq/activemq-karaf/5.14.5/xml/features
admin@root()> feature:install activemq-broker


karaf@root()> feature:install webconsole
karaf@root()> feature:install activemq-web-console

in features config add     webconsole, \
    activemq, \
    activemq-web-console

