package com.example.managementresiko.ui.tools;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

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

public class ToolsFragment extends Fragment {

    private ToolsViewModel toolsViewModel;

    String kd_responden,jenis_tabel,kesesuaian_c1,kesesuaian_c2,kesesuaian_c3,kesesuaian_c4,kesesuaian_c5, kelengkapan_c1,kelengkapan_c2,kelengkapan_c3,kelengkapan_c4,kelengkapan_c5,deskripsi_c1,deskripsi_c2,deskripsi_c3,deskripsi_c4,deskripsi_c5;
    Spinner spn_kesesuaian_c1,spn_kesesuaian_c2,spn_kesesuaian_c3,spn_kesesuaian_c4,spn_kesesuaian_c5,spn_kelengkapan_c1,spn_kelengkapan_c2,spn_kelengkapan_c3,spn_kelengkapan_c4,spn_kelengkapan_c5;
    EditText txt_kondisi_c1,txt_kondisi_c2,txt_kondisi_c3,txt_kondisi_c4,txt_kondisi_c5;
    Button btn_simpan;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tools, container, false);

        Intent i= getActivity().getIntent();
        kd_responden = i.getStringExtra("no_responden");
        jenis_tabel = i.getStringExtra("jenis_tabel");

        fetchData();

        spn_kelengkapan_c1 = root.findViewById(R.id.c1_kelengkapan);
        spn_kelengkapan_c2 = root.findViewById(R.id.c2_kelengkapan);
        spn_kelengkapan_c3 = root.findViewById(R.id.c3_kelengkapan);
        spn_kelengkapan_c4 = root.findViewById(R.id.c4_kelengkapan);
        spn_kelengkapan_c5 = root.findViewById(R.id.c5_kelengkapan);

        spn_kesesuaian_c1 = root.findViewById(R.id.c1_kesesuaian);
        spn_kesesuaian_c2 = root.findViewById(R.id.c2_kesesuaian);
        spn_kesesuaian_c3 = root.findViewById(R.id.c3_kesesuaian);
        spn_kesesuaian_c4 = root.findViewById(R.id.c4_kesesuaian);
        spn_kesesuaian_c5 = root.findViewById(R.id.c5_kesesuaian);

        txt_kondisi_c1 = root.findViewById(R.id.c1_deskripsi);
        txt_kondisi_c2 = root.findViewById(R.id.c2_deskripsi);
        txt_kondisi_c3 = root.findViewById(R.id.c3_deskripsi);
        txt_kondisi_c4 = root.findViewById(R.id.c4_deskripsi);
        txt_kondisi_c5 = root.findViewById(R.id.c5_deskripsi);


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
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        RequestParams data = new RequestParams();
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();


        String[] selectionValKelenglapan = {"a","b","c","d","e"};
        String[] selectionVal = {"1","2","3","4","5"};


        String sp_selected_val = selectionVal[(int) spn_kesesuaian_c1.getSelectedItemId()];

        Log.d("Value:", String.valueOf(sp_selected_val));


        kesesuaian_c1 = selectionVal[(int) spn_kesesuaian_c1.getSelectedItemId()];
        kesesuaian_c2 = selectionVal[(int) spn_kesesuaian_c2.getSelectedItemId()];
        kesesuaian_c3 = selectionVal[(int) spn_kesesuaian_c3.getSelectedItemId()];
        kesesuaian_c4 = selectionVal[(int) spn_kesesuaian_c4.getSelectedItemId()];
        kesesuaian_c5 = selectionVal[(int) spn_kesesuaian_c5.getSelectedItemId()];

        kelengkapan_c1 = selectionValKelenglapan[(int) spn_kelengkapan_c1.getSelectedItemId()];
        kelengkapan_c2 = selectionValKelenglapan[(int) spn_kelengkapan_c2.getSelectedItemId()];
        kelengkapan_c3 = selectionValKelenglapan[(int) spn_kelengkapan_c3.getSelectedItemId()];
        kelengkapan_c4 = selectionValKelenglapan[(int) spn_kelengkapan_c4.getSelectedItemId()];
        kelengkapan_c5 = selectionValKelenglapan[(int) spn_kelengkapan_c5.getSelectedItemId()];

        deskripsi_c1 = txt_kondisi_c1.getText().toString();
        deskripsi_c2 = txt_kondisi_c2.getText().toString();
        deskripsi_c3 = txt_kondisi_c3.getText().toString();
        deskripsi_c4 = txt_kondisi_c4.getText().toString();
        deskripsi_c5 = txt_kondisi_c5.getText().toString();

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("no_responden",kd_responden);
        map.put("tabel",jenis_tabel);
        map.put("c1_kesesuaian",kesesuaian_c1);
        map.put("c2_kesesuaian",kesesuaian_c2);
        map.put("c3_kesesuaian",kesesuaian_c3);
        map.put("c4_kesesuaian",kesesuaian_c4);
        map.put("c5_kesesuaian",kesesuaian_c5);

        map.put("c1_kelengkapan",kelengkapan_c1);
        map.put("c2_kelengkapan",kelengkapan_c2);
        map.put("c3_kelengkapan",kelengkapan_c3);
        map.put("c4_kelengkapan",kelengkapan_c4);
        map.put("c5_kelengkapan",kelengkapan_c5);

        map.put("c1_deskripsi",deskripsi_c1);
        map.put("c2_deskripsi",deskripsi_c2);
        map.put("c3_deskripsi",deskripsi_c3);
        map.put("c4_deskripsi",deskripsi_c4);
        map.put("c5_deskripsi",deskripsi_c5);

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
        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppConfig.URL_DATA_RISK+kd_responden,
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

                                    if (c.getString("c1_kelengkapan").equals("a")){
                                        spn_kelengkapan_c1.setSelection(0);
                                    }else if (c.getString("c1_kelengkapan").equals("b")){
                                        spn_kelengkapan_c1.setSelection(1);
                                    }else if (c.getString("c1_kelengkapan").equals("c")){
                                        spn_kelengkapan_c1.setSelection(2);
                                    }else if (c.getString("c1_kelengkapan").equals("d")){
                                        spn_kelengkapan_c1.setSelection(3);
                                    }else if (c.getString("c1_kelengkapan").equals("e")){
                                        spn_kelengkapan_c1.setSelection(4);
                                    }

                                    if (c.getString("c1_kesesuaian").equals("1")){
                                        spn_kesesuaian_c1.setSelection(0);
                                    }else if (c.getString("c1_kesesuaian").equals("2")){
                                        spn_kesesuaian_c1.setSelection(1);
                                    }else if (c.getString("c1_kesesuaian").equals("3")){
                                        spn_kesesuaian_c1.setSelection(2);
                                    }else if (c.getString("c1_kesesuaian").equals("4")){
                                        spn_kesesuaian_c1.setSelection(3);
                                    }else if (c.getString("c1_kesesuaian").equals("5")){
                                        spn_kesesuaian_c1.setSelection(4);
                                    }

                                    if (c.getString("c2_kelengkapan").equals("a")){
                                        spn_kelengkapan_c2.setSelection(0);
                                    }else if (c.getString("c2_kelengkapan").equals("b")){
                                        spn_kelengkapan_c2.setSelection(1);
                                    }else if (c.getString("c2_kelengkapan").equals("c")){
                                        spn_kelengkapan_c2.setSelection(2);
                                    }else if (c.getString("c2_kelengkapan").equals("d")){
                                        spn_kelengkapan_c2.setSelection(3);
                                    }else if (c.getString("c2_kelengkapan").equals("e")){
                                        spn_kelengkapan_c2.setSelection(4);
                                    }

                                    if (c.getString("c2_kesesuaian").equals("1")){
                                        spn_kesesuaian_c2.setSelection(0);
                                    }else if (c.getString("c2_kesesuaian").equals("2")){
                                        spn_kesesuaian_c2.setSelection(1);
                                    }else if (c.getString("c2_kesesuaian").equals("3")){
                                        spn_kesesuaian_c2.setSelection(2);
                                    }else if (c.getString("c2_kesesuaian").equals("4")){
                                        spn_kesesuaian_c2.setSelection(3);
                                    }else if (c.getString("c2_kesesuaian").equals("5")){
                                        spn_kesesuaian_c2.setSelection(4);
                                    }

                                    if (c.getString("c3_kelengkapan").equals("a")){
                                        spn_kelengkapan_c3.setSelection(0);
                                    }else if (c.getString("c3_kelengkapan").equals("b")){
                                        spn_kelengkapan_c3.setSelection(1);
                                    }else if (c.getString("c3_kelengkapan").equals("c")){
                                        spn_kelengkapan_c3.setSelection(2);
                                    }else if (c.getString("c3_kelengkapan").equals("d")){
                                        spn_kelengkapan_c3.setSelection(3);
                                    }else if (c.getString("c3_kelengkapan").equals("e")){
                                        spn_kelengkapan_c3.setSelection(4);
                                    }

                                    if (c.getString("c3_kesesuaian").equals("1")){
                                        spn_kesesuaian_c3.setSelection(0);
                                    }else if (c.getString("c3_kesesuaian").equals("2")){
                                        spn_kesesuaian_c3.setSelection(1);
                                    }else if (c.getString("c3_kesesuaian").equals("3")){
                                        spn_kesesuaian_c3.setSelection(2);
                                    }else if (c.getString("c3_kesesuaian").equals("4")){
                                        spn_kesesuaian_c3.setSelection(3);
                                    }else if (c.getString("c3_kesesuaian").equals("5")){
                                        spn_kesesuaian_c3.setSelection(4);
                                    }

                                    if (c.getString("c4_kelengkapan").equals("a")){
                                        spn_kelengkapan_c4.setSelection(0);
                                    }else if (c.getString("c4_kelengkapan").equals("b")){
                                        spn_kelengkapan_c4.setSelection(1);
                                    }else if (c.getString("c4_kelengkapan").equals("c")){
                                        spn_kelengkapan_c4.setSelection(2);
                                    }else if (c.getString("c4_kelengkapan").equals("d")){
                                        spn_kelengkapan_c4.setSelection(3);
                                    }else if (c.getString("c4_kelengkapan").equals("e")){
                                        spn_kelengkapan_c4.setSelection(4);
                                    }

                                    if (c.getString("c4_kesesuaian").equals("1")){
                                        spn_kesesuaian_c4.setSelection(0);
                                    }else if (c.getString("c4_kesesuaian").equals("2")){
                                        spn_kesesuaian_c4.setSelection(1);
                                    }else if (c.getString("c4_kesesuaian").equals("3")){
                                        spn_kesesuaian_c4.setSelection(2);
                                    }else if (c.getString("c4_kesesuaian").equals("4")){
                                        spn_kesesuaian_c4.setSelection(3);
                                    }else if (c.getString("c4_kesesuaian").equals("5")){
                                        spn_kesesuaian_c4.setSelection(4);
                                    }

                                    if (c.getString("c5_kelengkapan").equals("a")){
                                        spn_kelengkapan_c5.setSelection(0);
                                    }else if (c.getString("c5_kelengkapan").equals("b")){
                                        spn_kelengkapan_c5.setSelection(1);
                                    }else if (c.getString("c5_kelengkapan").equals("c")){
                                        spn_kelengkapan_c5.setSelection(2);
                                    }else if (c.getString("c5_kelengkapan").equals("d")){
                                        spn_kelengkapan_c5.setSelection(3);
                                    }else if (c.getString("c_kelengkapan").equals("e")){
                                        spn_kelengkapan_c5.setSelection(4);
                                    }

                                    if (c.getString("c5_kesesuaian").equals("1")){
                                        spn_kesesuaian_c5.setSelection(0);
                                    }else if (c.getString("c5_kesesuaian").equals("2")){
                                        spn_kesesuaian_c5.setSelection(1);
                                    }else if (c.getString("c5_kesesuaian").equals("3")){
                                        spn_kesesuaian_c5.setSelection(2);
                                    }else if (c.getString("c5_kesesuaian").equals("4")){
                                        spn_kesesuaian_c5.setSelection(3);
                                    }else if (c.getString("c5_kesesuaian").equals("5")){
                                        spn_kesesuaian_c5.setSelection(4);
                                    }

                                    txt_kondisi_c1.setText(c.getString("c1_deskripsi"));
                                    txt_kondisi_c2.setText(c.getString("c2_deskripsi"));
                                    txt_kondisi_c3.setText(c.getString("c3_deskripsi"));
                                    txt_kondisi_c4.setText(c.getString("c4_deskripsi"));
                                    txt_kondisi_c5.setText(c.getString("c5_deskripsi"));

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