package com.gingbear.githubtest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.InstrumentationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.PermissionInfo;
import android.content.pm.ServiceInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.Manifest;

import com.google.android.c2dm.*;

/**
 * 参考サイト
 * https://gist.github.com/2472683
 *
 */
public class CustomPackageManager {
	public static final String LOG_TAG = "CustomPackageManager";

	/**
	 * permissionの説明文を返す。
	 * @param string
	 * @return
	 */
public static String testest(String string){
	String sting = Manifest.permission.ACCESS_WIFI_STATE;
	if(string.equals(sting))
		return "Wifi の ON/OFF の検知と変更";
	sting = Manifest.permission.CHANGE_WIFI_STATE;
	if(string.equals(sting))
		return "Wifi の ON/OFF の検知と変更";
	
   //<!-- Android端末各種情報取得 -->
	sting = Manifest.permission.READ_PHONE_STATE; 
	if(string.equals(sting))
		return "Android端末各種情報取得";
	
   // <!-- ネットワーク接続状態の検知 -->
	sting = Manifest.permission.ACCESS_NETWORK_STATE;
	if(string.equals(sting))
		return "ネットワーク接続状態の検知";
	
   // <!-- インターネット利用 -->
	sting = Manifest.permission.INTERNET;
	if(string.equals(sting))
		return "インターネット利用";
	
   // <!-- GPSの利用権限 （LocationManager.GPS_PROVIDER利用時） -->
	sting = Manifest.permission.ACCESS_FINE_LOCATION;
	if(string.equals(sting))
		return "GPSの利用権限 （LocationManager.GPS_PROVIDER利用時）";
	
   // <!-- ネットワークによる位置取得の利用権限 （LocationManager.NETWORK_PROVIDER利用時） -->
	sting = Manifest.permission.ACCESS_COARSE_LOCATION;
	if(string.equals(sting))
		return "ネットワークによる位置取得の利用権限 （LocationManager.NETWORK_PROVIDER利用時）";
	
  //  <!-- エミュレータにてGPSを利用する場合の権限 -->
	sting = Manifest.permission.ACCESS_MOCK_LOCATION;
	if(string.equals(sting))
		return "エミュレータにてGPSを利用する場合の権限 ";
	

  //  <!-- バッテリー -->
	sting = Manifest.permission.BATTERY_STATS;
//    <!-- BLUETOOTH -->
	sting = Manifest.permission.BLUETOOTH;

//<!-- このアプリケーションだけがメッセージの受信と登録の結果の受信が可能 --> 
	sting = com.google.android.c2dm.C2DMBaseReceiver.REGISTRATION_CALLBACK_INTENT;
	
  //  <permission
   //     android:name="com.gingbear.githubtest.permission.C2D_MESSAGE"
  //      android:protectionLevel="signature" />

//<!-- アカウント情報を取得 --> 
	sting = Manifest.permission.GET_ACCOUNTS;
 //   <uses-permission android:name="com.gingbear.githubtest.permission.C2D_MESSAGE;
    		sting = Manifest.permission.RECEIVE_BOOT_COMPLETED;
//<!-- このアプリケーションは登録とメッセージ受信の許可がある -->
  //  <uses-permission android:name="com.google.android.c2dm.permission.RECEIVE;
//<!-- このアプリケーションは登録とメッセージ受信の許可がある -->
    		sting = Manifest.permission.INTERNET;
	sting = Manifest.permission.WAKE_LOCK;
	sting = Manifest.permission.ACCESS_NETWORK_STATE;
	
	return "";
}
	/**
	 * すべてのpermissionを列挙する
	 */
	static public void getPermissions(Context context) {
		PackageManager pm = context.getPackageManager();
		List<PackageInfo> ilist = pm
				.getInstalledPackages(PackageManager.GET_PERMISSIONS);
		List<String> appPermissions = new ArrayList<String>();
		for (PackageInfo i : ilist) {
			String[] perms = i.requestedPermissions;
			if (perms != null) {
				for (int j = 0; j < perms.length; j++) {
					if (!appPermissions.contains(perms[j])) {
						appPermissions.add(perms[j]);
					}
				}
			}
		}

		List<String> manifestPermissions = new ArrayList<String>();

		//
		Field[] fields = Manifest.permission.class.getFields();
		if (fields != null) {
			for (Field f : fields) {
				Object value = null;
				try {
					value = f.get(null);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (value != null) {
					manifestPermissions.add(value.toString());
				}
			}
		}

		List<String> allPermissions = new ArrayList<String>(manifestPermissions);
		for (String p : appPermissions) {
			if (!allPermissions.contains(p)) {
				allPermissions.add(p);
			}
		}

		// sort
		String[] appPerms = appPermissions.toArray(new String[0]);
		String[] manPerms = manifestPermissions.toArray(new String[0]);
		String[] allPerms = allPermissions.toArray(new String[0]);
		Arrays.sort(appPerms);
		Arrays.sort(manPerms);
		Arrays.sort(allPerms);

		for (int i = 0; i < appPerms.length; ++i) {
			CustomLog.v(LOG_TAG, appPerms[i] + "," + testest(appPerms[i]));

		}
	}
	
	
    private void checkInstalledApplication( String fileter , Context context){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>( context, android.R.layout.simple_list_item_1 );
        //ArrayAdapter<ImageView> images = new ArrayAdapter<ImageView>( this, android.R.layout.simple_list_item_1 );

		PackageManager pm = context.getPackageManager();
		int flag = PackageManager.GET_INTENT_FILTERS | PackageManager.GET_RECEIVERS | PackageManager.GET_PERMISSIONS;
		List<PackageInfo> list = pm.getInstalledPackages( flag  );
		for( Iterator<PackageInfo> iterator = list.iterator(); iterator.hasNext(); ){
			PackageInfo info = iterator.next();
			if( fileter != null ){
				if( info.packageName.contains(fileter) ) {
					continue;
				}
			}
			adapter.add( info.packageName );
		/*	
			ImageView v = new ImageView( getApplicationContext() );
			v.setImageDrawable( info.applicationInfo.loadIcon( pm ) );
			images.add( v );
		*/
		}


        ListView listView = (ListView)findViewById( R.id.listView1 );
        listView.setAdapter( adapter );
        //listView.setAdapter( images );

 
        // リストビューのアイテムがクリックされた時に呼び出されるコールバックリスナーを登録します
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView listView = (ListView) parent;
                // クリックされたアイテムを取得します
                String item = (String) listView.getItemAtPosition(position);
                
                Intent intent = new Intent( getApplicationContext(), PackageInfoActivity.class );
                intent.putExtra( "TEST", item );
                startActivity( intent );
            }
        });
 
        /*
        // リストビューのアイテムが選択された時に呼び出されるコールバックリスナーを登録します
        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView&lt;?&gt; parent, View view,
                    int position, long id) {
                ListView listView = (ListView) parent;
                // 選択されたアイテムを取得します
                String item = (String) listView.getSelectedItem();
                Toast.makeText(ListViewSampleActivity.this, item, Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView&lt;?&gt; parent) {
            }
        });
         */
    	
    }
  //===================================================================================
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.package_info);
        
		PackageManager pm = getPackageManager();
		int flags =
	            PackageManager.GET_ACTIVITIES |
	            PackageManager.GET_RECEIVERS | 
	            PackageManager.GET_SERVICES |
	            PackageManager.GET_PROVIDERS |
	            PackageManager.GET_INSTRUMENTATION |
	            PackageManager.GET_INTENT_FILTERS |
	            PackageManager.GET_SIGNATURES |
	            PackageManager.GET_RESOLVED_FILTER |
	            PackageManager.GET_META_DATA |
	            PackageManager.GET_GIDS |
	            PackageManager.GET_DISABLED_COMPONENTS |
	            PackageManager.GET_SHARED_LIBRARY_FILES |
	            PackageManager.GET_URI_PERMISSION_PATTERNS |
	            PackageManager.GET_PERMISSIONS |
	            PackageManager.GET_UNINSTALLED_PACKAGES |
	            PackageManager.GET_CONFIGURATIONS;
            
		//List<PackageInfo> list = pm.getInstalledPackages( flag  );

        EditText text = (EditText)findViewById( R.id.editText1 );
        EditText adText = (EditText)findViewById( R.id.editText2 );
        
        String packageName = getIntent().getStringExtra( "TEST" );
        
        MyStringBuilder str = new MyStringBuilder();
        
		try {
			PackageInfo info = pm.getPackageInfo( packageName, flags );
			ApplicationInfo app = info.applicationInfo;


			str.append( "packageName     : " + info.packageName + "\n" );
			str.append( "sharedUserId    : " + info.sharedUserId + "\n" );
			str.append( "sharedUserLabel : " + info.sharedUserLabel + "\n" );

			str.append( "instrumentation : " + info.instrumentation + "\n" );
			putInfo( str, info.instrumentation, "Instrumentation" );

			str.append( "activities : " + info.activities + "\n" );
			putInfo( str, info.activities, "Activity" );

			str.append( "receivers : " + info.receivers + "\n" );
			putInfo( str, info.receivers, "Receivers" );

			str.append( "services : " + info.services + "\n" );
			putInfo( str, info.services, "Services" );

			str.append( "permissions : " + info.permissions + "\n" );
			putInfo( str, info.permissions, "Permissions" );

			str.append( "requestedPermissions : " + info.requestedPermissions + "\n" );
			if( info.requestedPermissions != null ) {
				for( int i = 0; i < info.requestedPermissions.length; i++ ) {
					str.append( "  " + i + ")" + info.requestedPermissions[i] + "\n" );
				}
			}

			str.append( " --- application info ---\n"  );
			str.append( "  backupAgentName : " + app.backupAgentName + "\n" );
			str.append( "  dataDir         : " + app.dataDir + "\n" );
			str.append( "  manageSpaceActivityName : " + app.manageSpaceActivityName + "\n" );
			str.append( "  nativeLibraryDir : " + app.nativeLibraryDir + "\n" );
			str.append( "  sourceDir   : " + app.sourceDir + "\n" );
			str.append( "  processName : " + app.processName + "\n" );
			str.append( "  permission : " + app.permission + "\n" );

			str.append( "  sharedLibraryFiles : " + app.sharedLibraryFiles + "\n" );
			if( app.sharedLibraryFiles != null ) {
				int numOfLibs = app.sharedLibraryFiles.length;
				str.append( "     --- Libraries [" + numOfLibs + "] ---\n"  );
				for( int i = 0; i < numOfLibs; i++ ){
					str.append( "     " + i + ") " + app.sharedLibraryFiles[i] + "\n" );
				}
			}

			//app.dump( new StringBuilderPrinter(str), "#######" );	// あんまりうれしくない

	        text.append( str.toString() );
	        adText.append( str.getFiltered() );

	        //text.append( info.toString() );	// あんまりうれしくない
		}catch( NameNotFoundException e ) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
    }
    
	private void putInfo( MyStringBuilder str, InstrumentationInfo info[], String title ) {
		if( info == null ){
			return;
		}
		str.append( "  --- " + title + " info(s) ---\n"  );
		for( int i = 0; i < info.length; i++ ) {
			InstrumentationInfo inst = info[i];
			str.append( "  " + title + "[" + i + "]---\n" );
			str.append( "    name : " + inst.packageName + "\n" );
		}
	}

	private void putInfo( MyStringBuilder str, PermissionInfo info[], String title ) {
		if( info == null ){
			return;
		}

		str.append( "  --- " + title + " info(s) ---\n"  );
		for( int i = 0; i < info.length; i++ ) {
			str.append( "  " + title + "[" + i + "]---\n" );
			PermissionInfo permission = info[i];
			str.append( "    name : " + permission.name + "\n" );
			str.append( "    packageName : " + permission.packageName + "\n" );
			str.append( "    protectionLevel : " + permission.protectionLevel + "\n" );
		}
	}
    
	private void putInfo( MyStringBuilder str, ServiceInfo info[], String title ) {
		if( info == null ){
			return;
		}

		str.append( "  --- " + title + " info(s) ---\n"  );
		for( int i = 0; i < info.length; i++ ){
			str.append( "  " + title + "[" + i + "]---\n" );
			ServiceInfo service = info[i];
			str.append( "    name : " + service.name + "\n" );
			str.append( "    packageName : " + service.packageName + "\n" );
			str.append( "    enabled : " + service.enabled + "\n" );
			str.append( "    permission : " + service.permission + "\n" );
		}
	}

	private void putInfo( MyStringBuilder str, ActivityInfo info[], String title ) {
		if( info == null ){
			return;
		}

		str.append( "  --- " + title + " info(s) ---\n"  );
		for( int i = 0; i < info.length; i++ ){
			str.append( "  " + title + "[" + i + "]---\n" );
			ActivityInfo activity = info[i];
			str.append( "    name : " + activity.name + "\n" );
			str.append( "    packageName : " + activity.packageName + "\n" );
			str.append( "    targetActivity : " + activity.targetActivity + "\n" );
			str.append( "    permission : " + activity.permission + "\n" );
		}
	}

	private class MyStringBuilder  {
		private StringBuilder buf;
		private StringBuilder fileterd;

		public MyStringBuilder(){
			buf = new StringBuilder();
			fileterd = new StringBuilder();
		}

		public void append( String str ){
			buf.append(  str  );
			if( str.contains( "Ad" ) ){
				fileterd.append( str );
			}
		}

		public String toString() {
			return buf.toString();
		}

		public StringBuilder getFiltered(){
			return fileterd;
		}

	}   
    

}
