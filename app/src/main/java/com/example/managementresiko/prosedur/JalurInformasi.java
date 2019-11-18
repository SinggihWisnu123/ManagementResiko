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

public class JalurInformasi extends Fragment {

    public JalurInformasi() {
        // Required empty public constructor
    }

    View root;
    String kd_responden,jenis_tabel,kesesuaian_c11,kesesuaian_c12,kesesuaian_c13,kesesuaian_c14,kelengkapan_c11,kelengkapan_c12,kelengkapan_c13,kelengkapan_c14,deskripsi_c11,deskripsi_c12,deskripsi_c13,deskripsi_c14;
    Spinner spn_kesesuaian_c11,spn_kesesuaian_c12,spn_kesesuaian_c13,spn_kesesuaian_c14,spn_kelengkapan_c11,spn_kelengkapan_c12,spn_kelengkapan_c13,spn_kelengkapan_c14;
    EditText txt_kondisi_c11,txt_kondisi_c12,txt_kondisi_c13,txt_kondisi_c14;
    Button btn_simpan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_jalur_informasi, container, false);

        Intent i= getActivity().getIntent();
        kd_responden = i.getStringExtra("no_responden");
        jenis_tabel = i.getStringExtra("jenis_tabel");

        fetchData();

        spn_kelengkapan_c11 = root.findViewById(R.id.c11_kelengkapan);
        spn_kelengkapan_c12 = root.findViewById(R.id.c12_kelengkapan);
        spn_kelengkapan_c13 = root.findViewById(R.id.c13_kelengkapan);
        spn_kelengkapan_c14 = root.findViewById(R.id.c14_kelengkapan);

        spn_kesesuaian_c11 = root.findViewById(R.id.c11_kesesuaian);
        spn_kesesuaian_c12 = root.findViewById(R.id.c12_kesesuaian);
        spn_kesesuaian_c13 = root.findViewById(R.id.c13_kesesuaian);
        spn_kesesuaian_c14 = root.findViewById(R.id.c14_kesesuaian);

        txt_kondisi_c11 = root.findViewById(R.id.c11_deskripsi);
        txt_kondisi_c12 = root.findViewById(R.id.c12_deskripsi);
        txt_kondisi_c13 = root.findViewById(R.id.c13_deskripsi);
        txt_kondisi_c14 = root.findViewById(R.id.c14_deskripsi);

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
        kesesuaian_c11 = selectionVal[(int) spn_kesesuaian_c11.getSelectedItemId()];
        kesesuaian_c12 = selectionVal[(int) spn_kesesuaian_c12.getSelectedItemId()];
        kesesuaian_c13 = selectionVal[(int) spn_kesesuaian_c13.getSelectedItemId()];
        kesesuaian_c14 = selectionVal[(int) spn_kesesuaian_c14.getSelectedItemId()];

        kelengkapan_c11 = selectionValKelenglapan[(int) spn_kelengkapan_c11.getSelectedItemId()];
        kelengkapan_c12 = selectionValKelenglapan[(int) spn_kelengkapan_c12.getSelectedItemId()];
        kelengkapan_c13 = selectionValKelenglapan[(int) spn_kelengkapan_c13.getSelectedItemId()];
        kelengkapan_c14 = selectionValKelenglapan[(int) spn_kelengkapan_c14.getSelectedItemId()];

        deskripsi_c11 = txt_kondisi_c11.getText().toString();
        deskripsi_c12 = txt_kondisi_c12.getText().toString();
        deskripsi_c13 = txt_kondisi_c13.getText().toString();
        deskripsi_c14 = txt_kondisi_c14.getText().toString();

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("no_responden",kd_responden);
        map.put("tabel",jenis_tabel);
        map.put("c11_kesesuaian",kesesuaian_c11);
        map.put("c12_kesesuaian",kesesuaian_c12);
        map.put("c13_kesesuaian",kesesuaian_c13);
        map.put("c14_kesesuaian",kesesuaian_c14);

        map.put("c11_kelengkapan",kelengkapan_c11);
        map.put("c12_kelengkapan",kelengkapan_c12);
        map.put("c13_kelengkapan",kelengkapan_c13);
        map.put("c14_kelengkapan",kelengkapan_c14);

        map.put("c11_deskripsi",deskripsi_c11);
        map.put("c12_deskripsi",deskripsi_c12);
        map.put("c13_deskripsi",deskripsi_c13);
        map.put("c14_deskripsi",deskripsi_c14);

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

                                    if (c.getString("c11_kelengkapan").equals("a")){
                                        spn_kelengkapan_c11.setSelection(0);
                                    }else if (c.getString("c11_kelengkapan").equals("b")){
                                        spn_kelengkapan_c11.setSelection(1);
                                    }else if (c.getString("c11_kelengkapan").equals("c")){
                                        spn_kelengkapan_c11.setSelection(2);
                                    }else if (c.getString("c11_kelengkapan").equals("d")){
                                        spn_kelengkapan_c11.setSelection(3);
                                    }else if (c.getString("c11_kelengkapan").equals("e")){
                                        spn_kelengkapan_c11.setSelection(4);
                                    }

                                    if (c.getString("c11_kesesuaian").equals("1")){
                                        spn_kesesuaian_c11.setSelection(0);
                                    }else if (c.getString("c11_kesesuaian").equals("2")){
                                        spn_kesesuaian_c11.setSelection(1);
                                    }else if (c.getString("c11_kesesuaian").equals("3")){
                                        spn_kesesuaian_c11.setSelection(2);
                                    }else if (c.getString("c11_kesesuaian").equals("4")){
                                        spn_kesesuaian_c11.setSelection(3);
                                    }else if (c.getString("c11_kesesuaian").equals("5")){
                                        spn_kesesuaian_c11.setSelection(4);
                                    }

                                    if (c.getString("c12_kelengkapan").equals("a")){
                                        spn_kelengkapan_c12.setSelection(0);
                                    }else if (c.getString("c12_kelengkapan").equals("b")){
                                        spn_kelengkapan_c12.setSelection(1);
                                    }else if (c.getString("c12_kelengkapan").equals("c")){
                                        spn_kelengkapan_c12.setSelection(2);
                                    }else if (c.getString("c12_kelengkapan").equals("d")){
                                        spn_kelengkapan_c12.setSelection(3);
                                    }else if (c.getString("c12_kelengkapan").equals("e")){
                                        spn_kelengkapan_c12.setSelection(4);
                                    }

                                    if (c.getString("c12_kesesuaian").equals("1")){
                                        spn_kesesuaian_c12.setSelection(0);
                                    }else if (c.getString("c12_kesesuaian").equals("2")){
                                        spn_kesesuaian_c12.setSelection(1);
                                    }else if (c.getString("c12_kesesuaian").equals("3")){
                                        spn_kesesuaian_c12.setSelection(2);
                                    }else if (c.getString("c12_kesesuaian").equals("4")){
                                        spn_kesesuaian_c12.setSelection(3);
                                    }else if (c.getString("c12_kesesuaian").equals("5")){
                                        spn_kesesuaian_c12.setSelection(4);
                                    }

                                    if (c.getString("c13_kelengkapan").equals("a")){
                                        spn_kelengkapan_c13.setSelection(0);
                                    }else if (c.getString("c13_kelengkapan").equals("b")){
                                        spn_kelengkapan_c13.setSelection(1);
                                    }else if (c.getString("c13_kelengkapan").equals("c")){
                                        spn_kelengkapan_c13.setSelection(2);
                                    }else if (c.getString("c13_kelengkapan").equals("d")){
                                        spn_kelengkapan_c13.setSelection(3);
                                    }else if (c.getString("c13_kelengkapan").equals("e")){
                                        spn_kelengkapan_c13.setSelection(4);
                                    }

                                    if (c.getString("c13_kesesuaian").equals("1")){
                                        spn_kesesuaian_c13.setSelection(0);
                                    }else if (c.getString("c13_kesesuaian").equals("2")){
                                        spn_kesesuaian_c13.setSelection(1);
                                    }else if (c.getString("c13_kesesuaian").equals("3")){
                                        spn_kesesuaian_c13.setSelection(2);
                                    }else if (c.getString("c13_kesesuaian").equals("4")){
                                        spn_kesesuaian_c13.setSelection(3);
                                    }else if (c.getString("c13_kesesuaian").equals("5")){
                                        spn_kesesuaian_c13.setSelection(4);
                                    }

                                    if (c.getString("c14_kelengkapan").equals("a")){
                                        spn_kelengkapan_c14.setSelection(0);
                                    }else if (c.getString("c14_kelengkapan").equals("b")){
                                        spn_kelengkapan_c14.setSelection(1);
                                    }else if (c.getString("c14_kelengkapan").equals("c")){
                                        spn_kelengkapan_c14.setSelection(2);
                                    }else if (c.getString("c14_kelengkapan").equals("d")){
                                        spn_kelengkapan_c14.setSelection(3);
                                    }else if (c.getString("c14_kelengkapan").equals("e")){
                                        spn_kelengkapan_c14.setSelection(4);
                                    }

                                    if (c.getString("c14_kesesuaian").equals("1")){
                                        spn_kesesuaian_c14.setSelection(0);
                                    }else if (c.getString("c14_kesesuaian").equals("2")){
                                        spn_kesesuaian_c14.setSelection(1);
                                    }else if (c.getString("c14_kesesuaian").equals("3")){
                                        spn_kesesuaian_c14.setSelection(2);
                                    }else if (c.getString("c14_kesesuaian").equals("4")){
                                        spn_kesesuaian_c14.setSelection(3);
                                    }else if (c.getString("c14_kesesuaian").equals("5")){
                                        spn_kesesuaian_c14.setSelection(4);
                                    }


                                    txt_kondisi_c11.setText(c.getString("c11_deskripsi"));
                                    txt_kondisi_c12.setText(c.getString("c12_deskripsi"));
                                    txt_kondisi_c13.setText(c.getString("c13_deskripsi"));
                                    txt_kondisi_c14.setText(c.getString("c14_deskripsi"));

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
