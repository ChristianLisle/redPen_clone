package com.example.volley_test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class JsonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);

        // Tag used to cancel the request
        String tag_json_obj ="json_obj_req";
        String url ="https://api.androidhive.info/volley/person_object.json";
        ProgressDialog pDialog =newProgressDialog(this); pDialog.setMessage("Loading..."); pDialog.show();
        JsonActivity jsonObjReq =newJsonObjectRequest(Method.GET, url,null,
                newResponse.Listener<JSONObject>() {
            @Override
                    publicvoidonResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                pDialog.hide();
            }
        },newResponse.ErrorListener() {
            @Override publicvoidonErrorResponse(VolleyError error) {
                VolleyLog.d(TAG,"Error: "+ error.getMessage()); // hide the progress dialog
                pDialog.hide();
            } });
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);



    }
}