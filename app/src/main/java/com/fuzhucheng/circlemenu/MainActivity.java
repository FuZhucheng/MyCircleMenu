package com.fuzhucheng.circlemenu;

import com.bumptech.glide.Glide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class MainActivity extends AppCompatActivity {

    private UpCircleMenuLayout myCircleMenuLayout;

    //四个fragment页面
    private HomepageFragment homepageFragment;
    private SettingFragment settingFragment;
    private HistoryFragment historyFragment;
    private FourthFragment fourthFragment;
    private FifthFragment fifthFragment;

    private String[] mItemTexts = new String[]{"安全中心 ", "特色服务", "投资理财",
            "转账汇款", "我的账户", "安全中心", "特色服务", "投资理财", "转账汇款", "我的账户"};
    private int[] mItemImgs = new int[]{R.drawable.home_mbank_1_normal,
            R.drawable.home_mbank_2_normal, R.drawable.home_mbank_3_normal,
            R.drawable.home_mbank_4_normal, R.drawable.home_mbank_5_normal,
            R.drawable.home_mbank_1_normal, R.drawable.home_mbank_2_normal,
            R.drawable.home_mbank_3_normal, R.drawable.home_mbank_4_normal,
            R.drawable.home_mbank_5_normal};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.ss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;

//               intent = new Intent(MainActivity.this, CircleActivity.class);
//
//                startActivity(intent);


                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.circle_menu_item2, null);
                ImageView iv = (ImageView) view
                        .findViewById(R.id.id_circle_menu_item_image3);
//                TextView tv = (TextView) view
//                        .findViewById(R.id.id_circle_menu_item_text2);
//                iv.setVisibility(View.GONE);
                iv.setImageDrawable(getResources().getDrawable((R.drawable.turnplate_center_unlogin)));
//                tv.setText("adasd");

                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setMaxHeight(50);
                imageView.setImageDrawable(getResources().getDrawable((R.drawable.turnplate_center_unlogin)));
//
                myCircleMenuLayout.addByView(view);
//                myCircleMenuLayout.removeAllViews();
//                myCircleMenuLayout.setMenuItemIconsAndTexts(mItemImgs,mItemTexts);

//                myCircleMenuLayout.setMenuItemIconsAndTexts(mItemImgs,mItemTexts);
            }




        });
        findViewById(R.id.ss1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                myCircleMenuLayout.requestLayout();

                addHeadView();

            }
        });



        //第一次初始化首页默认显示第一个fragment
        initFragment1();
        myCircleMenuLayout = (UpCircleMenuLayout) findViewById(R.id.id_mymenulayout);
        myCircleMenuLayout.setMenuItemIconsAndTexts(mItemImgs,mItemTexts);
        myCircleMenuLayout.setOnMenuItemClickListener(new UpCircleMenuLayout.OnMenuItemClickListener() {

            @Override
            public void itemClick(int pos) {
                System.out.println("1111111111111:"+pos);
//                Toast.makeText(MainActivity.this, mItemTexts[pos],
//                        Toast.LENGTH_SHORT).show();
                switch (pos) {
                    case 0:
                        initFragment1();
                        setTitle("安全中心");
                        break;
                    case 1:
                        initFragment2();
                        setTitle("特色服务");
                        break;
                    case 2:
                        initFragment3();
                        setTitle("投资理财");
                        break;
                    case 3:
                        initFragment4();
                        setTitle("转账汇款");
                        break;
                    case 4:
                        initFragment5();
                        setTitle("我的账户");
                        break;
                    case 5:
                        initFragment1();
                        setTitle("安全中心");
                        break;
                    case 6:
                        initFragment2();
                        setTitle("特色服务");
                        break;
                    case 7:
                        initFragment3();
                        setTitle("投资理财");
                        break;
                    case 8:
                        initFragment4();
                        setTitle("转账汇款");
                        break;
                    case 9:
                        initFragment5();
                        setTitle("我的账户");
                        break;
                }
            }

            @Override
            public void itemClick(View view, int pos) {

            }

            @Override
            public void itemCenterClick(View view) {
                Toast.makeText(MainActivity.this,
                        "you can do something just like ccb  ",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void addHeadView(){

        List<View> views = new ArrayList<View>();
        for(int i=0;i<4;i++) {


            View view = LayoutInflater.from(getApplicationContext())
                    .inflate(R.layout.circle_menu_item3, null);



            ImageView iv = (ImageView) view
                    .findViewById(R.id.id_circle_menu_item_image);


            Glide.with(getApplicationContext())
                    .load("as")
                    .placeholder(R.drawable.el_assist_icon_personal_placeholder)
                    .bitmapTransform(
                            new CropCircleTransformation(getApplicationContext()))
                    .into(iv);
//
//
//            tv.setText(guardianDetailVo.getRealName());

            views.add(view);
        }
        myCircleMenuLayout.setAngleInterval(52);
        myCircleMenuLayout.addByAllView(views);

    }




    //显示第一个fragment
    private void initFragment1(){
        //开启事务，fragment的控制是由事务来实现的

        homepageFragment = new HomepageFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_tv,homepageFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    //显示第二个fragment
    private void initFragment2(){
        //开启事务，fragment的控制是由事务来实现的

        settingFragment = new SettingFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_tv,settingFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    private void initFragment3(){
        //开启事务，fragment的控制是由事务来实现的

        historyFragment = new HistoryFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_tv,historyFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    private void initFragment4(){
        //开启事务，fragment的控制是由事务来实现的

        fourthFragment = new FourthFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_tv,fourthFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    private void initFragment5(){
        //开启事务，fragment的控制是由事务来实现的

        fifthFragment = new FifthFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_tv,fifthFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
