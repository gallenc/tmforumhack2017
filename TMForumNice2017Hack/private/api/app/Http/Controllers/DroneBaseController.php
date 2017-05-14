<?php

namespace App\Http\Controllers;

use App\Query;

class DroneBaseController extends Controller
{
    public function index()
    {
        return $this->transform(
            Query::getDroneBase()
        );
    }

    private function transform($base)
    {
        return [
            'latitude'  => (float) $base['geoCode']['latitude'],
            'longitude' => (float) $base['geoCode']['longitude'],
        ];
    }
}
