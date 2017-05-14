<?php

namespace App\Providers;

use Illuminate\Support\Facades\Route;
use Illuminate\Foundation\Support\Providers\RouteServiceProvider as ServiceProvider;

class RouteServiceProvider extends ServiceProvider
{
    /**
     * Define the routes for the application.
     *
     * @return void
     */
    public function map()
    {
        Route::middleware('web')
             ->namespace('App\Http\Controllers')
             ->group(base_path('routes/web.php'));

        Route::middleware('api')
             ->middleware(['cors'])
             ->namespace('App\Http\Controllers')
             ->group(base_path('routes/api.php'));
    }
}
