package com.project.lgs.Database;

import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient;
import com.project.lgs.CategoryClasses.Category;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CategoriesMgr {

        MongoOperations operations;
        String collection  = "Category";


        public CategoriesMgr(String databaseName, RemoteMongoClient mongoClient){

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


        public String updateDoument (HashMap<String,String> values, HashMap<String, ObjectId> filter){

            String res = "1";

            try {
                operations.updateDoument(collection, values, filter);
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

        public ArrayList<Category> findDocument (HashMap<String,String> values, HashMap<String,Integer> sorting){

            List <Document> res =  operations.findDocument(collection,values,sorting,0);
            ArrayList <Category> categories = new ArrayList<Category>();

            for (Document doc: res) {
                Category cat = new Category((String)doc.get("_id").toString(),
                        (String)doc.get("Name"),
                        (String)doc.get("Display"));

                categories.add(cat);

            }

            return categories;

        }
}
