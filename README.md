[![Build Status](https://github.com/ITECOMMPAY/mobile-sdk-android-ui/actions/workflows/master_push_pr.yml/badge.svg)]()
![Maven Central Version](https://img.shields.io/maven-central/v/com.ecommpay/msdk-ui-common)

## Overview

Mobile SDK UI  for Android is a software development kit that can be used to integrate Android applications with the [Ecommpay](https://ecommpay.com/) payment platform.

It provides the functionality for interaction of customers with the user interface and for interaction of a mobile application with the payment platform which allows sending and receiving necessary information during payment processing.

It can be embedded in mobile applications developed for Android **API 21** or later.

## Quickstart

**How to use example project**

1. Open `integration-example` module;
2. Open `build.gradle` and pass values to `projectId` and `projectSecretKey`;
3. Run sync gradle task.

**Importing libraries in your project**

The SDK for Android libraries can be imported via MavenCentral. To import the libraries via MavenCentral you need to add the following dependencies to the `dependencies` section:

```
implementation "com.ecommpay:msdk-ui:LATEST_VERSION"
implementation "com.ecommpay:msdk-core-android:LATEST_VERSION"
```

## Use

### Opening payment form

SDK UI for Android supports such actions as performing one-time purchases and placing authorisation holds as part of executing two-step purchases, registering COF purchases and performing payment card verification. To initiate these actions, you need a certain parameter set. The required minimum of parameters is passed in the  `EcmpPaymentInfo`  object while other parameters can be passed in the  `paymentOptions`  object, requested from the customer, or received from the payment platform.

To open the payment form:

1. Create the `EcmpPaymentInfo` object.

This object must contain the following required parameters:

- `projectId`  (Integer) — a project identifier assigned by Ecommpay
- `paymentId`  (String) — a payment identifier unique within the project
- `paymentCurrency`  (String) — the payment currency code in the ISO 4217 alpha-3 format
- `paymentAmount`  (Long) — the payment amount in the smallest currency units
- `customerId`  (String) — a customer's identifier within the project
- `signature`  (String) — a request signature generated after all required parameters have been specified

```
val ecmpPaymentInfo = EcmpPaymentInfo(
    projectId = 77655,
    paymentId = "payment_322",
    paymentAmount = 100,
    paymentCurrency = "USD",
    paymentDescription = "Cosmoshop payment",
    customerId = "customer_003",
    //Code of the customer's country
    regionCode = "DE",
    //Token associated with certain payment data
    token = "o8i7u65y4t3rkjhgfdw3456789oikjhgfdfghjkl...",
    //Payment interface language code
    languageCode = "de",
    //Data to be included in the notification with the list of the purchased items
    receiptData = "eyAKICAicG9zaXRpb25zIjpbIAogICAgIIjoxLAogICAgICAgICJhbW91bnQiOjU5OTAsCiAgQ==",
    //Parameter to enable hiding or displaying saved payment instruments
    hideSavedWallets = false,
    //Identifier of the preselected payment method
    forcePaymentMethod = "card"
)
```

2. Sign the parameters contained in the `EcmpPaymentInfo` object.

```
ecmpPaymentInfo.signature = SignatureGenerator
 .generateSignature(
  paramsToSign = ecmpPaymentInfo.getParamsForSignature(),
  secret = SECRET_KEY
 )
```

3. Create the `EcmpPaymentOptions` object that contains the required parameter `actionType` (enum) with the value specifying the required operation type:

- `EcmpActionType.Sale`
- `EcmpActionType.Auth`
- `EcmpActionType.Verify`
- `EcmpActionType.Tokenize`

In addition to the required  `EcmpPaymentInfo`  object and the  `actionType`  parameter, the following example contains several additional parameters including the  `EcmpAdditionalFields`  object with data specified in the fields that are used for collecting customer information.

```
val paymentOptions = paymentOptions {
    //Required object for payment
    paymentInfo = ecmpPaymentInfo

    //Optional objects for payment
    //EcmpActionType.Sale by default
    actionType = EcmpActionType.Sale

    //GooglePay configurations
    isTestEnvironment = true
    merchantId = BuildConfig.GPAY_MERCHANT_ID
    merchantName = "Example Merchant Name"

    additionalFields {
        field {
            type = EcmpAdditionalFieldType.CUSTOMER_EMAIL,
            value = "mail@mail.com"
        }
        field {
            type = EcmpAdditionalFieldType.CUSTOMER_FIRST_NAME,
            value = "firstName"
        }
    }
    screenDisplayModes {
        mode(EcmpScreenDisplayMode.HIDE_SUCCESS_FINAL_SCREEN)
        mode(EcmpScreenDisplayMode.HIDE_DECLINE_FINAL_SCREEN)
    }
    recurrentData = EcmpRecurrentData()
    recipientInfo = EcmpRecipientInfo()

    //Parameter to enable hiding or displaying scanning cards feature
    hideScanningCard = false,

    //Custom theme
    isDarkTheme = false
    brandColor = "#000000" //#RRGGBB
    //Any bitmap image
    logoImage = BitmapFactory.decodeResource(
        resources,
        R.drawable.example_logo
    )
}
```

4. Create the `Ecommpay` object.

If necessary, you can open the payment form in the test mode in order to get information about errors if there were any when payment parameters were specified or to test processing payments with a certain payment result. In the `Ecommpay` object, specify the `EcmpMockModeType.SUCCESS` value for the `mockModeType` parameter (if you need to receive `Success` payment result). You can also pass values `EcmpMockModeType.DECLINE` (if you need to receive `Decline` payment result) and `EcmpMockModeType.DISABLED` (if you need to switch to the production mode).

```
val sdk = Ecommpay(
   context = applicationContext,
   paymentOptions = paymentOptions,
   mockModeType = Ecommpay.EcmpMockModeType.DISABLED
)
```

5. Open the payment form.

```
startActivityForResult.launch(sdk.intent)
```

6. Handle result.

```
val startActivityForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
result ->
 val data = result.data
 when (result.resultCode) {
  Ecommpay.RESULT_SUCCESS -> {
   val payment = Json.decodeFromString<Payment?>(
    data?.getStringExtra(Ecommpay.EXTRA_PAYMENT).toString()
   )
   when {
    payment?.token != null -> {
     Toast.makeText(this,"Tokenization was finished successfully. Your token is ${payment.token}",Toast.LENGTH_SHORT).show()
     Log.d("PaymentSDK","Tokenization was finished successfully. Your token is ${payment.token}")
    }
    else -> {
     Toast.makeText(this,"Payment was finished successfully",Toast.LENGTH_SHORT).show()
     Log.d("PaymentSDK", "Payment was finished successfully")
    }
   }
  }
  Ecommpay.RESULT_CANCELLED -> {
   Toast.makeText(this, "Payment was cancelled", Toast.LENGTH_SHORT).show()
   Log.d("PaymentSDK", "Payment was cancelled")
  }
  Ecommpay.RESULT_DECLINE -> {
   Toast.makeText(this, "Payment was declined", Toast.LENGTH_SHORT).show()
   Log.d("PaymentSDK", "Payment was declined")
  }
  Ecommpay.RESULT_ERROR -> {
   val errorCode = data?.getStringExtra(Ecommpay.EXTRA_ERROR_CODE)
   val message = data?.getStringExtra(Ecommpay.EXTRA_ERROR_MESSAGE)
   Toast.makeText(this,"Payment was interrupted. See logs",Toast.LENGTH_SHORT).show()
   Log.d("PaymentSDK","Payment was interrupted. Error code: $errorCode. Message: $message")
  }
 }
}
```

## Capabilities

The following functional capabilities are supported by SDK UI for Android:

- Processing different types of payments made with cards and Google Pay as well as other payment methods available for the merchant's project. Supported payment types include:
  - One-time one-step purchases.
  - One-time two-step purchases (an authorisation hold can be placed via the SDK and subsequent debiting of the authorised amount is carried out via  Gate  or  Dashboard).
  - COF purchases (they can be registered via the SDK and then managed via  Gate  or  Dashboard).
- Performing payment card verification (it involves debiting a zero amount from the customer's card).
- Checking current payment information.
- Auxiliary procedures and additional capabilities to boost payment acceptance rates:
  - Submission of additional payment information.
  - Payment retries.
  - Cascade payment processing.
  - Collecting customer data.
- Additional capabilities to improve user experience:
  - Saving customer payment data.
  - Sending email notifications with the list of purchased items to customers.
- Customising the appearance of the payment interface including the colour scheme settings and the option to add the logo.

In case of card and Google Pay payments, the payment interface described in this article is used. With other payment methods,  Payment Page  is used during payment processing.

## Workflow

Generally, the following workflow is relevant when one-step purchases are processed with the use of SDK UI for Android.
![](https://developers.ecommpay.com/en/images/sdk/android/en_sdk_ui_core_functional.svg)

1. In the user interface of a mobile application, the customer initiates a purchase by clicking the payment button or in a different fashion set up on the merchant side.
2. In the mobile application, a set of parameters for creating a payment session is generated. Then, with the help of SDK UI for Android, this set is converted into a string for signing, and the string is sent to the server side of the merchant web service.
3. On the server side of the merchant web service, the parameters can be checked and supplemented if necessary, and the signature to the final parameter set is generated, following which the prepared data is sent back to SDK UI for Android.
4. With the help of SDK UI for Android, a payment session is initiated in the payment platform.
5. On the payment platform side, the payment interface is prepared in accordance with the invocation parameters, and the data for opening the interface is passed to the customer's device.
6. In the mobile application, the payment form is displayed to the customer.
7. The customer selects a payment method (if no method was selected when the payment session was initiated), specifies the necessary information, and confirms the purchase.
8. SDK UI for Android sends a purchase request to the payment platform.
9. On the payment platform side, the payment is registered and all necessary technical actions are performed; these actions include sending the required data to the payment environment—to the providers and payment systems.
10. The payment is processed in the payment environment. Then the payment result information is received in the payment platform.
11. In the payment platform, the information about the payment result is processed and a callback is sent to the server side of the web service.
12. The information about the purchase result is sent from the payment platform to SDK UI for Android.
13. The notification with the result information is displayed to the customer in the user interface.

## Interface

When card and Google Pay payments are processed, the customer interacts with the user interface designed by the  ecommpay specialists. This user interface can be customised: you can change its colour and add your company's logo.

![](https://developers.ecommpay.com/en/images/sdk/android/all_sdk_ui_core_design_color.png)

## Setup

To integrate the web service with the [Ecommpay](https://ecommpay.com/)  payment platform by using SDK UI for Android:

1. Address the following organisational issues of interaction with  ecommpay:
    1. If your company has not obtained a project identifier and a secret key for interacting with  ecommpay, submit the application for connecting to the  ecommpay  payment platform.
    2. If your company has obtained a project identifier and a secret key for interacting with  ecommpay, inform the technical support specialists about the company's intention to integrate by using SDK UI for Android and coordinate the procedure of testing and launching the functionality.
2. Complete the following preliminary technical steps:
    1. Download and link the SDK UI for Android libraries.
    2. Ensure the collection of data necessary for opening the payment form. The minimum data set needed in order to open the payment form consists of the project, payment, and customer identifiers as well as of the payment amount and currency.
    3. Ensure signature generation for the data on the server side of the mobile application.
    4. Ensure the receipt of and the response to the notifications from SDK UI for Android as well as the receipt of and the response to the callbacks from the payment platform on the web service side.
3. With the technical support specialists, coordinate the timeline and the main steps of integrating, testing (including testing available payment methods), and launching the solution.
    1. For testing, use the test project identifier and the details of  [test cards](https://developers.ecommpay.com/en/en_PP_TestCards.html).
    2. For switching to the production mode, change the value of the test project identifier to the value of the production project identifier received from  ecommpay.

If you have any questions about working with SDK UI for Android, contact the  ecommpay  technical support specialists ([support@ecommpay.com](mailto:support@ecommpay.com)).

For more detailed information see [docs](https://developers.ecommpay.com/en/en_sdk_ui_and_core_android.html#en_sdk_ui_and_core_android).
