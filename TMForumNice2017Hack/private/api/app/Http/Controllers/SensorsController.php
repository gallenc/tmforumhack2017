<?php

namespace App\Http\Controllers;

use App\Query;

class SensorsController extends Controller
{
    public function index()
    {
        return Query::getNodes()
            ->map(function ($node) {
                return $this->transform($node);
            })->values();
    }

    private function transform($node)
    {
        return [
            'name'      => $node['label'],
            'latitude'  => $node['assetRecord']['latitude'],
            'longitude' => $node['assetRecord']['longitude'],
        ];
    }
}
