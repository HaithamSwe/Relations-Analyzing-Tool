package sample;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;


public class Main extends Application {
    protected String path;
    protected Scanner scanner;

    public static void main(String[] args) {

        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Button button = new Button("Select an input file");
        AnchorPane anchorPane = new AnchorPane(button);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                fileChooser(stage);
                try {
                    initializeScanner(path);
                    if (!scanFile(scanner))
                        throw new Exception();
                    printToTxtFile();

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    System.out.println("error");
                    System.out.print(Functionalities.toString(""));
                    System.out.print(
                            Functionalities.toString("") + transitivityToString(Functionalities.matrix) + "\nType of relation: "
                                    + relationTypeToString(Functionalities.matrix));
                }
                //System.out.println(toString1());

            }
        });
        Scene scene = new Scene(anchorPane);
        stage.setMinHeight(button.getHeight());
        stage.setMaxWidth(button.getWidth());
        stage.setScene(scene);
        stage.show();

    }

    protected void printToTxtFile() throws IOException {
        PrintWriter printWriter = new PrintWriter(new FileOutputStream(path + ".2.txt"));
        Scanner stringScanner = new Scanner(toString1());
        while (stringScanner.hasNextLine())
            printWriter.println(stringScanner.nextLine());
        stringScanner.close();
        printWriter.close();
    }

    @Override
    public void stop() throws Exception {
        scanner.close();
    }

    private void fileChooser(Stage mainStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(mainStage);
        path = selectedFile.getAbsolutePath();
    }

    private void initializeScanner(String path) throws IOException {
        Scanner scanner = new Scanner(new FileInputStream(path));
        this.scanner = scanner;
    }

    private boolean scanFile(Scanner scanner) {
        try {
            scanner.nextLine();

            String temp = scanner.nextLine();
            Functionalities.setMembers = temp.split(",");
            Functionalities.setMembers[0] = Functionalities.setMembers[0].substring(1);
            Functionalities.setMembers[Functionalities.setMembers.length
                    - 1] = Functionalities.setMembers[Functionalities.setMembers.length - 1].substring(0,
                    Functionalities.setMembers[Functionalities.setMembers.length - 1].length() - 1);
            int[][] matrix = new int[Functionalities.setMembers.length][Functionalities.setMembers.length];
            for (int i = 0; i < matrix.length; i++)
                for (int j = 0; j < matrix.length; j++)
                    matrix[i][j] = scanner.nextInt();
            Functionalities.matrix = matrix;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public String toString1() {
        String temp = Functionalities.toString("") + "reflexivity: " + reflexivityToString(Functionalities.matrix)
                + "\nSymmetry: " + symmetryToString(Functionalities.matrix) + "\nTransitivity: "
                + transitivityToString(Functionalities.matrix) + "\nType of relation: "
                + relationTypeToString(Functionalities.matrix);

        if (Functionalities.isEquivalence(Functionalities.matrix)) {
            temp += "Equivalence Classes"
                    + equivalenceClassesToString(Functionalities.matrix, Functionalities.setMembers);
        } else if (Functionalities.isAntisymmetric(Functionalities.matrix)) {
            Functionalities.generateHasseDiagram();
            temp += "\n" + Functionalities.hasseDiagram;
            temp += "\nmaximals: " + Functionalities.maximals.toString() + "\nminimals: "
                    + Functionalities.minimals.toString() + "\ngreatest: " + Functionalities.greatest + "\nleast: "
                    + Functionalities.least;
        }

        return temp;
    }

    public String reflexivityToString(int[][] matrix) {
        if (Functionalities.isReflexive(matrix))
            return "Reflexive";
        else if (Functionalities.isIrreflexive(matrix))
            return "Irreflexive";
        return "Not Reflexive, Not Irreflexive";
    }

    private String symmetryToString(int[][] matrix) {
        if (Functionalities.isSymmetric(matrix))
            return "Symmetric";
        else if (Functionalities.isAntisymmetric(matrix))
            return "Antisymmetric";
        else if (Functionalities.isAsymmetric(matrix))
            return "Asymmetric";

        return "not Symmetric, not Antisymmetric, not Asymmetric";
    }

    private String transitivityToString(int[][] matrix) {
        if (Functionalities.isTransitive(matrix))
            return "Transitive";
        return "not Transitive";

    }

    private String relationTypeToString(int[][] matrix) {
        if (Functionalities.isEquivalence(matrix))
            return "Equivalence";
        else if (Functionalities.isPartialOrdering(matrix))
            return "Partial Ordering";
        return "not Equivalence, not Partial Ordering";
    }

    private String equivalenceClassesToString(int[][] matrix, String setMembers[]) {
//		String[][] equivalenceClasses = Functionalities.getEquivalenceClasses(matrix, setMembers);
//		String temp = "";
//		for (int i = 0; i < equivalenceClasses.length; i++) {
//			temp += "EQ" + (i + 1) + ": {";
//			for (int j = 0; j < equivalenceClasses[i].length; j++)
//				temp += equivalenceClasses[i][j];
//			temp += "}\n";
//		}
//		return temp;
        return Functionalities.getEquivalence(matrix, setMembers);
    }
}