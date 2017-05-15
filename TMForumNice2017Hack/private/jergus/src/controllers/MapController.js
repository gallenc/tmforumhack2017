export default function ($interval, Map, Api) {
    this.map = Map.southampton();

    this.updateDrones = () => {
        Api.getDrones().then(drones => {
            Map.drones(drones).forEach(drone => {
                Object.assign(_.filter(this.drones, { id: drone.id })[0], drone);
            });
        });
    };

    Map.ready().then(() => {
        $interval(() => {
            this.updateDrones();
        }, 1000);

        $interval(() => {
            Api.ping();
        }, 1000 * 30);

        Api.getDrones().then(drones => {
            this.drones = Map.drones(drones);
        });

        Api.getDroneBase().then(coords => {
            this.base = Map.base(coords);
        });

        Api.getWaypaths().then(waypaths => {
            this.polylines = Map.waypathsToPolylines(waypaths);
        });
    });
};
