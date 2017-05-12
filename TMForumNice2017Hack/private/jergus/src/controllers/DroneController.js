import env from '../.env'
export default  function (DroneHTTPService) {

    this.drone_names =
      [
        'solent_drone_1',
        'solent_drone_2',
        'solent_drone_3',
        'solent_drone_4'
    ];


    DroneHTTPService.get_drones().then(results => {
        this.drones = results.data._embedded.vehicle;
        this.create_drones();
    });

    this.create_drones  = () => {
        this.drone_names.map((name) => {
           if(!this.drones.find(drone => drone.name == name)) {
              DroneHTTPService.create_drone(name).then((result) => {
                  console.log('drone was created');
              }, error =>  {
                  console.log('there was an error' + error);
              });
           } else {

           }
        })
    }








}