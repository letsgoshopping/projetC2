package com.project.lgs.Database;


import android.util.Log;

import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient;
import com.project.lgs.Product;
import com.project.lgs.R;

import org.bson.Document;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ProductsMgr {

        MongoOperations operations;
        String collection  = "Products";


        public ProductsMgr(String databaseName, RemoteMongoClient mongoClient){

            this.operations = new MongoOperations(databaseName, mongoClient);
        }


        public String insertDocument (HashMap<String,String> values){

            String res = "1";
            try{
                operations.insertDocument(collection,values);
            }catch (Exception e){
                res = "-1";
            }

            return res;
        }


        public String updateDoument (HashMap<String,String> values, String id){

            String res = "1";

            try {
                operations.updateDoument(collection, values, id);
            }catch(Exception e){
                res = "-1";
            }

            return res;
        }

        public String deleteDocument (String id){

            String res = "1";
            try{
                operations.deleteDocument(collection,id);
            }catch(Exception e){
                res = "-1";
            }

            return res;
        }

        public ArrayList<Product> findDocument (HashMap<String,String> values, HashMap<String,Integer> sorting, int limit){

            List <Document> res =  operations.findDocument(collection,values,sorting,limit);
            ArrayList <Product> product = new ArrayList<Product>();

            for (Document doc: res) {
                Product pro = new Product((String)doc.get("Title"),
                        (String)doc.get("Description"),
                        (String)doc.get("Rating"),
                        (String)doc.get("Price"), R.drawable.prodsample,

                        (String)doc.get("User"),
                        (String)doc.get("PDate"),
                        (String)doc.get("Category"));

                product.add(pro);

            }

            Log.d("searchArray",Integer.toString(product.size()));
            return product;

        }
}
