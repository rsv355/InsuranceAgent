package com.app.insuranceagent;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

/**
 * Created by SAGAR on 8/16/2015.
 */
public class InfoDialog extends Dialog {
    Context context;
    private TextView okButton, bookDesc;

    String title, description;

    public InfoDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.info_dialog);

        init();
    }

    private void init() {
        okButton = (TextView)findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             dismiss();
            }
        });

    }
}
