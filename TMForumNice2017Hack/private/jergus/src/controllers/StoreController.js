export default function (HuaweiHTTPService, SalesForceService, $uibModal) {

    this.basket = [];

    HuaweiHTTPService.get_balance((x) => {
        console.log(x.data);
    })

    this.open = function (id) {

        let product = this.products.find(product => id == product.Id);

        let modalInstance = $uibModal.open({
            animation: true,
            component: 'amountModalComponent',
            resolve: {
                items: function () {
                    return {
                        product:product
                    }
                }
            }
        });

        modalInstance.result.then(function (selectedItem) {
            console.log(selectedItem);
        }, function () {

        });


    }


    SalesForceService.get_products().then((response) => {
        this.products = response.data.map(product => {
            product.category_name = product['category.name'];
            var components = product.description.split('|');
            product.description = components[0];
            product.image = components[1];
            return product;
        });
    });

    this.foo = function () {
        console.log('foo has been called');
    }

    this.add = function (item) {
        this.basket.push(item);
    }
}

