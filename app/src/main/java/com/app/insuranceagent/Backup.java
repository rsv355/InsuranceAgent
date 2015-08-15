package com.app.insuranceagent;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.insuranceagent.model.BackupModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Calendar;


public class Backup extends ActionBarActivity {
    TextView txtTitle;
    ImageView imgBack, newBackup;
    ListView listView;
    boolean success;
    ArrayList<BackupModel> backupList = new ArrayList<>();
    CustomAdapter adapter;
    int day, month, year, hour, min;
    String date;
    EditText edSearchBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.backup);

        Calendar cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        hour = cal.get(Calendar.HOUR);
        min = cal.get(Calendar.MINUTE);
        date = day + "-" + (month + 1) + "-" + year + "-" + hour + ":" + min;

        init();

        edSearchBox.setText("Insurance-Backup_" + date);

        newBackup.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             Calendar cal = Calendar.getInstance();
                                             day = cal.get(Calendar.DAY_OF_MONTH);
                                             month = cal.get(Calendar.MONTH);
                                             year = cal.get(Calendar.YEAR);
                                             hour = cal.get(Calendar.HOUR);
                                             min = cal.get(Calendar.MINUTE);
                                             date = day + "-" + (month + 1) + "-" + year + "-" + hour + ":" + min;
                                             try {
                                                 File file = new File(Environment.getExternalStorageDirectory() + "/InsuranceBackup");
                                                 if (file.exists()) {
                                                     success = true;
                                                 } else {
                                                     success = file.mkdir();
                                                 }
                                                 if ((success) && file.canWrite()) {
                                                     String backupDBPath = "Insurance-Backup_" + date;
                                                     File currentDB = getDatabasePath("MyDB1");
                                                     File backupDB = new File(file, backupDBPath);

                                                     if (currentDB.exists()) {
                                                         FileChannel src = new FileInputStream(currentDB).getChannel();
                                                         FileChannel dst = new FileOutputStream(backupDB).getChannel();
                                                         dst.transferFrom(src, 0, src.size());
                                                         src.close();
                                                         dst.close();
                                                         Toast.makeText(Backup.this, "Backup created successfully.", Toast.LENGTH_LONG).show();
                                                         restartActivity();
                                                     } else {
                                                     }
                                                 } else {
                                                 }

                                             } catch (
                                                     Exception e
                                                     )

                                             {
                                             }
                                         }
                                     }

        );
    }

    private void init() {

        listView = (ListView) findViewById(R.id.listView);
        edSearchBox = (EditText) findViewById(R.id.edSearchBox);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        newBackup = (ImageView) findViewById(R.id.newBackup);


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        try {
            backupList.clear();
            File file = new File(Environment.getExternalStorageDirectory() + "/InsuranceBackup");
            if (file.exists() || file != null) {
                File[] filenames = file.listFiles();
                for (File tmpf : filenames) {
                    BackupModel model = new BackupModel();
                    model.setFileName(tmpf.getName());
                    backupList.add(model);
                }
                adapter = new CustomAdapter(this, backupList);
                listView.setAdapter(adapter);
            }
        } catch (Exception e) {
            Log.e("#### exc", e.toString());
        }

    }

    private void copyFile(String inputPath, String inputFile, String outputPath) {

        InputStream in = null;
        OutputStream out = null;
        try {

            //create output directory if it doesn't exist
            File dir = new File(outputPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }


            in = new FileInputStream(inputPath + inputFile);
            out = new FileOutputStream(outputPath);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            // write the output file (You have now copied the file)
            out.flush();
            out.close();
            out = null;

        } catch (FileNotFoundException fnfe1) {
            Log.e("tag", fnfe1.getMessage());
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

    }

    private class CustomAdapter extends BaseAdapter {

        Context context;
        ArrayList<BackupModel> backupList = new ArrayList<>();
        LayoutInflater layoutInflator;

        public CustomAdapter(Context context, ArrayList<BackupModel> backupList) {
            this.context = context;
            this.backupList = backupList;
        }

        @Override
        public int getCount() {
            return backupList.size();
        }

        @Override
        public BackupModel getItem(int position) {
            return backupList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return (long) position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = new ViewHolder();
            layoutInflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = layoutInflator.inflate(R.layout.backup_row, parent, false);
                holder.text = (TextView) convertView.findViewById(R.id.fileName);
                holder.delete = (ImageView) convertView.findViewById(R.id.deleteFile);
                holder.restore = (ImageView) convertView.findViewById(R.id.restoreFile);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            BackupModel model = backupList.get(position);
            holder.text.setText(model.getFileName());
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Are you sure to delete this ")
                            .setNegativeButton("YES",
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            // TODO Auto-generated method stub
                                            BackupModel model = backupList.get(position);
                                            File f = new File(Environment.getExternalStorageDirectory() + "/InsuranceBackup/" + model.getFileName());
                                            if (f.exists()) {
                                                f.delete();
                                                if (Build.VERSION.SDK_INT >= 11) {
                                                    recreate();
                                                } else {
                                                    restartActivity();
                                                }
                                            }
                                        }
                                    })
                            .setPositiveButton("NO",
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            // TODO Auto-generated method stub
                                            dialog.dismiss();
                                        }
                                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });

            holder.restore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Are you sure to restore your database from this backup?")
                            .setNegativeButton("YES",
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            // TODO Auto-generated method stub
                                            BackupModel model = backupList.get(position);
                                            String inPath = Environment.getExternalStorageDirectory() + "/InsuranceBackup/";
                                            String inFile = model.getFileName();
                                            String outPath = getDatabasePath("MyDB1").toString();
                                            copyFile(inPath, inFile, outPath);
                                        }
                                    })
                            .setPositiveButton("NO",
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            // TODO Auto-generated method stub
                                            dialog.dismiss();
                                        }
                                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                }
            });

            return convertView;
        }

        private void alert() {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Are you sure to delete this ")
                    .setNegativeButton("YES",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // TODO Auto-generated method stub
                                    Intent intent = new Intent(
                                            Intent.ACTION_MAIN);
                                    intent.addCategory(Intent.CATEGORY_HOME);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            })
                    .setPositiveButton("NO",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // TODO Auto-generated method stub
                                    dialog.dismiss();

                                }
                            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

        class ViewHolder {
            private TextView text;
            private ImageView delete, restore;
        }
    }

    private void restartActivity() {
        Intent intent = getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);

        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}
