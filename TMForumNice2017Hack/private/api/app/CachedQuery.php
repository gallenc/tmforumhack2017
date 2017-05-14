<?php

namespace App;

use Cache;
use App\RawQuery;

class CachedQuery
{
    protected $query;

    public function __construct(RawQuery $query)
    {
        $this->query = $query;
    }

    public function __call($method, $args)
    {
        return Cache::rememberForever($method, function () use ($method, $args) {
            return $this->query->$method(...$args);
        });
    }
}
