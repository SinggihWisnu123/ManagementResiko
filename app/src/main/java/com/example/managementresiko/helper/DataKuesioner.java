package com.example.managementresiko.helper;

public class DataKuesioner {
    String nama_perusahaan;
    String vprosedur;
        String vrisk;
    String tgl_entri;
    String no_responden;

    public DataKuesioner(){}

    public String getNo_responden() {
        return no_responden;
    }

    public String getTanggal() {
        return tgl_entri;
    }

    public String getNama() {
        return nama_perusahaan;
    }

    public String getAhp_resiko() {
        return vrisk;
    }

    public String getAhp_prosedur() {
        return vprosedur;
    }



}
