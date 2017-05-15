<?php

    $connection = curl_init();
    $connectionString = array("http://139.162.227.142:8080/DSPRODUCTCATALOG2/api/catalogManagement/v2/");
    $catalogueTypes = array("productSpecification", "category", "productOffering", "serviceSpecification");
    curl_setopt($connection, CURLOPT_CUSTOMREQUEST, "DELETE");

    for ($i=0; $i < 1000; $i++) {
        foreach ($connectionString as $string) {
                foreach ($catalogueTypes as $type) {
                    curl_setopt($connection, CURLOPT_URL, $string.$type."/".$i);
                    curl_exec($connection);
                }
            }
        }

    curl_close($connection);
 ?>
