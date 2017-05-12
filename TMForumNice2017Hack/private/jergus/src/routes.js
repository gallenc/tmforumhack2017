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
};
