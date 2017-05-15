<?php

namespace App;

use GuzzleHttp\Client;

class HttpClient
{
    protected $client;

    public function __construct(Client $client)
    {
        $this->client = $client;
    }

    public function get($url, $auth = [])
    {
        $response = $this->client->request('GET', $url, compact('auth'))->getBody();

        return json_decode($response, true);
    }

    public function post($url, $json)
    {
        $response = $this->client->request('POST', $url, compact('json'))->getBody();

        return json_decode($response, true);
    }
}
