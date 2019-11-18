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


public class PemberhentianRespon extends Fragment {

    public PemberhentianRespon() {
        // Required empty public constructor
    }

    String kd_responden,jenis_tabel,kesesuaian_c51,kesesuaian_c52,kesesuaian_c53,kesesuaian_c54,kelengkapan_c51,kelengkapan_c52,kelengkapan_c53,kelengkapan_c54,deskripsi_c51,deskripsi_c52,deskripsi_c53,deskripsi_c54;
    Spinner spn_kesesuaian_c51,spn_kesesuaian_c52,spn_kesesuaian_c53,spn_kesesuaian_c54,spn_kelengkapan_c51,spn_kelengkapan_c52,spn_kelengkapan_c53,spn_kelengkapan_c54;
    EditText txt_kondisi_c51,txt_kondisi_c52,txt_kondisi_c53,txt_kondisi_c54;
    Button btn_simpan;

    View root;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_pemberhentian_respon, container, false);
        Intent i= getActivity().getIntent();
        kd_responden = i.getStringExtra("no_responden");
        jenis_tabel = i.getStringExtra("jenis_tabel");

        fetchData();

        spn_kelengkapan_c51 = root.findViewById(R.id.c51_kelengkapan);
        spn_kelengkapan_c52 = root.findViewById(R.id.c52_kelengkapan);
        spn_kelengkapan_c53 = root.findViewById(R.id.c53_kelengkapan);
        spn_kelengkapan_c54 = root.findViewById(R.id.c54_kelengkapan);

        spn_kesesuaian_c51 = root.findViewById(R.id.c51_kesesuaian);
        spn_kesesuaian_c52 = root.findViewById(R.id.c52_kesesuaian);
        spn_kesesuaian_c53 = root.findViewById(R.id.c53_kesesuaian);
        spn_kesesuaian_c54 = root.findViewById(R.id.c54_kesesuaian);

        txt_kondisi_c51 = root.findViewById(R.id.c51_deskripsi);
        txt_kondisi_c52 = root.findViewById(R.id.c52_deskripsi);
        txt_kondisi_c53 = root.findViewById(R.id.c53_deskripsi);
        txt_kondisi_c54 = root.findViewById(R.id.c54_deskripsi);

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
        kesesuaian_c51 = selectionVal[(int) spn_kesesuaian_c51.getSelectedItemId()];
        kesesuaian_c52 = selectionVal[(int) spn_kesesuaian_c52.getSelectedItemId()];
        kesesuaian_c53 = selectionVal[(int) spn_kesesuaian_c53.getSelectedItemId()];
        kesesuaian_c54 = selectionVal[(int) spn_kesesuaian_c54.getSelectedItemId()];

        kelengkapan_c51 = selectionValKelenglapan[(int) spn_kelengkapan_c51.getSelectedItemId()];
        kelengkapan_c52 = selectionValKelenglapan[(int) spn_kelengkapan_c52.getSelectedItemId()];
        kelengkapan_c53 = selectionValKelenglapan[(int) spn_kelengkapan_c53.getSelectedItemId()];
        kelengkapan_c54 = selectionValKelenglapan[(int) spn_kelengkapan_c54.getSelectedItemId()];

        deskripsi_c51 = txt_kondisi_c51.getText().toString();
        deskripsi_c52 = txt_kondisi_c52.getText().toString();
        deskripsi_c53 = txt_kondisi_c53.getText().toString();
        deskripsi_c54 = txt_kondisi_c54.getText().toString();

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("no_responden",kd_responden);
        map.put("tabel",jenis_tabel);
        map.put("c51_kesesuaian",kesesuaian_c51);
        map.put("c52_kesesuaian",kesesuaian_c52);
        map.put("c53_kesesuaian",kesesuaian_c53);
        map.put("c54_kesesuaian",kesesuaian_c54);

        map.put("c51_kelengkapan",kelengkapan_c51);
        map.put("c52_kelengkapan",kelengkapan_c52);
        map.put("c53_kelengkapan",kelengkapan_c53);
        map.put("c54_kelengkapan",kelengkapan_c54);

        map.put("c51_deskripsi",deskripsi_c51);
        map.put("c52_deskripsi",deskripsi_c52);
        map.put("c53_deskripsi",deskripsi_c53);
        map.put("c54_deskripsi",deskripsi_c54);

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

                                    if (c.getString("c51_kelengkapan").equals("a")){
                                        spn_kelengkapan_c51.setSelection(0);
                                    }else if (c.getString("c51_kelengkapan").equals("b")){
                                        spn_kelengkapan_c51.setSelection(1);
                                    }else if (c.getString("c51_kelengkapan").equals("c")){
                                        spn_kelengkapan_c51.setSelection(2);
                                    }else if (c.getString("c51_kelengkapan").equals("d")){
                                        spn_kelengkapan_c51.setSelection(3);
                                    }else if (c.getString("c51_kelengkapan").equals("e")){
                                        spn_kelengkapan_c51.setSelection(4);
                                    }

                                    if (c.getString("c51_kesesuaian").equals("1")){
                                        spn_kesesuaian_c51.setSelection(0);
                                    }else if (c.getString("c51_kesesuaian").equals("2")){
                                        spn_kesesuaian_c51.setSelection(1);
                                    }else if (c.getString("c51_kesesuaian").equals("3")){
                                        spn_kesesuaian_c51.setSelection(2);
                                    }else if (c.getString("c51_kesesuaian").equals("4")){
                                        spn_kesesuaian_c51.setSelection(3);
                                    }else if (c.getString("c51_kesesuaian").equals("5")){
                                        spn_kesesuaian_c51.setSelection(4);
                                    }

                                    if (c.getString("c52_kelengkapan").equals("a")){
                                        spn_kelengkapan_c52.setSelection(0);
                                    }else if (c.getString("c52_kelengkapan").equals("b")){
                                        spn_kelengkapan_c52.setSelection(1);
                                    }else if (c.getString("c52_kelengkapan").equals("c")){
                                        spn_kelengkapan_c52.setSelection(2);
                                    }else if (c.getString("c52_kelengkapan").equals("d")){
                                        spn_kelengkapan_c52.setSelection(3);
                                    }else if (c.getString("c52_kelengkapan").equals("e")){
                                        spn_kelengkapan_c52.setSelection(4);
                                    }

                                    if (c.getString("c52_kesesuaian").equals("1")){
                                        spn_kesesuaian_c52.setSelection(0);
                                    }else if (c.getString("c52_kesesuaian").equals("2")){
                                        spn_kesesuaian_c52.setSelection(1);
                                    }else if (c.getString("c52_kesesuaian").equals("3")){
                                        spn_kesesuaian_c52.setSelection(2);
                                    }else if (c.getString("c52_kesesuaian").equals("4")){
                                        spn_kesesuaian_c52.setSelection(3);
                                    }else if (c.getString("c52_kesesuaian").equals("5")){
                                        spn_kesesuaian_c52.setSelection(4);
                                    }

                                    if (c.getString("c53_kelengkapan").equals("a")){
                                        spn_kelengkapan_c53.setSelection(0);
                                    }else if (c.getString("c53_kelengkapan").equals("b")){
                                        spn_kelengkapan_c53.setSelection(1);
                                    }else if (c.getString("c53_kelengkapan").equals("c")){
                                        spn_kelengkapan_c53.setSelection(2);
                                    }else if (c.getString("c53_kelengkapan").equals("d")){
                                        spn_kelengkapan_c53.setSelection(3);
                                    }else if (c.getString("c53_kelengkapan").equals("e")){
                                        spn_kelengkapan_c53.setSelection(4);
                                    }

                                    if (c.getString("c53_kesesuaian").equals("1")){
                                        spn_kesesuaian_c53.setSelection(0);
                                    }else if (c.getString("c53_kesesuaian").equals("2")){
                                        spn_kesesuaian_c53.setSelection(1);
                                    }else if (c.getString("c53_kesesuaian").equals("3")){
                                        spn_kesesuaian_c53.setSelection(2);
                                    }else if (c.getString("c53_kesesuaian").equals("4")){
                                        spn_kesesuaian_c53.setSelection(3);
                                    }else if (c.getString("c53_kesesuaian").equals("5")){
                                        spn_kesesuaian_c53.setSelection(4);
                                    }

                                    if (c.getString("c54_kelengkapan").equals("a")){
                                        spn_kelengkapan_c54.setSelection(0);
                                    }else if (c.getString("c54_kelengkapan").equals("b")){
                                        spn_kelengkapan_c54.setSelection(1);
                                    }else if (c.getString("c54_kelengkapan").equals("c")){
                                        spn_kelengkapan_c54.setSelection(2);
                                    }else if (c.getString("c54_kelengkapan").equals("d")){
                                        spn_kelengkapan_c54.setSelection(3);
                                    }else if (c.getString("c54_kelengkapan").equals("e")){
                                        spn_kelengkapan_c54.setSelection(4);
                                    }

                                    if (c.getString("c54_kesesuaian").equals("1")){
                                        spn_kesesuaian_c54.setSelection(0);
                                    }else if (c.getString("c54_kesesuaian").equals("2")){
                                        spn_kesesuaian_c54.setSelection(1);
                                    }else if (c.getString("c54_kesesuaian").equals("3")){
                                        spn_kesesuaian_c54.setSelection(2);
                                    }else if (c.getString("c54_kesesuaian").equals("4")){
                                        spn_kesesuaian_c54.setSelection(3);
                                    }else if (c.getString("c54_kesesuaian").equals("5")){
                                        spn_kesesuaian_c54.setSelection(4);
                                    }


                                    txt_kondisi_c51.setText(c.getString("c51_deskripsi"));
                                    txt_kondisi_c52.setText(c.getString("c52_deskripsi"));
                                    txt_kondisi_c53.setText(c.getString("c53_deskripsi"));
                                    txt_kondisi_c54.setText(c.getString("c54_deskripsi"));

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
