export default function (Map, Api) {
    this.map = Map.southampton();

    Api.getDroneBase().then(coords => {
        this.base = Map.base(coords);
    });

    Api.getWaypaths().then(waypaths => {
        this.polylines = Map.waypathsToPolylines(waypaths);
    });
};
