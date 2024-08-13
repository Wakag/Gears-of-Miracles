  // Do learn to insert your names and a brief description of
  // what the program is supposed to do!

  // This is a skeleton program for developing a parser for checking PVMlike files
  // KL Bradshaw 2021

  import java.util.*;
  import library.*;

  class Token {
    public int kind;
    public String val;

    public Token(int kind, String val) {
      this.kind = kind;
      this.val = val;
    }

  } // Token

  class PVMChecker1 {

    // +++++++++++++++++++++++++ File Handling and Error handlers ++++++++++++++++++++

    static InFile input;
    static OutFile output;

    static String newFileName(String oldFileName, String ext) {
    // Creates new file name by changing extension of oldFileName to ext
      int i = oldFileName.lastIndexOf('.');
      if (i < 0) return oldFileName + ext; else return oldFileName.substring(0, i) + ext;
    }  // newFileName

    static void reportError(String errorMessage) {
    // Displays errorMessage on standard output and on reflected output
      System.out.println(errorMessage);
      output.writeLine(errorMessage);
    } // reportError

    static void abort(String errorMessage) {
    // Abandons parsing after issuing error message
      reportError(errorMessage);
      output.close();
      System.exit(1);
    } // abort

    // +++++++++++++++++++++++  token kinds enumeration +++++++++++++++++++++++++

    static final int
      noSym        =  0,
      EOFSym       =  1,
      identSym     =  2,
      numSym       =  3,
      beginSym     =  4,
      endSym       =  5,
      addSym       =  6,
      labelSym     =  7,
      dspSym       =  8,
      ceqSym       =  9,
      stoSym       =  10,
      ldaSym       =  11,
      ldvSym       =  12,
      ldcSym       =  13, 
      cneSym       =  14,
      prniSym      =  15,
      inpiSym      =  16,
      bzeSym       =  17,
      brnSym       =  18,
      periodSym    =  19,
      eolSym       =  20,
      negSym       =  21;
      // and others like this

    // +++++++++++++++++++++++++++++ Character Handler ++++++++++++++++++++++++++

    static final char EOF = '\0';
    static boolean atEndOfFile = false;

    // Declaring ch as a global variable is done for simplicity - global variables
    // are not always a good thing

    static char ch;    // look ahead character for scanner

    static void getChar() {
    // Obtains next character ch from input, or CHR(0) if EOF reached
    // Reflect ch to output
      if (atEndOfFile) ch = EOF;
      else {
        ch = input.readChar();
        atEndOfFile = ch == EOF;
        if (!atEndOfFile) output.write(ch);
      }
    } // getChar


    // +++++++++++++++++++++++++++++++ Scanner ++++++++++++++++++++++++++++++++++

    // Declaring sym as a global variable is done for simplicity - global variables
    // are not always a good thing

    static Token sym;

    static void getSym() {
    // Scans for next sym from input
      while (ch > EOF && ch <= ' ' && ch != '\n') getChar();  // need to check EOL, so can't ignore it
      StringBuilder symLex = new StringBuilder();
      int symKind = noSym;


      // over to you!
      if (Character.isLetter(ch)) {
        do {
          symLex.append(ch); 
          getChar();
        } while (Character.isLetterOrDigit(ch));

        if (ch == ':'){
          symKind = labelSym;
          symLex.append(ch);
          getChar();
        } else {

      String check = symLex.toString().toUpperCase();
      switch (check) {    
        case "BEGIN" :
          symKind =  beginSym; 
          getChar() ;
          break ;
        case "END" :
          symKind =  endSym; 
          getChar() ;
          break ;
        case "ADD" :
          symKind =  addSym; 
          getChar() ;
          break ;  
        case "CEQ" :
          symKind = ceqSym ;
          getChar();
          break ; 
        case "CNE" :
          symKind = cneSym ;
          getChar();
          break ;
        case "LDV" :
          symKind = ldvSym ;
          getChar();
          break ;
        case "STO" :
          symKind = stoSym ;
          getChar();
          break ;
        case "PRNI" :
          symKind = prniSym ;
          getChar();
          break ;
        case "INPI":
          symKind = inpiSym ;
          getChar();
          break ;
        case "DSP" :
          symKind = dspSym ;
          getChar() ;
          break; 
        case "LDC" :
          symKind = ldcSym ;
          getChar();
          break; 
        case "LDA" :
          symKind = ldaSym ;
          getChar();
          break;
        case "BZE" :
          symKind = bzeSym ; 
          getChar() ;
          break;
        case "BRN" :
          symKind = brnSym ;
          getChar() ;
          break;
        default :
          symKind = identSym ;
        }
      } 
      } else if (Character.isDigit(ch)){
        do {
          symLex.append(ch);
          getChar();   
        } while (Character.isDigit(ch));
        symKind = numSym;
      }
       else {
        symLex.append(ch);
        switch(ch) {
          case EOF:
            symLex = new StringBuilder("EOF");
            symKind = EOFSym;
            break;
          case '\n':
            symKind = eolSym ;
            getChar();
            break; 
          case '+':
            symKind = addSym;
            getChar();
            break;
          case '-':
            symKind = negSym;
            getChar();
            break; 
          case '.':
            symKind = periodSym;
            getChar() ;
            break;
          default:
            symKind = noSym;
            getChar();
            break;    
        }
      } 
      sym = new Token(symKind, symLex.toString());
    } // getSym

  /*  ++++ Commented out for the moment

    // +++++++++++++++++++++++++++++++ Parser +++++++++++++++++++++++++++++++++++

    static void accept(int wantedSym, String errorMessage) {
    // Checks that lookahead token is wantedSym
      if (sym.kind == wantedSym) getSym(); else abort(errorMessage);
    } // accept

    static void accept(IntSet allowedSet, String errorMessage) {
    // Checks that lookahead token is in allowedSet
      if (allowedSet.contains(sym.kind)) getSym(); else abort(errorMessage);
    } // accept
    */
  //  static void PVMlike() {}

  /* ++++++ */

    // +++++++++++++++++++++ Main driver function +++++++++++++++++++++++++++++++

    public static void main(String[] args) {
      // Open input and output files from command line arguments
      if (args.length == 0) {
        System.out.println("Usage: PVMChecker FileName");
        System.exit(1);
      }
      input = new InFile(args[0]);
      output = new OutFile(newFileName(args[0], ".out"));

      getChar();                                  // Lookahead character

  //  To test the scanner we can use a loop like the following:

      do {
        getSym();                                 // Lookahead symbol
        OutFile.StdOut.write(sym.kind, 3);
        OutFile.StdOut.writeLine(" " + sym.val);
      } while (sym.kind != EOFSym); 

  /*  After the scanner is debugged, comment out lines 127 to 131 and uncomment lines 135 to 138. 
      In other words, replace the code immediately above with this code:

      getSym();                                   // Lookahead symbol
      PVMlike();                                  // Start to parse from the goal symbol
      // if we get back here everything must have been satisfactory
      System.out.println("Parsed correctly");
  */
      output.close();
    } // main

  } // PVMChecker
