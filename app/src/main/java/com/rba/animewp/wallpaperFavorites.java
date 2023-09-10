package com.rba.animewp;

import static com.rba.animewp.wallpaperdetail.sqlhelper;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import com.rba.animewp.adapters.favorite_adapter;
import com.rba.animewp.mData.constant;
import com.rba.animewp.mData.items_favorit;

import java.util.ArrayList;

public class wallpaperFavorites extends AppCompatActivity {
    Toolbar toolbar;
    GridView gridView;
    private ArrayList<items_favorit> list;
    private favorite_adapter adapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper_favorites);

        toolbar = this.findViewById(R.id.mtoolbar);
        setSupportActionBar(toolbar);
        //setTitle("Pubg Wallpapers");

        gridView = this.findViewById(R.id.wallgridView);
        load_data();


        constant.chekinternet(this);


    }
    private void load_data() {

        list = new ArrayList<>();
        adapter = new favorite_adapter(this, R.layout.wallpaperfavitem, list);
        gridView.setAdapter(adapter);

        updateFoodList();
        gridView.setOnItemClickListener((adapterView, view, position, l) -> {

            Cursor c = sqlhelper.getData("SELECT id FROM FOOD");
            ArrayList<Integer> arrID = new ArrayList<Integer>();
            while (c.moveToNext()) {
                arrID.add(c.getInt(0));
            }
            showDialogDelete(arrID.get(position));
            c.close();
        });
        refreshdata();

    }


    void showDialogDelete(int idFood) {
        AlertDialog.Builder alert_d = new AlertDialog.Builder(this);

        alert_d.setTitle("Remove From Favorite!!");
        alert_d.setMessage("You want to delete from favorite ?");
        alert_d.setCancelable(false);

        alert_d.setPositiveButton("Delete", (dialog, which) -> {
            try {
                sqlhelper.deleteData(idFood);
                Toast.makeText(getApplicationContext(), "Deleted successfully", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
            updateFoodList();
        });

        alert_d.setNeutralButton("Cancel", (dialog, which) -> {
            dialog.dismiss();
        });

        alert_d.show();
    }

    private void updateFoodList() {

        Cursor cursor = sqlhelper.getData("SELECT * FROM FOOD");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String price = cursor.getString(2);
            byte[] image = cursor.getBlob(3);

            list.add(new items_favorit(name, price, image, id));
        }
        adapter.notifyDataSetChanged();
        cursor.close();
    }

    private void refreshdata() {

        SQLiteDatabase db = sqlhelper.getWritableDatabase();
        String count = "SELECT count(*) FROM  FOOD";
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if (icount > 0) {
            Toast.makeText(getApplicationContext(), "Favorites", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "No Favorite", Toast.LENGTH_SHORT).show();
        }
        mcursor.close();
    }

}