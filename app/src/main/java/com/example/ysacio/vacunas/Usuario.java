package com.example.ysacio.vacunas;

/**
 * Created by maxvillamayor on 13/3/17.
 */

public class Usuario {

    private int id;
    private String name;
    private String mail;
    private String name_baby;
    private String date_baby;
    private String sex;




    public Usuario() {}

    public Usuario(String name ,String mail, String name_baby,String date_baby,String sex) {
        this.name = name;
        this.mail = mail;
        this.name_baby = name_baby;
        this.date_baby = date_baby;
        this.sex = sex;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName_baby() {
        return name_baby;
    }

    public void setName_baby(String name_baby) {
        this.name_baby = name_baby;
    }

    public String getDate_baby() {
        return date_baby;
    }

    public void setDate_baby(String date_baby) {
        this.date_baby = date_baby;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }


    @Override
    public String toString() {
//        return "Usuario{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", mail='" + mail + '\'' +
//                ", name_baby='" + name_baby + '\'' +
//                ", date_baby='" + date_baby + '\'' +
//                ", sex='" + sex + '\'' +
//                '}';

                return
                " Nombre:" + name_baby +"\n"+
                " Fecha Nacimiento:" + date_baby +"\n"+

                " Sexo:" + sex  ;
    }


}