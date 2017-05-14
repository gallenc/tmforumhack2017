<?php

namespace App;

use GuzzleHttp\Client;

class RawQuery
{
    protected $client;

    public function __construct(Client $client)
    {
        $this->client = $client;
        $this->glassfish = config('app.glassfish');
        $this->opennms = config('app.opennms.url');
    }

    public function getDroneBase()
    {
        return $this->get($this->glassfish.'/addressManagement/api/addressManagement/v1/address?streetName=DroneBase')[0];
    }

    public function getAddresses()
    {
        return collect($this->get($this->glassfish.'/addressManagement/api/addressManagement/v1/address'))
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
        return $this->post($this->glassfish.'/tmforum-address-gis-distance/gisaddress/api/v1/waypath?streetName='.$from['streetName'], $to);
    }

    public function getNodes()
    {
        $credentials = [
            config('app.opennms.username'), config('app.opennms.password'),
        ];

        return collect($this->get($this->opennms.'/opennms/rest/nodes.json', $credentials)['node'])
            ->filter(function ($node) {
                return $node['foreignSource'] == 'southampton-port';
            });
    }

    private function get($url, $auth = [])
    {
        $response = $this->client->request('GET', $url, compact('auth'))->getBody();

        return json_decode($response, true);
    }

    private function post($url, $json)
    {
        $response = $this->client->request('POST', $url, compact('json'))->getBody();

        return json_decode($response, true);
    }
}
