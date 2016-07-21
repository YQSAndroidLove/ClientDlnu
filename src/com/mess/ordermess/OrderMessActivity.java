package com.mess.ordermess;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobQuery.CachePolicy;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.listener.FindListener;

import com.mess.clientdlnu.R;
import com.mess.ordermess.adapter.OrderMessAdapter;
import com.mess.ordermess.adapter.OrderShopAdapter;
import com.mess.ordermess.constant.Constants;
import com.mess.ordermess.dao.MessMenu;
import com.mess.ordermess.dao.ShopMsgInfo;
import com.mess.ordermess.net.NetUntils;
import com.mess.ordermess.utils.DensityUtils;
import com.mess.publicmethod.PublicShowMessMsgActivity;
import com.mess.publicmethod.PublicShowShopMsgActivity;
import com.mess.publicmethod.SearchMess;

public class OrderMessActivity extends Activity {

	private int SELECZT_TYPE = Constants.COMMEND_MESS_NEW; // ��¼��ǰѡ��İ�ť
															// ��ʼʱΪ���²�Ʒ��ť

	private LinearLayout order_mess_Loading;
	private ListView lv_order_mess;
	private List<ShopMsgInfo> shopInfos;
	private List<MessMenu> messInfos;
	private TextView tv_mess_new, tv_mess_hot, tv_mess_discount, tv_mess_shop;
	private OrderMessAdapter messAdapter = null;
	private OrderShopAdapter shopAdapter = null;

	private int offset = 0;
	private int maxNumber = Constants.SQL_COUNT;
	private long exitTime = 0;// �˳�ʱ��
	private boolean isClickListView = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_mess_activity);

		this.order_mess_Loading = (LinearLayout) this
				.findViewById(R.id.order_mess_Loading);
		this.lv_order_mess = (ListView) this.findViewById(R.id.lv_order_mess);
		this.tv_mess_new = (TextView) this.findViewById(R.id.tv_mess_new);
		this.tv_mess_hot = (TextView) this.findViewById(R.id.tv_mess_hot);
		this.tv_mess_discount = (TextView) this
				.findViewById(R.id.tv_mess_discount);
		this.tv_mess_shop = (TextView) this.findViewById(R.id.tv_mess_shop);

		/* initialize bmob java bean */
		Bmob.initialize(this, Constants.BMOB_API_ID);

		messInfos = new ArrayList<MessMenu>();
		fillMessData(Constants.COMMEND_MESS_NEW);
		this.tv_mess_new.setBackgroundResource(R.drawable.gardient_box_pressed);

		addMessMsgListener();
	}

	/**
	 * �����˵�
	 * 
	 * @param view
	 */
	public void searchMess(View view) {
		Intent intent = new Intent(getApplicationContext(), SearchMess.class);
		startActivity(intent);
	}

	/**
	 * ���ز�Ʒ����
	 */
	private void fillMessData(final int type) {
		isClickListView = false;
		if (System.currentTimeMillis() - exitTime > 10000) {
			judgeNetState();
		}
		order_mess_Loading.setVisibility(View.VISIBLE);
		new Thread() {
			public void run() {
				getMessMessageData(type);
			}
		}.start();
		exitTime = System.currentTimeMillis();
	}

	/**
	 * ���ص����Ϣ����
	 * 
	 * @param type
	 */
	private void fillShopData(final int type) {
		isClickListView = false;
		if (System.currentTimeMillis() - exitTime > 10000) {
			judgeNetState();
		}
		order_mess_Loading.setVisibility(View.VISIBLE);
		new Thread() {
			public void run() {
				getShopMessData(type);
			}
		}.start();
		exitTime = System.currentTimeMillis();
	}

	/**
	 * �̵�����
	 * 
	 * @param view
	 */
	public void selectShop(View view) {
		// ���������غ��� viewShop
		//���û�������ˣ������Ƿ�ӳ���ˣ������趨��һ�ε��ʱ��
		if(!isClickListView){
			return;
		}
		changeBackground(Constants.COMMEND_MESS_SHOP);
		SELECZT_TYPE = Constants.COMMEND_MESS_SHOP;
		shopAdapter = null;
		shopInfos = new ArrayList<ShopMsgInfo>();
		fillShopData(Constants.COMMEND_MESS_SHOP);
	}

	/**
	 * ������Ʒ
	 * 
	 * @param view
	 */
	public void hotMarket(View view) {
		// ���������غ��� viewSalseHot
		// ���ӵ�Ҳ˵����ص���������ʱ��Ҫ������������Ϊnull,��������ʾʱ��
		// ��������view�Ļ������һ�£��ᵼ�³�������ʧ��
		// ���ε��ڶ��ε�������ٽ���ˢ�´���
		if(!isClickListView){
			return;
		}
		changeBackground(Constants.COMMEND_MESS_HOT);
		offset = 0;
		maxNumber = Constants.SQL_COUNT;
		SELECZT_TYPE = Constants.COMMEND_MESS_HOT;
		messAdapter = null;
		messInfos = null;
		messInfos = new ArrayList<MessMenu>();
		fillMessData(Constants.COMMEND_MESS_HOT);
	}

	/**
	 * �ؼ۲�Ʒ
	 * 
	 * @param view
	 */
	public void discountMess(View view) {
		// ���������غ��� viewCheap
		if(!isClickListView){
			return;
		}
		changeBackground(Constants.COMMEND_MESS_DISCOUNT);
		offset = 0;
		maxNumber = Constants.SQL_COUNT;
		SELECZT_TYPE = Constants.COMMEND_MESS_DISCOUNT;
		messAdapter = null;
		messInfos = null;
		messInfos = new ArrayList<MessMenu>();
		fillMessData(Constants.COMMEND_MESS_DISCOUNT);
	}

	/**
	 * ��Ʒ����
	 * 
	 * @param view
	 */
	public void newMess(View view) {
		// ���������غ��� viewNewMess
		if(!isClickListView){
			return;
		}
		changeBackground(Constants.COMMEND_MESS_NEW);
		offset = 0;
		maxNumber = Constants.SQL_COUNT;
		SELECZT_TYPE = Constants.COMMEND_MESS_NEW;
		messAdapter = null;
		messInfos = null;
		messInfos = new ArrayList<MessMenu>();
		fillMessData(Constants.COMMEND_MESS_NEW);
	}

	public void judgeNetState() {
		exitTime = 0;
		if (!NetUntils.isNetworkAvailable(this)) {
			Toast.makeText(getApplicationContext(), "�������Ӵ����������磡", 0).show();
		}
	}

	/**
	 * ����ѡ����İ�ť�ı���ɫ
	 * 
	 * @param select
	 *            ��ǰѡ�еİ�ť���
	 */
	public void changeBackground(int select) {
		if (Constants.COMMEND_MESS_NEW == select) {
			this.tv_mess_new
					.setBackgroundResource(R.drawable.gardient_box_pressed);
		} else {
			this.tv_mess_new.setBackgroundResource(R.drawable.gardient_box);
		}
		if (Constants.COMMEND_MESS_HOT == select) {
			this.tv_mess_hot
					.setBackgroundResource(R.drawable.gardient_box_pressed);
		} else {
			this.tv_mess_hot.setBackgroundResource(R.drawable.gardient_box);
		}
		if (Constants.COMMEND_MESS_DISCOUNT == select) {
			this.tv_mess_discount
					.setBackgroundResource(R.drawable.gardient_box_pressed);
		} else {
			this.tv_mess_discount
					.setBackgroundResource(R.drawable.gardient_box);
		}
		if (Constants.COMMEND_MESS_SHOP == select) {
			this.tv_mess_shop
					.setBackgroundResource(R.drawable.gardient_box_pressed);
		} else {
			this.tv_mess_shop.setBackgroundResource(R.drawable.gardient_box);
		}

	}

	/**
	 * �¼�����
	 */
	private void addMessMsgListener() {
		lv_order_mess.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				if (isClickListView) {
					if (SELECZT_TYPE == Constants.COMMEND_MESS_SHOP) {
						Intent intent = new Intent(getApplicationContext(),
								PublicShowShopMsgActivity.class);
						intent.putExtra("com.mess.ordermess.dao.ShopMsgInfo",
								(Parcelable) shopInfos.get(position));
						startActivity(intent);
					} else {
						Intent intent = new Intent(getApplicationContext(),
								PublicShowMessMsgActivity.class);
						intent.putExtra("com.mess.ordermess.dao.MessMenu",
								(Parcelable) messInfos.get(position));
						startActivity(intent);
					}
				}  
			}

		});
	}

	/**
	 * ȡ�÷������˵�ҵ���Ϣ
	 * 
	 * @param type
	 */
	public void getShopMessData(final int type) {
		BmobQuery<ShopMsgInfo> query = new BmobQuery<ShopMsgInfo>();
		query.order("-shop_Star");
		query.findObjects(this, new FindListener<ShopMsgInfo>() {
			@Override
			public void onSuccess(List<ShopMsgInfo> object) {
				if (object.size() > 0) {
					if (shopInfos == null) {
						shopInfos = object;
					} else {
						shopInfos.addAll(object);
					}
				}
				sendMessage(Constants.GAIN_SHOP_MSG_SUCCESS, type);
			}

			@Override
			public void onError(int code, String msg) {
				sendMessage(Constants.GAIN_SHOP_MSG_ERROR, type);
			}
		});
	}

	/**
	 * ȡ�÷���˲�Ʒ������
	 * 
	 * @param type
	 */
	public void getMessMessageData(final int type) {
		final BmobQuery<MessMenu> query = new BmobQuery<MessMenu>();
		if (type == Constants.COMMEND_MESS_NEW) {
			String d = "2015-5-1";
			query.addWhereGreaterThanOrEqualTo("market_Time", new BmobDate(
					DensityUtils.transLateStringToDate(d)));
			query.order("-market_Time");
			query.setLimit(20);
		} else if (type == Constants.COMMEND_MESS_HOT) {
			query.addWhereGreaterThan("sales_Volume", 800);
			query.order("-sales_Volume");
		} else if (type == Constants.COMMEND_MESS_DISCOUNT) {
			query.addWhereLessThanOrEqualTo("mess_Price", 8);
			query.order("mess_Price");
		}
		query.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);
		query.findObjects(OrderMessActivity.this, new FindListener<MessMenu>() {
			@Override
			public void onSuccess(List<MessMenu> object) {
				if (object.size() > 0) {
					if (messInfos == null) {
						messInfos = object;
					} else {
						messInfos.addAll(object);
					}
				}
				sendMessage(Constants.GAIN_MESS_MSG_SUCCESS, type);
			}

			@Override
			public void onError(int code, String msg) {
				sendMessage(Constants.GAIN_MESS_MSG_ERROR, type);
			}
		});
	}

	private void sendMessage(int messType, int adapterType) {
		Message handlerMsg = handler.obtainMessage();
		handlerMsg.obj = adapterType;
		handlerMsg.what = messType;
		handler.sendMessage(handlerMsg);
	}

	/**
	 * ˢ�½���
	 */
	final Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			int type = (Integer) msg.obj;
			order_mess_Loading.setVisibility(View.INVISIBLE);
			switch (msg.what) {
			case Constants.GAIN_MESS_MSG_SUCCESS:
				if (messAdapter == null) {
					messAdapter = new OrderMessAdapter(OrderMessActivity.this,
							type, messInfos);
					lv_order_mess.setAdapter(messAdapter);
				} else {
					messAdapter.notifyDataSetChanged();
				}
				isClickListView = true;
				break;
			case Constants.GAIN_SHOP_MSG_SUCCESS:
				if (shopAdapter == null) {
					shopAdapter = new OrderShopAdapter(OrderMessActivity.this,
							Constants.COMMEND_MESS_SHOP, shopInfos);
					lv_order_mess.setAdapter(shopAdapter);
				} else {
					shopAdapter.notifyDataSetChanged();
				}
				isClickListView = true;
				break;
			case Constants.GAIN_MESS_MSG_ERROR:
				messAdapter = new OrderMessAdapter(OrderMessActivity.this,
						type, messInfos);
				lv_order_mess.setAdapter(messAdapter);
				isClickListView = true;
				break;
			case Constants.GAIN_SHOP_MSG_ERROR:
				shopAdapter = new OrderShopAdapter(OrderMessActivity.this,
						Constants.COMMEND_MESS_SHOP, shopInfos);
				lv_order_mess.setAdapter(shopAdapter);
				isClickListView = true;
				break;
			default:
				break;
			}
		}
	};
}
