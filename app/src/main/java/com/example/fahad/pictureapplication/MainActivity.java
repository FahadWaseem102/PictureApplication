package com.example.fahad.pictureapplication;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class MainActivity extends AppCompatActivity {

    ImageView image ;
    Button button ;
    private static final int PICK_IMAGE= 1 ;
    private static final int CAPTURE_IMAGE= 0 ;
    Uri imageUri ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image= findViewById(R.id.imageView) ;
        button = findViewById(R.id.button) ;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPermission() ;
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICK_IMAGE:
            if (resultCode == RESULT_OK) {
                imageUri = data.getData();
                image.setImageURI(imageUri);
            }
            break;
            case CAPTURE_IMAGE:
            if (resultCode == RESULT_OK) {
                imageUri = data.getData();
                image.setImageURI(imageUri);
            }
            break;
        }
    }

    private void setPermission() {
        try{
                PackageManager pm = getPackageManager();
                int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, getPackageName());
                if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                final String[] options = {"Take Photo", "Open Gallery", "Cancel"};
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setTitle("Choose Photo");
                alertDialogBuilder.setItems(options, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (options[which].equals("Take Photo")) {
                            dialog.cancel();
                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(cameraIntent, CAPTURE_IMAGE);
                        }
                        if (options[which].equals("Open Gallery")) {
                            dialog.cancel();
                            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(galleryIntent, PICK_IMAGE);
                        }
                        if (options[which].equals("Cancel")) {
                            dialog.cancel();
                        }
                    }
                });
                alertDialogBuilder.show();
            }else
                    Toast.makeText(this, "Camera Permission error", Toast.LENGTH_SHORT).show();
        } catch (Exception e){
                Toast.makeText(this, "Camera Permission error", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

