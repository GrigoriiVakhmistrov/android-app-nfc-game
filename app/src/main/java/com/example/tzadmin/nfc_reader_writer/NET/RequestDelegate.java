package com.example.tzadmin.nfc_reader_writer.NET;

/**
 * Created by velor on 6/28/17.
 */

public interface RequestDelegate {
    public void RequestDone(String url, String body, Object backParam, Integer stage);
    public void TaskDone(Integer success);
}
