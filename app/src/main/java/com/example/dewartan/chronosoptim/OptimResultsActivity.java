package com.example.dewartan.chronosoptim;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * Created by Prakhar on 12/4/2015.
 */

public class OptimResultsActivity extends ClientDevice{
    private OptimResultsAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.optim_results);

        Team team=getIntent().getParcelableExtra("team");
        String teamId=team.getId();

        SyncBuffer syncBuffer=new SyncBuffer(this);
        ListView list=(ListView) findViewById(R.id.list);
//        Log.w("oract", team.getMembers());
        if(team.getSize()>1){
            adapter = new OptimResultsAdapter(team.getSize());
            list.setAdapter(adapter);
            syncBuffer.send("&x=optim&id=" + teamId);
        }
//        adapter.set("12/16,9:30,10:30,0;12/17,10:00,11:00,0");
    }


    public void select(View view) {
        Intent backIntent=new Intent();
        backIntent.putExtra("time",(String)((TextView)view.findViewById(R.id.time)).getText());
        backIntent.putExtra("date",(String)((TextView)view.findViewById(R.id.date)).getText());
        setResult(RESULT_OK,backIntent);
        finish();
    }


    public void uponSync(String response){
        // check if appropriate response
        adapter.set(response);
    }
    public void coverActions(LinkedList<String> actions){}
    public LinkedList<String> recoverActions(){return new LinkedList<>();}

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void cancel(View view){
        setResult(RESULT_CANCELED,null);
        finish();
    }
}

