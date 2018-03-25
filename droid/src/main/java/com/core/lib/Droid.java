package com.core.lib;

/**
 * Created by l on 06/01/2018.
 */

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;

import android.content.DialogInterface;
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
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.commons.io.FileUtils;
import org.apache.http.protocol.HTTP;
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
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;


/**
 * Created by l on 31/12/2017.
 */

public class Droid {

    private  Context app;
    private long fileLength=0;

    /**
     *
     * @param AppContext
     */
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

    private String inputStreamToString(InputStream is) {

        String line = "";
        StringBuilder total = new StringBuilder();

        // Wrap a BufferedReader around the InputStream
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));

        try {
            // Read response until the end
            while ((line = rd.readLine()) != null) {
                total.append(line);
            }
        } catch (IOException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        catch(Exception e)
        {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }

        // Return full string
        return total.toString();
    }



    private class httppostrequest extends AsyncTask<Void,Integer,String> {
        HashMap<String,String> postdata=new HashMap<String,String>();
        String URL="";
        boolean ERR_NET=false;
        HttpRequest irequest;
        Exception httpe=null;
        CoreHttpResponsed responsedMessage = new CoreHttpResponsed();



        public httppostrequest(String URL,HashMap<String,String> postdata,HttpRequest irequest){

            this.URL=URL;
            this.postdata=postdata;
            this.irequest = irequest;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(URL);

            try{


                Mentity entity = new Mentity(
                        new Mentity.ProgressListener() {

                            @Override
                            public void transferred(long num) {

                            }
                        });


                for (Map.Entry<String,String> post : postdata.entrySet()) {

                    entity.addPart(post.getKey(),new StringBody(post.getValue()));

                }

                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();
                responsedMessage.$Data =  inputStreamToString(response.getEntity().getContent());
                responsedMessage.$Code = response.getStatusLine().getStatusCode();
                if(responsedMessage.$Code != 200) ERR_NET =true;

            }
            catch (ClientProtocolException e) {
                ERR_NET =true;

                httpe = e;

            } catch (IOException e) {
                ERR_NET =true;

                httpe = e;
            }



            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(!ERR_NET){
                irequest.OnSuccess(responsedMessage);
            }else{
                if(httpe == null) {
                    irequest.OnError(responsedMessage);
                }else{
                    irequest.OnHttpError(httpe);
                }
            }
        }
    }

    /**
     *
     */
    private class httpgetrequest extends AsyncTask<Void,Integer,String> {

        String URL="";
        boolean ERR_NET=false;
        HttpRequest irequest;
        Exception httpe=null;


        CoreHttpResponsed responsedMessage = new CoreHttpResponsed();

        public httpgetrequest(String URL,HttpRequest irequest){

            this.URL=URL;
            this.irequest = irequest;
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

                if(responsedMessage.$Code != 200) ERR_NET =true;

            }
            catch (ClientProtocolException e) {

                ERR_NET =true;
                httpe = e;

            } catch (IOException e) {

                ERR_NET =true;
                httpe = e;
            }



            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(!ERR_NET){
                irequest.OnSuccess(responsedMessage);
            }else{
                if(httpe == null) {
                    irequest.OnError(responsedMessage);
                }else{
                    irequest.OnHttpError(httpe);
                }
            }
        }
    }

    private class httpdeleterequest extends AsyncTask<Void,Integer,String> {

        String URL="";
        boolean ERR_NET=false;
        HttpRequest irequest;
        Exception httpe=null;


        CoreHttpResponsed responsedMessage = new CoreHttpResponsed();

        public httpdeleterequest(String URL,HttpRequest irequest){

            this.URL=URL;
            this.irequest = irequest;
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

                if(responsedMessage.$Code != 200) ERR_NET =true;

            }
            catch (ClientProtocolException e) {

                ERR_NET =true;
                httpe = e;

            } catch (IOException e) {

                ERR_NET =true;
                httpe = e;
            }



            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(!ERR_NET){
                irequest.OnSuccess(responsedMessage);
            }else{
                if(httpe == null) {
                    irequest.OnError(responsedMessage);
                }else{
                    irequest.OnHttpError(httpe);
                }
            }
        }
    }


    private class httpputrequest extends AsyncTask<Void,Integer,String> {
        HashMap<String,String> postdata=new HashMap<String,String>();
        String URL="";
        boolean ERR_NET=false;
        HttpRequest irequest;
        Exception httpe=null;
        CoreHttpResponsed responsedMessage = new CoreHttpResponsed();



        public httpputrequest(String URL,HashMap<String,String> postdata,HttpRequest irequest){

            this.URL=URL;
            this.postdata=postdata;
            this.irequest = irequest;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPut request = new HttpPut(URL);

            try{


                Mentity entity = new Mentity(
                        new Mentity.ProgressListener() {

                            @Override
                            public void transferred(long num) {

                            }
                        });


                for (Map.Entry<String,String> post : postdata.entrySet()) {

                    entity.addPart(post.getKey(),new StringBody(post.getValue()));

                }

                request.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(request);
                HttpEntity r_entity = response.getEntity();
                responsedMessage.$Data =  inputStreamToString(response.getEntity().getContent());
                responsedMessage.$Code = response.getStatusLine().getStatusCode();
                if(responsedMessage.$Code != 200) ERR_NET =true;

            }
            catch (ClientProtocolException e) {
                ERR_NET =true;

                httpe = e;

            } catch (IOException e) {
                ERR_NET =true;

                httpe = e;
            }



            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(!ERR_NET){
                irequest.OnSuccess(responsedMessage);
            }else{
                if(httpe == null) {
                    irequest.OnError(responsedMessage);
                }else{
                    irequest.OnHttpError(httpe);
                }
            }
        }
    }

    private class httpupload extends AsyncTask<Void,Integer,String>{
        HashMap<String,String> postdata=new HashMap<String,String>();
        String URL="";
        HttpFileRequest ifilerequest = null;
        File file;
        boolean ERR_NET=false;
        Exception httpe=null;
        CoreHttpResponsed responsedMessage = new CoreHttpResponsed();


        /**
         *
         * @param URL
         * @param file
         * @param postdata
         * @param ifilerequest
         */
        public httpupload(String URL, File file, HashMap<String,String> postdata,HttpFileRequest ifilerequest){

            this.URL=URL;
            this.postdata=postdata;
            this.ifilerequest = ifilerequest;
            this.file=file;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            ifilerequest.OnProgress(values[0]);
        }

        @Override
        protected String doInBackground(Void... params) {


            String responseString = null;
            CoreHttpResponsed responsedMessage = new CoreHttpResponsed();
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(URL);


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

                responsedMessage.$Data =  inputStreamToString(response.getEntity().getContent());
                responsedMessage.$Code = response.getStatusLine().getStatusCode();
                if(responsedMessage.$Code != 200) ERR_NET =true;

            }
            catch (ClientProtocolException e) {
                httpe = e;
                ERR_NET = true;

            } catch (IOException e) {
                httpe = e;
                ERR_NET = true;
            }

            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(!ERR_NET){
                ifilerequest.OnSuccess(responsedMessage);
            }else{
                if(httpe == null) {
                    ifilerequest.OnError(responsedMessage);
                }else{
                    ifilerequest.OnHttpError(httpe);
                }
            }
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

    /**
     *
     * @param URL
     * @param POSTDATA
     * @param irequest
     */
    public void $PostRequest( String URL, HashMap<String,String> POSTDATA, HttpRequest irequest){

        new httppostrequest(URL,POSTDATA,irequest).execute();

    }

    /**
     *
     * @param URL
     * @param request
     */
    public void $GetRequest(String URL, HttpRequest request){

        new httpgetrequest(URL, request).execute();

    }

    /**
     *
     * @param URL
     * @param request
     */
    public void $DeleteRequest( String URL, HttpRequest request){

        new httpdeleterequest(URL,request).execute();

    }

    /**
     *
     * @param URL
     * @param POSTDATA
     * @param irequest
     */
    public void $PutRequest( String URL, HashMap<String,String> POSTDATA, HttpRequest irequest){

        new httpputrequest(URL,POSTDATA,irequest).execute();

    }


    /**
     *
     * @param URL
     * @param FILE
     * @param POSTDATA
     * @param ifilerequest
     */
    public void $Upload(String URL,File FILE,HashMap<String,String> POSTDATA,HttpFileRequest ifilerequest){

        new httpupload(URL,FILE,POSTDATA,ifilerequest).execute();

    }

    /**
     *
     * @param URL_
     * @param DistPath
     * @param ifilerequest
     */
    public void $Download(final String URL_, final String DistPath, final HttpFileRequest ifilerequest){
        final File root = new File(DistPath);




        new AsyncTask<String, Integer,String>(){

            boolean ERR_NET=false;
            Exception httpe = null;
            CoreHttpResponsed responsedMessage = new CoreHttpResponsed();
            byte[] downloadedFile = null;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                ifilerequest.OnProgress(values[0]);
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

                    responsedMessage.$Code = connection.getResponseCode();

                    //if response code is not 200 then get the response as string
                    if(responsedMessage.$Code != 200){

                        responsedMessage.$Data = connection.getResponseMessage();
                        ERR_NET = true;

                    }

                    input = connection.getInputStream();
                    output = new FileOutputStream(root);
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
                        if (fileLength > 0) {
                            // only if total length is known
                            publishProgress((int) (total * 100 / fileLength));

                        }

                        output.write(data, 0, count);

                    }

                     responsedMessage.$FileContent = data;

                }catch (IOException e){

                    httpe = e;
                    ERR_NET = true;

                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(!ERR_NET){
                    ifilerequest.OnSuccess(responsedMessage);
                }else{
                    if(httpe == null) {
                        ifilerequest.OnError(responsedMessage);
                    }else{
                        ifilerequest.OnHttpError(httpe);
                    }
                }
            }
        }.execute();

    }




    // FILE ----------------------------------------------------------------------------

    /**
     *
     * @param PathName
     * @param FOLDER
     * @return
     */
    public File $CreateFolder(String PathName,String FOLDER){

        File folder = new File(PathName + FOLDER);
        if(!folder.exists()){

            folder.mkdir();
        }
        return folder;

    }

    /**
     *
     * @param PathName
     * @param srcFile
     * @param srcDST
     * @return
     */
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

    /**
     *
     * @param src
     * @param dst
     * @return
     */
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

    /**
     *
     * @param dir
     * @return
     */
    public boolean $CleanDirectory(String dir) {

        try {
            FileUtils.cleanDirectory(new File(dir));
            return true;
        } catch (IOException e) {
            Log.e("CORE $CleanDirectory",e.toString());
            return false;
        }

    }

    /**
     *
     * @param dir
     * @return
     */
    public boolean $DeleteDirectory(String dir) {

        try {
            FileUtils.deleteDirectory(new File(dir));
            return true;
        } catch (IOException e) {
            Log.e("CORE $DeleteDirectory",e.toString());
            return false;
        }

    }

    /**
     *
     * @param PathName
     * @param FILENAME
     * @return
     */
    public Boolean $DeleteFile(String PathName,String FILENAME){
        String output="";
        File folder = new File(PathName+"");

        if(folder.exists()) {

            File file = new File(folder,FILENAME);
            return file.delete();

        }
        return false;
    }


    /**
     *
     * @param FILE
     * @param PathName
     * @param FILENAME
     * @return
     */
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

    /**
     *
     * @param PathName
     * @param FILENAME
     * @return
     */
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


    /**
     *
     * @param PathName
     * @param FILENAME
     * @param NL
     * @return
     */
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


    /**
     *
     * @param PathName
     * @param FILENAME
     * @param CONTENT
     * @param APPEND
     * @return
     */
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


    /**
     *
     * @param strJSON
     * @return
     */
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

    /**
     *
     * @param STRING
     * @return
     */
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

    /**
     *
     * @param JSON
     * @param KEY
     * @return
     */
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

    /**
     *
     * @param JSON
     * @param KEY
     * @return
     */
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


    /**
     *
     * @param JSON
     * @param KEY
     * @return
     */
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


    /**
     *
     * @param JSON
     * @param KEY
     * @return
     */
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


    /**
     *
     * @param JSON
     * @param KEY
     * @return
     */
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

    /**
     *
     * @param JSON
     * @param KEY
     * @return
     */
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

    /**
     *
     * @param array
     * @param Index
     * @return
     */
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

    /**
     *
     * @param array
     * @param Index
     * @return
     */
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


    /**
     *
     * @param array
     * @param Index
     * @return
     */
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


    /**
     *
     * @param array
     * @param Index
     * @return
     */
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

    /**
     *
     * @param array
     * @param Index
     * @return
     */
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

    /**
     *
     * @param array
     * @param Index
     * @return
     */
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

    /**
     *
     * @param v
     * @param e
     */
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

    /**
     *
     * @param v
     * @param e
     */
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

    /**
     *
     * @param v
     * @param e
     */
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


    /**
     *
     * @param v
     * @param e
     */
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


    /**
     *
     * @return
     */
    public Boolean $HasInternetAccess(){

        ConnectivityManager connectivityManager = (ConnectivityManager) app.getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    /**
     *
     * @param IMAGEVIEW
     * @param URL
     * @param ROUNDPX
     */
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

    /**
     * utils
     */

    public AlertDialog.Builder $Alert(String message,final Runnable r){

        AlertDialog.Builder abuilder = new AlertDialog.Builder(app);
        abuilder.setMessage(message);
        abuilder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                 if(r != null) r.run();
            }
        });


        return abuilder;


    }


    public AlertDialog.Builder $Dialog(String message,final String PositiveText,final String NegativeText,final Dialog d){

        AlertDialog.Builder abuilder = new AlertDialog.Builder(app);
        abuilder.setMessage(message);
        abuilder.setPositiveButton(PositiveText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                d.OnPositiveClick(dialog,which);
            }
        });

        abuilder.setPositiveButton(NegativeText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                d.OnPositiveClick(dialog,which);
            }
        });


        return abuilder;


    }




}
