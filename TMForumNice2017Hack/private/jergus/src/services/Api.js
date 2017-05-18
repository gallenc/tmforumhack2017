import env from '../.env';

export default function ($http) {
    return {
        ping() {
            return $http.get(env.API_URL + '/ping');
        },

        getDroneBase() {
            return $http.get(env.API_URL + '/drone-base').then(response => response.data);
        },

        getWaypaths() {
            return $http.get(env.API_URL + '/waypaths').then(response => response.data);
        },

        getDrones() {
            return $http.get(env.API_URL + '/drones').then(response => response.data);
        },

        getNodes() {
            return $http.get(env.API_URL + '/sensors').then(response => response.data);
        },
    };
};
