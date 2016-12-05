package com.teamsmokeweed.qroute.readqr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.teamsmokeweed.qroute.Content;
import com.teamsmokeweed.qroute.R;
import com.teamsmokeweed.qroute.firebase.CenteridValue;
import com.teamsmokeweed.qroute.firebase.SaveToMobileID;

import java.util.Arrays;
import java.util.List;

import me.dm7.barcodescanner.core.IViewFinder;
import me.dm7.barcodescanner.core.ViewFinderView;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by jongzazaal on 3/11/2559.
 */

public class ReadActivity  extends Activity implements ZXingScannerView.ResultHandler , ConnectivityReceiver.ConnectivityReceiverListener{

    private ZXingScannerView mScannerView;
    private Button gallery;
    //private DateQr dateQr;
    private static int RESULT_LOAD_IMAGE = 1;
    SharedPreferences prefs = null;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    CenteridValue centeridValue;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.read_qr);
        checkConnection();


//        Dexter.initialize(ReadActivity.this);
//        Dexter.checkPermission(new PermissionListener() {
//            @Override
//            public void onPermissionGranted(PermissionGrantedResponse response) {/* ... */}
//
//            @Override
//            public void onPermissionDenied(PermissionDeniedResponse response) {finish();}
//
//            @Override
//            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
//
//
//                // ask permission once time
//                //token.cancelPermissionRequest();
//
//                // request permission when call method again
//                //token.continuePermissionRequest();
//
//
//
//
//            }
//        }, Manifest.permission.CAMERA);

        //setupToolbar();



        gallery = (Button) findViewById(R.id.gallery);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
        ViewGroup contentFrame = (ViewGroup) findViewById(R.id.content_frame);
        mScannerView = new ZXingScannerView(this){@Override
        protected IViewFinder createViewFinderView(Context context) {
            return new CustomViewFinderView(context);
        }};
        contentFrame.addView(mScannerView);

        mFirebaseInstance = FirebaseDatabase.getInstance();


    }


    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
        MyApplication.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }
    List<String> sqr = null;
    String[] sqrr = null;
    @Override
    public void handleResult(final Result rawResult) {
//        Toast.makeText(this, "Contents = " + rawResult.getText() +
//                ", Format = " + rawResult.getBarcodeFormat().toString(), Toast.LENGTH_SHORT).show();


//        Toast.makeText(ReadActivity.this, rawResult.getText(), Toast.LENGTH_SHORT).show();
//        String[] sQr = rawResult.getText().split("#420#");
        //String part1 = parts[0];

//        try{
//            setDateQr(sQr);
//            AddDatabaseQr addDatabaseQr = new AddDatabaseQr(dateQr, getApplicationContext());
//            addDatabaseQr.addDb();
//            Intent i = new Intent(getApplicationContext(), ResultReadQrActivity.class);
//            i.putExtra("sQr", sQr);
//            startActivity(i);}
//        catch (Exception e){
//            Toast.makeText(ReadActivity.this, "QrCode Not math is programmm", Toast.LENGTH_SHORT).show();
//            //return;
//            onPause();
//            onResume();
//        }


        prefs = getSharedPreferences("CheckFirst", MODE_PRIVATE);
        String MID = prefs.getString("MID", "-null");

        new SaveToMobileID(rawResult.getText(), MID);



//        try{



//            //new SaveToMobileID(rawResult.getText());
//



//        }
//        catch (Exception e){
//

//        }

        mFirebaseDatabase = mFirebaseInstance.getReference("centerid");
        mFirebaseDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                //Log.d("sassssssssssssss", dataSnapshot.get.toString()+"//sss");
//                Toast.makeText(ReadActivity.this, dataSnapshot.getKey()+"//"+rawResult.getText(), Toast.LENGTH_SHORT).show();
                if(dataSnapshot.getKey().equals(rawResult.getText())){
                    //Toast.makeText(ReadActivity.this, dataSnapshot.getKey(), Toast.LENGTH_SHORT).show();
                    centeridValue = dataSnapshot.getValue(CenteridValue.class);

                    sqr = Arrays.asList(
                            centeridValue.getLat().toString(),
                            centeridValue.getLng().toString(),
                            centeridValue.getTitles(),
                            centeridValue.getPlaceName(),
                            centeridValue.getPlaceType(),
                            centeridValue.getDes(),
                            centeridValue.getWeb(),
                            centeridValue.getPic(),
                            centeridValue.getStart_date(),
                            centeridValue.getStart_time(),
                            centeridValue.getEnd_date(),
                            centeridValue.getEnd_time()
                    );
                    sqrr = (String[]) sqr.toArray(new String[sqr.size()]);
                    Intent intent = new Intent(ReadActivity.this, Content.class);
                    //String[] sqr = sqr;
                    //ArrayList<String> sqrr = new ArrayList<String>(sqr);

                    intent.putExtra("sQr", sqrr);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //String[] parts = rawResult.getText().split(":");
        //String part1 = parts[1]; // 004

        try {

        }
        catch (Exception e){

        }





        // Note:
        // * Wait 2 seconds to resume the preview.
        // * On older devices continuously stopping and resuming camera preview can result in freezing the app.
        // * I don't know why this is the case but I don't have the time to figure out.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScannerView.resumeCameraPreview(ReadActivity.this);
            }
        }, 2000);
    }
    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }

    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            message = "Good! Connected to Internet";
            color = Color.WHITE;
        } else {
            message = "Sorry! Not connected to internet";
            color = Color.RED;
        }

        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.coordinator_layout), message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }

    private static class CustomViewFinderView extends ViewFinderView {
        public static final String TRADE_MARK_TEXT = "ZXing";
        public static final int TRADE_MARK_TEXT_SIZE_SP = 40;
        public final Paint PAINT = new Paint();

        public CustomViewFinderView(Context context) {
            super(context);
            init();
        }

        public CustomViewFinderView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        private void init() {
            PAINT.setColor(Color.WHITE);
            PAINT.setAntiAlias(true);
            float textPixelSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                    TRADE_MARK_TEXT_SIZE_SP, getResources().getDisplayMetrics());
            PAINT.setTextSize(textPixelSize);
            setSquareViewFinder(true);
        }

        @Override
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            drawTradeMark(canvas);
        }

        private void drawTradeMark(Canvas canvas) {
            Rect framingRect = getFramingRect();
            float tradeMarkTop;
            float tradeMarkLeft;
            if (framingRect != null) {
                tradeMarkTop = framingRect.bottom + PAINT.getTextSize() + 10;
                tradeMarkLeft = framingRect.left;
            } else {
                tradeMarkTop = 10;
                tradeMarkLeft = canvas.getHeight() - PAINT.getTextSize() - 10;
            }
            //canvas.drawText(TRADE_MARK_TEXT, tradeMarkLeft, tradeMarkTop, PAINT);
        }
    }

//    private void setDateQr(String[] sQr){
//        dateQr = new DateQr();
//
//        dateQr.setTitles(sQr[2]);
//        dateQr.setPlaceName(sQr[3]);
//        dateQr.setPlaceType(sQr[4]);
//        dateQr.setDes(sQr[5]);
//        dateQr.setLat(Float.valueOf(sQr[0]));
//        dateQr.setLng(Float.valueOf(sQr[1]));
//        dateQr.setWebPage(sQr[6]);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            readQr(BitmapFactory.decodeFile(picturePath));

        }
    }
    public void readQr(Bitmap b){
        Bitmap generatedQRCode = b;
        int width = generatedQRCode.getWidth();
        int height = generatedQRCode.getHeight();
        int[] pixels = new int[width * height];
        generatedQRCode.getPixels(pixels, 0, width, 0, 0, width, height);

        RGBLuminanceSource source = new RGBLuminanceSource(width, height, pixels);

        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));

        Reader reader = new MultiFormatReader();
        Result result = null;
        try {
            result = reader.decode(binaryBitmap);
        } catch (NotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "Not Find", Toast.LENGTH_SHORT).show();
            return;
        } catch (ChecksumException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }
        //String text = result.getText();
//        prefs = getSharedPreferences("CheckFirst", MODE_PRIVATE);
//        String MID = prefs.getString("MID", "-null");
//
//        new SaveToMobileID(result.getText(), MID);
//        Toast.makeText(this, result.getText(), Toast.LENGTH_SHORT).show();
        handleResult(result);

//        String[] sQr = text.split("#420#");
//        //String part1 = parts[0];
//
//        try{setDateQr(sQr);
//            AddDatabaseQr addDatabaseQr = new AddDatabaseQr(dateQr, getApplicationContext());
//            addDatabaseQr.addDb();}
//        catch (Exception e){
//            Toast.makeText(ReadActivity.this, "QrCode Not math is program", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//
//        Intent i = new Intent(getApplicationContext(), ResultReadQrActivity.class);
//        i.putExtra("sQr", sQr);
//        startActivity(i);
    }
}

