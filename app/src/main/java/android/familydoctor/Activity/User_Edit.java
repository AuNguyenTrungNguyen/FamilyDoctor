package android.familydoctor.Activity;

import android.content.Context;
import android.familydoctor.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by buimi on 6/23/2017.
 */

public class User_Edit extends AppCompatActivity {
    final Context context = this;
    private Toolbar toolbar;
    private ImageView imageView;



    String id = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suattcanhan);

        initToolBar();


    }


    public void luuUser(View view){



    }

    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_about);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

//    private void initDanhSachGoiY(){
//        Spinner khuvuc = (Spinner) findViewById(R.id.spinner_khuvuc);
//        Spinner linhvuc = (Spinner) findViewById(R.id.spinner_linhvuc);
//        khuvuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//        linhvuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });







//    public void setHinhAnh(){
//        imageView = (ImageView) findViewById(R.id.img_add_user);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new AlertDialog.Builder(context)
//                        .setNeutralButton("Chụp ảnh mới", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                                startActivityForResult(intent, 1);
//                            }
//                        })
//                        .setNegativeButton("Chọn ảnh từ thư viện", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                                intent.setType("image*//*");
//                                startActivityForResult(intent, 2);
//                            }
//                        })
//                        .show();
//
//            }
//        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == RESULT_OK && requestCode == 1) {
//            // lay hinh thu nho cua hinh vua chup
//            Bitmap hinh = (Bitmap) data.getExtras().get("data");
//            imageView.setImageBitmap(hinh);
//
//        }
//
//        if (resultCode == RESULT_OK && requestCode == 2) {
//
//            Uri imageUri = data.getData();
//            imageView.setImageURI(imageUri);
//
//        }
//    }*/
}
