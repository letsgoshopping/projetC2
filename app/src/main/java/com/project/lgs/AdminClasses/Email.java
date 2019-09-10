package com.project.lgs.AdminClasses;

import com.project.lgs.Database.SupplierMgr;
import com.project.lgs.Database.UsersMgr;
import com.project.lgs.OrdersClasses.Order;
import com.project.lgs.ProductClasses.Product;
import com.project.lgs.SupplierClasses.Supplier;
import com.project.lgs.UsersClasses.User;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.HashMap;

public class Email {

    Order order;
    ArrayList<Product> products = new ArrayList<>();
    User user;
    boolean isAdmin;

    public Email(Order order, boolean isAdmin){
        this.order = order;
        this.isAdmin = isAdmin;

        if (isAdmin == true) {

            UsersMgr usersMgr = new UsersMgr(AdminActivity.dbName, AdminActivity.mongoClient);
            HashMap<String, String> userId = new HashMap<>();
            userId.put("Email", order.getUserId());

            ArrayList<User> userList = usersMgr.findDocument(userId, new HashMap<String, Integer>(), 1);
            if (userList.size() > 0) {
                user = userList.get(0);
            }
        }

    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public void addProduct (Product p){

        if(isAdmin == true){
            SupplierMgr  s = new SupplierMgr(AdminActivity.dbName, AdminActivity.mongoClient);
            HashMap<String, ObjectId> filter = new HashMap<>();
            filter.put("_id", new ObjectId(p.getUser()));
            ArrayList <Supplier> sup = s.findDocumentById(filter, new HashMap<String, Integer>(), 1);
            if(sup.size()>0){
                p.setSupEmail(sup.get(0).getEmail());
            }
        }

        products.add(p);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
