export default function (HuaweiHTTPService,SalesForceService, $uibModal) {

    this.basket = [];
    let modalInstance;

    HuaweiHTTPService.get_balance((response) => {
        console.log(reponse.data);
    })

    HuaweiHTTPService.charge_amount(this.total_amount).then((response) => {
        console.log(reponse.data);
    })

    this.open = function () {
       let modalInstance = $uibModal.open({
            animation: true,
            component: 'amountModalComponent',
            resolve: {
                items: function () {
                    return 'foo';
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
            product.charge_per = components[2];
            product.unit = components[3];
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

