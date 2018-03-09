package com.core.lib;

/**
 * Created by l on 06/01/2018.
 */

import android.annotation.SuppressLint;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.commons.io.FileUtils;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by l on 31/12/2017.
 */

public class Droid {

    private  Context app;
    private long fileLength=0;
    public Droid(Context AppContext){

        this.app = AppContext;

    }

    public HashMap<String,CoreHttpResponsed> $httpResponse=new HashMap<String,CoreHttpResponsed>();

    public  HashMap<String,HttpClient> $http=new HashMap<String,HttpClient>();
    public HashMap<String,Integer> $progData=new HashMap<String,Integer>();


    private static interface ProgressListener {
        void transferred(long num);
    }
    @SuppressWarnings("deprecation")
    private static class Mentity extends MultipartEntity {

        private final ProgressListener listener;

        public Mentity(final ProgressListener listener) {
            super();
            this.listener = listener;
        }

        public Mentity(final HttpMultipartMode mode,
                       final ProgressListener listener) {
            super(mode);
            this.listener = listener;
        }

        public Mentity(HttpMultipartMode mode, final String boundary,
                       final Charset charset, final ProgressListener listener) {
            super(mode, boundary, charset);
            this.listener = listener;
        }

        @Override
        public void writeTo(final OutputStream outstream) throws IOException {
            super.writeTo(new Mentity.CountingOutputStream(outstream, this.listener));
        }

        public static interface ProgressListener {
            void transferred(long num);
        }

        public static class CountingOutputStream extends FilterOutputStream {

            private final ProgressListener listener;
            private long transferred;

            public CountingOutputStream(final OutputStream out, final ProgressListener listener) {
                super(out);
                this.listener = listener;
                this.transferred = 0;
            }

            public void write(byte[] b, int off, int len) throws IOException {
                out.write(b, off, len);
                this.transferred += len;
                this.listener.transferred(this.transferred);
            }

            public void write(int b) throws IOException {
                out.write(b);
                this.transferred++;
                this.listener.transferred(this.transferred);
            }
        }

    }
    private class httppostrequest extends AsyncTask<Void,Integer,String> {
        HashMap<String,String> postdata=new HashMap<String,String>();
        String URL="",ID="";
        boolean ERR_NET=false;
        Runnable r,err=null;


        public httppostrequest(String ID,String URL,HashMap<String,String> postdata,Runnable r,Runnable err){

            this.URL=URL;
            this.postdata=postdata;
            this.r=r;
            this.ID=ID;
            if(err!=null){this.err=err;}

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(URL);
            CoreHttpResponsed responsedMessage = new CoreHttpResponsed();
            try{


                Mentity entity = new Mentity(
                        new Mentity.ProgressListener() {

                            @Override
                            public void transferred(long num) {

                            }
                        });


                for (Map.Entry<String,String> post : postdata.entrySet()) {
                    post.setValue("");
                    entity.addPart(post.getKey(),new StringBody(post.getValue()));
                }

                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();
                responsedMessage.$Data = EntityUtils.toString(r_entity);
                responsedMessage.$Code = response.getStatusLine().getStatusCode();
                $httpResponse.put(ID, responsedMessage);

            }
            catch (ClientProtocolException e) {
                responsedMessage.$IsNotSent = true;
                responsedMessage.$Error_Message = e.toString();
                $httpResponse.put(ID, responsedMessage);
                ERR_NET=true;
                Log.e("CORE PostRequest", e.toString());
            } catch (IOException e) {
                ERR_NET=true;
                responsedMessage.$IsNotSent = true;
                responsedMessage.$Error_Message = e.toString();
                $httpResponse.put(ID, responsedMessage);
                Log.e("CORE PostRequest",e.toString());
            }



            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(!ERR_NET){
                r.run();
            }else{

                if(err != null) err.run();
            }
        }
    }

    private class httpgetrequest extends AsyncTask<Void,Integer,String> {

        String URL="",ID="";
        boolean ERR_NET=false;
        Runnable r,err=null;
        CoreHttpResponsed responsedMessage = new CoreHttpResponsed();

        public httpgetrequest(String ID,String URL,Runnable r,Runnable err){

            this.URL=URL;

            this.r=r;
            this.ID=ID;
            if(err!=null){this.err=err;}

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            HttpClient httpclient = new DefaultHttpClient();
            HttpGet request = new HttpGet(URL);

            try{


                Mentity entity = new Mentity(
                        new Mentity.ProgressListener() {

                            @Override
                            public void transferred(long num) {

                            }
                        });

                // Making server call
                HttpResponse response = httpclient.execute(request);
                HttpEntity r_entity = response.getEntity();
                responsedMessage.$Data = EntityUtils.toString(r_entity);
                responsedMessage.$Code = response.getStatusLine().getStatusCode();
                $httpResponse.put(ID, responsedMessage);
            }
            catch (ClientProtocolException e) {

                ERR_NET=true;

                responsedMessage.$IsNotSent = true;
                responsedMessage.$Error_Message =e.toString();
                $httpResponse.put(ID, responsedMessage);
                Log.e("CORE","GetRequest error:" +e.toString());
            } catch (IOException e) {
                ERR_NET=true;

                responsedMessage.$IsNotSent = true;
                responsedMessage.$Error_Message =e.toString();
                $httpResponse.put(ID, responsedMessage);
                Log.e("CORE","GetRequest error:" +e.toString());
            }



            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(!ERR_NET){
                r.run();
            }else{
                if(err != null) err.run();
            }
        }
    }

    private class httpdeleterequest extends AsyncTask<Void,Integer,String> {

        String URL="",ID="";
        boolean ERR_NET=false;
        Runnable r,err=null;
        CoreHttpResponsed responsedMessage = new CoreHttpResponsed();

        public httpdeleterequest(String ID,String URL,Runnable r,Runnable err){

            this.URL=URL;

            this.r=r;
            this.ID=ID;
            if(err!=null){this.err=err;}

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            HttpClient httpclient = new DefaultHttpClient();
            HttpDelete request = new HttpDelete(URL);

            try{


                Mentity entity = new Mentity(
                        new Mentity.ProgressListener() {

                            @Override
                            public void transferred(long num) {

                            }
                        });

                // Making server call
                HttpResponse response = httpclient.execute(request);
                HttpEntity r_entity = response.getEntity();
                responsedMessage.$Data = EntityUtils.toString(r_entity);
                responsedMessage.$Code = response.getStatusLine().getStatusCode();
                $httpResponse.put(ID, responsedMessage);

            }
            catch (ClientProtocolException e) {

                ERR_NET=true;
                responsedMessage.$Error_Message = e.toString();
                responsedMessage.$IsNotSent = true;
                $httpResponse.put(ID, responsedMessage);
                Log.e("CORE","$DeleteRequest error:" +e.toString());
            } catch (IOException e) {
                ERR_NET=true;
                responsedMessage.$Error_Message =e.toString();
                responsedMessage.$IsNotSent = true;
                $httpResponse.put(ID, responsedMessage);
                Log.e("CORE","$DeleteRequest error:" +e.toString());
            }



            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(!ERR_NET){
                r.run();
            }else{
                if(err != null) err.run();
            }
        }
    }

    private class httputrequest extends AsyncTask<Void,Integer,String> {
        HashMap<String,String> postdata=new HashMap<String,String>();
        String URL="",ID="";
        boolean ERR_NET=false;
        Runnable r,err=null;
        CoreHttpResponsed responsedMessage = new CoreHttpResponsed();

        public httputrequest(String ID,String URL,HashMap<String,String> postdata,Runnable r,Runnable err){

            this.URL=URL;
            this.postdata=postdata;
            this.r=r;
            this.ID=ID;
            if(err!=null){this.err=err;}

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPut httppost = new HttpPut(URL);

            try{


                Mentity entity = new Mentity(
                        new Mentity.ProgressListener() {

                            @Override
                            public void transferred(long num) {

                            }
                        });


                for (Map.Entry<String,String> post : postdata.entrySet()) {
                    post.setValue("");
                    entity.addPart(post.getKey(),new StringBody(post.getValue()));
                }

                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();
                responsedMessage.$Data = EntityUtils.toString(r_entity);
                responsedMessage.$Code = response.getStatusLine().getStatusCode();
                $httpResponse.put(ID, responsedMessage);

            }
            catch (ClientProtocolException e) {

                ERR_NET=true;
                responsedMessage.$Error_Message = e.toString();
                responsedMessage.$IsNotSent = true;
                $httpResponse.put(ID, responsedMessage);
                Log.e("CORE","PostRequest error:" +e.toString());
            } catch (IOException e) {
                ERR_NET=true;

                responsedMessage.$Error_Message = e.toString();
                responsedMessage.$IsNotSent = true;
                $httpResponse.put(ID, responsedMessage);
                Log.e("CORE","PostRequest error:" +e.toString());
            }



            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(!ERR_NET){
                r.run();
            }else{
                if(err != null) err.run();
            }
        }
    }

    private class httpupload extends AsyncTask<Void,Integer,String>{
        HashMap<String,String> postdata=new HashMap<String,String>();
        String URL="",ID="";
        Runnable r,prog,er;
        File file;
        boolean ERR_NET=false;
        public httpupload(String ID, String URL, File file, HashMap<String,String> postdata, Runnable prog, Runnable r, Runnable er){

            this.URL=URL;
            this.postdata=postdata;
            this.r=r;
            this.er = er;
            this.ID=ID;
            this.prog=prog;
            this.file=file;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            $progData.put(ID, values[0]);

            if(prog!=null) prog.run();


        }

        @Override
        protected String doInBackground(Void... params) {


            String responseString = null;
            CoreHttpResponsed responsedMessage = new CoreHttpResponsed();
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(URL);
            $http.put(ID,httpclient);

            try{


                Mentity entity = new Mentity(
                        new Mentity.ProgressListener() {

                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) fileLength) * 100));

                            }
                        });
                entity.addPart("FILE",new FileBody(file));
                fileLength = entity.getContentLength();
                for (Map.Entry<String,String> post : postdata.entrySet()) {

                    entity.addPart(post.getKey(),new StringBody(post.getValue()));
                }

                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();
                responsedMessage.$Data = EntityUtils.toString(r_entity);
                responsedMessage.$Code = response.getStatusLine().getStatusCode();
                $httpResponse.put(ID, responsedMessage);

            }
            catch (ClientProtocolException e) {
                responsedMessage.$Error_Message  =e.toString();
                responsedMessage.$IsNotSent = true;
                $httpResponse.put(ID, responsedMessage);
                Log.e("CORE http upload", e.toString());
                ERR_NET = true;

            } catch (IOException e) {
                responsedMessage.$Error_Message  =e.toString();
                responsedMessage.$IsNotSent = true;
                $httpResponse.put(ID, responsedMessage);
                Log.e("CORE http upload", e.toString());
                ERR_NET = true;
            }

            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(r!=null) r.run();
            else if(ERR_NET && er!=null) er.run();
        }
    }


    //SQLite ------------------------------------------------------------------------

    public SQLiteDatabase $DB(String DB){

        SQLiteDatabase db =app.openOrCreateDatabase(DB, app.MODE_PRIVATE, null);
        return db;
    }


    // Session------------------------------------------------------------------------

    public Integer $session(String KEY){
        int output=0;


        try{
            SQLiteDatabase db=new Droid(app).$DB(KEY);
            Cursor c=db.rawQuery("SELECT * FROM session",null);
            if(c.getCount()>0){

                output=1;

            }}
        catch(SQLiteException e){

            output = 0;

        }
        return output;
    }

    public void $SetSession(String KEY,String VALUE){

        SQLiteDatabase db=new Droid(app).$DB(KEY);
        db.execSQL("CREATE TABLE IF NOT EXISTS session(log VARCHAR);");
        Cursor c=db.rawQuery("SELECT * FROM session",null);
        if(c.getCount()>0){

            db.execSQL("UPDATE session SET log=? WHERE 1",new String[]{VALUE});

        }else{ db.execSQL("INSERT INTO session(log)VALUES(?)",new String[]{VALUE});}


    }

    public String $GetSession(String KEY){
        String output="";
        SQLiteDatabase db=new Droid(app).$DB(KEY);
        Cursor c=db.rawQuery("SELECT * FROM session",null);
        if(c.getCount()>0){
            c.moveToFirst();
            output=c.getString(c.getColumnIndex("log"));
        }
        return output;
    }

    public void $RemoveSession(String KEY){

        app.deleteDatabase(KEY);

    }


    // HTTP ----------------------------------------------------------------------------
    public void $PostRequest(String REQUEST_ID, String URL, HashMap<String,String> POSTDATA, Runnable ONSUCCESS, Runnable ONERROR){

        new httppostrequest(REQUEST_ID,URL,POSTDATA,ONSUCCESS,ONERROR).execute();

    }

    public void $GetRequest(String REQUEST_ID, String URL, Runnable ONSUCCESS, Runnable ONERROR){

        new httpgetrequest(REQUEST_ID,URL,ONSUCCESS,ONERROR).execute();

    }

    public void $DeleteRequest(String REQUEST_ID, String URL, Runnable ONSUCCESS, Runnable ONERROR){

        new httpdeleterequest(REQUEST_ID,URL,ONSUCCESS,ONERROR).execute();

    }

    public void $PutRequest(String REQUEST_ID, String URL, HashMap<String,String> POSTDATA, Runnable ONSUCCESS, Runnable ONERROR){

        new httputrequest(REQUEST_ID,URL,POSTDATA,ONSUCCESS,ONERROR).execute();

    }

    public void $Upload(String ID,String URL,File FILE,HashMap<String,String> POSTDATA,Runnable ONPROGRESS,Runnable ONLOAD,Runnable ERROR){

        new httpupload(ID,URL,FILE,POSTDATA,ONPROGRESS,ONLOAD,ERROR).execute();

    }

    public void $Download(final String URL_,String PathName,final String Filename,final Runnable r){
        final File root = new File(PathName);
        new AsyncTask<String, Integer,String>(){

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... params) {


                InputStream input = null;
                OutputStream output = null;
                HttpURLConnection connection = null;

                try {
                    URL url = new URL(URL_);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                    int fileLength = connection.getContentLength();

                    input = connection.getInputStream();
                    output = new FileOutputStream(root+"/"+Filename);
                    byte data[] = new byte[4096];
                    long total = 0;
                    int count;

                    while ((count = input.read(data)) != -1) {
                        // allow canceling with back button
                        if (isCancelled()) {
                            input.close();
                            return null;
                        }
                        total += count;
                        // publishing the progress....
                        if (fileLength > 0) // only if total length is known
                            publishProgress((int) (total * 100 / fileLength));
                        output.write(data, 0, count);

                    }


                }catch (IOException e){}
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                r.run();
            }
        }.execute();

    }




    // FILE ----------------------------------------------------------------------------
    public File $CreateFolder(String PathName,String FOLDER){

        File folder = new File(PathName + FOLDER);
        if(!folder.exists()){

            folder.mkdir();
        }
        return folder;

    }

    public static boolean $CopyFile(String PathName,String srcFile, String srcDST)  {

        File SRC = new File(PathName+""+srcFile);
        File DST = new File(srcDST);

        try {
            InputStream in = new FileInputStream(SRC);

            try {
                OutputStream out = new FileOutputStream(DST);
                try {
                    // Transfer bytes from in to out
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                } finally {
                    out.close();
                    return true;
                }
            } finally {
                in.close();
                return true;
            }}
        catch (IOException e){

            return false;

        }
    }

    public boolean $CopyDirectory(String src, String dst) {

        File srcDir = new File(src);
        File destDir = new File(dst);
        try {
            FileUtils.copyDirectory(srcDir, destDir);
            return true;
        } catch (IOException e) {
            Log.e("CORE $CopyDirectory",e.toString());
            return false;
        }

    }


    public boolean $CleanDirectory(String dir) {

        try {
            FileUtils.cleanDirectory(new File(dir));
            return true;
        } catch (IOException e) {
            Log.e("CORE $CleanDirectory",e.toString());
            return false;
        }

    }

    public boolean $DeleteDirectory(String dir) {

        try {
            FileUtils.deleteDirectory(new File(dir));
            return true;
        } catch (IOException e) {
            Log.e("CORE $DeleteDirectory",e.toString());
            return false;
        }

    }

    public Boolean $DeleteFile(String PathName,String FILENAME){
        String output="";
        File folder = new File(PathName+"");

        if(folder.exists()) {

            File file = new File(folder,FILENAME);
            return file.delete();

        }
        return false;
    }




    public boolean $WriteFile(byte[] FILE,String PathName,String FILENAME){

        File folder = new File(PathName);

        if(folder.exists()) {

            File file = new File(folder,FILENAME);
            BufferedOutputStream bos = null;
            try {
                bos = new BufferedOutputStream(new FileOutputStream(file));
            } catch (FileNotFoundException e) {
                Log.e("Core $WriteFile",e.toString());
            }


            try {
                bos.write(FILE);
                bos.flush();
                bos.close();
            } catch (IOException e) {
                Log.e("Core $WriteFile",e.toString());
            }


        }

        return false;
    }


    public byte[] $ReadFile(String PathName,String FILENAME){

        File folder = new File(PathName);

        if(folder.exists()) {

            File file = new File(folder,FILENAME);
            int size = (int) file.length();
            byte[] bytes = new byte[size];
            try {
                BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
                buf.read(bytes, 0, bytes.length);
                buf.close();

                return bytes;

            } catch (FileNotFoundException e) {

                Log.e("Core $ReadFile",e.toString());
                return null;
            } catch (IOException e) {

                Log.e("Core $ReadFile",e.toString());
                return null;
            }

        }

        return null;
    }





    public String $ReadTextFile(String PathName,String FILENAME,Boolean NL){
        String output="";
        File folder = new File(PathName+"");

        if(folder.exists()) {
            try{

                File file = new File(folder,FILENAME);
                StringBuilder text = new StringBuilder();
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                while ((line = br.readLine()) != null) {
                    text.append(line);
                    if(NL){
                        text.append('\n');
                    }
                }
                br.close();

                output=text.toString();
            }
            catch (IOException e){}
        }
        return output;
    }






    public Boolean $WriteTextFile(String PathName,String FILENAME,String CONTENT,Boolean APPEND){


        File folder = new File(PathName);
        if(folder.exists()){

            File gpxfile = new File(folder, FILENAME);

            try {
                FileWriter writer = new FileWriter(gpxfile);

                if(!APPEND){

                    writer.write(CONTENT);
                }else{
                    writer.append(CONTENT);}
                writer.flush();
                writer.close();
                return true;
            }catch(IOException e){
                Log.e("CORE $WriteTextFile",e.toString());
                return false;

            }
        }
        else return false;



    }




    //string to Object parser
    public JSONObject $Json(String strJSON){

        JSONObject output=null;
        if((strJSON==null)||(strJSON.equals(""))){

            strJSON="{'null':'java.jsonErr'}";
        }
        try{
            output=new JSONObject(strJSON);
        }
        catch(JSONException e){
            Log.e("CORE","$Json error:" +e.toString());
        }

        return output;

    }

    public JSONArray $JsonArray(String STRING){
        if((STRING==null)||(STRING.equals(""))){

            STRING="[]";
        }
        JSONArray output=null;

        try{

            output=new JSONArray(STRING);
        }
        catch (JSONException e){
            Log.e("CORE","$JSONArray error:" +e.toString());
        }

        return output;
    }

    public String $GetString(JSONObject JSON,String KEY){
        String output="";
        if(JSON.has(KEY)){
            try{

                output=JSON.getString(KEY);
            }
            catch(JSONException e){

                Log.e("CORE","$GetString error:" +e.toString());

            }}
        return output;
    }

    public Boolean $GetBool(JSONObject JSON,String KEY){
        Boolean output=false;
        if(JSON.has(KEY)){
            try{

                output=JSON.getBoolean(KEY);
            }
            catch(JSONException e){

                Log.e("CORE","$GetBool error:" +e.toString());

            }}
        return output;
    }

    public int $GetInt(JSONObject JSON,String KEY){
        int output=0;
        if(JSON.has(KEY)){
            try{

                output=JSON.getInt(KEY);
            }
            catch(JSONException e){

                Log.e("CORE","$GetInt error:" +e.toString());

            }}
        return output;
    }

    public double $GetDouble(JSONObject JSON,String KEY){
        double output=0;
        if(JSON.has(KEY)){
            try{

                output=JSON.getDouble(KEY);
            }
            catch(JSONException e){

                Log.e("CORE","$GetDouble error:" +e.toString());

            }}
        return output;
    }

    public long $GetLong(JSONObject JSON,String KEY){
        long output=0;
        if(JSON.has(KEY)){
            try{

                output=JSON.getLong(KEY);
            }
            catch(JSONException e){

                Log.e("CORE","$GetLong error:" +e.toString());

            }}
        return output;
    }

    public Object $Get(JSONObject JSON,String KEY){
        Object output=0;
        if(JSON.has(KEY)){
            try{

                output=JSON.get(KEY);
            }
            catch(JSONException e){

                Log.e("CORE","$Get error:" +e.toString());

            }}
        return output;
    }

    public String $GetString(JSONArray array,int Index){
        String output="";
        try{

            output=array.getString(Index);
        }
        catch(JSONException e){

            Log.e("CORE","$GetString error:" +e.toString());

        }
        return output;
    }

    public Boolean $GetBool(JSONArray array,int Index){
        Boolean output=false;

        try{

            output=array.getBoolean(Index);

        }
        catch(JSONException e) {

            Log.e("CORE", "$GetBool error:" + e.toString());

        }
        return output;
    }

    public int $GetInt(JSONArray array,int Index){
        int output=0;

        try{

            output=array.getInt(Index);
        }
        catch(JSONException e){

            Log.e("CORE","$GetInt error:" +e.toString());

        }
        return output;
    }

    public double $GetDouble(JSONArray array,int Index){
        double output=0;

        try{

            output=array.getDouble(Index);
        }
        catch(JSONException e){

            Log.e("CORE","$GetDouble error:" +e.toString());

        }
        return output;
    }

    public long $GetLong(JSONArray array,int Index){
        long output=0;

        try{

            output=array.getLong(Index);
        }
        catch(JSONException e){

            Log.e("CORE","$GetLong error:" +e.toString());

        }
        return output;
    }

    public Object $Get(JSONArray array,int Index){
        Object output=0;

        try{

            output=array.get(Index);

        }
        catch(JSONException e){

            Log.e("CORE","$Get error:" +e.toString());

        }
        return output;
    }


    public void $Click(View v,final Event e){

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EventResult result = new EventResult();
                result.$View = v;
                e.Run(result);
            }
        });

    }


    public void $Focus(View v,final Event e){

        v.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EventResult result = new EventResult();
                result.$View = v;
                result.$HasFocus = hasFocus;
                e.Run(result);
            }
        });

    }


    public void $KeyPress(View v,final Event e){

        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                EventResult result = new EventResult();
                result.$View = v;
                result.$KeyCode = keyCode;
                result.$KeyEvent = event;
                return e.Run(result);

            }
        });

    }







    public void $Touch(View v,final Event e){

        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                EventResult result = new EventResult();
                result.$View = v;
                result.$MotionEvent = event;
                return e.Run(result);
            }
        });

    }



    public Boolean $HasInternetAccess(){

        ConnectivityManager connectivityManager = (ConnectivityManager) app.getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }



    public void $GetUrlImage(final View IMAGEVIEW,final String URL,final Integer ROUNDPX){

        new AsyncTask<String,Void,Bitmap>() {
            Bitmap bitmap = null;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Bitmap doInBackground(String... params) {
                String imageURL = URL;


                try {

                    InputStream input = new java.net.URL(imageURL).openStream();

                    bitmap = BitmapFactory.decodeStream(input);


                } catch (Exception e) {
                    e.printStackTrace();
                }
                return bitmap;

            }

        };

    }
}
