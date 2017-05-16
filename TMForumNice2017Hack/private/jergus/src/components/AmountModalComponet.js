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
        };

        $ctrl.ok = function () {
            $ctrl.close({$value: {
                item: $ctrl.items,
                quantity: $ctrl.quantity,
                total: ($ctrl.quantity * $ctrl.items.product.charge_per),
            }});
        };

        $ctrl.cancel = function () {
            $ctrl.dismiss({$value: 'cancel'});
        };
    }

}
