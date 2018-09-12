package com.fuzhucheng.circlemenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * <pre>
 * @author zhy
 * http://blog.csdn.net/lmj623565791/article/details/43131133
 * </pre>
 */
public class CircleActivity extends Activity
{

	private CircleMenuLayout mCircleMenuLayout;

	private Button mButton;


	private String[] mItemTexts = new String[] { "安全中心 ", "特色服务", "投资理财",
			"转账汇款", "我的账户", "信用卡","信用卡","信用卡" };
	private int[] mItemImgs = new int[] { R.drawable.home_mbank_1_normal,
			R.drawable.home_mbank_2_normal, R.drawable.home_mbank_3_normal,
			R.drawable.home_mbank_4_normal, R.drawable.home_mbank_5_normal,
			R.drawable.home_mbank_6_normal,R.drawable.home_mbank_6_normal,R.drawable.home_mbank_6_normal };

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		//自已切换布局文件看效果
		setContentView(R.layout.activity_main02);


		mButton= (Button) findViewById(R.id.bt);
//		setContentView(R.layout.activity_main);

		mCircleMenuLayout = (CircleMenuLayout) findViewById(R.id.id_menulayout);
//		mCircleMenuLayout.setMenuItemIconsAndTexts(mItemImgs, mItemTexts);
//
//
//
//		mCircleMenuLayout.setOnMenuItemClickListener(new OnMenuItemClickListener()
//		{
//
//			@Override
//			public void itemClick(View view, int pos)
//			{
//				Toast.makeText(CircleActivity.this, mItemTexts[pos],
//						Toast.LENGTH_SHORT).show();
//
//			}
//
//			@Override
//			public void itemCenterClick(View view)
//			{
//				Toast.makeText(CircleActivity.this,
//						"you can do something just like ccb  ",
//						Toast.LENGTH_SHORT).show();
//
//			}
//		});










		mButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mCircleMenuLayout.setMenuItemIconsAndTexts(mItemImgs, mItemTexts);
			}
		});

	}

}
