package com.mess.ordermess.adapter;

import java.text.DateFormat;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mess.clientdlnu.R;
import com.mess.ordermess.adapter.LoadImage.GetImageBitmap;
import com.mess.ordermess.constant.Constants;
import com.mess.ordermess.dao.MessMenu;
import com.mess.ordermess.utils.DensityUtils;

public class OrderMessAdapter extends BaseAdapter {

	private Activity context;
	private int type;
	private int mSelect = 0;
	private List<MessMenu> messInfos;

	/**
	 * ListView������ʾ������
	 * 
	 * @param context
	 *            ��ǰ������
	 * @param type
	 *            ��������
	 * @param messInfos
	 *            ��Ҫ���ص�����
	 */
	public OrderMessAdapter(Activity context, int type, List<MessMenu> messInfos) {
		this.context = context;
		this.type = type;
		this.messInfos = messInfos;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return messInfos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		return viewTemp(position, convertView);
	}

	/**
	 * ͨ������������������
	 * 
	 * @param position
	 *            ��ȡ���ݵ�λ��
	 * @param convertView
	 *            ��ʷ�����viwe����
	 * @return
	 */
	private View viewTemp(int position, View convertView) {
		View view;
		final ViewPriceHolder holder;
		if (convertView != null && convertView instanceof RelativeLayout) {
			view = convertView;
			holder = (ViewPriceHolder) convertView.getTag();
		} else {
			view = View.inflate(context, R.layout.nav_mess_list_item, null);
			holder = new ViewPriceHolder();
			holder.iv_mess_icon = (ImageView) view
					.findViewById(R.id.iv_mess_icon);
			holder.iv_mess_name = (TextView) view
					.findViewById(R.id.iv_mess_name);
			holder.tv_mess_price = (TextView) view
					.findViewById(R.id.tv_mess_price);
			holder.iv_mess_temp = (TextView) view
					.findViewById(R.id.iv_mess_temp);
			holder.mv_mess_state = (ImageView) view
					.findViewById(R.id.mv_mess_state);
			view.setTag(holder);
		}

		final MessMenu messObject = messInfos.get(position);

		String messName = messObject.getMess_Name();

		holder.iv_mess_icon.setImageResource(R.drawable.loading);

		LoadImage.LoadImageFile(context, Constants.LOADING_SAMLL_IMAGE,
				messObject.getMess_Graph(), new GetImageBitmap() {

					@Override
					public void getImageBitmap(Bitmap bitmap, String cacheUrl) {
						// ͨ���ص���λͼ·����ȡ���ļ���
						// �жϵõ���bitmapͼƬ�Ƿ��뵱ǰҪ���ص��ļ��Ƿ���һ�µģ�
						// ����ǣ�����ʾ��λͼ��������жϣ������ͼƬ���ϵ���ת
						if (DensityUtils.judgeFileSame(
								messObject.getMess_Graph(), cacheUrl)) {
							holder.iv_mess_icon.setImageBitmap(bitmap);
						}
					}
				});

		holder.iv_mess_name.setText(messName);
		holder.iv_mess_temp.setText(messObject.getMess_Describe());

		if (type == Constants.COMMEND_MESS_NEW) {
			String date = messObject.getMarket_Time().getDate();
			String str = DateFormat.getDateInstance()
					.format(DensityUtils.transLateStringToDate(date))
					.toString();
			holder.tv_mess_price.setText("����ʱ��:" + str);
			holder.mv_mess_state.setImageResource(R.drawable.newmarket);
		} else if (type == Constants.COMMEND_MESS_HOT) {
			int salseCount = messObject.getSales_Volume();
			holder.tv_mess_price.setText("������: " + salseCount);
			if (salseCount >= 500) {
				holder.mv_mess_state
						.setImageResource(R.drawable.salse_hot_fire);
			} else {
				holder.mv_mess_state.setImageResource(R.drawable.salse_hot);
			}
		} else if (type == Constants.COMMEND_MESS_DISCOUNT) {
			holder.tv_mess_price.setText("ʡ�ļ�: " + messObject.getMess_Price()
					+ "Ԫ");
			holder.mv_mess_state.setImageResource(R.drawable.cheap);
		} else if (type == Constants.COMMEND_MESS_CLISSIFY) {
			holder.tv_mess_price.setText("�ο���: " + messObject.getMess_Price()
					+ "Ԫ");
			holder.mv_mess_state.setImageResource(R.drawable.nav_mess_selector);
		}
		return view;
	}
}
