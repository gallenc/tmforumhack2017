<?php

namespace App;

use Cache;
use App\Query;
use Carbon\Carbon;
use App\HttpClient;

class DroneService
{
    protected $client;

    public function __construct(HttpClient $client)
    {
        $this->client = $client;
        $this->url = config('app.drone_api');
    }

    public function getAvailable()
    {
        return $this->all()->reject->active;
    }

    public function sendDroneToWaypath($drone, $waypath)
    {
        $actions = collect($waypath['points'])
            ->map(function ($point) {
                return [
                    'coordinate' => [
                        (float) $point['latitude'],
                        (float) $point['longitude'],
                        50
                    ],
                    'command' => 16, // go to command,
                    'param1' => 0,
                    'param2' => 0,
                    'param3' => 0,
                    'param4' => 0,
                ];
            });

        // return home
        $actions->push(['coordinate' => [0,0,0], 'param1' => 0, 'param2' => 0, 'param3' => 0, 'param4' => 0, 'command' => 20]);

        // land
        $actions->push(['coordinate' => [0,0,0], 'param1' => 0, 'param2' => 0, 'param3' => 0, 'param4' => 0, 'command' => 21]);

        // take off
        $this->client->post($this->url."/vehicle/{$drone['id']}/action", ['height' => 30, 'name' => 'Takeoff']);

        // create mission
        $this->client->post($this->url."/vehicle/{$drone['id']}/mission", $actions->values()->all());

        // start mission
        $this->client->post($this->url."/vehicle/{$drone['id']}/action", ['name' => 'Start-Mission']);
    }

    public function sendDrones()
    {
        $this->getAvailable()
            ->each(function ($drone) {
                $waypath = Query::getWaypaths()->sortBy(function ($waypath) {
                    return (int) Cache::get('waypath.'.$waypath['id']);
                })->first();

                $this->sendDroneToWaypath($drone, $waypath);

                Cache::forever('waypath.'.$waypath['id'], Carbon::now()->timestamp);
            });
    }

    public function all()
    {
        return collect([
            $this->client->get($this->url.'/vehicle/48bb4f10'),
            $this->client->get($this->url.'/vehicle/8b4024aa'),
            $this->client->get($this->url.'/vehicle/ac80ec8a'),
            // $this->client->get($this->url.'/vehicle/0e1b383a'),
        ])->map(function ($drone) {
            return [
                'id'       => $drone['id'],
                'active'   => $drone['armed'],
                'coords' => [
                    'latitude'  => $drone['global_frame']['lat'],
                    'longitude' => $drone['global_frame']['lon'],
                ]
            ];
        });
    }
}
