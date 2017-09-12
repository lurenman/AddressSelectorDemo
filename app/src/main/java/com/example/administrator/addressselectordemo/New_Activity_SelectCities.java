package com.example.administrator.addressselectordemo;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.administrator.addressselectordemo.adapter.City1ListViewAdapter;
import com.example.administrator.addressselectordemo.adapter.City2ListViewAdapter;
import com.example.administrator.addressselectordemo.adapter.City3ListViewAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class New_Activity_SelectCities extends Activity {

    private Context mContext = this;

    private ImageView iv_back; // 返回

    private TextView tv_1;
    private TextView tv_2;
    private TextView tv_3;

    private ListView list1;
    private ListView list2;
    private ListView list3;

    private City1ListViewAdapter adapter1;
    private City2ListViewAdapter adapter2;
    private City3ListViewAdapter adapter3;

    private List<String> cityList1 = new ArrayList<String>();
    private List<String> cityList2 = new ArrayList<String>();
    private List<String> cityList3 = new ArrayList<String>();

    private int POSITION1 = 0;
    private int POSITION2 = 0;
    private int POSITION3 = 0;

    private String cityStr1 = "";
    private String cityStr2 = "";
    private String cityStr3 = "";

    /**
     * 把全国的省市区的信息以json的格式保存，解析完成后赋值为null
     */
    private JSONObject mJsonObj;

    /**
     * 所有省
     */
    private String[] mProvinceDatas;
    /**
     * key - 省 value - 市s
     */
    private Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    /**
     * key - 市 values - 区s
     */
    private Map<String, String[]> mAreaDatasMap = new HashMap<String, String[]>();

    private String pagestate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 填充标题栏 不显示顶部灰色栏
        setContentView(R.layout.activity_new_selectcity);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            pagestate = bundle.getString("pagestate");
        }
        initJsonData();

        controlInit();
        controlEvent();

    }

    /**
     * 初始化控件
     */
    @SuppressLint("InlinedApi")
    private void controlInit() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_1 = (TextView) findViewById(R.id.tv_1);
        tv_2 = (TextView) findViewById(R.id.tv_2);
        tv_3 = (TextView) findViewById(R.id.tv_3);

        list1 = (ListView) findViewById(R.id.list1);
        list2 = (ListView) findViewById(R.id.list2);
        list3 = (ListView) findViewById(R.id.list3);

        initDatas();

        adapter1 = new City1ListViewAdapter(mContext, cityList1);
        adapter2 = new City2ListViewAdapter(mContext, cityList2);
        adapter3 = new City3ListViewAdapter(mContext, cityList3);

        list1.setAdapter(adapter1);
        list2.setAdapter(adapter2);
        list3.setAdapter(adapter3);

        cityList1.addAll(java.util.Arrays.asList(mProvinceDatas));
        adapter1.setSeclection(POSITION1);
        adapter1.notifyDataSetChanged();

        updateCities();
    }

    /**
     * 控件事件
     */
    private void controlEvent() {

        iv_back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO 自动生成的方法存根
                New_Activity_SelectCities.this.finish();
            }
        });

        list1.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (POSITION1 != position) {
                    POSITION1 = position;
                    adapter1.setSeclection(POSITION1);
                    adapter1.notifyDataSetChanged();

                    POSITION2 = 0;
                    POSITION3 = 0;

                    cityStr1 = "";
                    cityStr2 = "";
                    cityStr3 = "";

                    tv_1.setText("");
                    tv_2.setText("");
                    tv_3.setText("");
                    updateCities();

                }
            }
        });
        list2.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (POSITION2 != position) {
                    POSITION2 = position;
                    adapter2.setSeclection(POSITION2);
                    adapter2.notifyDataSetChanged();

                    POSITION3 = 0;
                    cityStr2 = "";
                    cityStr3 = "";
                    tv_2.setText("");
                    tv_3.setText("");
                    updateAreas();

                }
            }
        });
        list3.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (POSITION3 != position) {
                    POSITION3 = position;
                    if (cityList3 != null && !cityList3.isEmpty()) {
                        adapter3.setSeclection(POSITION3);
                        adapter3.notifyDataSetChanged();
                        cityStr3 = cityList3.get(POSITION3);
                        tv_3.setText(cityStr3);
                    } else {
                        cityStr3 = "";
                        tv_3.setText("");
                    }
                }
            }
        });
    }

    /**
     * 从assert文件夹中读取省市区的json文件，然后转化为json对象
     */
    private void initJsonData() {
        try {
            StringBuffer sb = new StringBuffer();
            InputStream is = getAssets().open("city1.json");
            int len = -1;
            byte[] buf = new byte[is.available()];
            while ((len = is.read(buf)) != -1) {
                sb.append(new String(buf, 0, len, "utf-8"));
                // sb.append(new String(buf,"utf-8"));
            }
            is.close();
            mJsonObj = new JSONObject(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析整个Json对象，完成后释放Json对象的内存
     */
    private void initDatas() {
        try {
            JSONArray jsonArray = mJsonObj.getJSONArray("citylist");
            mProvinceDatas = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonP = jsonArray.getJSONObject(i);// 每个省的json对象
                String province = jsonP.getString("p");// 省名字

                mProvinceDatas[i] = province;

                JSONArray jsonCs = null;
                try {
                    /**
                     * Throws JSONException if the mapping doesn't exist or is
                     * not a JSONArray.
                     */
                    jsonCs = jsonP.getJSONArray("c");
                } catch (Exception e1) {
                    continue;
                }
                String[] mCitiesDatas = new String[jsonCs.length()];
                for (int j = 0; j < jsonCs.length(); j++) {
                    JSONObject jsonCity = jsonCs.getJSONObject(j);
                    String city = jsonCity.getString("n");// 市名字
                    mCitiesDatas[j] = city;
                    JSONArray jsonAreas = null;
                    try {
                        /**
                         * Throws JSONException if the mapping doesn't exist or
                         * is not a JSONArray.
                         */
                        jsonAreas = jsonCity.getJSONArray("a");
                    } catch (Exception e) {
                        continue;
                    }

                    String[] mAreasDatas = new String[jsonAreas.length()];// 当前市的所有区
                    for (int k = 0; k < jsonAreas.length(); k++) {
                        String area = jsonAreas.getJSONObject(k).getString("s");// 区域的名称
                        mAreasDatas[k] = area;
                    }
                    mAreaDatasMap.put(city, mAreasDatas);
                }

                mCitisDatasMap.put(province, mCitiesDatas);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        mJsonObj = null;
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities() {
        cityStr1 = mProvinceDatas[POSITION1];
        tv_1.setText(cityStr1);
        String[] cities = mCitisDatasMap.get(cityStr1);
        cityList2.clear();
        cityList3.clear();
        if (cities != null) {
            cityList2.addAll(java.util.Arrays.asList(cities));
            adapter2.setSeclection(POSITION2);

            updateAreas();
        } else {
            adapter3.notifyDataSetChanged();
        }
        adapter2.notifyDataSetChanged();
    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas() {

        if (mCitisDatasMap.get(cityStr1) != null) {
            cityStr2 = mCitisDatasMap.get(cityStr1)[POSITION2];
            tv_2.setText(cityStr2);
            String[] areas = mAreaDatasMap.get(cityStr2);

            if (areas != null) {
                cityList3.clear();
                adapter3.setSeclection(POSITION3);
                cityList3.addAll(java.util.Arrays.asList(areas));

                cityStr3 = cityList3.get(POSITION3);
                tv_3.setText(cityStr3);

            }
        }
        adapter3.notifyDataSetChanged();
    }

    public void showChoose(View view) {

//        if (TextUtils.equals(pagestate, "New_Activity_dzgl_add") && New_Activity_dzgl_add.tv_szdq != null) {
//            New_Activity_dzgl_add.tv_szdq.setText(cityStr1 + cityStr2 + cityStr3);
//            New_Activity_dzgl_add.tv_szdq.setTag(cityStr1 + cityStr2 + cityStr3);
//        }
//        New_Activity_SelectCities.this.finish();

    }

    @Override
    protected void onStart() {
        // TODO 自动生成的方法存根
        super.onStart();
       // CommonClass.judgeInfoExists(New_Activity_SelectCities.this);
    }

    /**
     * 键盘监听事件
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            New_Activity_SelectCities.this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}