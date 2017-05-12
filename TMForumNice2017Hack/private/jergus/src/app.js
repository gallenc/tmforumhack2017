import angular from 'angular';


/**
 * Vendor
 */
import 'angular-route';
import 'angular-ui-router';

const app = angular.module('app', [
    'ngRoute', 'ui.router',
]);

app.config(function ($qProvider) {
    $qProvider.errorOnUnhandledRejections(false);
});

/**
 * Something else
 */
import './styles/app.scss';

/**
 * Views
 */
const templates = require.context('./views', true, /\.html$/);

templates.keys().forEach(templates);

/**
 * Configuration
 */
app.config(require('./routes.js').default);

/**
 * Controllers
 */
app.controller('HomepageController', require('./controllers/HomepageController.js').default);
app.controller('DroneController', require('./controllers/DroneController.js').default);

/**
 * App Services
 */

app.service('DroneHTTPService', require('./services/DroneHTTPService.js').default);
app.service('DroneListService', require('./services/DroneListService').default);





