package com.example.managementresiko.prosedur;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.managementresiko.R;
import com.example.managementresiko.app.AppConfig;
import com.example.managementresiko.app.AppController;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SistemKominikasi extends Fragment {

    public SistemKominikasi() {
        // Required empty public constructor
    }

    View root;
    String kd_responden,jenis_tabel,kesesuaian_b1,kesesuaian_b2,kesesuaian_b3,kelengkapan_b1,kelengkapan_b2,kelengkapan_b3,deskripsi_b1,deskripsi_b2,deskripsi_b3;
    Spinner spn_kesesuaian_b1,spn_kesesuaian_b2,spn_kesesuaian_b3,spn_kelengkapan_b1,spn_kelengkapan_b2,spn_kelengkapan_b3;
    EditText txt_kondisi_b1,txt_kondisi_b2,txt_kondisi_b3;
    Button btn_simpan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_sistem_kominikasi, container, false);
        Intent i= getActivity().getIntent();
        kd_responden = i.getStringExtra("no_responden");
        jenis_tabel = i.getStringExtra("jenis_tabel");

        fetchData();

        spn_kelengkapan_b1 = root.findViewById(R.id.b1_kelengkapan);
        spn_kelengkapan_b2 = root.findViewById(R.id.b2_kelengkapan);
        spn_kelengkapan_b3 = root.findViewById(R.id.b3_kelengkapan);

        spn_kesesuaian_b1 = root.findViewById(R.id.b1_kesesuaian);
        spn_kesesuaian_b2 = root.findViewById(R.id.b2_kesesuaian);
        spn_kesesuaian_b3 = root.findViewById(R.id.b3_kesesuaian);

        txt_kondisi_b1 = root.findViewById(R.id.b1_deskripsi);
        txt_kondisi_b2 = root.findViewById(R.id.b2_deskripsi);
        txt_kondisi_b3 = root.findViewById(R.id.b3_deskripsi);

        btn_simpan = root.findViewById(R.id.btn_simpan);

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createData();
            }
        });

        return root;
    }

    private void createData() {
        //Create AsycHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        RequestParams data = new RequestParams();
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();


        String[] selectionValKelenglapan = {"a","b","c","d","e"};
        String[] selectionVal = {"1","2","3","4","5"};
        kesesuaian_b1 = selectionVal[(int) spn_kesesuaian_b1.getSelectedItemId()];
        kesesuaian_b2 = selectionVal[(int) spn_kesesuaian_b2.getSelectedItemId()];
        kesesuaian_b3 = selectionVal[(int) spn_kesesuaian_b3.getSelectedItemId()];

        kelengkapan_b1 = selectionValKelenglapan[(int) spn_kelengkapan_b1.getSelectedItemId()];
        kelengkapan_b2 = selectionValKelenglapan[(int) spn_kelengkapan_b2.getSelectedItemId()];
        kelengkapan_b3 = selectionValKelenglapan[(int) spn_kelengkapan_b3.getSelectedItemId()];

        deskripsi_b1 = txt_kondisi_b1.getText().toString();
        deskripsi_b2 = txt_kondisi_b2.getText().toString();
        deskripsi_b3 = txt_kondisi_b3.getText().toString();

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("no_responden",kd_responden);
        map.put("tabel",jenis_tabel);
        map.put("b1_kesesuaian",kesesuaian_b1);
        map.put("b2_kesesuaian",kesesuaian_b2);
        map.put("b3_kesesuaian",kesesuaian_b3);

        map.put("b1_kelengkapan",kelengkapan_b1);
        map.put("b2_kelengkapan",kelengkapan_b2);
        map.put("b3_kelengkapan",kelengkapan_b3);

        map.put("b1_deskripsi",deskripsi_b1);
        map.put("b2_deskripsi",deskripsi_b2);
        map.put("b3_deskripsi",deskripsi_b3);

        wordList.add(map);
        params.put("tambahResponden",wordList);

        System.out.println(params);
        Log.d("Data : ", String.valueOf(params));
        client.post(AppConfig.URL_UPDATE, params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                System.out.println(response);
                //dismiss();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean status = jObj.getBoolean("status");

                    Log.e("Status ", String.valueOf(status));

                    // Check for error node in json
                   /* if (status==true) {
                        String data = jObj.getString("data");
                        String kd_reesponden = data.toString();
                        Intent intent = new Intent(getActivity(),FormB.class);
                        intent.putExtra("no_responden",kd_reesponden);
                        startActivity(intent);

                    }*/

                    Toast.makeText(getContext(), "Data Tersimpan!", Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getContext(), "Data Tersimpan!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    /*startActivity(new Intent(getContext(),MainActivity.class));*/
                }
            }

            @Override
            public void onFailure(int statusCode, Throwable error,
                                  String content) {
                // TODO Auto-generated method stub
                //     pDialog.hide();
                if(statusCode == 404){
                    Toast.makeText(getContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }else if(statusCode == 500){
                    Toast.makeText(getContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet]", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void fetchData() {
        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppConfig.URL_DATA_PROSEDUR+kd_responden,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {

                        dialog.hide();


                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean status= jsonObject.getBoolean("status");
                            if (status==true) {
                                // successfully received product details
                                JSONArray productObj = jsonObject.getJSONArray("data"); // JSON Array

                                // get first product object from JSON Array
                                for (int i = 0; i < productObj.length(); i++) {
                                    JSONObject c = productObj.getJSONObject(i);

                                    if (c.getString("b1_kelengkapan").equals("a")){
                                        spn_kelengkapan_b1.setSelection(0);
                                    }else if (c.getString("b1_kelengkapan").equals("b")){
                                        spn_kelengkapan_b1.setSelection(1);
                                    }else if (c.getString("b1_kelengkapan").equals("c")){
                                        spn_kelengkapan_b1.setSelection(2);
                                    }else if (c.getString("b1_kelengkapan").equals("d")){
                                        spn_kelengkapan_b1.setSelection(3);
                                    }else if (c.getString("b1_kelengkapan").equals("e")){
                                        spn_kelengkapan_b1.setSelection(4);
                                    }

                                    if (c.getString("b1_kesesuaian").equals("1")){
                                        spn_kesesuaian_b1.setSelection(0);
                                    }else if (c.getString("b1_kesesuaian").equals("2")){
                                        spn_kesesuaian_b1.setSelection(1);
                                    }else if (c.getString("b1_kesesuaian").equals("3")){
                                        spn_kesesuaian_b1.setSelection(2);
                                    }else if (c.getString("b1_kesesuaian").equals("4")){
                                        spn_kesesuaian_b1.setSelection(3);
                                    }else if (c.getString("b1_kesesuaian").equals("5")){
                                        spn_kesesuaian_b1.setSelection(4);
                                    }

                                    if (c.getString("b2_kelengkapan").equals("a")){
                                        spn_kelengkapan_b2.setSelection(0);
                                    }else if (c.getString("b2_kelengkapan").equals("b")){
                                        spn_kelengkapan_b2.setSelection(1);
                                    }else if (c.getString("b2_kelengkapan").equals("c")){
                                        spn_kelengkapan_b2.setSelection(2);
                                    }else if (c.getString("b2_kelengkapan").equals("d")){
                                        spn_kelengkapan_b2.setSelection(3);
                                    }else if (c.getString("b2_kelengkapan").equals("e")){
                                        spn_kelengkapan_b2.setSelection(4);
                                    }

                                    if (c.getString("b2_kesesuaian").equals("1")){
                                        spn_kesesuaian_b2.setSelection(0);
                                    }else if (c.getString("b2_kesesuaian").equals("2")){
                                        spn_kesesuaian_b2.setSelection(1);
                                    }else if (c.getString("b2_kesesuaian").equals("3")){
                                        spn_kesesuaian_b2.setSelection(2);
                                    }else if (c.getString("b2_kesesuaian").equals("4")){
                                        spn_kesesuaian_b2.setSelection(3);
                                    }else if (c.getString("b2_kesesuaian").equals("5")){
                                        spn_kesesuaian_b2.setSelection(4);
                                    }

                                    if (c.getString("b3_kelengkapan").equals("a")){
                                        spn_kelengkapan_b3.setSelection(0);
                                    }else if (c.getString("b3_kelengkapan").equals("b")){
                                        spn_kelengkapan_b3.setSelection(1);
                                    }else if (c.getString("b3_kelengkapan").equals("c")){
                                        spn_kelengkapan_b3.setSelection(2);
                                    }else if (c.getString("b3_kelengkapan").equals("d")){
                                        spn_kelengkapan_b3.setSelection(3);
                                    }else if (c.getString("b3_kelengkapan").equals("e")){
                                        spn_kelengkapan_b3.setSelection(4);
                                    }

                                    if (c.getString("b3_kesesuaian").equals("1")){
                                        spn_kesesuaian_b3.setSelection(0);
                                    }else if (c.getString("b3_kesesuaian").equals("2")){
                                        spn_kesesuaian_b3.setSelection(1);
                                    }else if (c.getString("b3_kesesuaian").equals("3")){
                                        spn_kesesuaian_b3.setSelection(2);
                                    }else if (c.getString("b3_kesesuaian").equals("4")){
                                        spn_kesesuaian_b3.setSelection(3);
                                    }else if (c.getString("b3_kesesuaian").equals("5")){
                                        spn_kesesuaian_b3.setSelection(4);
                                    }


                                    txt_kondisi_b1.setText(c.getString("b1_deskripsi"));
                                    txt_kondisi_b2.setText(c.getString("b2_deskripsi"));
                                    txt_kondisi_b3.setText(c.getString("b3_deskripsi"));

                                }

                                // product with this pid found
                                // Edit Text

                                // display product data in EditText


                            }else{
                                // product with pid not found
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();


                        }




                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error != null){

                            Toast.makeText(getContext(), "Cek internet Anda!", Toast.LENGTH_LONG).show();
                            dialog.hide();
                        }
                    }
                }

        );

        AppController.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

}
