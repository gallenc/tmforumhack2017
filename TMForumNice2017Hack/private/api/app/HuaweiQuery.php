<?php

namespace App;

use GuzzleHttp\Client;
use Illuminate\Support\Facades\Request;

class HuaweiQuery
{
    public function __construct()
    {
        $this->client = new Client(['verify' => false]);
    }

    public function get($url)
    {
        $response = $this->client->request('GET', $url, [
            'headers' => [
                'Authorization' => 'Bearer e4aef681-bda6-48f9-830d-5f1e79270740',
            ],
        ]);

        return json_decode((string) $response->getBody(), true);
    }

    public function post($url, $json){
        $response = $this->client->request('POST', $url, [
            'headers' => [
                'Authorization' => 'Bearer e4aef681-bda6-48f9-830d-5f1e79270740',
            ],
            'json' => $json,
        ]);


        return json_decode((string) $response->getBody(), true);
    }
}
