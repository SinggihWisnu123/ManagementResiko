package com.example.managementresiko.ui.gallery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    String kd_responden,jenis_tabel,kesesuaian_a1,kesesuaian_a2,kesesuaian_a3,kesesuaian_a4,kesesuaian_a5,kelengkapan_a1,kelengkapan_a2,kelengkapan_a3,kelengkapan_a4,kelengkapan_a5,deskripsi_a1,deskripsi_a2,deskripsi_a3,deskripsi_a4,deskripsi_a5;
    Spinner spn_kesesuaian_a1,spn_kesesuaian_a2,spn_kesesuaian_a3,spn_kesesuaian_a4,spn_kesesuaian_a5,spn_kelengkapan_a1,spn_kelengkapan_a2,spn_kelengkapan_a3,spn_kelengkapan_a4,spn_kelengkapan_a5;
    EditText txt_kondisi_a1,txt_kondisi_a2,txt_kondisi_a3,txt_kondisi_a4,txt_kondisi_a5;
    Button btn_simpan;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        Intent i= getActivity().getIntent();
        kd_responden = i.getStringExtra("no_responden");
        jenis_tabel = i.getStringExtra("jenis_tabel");

        fetchData();

        spn_kelengkapan_a1 = root.findViewById(R.id.spn_kelengkapan_a1);
        spn_kelengkapan_a2 = root.findViewById(R.id.spn_kelengkapan_a2);
        spn_kelengkapan_a3 = root.findViewById(R.id.spn_kelengkapan_a3);
        spn_kelengkapan_a4 = root.findViewById(R.id.spn_kelengkapan_a4);
        spn_kelengkapan_a5 = root.findViewById(R.id.spn_kelengkapan_a5);
        spn_kesesuaian_a1 = root.findViewById(R.id.spn_kesesuaian_a1);

        spn_kesesuaian_a2 = root.findViewById(R.id.spn_kesesuaian_a2);
        spn_kesesuaian_a3 = root.findViewById(R.id.spn_kesesuaian_a3);
        spn_kesesuaian_a4 = root.findViewById(R.id.spn_kesesuaian_a4);
        spn_kesesuaian_a5 = root.findViewById(R.id.spn_kesesuaian_a5);

        txt_kondisi_a1 = root.findViewById(R.id.txt_deskripsi_a1);
        txt_kondisi_a2 = root.findViewById(R.id.txt_deskripsi_a2);
        txt_kondisi_a3 = root.findViewById(R.id.txt_deskripsi_a3);
        txt_kondisi_a4 = root.findViewById(R.id.txt_deskripsi_a4);
        txt_kondisi_a5 = root.findViewById(R.id.txt_deskripsi_a5);

        btn_simpan = root.findViewById(R.id.btn_simpan);


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

        String sp_selected_val = selectionVal[(int) spn_kesesuaian_a1.getSelectedItemId()];

        Log.d("Value:", String.valueOf(sp_selected_val));


        kesesuaian_a1 = selectionVal[(int) spn_kesesuaian_a1.getSelectedItemId()];
        kesesuaian_a2 = selectionVal[(int) spn_kesesuaian_a2.getSelectedItemId()];
        kesesuaian_a3 = selectionVal[(int) spn_kesesuaian_a3.getSelectedItemId()];
        kesesuaian_a4 = selectionVal[(int) spn_kesesuaian_a4.getSelectedItemId()];
        kesesuaian_a5 = selectionVal[(int) spn_kesesuaian_a5.getSelectedItemId()];

        kelengkapan_a1 = selectionValKelenglapan[(int) spn_kelengkapan_a1.getSelectedItemId()];
        kelengkapan_a2 = selectionValKelenglapan[(int) spn_kelengkapan_a2.getSelectedItemId()];
        kelengkapan_a3 = selectionValKelenglapan[(int) spn_kelengkapan_a3.getSelectedItemId()];
        kelengkapan_a4 = selectionValKelenglapan[(int) spn_kelengkapan_a4.getSelectedItemId()];
        kelengkapan_a5 = selectionValKelenglapan[(int) spn_kelengkapan_a5.getSelectedItemId()];

        deskripsi_a1 = txt_kondisi_a1.getText().toString();
        deskripsi_a2 = txt_kondisi_a2.getText().toString();
        deskripsi_a3 = txt_kondisi_a3.getText().toString();
        deskripsi_a4 = txt_kondisi_a4.getText().toString();
        deskripsi_a5 = txt_kondisi_a5.getText().toString();

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("no_responden",kd_responden);
        map.put("tabel",jenis_tabel);
        map.put("a1_kesesuaian",kesesuaian_a1);
        map.put("a2_kesesuaian",kesesuaian_a2);
        map.put("a3_kesesuaian",kesesuaian_a3);
        map.put("a4_kesesuaian",kesesuaian_a4);
        map.put("a5_kesesuaian",kesesuaian_a5);

        map.put("a1_kelengkapan",kelengkapan_a1);
        map.put("a2_kelengkapan",kelengkapan_a2);
        map.put("a3_kelengkapan",kelengkapan_a3);
        map.put("a4_kelengkapan",kelengkapan_a4);
        map.put("a5_kelengkapan",kelengkapan_a5);

        map.put("a1_deskripsi",deskripsi_a1);
        map.put("a2_deskripsi",deskripsi_a2);
        map.put("a3_deskripsi",deskripsi_a3);
        map.put("a4_deskripsi",deskripsi_a4);
        map.put("a5_deskripsi",deskripsi_a5);

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

                                    if (c.getString("a1_kelengkapan").equals("a")){
                                        spn_kelengkapan_a1.setSelection(0);
                                    }else if (c.getString("a1_kelengkapan").equals("b")){
                                        spn_kelengkapan_a1.setSelection(1);
                                    }else if (c.getString("a1_kelengkapan").equals("c")){
                                        spn_kelengkapan_a1.setSelection(2);
                                    }else if (c.getString("a1_kelengkapan").equals("d")){
                                        spn_kelengkapan_a1.setSelection(3);
                                    }else if (c.getString("a1_kelengkapan").equals("e")){
                                        spn_kelengkapan_a1.setSelection(4);
                                    }

                                    if (c.getString("a1_kesesuaian").equals("1")){
                                        spn_kesesuaian_a1.setSelection(0);
                                    }else if (c.getString("a1_kesesuaian").equals("2")){
                                        spn_kesesuaian_a1.setSelection(1);
                                    }else if (c.getString("a1_kesesuaian").equals("3")){
                                        spn_kesesuaian_a1.setSelection(2);
                                    }else if (c.getString("a1_kesesuaian").equals("4")){
                                        spn_kesesuaian_a1.setSelection(3);
                                    }else if (c.getString("a1_kesesuaian").equals("5")){
                                        spn_kesesuaian_a1.setSelection(4);
                                    }

                                    if (c.getString("a2_kelengkapan").equals("a")){
                                        spn_kelengkapan_a2.setSelection(0);
                                    }else if (c.getString("a2_kelengkapan").equals("b")){
                                        spn_kelengkapan_a2.setSelection(1);
                                    }else if (c.getString("a2_kelengkapan").equals("c")){
                                        spn_kelengkapan_a2.setSelection(2);
                                    }else if (c.getString("a2_kelengkapan").equals("d")){
                                        spn_kelengkapan_a2.setSelection(3);
                                    }else if (c.getString("a2_kelengkapan").equals("e")){
                                        spn_kelengkapan_a2.setSelection(4);
                                    }

                                    if (c.getString("a2_kesesuaian").equals("1")){
                                        spn_kesesuaian_a2.setSelection(0);
                                    }else if (c.getString("a2_kesesuaian").equals("2")){
                                        spn_kesesuaian_a2.setSelection(1);
                                    }else if (c.getString("a2_kesesuaian").equals("3")){
                                        spn_kesesuaian_a2.setSelection(2);
                                    }else if (c.getString("a2_kesesuaian").equals("4")){
                                        spn_kesesuaian_a2.setSelection(3);
                                    }else if (c.getString("a2_kesesuaian").equals("5")){
                                        spn_kesesuaian_a2.setSelection(4);
                                    }

                                    if (c.getString("a3_kelengkapan").equals("a")){
                                        spn_kelengkapan_a3.setSelection(0);
                                    }else if (c.getString("a3_kelengkapan").equals("b")){
                                        spn_kelengkapan_a3.setSelection(1);
                                    }else if (c.getString("a3_kelengkapan").equals("c")){
                                        spn_kelengkapan_a3.setSelection(2);
                                    }else if (c.getString("a3_kelengkapan").equals("d")){
                                        spn_kelengkapan_a3.setSelection(3);
                                    }else if (c.getString("a3_kelengkapan").equals("e")){
                                        spn_kelengkapan_a3.setSelection(4);
                                    }

                                    if (c.getString("a3_kesesuaian").equals("1")){
                                        spn_kesesuaian_a3.setSelection(0);
                                    }else if (c.getString("a3_kesesuaian").equals("2")){
                                        spn_kesesuaian_a3.setSelection(1);
                                    }else if (c.getString("a3_kesesuaian").equals("3")){
                                        spn_kesesuaian_a3.setSelection(2);
                                    }else if (c.getString("a3_kesesuaian").equals("4")){
                                        spn_kesesuaian_a3.setSelection(3);
                                    }else if (c.getString("a3_kesesuaian").equals("5")){
                                        spn_kesesuaian_a3.setSelection(4);
                                    }

                                    if (c.getString("a4_kelengkapan").equals("a")){
                                        spn_kelengkapan_a4.setSelection(0);
                                    }else if (c.getString("a4_kelengkapan").equals("b")){
                                        spn_kelengkapan_a4.setSelection(1);
                                    }else if (c.getString("a4_kelengkapan").equals("c")){
                                        spn_kelengkapan_a4.setSelection(2);
                                    }else if (c.getString("a4_kelengkapan").equals("d")){
                                        spn_kelengkapan_a4.setSelection(3);
                                    }else if (c.getString("a4_kelengkapan").equals("e")){
                                        spn_kelengkapan_a4.setSelection(4);
                                    }

                                    if (c.getString("a4_kesesuaian").equals("1")){
                                        spn_kesesuaian_a4.setSelection(0);
                                    }else if (c.getString("a4_kesesuaian").equals("2")){
                                        spn_kesesuaian_a4.setSelection(1);
                                    }else if (c.getString("a4_kesesuaian").equals("3")){
                                        spn_kesesuaian_a4.setSelection(2);
                                    }else if (c.getString("a4_kesesuaian").equals("4")){
                                        spn_kesesuaian_a4.setSelection(3);
                                    }else if (c.getString("a4_kesesuaian").equals("5")){
                                        spn_kesesuaian_a4.setSelection(4);
                                    }

                                    if (c.getString("a5_kelengkapan").equals("a")){
                                        spn_kelengkapan_a5.setSelection(0);
                                    }else if (c.getString("a5_kelengkapan").equals("b")){
                                        spn_kelengkapan_a5.setSelection(1);
                                    }else if (c.getString("a5_kelengkapan").equals("c")){
                                        spn_kelengkapan_a5.setSelection(2);
                                    }else if (c.getString("a5_kelengkapan").equals("d")){
                                        spn_kelengkapan_a5.setSelection(3);
                                    }else if (c.getString("a_kelengkapan").equals("e")){
                                        spn_kelengkapan_a5.setSelection(4);
                                    }

                                    if (c.getString("a5_kesesuaian").equals("1")){
                                        spn_kesesuaian_a5.setSelection(0);
                                    }else if (c.getString("a5_kesesuaian").equals("2")){
                                        spn_kesesuaian_a5.setSelection(1);
                                    }else if (c.getString("a5_kesesuaian").equals("3")){
                                        spn_kesesuaian_a5.setSelection(2);
                                    }else if (c.getString("a5_kesesuaian").equals("4")){
                                        spn_kesesuaian_a5.setSelection(3);
                                    }else if (c.getString("a5_kesesuaian").equals("5")){
                                        spn_kesesuaian_a5.setSelection(4);
                                    }

                                    txt_kondisi_a1.setText(c.getString("a1_deskripsi"));
                                    txt_kondisi_a2.setText(c.getString("a2_deskripsi"));
                                    txt_kondisi_a3.setText(c.getString("a3_deskripsi"));
                                    txt_kondisi_a4.setText(c.getString("a4_deskripsi"));
                                    txt_kondisi_a5.setText(c.getString("a5_deskripsi"));

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