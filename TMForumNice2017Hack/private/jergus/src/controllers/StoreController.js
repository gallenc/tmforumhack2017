export default function (SalesForceService) {

    this.basket = [];

    SalesForceService.get_products().then((response) => {
        this.products = response.data.map(product => {
            product.category_name = product['category.name'];
            return product;
        });
    });

    this.add = function (item) {
        this.basket.push(item);
    };
};

