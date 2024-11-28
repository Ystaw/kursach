package messages;


import tables.*;

import java.io.Serializable;
import java.util.List;

public class Message implements Serializable{
    private MessageType messageType;
    private Account accounts;
    private Clients clients;
    private Products products;
    private QualityAssessments qualityAssessments;


    private Outlets outlets;
    private Rent rent;


    private String str;

    private List<Outlets> listOutlets;
    private List<Rent> listRents;


    private List<Clients> listClients;
    private List<Account> listAccounts;
    private List<Products> listProducts;
    private List<QualityAssessments> listQualityAssessmentss;





    private final static long serialVersionUID=1L;  //Универсальный идентификатор версии для Serializable класса,
    // Десериализация использует этот номер, чтобы гарантировать, что загруженный класс точно соответствует сериализованному объекту

    public Account getAccount() {
        return accounts;
    }
    public Clients getClients() {
        return clients;
    }
    public Products getProducts() {
        return products;
    }
    public QualityAssessments getQualityAssessments() {
        return qualityAssessments;
    }




    public Outlets getOutlets() {
        return outlets;
    }
    public Rent getRent() {
        return rent;
    }


    public void setAccounts(Account accounts) {
        this.accounts = accounts;
    }
    public void setClients(Clients clients) {
        this.clients = clients;
    }
    public void setProducts(Products products) {
        this.products = products;
    }
    public void setQualityAssessments(QualityAssessments qualityAssessments) {
        this.qualityAssessments = qualityAssessments;
    }



    public void setOutlets(Outlets outlets) {
        this.outlets = outlets;
    }
    public void setRent(Rent rent) {
        this.rent = rent;
    }



    public MessageType getMessageType() {
        return messageType;
    }
    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public Message() {}
    public Message(Account account)  {
        this.accounts=account;
    }
    public Message(Clients client) {this.clients=client;}
    public Message(Products products) {this.products=products;}
    public Message(List <Products> list) {listProducts=list;}
    public Message(QualityAssessments qualityAssessments) {this.qualityAssessments =qualityAssessments;}



    public Message(Rent rent) {this.rent=rent;}


    public Message(Outlets outlets) {this.outlets=outlets;}



    public void setFreeOutlets (List<Outlets> list){
        listOutlets=list;
    }

    public void setOutlets (List<Outlets> list){
        listOutlets=list;
    }

    public void setRents (List<Rent> list){
        listRents=list;
    }



    public void setFreeProducts (List<Products> list){
        listProducts=list;
    }

    public void setProducts (List<Products> list){
        listProducts=list;
    }

    public void setClients (List<Clients> list){
        listClients=list;
    }
    public void setAccounts(List<Account> accounts) {
        listAccounts= accounts;
    }
    public void setQualityAssessments (List<QualityAssessments> list){
        listQualityAssessmentss=list;
    }


    public List<Outlets> getListOutlets() {
        return listOutlets;
    }
    public List<Rent> getListRents() {
        return listRents;
    }


    public List<Products> getListProducts() {
        return listProducts;
    }
    public List<Clients> getListClients(){ return listClients;}
    public List<Account> getListAccounts(){ return listAccounts;}
    public List<QualityAssessments> getListQualityAssessmentss() {
        return listQualityAssessmentss;
    }



}
