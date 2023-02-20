import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Task1 extends Task{
    public int N = 0;
    public int M = 0;
    public int K = 0;
    public ArrayList<ArrayList<Integer>> listaSeturi = new ArrayList<>();
    public ArrayList<ArrayList<Integer>> clauze = new ArrayList<>();
    public ArrayList<Integer> raspunsOracol = new ArrayList<>();
    public ArrayList<Integer> rezultat = new ArrayList<>();
    public int lengthOfAllSets = 0;
    public boolean gasit = false;


    @Override
    public void solve() {
        int j = 1;
        for (int i = 0; i < K; i++) {
            ArrayList<Integer> temp = new ArrayList<>();
            int contor = 0;
            while (contor < listaSeturi.size()) {
                temp.add(j);
                j++;
                contor++;
            }
            clauze.add(temp);
        }
//        j = 1;
//        for (int i = 0; i < M; i++) {
//            ArrayList<Integer> temp = new ArrayList<>();
//            for (int contor = 0; contor < K ; contor++) {
//                temp.add((j) * (-1));
//                j += M;
//            }
//            j = i + 2;
//            clauze.add(temp);
//        }
        j = 0;
        for (int i = 0; i < M; i++) {
            j = 0;
            while (j < K - 1) {
                int l = j + 1;
                while (l < K) {
                    ArrayList<Integer> temp = new ArrayList<>();
                    temp.add(clauze.get(j).get(i) * (-1));
                    temp.add(clauze.get(l).get(i) * (-1));
                    clauze.add(temp);
                    l++;
                }
                j++;
            }
        }

        j = 0;
        for (int i = 0; i < K; i++) {
            while (j < clauze.get(i).size() - 1) {
                int l = j + 1;
                while (l < clauze.get(i).size()) {
                    ArrayList<Integer> temp = new ArrayList<>();
                    temp.add(clauze.get(i).get(j) * (-1));
                    temp.add(clauze.get(i).get(l) * (-1));
                    clauze.add(temp);
                    l++;
                }
                j++;
            }
            j = i;
        }

        for (int i = 1; i <= N; i++) {
            ArrayList<Integer> temp = new ArrayList<>();
            for (j = 0; j < M; j++) {
                if (listaSeturi.get(j).contains(i)) {
                    for (int l = 0; l < K; l++) {
                        temp.add((j+1) + l * M);
                    }
                }
            }
            clauze.add(temp);
        }

       // System.out.println(clauze);
    }

    @Override
    public void readProblemData() {
        try {

            InputStreamReader in= new InputStreamReader(System.in);

            BufferedReader input = new BufferedReader(in);

            String str;
            String[] nr;
            nr = input.readLine().split(" ");

            N = Integer.parseInt(nr[0]);
            M = Integer.parseInt(nr[1]);
            K = Integer.parseInt(nr[2]);

//            System.out.println("N este : " + N);
//            System.out.println("M este : " + M);
//            System.out.println("K este : " + K);

            while ((str = input.readLine()) != null) {
                String[] nr1 = str.split(" ");
                ArrayList<Integer> templist = new ArrayList<>();
                lengthOfAllSets += Integer.parseInt(nr1[0]);
                for (int i = 1; i < nr1.length; i++) {
                    templist.add(i - 1, Integer.valueOf(nr1[i]));
                }
                listaSeturi.add(templist);
            }

        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    @Override
    public void formulateOracleQuestion() {
        try {
            try {
                FileWriter myWriter = new FileWriter("sat.cnf");
                myWriter.write("p cnf " + M * K + " " + clauze.size() + "\n");
                for (int i = 0; i < clauze.size(); i++) {
                    //System.out.println(clauze.get(i));
                    for (int j = 0; j < clauze.get(i).size(); j++) {
                        myWriter.write(clauze.get(i).get(j) + " ");
                    }
                    myWriter.write("0\n");
                }
                myWriter.close();
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            askOracle();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void decipherOracleAnswer() {
        File file = new File("sat.sol");
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(file));
            String text = null;

            text = reader.readLine();
            if (text.equals("True")) {
                gasit = true;
            }
            if (lengthOfAllSets <= M && K < M) {
                gasit = false;
            }
            text = reader.readLine();

            text = reader.readLine();
            reader.close();
            String[] temp = text.split(" ");
            for (String s : temp) {
                int nr = Integer.parseInt(s);
                raspunsOracol.add(nr);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
            }
        }
    }

    @Override
    public void writeAnswer() {
        for (Integer integer : raspunsOracol) {
            if (integer > 0) {
                if (integer % M != 0) {
                    rezultat.add(integer % M);
                } else {
                    rezultat.add(M);
                }
            }
        }
        if (gasit) {
            //System.out.println(lengthOfAllSets);
            System.out.println("True");
            System.out.println(K);
            for (Integer integer : rezultat) {
                System.out.printf(integer + " ");
            }
        } else {
            System.out.println("False");
        }
        rezultat.clear();
    }
}
