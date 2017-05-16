export default {
    templateUrl: '/views/modal.html',
    bindings: {
        resolve: '<',
        close: '&',
        dismiss: '&'
    },
    controller: function () {
        var $ctrl = this;
        this.amount = 0;

        $ctrl.$onInit = function () {
            $ctrl.items = $ctrl.resolve.items;
            console.log($ctrl.items);
        };

        $ctrl.ok = function () {
            $ctrl.close({$value: this.amount});
        };

        $ctrl.cancel = function () {
            $ctrl.dismiss({$value: 'cancel'});
        };
    }

}
