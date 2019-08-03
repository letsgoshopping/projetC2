package com.project.lgs.Database;


import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient;

import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TagsMgr {

        MongoOperations operations;
        String collection  = "Tags";


        public TagsMgr(String databaseName, RemoteMongoClient mongoClient){

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

        public List<Document> findDocument (HashMap<String,String> values, HashMap<String,Integer> sorting, int limit){

            return operations.findDocument(collection,values,sorting, limit);

        }
}
