package Controller.Factories;

import Model.Databases.Database;

/**
 * A DatabaseFactory must be able to make a Database of some kind. When requested,
 * it should be able to make and return said Database.
 *
 * @author Elijah Cantella - edc8230@rit.edu
 */
public interface DatabaseFactory {

    Database makeDatabase();
}
