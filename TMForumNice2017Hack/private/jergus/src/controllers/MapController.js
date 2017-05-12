export default function (Map, Api) {
    this.polylines = [];
    this.map = Map.southampton();

    this.draw = (collections) => {
        collections.forEach((collection, index) => {
            this.polylines.push(Map.polyline(collection, index));
        });
    };

    Map.ready().then(() => {
        Api.getCollections()
            .then(({ collections, base }) => {
                Api.getWaypaths(collections, base).then(c => this.draw(c));

                this.base = Map.base(base);
            });
    });
};
