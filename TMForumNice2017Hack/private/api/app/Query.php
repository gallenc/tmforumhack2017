<?php

namespace App;

use App\RawQuery;
use App\CachedQuery;
use Illuminate\Support\Facades\Facade;

class Query extends Facade
{
    public static function getFacadeAccessor()
    {
        // return RawQuery::class;
        return CachedQuery::class;
    }
}
