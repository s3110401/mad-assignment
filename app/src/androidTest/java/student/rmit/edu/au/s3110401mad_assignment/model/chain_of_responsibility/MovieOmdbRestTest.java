package student.rmit.edu.au.s3110401mad_assignment.model.chain_of_responsibility;

import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by Michaelsun Baluyos on 3/10/2015.
 *
 *
 */
public class MovieOmdbRestTest extends TestCase {

    @SmallTest
    public void testHasEntry() throws Exception {
        MovieOmdbRest movieOmdbRest = new MovieOmdbRest();

        Log.e("Passing test", "Man");
        Assert.assertTrue("Should find any query with man", movieOmdbRest.hasEntry("man"));
        Log.e("Should pass test", "Man of Steel");
        Assert.assertTrue("Should find any query with Man of Steel", movieOmdbRest.hasEntry("man"));
        Log.e("Failed test", "manzzzzz");
        Assert.assertFalse(movieOmdbRest.hasEntry("manzzzzz"));
    }

    @SmallTest
    public void testGetJsonFromHttp() throws Exception {
        MovieOmdbRest movieOmdbRest = new MovieOmdbRest();
        String shortJson = movieOmdbRest.getJsonFromHttp(MovieOmdbRest.OMDB_URL_SHORT_BY_ID,"tt0770828");
        String fullJson = movieOmdbRest.getJsonFromHttp(MovieOmdbRest.OMDB_URL_FULL_BY_ID, "tt0770828");

//        Log.e("Get Short Json", shortJson);
//        Log.e("Get Full Json", shortJson);

        Log.e("Ayy lmao Process short", this.getClass().getSimpleName());
        JSONObject objShort = (JSONObject) new JSONTokener(shortJson).nextValue();
        Log.d("Get Short Plot", objShort.getString("Plot"));

        Log.e("Ayy lmao Process full", this.getClass().getSimpleName());
        JSONObject objFull = (JSONObject) new JSONTokener(fullJson).nextValue();
        Log.d(".Get Full Plot", objFull.getString("Plot"));

        Assert.assertNotNull(shortJson);
        Assert.assertNotNull(fullJson);
        Assert.assertFalse("Plots should not be the same",
                objFull.getString("Plot").equals(
                        objShort.getString("Plot")));
        Assert.assertFalse("Json not be the same", shortJson.equals(fullJson));
    }
}