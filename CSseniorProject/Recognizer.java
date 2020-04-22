package CSseniorProject;

public class Recognizer {
        public Recognizer() {
        }

        public String convert(String input) {
            String[] inputArr = input.split ("\n");
            String output = "";

            if (inputArr.length % 4 == 0) {
                int digit = inputArr[0].length () / 3;

                for (int i = 0; i < digit; i++) {
                    int start = i * 3;
                    String input0 = inputArr[0].substring (start, (start + 3));
                    String input1 = inputArr[1].substring (start, (start + 3));
                    String input2 = inputArr[2].substring (start, (start + 3));
                    String input3 = inputArr[3].substring (start, (start + 3));

                    String outputInner = "";

                    for (Digit lineDigit : numberList) {
                        boolean firstLine = false;
                        boolean secondLine = false;
                        boolean thirdLine = false;
                        boolean fourthLine = false;
                        if (input0.equals (lineDigit.getFirstLine ())) {
                            firstLine = true;
                        }
                        if (input1.equals (lineDigit.getSecondLine ())) {
                            secondLine = true;
                        }
                        if (input2.equals (lineDigit.getThirdLine ())) {
                            thirdLine = true;
                        }
                        if (input3.equals (lineDigit.getFourthLine ())) {
                            fourthLine = true;
                        }
                        if (firstLine && secondLine && thirdLine && fourthLine) {
                            outputInner = lineDigit.getOutput ();
                            break;
                        }
                    }
                    output += outputInner.equals ("") ? "?" : outputInner;

                }
                if (inputArr.length > 4) {

                    for (int j = 4; j < inputArr.length; j = j + 4) {
                        int secondRowDigit = inputArr[j].length () / 3;
                        output += ",";
                        for (int i = 0; i < secondRowDigit; i++) {
                            int start = i * 3;
                            String input0 = inputArr[j].substring (start, (start + 3));
                            String input1 = inputArr[j + 1].substring (start, (start + 3));
                            String input2 = inputArr[j + 2].substring (start, (start + 3));
                            String input3 = inputArr[j + 3].substring (start, (start + 3));

                            String outputInner = "";

                            for (Digit lineDigit : numberList) {
                                boolean firstLine = false;
                                boolean secondLine = false;
                                boolean thirdLine = false;
                                boolean fourthLine = false;
                                if (input0.equals (lineDigit.getFirstLine ())) {
                                    firstLine = true;
                                }
                                if (input1.equals (lineDigit.getSecondLine ())) {
                                    secondLine = true;
                                }
                                if (input2.equals (lineDigit.getThirdLine ())) {
                                    thirdLine = true;
                                }
                                if (input3.equals (lineDigit.getFourthLine ())) {
                                    fourthLine = true;
                                }
                                if (firstLine && secondLine && thirdLine && fourthLine) {
                                    outputInner = lineDigit.getOutput ();
                                    break;
                                }
                            }
                            output += outputInner.equals ("") ? "?" : outputInner;
                        }
                    }

                }
                if (inputArr[0].length () % 3 != 0)
                    output += "?";
            } else {
                output += "?";
            }
            return output;
        }

        private Digit ZERO = new Digit ("0", " _ ", "| |", "|_|", "   ");
        private Digit ONE = new Digit ("1", "   ", "  |", "  |", "   ");
        private Digit TWO = new Digit ("2", " _ ", " _|", "|_ ", "   ");
        private Digit THREE = new Digit ("3", " _ ", " _|", " _|", "   ");
        private Digit FOUR = new Digit ("4", "   ", "|_|", "  |", "   ");
        private Digit FIVE = new Digit ("5", " _ ", "|_ ", " _|", "   ");
        private Digit SIX = new Digit ("6", " _ ", "|_ ", "|_|", "   ");
        private Digit SEVEN = new Digit ("7", " _ ", "  |", "  |", "   ");
        private Digit EIGHT = new Digit ("8", " _ ", "|_|", "|_|", "   ");
        private Digit NINE = new Digit ("9", " _ ", "|_|", " _|", "   ");
        private Digit A = new Digit ("A", " _ ", "|_|", "| |", "   ");
        private Digit B = new Digit ("B", "   ", "|_ ", "|_|", "   ");
        private Digit C = new Digit ("C", " _ ", "|  ", "|_ ", "   ");
        private Digit D = new Digit ("D", "   ", " _|", "|_|", "   ");
        private Digit E = new Digit ("E", " _ ", "|_ ", "|_ ", "   ");
        private Digit F = new Digit ("F", " _ ", "|_ ", "|  ", "   ");

        public Digit[] numberList = {
                ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE,
                A, B, C, D, E, F
        };


}
