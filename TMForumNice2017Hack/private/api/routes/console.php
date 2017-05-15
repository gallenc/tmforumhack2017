<?php

use App\DroneService;

Artisan::command('drone', function (DroneService $drones) {
    $drones->sendDrones();
});
