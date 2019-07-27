package com.project.lgs.Database;

import com.google.android.gms.tasks.Task;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteFindIterable;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoCollection;
import com.mongodb.stitch.core.services.mongodb.remote.RemoteDeleteResult;
import com.mongodb.stitch.core.services.mongodb.remote.RemoteInsertOneResult;

import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;


public class MongoOperations {

        private RemoteMongoClient mongoClient;
        private String databaseName;


        MongoOperations(String databaseName,  RemoteMongoClient mongoClient){

            this.databaseName = databaseName;
            this.mongoClient = mongoClient;
        }


        public String insertDocument (String collection, HashMap<String,String> values){

            String res;

            RemoteMongoCollection mongoCollection = mongoClient.getDatabase(databaseName).getCollection(collection);

            Document newDoc = new Document();

            for(Entry<String, String> entry : values.entrySet()) {
                String key = entry.getKey();
                String val = entry.getValue();

                newDoc.append(key,val);
            }

            final Task <RemoteInsertOneResult> insertTask = mongoCollection.insertOne(newDoc);
            if (insertTask.isSuccessful()) {
                res = insertTask.getResult().getInsertedId().toString();
            } else {
                res = "-1";
            }

            return res;
        }

        public String updateDoument (String collection, HashMap<String,String> values, String id){

            String res;

            res = this.deleteDocument(collection,id);

            if (res.equals("1")) {

                res = this.insertDocument(collection,values);

            }

            return res;
        }

        public String deleteDocument (String collection, String id){

            String res;

            RemoteMongoCollection mongoCollection = mongoClient.getDatabase(databaseName).getCollection(collection);

            Document filterDoc = new Document().append("id", id);

            Task<RemoteDeleteResult> deleteTask = mongoCollection.deleteOne(filterDoc);

            if (deleteTask.isSuccessful()) {

                res = "1";

            } else {
                res = "-1";
            }

            return res;
        }

        public ArrayList<Document> findDocument (String collection, HashMap<String,String> values, HashMap<String,String> sorting){

            String res;

            RemoteMongoCollection mongoCollection = mongoClient.getDatabase(databaseName).getCollection(collection);

            Document searchDoc = new Document();

            for(Entry<String, String> entry : values.entrySet()) {
                String key = entry.getKey();
                String val = entry.getValue();

                searchDoc.append(key,val);
            }

            Document sortingDoc = new Document();

            for(Entry<String, String> entry : values.entrySet()) {
                String key = entry.getKey();
                String val = entry.getValue();

                sortingDoc.append(key,val);
            }

            RemoteFindIterable findResults = mongoCollection
                    .find(searchDoc)
                    .sort(sortingDoc);

            Task <List<Document>> resultsTask = findResults.into(new ArrayList<Document>());
            if (resultsTask.isSuccessful()) {

                ArrayList<Document> list = (ArrayList)resultsTask.getResult();;
                return list;

            } else {
                return null;
            }

    }


}
