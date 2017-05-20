package com.vpaliy.bakingapp.data.local;


import android.content.UriMatcher;
import android.net.Uri;
import android.util.SparseArray;

public class RecipeMatchUri {


    private UriMatcher uriMatcher;
    private SparseArray<RecipeMatchEnum> codeMap;

    public RecipeMatchUri(){
        buildUriMatcher();
        buildUriMap();
    }

    private void buildUriMatcher(){
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        RecipeMatchEnum[] movieUriEnum=RecipeMatchEnum.values();
        for(RecipeMatchEnum uriEnum:movieUriEnum){
            uriMatcher.addURI(RecipeContract.CONTENT_AUTHORITY,uriEnum.path,uriEnum.code);
        }
    }

    private void buildUriMap(){
        codeMap=new SparseArray<>();
        RecipeMatchEnum[] movieUriEnum=RecipeMatchEnum.values();
        for(RecipeMatchEnum uriEnum:movieUriEnum){
            codeMap.put(uriEnum.code,uriEnum);
        }
    }

    public RecipeMatchEnum match(Uri uri){
        final int code=uriMatcher.match(uri);
        if(codeMap.get(code)==null){
            throw new UnsupportedOperationException("Unknown uri with code " + code);
        }
        return codeMap.get(code);
    }

    public String getType(Uri uri){
        final int code=uriMatcher.match(uri);
        if(codeMap.get(code)==null){
            throw new UnsupportedOperationException("Unknown uri with code " + code);
        }
        return codeMap.get(code).contentType;
    }


}
