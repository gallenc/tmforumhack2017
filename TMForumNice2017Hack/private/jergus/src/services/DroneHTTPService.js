import env from '../.env'

export default function ($http, $q) {

    this.drone_ids =
        [
            'ada518cf',
            '73c388d1',
            '49d73055',
            '63ce3f3a'
        ];

    return {

        get_drones: () => {

            return $http.get(env.DRONE_URL + 'vehicle')

        },

        get_drones: () =>  {


           // $http.get(env.DRONE_URL + 'vehicle/' + this.drone_ids[])

             /*let drone_list = this.drone_ids.map(id => $http.get(env.DRONE_URL + 'vehicle/' + id));
             $q.all(drone_list).then(result => {
                 console.log(result);
             });*/

        },

        create_drone: (name) => {
            return $http.post(env.DRONE_URL + 'vehicle', {"name": name, "vehicleType": "simulated"});

        },

        get_drone: (id) => {
            return $http.get(env.DRONE_URL + 'vehicle/' + this.drone_ids[0]).then(drone => {
                console.log(drone);
            });
        }


    }

}