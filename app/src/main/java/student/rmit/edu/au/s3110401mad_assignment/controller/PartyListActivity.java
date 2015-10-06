package student.rmit.edu.au.s3110401mad_assignment.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.List;

import student.rmit.edu.au.s3110401mad_assignment.R;
import student.rmit.edu.au.s3110401mad_assignment.controller.adapter.PartyListAdapter;
import student.rmit.edu.au.s3110401mad_assignment.model.Party;
import student.rmit.edu.au.s3110401mad_assignment.model.PartyModel;

public class PartyListActivity extends AppCompatActivity {

    private PartyListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_list);

        List<Party> parties = PartyModel.getSingleton().getAllParties();
        listAdapter = new PartyListAdapter(this, parties);
        ((ListView)findViewById(R.id.party_list)).setAdapter(listAdapter);

        if (getSupportActionBar() == null)
            return;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        listAdapter.notifyDataSetChanged();
    }
}