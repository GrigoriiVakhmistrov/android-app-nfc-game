package com.example.tzadmin.nfc_reader_writer.NET;

/**
 * Created by velor on 6/28/17.
 */

public interface RequestDelegate {
    public void RequestDone(String url, Object result);
    public void TaskDone(Boolean success);
}
