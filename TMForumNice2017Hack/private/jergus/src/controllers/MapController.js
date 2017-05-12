export default function (Map, Api) {
    this.polylines = [];
    this.map = Map.southampton();

    Map.ready().then(() => {
        Api.getWaypaths()
            .then(paths => {
                paths.forEach((path, index) => {
                    this.polylines.push(Map.polyline(path, index));
                });
            });
    });
};
