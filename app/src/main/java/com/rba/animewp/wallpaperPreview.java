package com.rba.animewp;

import static com.rba.animewp.wallpaperdetail.sqlhelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.snackbar.Snackbar;
import com.rba.animewp.adapters.ImageDownloader;
import com.rba.animewp.adapters.WallpaperSetter;
import com.rba.animewp.ads.AdsController;
import com.rba.animewp.mData.ConstantFile;
import com.rba.animewp.mData.WallpaperDialog;
import com.rba.animewp.mData.constant;
import com.rba.animewp.mData.items_wallpaper;
import com.rba.animewp.mData.settings;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;


public class wallpaperPreview extends AppCompatActivity {
    private static final int REQUEST____STATE = 1,
            REQUEST_READ_STORAGE = 110,
            REQUEST_WRITE_STORAGE = 111;

    ImageView wallstuffimage;
    TextView wallstufftitle, wallstufftype;
    ImageView setwallpaper, download, bfavorit, wallstuffshare;
    int P;
    String directory, extension, fileplace;
    View apnaview;
    LinearLayout lineartooltable;
    String id_id, name_name, url_url, the_save_boton;

    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private String imageUrl = "https://www.example.com/image.jpg"; // Replace with your image URL
    private ProgressBar m_prg;
    private int requestCode;
    private String[] permissions;
    private int[] grantResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper_preview);


        SharedPreferences sharedpre = getSharedPreferences(ConstantFile.shared_pref_file_name, MODE_PRIVATE);

        constant.chekinternet(this);

        id_lists();
        lineartooltable.setVisibility(View.INVISIBLE);
        P = getIntent().getIntExtra("position",0) ;
        items_wallpaper items_wallpaper1 = settings.arrayL.get(P);
        //----wallpaper ---------------------------
        id_id = items_wallpaper1.getId();
        name_name = items_wallpaper1.getName();
        url_url = items_wallpaper1.getImageURL();
        the_save_boton = sharedpre.getString("w_buttonview", "default");
        a_display_image();
        /*id_id = getIntent().getStringExtra("id");
        name_name = getIntent().getStringExtra("name");
        url_url = getIntent().getStringExtra("image");
        String the_save_boton = getIntent().getStringExtra("buttonview");*/

        if (the_save_boton.equals("no")) {
            download.setVisibility(View.GONE);
        }


        wallstufftitle.setText(name_name);
        switch (url_url.toLowerCase().substring(url_url.length() - 3)) {
            case "jpg":
                wallstufftype.setText("Type" + "Format jpg");
                extension = ".jpg";
                break;
            case "peg":
                wallstufftype.setText("Type" + "Format jpeg");
                extension = ".jpeg";
                break;
            case "png":
                wallstufftype.setText("Type" + "Format png");
                extension = ".png";
                break;
            case "gif":
                wallstufftype.setText("Type" + "Format gif");
                extension = ".gif";
                break;
            default:
                wallstufftype.setText("Type" + "Unknown");
                extension = ".jpg";
                break;
        }


            is_it_fav();



        download.setOnClickListener(view -> {
            ImageDownloader imageDownloader = new ImageDownloader(this, apnaview);
            AdsController.Showinter(this, new AdsController.Adfinished() {
                @Override
                public void onADfinished() {
                    apnaview = view;

                    imageDownloader.downloadImage(url_url, name_name+".jpg");
                }
            });




        });

        bfavorit.setOnClickListener(view -> {



                set_to_favorite(); // add to favorite

        });

        setwallpaper.setOnClickListener(ourView -> {
            WallpaperSetter wallpaperSetter = new WallpaperSetter(this, getSupportFragmentManager(), url_url);
            AdsController.Showinter(this, new AdsController.Adfinished() {
                @Override
                public void onADfinished() {

                    wallpaperSetter.setWallpaperInBackground(ourView);
                }
            });


        });

        String shareText = "Download This Amazing Wallpaper For Your Mobile." + "\n\n" + url_url;

        wallstuffshare.setOnClickListener(v -> {



                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.setType("text/plain");
                sendIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                startActivity(Intent.createChooser(sendIntent, "Share wallpaper via:"));

        });



    }

    private void id_lists() {
        wallstufftitle = findViewById(R.id.wallstufftitle);
        wallstufftype = findViewById(R.id.wallpaperType);
        wallstuffimage = findViewById(R.id.wallstuffimage);
        m_prg = findViewById(R.id.w_pogrfull);
        lineartooltable = findViewById(R.id.toltable);

        download = findViewById(R.id.download);
        bfavorit = findViewById(R.id.wallfav);
        setwallpaper = findViewById(R.id.setwallpaper);
        wallstuffshare = findViewById(R.id.wallstuffshare);
    }












    @Override
    protected void onResume() {
        super.onResume();

    }

    private void a_display_image() {

        RequestOptions requestOptions = new RequestOptions().error(R.drawable.ic_holder)
                .diskCacheStrategy(DiskCacheStrategy.ALL);


            Glide.with(this).asBitmap().load(url_url).apply(requestOptions)
                    .addListener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            m_prg.setVisibility(View.GONE);
                            lineartooltable.setVisibility(View.INVISIBLE);
                            Toast.makeText(wallpaperPreview.this, "No Preview", Toast.LENGTH_SHORT).show();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            m_prg.setVisibility(View.GONE);
                            lineartooltable.setVisibility(View.VISIBLE);
                            return false;
                        }
                    })
                    .into(wallstuffimage);

        }


    private void a_set_wallpaper(View ourView) {

        Glide.with(getApplicationContext()).asBitmap().load(url_url)
                .into(new Target<Bitmap>() {
                    @Override
                    public void onLoadStarted(Drawable placeholder) {
                    }

                    @Override
                    public void onLoadFailed(Drawable errorDrawable) {
                    }

                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        WallpaperDialog wallSelectDialog = new WallpaperDialog(wallpaperPreview.this, resource, ourView);
                        wallSelectDialog.show(getSupportFragmentManager(), "wallselectdialog");
                    }

                    @Override
                    public void onLoadCleared(Drawable placeholder) {
                    }

                    @Override
                    public void getSize(SizeReadyCallback cxb) {
                        cxb.onSizeReady(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
                    }

                    @Override
                    public void removeCallback(SizeReadyCallback cb) {
                    }

                    @Override
                    public Request getRequest() {
                        return null;
                    }

                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onStop() {
                    }

                    @Override
                    public void onDestroy() {
                    }

                    @Override
                    public void setRequest(Request request) {
                    }


                });
    }

    private void is_it_fav() {
        String query = "Select * From FOOD where price = '" + url_url + "'";

        if (sqlhelper.getData(query).getCount() > 0) {
            bfavorit.setImageResource(R.drawable.favorit_true); // yes exists
        } else {
            bfavorit.setImageResource(R.drawable.ic_fav_menu); // not exists
        }
    }

    private void set_to_favorite() {

        String query = "Select * From FOOD where price = '" + url_url + "'";

        if (sqlhelper.getData(query).getCount() > 0) {
            Toast.makeText(this, "Already Added", Toast.LENGTH_SHORT).show();
            is_it_fav(); // refresh fav icon
        } else {
            //bfavorit.setImageResource(R.drawable.ic_favorite_false); // not exists
            addfavorit(); /// add as fav
            is_it_fav(); // refresh fav icon

        }
    }

    private void addfavorit() {
        try {
            sqlhelper.insertData(name_name, url_url);
            Toast.makeText(getApplicationContext(), "Added successfully!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setDownload(url_url);
            } else {
                Toast.makeText(this, "Permission denied. Cannot save image.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setDownload(String uRl) {
        ProgressDialog wall_progressDialog;
        fileplace = directory + name_name + "_" + id_id + extension;

        File direct = new File(directory);
        Toast.makeText(this, "Downloading Wallpaper...", Toast.LENGTH_LONG).show();

        if (!direct.exists()) {
            direct.mkdirs();
        }

        wall_progressDialog = new ProgressDialog(wallpaperPreview.this);
        wall_progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        wall_progressDialog.setCancelable(true);
        wall_progressDialog.setMessage("Downloading, Please Wait...");
        wall_progressDialog.show();

        RequestOptions requestOptions = new RequestOptions().error(R.drawable.ic_holder)
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(this).asBitmap().load(uRl).apply(requestOptions)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        new AsyncTask<Void, Void, Long>() {
                            @Override
                            protected Long doInBackground(Void... voids) {
                                File file = new File(fileplace);
                                long contentlength = resource.getByteCount();

                                try {
                                    OutputStream os = new FileOutputStream(file);
                                    resource.compress(Bitmap.CompressFormat.PNG, 100, os);
                                    os.flush();
                                    os.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                return contentlength;
                            }

                            @Override
                            protected void onPostExecute(Long contentlength) {
                                super.onPostExecute(contentlength);
                                wall_progressDialog.dismiss();
                                Snackbar.make(apnaview, "Download Finished", Snackbar.LENGTH_LONG)
                                        .setAction("Open", view -> {
                                            if (Build.VERSION.SDK_INT >= 24) {
                                                try {
                                                    Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                                                    m.invoke(null);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            Intent intent = new Intent();
                                            intent.setAction(Intent.ACTION_VIEW);
                                            intent.setDataAndType(Uri.parse("file://" + fileplace), "image/*");
                                            startActivity(intent);
                                        })
                                        .show();
                            }
                        }.execute();
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
    }

}