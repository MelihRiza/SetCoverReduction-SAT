import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Trial{
    public static void main(String[] args) {
        Task1 task1 = new Task1();
        task1.readProblemData();
        task1.solve();
        task1.formulateOracleQuestion();
        task1.decipherOracleAnswer();
        task1.writeAnswer();
    }

}
