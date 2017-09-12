package com.example.administrator.addressselectordemo;
import android.widget.Toast;

import com.example.administrator.addressselectordemo.addresslib.AddressSelector;
import com.example.administrator.addressselectordemo.addresslib.CityInterface;
import com.example.administrator.addressselectordemo.addresslib.OnItemClickListener;
import com.example.administrator.addressselectordemo.entity.City;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/9/8.
 * 参考github
 * https://github.com/Blincheng/AddressSelector
 */

public class SelectCitiesActivity extends BaseActivity {
    private ArrayList<City> cities1 = new ArrayList<>();
    private ArrayList<City> cities2 = new ArrayList<>();
    private ArrayList<City> cities3 = new ArrayList<>();
    private AddressSelector addressSelector;



    @Override
    protected void initVariables() {
        //以下这种方式不好，要从assets中获取数据比较好
        //拿到本地JSON 并转成String
        try {
            JSONArray jsonArray= new JSONArray(getString(R.string.cities1));
            for(int i =0;i<jsonArray.length();i++){
                cities1.add(new Gson().fromJson(jsonArray.get(i).toString(),City.class));
            }
            JSONArray jsonArray2= new JSONArray(getString(R.string.cities2));
            for(int i =0;i<jsonArray2.length();i++){
                cities2.add(new Gson().fromJson(jsonArray2.get(i).toString(),City.class));
            }
            JSONArray jsonArray3= new JSONArray(getString(R.string.cities3));
            for(int i =0;i<jsonArray3.length();i++){
                cities3.add(new Gson().fromJson(jsonArray3.get(i).toString(),City.class));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_selectcity);
        addressSelector = (AddressSelector) findViewById(R.id.address);
        addressSelector.setTabAmount(3);
        addressSelector.setCities(cities1);

    }

    @Override
    protected void initEnvent() {
        super.initEnvent();
        addressSelector.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void itemClick(AddressSelector addressSelector, CityInterface
                    city, int tabPosition) {
                switch (tabPosition){
                    case 0:
                        addressSelector.setCities(cities2);
                        break;
                    case 1:
                        addressSelector.setCities(cities3);
                        break;
                    case 2:
                        Toast.makeText(getApplicationContext(),"tabPosition ："+tabPosition+" "+city.getCityName(),Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        addressSelector.setOnTabSelectedListener(new AddressSelector.OnTabSelectedListener() {
            @Override
            public void onTabSelected(AddressSelector addressSelector, AddressSelector.Tab tab) {
                switch (tab.getIndex()){
                    case 0:
                        addressSelector.setCities(cities1);
                        break;
                    case 1:
                        addressSelector.setCities(cities2);
                        break;
                    case 2:
                        addressSelector.setCities(cities3);
                        break;
                }
            }

            @Override
            public void onTabReselected(AddressSelector addressSelector, AddressSelector.Tab tab) {

            }
        });
    }

    @Override
    protected void loadData() {

    }
}
