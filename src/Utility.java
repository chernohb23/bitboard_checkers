import java.util.ArrayList;
public class Utility {
    public static String toBinaryString(int value){                          // method that converts the decimal value of a number into a binary representation
        StringBuilder binaryString = new StringBuilder(32);
        for(int i = 31; i >= 0; i--){
            binaryString.append((value >> i) & 1);
        }
        return binaryString.toString();
    }
    public static String toHexString(int value){            // method that converts  the decimal value of a number into a hexadecimal representation
        return String.format("%08X", value);
    }
    public static int getBit (int value, int position){      // method that gets the bit of a number at a specific position
        return (value >> position) & 1;
    }
    public static String toDecimalString(String value){        // method that converts the binary representation of a number into its decimal value
        ArrayList<String> binary = new ArrayList<>();
        for(int i = 0; i < value.length(); i++){
            char c = value.charAt(i);
            String s = Character.toString(c);
            binary.add(s);
        }
        ArrayList<Integer> results = new ArrayList<>();
        int multi = 1;
        for(int i = binary.size() - 1; i >= 0; i--){
            int num = Integer.parseInt(binary.get(i));
            int res = num * multi;
            results.add(res);
            multi *= 2;
        }
        int sum = 0;
        for (int hold : results) {
            sum += hold;
        }
        return Integer.toString(sum);
    }
    public static int shiftLeft(int value, int positions){           // method that shifts the bits of a decimal value left by whatever number of positions (multiplication)
        return value << positions;
    }
    public static int shiftRight(int value, int positions){        // method that shifts the bits of a decimal value right by whatever number of positions (division)
        return value >> positions;
    }
    public static int clearBit(int value, int position){        // method that clears the bit of a decimal value at a specific position, making it 0
        return value & ~(1 << position);
    }
    public static int toggleBit(int value, int position){             // method that changes the bit from 1 to 0 or 0 to 1 depending on the bit value
        return value ^ 1 << position;
    }
    public static int setBit(int value, int position){              // method that sets the bit of a decimal value at a specific position, making it 1
        return value | 1 << position;
    }
    public static int bitAddition(int a, int b){            // method calculates the sum of two binary numbers
        while(b != 0){
            int carry = a & b;
            a = a ^ b;
            b = carry << 1;
        }
        return a;
    }
    public static int bitSubtraction(int a, int b){        // method calculates the difference of two binary numbers
        b = ~b + 1; // two's complement
        return bitAddition(a, b);
    }
    public static int getBitPosition(int row, int column) {
        int product = shiftLeft(row, 2);
        int quotient = shiftRight(column, 1);
        return bitAddition(product, quotient);                             // same as (row * 4 + column / 2)
    }
}