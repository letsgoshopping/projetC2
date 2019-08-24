package com.project.lgs.Database;

import android.util.Log;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mongodb.BasicDBObject;
import com.mongodb.stitch.android.core.auth.StitchUser;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteFindIterable;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoCollection;
import com.mongodb.stitch.core.services.mongodb.remote.RemoteDeleteResult;
import com.mongodb.stitch.core.services.mongodb.remote.RemoteInsertOneResult;
import com.mongodb.stitch.core.services.mongodb.remote.RemoteUpdateResult;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;


public class MongoOperations{

        private RemoteMongoClient mongoClient;
        private String databaseName;
        ArrayList<Document> result = new ArrayList<>();


        MongoOperations(String databaseName,  RemoteMongoClient mongoClient){

            this.databaseName = databaseName;
            this.mongoClient = mongoClient;
        }


        public void insertDocument (String collection, HashMap<String,String> values, HashMap<String,byte[]> pics)throws Exception{

            RemoteMongoCollection mongoCollection = mongoClient.getDatabase(databaseName).getCollection(collection);

            Document newDoc = new Document();


            for (Entry<String, String> entry : values.entrySet()) {
                String key = entry.getKey();
                String val = entry.getValue();

                newDoc.append(key, val);
            }

            for (Entry<String, byte[]> entry : pics.entrySet()) {
                String key = entry.getKey();
                byte[] val = entry.getValue();

                newDoc.append(key, val);
            }

             mongoCollection.insertOne(newDoc).continueWithTask(new Continuation<StitchUser, Task<StitchUser>>() {
                @Override
                public Task<StitchUser> then(Task<StitchUser> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw new Exception("Insertion failed");
                    }

                    return task;
                }
            });


        }

        public void insertDocument (String collection, HashMap<String,String> values)throws Exception{

                RemoteMongoCollection mongoCollection = mongoClient.getDatabase(databaseName).getCollection(collection);

                Document newDoc = new Document();
                int a;

                for (Entry<String, String> entry : values.entrySet()) {
                    String key = entry.getKey();
                    String val = entry.getValue();

                    newDoc.append(key, val);
                }

            mongoCollection.insertOne(newDoc).continueWithTask(new Continuation<StitchUser, Task<StitchUser>>() {
                @Override
                public Task<StitchUser> then(Task<StitchUser> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw new Exception("Insertion failed");
                    }

                    return task;
                }
            });

        }

        public void insertDocumentMulti (String collection, Document values,HashMap<String,String> values2)throws Exception {

            RemoteMongoCollection mongoCollection = mongoClient.getDatabase(databaseName).getCollection(collection);


            for (Entry<String, String> entry : values2.entrySet()) {
                String key = entry.getKey();
                String val = entry.getValue();

                values.append(key, val);
            }


            mongoCollection.insertOne(values).continueWithTask(new Continuation<StitchUser, Task<StitchUser>>() {
                @Override
                public Task<StitchUser> then(Task<StitchUser> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw new Exception("Insertion failed");
                    }
                    return task;
                }
            });

        }

    public void insertDocumentWait (String collection, HashMap<String,String> values)throws Exception{

        RemoteMongoCollection mongoCollection = mongoClient.getDatabase(databaseName).getCollection(collection);

        Document newDoc = new Document();

        for (Entry<String, String> entry : values.entrySet()) {
            String key = entry.getKey();
            String val = entry.getValue();

            newDoc.append(key, val);
        }

        final Task <RemoteInsertOneResult> insertTask = mongoCollection.insertOne(newDoc).continueWithTask(new Continuation<StitchUser, Task<StitchUser>>() {
            @Override
            public Task<StitchUser> then(Task<StitchUser> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw new Exception("Insertion failed");
                }

                return task;
            }
        });

       /* while (!insertTask.isComplete()){
            Log.d("lala", "insertDocumentWait: ");
        }*/

    }

        public void updateDoument (String collection, HashMap<String,String> values,  HashMap<String, ObjectId> filter)throws Exception{

            RemoteMongoCollection mongoCollection = mongoClient.getDatabase(databaseName).getCollection(collection);
            Document filterDoc = new Document();
            Document cond = new Document();

            for (Entry<String, String> entry : values.entrySet()) {
                String key = entry.getKey();
                String val = entry.getValue();

                cond.append(key, val);
            }

            for (Entry<String, ObjectId> entry : filter.entrySet()) {
                String key = entry.getKey();
                ObjectId val = entry.getValue();

                filterDoc.append(key, val);
            }

            Document updateDoc = new Document().append("$push",cond);

            try {

                final Task <RemoteUpdateResult> updateTask =
                        mongoCollection.updateOne(filterDoc, updateDoc);
                updateTask.addOnCompleteListener(new OnCompleteListener <RemoteUpdateResult> () {
                    @Override
                    public void onComplete(Task <RemoteUpdateResult> task) {
                        if (task.isSuccessful()) {
                            long numMatched = task.getResult().getMatchedCount();
                            long numModified = task.getResult().getModifiedCount();
                            Log.d("app", String.format("successfully matched %d and modified %d documents",
                                    numMatched, numModified));
                        } else {
                            Log.e("app", "failed to update document with: ", task.getException());
                        }
                    }
                });

                while(!updateTask.isComplete()){}

            } catch (Exception e){

                throw new Exception("Update failed");

            }

        }

        public void updateDocumentMulti (String collection, Document values,HashMap<String,String> values2,HashMap<String,ObjectId> filter)throws Exception {

            RemoteMongoCollection mongoCollection = mongoClient.getDatabase(databaseName).getCollection(collection);

            Document filterDoc = new Document();


            for (Entry<String, String> entry : values2.entrySet()) {
                String key = entry.getKey();
                String val = entry.getValue();

                values.append(key, val);
            }



            for (Entry<String, ObjectId> entry : filter.entrySet()) {
                String key = entry.getKey();
                ObjectId val = entry.getValue();

                filterDoc.append(key, val);
            }


            Document updateDoc = new Document().append("$set",values);

            try {

                final Task <RemoteUpdateResult> updateTask =
                        mongoCollection.updateOne(filterDoc, updateDoc);
                updateTask.addOnCompleteListener(new OnCompleteListener <RemoteUpdateResult> () {
                    @Override
                    public void onComplete(Task <RemoteUpdateResult> task) {
                        if (task.isSuccessful()) {
                            long numMatched = task.getResult().getMatchedCount();
                            long numModified = task.getResult().getModifiedCount();
                            Log.d("app", String.format("successfully matched %d and modified %d documents",
                                    numMatched, numModified));
                        } else {
                            Log.e("app", "failed to update document with: ", task.getException());
                        }
                    }
                });

                while (!updateTask.isComplete()){}

            } catch (Exception e){

                throw new Exception("Update failed");

            }
        }

        public void deleteDocument (String collection, ObjectId id) throws Exception{

            String res;

            RemoteMongoCollection mongoCollection = mongoClient.getDatabase(databaseName).getCollection(collection);

            Document filterDoc = new Document().append("_id", id);

            final Task<RemoteDeleteResult> deleteTask = mongoCollection.deleteMany(filterDoc);
            deleteTask.addOnCompleteListener(new OnCompleteListener <RemoteDeleteResult> () {
                @Override
                public void onComplete(Task <RemoteDeleteResult> task) {
                    if (task.isSuccessful()) {
                        long numDeleted = task.getResult().getDeletedCount();
                        Log.d("app", String.format("successfully deleted %d documents", numDeleted));
                    } else {
                        Log.e("app", "failed to delete document with: ", task.getException());
                    }
                }
            });

            while(!deleteTask.isComplete()){}
        }

        public void deleteDocument (String collection, HashMap<String,String> filter) throws Exception{

            Document filterDoc =  new Document();

            RemoteMongoCollection mongoCollection = mongoClient.getDatabase(databaseName).getCollection(collection);

            for (Entry<String, String> entry : filter.entrySet()) {
                String key = entry.getKey();
                String val = entry.getValue();

                filterDoc.append(key, val);
            }

            final Task<RemoteDeleteResult> deleteTask = mongoCollection.deleteMany(filterDoc);
            deleteTask.addOnCompleteListener(new OnCompleteListener <RemoteDeleteResult> () {
                @Override
                public void onComplete(Task <RemoteDeleteResult> task) {
                    if (task.isSuccessful()) {
                        long numDeleted = task.getResult().getDeletedCount();
                        Log.d("app", String.format("successfully deleted %d documents", numDeleted));
                    } else {
                        Log.e("app", "failed to delete document with: ", task.getException());
                    }
                }
            });

            while(!deleteTask.isComplete()){}
        }

        public ArrayList<Document> findDocument (String collection, HashMap<String,String> values, HashMap<String,Integer> sorting,int limit) {

            RemoteMongoCollection mongoCollection = mongoClient.getDatabase(databaseName).getCollection(collection);

            Document searchDoc = new Document();

            for (Entry<String, String> entry : values.entrySet()) {
                String key = entry.getKey();
                String val = entry.getValue();

                searchDoc.append(key, val);
            }

            Document sortingDoc = new Document();

            if (sorting != null) {

                for (Entry<String, Integer> entry : sorting.entrySet()) {
                    String key = entry.getKey();
                    int val = entry.getValue();

                    sortingDoc.append(key, val);
                }
            }

            RemoteFindIterable findResults;

            if(limit != 0) {
                findResults = mongoCollection
                        .find(searchDoc)
                        .limit(limit)
                        .sort(sortingDoc);
            }
            else{
                findResults = mongoCollection
                        .find(searchDoc)
                        .sort(sortingDoc);
            }


            Task <List<Document>> itemsTask = findResults.into(result).addOnCompleteListener(new OnCompleteListener <List<Document>> () {
                @Override
                public void onComplete(Task<List<Document>> task) {
                    if (task.isSuccessful()) {
                    } else {
                        Log.e("app", "failed to find documents with: ", task.getException());
                    }
                }
            });

            while (!itemsTask.isComplete()){};
            return  result;

        }

    public ArrayList<Document> findDocumentById (String collection, HashMap<String,ObjectId> values, HashMap<String,Integer> sorting,int limit) {

        RemoteMongoCollection mongoCollection = mongoClient.getDatabase(databaseName).getCollection(collection);

        Document searchDoc = new Document();

        for (Entry<String, ObjectId> entry : values.entrySet()) {
            String key = entry.getKey();
            ObjectId val = entry.getValue();

            searchDoc.append(key, val);
        }

        Document sortingDoc = new Document();

        if (sorting != null) {

            for (Entry<String, Integer> entry : sorting.entrySet()) {
                String key = entry.getKey();
                int val = entry.getValue();

                sortingDoc.append(key, val);
            }
        }

        RemoteFindIterable findResults;

        if(limit != 0) {
            findResults = mongoCollection
                    .find(searchDoc)
                    .limit(limit)
                    .sort(sortingDoc);
        }
        else{
            findResults = mongoCollection
                    .find(searchDoc)
                    .sort(sortingDoc);
        }


        Task <List<Document>> itemsTask = findResults.into(result).addOnCompleteListener(new OnCompleteListener <List<Document>> () {
            @Override
            public void onComplete(Task<List<Document>> task) {
                if (task.isSuccessful()) {
                } else {
                    Log.e("app", "failed to find documents with: ", task.getException());
                }
            }
        });

        while (!itemsTask.isComplete()){};
        return  result;

    }

        public ArrayList<Document> findDocumentNonExact (String collection, BasicDBObject values, HashMap<String,Integer> sorting, int limit) {

            RemoteMongoCollection mongoCollection = mongoClient.getDatabase(databaseName).getCollection(collection);

            Document sortingDoc = new Document();

            if (sorting != null) {

                for (Entry<String, Integer> entry : sorting.entrySet()) {
                    String key = entry.getKey();
                    int val = entry.getValue();

                    sortingDoc.append(key, val);
                }
            }

            RemoteFindIterable findResults;

            if(limit != 0) {
                findResults = mongoCollection
                        .find(values)
                        .limit(limit)
                        .sort(sortingDoc);
            }
            else{
                findResults = mongoCollection
                        .find(values)
                        .sort(sortingDoc);
            }


            Task <List<Document>> itemsTask = findResults.into(result).addOnCompleteListener(new OnCompleteListener <List<Document>> () {
                @Override
                public void onComplete(Task<List<Document>> task) {
                    if (task.isSuccessful()) {
                    } else {
                        Log.e("app", "failed to find documents with: ", task.getException());
                    }
                }
            });

            while (!itemsTask.isComplete()){};
            return  result;

        }
}
