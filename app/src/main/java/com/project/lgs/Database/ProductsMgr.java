package com.project.lgs.Database;


import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient;
import com.project.lgs.ProductClasses.Product;

import org.bson.Document;

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

        public String insertDocumentWPic (HashMap<String,String> values, HashMap<String,byte[]> pics){

            String res = "1";
            try{
                operations.insertDocument(collection,values,pics);
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
                        (String)doc.get("Price"),
                        (byte[])doc.get("Image"),
                        (String)doc.get("User"),
                        (String)doc.get("PDate"),
                        (String)doc.get("Category"));

                product.add(pro);

            }

            return product;

        }
}
