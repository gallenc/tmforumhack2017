export default function ($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('/dashboard');

    $stateProvider.state('dashboard', {
        url: '/dashboard',
        templateUrl: '/views/dashboard.html',
    });

    $stateProvider.state('map', {
        url: '/map',
        templateUrl: '/views/map.html',
        controller: 'MapController as vm'
    });

    $stateProvider.state('drone', {
        url: '/drone',
        templateUrl: '/views/drone.html',
        controller: 'DroneController as vm'
    });

    $stateProvider.state('store', {
       url: '/store',
       templateUrl: '/views/store.html',
       controller: 'StoreController as vm'
    });
};


