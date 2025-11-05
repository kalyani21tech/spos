import java.util.*;

class Symbol {
    String name;
    int address;
    Symbol(String name, int address) {
        this.name = name;
        this.address = address;
    }
}

class Literal {
    String literal;
    int address;
    Literal(String literal, int address) {
        this.literal = literal;
        this.address = address;
    }
}

public class Pass1Assembler {
    static int LC=0; 
    static List<Symbol> symbolTable = new ArrayList<>();
    static List<Literal> literalTable = new ArrayList<>();

    static String[] IS = {"ADD", "SUB", "MUL", "MOVER"};
    static String[] REG = {"AREG", "BREG", "CREG", "DREG"};
    static String[] AD = {"START", "END"};
    static String[] DL = {"DS", "DC"};

    public static void main(String[] args) {
        String code[] = {
            "START 100",
            "MOVER AREG, ='5'",
            "ADD BREG, X",
            "X DS 1",
            "END"
        };

        for (String line : code) {
            processLine(line);
        }

        
        System.out.println("\n--- Symbol Table ---");
        for (int i = 0; i < symbolTable.size(); i++)
            System.out.println(i + " " + symbolTable.get(i).name + " " + symbolTable.get(i).address);

        System.out.println("\n--- Literal Table ---");
        for (int i = 0; i < literalTable.size(); i++)
            System.out.println(i + " " + literalTable.get(i).literal + " " + literalTable.get(i).address);
    }

    static void processLine(String line) {
        String tokens[] = line.split("[ ,]+");

        if (tokens[0].equals("START")) {
            LC = Integer.parseInt(tokens[1]);
            System.out.println("AD(01) C(" + LC + ")");
        } 
        else if (tokens[0].equals("END")) {
            System.out.println("AD(02)");
        } 
        else if (Arrays.asList(IS).contains(tokens[0])) {
            System.out.print("IS(" + (Arrays.asList(IS).indexOf(tokens[0]) + 1) + ") ");
            if (Arrays.asList(REG).contains(tokens[1]))
                System.out.print((Arrays.asList(REG).indexOf(tokens[1]) + 1) + " ");

            if (tokens[2].startsWith("='")) {
                literalTable.add(new Literal(tokens[2], -1));
                System.out.println("L" + (literalTable.size() - 1));
            } else {
                symbolTable.add(new Symbol(tokens[2], -1));
                System.out.println("S" + (symbolTable.size() - 1));
            }
            LC++;
        } 
        else if (tokens.length > 1 && Arrays.asList(DL).contains(tokens[1])) {
            int size = Integer.parseInt(tokens[2]);
            symbolTable.add(new Symbol(tokens[0], LC));
            System.out.println("DL(" + (Arrays.asList(DL).indexOf(tokens[1]) + 1) + ") C(" + size + ")");
            LC += size;
        }
    }
}
