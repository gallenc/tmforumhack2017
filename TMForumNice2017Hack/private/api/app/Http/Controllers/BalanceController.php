<?php

namespace App\Http\Controllers;

use App\HttpClient;

class BalanceController extends Controller
{
    public function index(HttpClient $client)
    {
        dd();
        return $client->get("http://218.4.33.207:17100/openapi1/V1/rating/balancemanagement/v1/buckets?productid=3313810128531");
    }
}
