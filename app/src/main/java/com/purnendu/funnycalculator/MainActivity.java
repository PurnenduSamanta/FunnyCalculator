package com.purnendu.funnycalculator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.udojava.evalex.Expression;
import java.math.BigDecimal;
import java.util.Objects;

public   class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView image;
    private static final int PICK_IMAGE = 100;
    private static final int STORAGE_PERMISSION_CODE=101;
    private Uri imageUri;
    private double ans1=0;
    private TextView result;
    private Button one, two, three, four, five, six, seven, eight, nine, zero, decimal, power, fbo, fbc, root, clear, equal,    add, minus, mul, div, sound;
    private ImageButton back;
    private LinearLayout result_parent;
    private final String[] Colours = {"#81ecec", "#74b9ff", "#ffeaa7", "#dfe6e9", "#fd79a8", "#e17055", "#fdcb6e", "#f6e58d", "#badc58", "#dff9fb", "#ff7979", "#c7ecee", "#7ed6df", "#e056fd", "#686de0", "#95afc0", "#22a6b3", "#4834d4", "#9c88ff", "#fbc531", "#4cd137", "#00a8ff", "#e84118", "#7f8fa6","#ff9ff3","#feca57","#ff6b6b","#48dbfb","#1dd1a1","#c8d6e5","#5f27cd","#f368e0","#8395a7","#ff7f50","#a4b0be","#ffa502","#7bed9f","#70a1ff","#dfe4ea","#eccc68","#3742fa","#8854d0","#fc5c65","#fed330","#26de81","#778ca3","#D6A2E8","#9AECDB","#25CCF7","#EAB543"};
    private boolean tone = true;
    private MediaPlayer mp;
    private VideoView vv;
    SharedPreferences sharedPreferences1;
    private String temp="";
    private int track_Image=0,track_Ans_Press=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result_parent = findViewById(R.id.result_parent);
        image = findViewById(R.id.image);
        result = findViewById(R.id.result);
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);
        seven = findViewById(R.id.seven);
        eight = findViewById(R.id.eight);
        nine = findViewById(R.id.nine);
        zero = findViewById(R.id.zero);
        decimal = findViewById(R.id.decimal);
        power = findViewById(R.id.power);
        fbo = findViewById(R.id.fbo);
        fbc = findViewById(R.id.fbc);
        root = findViewById(R.id.root);
        clear = findViewById(R.id.clear);
        equal = findViewById(R.id.equal);
        sound = findViewById(R.id.sound);
        back = findViewById(R.id.back);
        add = findViewById(R.id.add);
        minus = findViewById(R.id.minus);
        mul = findViewById(R.id.mul);
        div = findViewById(R.id.div);
        vv=findViewById(R.id.vv);

        mp = MediaPlayer.create(this, R.raw.tone);

        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        zero.setOnClickListener(this);
        decimal.setOnClickListener(this);
        power.setOnClickListener(this);
        fbo.setOnClickListener(this);
        fbc.setOnClickListener(this);
        root.setOnClickListener(this);
        clear.setOnClickListener(this);
        equal.setOnClickListener(this);
        sound.setOnClickListener(this);
        back.setOnClickListener(this);
        add.setOnClickListener(this);
        minus.setOnClickListener(this);
        mul.setOnClickListener(this);
        div.setOnClickListener(this);

        colourChange();



        vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp1) {
                vv.start(); //need to make transition seamless.
            }
        });

        Uri uri1 = Uri.parse("android.resource://com.purnendu.funnycalculator/"
                + R.raw.fish);

        vv.setVideoURI(uri1);
        vv.requestFocus();
        vv.start();




        String PACKAGE_NAME = getApplicationContext().getPackageName();
        sharedPreferences1 = getSharedPreferences("IMAGE", MODE_PRIVATE);
        Uri path = Uri.parse("android.resource://" + PACKAGE_NAME + "/" + R.drawable.cartoon1);
        String imgPath = path.toString();
        String imageUriString = sharedPreferences1.getString("url", imgPath);
        if (imageUriString != null) {
            if (imageUriString.equals(imgPath)) {
                try {
                    imageUri = Uri.parse(imgPath);
                    Glide.with(MainActivity.this).load(imageUri).into(image);
                } catch (IllegalArgumentException e) {
                    Toast.makeText(this, "Background Pic is not available,pls reinstall the app", Toast.LENGTH_SHORT).show();
                }
            }
           else if(!imageUriString.equals(imgPath)) {
                track_Image=0;
                if(permissionHandle(Manifest.permission.READ_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE)) {
                    try {
                        imageUri = Uri.parse(imageUriString);
                        Glide.with(MainActivity.this).load(imageUri).into(image);
                    } catch (IllegalArgumentException e) {
                        path = Uri.parse("android.resource://" + PACKAGE_NAME + "/" + R.drawable.cartoon1);
                        Glide.with(MainActivity.this).load(path).into(image);
                    }
                }
                else
                {
                    try {
                        imageUri = Uri.parse(imgPath);
                        Glide.with(MainActivity.this).load(imageUri).into(image);
                    } catch (IllegalArgumentException e) {
                        Toast.makeText(this, "Background Pic is not available,pls reinstall the app", Toast.LENGTH_SHORT).show();
                    }
                }
            }
           else
            {
                Toast.makeText(this, "Something Wrong Happened", Toast.LENGTH_SHORT).show();
            }
        }






        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((result.getText().toString().length() > 9)&&(track_Ans_Press==0)) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                    builder1.setMessage(result.getText().toString());
                    builder1.setCancelable(true);
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
                else if(track_Ans_Press==1)
                {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                    builder1.setMessage(String.valueOf(ans1));
                    builder1.setCancelable(true);
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
            }
        });


    }

    @Override
    protected void onPostResume() {

        vv.start();

        super.onPostResume();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        int id = v.getId();
        result.setEllipsize(TextUtils.TruncateAt.START);
        switch (id) {
            case (R.id.one):
                if (result.getText().toString().equals("00")) {
                    result.setText("1");
                } else {
                    result.setText(result.getText().toString() + "1");
                }
                track_Ans_Press=0;
                if (tone) {
                    mp.start();
                }

                if(result.getText().toString().length()<15) {
                    colourChange();
                }
                if(result.getText().toString().length()==15) {
                   colourSetWhite();
                }
                break;
            case (R.id.two):
                if (result.getText().toString().equals("00")) {
                    result.setText("2");
                } else {
                    result.setText(result.getText().toString() + "2");
                }
                track_Ans_Press=0;
                if (tone) {
                    mp.start();
                }
                if(result.getText().toString().length()<15) {
                    colourChange();
                }
                if(result.getText().toString().length()==15) {
                    colourSetWhite();
                }
                break;
            case (R.id.three):
                if (result.getText().toString().equals("00")) {
                    result.setEllipsize(TextUtils.TruncateAt.START);
                    result.setText("3");
                } else {
                    result.setText(result.getText().toString() + "3");
                }
                track_Ans_Press=0;
                if (tone) {
                    mp.start();
                }
                if(result.getText().toString().length()<15) {
                    colourChange();
                }
                if(result.getText().toString().length()==15) {
                    colourSetWhite();
                }
                break;
            case (R.id.four):
                if (result.getText().toString().equals("00")) {
                    result.setText("4");
                } else {
                    result.setText(result.getText().toString() + "4");
                }
                track_Ans_Press=0;
                if (tone) {
                    mp.start();
                }
                if(result.getText().toString().length()<15) {
                    colourChange();
                }
                if(result.getText().toString().length()==15) {
                    colourSetWhite();
                }
                break;
            case (R.id.five):
                if (result.getText().toString().equals("00")) {
                    result.setText("5");
                } else {
                    result.setText(result.getText().toString() + "5");
                }
                track_Ans_Press=0;
                if (tone) {
                    mp.start();
                }
                if(result.getText().toString().length()<15) {
                    colourChange();
                }
                if(result.getText().toString().length()==15) {
                    colourSetWhite();
                }
                break;
            case (R.id.six):
                if (result.getText().toString().equals("00")) {
                    result.setText("6");
                } else {
                    result.setText(result.getText().toString() + "6");
                }
                track_Ans_Press=0;
                if (tone) {
                    mp.start();
                }
                if(result.getText().toString().length()<15) {
                    colourChange();
                }
                if(result.getText().toString().length()==15) {
                    colourSetWhite();
                }
                break;
            case (R.id.seven):
                if (result.getText().toString().equals("00")) {
                    result.setText("7");
                } else {
                    result.setText(result.getText().toString() + "7");
                }
                track_Ans_Press=0;
                if (tone) {
                    mp.start();
                }
                if(result.getText().toString().length()<15) {
                    colourChange();
                }
                if(result.getText().toString().length()==15) {
                    colourSetWhite();
                }
                break;
            case (R.id.eight):
                if (result.getText().toString().equals("00")) {
                    result.setText("8");
                } else {
                    result.setText(result.getText().toString() + "8");
                }
                track_Ans_Press=0;
                if (tone) {
                    mp.start();
                }
                if(result.getText().toString().length()<15) {
                    colourChange();
                }
                if(result.getText().toString().length()==15) {
                    colourSetWhite();
                }
                break;
            case (R.id.nine):
                if (result.getText().toString().equals("00")) {
                    result.setText("9");
                } else {
                    result.setText(result.getText().toString() + "9");
                }
                track_Ans_Press=0;
                if (tone) {
                    mp.start();
                }
                if(result.getText().toString().length()<15) {
                    colourChange();
                }
                if(result.getText().toString().length()==15) {
                    colourSetWhite();
                }
                break;
            case (R.id.zero):
                if (result.getText().toString().equals("00")) {
                    result.setText("0");
                } else {
                    result.setText(result.getText().toString() + "0");
                }
                track_Ans_Press=0;
                if (tone) {
                    mp.start();
                }
                if(result.getText().toString().length()<15) {
                    colourChange();
                }
                if(result.getText().toString().length()==15) {
                    colourSetWhite();
                }
                break;
            case (R.id.decimal):
                if (result.getText().toString().equals("00")) {
                    result.setText("0.");
                } else {
                    result.setText(result.getText().toString() + ".");
                }
                track_Ans_Press=0;
                if (tone) {
                    mp.start();
                }
                if(result.getText().toString().length()<15) {
                    colourChange();
                }
                if(result.getText().toString().length()==15) {
                    colourSetWhite();
                }
                break;
            case (R.id.power):
                if ((result.getText().toString().equals("00")) || (result.getText().toString().equals(""))) {
                    result.setText("0^");
                } else {
                    result.setText(result.getText().toString() + "^");
                }
                track_Ans_Press=0;
                if (tone) {
                    mp.start();
                }
                if(result.getText().toString().length()<15) {
                    colourChange();
                }
                if(result.getText().toString().length()==15) {
                    colourSetWhite();
                }
                break;
            case (R.id.fbo):
                if (result.getText().toString().equals("00")) {
                    result.setText("(");
                } else {
                    result.setText(result.getText().toString() + "(");
                }
                track_Ans_Press=0;
                if (tone) {
                    mp.start();
                }
                if(result.getText().toString().length()<15) {
                    colourChange();
                }
                if(result.getText().toString().length()==15) {
                    colourSetWhite();
                }
                break;
            case (R.id.fbc):
                if (!((result.getText().toString().equals("00")) || (result.getText().toString().equals("")))) {
                    result.setText(result.getText().toString() + ")");
                    track_Ans_Press=0;
                    if (tone) {
                        mp.start();
                    }
                    if(result.getText().toString().length()<15) {
                        colourChange();
                    }
                    if(result.getText().toString().length()==15) {
                        colourSetWhite();
                    }
                }
                break;
            case (R.id.root):
                if ((result.getText().toString().equals("00")) || (result.getText().toString().equals(""))) {
                    result.setText("√(");
                } else {
                    char last = result.getText().toString().charAt(result.getText().toString().length() - 1);
                    if (Character.isDigit(last)) {
                        result.setText(result.getText().toString() + "×√(");
                    }
                    else
                    {
                        result.setText(result.getText().toString() + "√(");
                    }
                }
                track_Ans_Press = 0;
                if (tone) {
                    mp.start();
                }
                if (result.getText().toString().length() < 15) {
                    colourChange();
                }
                if (result.getText().toString().length() == 15) {
                    colourSetWhite();
                }
                break;
            case (R.id.clear):
                if ((result.getText().toString().equals("00")) || (result.getText().toString().equals(""))) {
                    Toast.makeText(this, "Nothing Input", Toast.LENGTH_SHORT).show();
                } else {
                    result.setText("");
                    track_Ans_Press=0;
                    if (tone) {
                        mp.start();
                    }
                    colourChange();
                }
                break;
            case (R.id.equal):
                if ((result.getText().toString().equals("00")) || (result.getText().toString().equals(""))) {
                    Toast.makeText(this, "Nothing Input", Toast.LENGTH_SHORT).show();
                } else {
                    if(!result.getText().toString().equals(temp)) {
                        String string = result.getText().toString();
                        String replaced = string.replace("√", "SQRT");
                         replaced = replaced.replace("×", "*");
                        try {
                            Expression expression = new Expression(replaced);
                            expression.setPrecision(10);
                            BigDecimal ans = expression.eval();
                            ans1=ans.doubleValue();
                            result.setEllipsize(TextUtils.TruncateAt.END);
                            result.setText(String.valueOf(ans1));
                            track_Ans_Press=1;
                            if (tone) {
                                mp.start();
                            }
                            colourChange();
                            temp = result.getText().toString();
                        } catch (Exception e) {
                            Toast.makeText(this, "Bad Expression", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
            case (R.id.back):
                if (result.getText().toString().equals("00")) {
                    Toast.makeText(this, "Nothing Input", Toast.LENGTH_SHORT).show();
                } else {
                    if (!result.getText().toString().equals("")) {
                        String string = result.getText().toString().substring(0, result.getText().toString().length() - 1);
                        track_Ans_Press=0;
                        if (tone) {
                            mp.start();
                        }
                        result.setText(string);
                    }
                }
                break;
            case (R.id.add):
                if (!result.getText().toString().equals("")) {
                    result.setText(result.getText().toString() + "+");
                    track_Ans_Press=0;
                    if (tone) {
                        mp.start();
                    }
                    if(result.getText().toString().length()<15) {
                        colourChange();
                    }
                    if(result.getText().toString().length()==15) {
                        colourSetWhite();
                    }
                }
                break;
            case (R.id.minus):
                if (!result.getText().toString().equals("")) {
                    result.setText(result.getText().toString() + "-");
                    track_Ans_Press=0;
                    if (tone) {
                        mp.start();
                    }
                    if(result.getText().toString().length()<15) {
                        colourChange();
                    }
                    if(result.getText().toString().length()==15) {
                        colourSetWhite();
                    }
                }
                break;
            case (R.id.mul):
                if (!result.getText().toString().equals("")) {
                    result.setText(result.getText().toString() + "×");
                    track_Ans_Press=0;
                    if (tone) {
                        mp.start();
                    }
                    if(result.getText().toString().length()<15) {
                        colourChange();
                    }
                    if(result.getText().toString().length()==15) {
                        colourSetWhite();
                    }
                }
                break;
            case (R.id.div):
                    if (!result.getText().toString().equals("")) {
                        result.setText(result.getText().toString() + "/");
                        track_Ans_Press=0;
                        if (tone) {
                            mp.start();
                        }
                        if(result.getText().toString().length()<15) {
                            colourChange();
                        }
                        if(result.getText().toString().length()==15) {
                            colourSetWhite();
                        }
                    }
                break;
            case (R.id.sound):
                if (tone) {
                    tone = false;
                    Toast.makeText(this, "Sound Off", Toast.LENGTH_SHORT).show();
                } else {
                    tone = true;
                    mp.start();
                    Toast.makeText(this, "Sound On", Toast.LENGTH_SHORT).show();
                }
                colourChange();
                break;


        }


    }

    public void colourChange() {
        SharedPreferences sharedPreferences = getSharedPreferences("colour", MODE_PRIVATE);
        int arrayIndex = sharedPreferences.getInt("hex", 0);
        String bc = Colours[arrayIndex];
        String bc_plus_one=Colours[arrayIndex+1];
        String bc_plus_two=Colours[arrayIndex+2];
        one.setBackgroundColor(Color.parseColor(bc));
        two.setBackgroundColor(Color.parseColor(bc));
        three.setBackgroundColor(Color.parseColor(bc));
        four.setBackgroundColor(Color.parseColor(bc));
        five.setBackgroundColor(Color.parseColor(bc));
        six.setBackgroundColor(Color.parseColor(bc));
        seven.setBackgroundColor(Color.parseColor(bc));
        eight.setBackgroundColor(Color.parseColor(bc));
        nine.setBackgroundColor(Color.parseColor(bc));
        zero.setBackgroundColor(Color.parseColor(bc));
        decimal.setBackgroundColor(Color.parseColor(bc));
        clear.setBackgroundColor(Color.parseColor(bc));
        add.setBackgroundColor(Color.parseColor(bc));
        minus.setBackgroundColor(Color.parseColor(bc));
        mul.setBackgroundColor(Color.parseColor(bc));
        div.setBackgroundColor(Color.parseColor(bc));
        equal.setBackgroundColor(Color.parseColor(bc));
        sound.setBackgroundColor(Color.parseColor(bc));
        power.setBackgroundColor(Color.parseColor(bc));
        root.setBackgroundColor(Color.parseColor(bc));
        fbo.setBackgroundColor(Color.parseColor(bc));
        fbc.setBackgroundColor(Color.parseColor(bc));
        result_parent.setBackgroundColor(Color.parseColor(bc_plus_one));
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor(bc_plus_two));
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(colorDrawable);

        arrayIndex++;
        if (arrayIndex > 47) {
            arrayIndex = 0;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("hex", arrayIndex);
        editor.apply();


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
            if(item.getItemId()==R.id.cb) {
                Toast.makeText(this, "Press once more time  to confirm", Toast.LENGTH_SHORT).show();
                permissionHandle(Manifest.permission.READ_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);
                track_Image = 1;
                return true;
            }
            else if(item.getItemId()==R.id.about) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialog);
                builder1.setTitle("About");
                String messege = "Funny Calculator works to provide you clean,simple,colourful UI with best user experience on your way of calculation.The video used in the application from Cassio Toledo youtube video.  Do write to us with your feedback or copyright queries at";
                String messege1 = "joysamanta123@gmail.com";
                SpannableStringBuilder builder = new SpannableStringBuilder();
                builder.append(messege);
                builder.append(" ");
                SpannableString ss = new SpannableString(messege1);
                ss.setSpan(new ForegroundColorSpan(Color.RED), 0, messege1.length(), 0);
                builder.append(ss);
                builder1.setMessage(builder);
                builder1.setCancelable(true);
                AlertDialog alert11 = builder1.create();
                alert11.show();
                return (true);
            }
            else if(item.getItemId()==R.id.rating)
            {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(
                            "https://play.google.com/store/apps/details?id=com.purnendu.funnycalculator"));
                    intent.setPackage("com.android.vending");
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(MainActivity.this, "You don't have any app to perform this action ", Toast.LENGTH_SHORT).show();
                }
                return (true);
        }
            else
        return (super.onOptionsItemSelected(item));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        sharedPreferences1=getSharedPreferences("IMAGE",MODE_PRIVATE);
        SharedPreferences.Editor Image = sharedPreferences1.edit();
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            if(data!=null)
            {
                imageUri = data.getData();
                if(imageUri!=null)
                {
                    Image.putString("url",imageUri.toString());
                    Image.apply();
                    Glide.with(MainActivity.this).load(imageUri).into(image);

                }
            }
        }
    }
    boolean permissionHandle(String permission, int requestCode)
    {
        if(track_Image==1) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, permission)
                    == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{permission},
                        requestCode);
                return false;
            } else {
                try {
                    Intent gallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(gallery, PICK_IMAGE);
                    return true;
                }
                catch (ActivityNotFoundException e)
                {
                    Toast.makeText(this, "You do not have any app to perform this action", Toast.LENGTH_SHORT).show();
                    return  false;
                }
            }
        }
        if(track_Image==0)
        {
            return ContextCompat.checkSelfPermission(MainActivity.this, permission)
                    == PackageManager.PERMISSION_GRANTED;
        }

    return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                try {
                    Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(gallery, PICK_IMAGE);
                    Toast.makeText(MainActivity.this,
                            "Storage Permission Granted",
                            Toast.LENGTH_SHORT)
                            .show();
                }
                catch (ActivityNotFoundException e)
                {
                    Toast.makeText(this, "You do not have any app to perform this action", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(MainActivity.this,
                        "Storage Permission Denied,Give storage permission manually by going to app setting ",
                        Toast.LENGTH_LONG)
                        .show();
            }
        }
    }

    public void colourSetWhite()
    {
        one.setBackgroundColor(Color.parseColor("#bdc3c7"));
        two.setBackgroundColor(Color.parseColor("#bdc3c7"));
        three.setBackgroundColor(Color.parseColor("#bdc3c7"));
        four.setBackgroundColor(Color.parseColor("#bdc3c7"));
        five.setBackgroundColor(Color.parseColor("#bdc3c7"));
        six.setBackgroundColor(Color.parseColor("#bdc3c7"));
        seven.setBackgroundColor(Color.parseColor("#bdc3c7"));
        eight.setBackgroundColor(Color.parseColor("#bdc3c7"));
        nine.setBackgroundColor(Color.parseColor("#bdc3c7"));
        zero.setBackgroundColor(Color.parseColor("#bdc3c7"));
        decimal.setBackgroundColor(Color.parseColor("#bdc3c7"));
        clear.setBackgroundColor(Color.parseColor("#bdc3c7"));
        add.setBackgroundColor(Color.parseColor("#bdc3c7"));
        minus.setBackgroundColor(Color.parseColor("#bdc3c7"));
        mul.setBackgroundColor(Color.parseColor("#bdc3c7"));
        div.setBackgroundColor(Color.parseColor("#bdc3c7"));
        equal.setBackgroundColor(Color.parseColor("#bdc3c7"));
        sound.setBackgroundColor(Color.parseColor("#bdc3c7"));
        power.setBackgroundColor(Color.parseColor("#bdc3c7"));
        root.setBackgroundColor(Color.parseColor("#bdc3c7"));
        fbo.setBackgroundColor(Color.parseColor("#bdc3c7"));
        fbc.setBackgroundColor(Color.parseColor("#bdc3c7"));
        //parent.setBackgroundColor(Color.parseColor("#bdc3c7"));
        result_parent.setBackgroundColor(Color.parseColor("#bdc3c7"));
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#bdc3c7"));
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(colorDrawable);
    }
}