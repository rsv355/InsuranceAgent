package com.app.insuranceagent;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{
    LinearLayout linearProducts,linearStats,linearBackup,linearClaims,linearAppointments,linearClients,linearComp,linearAgents,linearPolicies;
    AdRequest adRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        AdView adView = (AdView) this.findViewById(R.id.adView);
        // Request for Ads
        adRequest = new AdRequest.Builder()
                .build();

        // Load ads into Banner Ads
        adView.loadAd(adRequest);
        // adview ends
    }

  private void init(){
      linearClients = (LinearLayout)findViewById(R.id.linearClients);
      linearComp = (LinearLayout)findViewById(R.id.linearComp);
      linearAgents = (LinearLayout)findViewById(R.id.linearAgents);
      linearAppointments = (LinearLayout)findViewById(R.id.linearAppointments);
      linearPolicies= (LinearLayout)findViewById(R.id.linearPolicies);
      linearClaims= (LinearLayout)findViewById(R.id.linearClaims);
      linearStats= (LinearLayout)findViewById(R.id.linearStats);
      linearBackup  = (LinearLayout)findViewById(R.id.linearBackup);
      linearProducts= (LinearLayout)findViewById(R.id.linearProducts);

      linearStats.setOnClickListener(this);
      linearBackup.setOnClickListener(this);
      linearProducts.setOnClickListener(this);



      linearClaims.setOnClickListener(this);
      linearPolicies.setOnClickListener(this);
      linearAppointments.setOnClickListener(this);
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
            case R.id.linearAppointments:
                Intent i4 = new Intent(this,SearchAppointments.class);
                startActivity(i4);
                break;
            case R.id.linearPolicies:
                Intent i5 = new Intent(this,SearchPolicies.class);
                startActivity(i5);
                break;

            case R.id.linearClaims:
                Intent i6 = new Intent(this,SearchClaims.class);
                startActivity(i6);
                break;


            case R.id.linearStats:
                Intent i7 = new Intent(this,Statisitcs.class);
                startActivity(i7);
                break;
            case R.id.linearBackup:
                Intent i8 = new Intent(this,Backup.class);
                startActivity(i8);
                break;

            case R.id.linearProducts:
                Intent i9 = new Intent(this,Products.class);
                startActivity(i9);
                break;
        }
    }
}
