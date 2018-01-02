package com.example.administrator.demo1;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MyCropView myCropView;
    private Button btnCrop;
    private Button btnCancel;
    private ImageView croppedImageView;
    private ScrollView sv;

    private TextView text ;

    private int w , h ;
    // 假设从图片选择器传递来的图片路径如下
    private static final String CROP_IMAGE_PATH = "/storage/emulated/0/20170609123109248011.jpg";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myCropView = (MyCropView) findViewById(R.id.myCropView);
        btnCrop = (Button) findViewById(R.id.btn_crop);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        croppedImageView = (ImageView) findViewById(R.id.croppedImageView);
        sv = (ScrollView) findViewById(R.id.scrollview);

        sv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ViewTreeObserver vto2 = myCropView.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                myCropView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                w = myCropView.getWidth() ;
                h = myCropView.getHeight() ;
                handler.sendEmptyMessage(1001);


            }
        });

        text = (TextView) findViewById(R.id.text);
        text.setText(new NdkJniUtils().getCLanguageString());
        text.setText("");
        btnCrop.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        sv.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                myCropView.getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            }

        });
    }


    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1001){
                myCropView.setBmpPath(CROP_IMAGE_PATH,w,h);
            }
        }
    };

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btn_crop:
                Bitmap croppedImage = myCropView.getCroppedImage();
                if (croppedImage==null){
                    return ;
                }
                croppedImageView.setImageBitmap(croppedImage);
                break;
            case R.id.btn_cancel:
//                Intent intent = new Intent(MainActivity.this,ScanActivity.class);
//                intent.putExtra("path", CROP_IMAGE_PATH);
//                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.activity_main, menu);
        return false;

    }

}
