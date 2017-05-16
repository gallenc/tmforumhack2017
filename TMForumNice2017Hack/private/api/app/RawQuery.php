<?php

namespace App;

use App\HttpClient;

class RawQuery
{
    protected $client;

    public function __construct(HttpClient $client)
    {
        $this->client = $client;
        $this->glassfish = config('app.glassfish');
        $this->opennms = config('app.opennms.url');
    }

    public function getDroneBase()
    {
        return $this->client->get($this->glassfish.'/addressManagement/api/addressManagement/v1/address?streetName=DroneBase')[0];
    }

    public function getAddresses()
    {
        return collect($this->client->get($this->glassfish.'/addressManagement/api/addressManagement/v1/address'))
            ->reject(function ($address) {
                return $address['streetName'] == 'DroneBase';
            })->values();
    }

    public function getWaypaths()
    {
        $base = $this->getDroneBase();

        return $this->getAddresses()
            ->unique('streetName')
            ->map(function ($address) use ($base) {
                $address['points'] =  $this->getWaypathPoints($address, $base);

                return $address;
            })->values();
    }

    public function getWaypathPoints($from, $to)
    {
        return $this->client->post($this->glassfish.'/tmforum-address-gis-distance/gisaddress/api/v1/waypath?streetName='.$from['streetName'], $to);
    }

    public function getNodes()
    {
        $credentials = [
            config('app.opennms.username'), config('app.opennms.password'),
        ];

        return collect($this->client->get($this->opennms.'/opennms/rest/nodes.json', $credentials)['node'])
            ->filter(function ($node) {
                return $node['foreignSource'] == 'southampton-port2';
            });
    }
}
