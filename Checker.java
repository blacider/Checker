/* 
 * AP(r) Computer Science GridWorld Case Study:
 * Copyright(c) 2005-2006 Cay S. Horstmann (http://horstmann.com)
 *
 * This code is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * @author Cay Horstmann
 * @author Chris Nevison
 * @author Barbara Cloud Wells
 */
import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.actor.Flower;
import info.gridworld.grid.Location;
import java.util.ArrayList;
import java.awt.Color;
/**
 * A <code>BoxBug</code> traces out a square "box" of a given size. <br />
 * The implementation of this class is testable on the AP CS A and AB exams.
 */
public class Checker extends Critter
{
    private boolean canJumper;
    /**
     * Processes the elements of <code>actors</code>. New actors may be added
     * to empty locations. Implemented to "eat" (i.e. remove) selected actors
     * that are not rocks or critters. Override this method in subclasses to
     * process actors in a different way. <br />
     * Postcondition: (1) The state of all actors in the grid other than this
     * critter and the elements of <code>actors</code> is unchanged. (2) The
     * location of this critter is unchanged.
     * @param actors the actors to be processed
     */
    public Checker(Color c) {
        setColor(c);
    }
    public Checker() {
    }
    public void processActors(ArrayList<Actor> actors)
    {
    }
    public ArrayList<Location> getMoveLocations()
    {
        ArrayList<Actor> actors = getActors();
        ArrayList<Location> locs = new ArrayList<Location>();
        for (Actor a : actors) {
            Location loc = getLocationJumper(a);
            if (loc != null) {
                locs.add(loc);
            }
        }
        if (locs.size() == 0) {
            canJumper = false;
            return super.getMoveLocations();
        } else {
            canJumper = true;
            return locs;
        }
    }
    private Location getLocationJumper(Actor actor) {
        int direction = getLocation().getDirectionToward(actor.getLocation());
        Location loc = actor.getLocation().getAdjacentLocation(direction);
        if (getGrid().isValid(loc)) {
            Actor a = getGrid().get(loc);
            if (a == null) {
                return loc;
            }
        }
        return null;
    }
    /**
     * Turns towards the new location as it moves.
     */
    public void makeMove(Location loc)
    {
        if (canJumper) {
            int direction = getLocation().getDirectionToward(loc);
            Actor a = getGrid().get(getLocation().getAdjacentLocation(direction));
            if (a instanceof Flower) {
                setColor(a.getColor());
                a.removeSelfFromGrid();
            }
        } else {
            Color c = getColor();
            int red = (int) (c.getRed() * 0.95);
            int green = (int) (c.getGreen() * 0.95);
            int blue = (int) (c.getBlue() * 0.95);

            setColor(new Color(red, green, blue));
        }
        
        setDirection(getLocation().getDirectionToward(loc));
        super.makeMove(loc);
    }
}
