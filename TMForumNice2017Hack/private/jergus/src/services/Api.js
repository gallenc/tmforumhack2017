export default function ($q, $http) {
    let URL = 'http://139.162.227.142:8080';

    return {
        getCollections() {
            return $http.get(URL + '/addressManagement/api/addressManagement/v1/address')
                .then(response => response.data)
                .then(collections => {
                    return {
                        base: _.filter(collections, { streetName: 'DroneBase' })[0],
                        collections:  _.uniqBy(collections, 'streetName').filter(p => p.streetName != 'DroneBase').map(p => p.streetName),
                    };
                });
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

        getWaypaths(collections, base) {
            return $q((resolve, reject) => {
                let load = (index) => {
                    if (! collections[index]) {
                        return resolve(collections);
                    }

                    this.geyWaypath(collections[index], base).then(points => {
                        collections[index] = points;

                        load(index + 1);
                    });
                };

                load(0);
            });
        },
    };
};
