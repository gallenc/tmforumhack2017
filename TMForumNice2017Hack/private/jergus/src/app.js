import angular from 'angular';
import _ from 'lodash';
import env from './.env';

/**
 * Vendor
 */
import 'angular-route';
import 'angular-ui-router';
import 'angular-google-maps';
import 'angular-simple-logger';
import 'angular-ui-bootstrap';
import 'angular-sanitize';
import 'ng-toast'

const app = angular.module('app', [
    'ui.bootstrap','ngRoute', 'ui.router', 'uiGmapgoogle-maps', 'ngToast'
]);

app.config(function ($qProvider) {
    $qProvider.errorOnUnhandledRejections(false);
});

app.config(function (uiGmapGoogleMapApiProvider) {
    uiGmapGoogleMapApiProvider.configure({
        key: env.GOOGLE_MAP_KEY
    });
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
app.controller('MapController', require('./controllers/MapController.js').default);
app.controller('StoreController', require('./controllers/StoreController.js').default);
app.controller('HeaderController', require('./controllers/HeaderController.js').default);

/**
 * App Services
 */
app.service('Map', require('./services/Map.js').default);
app.service('Api', require('./services/Api.js').default);
app.service('HuaweiHTTPService', require('./services/HuaweiHTTPService.js').default);
app.service('SalesForceService', require('./services/SalesForceService.js').default);
app.service('ShipCompany', require('./services/ShipCompany.js').default);

/**
 * Components
 */
app.component('amountModalComponent', require('./components/AmountModalComponet.js').default);
