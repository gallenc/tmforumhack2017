export default function ($scope, Map, Api) {
    this.polylines = [];
    this.collections = [];
    this.map = Map.southampton();

    $scope.$watch('vm.collections', collections => {
        this.polylines = [];

        collections.filter(c => c.hide != true).forEach((collection, index) => {
            this.polylines.push(Map.polyline(collection.waypath, index));
        });
    }, true);

    Map.ready().then(() => {
        Api.getCollections()
            .then(({ collections, base }) => {
                Api.getWaypaths(collections, base).then(collections => {
                    this.collections = collections;
                });

                this.base = Map.base(base);
            });
    });
};
