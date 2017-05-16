# TMForumNice2017Hack

## Getting started with the project!

### Layout
+ The project is laid out in with [Documentation](https://github.com/gallenc/tmforumhack2017/tree/master/TMForumNice2017Hack/documentation) required for the API's and OpenNMS API URLs being stored there.
+ Work for the OpenNMS side of this application can be found under the [OpenNMS-Config](https://github.com/gallenc/tmforumhack2017/tree/master/TMForumNice2017Hack/opennms-config) folder.
+ [Examples](https://github.com/gallenc/tmforumhack2017/tree/master/TMForumNice2017Hack/exampleHttpRequester) are shown using example requests
+ All work for the OpenHack Event is under the [Private](https://github.com/gallenc/tmforumhack2017/tree/master/TMForumNice2017Hack/private) folder

# Installation
In order to get the application running you only require 2 folders in the [Private](https://github.com/gallenc/tmforumhack2017/tree/master/TMForumNice2017Hack/private) folder
+  [jergus](https://github.com/gallenc/tmforumhack2017/tree/master/TMForumNice2017Hack/private/jergus)
+  [api](https://github.com/gallenc/tmforumhack2017/tree/master/TMForumNice2017Hack/private/api)

In order to get the GUI and the API running you need to have the following installed on the machine
+ [NodeJS](https://nodejs.org/en/),
+ [Composer](https://getcomposer.org/)
+ [Yarn](https://yarnpkg.com/en/)

Set up the .env file by creating a new file under the `private/jergus/src` folder again and add these to the file
```
SF_URL: 'https://eu11.salesforce.com/services/apexrest/openAPI',
SF_TOKE: 'Bearer "YOUR SALESFORCE API TOKEN"',
DRONE_URL: '52.56.254.135:1235/',
GOOGLE_MAP_KEY : 'YOUR GOOGLE MAP KEY'
```

Once the install is completed navigate to the jergus folder again and run these commands in sequence
    +   `yarn install`
    +   `yarn run serve`
This will run on the your local host.

After this you will need to install and integrate the [Laravel](https://laravel.com/) API; To do this you will need to follow and install the [Homestead Installation](https://laravel.com/docs/5.4/homestead) instructions and be sure to add the `private/api/public` to you site maps.

Once you have completed this whatever local domain homestead has assigned the site map for our API add the site domain to the `private/jergus/src` .env file
> API_URL: "YOUR DOMAIN HERE",

And then navigate to your site using the URL provided from the `yarn run serve` command to navigate to the interface on your local host, this is typically [http://localhost:8080](http://localhost:8080).
