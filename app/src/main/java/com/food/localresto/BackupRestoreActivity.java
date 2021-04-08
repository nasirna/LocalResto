package com.food.localresto;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.food.localresto.database.BackupData;
import com.food.localresto.database.SqliteDatabase;

public class BackupRestoreActivity extends AppCompatActivity implements BackupData.OnBackupListener{

    private Context context;
    private SqliteDatabase db;
    private BackupData backupData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.backup_restore_setting);

        setTitle("Backup & Restore");

        context = this;

        db = new SqliteDatabase(context);
        backupData = new BackupData(context);
        backupData.setOnBackupListener(this);

        Button backupButton = (Button) findViewById(R.id.btn_backup);
        backupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(BackupRestoreActivity.this,"Backup..",Toast.LENGTH_LONG).show();
                backupData.exportToSD();
            }
        });

        Button restoreButton = (Button) findViewById(R.id.btn_restore);
        restoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(BackupRestoreActivity.this,"Restore..",Toast.LENGTH_LONG).show();
                backupData.importFromSD();
            }
        });
    }

    @Override
    public void onFinishExport(String error) {
        String notify = error;
        if (error == null) {
            notify = "Export success";
        }
        Toast.makeText(context, notify, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFinishImport(String error) {
        String notify = error;
        if (error == null) {
            notify = "Import success";
            //updateListNote();
        }
        Toast.makeText(context, notify, Toast.LENGTH_SHORT).show();
    }
}
