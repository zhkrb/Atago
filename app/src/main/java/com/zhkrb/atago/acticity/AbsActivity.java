package com.zhkrb.atago.acticity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.zhkrb.atago.R;

public abstract class AbsActivity extends AppCompatActivity {

    protected Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayoutId());
        getWindow().setBackgroundDrawable(null);
        mContext = this;
        main();
    }

    protected abstract int getLayoutId();

    protected abstract void main();

    protected void setTitle(String title){
        TextView textView = findViewById(R.id.title);
        if (textView!=null){
            textView.setText(title);
        }
    }

    public void onBack(View view){
        onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
