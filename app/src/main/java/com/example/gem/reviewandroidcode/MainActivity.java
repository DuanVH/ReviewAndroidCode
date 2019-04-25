package com.example.gem.reviewandroidcode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.gem.reviewandroidcode.dbsqlite.DatabaseManager;
import com.example.gem.reviewandroidcode.pojo.Student;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = MainActivity.class.getName();

  @BindView(R.id.edt_id)
  EditText mIdEdt;
  @BindView(R.id.edt_name)
  EditText mNameEdt;
  @BindView(R.id.edt_address)
  EditText mAddressEdt;
  @BindView(R.id.edt_phone)
  EditText mPhoneEdt;
  @BindView(R.id.ivMenu)
  ImageView mMenuIv;
  @BindView(R.id.ivBack)
  ImageView mBackIv;
  @BindView(R.id.ivPlay)
  ImageView mPlayIv;
  @BindView(R.id.ivPause)
  ImageView mPauseIv;
  @BindView(R.id.ivCircle)
  ImageView mCircleIv;

  private int id;
  private String name;
  private String address;
  private String numPhone;
  private DatabaseManager mDatabaseManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.e(TAG, "onCreate: ");
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    mDatabaseManager = new DatabaseManager(this);
    initViews();

//    int x = 9/0;
//    double y = (float) x;

    // TODO
//    Dog d = new Animal();
    Integer a = new Integer(2);
    Integer b = new Integer(2);
    if (a == b)
      Log.e(TAG, "onCreate: bang nhau");
    else
      Log.e(TAG, "onCreate: khong bang nhau");
    Animal dog = new Dog();  /*Con cho la Dong vat*/
    ((Dog) dog).makeNoise();
    Animal dog1 = new Dog();  // Up casting
    dog1.run();
    dog1.runAnimal();
    ((Dog) dog1).runDog();  // down casting
    /* Chi */
  }

  private void initViews() {
    ArrayList<String> oldTexts = new ArrayList<>();
    mIdEdt.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override
      public void afterTextChanged(Editable s) {
        Log.e("DUAN_LOG", "afterTextChanged: " + s.toString());
//        new Sea
//        oldTexts.add(s.toString());
      }
    });
  }

  interface Action {
    int a = 0;
    String str = "";

    void makeNoise();

    void makeNoise1();

    void makeNoise2();
  }

  class Animal {
    void eat() {

    }

    public void run() {
      Log.e("DUAN_LOG", "run animal");
    }

    public void runAnimal() {
      Log.e("DUAN_LOG", "run...aaa");
    }
  }

  class Dog extends Animal implements Action {

    @Override
    public void run() {
      Log.e("DUAN_LOG", "run dog");
    }

    @Override
    void eat() {
      Log.e("DUAN_LOG", "eat: ");
    }

    public void runDog() {
      Log.e("DUAN_LOG", "run...ddd");
    }

    @Override
    public void makeNoise() {
      Log.e("DUAN_LOG", "makeNoise " + a);
    }

    @Override
    public void makeNoise1() {

    }

    @Override
    public void makeNoise2() {

    }
  }

  @Override
  protected void onStart() {
    super.onStart();
    Log.e(TAG, "onStart: ");
  }

  @Override
  protected void onResume() {
    super.onResume();
    Log.e(TAG, "onResume: ");
  }

  @Override
  protected void onPause() {
    super.onPause();
    Log.e(TAG, "onPause: ");
  }

  @Override
  protected void onStop() {
    super.onStop();
    Log.e(TAG, "onStop: ");
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    Log.e(TAG, "onDestroy: ");
  }

  @OnClick(R.id.ivMenu)
  public void onMenuClick() {
    Log.e(TAG, "onMenuClick: ");
  }

  @OnClick(R.id.ivBack)
  public void onBackClick() {
    Log.e(TAG, "onBackClick: ");
  }

  @OnClick(R.id.ivPlay)
  public void onPlayClick() {
    id = Integer.parseInt(mIdEdt.getText().toString());
    name = mNameEdt.getText().toString();
    address = mAddressEdt.getText().toString();
    numPhone = mPhoneEdt.getText().toString();
    Student student = new Student(id, name, address, numPhone);
    mDatabaseManager.addStudent(student);
  }

  @OnClick(R.id.ivPause)
  public void onPauseClick() {
    Log.e(TAG, "onPauseClick: " + mDatabaseManager.getStudent(1).getName());
  }

  @OnClick(R.id.ivCircle)
  public void onCircleClick() {
    Log.e(TAG, "onCircleClick: ");
  }

}
