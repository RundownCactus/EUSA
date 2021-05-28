package com.salmanqureshi.eusa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class PayByJazzCash extends AppCompatActivity {
    String postData = "";
    private WebView mWebView;
    private static final String paymentReturnUrl="http://localhost/order.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_by_jazz_cash);
        mWebView = (WebView) findViewById(R.id.activity_payment_webview);
        // Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new MyWebViewClient());
        webSettings.setDomStorageEnabled(true);
        mWebView.addJavascriptInterface(new FormDataInterface(), "FORMOUT");
        String price = getIntent().getStringExtra("price");
        String[] values = price.split("\\.");
        price = values[0];
        price = price + "00";
        Date Date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddkkmmss");
        String DateString = dateFormat.format(Date);
        // Convert Date to Calendar
        Calendar c = Calendar.getInstance();
        c.setTime(Date);
        c.add(Calendar.HOUR, 1);
        // Convert calendar back to Date
        Date currentDateHourPlusOne = c.getTime();
        String expiryDateString = dateFormat.format(currentDateHourPlusOne);
        String TransactionIdString = "T" + DateString;

        String JazzCash_MerchantID = "MC20233";
        String JazzCash_Password = "9y2905456a";
        String JazzCash_IntegritySalt = "x543uux50w";
        String JazzCash_ReturnURL = paymentReturnUrl;
        String JazzCash_Amount = price;
        String JazzCash_TxnDateTime = DateString;
        String JazzCash_TxnExpiryDateTime = expiryDateString;
        String JazzCash_TxnRefNo = TransactionIdString;
        String JazzCash_Version = "1.1";
        String JazzCash_TxnType = "";
        String JazzCash_Language = "EN";
        String JazzCash_SubMerchantID = "";
        String JazzCash_BankID = "TBANK";
        String JazzCash_ProductID = "RETL";
        String JazzCash_TxnCurrency = "PKR";
        String JazzCash_BillReference = "billRef";
        String JazzCash_Description = "Description of transaction";
        String JazzCash_SecureHash = "";
        String JazzCash_mpf_1 = "1";
        String JazzCash_mpf_2 = "2";
        String JazzCash_mpf_3 = "3";
        String JazzCash_mpf_4 = "4";
        String JazzCash_mpf_5 = "5";


        String sortedString = "";
        sortedString += JazzCash_IntegritySalt + "&";
        sortedString += JazzCash_Amount + "&";
        sortedString += JazzCash_BankID + "&";
        sortedString += JazzCash_BillReference + "&";
        sortedString += JazzCash_Description + "&";
        sortedString += JazzCash_Language + "&";
        sortedString += JazzCash_MerchantID + "&";
        sortedString += JazzCash_Password + "&";
        sortedString += JazzCash_ProductID + "&";
        sortedString += JazzCash_ReturnURL + "&";
        sortedString += JazzCash_TxnCurrency + "&";
        sortedString += JazzCash_TxnDateTime + "&";
        sortedString += JazzCash_TxnExpiryDateTime + "&";
        sortedString += JazzCash_TxnRefNo + "&";
        sortedString += JazzCash_Version + "&";
        sortedString += JazzCash_mpf_1 + "&";
        sortedString += JazzCash_mpf_2 + "&";
        sortedString += JazzCash_mpf_3 + "&";
        sortedString += JazzCash_mpf_4 + "&";
        sortedString += JazzCash_mpf_5;
        JazzCash_SecureHash = php_hash_hmac(sortedString, JazzCash_IntegritySalt);

        try {
            postData += URLEncoder.encode("pp_Version", "UTF-8")
                    + "=" + URLEncoder.encode(JazzCash_Version, "UTF-8") + "&";
            postData += URLEncoder.encode("pp_TxnType", "UTF-8")
                    + "=" + JazzCash_TxnType + "&";
            postData += URLEncoder.encode("pp_Language", "UTF-8")
                    + "=" + URLEncoder.encode(JazzCash_Language, "UTF-8") + "&";
            postData += URLEncoder.encode("pp_MerchantID", "UTF-8")
                    + "=" + URLEncoder.encode(JazzCash_MerchantID, "UTF-8") + "&";
            postData += URLEncoder.encode("pp_SubMerchantID", "UTF-8")
                    + "=" + JazzCash_SubMerchantID + "&";
            postData += URLEncoder.encode("pp_Password", "UTF-8")
                    + "=" + URLEncoder.encode(JazzCash_Password, "UTF-8") + "&";
            postData += URLEncoder.encode("pp_BankID", "UTF-8")
                    + "=" + URLEncoder.encode(JazzCash_BankID, "UTF-8") + "&";
            postData += URLEncoder.encode("pp_ProductID", "UTF-8")
                    + "=" + URLEncoder.encode(JazzCash_ProductID, "UTF-8") + "&";
            postData += URLEncoder.encode("pp_TxnRefNo", "UTF-8")
                    + "=" + URLEncoder.encode(JazzCash_TxnRefNo, "UTF-8") + "&";
            postData += URLEncoder.encode("pp_Amount", "UTF-8")
                    + "=" + URLEncoder.encode(JazzCash_Amount, "UTF-8") + "&";
            postData += URLEncoder.encode("pp_TxnCurrency", "UTF-8")
                    + "=" + URLEncoder.encode(JazzCash_TxnCurrency, "UTF-8") + "&";
            postData += URLEncoder.encode("pp_TxnDateTime", "UTF-8")
                    + "=" + URLEncoder.encode(JazzCash_TxnDateTime, "UTF-8") + "&";
            postData += URLEncoder.encode("pp_BillReference", "UTF-8")
                    + "=" + URLEncoder.encode(JazzCash_BillReference, "UTF-8") + "&";
            postData += URLEncoder.encode("pp_Description", "UTF-8")
                    + "=" + URLEncoder.encode(JazzCash_Description, "UTF-8") + "&";
            postData += URLEncoder.encode("pp_TxnExpiryDateTime", "UTF-8")
                    + "=" + URLEncoder.encode(JazzCash_TxnExpiryDateTime, "UTF-8") + "&";
            postData += URLEncoder.encode("pp_ReturnURL", "UTF-8")
                    + "=" + URLEncoder.encode(JazzCash_ReturnURL, "UTF-8") + "&";
            postData += URLEncoder.encode("pp_SecureHash", "UTF-8")
                    + "=" + JazzCash_SecureHash + "&";
            postData += URLEncoder.encode("ppmpf_1", "UTF-8")
                    + "=" + URLEncoder.encode(JazzCash_mpf_1, "UTF-8") + "&";
            postData += URLEncoder.encode("ppmpf_2", "UTF-8")
                    + "=" + URLEncoder.encode(JazzCash_mpf_2, "UTF-8") + "&";
            postData += URLEncoder.encode("ppmpf_3", "UTF-8")
                    + "=" + URLEncoder.encode(JazzCash_mpf_3, "UTF-8") + "&";
            postData += URLEncoder.encode("ppmpf_4", "UTF-8")
                    + "=" + URLEncoder.encode(JazzCash_mpf_4, "UTF-8") + "&";
            postData += URLEncoder.encode("ppmpf_5", "UTF-8")
                    + "=" + URLEncoder.encode(JazzCash_mpf_5, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        mWebView.postUrl("https://sandbox.jazzcash.com.pk/CustomerPortal/transactionmanagement/merchantform/", postData.getBytes());
    }

    private static class MyWebViewClient extends WebViewClient {
        private final String jsCode ="" + "function parseForm(form){"+
                "var values='';"+
                "for(var i=0 ; i< form.elements.length; i++){"+
                "   values+=form.elements[i].name+'='+form.elements[i].value+'&'"+
                "}"+
                "var url=form.action;"+
                "console.log('parse form fired');"+
                "window.FORMOUT.processFormData(url,values);"+
                "   }"+
                "for(var i=0 ; i< document.forms.length ; i++){"+
                "   parseForm(document.forms[i]);"+
                "};";

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if(url.equals(paymentReturnUrl)){
                System.out.println("return url cancelling");
                view.stopLoading();
                return;
            }
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if(url.equals(paymentReturnUrl)){
                return;
            }
            view.loadUrl("javascript:(function() { " + jsCode + "})()");
            super.onPageFinished(view, url);
        }
    }

    private class FormDataInterface {
        @JavascriptInterface
        public void processFormData(String url, String formData) {
            Intent i = new Intent(PayByJazzCash.this, CurrentJobMap.class);
            if (url.equals(paymentReturnUrl)) {
                String[] values = formData.split("&");
                for (String pair : values) {
                    String[] nameValue = pair.split("=");
                    if (nameValue.length == 2) {
                        i.putExtra(nameValue[0], nameValue[1]);
                    }
                }
                setResult(RESULT_OK, i);
                finish();
            }
        }
    }

    public static String php_hash_hmac(String data, String secret) {
        String returnString = "";
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] res = sha256_HMAC.doFinal(data.getBytes());
            returnString = bytesToHex(res);

        }catch (Exception e){
            e.printStackTrace();
        }
        return returnString;
    }

    public static String bytesToHex(byte[] bytes) {
        final char[] hexArray = "0123456789abcdef".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0, v; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}