package exchange.setup;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.robotrader.ebinjoy999.robotrader.R;

import tools.SharedPreferenceManagerC;

/**
 * Created by ebinjoy999 on 13/01/18.
 */

public class CustomDialogClass  extends Dialog implements
        android.view.View.OnClickListener  , QRCodeReaderView.OnQRCodeReadListener {

    String exchange;
    public Activity contextActiviyt;
    public Dialog d;
    public Button yes, no;

    TextView textViewHint,txtTitle;
    Button buttonRemove;

    SharedPreferenceManagerC sharedPreferenceManagerC;
    public CustomDialogClass(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.contextActiviyt = a;
    }

    private QRCodeReaderView qrCodeReaderView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        sharedPreferenceManagerC = new SharedPreferenceManagerC(contextActiviyt);

        yes = (Button) findViewById(R.id.btn_yes);
        buttonRemove = (Button) findViewById(R.id.buttonRemove);
        textViewHint = (TextView) findViewById(R.id.textViewHint);
        txtTitle = findViewById(R.id.txtTitle);
        yes.setOnClickListener(this);
        buttonRemove.setOnClickListener(this);

        setUpView();

    }

    private void setUpView() {
        txtTitle.setText("Credentials for"+exchange);
        String user = sharedPreferenceManagerC.getKeyToSharedPreferencString(KEY_user);
        String key = sharedPreferenceManagerC.getKeyToSharedPreferencString(KEY_pass);
        if( user.equalsIgnoreCase("")  || key.equalsIgnoreCase("")  ){
            setUpQRReader();
            buttonRemove.setVisibility(View.INVISIBLE);
        }else {
            buttonRemove.setVisibility(View.VISIBLE);
            textViewHint.setText("Added one key");

        }


    }

    private void setUpQRReader() {
        qrCodeReaderView = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        qrCodeReaderView.setOnQRCodeReadListener(CustomDialogClass.this);
        qrCodeReaderView.setVisibility(View.VISIBLE);
        // Use this function to enable/disable decoding
        qrCodeReaderView.setQRDecodingEnabled(true);

        // Use this function to change the autofocus interval (default is 5 secs)
        qrCodeReaderView.setAutofocusInterval(2000L);

        // Use this function to enable/disable Torch
        qrCodeReaderView.setTorchEnabled(true);

        // Use this function to set front camera preview
//        qrCodeReaderView.setFrontCamera();

        // Use this function to set back camera preview
        qrCodeReaderView.setBackCamera();
        qrCodeReaderView.startCamera();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                dismiss();
                break;
            case R.id.buttonRemove:
                sharedPreferenceManagerC.saveKeyToSharedPreferencString(KEY_user,"");
                sharedPreferenceManagerC.saveKeyToSharedPreferencString(KEY_pass,"");
                textViewHint.setText("(no API details available) Scan new QR code");
                buttonRemove.setVisibility(View.INVISIBLE);

                if(qrCodeReaderView!=null)  qrCodeReaderView.startCamera(); else setUpQRReader();
                break;
            default:
                break;
        }
//        dismiss();
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public final String KEY_user = "key_user";
    public final String KEY_pass = "key_user_pass";
    @Override
    public void onQRCodeRead(String text, PointF[] points) {
           String qrResult  = text;

           String[] split = qrResult.split("secret:");
           if(split!=null && split.length==2){
               String user = split[0].substring(5,split[0].length());
               String key = split[1];

               sharedPreferenceManagerC.saveKeyToSharedPreferencString(KEY_user,user);
               sharedPreferenceManagerC.saveKeyToSharedPreferencString(KEY_pass,key);

              if(qrCodeReaderView!=null)  qrCodeReaderView.stopCamera();
               buttonRemove.setVisibility(View.VISIBLE);
               textViewHint.setText("Added one key");

           }

    }

}
