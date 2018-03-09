package com.core.lib;

/**
 * Created by l on 11/01/2018.
 */

public class CoreFile {

  private Droid core;
  public String Storage = "";


  public CoreFile(Droid Core,String Storage){

      this.core = Core;
      this.Storage = Storage;

  }

  public CoreFileFolder $Folder(String FOLDER){

      String[] folders = FOLDER.split("/");

      String fPath ="";

      for (int i=0;i<folders.length;i++){

          fPath+=folders[i]+"/";
          core.$CreateFolder(Storage, fPath);

      }


     return new CoreFileFolder(this.Storage,fPath,this.core);

  }

}
