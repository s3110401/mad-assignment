package student.rmit.edu.au.s3110401mad_assignment.controller.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

import student.rmit.edu.au.s3110401mad_assignment.R;
import student.rmit.edu.au.s3110401mad_assignment.controller.PartyEditActivity;
import student.rmit.edu.au.s3110401mad_assignment.model.Contacts;
import student.rmit.edu.au.s3110401mad_assignment.model.Party;
import student.rmit.edu.au.s3110401mad_assignment.model.PartyModel;
import student.rmit.edu.au.s3110401mad_assignment.model.async_task.PartyDeleteDBTask;
import student.rmit.edu.au.s3110401mad_assignment.model.async_task.PartyInviteeQueryDBTask;

import android.content.Context;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;

public class PartyListAdapter extends BaseAdapter {

    private List<Party> partyList = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;

    public PartyListAdapter(Context context,
                            List<Party> eventList) {
        this.partyList = eventList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        if(partyList != null)
            return partyList.size();
        return 0;
    }

    @Override
    public Party getItem(int position) {
        return partyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        private TextView eventTitle;
        private Button deleteButton;
        private TextView eventDate;
        private TextView eventAttendeeCount;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        final Party party = getItem(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_party_row,
                    parent, false);
            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.eventTitle = (TextView) convertView
                .findViewById(R.id.party_list_title_text);

        viewHolder.eventDate = (TextView) convertView
                .findViewById(R.id.party_list_date_text);

        String title = (party.getVenue() != null) ? party.getVenue() : "Event " + position;
        viewHolder.eventTitle.setText(title);

        Calendar datetime = Calendar.getInstance();
        if(party.getDate() != null)
            datetime.setTime(party.getDate().getTime());

        int year = datetime.get(Calendar.YEAR);
        int monthOfYear = datetime.get(Calendar.MONTH);
        int dayOfMonth = datetime.get(Calendar.DAY_OF_MONTH);
        String stringDate = String.valueOf(year) + "-" + String.valueOf(monthOfYear)
                + "-" + String.valueOf(dayOfMonth);
        viewHolder.eventDate.setText(stringDate);

        viewHolder.eventAttendeeCount = (TextView) convertView
                .findViewById(R.id.party_list_attendee_count);

        try {
            List<Contacts> contactList =
                    new PartyInviteeQueryDBTask(context,party.getId()).execute().get();

            viewHolder.eventAttendeeCount.setText(convertView.getResources()
                    .getString(R.string.party_list_attendee_text) + contactList.size() + "");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        convertView
                .findViewById(R.id.party_list_edit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PartyEditActivity.class);
                intent.putExtra("party_id", party.getId());
                context.startActivity(intent);
            }
        });

        viewHolder.deleteButton = (Button) convertView
                .findViewById(R.id.party_list_delete_button);

        viewHolder.deleteButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                PartyModel.getSingleton().deleteEvent(party.getId());
                new PartyDeleteDBTask(context,getItem(position).getId()).execute();
                partyList.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}