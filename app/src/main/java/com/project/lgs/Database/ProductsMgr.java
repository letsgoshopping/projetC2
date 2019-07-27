package com.project.lgs.Database;


import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient;
import org.bson.Document;
import java.util.ArrayList;
import java.util.HashMap;



public class ProductsMgr {

        MongoOperations operations;
        String collection  = "Products";


        ProductsMgr(String databaseName, RemoteMongoClient mongoClient){

            this.operations = new MongoOperations(databaseName, mongoClient);
        }


        public String insertDocument (HashMap<String,String> values){

            return operations.insertDocument(collection,values);
        }


        public String updateDoument (HashMap<String,String> values, String id){

            return operations.updateDoument(collection,values,id);
        }

        public String deleteDocument (String id){

            return operations.deleteDocument(collection,id);
        }

        public ArrayList<Document> findDocument (HashMap<String,String> values, HashMap<String,String> sorting){

            return operations.findDocument(collection,values,sorting);

        }
}
