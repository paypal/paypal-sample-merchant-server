## Setup
After cloning this repo, add a file called `.env` in the root directory. Add `TOKEN=xxxx` where xxxx is the value found in the Team SDK Heroku dashboard under Settings -> Config Vars.

## Build and run locally
- To build, run `./gradlew clean build`.
- To run locally with Heroku, run `heroku local web`.
    - If you need to install the Heroku CLI, run `brew tap heroku/brew && brew install heroku`.

If everything worked, you should be able to hit `http://localhost:5000/order-validation-info`.

**Note:** This can't be deployed to Heroku at the moment, because we aren't able to hit `/v2/checkout/orders` outside of the PayPal network.

## Troubleshooting

### PayPal v2/orders Request Limitations
The PP merchant account we are using to generate an `orderID` has a high account balance which results in limitations on our ability to use this endpoint. Follow these steps to temporarily lift restrictions on the current merchant account.

1. Log into admin.msmaster.qa.paypal.com with the username `concordia_admin` and password `1111111`.
2. Search on the email `rtimoschuk-us-bus-onb-ppcp-approve-seller15@paypal.com`. It will take a while.
3. On the red banner where it says "Limited - High", click "View Limitations" and wait a bit.
4. Find and click the "Edit Limitations" button.
5. In the banner that says "Current Limitations", click the link in the white text that says "Remove Limitations".
6. Check all of the checkboxes. You must enter a memo.
