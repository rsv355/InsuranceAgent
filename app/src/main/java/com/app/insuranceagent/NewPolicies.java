package com.app.insuranceagent;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;

import fr.ganfra.materialspinner.MaterialSpinner;


public class NewPolicies extends ActionBarActivity {
    TextView txtTitle, txtSubHeading;
    ImageView imgClose, imgBack, imgSave;
    ListView listView;
    MaterialEditText edPolicyNumber, edProductD, edDate, edTime,edTerm,edInsSumm,edPrem,edDownP,edSales,edDeduct,edCharge,edCredit,edBal;

    MaterialSpinner spCType,spAgent,spCmp,spPType,spPayM,spPayFrq,spStatus;

    String tempclientName, tempagentName, tempcmpName,temppolNo,temppolType,temppolDetails,tempefftDate,
            tempexpDate,temptermLength,tempinsSummary,temppremium,tempdownPayment,tempsalesCommision,
            tempdeductible,temppayMethod,temppayFreq,tempstatus,tempcharge,tempcredit,tempbalance;

    int temppolID;
    ArrayList<String> policyType;
    ArrayList<String> paymentMethod;
    DBAdapter db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpolicy);
        db = new DBAdapter(NewPolicies.this);

        init();

        policyType = new ArrayList<>();
        policyType.add("Vehicle (auto)");
        policyType.add("Property");
        policyType.add("Home");
        policyType.add("Health");
        policyType.add("Dental");
        policyType.add("Life");
        policyType.add("International (Travel)");
        policyType.add("Other");


        paymentMethod = new ArrayList<>();
        paymentMethod.add("Cash/Check");
        paymentMethod.add("Debit Card/Credit Card");
        paymentMethod.add("Direct Debit/Standing Order");
        paymentMethod.add("In-House Payroll");
        paymentMethod.add("Other");

        ArrayAdapter<String> clientAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, policyType);
        spPType.setAdapter(clientAdapter);




        temppolID = getIntent().getIntExtra("polID", 1);
        tempclientName = getIntent().getStringExtra("clientName");
        tempagentName = getIntent().getStringExtra("agentName");
        tempcmpName = getIntent().getStringExtra("cmpName");
        temppolNo = getIntent().getStringExtra("polNo");

        temppolType = getIntent().getStringExtra("polType");
        temppolDetails = getIntent().getStringExtra("polDetails");
        tempefftDate = getIntent().getStringExtra("efftDate");
        tempexpDate = getIntent().getStringExtra("expDate");
        temptermLength = getIntent().getStringExtra("termLength");

        tempinsSummary = getIntent().getStringExtra("insSummary");
        temppremium = getIntent().getStringExtra("premium");
        tempdownPayment = getIntent().getStringExtra("downPayment");
        tempsalesCommision = getIntent().getStringExtra("salesCommision");
        tempdeductible = getIntent().getStringExtra("deductible");
        temppayMethod = getIntent().getStringExtra("payMethod");

        temppayFreq = getIntent().getStringExtra("payFreq");
        tempstatus = getIntent().getStringExtra("status");
        tempcharge = getIntent().getStringExtra("charge");
        tempcredit = getIntent().getStringExtra("credit");
        tempbalance = getIntent().getStringExtra("balance");



        if (temppolNo == null || temppolNo.toString().trim().length() == 0) {
            spPType.setSelection(0);
        } else {
            txtSubHeading.setText(temppolNo + " :: Edit");

        }





        spPType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        edProductD.setText("Vehicle Type:\nMake/Model:\nMarket Price:\nPrimary use:\nYear:\nMileage:\nTransmission:\nColor:");
                        break;
                    case 1:
                        edProductD.setText("Construction Type:\nMarket Price:\nYear Built:\nLocation:\nConstruction Materials:\n# of Units:\n# of Stories:\nOccupancy:");
                        break;
                    case 2:
                        edProductD.setText("Construction Type:\nMarket Price:\nYear Built:\nLocation:\nConstruction Materials:\n# of Units:\n# of Stories:\nOccupancy:");
                        break;
                    case 3:
                        edProductD.setText("SSN:\nCoverage Desired:Single / Family\nMember coverage:\nBeneficiaries:\nPersonal Info:\nMedical Info:");
                        break;
                    case 4:
                        edProductD.setText("SSN:\nCoverage Desired:Single / Family\nMember coverage:\nBeneficiaries:\nPersonal Info:\nMedical Info:");
                        break;
                    case 5:
                        edProductD.setText("SSN:\nCoverage Desired:Single / Family\nMember coverage:\nBeneficiaries:\nPersonal Info:\nMedical Info:");
                        break;
                    case 6:
                        edProductD.setText("");
                        break;
                    case 7:
                        edProductD.setText("");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




    }




    private void init(){


        edPolicyNumber= (MaterialEditText)findViewById(R.id.edPolicyNumber);
        edProductD= (MaterialEditText)findViewById(R.id.edProductD);
        edDate= (MaterialEditText)findViewById(R.id.edDate);
        edTime= (MaterialEditText)findViewById(R.id.edTime);
        edTerm= (MaterialEditText)findViewById(R.id.edTerm);
        edInsSumm= (MaterialEditText)findViewById(R.id.edInsSumm);
        edPrem= (MaterialEditText)findViewById(R.id.edPrem);
        edDownP= (MaterialEditText)findViewById(R.id.edDownP);
        edSales= (MaterialEditText)findViewById(R.id.edSales);
        edDeduct= (MaterialEditText)findViewById(R.id.edDeduct);
        edCharge= (MaterialEditText)findViewById(R.id.edCharge);
        edCredit= (MaterialEditText)findViewById(R.id.edCredit);
        edBal= (MaterialEditText)findViewById(R.id.edBal);

        spCType= (MaterialSpinner)findViewById(R.id.spCType);
        spAgent= (MaterialSpinner)findViewById(R.id.spAgent);
        spCmp= (MaterialSpinner)findViewById(R.id.spCmp);
        spPType= (MaterialSpinner)findViewById(R.id.spPType);
        spPayM= (MaterialSpinner)findViewById(R.id.spPayM);
        spPayFrq= (MaterialSpinner)findViewById(R.id.spPayFrq);
        spStatus= (MaterialSpinner)findViewById(R.id.spStatus);

        imgClose = (ImageView)findViewById(R.id.imgClose);
        imgBack = (ImageView)findViewById(R.id.imgBack);
        imgSave = (ImageView)findViewById(R.id.imgSave);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


    //end of main class
}
