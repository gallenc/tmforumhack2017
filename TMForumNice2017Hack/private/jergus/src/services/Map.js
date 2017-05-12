export default function (uiGmapGoogleMapApi) {
    return {
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
                coords: base.geoCode,
                options: {
                    icon: require('../img/base.png'),
                },
            };
        },

        ready() {
            return uiGmapGoogleMapApi;
        },

        polyline(path, index) {
            return {
                path: path,
                stroke: {
                    color: this.colors(index || 0),
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
                }]
            };
        },

        colors(index) {
            return [
                '#1abc9c', '#2ecc71', '#3498db', '#9b59b6', '#34495e',
                '#f39c12', '#d35400', '#c0392b', '#bdc3c7', '#7f8c8d',
                '#f1c40f', '#e67e22', '#e74c3c', '#ecf0f1', '#95a5a6',
                '#16a085', '#27ae60', '#2980b9', '#8e44ad', '#2c3e50',
            ][index];
        }
    };
};
