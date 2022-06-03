## About
This repository represents a sample merchant server for a merchant integrating with the [PayPal iOS-SDK](https://github.com/paypal/iOS-SDK) and [PayPal Android-SDK](https://github.com/paypal/Android-SDK). This repository uses sample PayPal merchant staging credentials to create PayPal orderIDs and universal access tokens, and to capture orders.

**Note:** The PayPal iOS SDK and Android SDK are both still in development and not meant for public consumption.

## Build and run locally
To build and run locally, use the command: `./mvnw spring-boot:run -Dspring-boot.run.profiles=local`. Running this command will install Maven and all necessary dependencies.

If everything worked, you should be able to hit `http://localhost:5000/id-token?countryCode=US`.

## Switching Merchant Account/ Authentication

To change the merchant account used on this server, update the credentials in `application-local.properties`.

After making this change, execute both the build and run steps in the *Build and run locally* section above.

## Deploying

The app is currently deployed to Heroku at this url: https://ppcp-sample-merchant-sand.herokuapp.com.

#### Deploying to sandbox

To deploy to sandbox, first add a git remote called sandbox:

`git remote add sandbox https://git.heroku.com/ppcp-sample-merchant-sand.git`

Pushing your changes to this remote will automatically deploy them:

`git push sandbox master`

#### Deploying to production

To deploy to production, first add a git remote called heroku:

`git remote add heroku https://git.heroku.com/ppcp-sample-merchant-prod.git`

Pushing your changes to this remote will automatically deploy them:

`git push heroku master`
