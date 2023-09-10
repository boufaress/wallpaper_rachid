package com.rba.animewp.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.view.View;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageDownloader {

    private Context context;
    private View rootView;

    public ImageDownloader(Context context, View rootView) {
        this.context = context;
        this.rootView = rootView;
    }

    public void downloadImage(String imageUrl, String fileName) {
        new DownloadTask(imageUrl, fileName).execute();
    }

    private class DownloadTask extends AsyncTask<Void, Void, Boolean> {
        private String imageUrl;
        private String fileName;
        private String filePath;

        private ProgressDialog progressDialog;

        public DownloadTask(String imageUrl, String fileName) {
            this.imageUrl = imageUrl;
            this.fileName = fileName;
            filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + fileName;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Downloading, Please Wait...");
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream input = connection.getInputStream();
                File file = new File(filePath);
                OutputStream output = new FileOutputStream(file);

                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = input.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }

                output.flush();
                output.close();
                input.close();
                connection.disconnect();

                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            progressDialog.dismiss();

            if (isSuccess) {
                showSnackbarAndOpenImage();
            } else {
                Toast.makeText(context, "Failed to download image", Toast.LENGTH_SHORT).show();
            }
        }

        private void showSnackbarAndOpenImage() {
            Snackbar.make(rootView, "Download Finished", Snackbar.LENGTH_LONG)
                    .setAction("", view -> {
                        if (Build.VERSION.SDK_INT >= 24) {
                            try {
                                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                                m.invoke(null);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        File imageFile = new File(filePath);
                        Uri imageUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", imageFile);

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(imageUri, "image/*");
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        context.startActivity(intent);
                    })
                    .show();
        }
    }
}
