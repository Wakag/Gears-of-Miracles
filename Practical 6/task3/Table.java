// Compute medal tallies for countries in Olympics productions
// K. Bradshaw, Rhodes University, 2024

// Complete this skeleton for Task 3 Prac 6, or develop your own version

/* Rebecca Hufkie - g22h2807 */

package Olympics;

import java.util.*;
import library.*;

  class Entry {                      // Country table entries
    public String name;              // The country name itself
    public int total;  				 // Total medals
    public Entry(String name) {
      this.name = name;
      this.total = 0;                // no medals yet
    }
  } // Entry

  class Table {
    static ArrayList<Entry> list = new ArrayList<Entry> (); 

    public static void clearTable() {
    // Clears cross-reference table
    list = new ArrayList<Entry> ();
    } // clearTable

    public static void incMedal(String name) {
    // Increment the medal total for country "name" -- if country doesn't exist create it.
      int i = 0;
      while (i < list.size() && !name.equals(list.get(i).name)) i++; //check if name of country already exists in table
      if (i >= list.size()){ 
        Entry entry = new Entry(name); //add new country entry if not found
        entry.total++; //increment total medals
        list.add(entry); //add country to table
      } else {
        Entry entry = list.get(i); //if found, get country name entry
        entry.total++; //increment total medals
      }
    } // incMedal
	

    public static void printTable() {
    // Prints out all countries with their medal totals
      System.out.printf("%-20s %s%n", "Country","Total Medals"); //format headings
      for (int i = 0; i < list.size(); i++){ //loop through list to get names of countries and total values
        Entry ent = list.get(i);
        System.out.printf("%-20s %d%n", ent.name, ent.total); //format output of country names and totals
      }
    } // printTable

  } // Table
