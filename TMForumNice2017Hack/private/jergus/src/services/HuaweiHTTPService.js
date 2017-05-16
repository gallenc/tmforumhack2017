import env from '../.env'

export  default  function ($http) {
    return {
        get_balance: () => {
            return $http.get(env.HU_URL + '/openapi1/V1/rating/balancemanagement/v1/buckets?productid=3313810128531');
        }

    }
}