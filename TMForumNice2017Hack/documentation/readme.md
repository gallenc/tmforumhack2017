# The final pitch for this project is 
SolentTMForumFinalPres17May;0.2.pdf

# specifications

The core TM Forum specification documents and RI's for this project are

## Address API
https://github.com/tmforum/DSADDRESS

## Catalog API
https://github.com/tmforum/DSPRODUCTCATALOG2

## Product Inventory API
https://github.com/FIWARE-TMForum/DSPRODUCTINVENTORY

spec: https://github.com/tmforum/TMFORUMAPISPECS14.5/blob/master/TMF620_Product_Catalog_Management_API_REST_Specification_R14.5.0-6.docx

## Glassfish instance
http://139.162.227.142:8080
admin https://139.162.227.142:4848/

to deploy using curl
curl --verbose  -H 'Accept: application/json' -X POST  -H 'X-Requested-By: dummy'   -F id=@/home/admin/devel/gitrepos/tmforumhack2017/TMForumNice2017Hack/tmforum-address-gis-distance.parent/iot-data-collection-simulator/target/iot-data-data-collection-simulator.war  -F force=true  --user admin:xxxx --insecure https://localhost:4848/management/domain/applications/application


## OpenNMS instance
http://demo1.opennms.co.uk:8980/opennms/login.jsp
http://85.159.209.91:8980/opennms/login.jsp

## JBPM KIE instance
wildfly http://139.162.227.142:28080/
jbpm kie http://139.162.227.142:28080/kie-wb-distribution-wars-6.5.0.Final-wildfly10/kie-wb.jsp

consol https://139.162.227.142:9993/console/App.html
