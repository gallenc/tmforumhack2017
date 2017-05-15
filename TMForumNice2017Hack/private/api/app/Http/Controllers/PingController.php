<?php

namespace App\Http\Controllers;

use App\DroneService;

class PingController extends Controller
{
    public function index(DroneService $drones)
    {
        $drones->sendDrones();

        return response()->json(['success' => true]);
    }
}
