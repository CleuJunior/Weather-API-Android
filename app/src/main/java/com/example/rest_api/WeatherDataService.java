package com.example.rest_api;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherDataService {

    public static final String QUERY_FOR_CITY_ID = "https://www.metaweather.com/api/location/search/?query=";
    Context context;
    String cityID;

    public WeatherDataService(Context context)
    {
        this.context = context;
    }

    public interface VolleyResponseListenner
    {
        void onError(String message);
        void onResponse(String cityD);
    }

    public void getCityID(String cityName, VolleyResponseListenner volleyResponseListenner)
    {
        String url = QUERY_FOR_CITY_ID + cityName;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>(){
            @Override
            public void onResponse(JSONArray response)
            {
                cityID = "";
                try {
                    JSONObject cityInfo = response.getJSONObject(0);
                    cityID = cityInfo.getString("woeid");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


//                Toast.makeText(context, "Cty ID: " + cityID, Toast.LENGTH_LONG).show();
                volleyResponseListenner.onResponse(cityID);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(context, "Something Wrong", Toast.LENGTH_LONG).show();
                volleyResponseListenner.onResponse("Something Wrong");
            }
        });

        MySingleton.getInstance(context).addToRequestQueue(request);

       // return cityID;
    }

//    public List<WeatherReportModel> getCityForecastByID(String cityID)
//    {
//
//    }
//
//    public List<WeatherReportModel> getCityForecastByName(String cityName)
//    {
//
//    }
}
