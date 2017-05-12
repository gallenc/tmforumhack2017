import angular from 'angular';
import _ from 'lodash';


/**
 * Vendor
 */
import 'angular-route';
import 'angular-ui-router';
import 'angular-google-maps';
import 'angular-simple-logger';

const app = angular.module('app', [
    'ngRoute', 'ui.router', 'uiGmapgoogle-maps',
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
app.controller('MapController', require('./controllers/MapController.js').default);

/**
 * App Services
 */

app.service('DroneHTTPService', require('./services/DroneHTTPService.js').default);
app.service('DroneListService', require('./services/DroneListService').default);
app.service('Map', require('./services/Map.js').default);
app.service('Api', require('./services/Api.js').default);

