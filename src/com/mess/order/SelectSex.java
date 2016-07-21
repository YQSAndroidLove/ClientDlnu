package com.mess.order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.mess.clientdlnu.R;

public class SelectSex extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_seleclsex);
		final ImageView isman = (ImageView)findViewById(R.id.isman);
		final ImageView isweman = (ImageView)findViewById(R.id.isweman);
		final Intent intent = getIntent();
		String sex = intent.getStringExtra("sex");
		if(sex.equals("��")){
			isman.setImageResource(R.drawable.btn_check_buttonless_on);
			isweman.setImageResource(R.drawable.btn_check_buttonless_off);
		}
		else if(sex.equals("Ů")){
			isweman.setImageResource(R.drawable.btn_check_buttonless_on);
			isman.setImageResource(R.drawable.btn_check_buttonless_off);
		}
		else{
				isman.setImageResource(R.drawable.btn_check_buttonless_off);
				isweman.setImageResource(R.drawable.btn_check_buttonless_off);
			}
		isman.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				isman.setImageResource(R.drawable.btn_check_buttonless_on);
				isweman.setImageResource(R.drawable.btn_check_buttonless_off);
				setResult(0x717,intent);
				intent.putExtra("sex", "��");
				finish();
			}			
		});
		isweman.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				isweman.setImageResource(R.drawable.btn_check_buttonless_on);
				isman.setImageResource(R.drawable.btn_check_buttonless_off);
				setResult(0x717,intent);
				intent.putExtra("sex", "Ů");
				finish();
			}			
		});
	}
}
