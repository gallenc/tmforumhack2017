## OpenNMS Elasticsearch 2 ReST Plugin

### Maven Project
~~~~
project groupId: org.opennms.plugins
project name:    tmforum-rest-spm-ri
version:         0.0.1-SNAPSHOT
~~~~

### Description

This project creates a graphml mapping from an opennms asset table
Presently experimental

### To install in OpenNMS 

Open the karaf command prompt using
~~~~
ssh -p 8101 admin@localhost

(or ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no if no host checking wanted)
~~~~

To install the feature in karaf use

~~~~

karaf@root> features:addurl mvn:org.opennms.plugins/tmforum-rest-spm-ri/0.0.1-SNAPSHOT/xml/features
karaf@root> features:install tmforum-rest-spm-ri

(or features:install tmforum-rest-spm-ri/0.0.1-SNAPSHOT for a specific version of the feature)
~~~~

To install on OpenNMS
---------------------
You need to add the repo where the feature is installed to the opennms karaf configuration.
Obviously this could point at a remote repository
However if you have built on your local machine, add the local repo as follows;

sudo vi /opt/opennms/org.ops4j.pax.url.mvn.cfg

change the following property to add file:/home/admin/.m2/repository@snapshots@id=localrepo 
where /home/admin/.m2/repository is the location of local maven repository

org.ops4j.pax.url.mvn.repositories= \
    http://repo1.maven.org/maven2@id=central, \
    http://svn.apache.org/repos/asf/servicemix/m2-repo@id=servicemix, \
    http://repository.springsource.com/maven/bundles/release@id=springsource.release, \
    http://repository.springsource.com/maven/bundles/external@id=springsource.external, \
    https://oss.sonatype.org/content/repositories/releases/@id=sonatype, \
    file:/home/admin/.m2/repository@snapshots@id=localrepo

