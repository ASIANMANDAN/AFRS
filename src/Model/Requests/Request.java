package Model.Requests;

import java.util.ArrayList;

/**
 * An interface to define behavior for the different kinds of requests.
 *
 * @author Daniel Wang dcw2772@rit.edu
 * @author Lindsey Ferretti ljf6974@rit.edu
 * @author Elijah Cantella edc8230@rit.edu
 * @author Meghan Johnson meg5228@rit.edu
 */
public interface Request {

    ArrayList execute();
    void setRequestInfo(ArrayList<String> requestInfo);
}
