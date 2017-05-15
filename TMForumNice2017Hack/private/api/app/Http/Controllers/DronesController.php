<?php

namespace App\Http\Controllers;

use App\DroneService;

class DronesController extends Controller
{
    public function index(DroneService $drones)
    {
        return $drones->all();
    }
}
