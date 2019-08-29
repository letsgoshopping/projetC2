package com.project.lgs.Database;


import android.util.Log;

import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient;
import com.project.lgs.SupplierClasses.Supplier;

import org.bson.Document;
import org.bson.types.Binary;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SupplierMgr {

        MongoOperations operations;
        String collection  = "Supplier";


        public SupplierMgr(String databaseName, RemoteMongoClient mongoClient){

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


        public String updateDocument (HashMap<String,String> values, HashMap<String, ObjectId> filter){

            String res = "1";

            try {
                operations.updateDoument(collection, values, filter);
            }catch(Exception e){
                res = "-1";
            }

            return res;
        }

        public String updateDocumentWPic (HashMap<String,String> values, HashMap<String, ObjectId> filter, HashMap<String,byte[]> pics){

            String res = "1";

            try {
                operations.updateDocument(collection, values, filter,pics);
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

    public String deleteDocument (HashMap<String,String> id){

        String res = "1";
        try{
            operations.deleteDocument(collection,id);
        }catch(Exception e){
            res = "-1";
        }

        return res;
    }

        public ArrayList<Supplier> findDocument (HashMap<String,String> values, HashMap<String,Integer> sorting, int limit){

            List <Document> res =  operations.findDocument(collection,values,sorting,limit);
            ArrayList <Supplier> user = new ArrayList<Supplier>();

            for (Document doc: res) {
                Supplier u = new Supplier((String)doc.get("_id").toString(),
                        (String)doc.get("Name"),
                        (String)doc.get("Description"),
                        (String)doc.get("PhoneNumber"),
                        (doc.get("Image", Binary.class)==null?null:doc.get("Image", Binary.class).getData()),
                        (String)doc.get("JoinDate"),
                        (String)doc.get("Email"));

                user.add(u);

                //Log.d("image", doc.get("_id").toString());

            }

            return user;

        }

    public ArrayList<Supplier> findDocumentById (HashMap<String,ObjectId> values, HashMap<String,Integer> sorting, int limit){

        List <Document> res =  operations.findDocumentById(collection,values,sorting,limit);
        ArrayList <Supplier> product = new ArrayList<Supplier>();

        for (Document doc: res) {
            Supplier u = new Supplier((String)doc.get("_id").toString(),
                    (String)doc.get("Name"),
                    (String)doc.get("Description"),
                    (String)doc.get("PhoneNumber"),
                    (doc.get("Image", Binary.class)==null?null:doc.get("Image", Binary.class).getData()),
                    (String)doc.get("JoinDate"),
                    (String)doc.get("Email"));

            product.add(u);

        }

        return product;

    }
}
