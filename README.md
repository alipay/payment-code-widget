# payment-code-widget

A lightweight library provides UI widgets to display payment code in mobile applications. The dimension of the payment code is optimal and scanner-friendly.

## Objective
To make sure that the scanner can easily read the payment code and let mobile users make payment successfully

## Supported standard
 - Alipay CGCP (Contactless Gateway Code Protocol)

## How to use
### Installation
```
// Add the dependency in your build.gradle
dependencies {
    ...
    implementation 'com.alipay.iap:payment-code-widget:1.0.0'
}
```
### BarcodeView
Use the BarcodeView in the layout xml file.
```
<com.alipay.iap.widget.payment.cgcp.BarcodeView
    android:id="@+id/barcodeView"
    android:layout_width="match_parent"
    android:layout_height="200dp" />
```
Get the instance of the `BarcodeView` and use `setCode` method to pass in the payment code
```
BarcodeView barCodeView = findViewById(R.id.barcodeView);
barCodeView.setCode("28345678901234567890");
```
Please note that the`BarcodeView` automatically resizes the height if the given layout height is less than the recommended size (i.e., less than 10mm), and the width of the barcode (including left and right quiet zones) is capped at 65mm.

#### Adaptive Barcode Display in action

![screenshot](/images/image1.png)
- The width of the Payment code is capped within 65mm even though the width of the view is > 65mm
- The height of the Payment code is capped within 10mm OR 15% of the length of the central barcode even though the height of the view is > 10mm

![screenshot](/images/image2.png)
- The width of the Payment code is capped within 65mm even though the width of the view is > 65mm
- The height of the Payment code maintains at 10mm even though the height of the view is < 10mm

![screenshot](/images/image3.png)
- The width of the Payment code is adjusted to cope with view if the width of the view is < 65mm
- The height of the Payment code is capped within 10mm OR 15% of the length of the central barcode even though the height of the view is > 10mm

## Dependencies
- zxing-core [3.3.2](https://mvnrepository.com/artifact/com.google.zxing/core/3.3.2)

## Appendix
### Alipay CGCP Adaptive Barcode display

![](/images/cpcg.png)