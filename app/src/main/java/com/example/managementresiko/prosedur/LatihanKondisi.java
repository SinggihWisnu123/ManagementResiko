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
import com.example.managementresiko.DashboardActivity;
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

public class LatihanKondisi extends Fragment {

    public LatihanKondisi() {
        // Required empty public constructor
    }
    String kd_responden,jenis_tabel,kesesuaian_e1,kesesuaian_e2,kesesuaian_e3,kesesuaian_e4,kesesuaian_e5,kesesuaian_e6,kesesuaian_e7,
            kelengkapan_e1,kelengkapan_e2,kelengkapan_e3,kelengkapan_e4,kelengkapan_e5,kelengkapan_e6,kelengkapan_e7,
            deskripsi_e1,deskripsi_e2,deskripsi_e3,deskripsi_e4,deskripsi_e5,deskripsi_e6,deskripsi_e7;
    Spinner spn_kesesuaian_e1,spn_kesesuaian_e2,spn_kesesuaian_e3,spn_kesesuaian_e4,spn_kesesuaian_e5,spn_kesesuaian_e6,spn_kesesuaian_e7,
            spn_kelengkapan_e1,spn_kelengkapan_e2,spn_kelengkapan_e3,spn_kelengkapan_e4,spn_kelengkapan_e5,spn_kelengkapan_e6,spn_kelengkapan_e7;
    EditText txt_kondisi_e1,txt_kondisi_e2,txt_kondisi_e3,txt_kondisi_e4,txt_kondisi_e5,txt_kondisi_e6,txt_kondisi_e7;
    Button btn_simpan,btn_selesai;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_latihan_kondisi, container, false);
        Intent i= getActivity().getIntent();
        kd_responden = i.getStringExtra("no_responden");
        jenis_tabel = i.getStringExtra("jenis_tabel");

        fetchData();

        spn_kelengkapan_e1 = root.findViewById(R.id.e1_kelengkapan);
        spn_kelengkapan_e2 = root.findViewById(R.id.e2_kelengkapan);
        spn_kelengkapan_e3 = root.findViewById(R.id.e3_kelengkapan);
        spn_kelengkapan_e4 = root.findViewById(R.id.e4_kelengkapan);
        spn_kelengkapan_e5 = root.findViewById(R.id.e5_kelengkapan);
        spn_kelengkapan_e6 = root.findViewById(R.id.e6_kelengkapan);
        spn_kelengkapan_e7 = root.findViewById(R.id.e7_kelengkapan);

        spn_kesesuaian_e1 = root.findViewById(R.id.e1_kesesuaian);
        spn_kesesuaian_e2 = root.findViewById(R.id.e2_kesesuaian);
        spn_kesesuaian_e3 = root.findViewById(R.id.e3_kesesuaian);
        spn_kesesuaian_e4 = root.findViewById(R.id.e4_kesesuaian);
        spn_kesesuaian_e5 = root.findViewById(R.id.e5_kesesuaian);
        spn_kesesuaian_e6 = root.findViewById(R.id.e6_kesesuaian);
        spn_kesesuaian_e7 = root.findViewById(R.id.e7_kesesuaian);

        txt_kondisi_e1 = root.findViewById(R.id.e1_deskripsi);
        txt_kondisi_e2 = root.findViewById(R.id.e2_deskripsi);
        txt_kondisi_e3 = root.findViewById(R.id.e3_deskripsi);
        txt_kondisi_e4 = root.findViewById(R.id.e4_deskripsi);
        txt_kondisi_e5 = root.findViewById(R.id.e5_deskripsi);
        txt_kondisi_e6 = root.findViewById(R.id.e6_deskripsi);
        txt_kondisi_e7 = root.findViewById(R.id.e7_deskripsi);

        btn_simpan = root.findViewById(R.id.btn_simpan);
        btn_selesai = root.findViewById(R.id.btn_selesai);

        btn_selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createData();
                Intent intent =  new Intent(getActivity(), DashboardActivity.class);
                startActivity(intent);
            }
        });
        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createData();
            }
        });
        return root;
    }

    private void createData() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        RequestParams data = new RequestParams();
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();


        String[] selectionValKelenglapan = {"a","b","c","d","e"};
        String[] selectionVal = {"1","2","3","4","5"};


        kesesuaian_e1 = selectionVal[(int) spn_kesesuaian_e1.getSelectedItemId()];
        kesesuaian_e2 = selectionVal[(int) spn_kesesuaian_e2.getSelectedItemId()];
        kesesuaian_e3 = selectionVal[(int) spn_kesesuaian_e3.getSelectedItemId()];
        kesesuaian_e4 = selectionVal[(int) spn_kesesuaian_e4.getSelectedItemId()];
        kesesuaian_e5 = selectionVal[(int) spn_kesesuaian_e5.getSelectedItemId()];
        kesesuaian_e6 = selectionVal[(int) spn_kesesuaian_e6.getSelectedItemId()];
        kesesuaian_e7 = selectionVal[(int) spn_kesesuaian_e7.getSelectedItemId()];

        kelengkapan_e1 = selectionValKelenglapan[(int) spn_kelengkapan_e1.getSelectedItemId()];
        kelengkapan_e2 = selectionValKelenglapan[(int) spn_kelengkapan_e2.getSelectedItemId()];
        kelengkapan_e3 = selectionValKelenglapan[(int) spn_kelengkapan_e3.getSelectedItemId()];
        kelengkapan_e4 = selectionValKelenglapan[(int) spn_kelengkapan_e4.getSelectedItemId()];
        kelengkapan_e5 = selectionValKelenglapan[(int) spn_kelengkapan_e5.getSelectedItemId()];
        kelengkapan_e6 = selectionValKelenglapan[(int) spn_kelengkapan_e6.getSelectedItemId()];
        kelengkapan_e7 = selectionValKelenglapan[(int) spn_kelengkapan_e7.getSelectedItemId()];

        deskripsi_e1 = txt_kondisi_e1.getText().toString();
        deskripsi_e2 = txt_kondisi_e2.getText().toString();
        deskripsi_e3 = txt_kondisi_e3.getText().toString();
        deskripsi_e4 = txt_kondisi_e4.getText().toString();
        deskripsi_e5 = txt_kondisi_e5.getText().toString();
        deskripsi_e6 = txt_kondisi_e6.getText().toString();
        deskripsi_e7 = txt_kondisi_e7.getText().toString();

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("no_responden",kd_responden);
        map.put("tabel",jenis_tabel);
        map.put("e1_kesesuaian",kesesuaian_e1);
        map.put("e2_kesesuaian",kesesuaian_e2);
        map.put("e3_kesesuaian",kesesuaian_e3);
        map.put("e4_kesesuaian",kesesuaian_e4);
        map.put("e5_kesesuaian",kesesuaian_e5);
        map.put("e6_kesesuaian",kesesuaian_e6);
        map.put("e7_kesesuaian",kesesuaian_e7);

        map.put("e1_kelengkapan",kelengkapan_e1);
        map.put("e2_kelengkapan",kelengkapan_e2);
        map.put("e3_kelengkapan",kelengkapan_e3);
        map.put("e4_kelengkapan",kelengkapan_e4);
        map.put("e5_kelengkapan",kelengkapan_e5);
        map.put("e6_kelengkapan",kelengkapan_e6);
        map.put("e7_kelengkapan",kelengkapan_e7);

        map.put("e1_deskripsi",deskripsi_e1);
        map.put("e2_deskripsi",deskripsi_e2);
        map.put("e3_deskripsi",deskripsi_e3);
        map.put("e4_deskripsi",deskripsi_e4);
        map.put("e5_deskripsi",deskripsi_e5);
        map.put("e6_deskripsi",deskripsi_e6);
        map.put("e7_deskripsi",deskripsi_e7);

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

                                    if (c.getString("e1_kelengkapan").equals("a")){
                                        spn_kelengkapan_e1.setSelection(0);
                                    }else if (c.getString("e1_kelengkapan").equals("b")){
                                        spn_kelengkapan_e1.setSelection(1);
                                    }else if (c.getString("e1_kelengkapan").equals("c")){
                                        spn_kelengkapan_e1.setSelection(2);
                                    }else if (c.getString("e1_kelengkapan").equals("d")){
                                        spn_kelengkapan_e1.setSelection(3);
                                    }else if (c.getString("e1_kelengkapan").equals("e")){
                                        spn_kelengkapan_e1.setSelection(4);
                                    }

                                    if (c.getString("e1_kesesuaian").equals("1")){
                                        spn_kesesuaian_e1.setSelection(0);
                                    }else if (c.getString("e1_kesesuaian").equals("2")){
                                        spn_kesesuaian_e1.setSelection(1);
                                    }else if (c.getString("e1_kesesuaian").equals("3")){
                                        spn_kesesuaian_e1.setSelection(2);
                                    }else if (c.getString("e1_kesesuaian").equals("4")){
                                        spn_kesesuaian_e1.setSelection(3);
                                    }else if (c.getString("e1_kesesuaian").equals("5")){
                                        spn_kesesuaian_e1.setSelection(4);
                                    }

                                    if (c.getString("e2_kelengkapan").equals("a")){
                                        spn_kelengkapan_e2.setSelection(0);
                                    }else if (c.getString("e2_kelengkapan").equals("b")){
                                        spn_kelengkapan_e2.setSelection(1);
                                    }else if (c.getString("e2_kelengkapan").equals("c")){
                                        spn_kelengkapan_e2.setSelection(2);
                                    }else if (c.getString("e2_kelengkapan").equals("d")){
                                        spn_kelengkapan_e2.setSelection(3);
                                    }else if (c.getString("e2_kelengkapan").equals("e")){
                                        spn_kelengkapan_e2.setSelection(4);
                                    }

                                    if (c.getString("e2_kesesuaian").equals("1")){
                                        spn_kesesuaian_e2.setSelection(0);
                                    }else if (c.getString("e2_kesesuaian").equals("2")){
                                        spn_kesesuaian_e2.setSelection(1);
                                    }else if (c.getString("e2_kesesuaian").equals("3")){
                                        spn_kesesuaian_e2.setSelection(2);
                                    }else if (c.getString("e2_kesesuaian").equals("4")){
                                        spn_kesesuaian_e2.setSelection(3);
                                    }else if (c.getString("e2_kesesuaian").equals("5")){
                                        spn_kesesuaian_e2.setSelection(4);
                                    }

                                    if (c.getString("e3_kelengkapan").equals("a")){
                                        spn_kelengkapan_e3.setSelection(0);
                                    }else if (c.getString("e3_kelengkapan").equals("b")){
                                        spn_kelengkapan_e3.setSelection(1);
                                    }else if (c.getString("e3_kelengkapan").equals("c")){
                                        spn_kelengkapan_e3.setSelection(2);
                                    }else if (c.getString("e3_kelengkapan").equals("d")){
                                        spn_kelengkapan_e3.setSelection(3);
                                    }else if (c.getString("e3_kelengkapan").equals("e")){
                                        spn_kelengkapan_e3.setSelection(4);
                                    }

                                    if (c.getString("e3_kesesuaian").equals("1")){
                                        spn_kesesuaian_e3.setSelection(0);
                                    }else if (c.getString("e3_kesesuaian").equals("2")){
                                        spn_kesesuaian_e3.setSelection(1);
                                    }else if (c.getString("e3_kesesuaian").equals("3")){
                                        spn_kesesuaian_e3.setSelection(2);
                                    }else if (c.getString("e3_kesesuaian").equals("4")){
                                        spn_kesesuaian_e3.setSelection(3);
                                    }else if (c.getString("e3_kesesuaian").equals("5")){
                                        spn_kesesuaian_e3.setSelection(4);
                                    }

                                    if (c.getString("e4_kelengkapan").equals("a")){
                                        spn_kelengkapan_e4.setSelection(0);
                                    }else if (c.getString("e4_kelengkapan").equals("b")){
                                        spn_kelengkapan_e4.setSelection(1);
                                    }else if (c.getString("e4_kelengkapan").equals("c")){
                                        spn_kelengkapan_e4.setSelection(2);
                                    }else if (c.getString("e4_kelengkapan").equals("d")){
                                        spn_kelengkapan_e4.setSelection(3);
                                    }else if (c.getString("e4_kelengkapan").equals("e")){
                                        spn_kelengkapan_e4.setSelection(4);
                                    }

                                    if (c.getString("e4_kesesuaian").equals("1")){
                                        spn_kesesuaian_e4.setSelection(0);
                                    }else if (c.getString("e4_kesesuaian").equals("2")){
                                        spn_kesesuaian_e4.setSelection(1);
                                    }else if (c.getString("e4_kesesuaian").equals("3")){
                                        spn_kesesuaian_e4.setSelection(2);
                                    }else if (c.getString("e4_kesesuaian").equals("4")){
                                        spn_kesesuaian_e4.setSelection(3);
                                    }else if (c.getString("e4_kesesuaian").equals("5")){
                                        spn_kesesuaian_e4.setSelection(4);
                                    }

                                    if (c.getString("e5_kelengkapan").equals("a")){
                                        spn_kelengkapan_e5.setSelection(0);
                                    }else if (c.getString("e5_kelengkapan").equals("b")){
                                        spn_kelengkapan_e5.setSelection(1);
                                    }else if (c.getString("e5_kelengkapan").equals("c")){
                                        spn_kelengkapan_e5.setSelection(2);
                                    }else if (c.getString("e5_kelengkapan").equals("d")){
                                        spn_kelengkapan_e5.setSelection(3);
                                    }else if (c.getString("e5_kelengkapan").equals("e")){
                                        spn_kelengkapan_e5.setSelection(4);
                                    }

                                    if (c.getString("e5_kesesuaian").equals("1")){
                                        spn_kesesuaian_e5.setSelection(0);
                                    }else if (c.getString("e5_kesesuaian").equals("2")){
                                        spn_kesesuaian_e5.setSelection(1);
                                    }else if (c.getString("e5_kesesuaian").equals("3")){
                                        spn_kesesuaian_e5.setSelection(2);
                                    }else if (c.getString("e5_kesesuaian").equals("4")){
                                        spn_kesesuaian_e5.setSelection(3);
                                    }else if (c.getString("e5_kesesuaian").equals("5")){
                                        spn_kesesuaian_e5.setSelection(4);
                                    }
                                    if (c.getString("e6_kelengkapan").equals("a")){
                                        spn_kelengkapan_e6.setSelection(0);
                                    }else if (c.getString("e6_kelengkapan").equals("b")){
                                        spn_kelengkapan_e6.setSelection(1);
                                    }else if (c.getString("e6_kelengkapan").equals("c")){
                                        spn_kelengkapan_e6.setSelection(2);
                                    }else if (c.getString("e6_kelengkapan").equals("d")){
                                        spn_kelengkapan_e6.setSelection(3);
                                    }else if (c.getString("e6_kelengkapan").equals("e")){
                                        spn_kelengkapan_e6.setSelection(4);
                                    }

                                    if (c.getString("e6_kesesuaian").equals("1")){
                                        spn_kesesuaian_e6.setSelection(0);
                                    }else if (c.getString("e6_kesesuaian").equals("2")){
                                        spn_kesesuaian_e6.setSelection(1);
                                    }else if (c.getString("e6_kesesuaian").equals("3")){
                                        spn_kesesuaian_e6.setSelection(2);
                                    }else if (c.getString("e6_kesesuaian").equals("4")){
                                        spn_kesesuaian_e6.setSelection(3);
                                    }else if (c.getString("e6_kesesuaian").equals("5")){
                                        spn_kesesuaian_e6.setSelection(4);
                                    }
                                    if (c.getString("e7_kelengkapan").equals("a")){
                                        spn_kelengkapan_e7.setSelection(0);
                                    }else if (c.getString("e7_kelengkapan").equals("b")){
                                        spn_kelengkapan_e7.setSelection(1);
                                    }else if (c.getString("e7_kelengkapan").equals("c")){
                                        spn_kelengkapan_e7.setSelection(2);
                                    }else if (c.getString("e7_kelengkapan").equals("d")){
                                        spn_kelengkapan_e7.setSelection(3);
                                    }else if (c.getString("e7_kelengkapan").equals("e")){
                                        spn_kelengkapan_e7.setSelection(4);
                                    }

                                    if (c.getString("e7_kesesuaian").equals("1")){
                                        spn_kesesuaian_e7.setSelection(0);
                                    }else if (c.getString("e7_kesesuaian").equals("2")){
                                        spn_kesesuaian_e7.setSelection(1);
                                    }else if (c.getString("e7_kesesuaian").equals("3")){
                                        spn_kesesuaian_e7.setSelection(2);
                                    }else if (c.getString("e7_kesesuaian").equals("4")){
                                        spn_kesesuaian_e7.setSelection(3);
                                    }else if (c.getString("e7_kesesuaian").equals("5")){
                                        spn_kesesuaian_e7.setSelection(4);
                                    }


                                    txt_kondisi_e1.setText(c.getString("e1_deskripsi"));
                                    txt_kondisi_e2.setText(c.getString("e2_deskripsi"));
                                    txt_kondisi_e3.setText(c.getString("e3_deskripsi"));
                                    txt_kondisi_e4.setText(c.getString("e4_deskripsi"));
                                    txt_kondisi_e5.setText(c.getString("e5_deskripsi"));
                                    txt_kondisi_e6.setText(c.getString("e6_deskripsi"));
                                    txt_kondisi_e7.setText(c.getString("e7_deskripsi"));

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
