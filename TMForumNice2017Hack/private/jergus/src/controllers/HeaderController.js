export default function ($state, HuaweiHTTPService, ShipCompany) {

    this.balance = 0;
    HuaweiHTTPService.get_balance().then(response => {
        this.balance = response.data.remainedAmount.amount;
    })

}