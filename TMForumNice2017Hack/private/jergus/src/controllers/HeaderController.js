export default function($state, HuaweiHTTPService, ShipCompany, $scope) {

        HuaweiHTTPService.get_balance().then(response => {
            response.data.remainedAmount.amount;
        })

}