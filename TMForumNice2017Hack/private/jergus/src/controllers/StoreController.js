export default function (SalesForceService) {

    SalesForceService.get_products().then((response) => {
        console.log(response);
        this.product = response.data;
    })
};

