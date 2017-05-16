export default function ($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('/');

    $stateProvider.state('dashboard', {
        url: '/',
        templateUrl: '/views/map.html',
        controller: 'MapController as vm'
    });

    $stateProvider.state('store', {
       url: '/store',
       templateUrl: '/views/store.html',
       controller: 'StoreController as vm'
    });
};


