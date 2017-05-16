export default function ($timeout, HuaweiHTTPService, SalesForceService, $uibModal, ngToast) {

    this.open = function (id) {

        let product = this.products.find(product => id == product.Id);

        let modalInstance = $uibModal.open({
            animation: true,
            component: 'amountModalComponent',
            resolve: {
                items: function () {
                    return {
                        product: product
                    }
                }
            }
        });

        modalInstance.result.then( (selectedItem) => {
            this.charge(selectedItem.item.product, selectedItem.quantity, selectedItem.total);
        }, function () {

        });


    }


    SalesForceService.get_products().then((response) => {
        this.products = response.data.map(product => {
            product.category_name = product['category.name'];
            var components = product.description.split('|');
            product.description = components[0];
            product.image = components[1];
            product.charge_per = components[2];
            product.unit = components[3];
            return product;
        });
    });

    this.charge = function ($product, $quantity, $amount) {
        $timeout(() => {
            ngToast.create("Amount "+$amount+" Charged for "+$quantity+" of "+$product.name);
        }, 500);

        HuaweiHTTPService.charge_amount($amount);
    }
}

