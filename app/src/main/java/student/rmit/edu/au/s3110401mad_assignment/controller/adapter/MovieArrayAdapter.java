package student.rmit.edu.au.s3110401mad_assignment.controller.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

import student.rmit.edu.au.s3110401mad_assignment.R;
import student.rmit.edu.au.s3110401mad_assignment.model.database.DatabaseHelper;
import student.rmit.edu.au.s3110401mad_assignment.model.Movie;
import student.rmit.edu.au.s3110401mad_assignment.model.MovieModel;
import student.rmit.edu.au.s3110401mad_assignment.model.PartyModel;
import student.rmit.edu.au.s3110401mad_assignment.model.async_task.CursorToStringTask;

/**
 * Created by Michaelsun Baluyos on 25/08/2015.
 *
 *
 */
public class MovieArrayAdapter extends CursorAdapter {
    public static final String MAIN_ROW_SPACE = " ";
    public static final String MAIN_ROW_RATING_OUT_OF_FIVE = "/5";

    public MovieArrayAdapter(Context context, Cursor cursor) {
        super(context, cursor, false);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.movie_row, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView titleView = (TextView) view.findViewById(R.id.title);
        TextView yearView = (TextView) view.findViewById(R.id.year);
        TextView plotView = (TextView) view.findViewById(R.id.plot);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        TextView numPartyView = (TextView) view.findViewById(R.id.num_of_invitees);
        TextView ratingBar = (TextView) view.findViewById(R.id.rating_bar);

        try {
            Movie movie = MovieModel.getSingleton().getMovieById(
                    "tt" + (new CursorToStringTask(cursor,DatabaseHelper.MOVIE_ID).execute().get())
            );
            // Fill in the Views with content
            titleView.setText(movie.getTitle());
            yearView.setText(movie.getYear());
            plotView.setText(movie.getShortPlot());

            Bitmap moviePoster = movie.getPoster();

            if(moviePoster != null) {
                imageView.setImageBitmap(moviePoster);
            }

            ratingBar.setText(context.getString(R.string.main_rating) +
                    MAIN_ROW_SPACE + movie.getRating() + MAIN_ROW_RATING_OUT_OF_FIVE);

            numPartyView.setText(context.getString(R.string.main_num_parties) +
                            MAIN_ROW_SPACE +
                            PartyModel.getSingleton().getPartiesByMovieId(movie.getId()).size()
            );
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
