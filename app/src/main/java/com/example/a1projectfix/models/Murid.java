package com.example.a1projectfix.models;

public class Murid {
    private String nama;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getFoto1() {
        return foto1;
    }

    public void setFoto1(String foto1) {
        this.foto1 = foto1;
    }

    public String getFoto2() {
        return foto2;
    }

    public void setFoto2(String foto2) {
        this.foto2 = foto2;
    }

    public String getTempat() {
        return tempat;
    }

    public void setTempat(String tempat) {
        this.tempat = tempat;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getBiaya() {
        return biaya;
    }

    public void setBiaya(String biaya) {
        this.biaya = biaya;
    }

    private String kelas;
    private String alamat;
    private String foto1;
    private String foto2;
    private String tempat;
    private String tanggal;
    private String key;
    private String biaya;

    public Murid() {

    }

    public Murid(String nama, String kelas, String alamat, String foto1, String foto2, String tempat, String tanggal, String key, String biaya) {
        this.nama = nama;
        this.kelas = kelas;
        this.alamat = alamat;
        this.foto1 = foto1;
        this.foto2 = foto2;
        this.tempat = tempat;
        this.tanggal = tanggal;
        this.key = key;
        this.biaya = biaya;
    }
}
