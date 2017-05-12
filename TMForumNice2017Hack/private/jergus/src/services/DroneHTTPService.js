import env from '../.env'

export default function ($http) {

    return {

        get_drones: () => {

            return $http.get(env.DRONE_URL + 'vehicle')

        },

        create_drone: (name) => {
            return $http.post(env.DRONE_URL + 'vehicle', {"name": name, "vehicleType": "simulated"});
        },

        get_drone: (id) => {
            return $http.get(env.DRONE_URL + 'vehicle/' + id);
        }


    }

}