package student.rmit.edu.au.s3110401mad_assignment.model.chain_of_responsibility;

import android.content.Context;

import java.util.ArrayList;

import student.rmit.edu.au.s3110401mad_assignment.model.database.PartyDatabaseManager;
import student.rmit.edu.au.s3110401mad_assignment.model.Party;
import student.rmit.edu.au.s3110401mad_assignment.model.PartyModel;

public class PartyRetrieveDB extends PartyMemoryManagementHandler {

    public PartyRetrieveDB(Context context, PartyModel partyModel) {
        super(context, partyModel);
    }

    @Override
    protected boolean hasEntry() {
        PartyDatabaseManager edm =
                new PartyDatabaseManager(context.getApplicationContext());
        edm.open();

        if(edm.getAllParties().size()==0) {
            edm.close();
            return false;
        }

        filteredParty = new ArrayList<>();

        for(Party party : edm.getAllParties()) {
            partyModel.addParty(party);
            filteredParty.add(party);
        }

        edm.close();

        return (filteredParty.size()>0);
    }
}
