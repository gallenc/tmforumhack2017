<?php

$router->get('/waypaths', 'WaypathsController@index');
$router->get('/drone-base', 'DroneBaseController@index');
$router->get('/sensors', 'SensorsController@index');

$router->get('/drones', 'DronesController@index');
$router->get('/ping', 'PingController@index');

$router->get('/balance', function(){
    return
});