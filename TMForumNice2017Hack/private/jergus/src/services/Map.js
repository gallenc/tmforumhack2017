export default function (uiGmapGoogleMapApi) {
    return {
        ready() {
            return uiGmapGoogleMapApi;
        },

        southampton() {
            return {
                center: {
                    latitude: 50.900,
                    longitude: -1.42,
                },
                zoom: 14,
            };
        },

        base(base) {
            return {
                id: 'droneBase',
                coords: base,
                options: {
                    icon: require('../img/base.png'),
                },
            };
        },

        waypathsToPolylines(waypaths) {
            return waypaths.map((path, index) => {
                return this.polyline(path.points, this.colors(index));
            });
        },

        polyline(path, color) {
            return {
                path: path,
                stroke: {
                    color: color,
                    weight: 3
                },
                geodesic: true,
                visible: true,
                icons: [{
                    icon: {
                        path: google.maps.SymbolPath.BACKWARD_OPEN_ARROW
                    },
                    offset: '25px',
                    repeat: '50px'
                }],
            };
        },

        colors(index) {
            return [
                '#1abc9c', '#2ecc71', '#3498db', '#9b59b6', '#34495e',
                '#f39c12', '#d35400', '#c0392b', '#bdc3c7', '#7f8c8d',
                '#f1c40f', '#e67e22', '#e74c3c', '#ecf0f1', '#95a5a6',
                '#16a085', '#27ae60', '#2980b9', '#8e44ad', '#2c3e50',
            ][index];
        },

        drones(drones) {
            return drones.map(drone => this.drone(drone));
        },

        drone(drone) {
            return {
                id: drone.id,
                coords: drone.coords,
                options: {
                    visible: drone.active,
                    icon: require('../img/drone-icon.png'),
                },
            };
        }
    };
};
