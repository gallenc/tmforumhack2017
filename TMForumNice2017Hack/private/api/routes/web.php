<?php

$router->get('/', function () {
    return response()->json([
        'online' => true,

        'endpoints' => [
            'GET /waypaths'   => 'Get waypaths for drones.',
            'GET /drone-base' => 'Get drone base.',
            'GET /sensors'    => 'Get list of sensors.',
            'GET /drones'     => 'Get list of drones with their positions.',
            'GET /ping'       => 'Ping the server.',
        ]
    ]);
});
