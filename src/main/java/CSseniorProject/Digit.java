package CSseniorProject;

public class Digit {
       public String output;
        public String firstLine;
        public String secondLine;
        public String thirdLine;
        public String fourthLine;

        public Digit(String output, String firstLine, String secondLine, String thirdLine, String fourthLine) {
            super ();
            this.output = output;
            this.firstLine = firstLine;
            this.secondLine = secondLine;
            this.thirdLine = thirdLine;
            this.fourthLine = fourthLine;
        }

        public String getFirstLine() {
            return firstLine;
        }

        public String getSecondLine() {
            return secondLine;
        }

        public String getThirdLine() {
            return thirdLine;
        }

        public String getFourthLine() {
            return fourthLine;
        }

        public String getOutput() {
            return output;
        }
}
