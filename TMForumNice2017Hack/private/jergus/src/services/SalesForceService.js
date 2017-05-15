import env from '../.env'

export default function ($http) {



    return {

        get_products:  () => {
           $http.defaults.headers.common.Authorization = env.SF_TOKE;
           return $http.get(env.SF_URL + '/productOffering');
        },

        send_order: () => {

        }


    }

}