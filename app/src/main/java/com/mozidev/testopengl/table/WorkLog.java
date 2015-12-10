package com.mozidev.testopengl.table;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.From;
import com.activeandroid.query.Select;
import com.mozidev.testopengl.Constants;

import java.util.List;

/**
 * Created by Igor
 * on 09.12.2015
 */
@Table(name = "WorkLog")
public class WorkLog extends Model {


    @Column(name = "time")
    public long time;

    @Column(name = "message")
    public String message;


    public long getTime() {
        return time;
    }


    public void setTime(long time) {
        this.time = time;
    }


    public String getMessage() {
        return message;
    }


    public void setMessage(String message) {
        this.message = message;
    }


    public WorkLog(long time, String message) {
        this.time = time;
        this.message = message;
    }


    public WorkLog() {
    }


    public static void clear(){
        new Delete().from(WorkLog.class).execute();
    }


    public static List<WorkLog> getEntityByTime(long time) {
        List<WorkLog> list;
        try {
            From from = new Select().from(WorkLog.class);
            if (from.exists()) list = from.where("time >= ?", time).execute();
            else return null;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return list;
    }



    public static List<WorkLog> getAll(){
        List<WorkLog> list;
        try {
            From from = new Select().from(WorkLog.class);
            if (from.exists()) list = from.execute();
            else return null;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return list;
    }

    public static void checkLog(){
        List<WorkLog> all = getAll();
        if(all != null){
            int size = all.size();
            if(size > Constants.MAX_ENTRIES_IN_LOG) {

                new Delete().from(WorkLog.class).where("id < ?", size - Constants.MAX_ENTRIES_IN_LOG).execute();
            }
        }

    }
}