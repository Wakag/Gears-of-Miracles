// Handle cross reference table for EBNF productions
// P.D. Terry, modified by KL Bradshaw 2024 

// Complete this skeleton for Prac 6, or develop your own version

/* Rebecca HUfkie - g22h2807
   Amogelang Mphela - g22a7399
   Wakanaka Gurure - g22g8069
   Derrick Asiedu Aboagye - g22a */ 

package EBNF;

import java.util.*;
import library.*;

  class Entry {                      // Cross reference table entries
    public String name;              // The identifier itself
    public ArrayList<Integer> refs;  // Line numbers where it appears
    public Entry(String name) {
      this.name = name;
      this.refs = new ArrayList<Integer>();
    }
  } // Entry

  class Table {
    static ArrayList<Entry> list = new ArrayList<Entry> (); 
    
    public static void clearTable() {
    // Clears cross-reference table
    list = new ArrayList<Entry> ();
    } // clearTable

    public static void addRef(String name, boolean declared, int lineRef) {
    // What do you suppose is the purpose of the "declared" parameter?
      int i = 0;
      while (i < list.size() && !name.equals(list.get(i).name)) i++;   //increment i until the token value (name) is equal to a name in list and i is less than list size
      if (i >= list.size()) {  
        Entry entry = new Entry(name); //create new entry for token value given
        if (declared){                //if declared is true, lineRef should be negative 
          entry.refs.add(-lineRef);
        } else { 
          entry.refs.add(lineRef);   //add reference as postive lineRef
        }
        list.add(entry); //add new entry to list
      } else { //name found, updates existing entry 
        Entry entry = list.get(i);
        if (declared) {
            if (entry.refs.isEmpty() || entry.refs.get(0) > 0) {
              entry.refs.add(-lineRef); //add new declaration
            }           
        } else {
          if (!entry.refs.contains(lineRef)) {
            entry.refs.add(0, lineRef); //change the start of list to first reference 
          }
        }
      }
    } // addRef

    public static void printTable() {
    // Prints out all references in the table (eliminate duplicates line numbers)

    Parser.output.writeLine("Cross Reference Listing:");
    for (int i = 0; i < list.size(); i++){
      //prints name of terminals and non-terminals with line references 
      Entry ent = list.get(i);
      Parser.output.write(ent.name, -16); //prints name into a output file

      StringBuilder lineNum = new StringBuilder();
      for (int j = 0; j < ent.refs.size(); j ++){
        int line = ent.refs.get(j);
        lineNum.append(line).append(" "); //prints all line references for name until end of list is reached
      }
      Parser.output.writeLine(lineNum.toString().trim(), -18); //format output
    }
    Parser.output.writeLine("The following are terminals, or undefined non-terminals:");
    StringBuilder undefined = new StringBuilder();
    for(int k = 0; k < list.size(); k++){
      Entry e = list.get(k);
      int last = e.refs.size() - 1 ; //gets last element of list
      if (e.refs.size() == 1 || e.refs.get(last) > 0){ //print terminals/undefined non-terminals - have list size of 1 or last element of list is greater than 0 
        if(e.refs.get(0) > 0 ){ //if list size is 1, element in list should be positive
          undefined.append(e.name).append(" ");
        }
      }
    }
    Parser.output.writeLine(undefined.toString().trim());
    } // printTable

  } // Table
