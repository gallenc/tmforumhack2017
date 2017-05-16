<?php

use App\HuaweiQuery;

$router->get('/test', function (HuaweiQuery $query) {
    return $query->get('https://218.4.33.207:17100/openapi1/V1/rating/balancemanagement/v1/buckets?productid=3313810128531');
});

$router->get('/waypaths', 'WaypathsController@index');
$router->get('/drone-base', 'DroneBaseController@index');
$router->get('/sensors', 'SensorsController@index');

$router->get('/drones', 'DronesController@index');
$router->get('/ping', 'PingController@index');
