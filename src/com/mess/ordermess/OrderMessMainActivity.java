package com.mess.ordermess;

import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;

import com.mess.clientdlnu.R;
import com.mess.order.MyMessage;
import com.mess.ordermess.constant.Constants;

public class OrderMessMainActivity extends TabActivity {

	private TabHost tabHost;

	private SharedPreferences sp;
	private boolean isInit = true;
	private long exitTime=0;// �˳�ʱ��

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.tabhosts);
		tabHost = this.getTabHost();

		sp = getSharedPreferences("config", MODE_PRIVATE);

		TabWidget tabWidget = this.getTabWidget();
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
				Gravity.BOTTOM);

		adddTabHostMenu();
		addChangeListener();
	}

	/**
	 * ΪTabhost��Ӳ˵�ѡ�
	 */
	private void adddTabHostMenu() {
		// Home Tab
		Intent intent0 = new Intent(this, NewsActivity.class);
		addMyStyleTab(Constants.HOME, R.drawable.new_activity_press, intent0);

		// Order Mess Tab
		Intent intent1 = new Intent(this, OrderMessActivity.class);
		addMyStyleTab(Constants.ORDER_MESS, R.drawable.shop, intent1);

		// Order Mess Tab
		Intent intent2 = new Intent(this, ClassifyNavigationActivity.class);
		addMyStyleTab(Constants.ORDER_CLASSIFY, R.drawable.navigation, intent2);

		// My Order Mess Tab
		Intent intent3 = new Intent(this, MyOrderActivity.class);
		addMyStyleTab(Constants.MY_ORDER_MESS, R.drawable.my_order, intent3);
	}

	/**
	 * �Զ���tabhostѡ����
	 * 
	 * @param label
	 *            �趨��ʾ���ı�
	 * @param drawableId
	 *            Ҫ��ʾ��ͼ��
	 * @param intent
	 *            Ҫ��ʾ�Ľ���
	 */
	private void addMyStyleTab(String label, int drawableId, Intent intent) {
		TabHost.TabSpec spec = tabHost.newTabSpec(label);

		View tabIndicator = LayoutInflater.from(this).inflate(
				R.layout.tabhost_item_style, getTabWidget(), false);
		TextView title = (TextView) tabIndicator.findViewById(R.id.tv_tab_tip);
		title.setText(label);
		if (isInit) {
			title.setTextColor(Color.rgb(238, 156, 12));
			isInit = false;
		}
		ImageView icon = (ImageView) tabIndicator
				.findViewById(R.id.img_tab_icon);
		icon.setImageResource(drawableId);

		spec.setIndicator(tabIndicator);
		spec.setContent(intent);
		tabHost.addTab(spec);
	}

	/**
	 * ��ѡ��ı�ʱ���ı�ͼ��
	 */
	public void addChangeListener() {
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {

				View view;
				ImageView iv;
				TextView tv;

				view = tabHost.getTabWidget().getChildAt(0);
				iv = (ImageView) view.findViewById(R.id.img_tab_icon);// ��ȡ�ؼ�imageView
				tv = (TextView) view.findViewById(R.id.tv_tab_tip);
				if (Constants.HOME.equals(tabId)) {
					iv.setImageDrawable(getResources().getDrawable(
							R.drawable.new_activity_press));// �ı�������Ҫ��ͼ��
					tv.setTextColor(Color.rgb(238, 156, 12));
				} else {
					iv.setImageDrawable(getResources().getDrawable(
							R.drawable.new_activity));// �ı�������Ҫ��ͼ��
					tv.setTextColor(Color.rgb(140, 140, 140));
				}

				view = tabHost.getTabWidget().getChildAt(1);
				iv = (ImageView) view.findViewById(R.id.img_tab_icon);
				tv = (TextView) view.findViewById(R.id.tv_tab_tip);
				if (Constants.ORDER_MESS.equals(tabId)) {
					iv.setImageDrawable(getResources().getDrawable(
							R.drawable.shop_press));
					tv.setTextColor(Color.rgb(137, 195, 31));
				} else {
					iv.setImageDrawable(getResources().getDrawable(
							R.drawable.shop));
					tv.setTextColor(Color.rgb(140, 140, 140));
				}

				view = tabHost.getTabWidget().getChildAt(2);
				iv = (ImageView) view.findViewById(R.id.img_tab_icon);
				tv = (TextView) view.findViewById(R.id.tv_tab_tip);
				if (Constants.ORDER_CLASSIFY.equals(tabId)) {
					iv.setImageDrawable(getResources().getDrawable(
							R.drawable.navigation_press));
					tv.setTextColor(Color.rgb(248, 52, 52));
				} else {
					iv.setImageDrawable(getResources().getDrawable(
							R.drawable.navigation));
					tv.setTextColor(Color.rgb(140, 140, 140));
				}

				view = tabHost.getTabWidget().getChildAt(3);
				iv = (ImageView) view.findViewById(R.id.img_tab_icon);
				tv = (TextView) view.findViewById(R.id.tv_tab_tip);
				if (Constants.MY_ORDER_MESS.equals(tabId)) {
					iv.setImageDrawable(getResources().getDrawable(
							R.drawable.my_order_press));
					tv.setTextColor(Color.rgb(51, 181, 229));
				} else {
					iv.setImageDrawable(getResources().getDrawable(
							R.drawable.my_order));
					tv.setTextColor(Color.rgb(140, 140, 140));
				}
			}

		});
	}
	
	@Override
	public void onBackPressed() {
		
	}
	
	@Override
	protected void onDestroy() {
		boolean isAutoLogin = sp.getBoolean("is_auto_login", true);
		if(!isAutoLogin){
			BmobUser.logOut(OrderMessMainActivity.this);
		}
		super.onDestroy();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		 
		// TODO �����η��ؼ��˳�Ӧ�ó���
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// �жϼ��ʱ�� ����2����˳�Ӧ��
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				// Ӧ����
				String applicationName = getResources().getString(
						R.string.app_name);
				String msg = "�ٰ�һ�η��ؼ��˳�" + applicationName;
				Toast.makeText(OrderMessMainActivity.this, msg, 0).show();
				exitTime = System.currentTimeMillis();
			} else {
//				moveTaskToBack(false); 
//				return false;
			}
			
		}
		return false;
	}
}
