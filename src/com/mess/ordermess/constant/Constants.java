package com.mess.ordermess.constant;

import android.os.Environment;

public class Constants {
	/*****************************************/
	
	public static String BMOB_API_ID = "b7af65d596b5d5d543ae0271aee8c224";
	public static String BMOB_API_ACCESSKEY = "981551412b1011855f230d14a4e64668";
	
	/*****************************************/
	
	public static String mSdRootPath = Environment.getExternalStorageDirectory().getPath();  
	public final static String FOLDER_NAME = "/ClientDlnu";  
	public final static String PICTURE_YUAN = "big_";  
	   
	/*****************************************/
	
	public static String HOME = "��ҳ";
	public static String ORDER_MESS = "�Ƽ�";
	public static String ORDER_CLASSIFY = "����";
	public static String MY_ORDER_MESS = "�ҵ�";
	
	/*****************************************/
	
	public static int SQL_COUNT = 24;	
	public static float DIALOG_SCREEN_WIDTH_SCALE = 0.8f;
	public static float DIALOG_SCREEN_HEIGHT_SCALE = 0.4f;
	
	/*****************************************/
	
	//��ʾ��Ʒ��Ʒ���б�ʶid
	public static int COMMEND_MESS_NEW = 0x0001;
	//��ʾ������Ʒ���б�ʶid
	public static int COMMEND_MESS_HOT = 0x0002;
	//��ʾ�ͼ۲�Ʒ���б�ʶid
	public static int COMMEND_MESS_DISCOUNT = 0x0003;
	//��ʾ�̵����б�ʶid
	public static int COMMEND_MESS_SHOP = 0x0004;
	
	/*******************************************/
	
	public static final int UPDATE_EMAIL = 0x0005;
	public static final int UPDATE_CALL = 0x0006;
	public static final int UPDATE_ADDRESS = 0x0007;
	public static final int UPDATE_SEX = 0x0008;
	public static final int UPDATE_MAJOR = 0x00024;
	public static final int UPDATE_NICK_NAME = 0x00025;
	public static final int UPDATE_AUTOGRAPH = 0x0009;
	public static final int UPDATE_GRAPH = 0x0010;
	
	/********************************************/
	
	public static int COMMEND_MESS_CLISSIFY = 0x0011;
	
	/*********************************************/
	
	public static int LOADING_SAMLL_IMAGE = 0x0012;
	public static int LOADING_BIG_IMAGE = 0x0013;
	
	/*****************************************/
	
	public final static int GAIN_MESS_MSG_SUCCESS = 0x0014;
	public final static int GAIN_SHOP_MSG_SUCCESS = 0x0015;
	public final static int GAIN_MESS_MSG_ERROR = 0x0016;
	public final static int GAIN_SHOP_MSG_ERROR = 0x0017;
	public final static int REFRESH_SYSTEM_TIME = 0x0018;
	public final static int SHOP_ACTIVITY_DATA = 0x0019;
	public final static int GALLERY_REFRESH = 0x0020;
	public final static int DOWNLOAD_ACTIVITY_IMAGE = 0x0021;
	
	
	public final static int GRIDVIEW_NEWS_ADAPTER = 0x0022;
	public final static int GRIDVIEW_CLASSIFY_ADAPTER = 0x0023;
	
	/*****************************************/
	
	public static String CLEAR_CACHE_NOTIFICATION = "�Ѿ���Ӧ�û���������ϣ�";
	public static String NOTIFICATION_CONTENT_TITLE = "ϵͳ����";
	public static String NOTIFICATION_CONTENT_TEXT = "�Ѿ���Ӧ�á���α�������ͼƬ������ϣ�";
	
	public static String CLEAR_CACHE_TIME[] = {
		"15����","30����","1Сʱ","24Сʱ","�ֶ�����"
	} ;
}
