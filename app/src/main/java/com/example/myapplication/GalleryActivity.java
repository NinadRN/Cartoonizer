package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class GalleryActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView mImageView;
    private Uri mImageUri;

    static {
        if (OpenCVLoader.initDebug()) {
            Log.d("OpenCV", "OpenCV loaded successfully");
        } else {
            Log.d("OpenCV", "OpenCV not loaded");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        Button mgallery = findViewById(R.id.gallery);
        mImageView = findViewById(R.id.imageview);

        mgallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
              startActivityForResult(Intent.createChooser(intent, "title"), PICK_IMAGE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,@Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.get()
                    .load(mImageUri)
                    .into(mImageView);
        }
    }

    private void convertToCartoon() {
        try {
            // Load the image from the device's storage
            Mat src = Imgcodecs.imread(getRealPathFromURI(mImageUri));

            // Convert the image to grayscale
            Mat gray = new Mat();
            Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);

            // Find the edges in the image
            Mat edges = new Mat();
            Imgproc.Canny(gray, edges, 50, 150);

            // Apply the pencil sketch filter to the edges image
            Mat pencilSketch = new Mat();
            
            
            Imgproc.pencilSketch(edges, pencilSketch, 60, 0.07f, 0.02f);

            // Convert the pencil sketch to a color image
            Mat colorSketch = new Mat();
            Imgproc.cvtColor(pencilSketch, colorSketch, Imgproc.COLOR_GRAY2BGR);

            // Combine the stylized image and the color sketch using a bitwise AND operation
            Mat cartoon = new Mat();
            Core.bitwise_and(colorSketch, src, cartoon);

            // Convert the result back to a bitmap and display it in the ImageView
            Bitmap bitmap = Bitmap.createBitmap(cartoon.cols(), cartoon.rows(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(cartoon, bitmap);
            mImageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper method to get the real path of an image URI
    private String getRealPathFromURI(Uri contentUri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor =
                getContentResolver().query(contentUri, projection, null, null, null);
            if (cursor == null) {
                return contentUri.getPath();
            } else {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                String path = cursor.getString(columnIndex);
                cursor.close();
                return path;
            }
        }

    }









