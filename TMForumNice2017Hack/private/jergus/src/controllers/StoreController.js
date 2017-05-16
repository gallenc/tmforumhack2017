export default function ($timeout, HuaweiHTTPService, SalesForceService, $uibModal, ngToast) {
    this.balance = 0;
    HuaweiHTTPService.get_balance().then(response => {
        this.balance = response.data[0].remainedAmount.amount;
    })

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
            ngToast.create("Amount £"+ (parseFloat($amount).toFixed(2)) +" Charged for "+$quantity+" of "+$product.name);
        }, 500);

        HuaweiHTTPService.charge_amount($amount)
            .then(() => {
                HuaweiHTTPService.get_balance().then(response => {
                    this.balance = response.data[0].remainedAmount.amount;
                })
            });
    }
}

