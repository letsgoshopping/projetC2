package com.project.lgs.Database;

import android.util.Log;

import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient;
import com.project.lgs.CartClasses.Cart;
import com.project.lgs.OrdersClasses.Order;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CartMgr {

    MongoOperations operations;
    String collection  = "Cart";


    public CartMgr(String databaseName, RemoteMongoClient mongoClient){

        this.operations = new MongoOperations(databaseName, mongoClient);
    }


    public String insertDocument (Document values,HashMap<String,String> values2){

        String res = "1";
        try{
            operations.insertDocumentMulti(collection,values,values2);
        }catch (Exception e){
            res = "-1";
        }

        return res;
    }

    public String updateDocument (HashMap<String,String> values, HashMap<String,ObjectId> filter){

        String res = "1";

        try {
            operations.updateDoument(collection, values, filter);
        }catch(Exception e){
            res = "-1";
        }

        return res;
    }

    public String updateDocumentMulti (Document values,HashMap<String,String> values2,HashMap<String, ObjectId> filter){

        String res = "1";

        try {
            operations.updateDocumentMulti(collection, values, values2, filter);
        }catch(Exception e){
            res = "-1";
        }

        return res;
    }

    public String deleteDocument (ObjectId id){

        String res = "1";
        try{
            operations.deleteDocument(collection,id);
        }catch(Exception e){
            res = "-1";
        }

        return res;
    }

    public ArrayList<Cart> findDocument (HashMap<String,String> values, HashMap<String,Integer> sorting, int limit){

        List <Document> res =  operations.findDocument(collection,values,sorting,limit);
        ArrayList <Cart> ordder = new ArrayList<Cart>();


        for (Document doc: res) {
            Cart pro = new Cart((String)doc.get("_id").toString(),
                    (String)doc.get("InvoiceNumber"),
                    (String)doc.get("Date"),
                    (String)doc.get("User"),
                    (Document) doc.get("Products"));

            ordder.add(pro);


        }

        return ordder;

    }
}
