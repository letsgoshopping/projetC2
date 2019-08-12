package com.project.lgs.Database;


import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient;
import com.project.lgs.R;
import com.project.lgs.SupplierClasses.Supplier;

import org.bson.Document;

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

        public ArrayList<Supplier> findDocument (HashMap<String,String> values, HashMap<String,Integer> sorting, int limit){

            List <Document> res =  operations.findDocument(collection,values,sorting,limit);
            ArrayList <Supplier> user = new ArrayList<Supplier>();

            for (Document doc: res) {
                Supplier u = new Supplier((String)doc.get("Name"),
                        (String)doc.get("Description"),
                        (String)doc.get("PhoneNumber"),
                         R.drawable.prodsample,
                        (String)doc.get("JoinDate"),
                        (String)doc.get("Email"),
                        (String)doc.get("Password"));

                user.add(u);

            }

            return user;

        }
}
