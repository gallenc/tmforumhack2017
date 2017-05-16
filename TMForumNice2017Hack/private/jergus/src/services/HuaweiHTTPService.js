import env from '../.env'

export  default  function ($http) {
    return {
        get_balance: () => {
            return $http.get(env.API_URL + '/huawei/balance')
        },
        charge_amount: (amount) => {
            return $http.post(env.API_URL + '/huawei/deduct', {'amount': amount})
        }
    }
}