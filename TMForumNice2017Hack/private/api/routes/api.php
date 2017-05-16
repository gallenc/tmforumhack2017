<?php

use App\HuaweiQuery;

$router->get('/huawei/balance', function (HuaweiQuery $query) {
    return $query->get('https://218.4.33.207:17100/openapi1/V1/rating/balancemanagement/v1/buckets?productid=3313810128531');
});

$router->post('/huawei/deduct', function (HuaweiQuery $query) {
    $params = [
        "identityNumber" => "3313810128531",
        "deductInfoList" => [[
            "accountType"=>"2000",
            "unitValue"=> (float) number_format((float) request()->get('amount'), 2),
            "currencyID"=>"1049",
            "unitType"=>"0"
        ]],
        "serviceSeqNo" => (string) rand(43248328943300, 99999999999999),
        "description" => "Port Service Charge",
    ];

    return $query->post('https://218.4.33.207:17100/openapi3/V1/rating/balancemanagement/v1/balancededuct', $params);
});

$router->get('/waypaths', 'WaypathsController@index');
$router->get('/drone-base', 'DroneBaseController@index');
$router->get('/sensors', 'SensorsController@index');

$router->get('/drones', 'DronesController@index');
$router->get('/ping', 'PingController@index');
