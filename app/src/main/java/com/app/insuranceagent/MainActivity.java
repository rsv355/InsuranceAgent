package com.app.insuranceagent;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{
    LinearLayout linearClients,linearComp,linearAgents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

  private void init(){
      linearClients = (LinearLayout)findViewById(R.id.linearClients);
      linearComp = (LinearLayout)findViewById(R.id.linearComp);
      linearAgents = (LinearLayout)findViewById(R.id.linearAgents);

      linearClients.setOnClickListener(this);
      linearComp.setOnClickListener(this);
      linearAgents.setOnClickListener(this);
  }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.linearClients:
                Intent i1 = new Intent(this,SearchClients.class);
                i1.putExtra("title","Clients");
                startActivity(i1);
                break;
            case R.id.linearComp:
                Intent i2 = new Intent(this,SearchCompanies.class);
                i2.putExtra("title","Insurance Companies");
                startActivity(i2);
                break;
            case R.id.linearAgents:
                Intent i3 = new Intent(this,SearchAgents.class);
                i3.putExtra("title","Insurance Agents");
                startActivity(i3);
                break;
        }
    }
}
