export default function (SalesForceService, $uibModal) {

    this.basket = [];
    let modalInstance;

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

