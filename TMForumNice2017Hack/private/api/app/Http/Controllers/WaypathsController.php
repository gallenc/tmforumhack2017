<?php

namespace App\Http\Controllers;

use App\Query;

class WaypathsController extends Controller
{
    public function index()
    {
        return Query::getWaypaths()
            ->map(function ($path) {
                return $this->transformPath($path);
            })->all();
    }

    private function transformPath($path)
    {
        return [
            'name'   => $path['streetName'],
            'points' => $this->transformPoints($path['points']),
        ];
    }

    private function transformPoints($points)
    {
        return collect($points)->map(function ($point) {
            return [
                'latitude'  => (float) $point['latitude'],
                'longitude' => (float) $point['longitude'],
            ];
        })->all();
    }
}
