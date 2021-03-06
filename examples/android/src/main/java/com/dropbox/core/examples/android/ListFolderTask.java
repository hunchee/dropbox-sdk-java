package com.dropbox.core.examples.android;

import android.os.AsyncTask;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.DbxFiles;

/**
 * Async task to list items in a folder
 */
class ListFolderTask extends AsyncTask<String, Void, DbxFiles.ListFolderResult> {

    private final DbxClientV2 mDbxClient;
    private final Callback mCallback;
    private Exception mException;

    public interface Callback {
        void onDataLoaded(DbxFiles.ListFolderResult result);

        void onError(Exception e);
    }

    public ListFolderTask(DbxClientV2 dbxClient, Callback callback) {
        mDbxClient = dbxClient;
        mCallback = callback;
    }

    @Override
    protected void onPostExecute(DbxFiles.ListFolderResult result) {
        super.onPostExecute(result);

        if (mException != null) {
            mCallback.onError(mException);
        } else {
            mCallback.onDataLoaded(result);
        }
    }

    @Override
    protected DbxFiles.ListFolderResult doInBackground(String... params) {
        try {
            return mDbxClient.files.listFolder(params[0]);
        } catch (DbxException e) {
            mException = e;
        }

        return null;
    }
}
