/**
 * The current Reservations within the AFRS system. A reservation can only be added or removed, they can't be
 * changed once instantiated. Every time a Reservation is added or removed this class should notify all observers.
 *
 * @author Dan Wang
 * @author Elijah Cantella - edc8230@g.rit.edu
 */

package Model.Databases;

import Model.Airport;
import Model.ReservationHierarchy.Reservation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;

public class ReservationDatabase extends Observable implements Database {

    // ----------
    // Attributes
    // ----------

    private Collection<Reservation> reservations;

    // -------
    // Methods
    // -------

    /**
     * Create a new ReservationDatabase with no existing Reservations.
     */
    public ReservationDatabase(){
        this.reservations = new ArrayList<Reservation>();
    }

    /**
     * Add one Reservation into the database. Should notify all observers if successful.
     * @param reservation Reservation to be added.
     */
    public void addReservations(Reservation reservation){
        if(!this.reservations.contains(reservation)) {
            reservations.add(reservation);

            // Change Made
            this.setChanged();
            this.notifyObservers();
        }
    }

    /**
     * Get all the Reservations within the database. There may be 0 or more Reservations.
     * @return Collection of all the Reservations within the database.
     */
    public Collection<Reservation> getAllReservations() {
        return this.reservations;
    }

    /**
     * Determines if the passenger has a Reservation.
     * @param passenger String passenger name to check for.
     * @return True if the passenger has a Reservation; False otherwise.
     */
    public boolean hasReservation(String passenger){
        for(Reservation r : reservations) {
            if(r.getPassengerName().equals(passenger)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get all Reservations that a passenger has.
     * @param passenger String passenger name to look for.
     * @return ArrayList of Reservations the passenger has.
     */
    public ArrayList<Reservation> getReservations(String passenger) {
        ArrayList<Reservation> result = new ArrayList<>();
        for(Reservation r: reservations) {
            if(r.getPassengerName().equals(passenger)) {
                result.add(r);
            }
        }
        return result;
    }

    /**
     * Remove a series of Reservations that a passenger has.
     * @param passenger String passenger to look for.
     * @param origin Airport where the Reservation begins.
     * @param destination Airport where the Reservation ends.
     */
    public void removeReservations(String passenger, Airport origin, Airport destination) {
        for(Reservation r: reservations) {
            if(r.getPassengerName().equals(passenger)) {
                if(r.getOrigin().equals(origin) && r.getDestination().equals(destination)) {
                    this.reservations.remove(r);
                    this.setChanged();
                    this.notifyObservers();
                    break;
                }
            }
        }
    }
}
