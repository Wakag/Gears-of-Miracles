
// Group Name: Gears of Miracles 
//Rebecca Hufkie - g22h2807
//Wakanaka Gurure - g22g8069
//Amogelang Mphela - g22a7399
//Derrick Asiedu Aboagye - g22A3680

import library.*;


class sievej {

    public static void main(String[] args) {

        final int Max = 49000;
        IntSet primeNums = new IntSet();
        int n, iterations, primes = 0;

        { IO.write("How many iterations? "); iterations = IO.readInt(); }
        { IO.write("Supply largest number to be tested "); n = IO.readInt(); }

        if (n > Max) {
            { IO.write("n too large, sorry"); }
            return;
        }

        int it = 1;
        while (it <= iterations) {
            primes = 0;
            primeNums.clear();
            
            // Creating a set from 2 to n 
            for (int i = 2; i <= n; i++) {
                primeNums.incl(i);
            }

            /
            for (int i = 2; i <= n; i++) {
                
                if (primeNums.contains(i)) {
                    primes++;
                    int k = i + i;
                // Remove all the numbers that are factors of 2
                    while (k <= n) {
                        primeNums.excl(k);
                        k += i;
                    }
                }
            }

            it++;
        }
        {IO.write(primeNums.toString() + "\n");}
        { IO.write(primes); IO.write(" primes"); }
    }
}
