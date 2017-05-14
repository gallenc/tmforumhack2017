import env from '../.env';

export default function ($http) {
    return {
        getDroneBase() {
            return $http.get(env.API_URL + '/drone-base').then(response => response.data);
        },

        getWaypaths() {
            return $http.get(env.API_URL + '/waypaths').then(response => response.data);
        },
    };
};
