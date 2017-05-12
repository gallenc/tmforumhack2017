export default function ($q, $http) {
    let URL = 'http://139.162.227.142:8080';

    return {
        getStreets() {
            return $http.get(URL + '/addressManagement/api/addressManagement/v1/address')
                .then(response => response.data);
        },

        geyWaypath(street, base) {
            return $http.post(URL + '/tmforum-address-gis-distance/gisaddress/api/v1/waypath?streetName=' + street, base)
                .then(response => response.data)
                .then(points => {
                    return points.map(p => ({
                        latitude: parseFloat(p.latitude),
                        longitude: parseFloat(p.longitude),
                    }));
                });
        },

        getWaypaths() {
            return $q((resolve, reject) => {
                this.getStreets().then(places => {
                    let base = _.filter(places, { streetName: 'DroneBase' })[0];
                    let streets =  _.uniqBy(places, 'streetName').filter(p => p.streetName != 'DroneBase').map(p => p.streetName);

                    let load = (index) => {
                        if (! streets[index]) {
                            return resolve(streets);
                        }

                        this.geyWaypath(streets[index], base).then(points => {
                            streets[index] = points;

                            load(index + 1);
                        });
                    };

                    load(0);
                });
            });
        },
    };
};
