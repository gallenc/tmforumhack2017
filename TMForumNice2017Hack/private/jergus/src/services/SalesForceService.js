import env from '../.env'

export default function ($http) {



    return {

        get_products:  () => {
           return $http.get(env.SF_URL + '/productOffering', {
               headers : {
                   'authorization' : env.SF_TOKE
               }
            });
        },

        get_product: (id) => {
            return $http.get(env.SF_URL + '/productOffering/' + id, {
                headers : {
                    'authorization' : env.SF_TOKE
                }
            });
        },

        send_order: () => {

        }


    }

}