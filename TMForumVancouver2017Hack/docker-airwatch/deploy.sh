#!/bin/bash

cp ./opennms-conf/etc/opennms.properties.d/opennms-alarmlist.properties    ./opennms-etc/opennms.properties.d/

cp ./opennms-conf/etc/opennms.properties.d/opennms-newtsconfig.properties  ./opennms-etc/opennms.properties.d/

cp ./opennms-conf/etc/org.opennms.plugin.mqttclient.cfg  ./opennms-etc/

cp ../opennms/MqttClient/kar-package/target/MqttClient.kar-package-*.kar ./opennms-deploy/