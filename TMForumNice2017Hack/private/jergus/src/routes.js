export default function ($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('/dashboard');

    $stateProvider.state('dashboard', {
        url: '/dashboard',
        templateUrl: '/views/dashboard.html',
    });

    $stateProvider.state('example', {
        url: '/example',
        templateUrl: '/views/example.html',
    });
};
