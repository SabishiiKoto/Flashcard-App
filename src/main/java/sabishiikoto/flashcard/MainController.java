package sabishiikoto.flashcard;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class MainController {

    @FXML
    private ComboBox<String> comboBoxCategory;

    @FXML
    private ComboBox<String> comboBoxForTerm;

    @FXML
    private Label labelForError;

    @FXML
    private Button buttonForFlashCard;

    @FXML
    private Label labelForNumberOfWord;

    @FXML
    private TextArea textAreaForDefinition;

    private ArrayList<String> categoryListForComboBox = new ArrayList<>();
    private ArrayList<Category> categories = new ArrayList<>();
    private Category currentCategory = null;
    private int currentIndex;
    private boolean isTermName;

    @FXML
    void aboutTrigger(ActionEvent event) {
        labelForError.setText("");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About This Flashcard Program!");
        alert.setHeaderText("Contact and Information");
        alert.setContentText("This is a personal project coded in java language!\nCheck out my GitHub: https://github.com/SabishiiKoto");
        alert.showAndWait();
    }

    @FXML
    void addTrigger(ActionEvent event){
        currentCategory = null;
        labelForNumberOfWord.setText("");
        buttonForFlashCard.setText("");
        String categoryName = comboBoxCategory.getValue();
        if (categoryName != null && !categoryName.isEmpty()){
            String termName = comboBoxForTerm.getValue();
            if (categoryListForComboBox.contains(categoryName)){
                int index = categoryListForComboBox.indexOf(categoryName);
                Category category = categories.get(index);
                if (termName != null && !termName.isEmpty() && !textAreaForDefinition.getText().trim().isEmpty()){
                    Term term = new Term(termName,textAreaForDefinition.getText());
                    if (category.contains(termName)){
                        boolean status = category.replace(term);
                        if (status) {
                            labelForError.setTextFill(Color.STEELBLUE);
                            labelForError.setText("Term is updated!");
                        }
                        else{
                            labelForError.setTextFill(Color.RED);
                            labelForError.setText("Term update is failed!");
                        }
                    }
                    else{
                        category.addTerm(term);
                        labelForError.setTextFill(Color.STEELBLUE);
                        labelForError.setText("A new term is added!");
                        comboBoxForTerm.getItems().add(termName);
                    }
                    textAreaForDefinition.clear();
                    comboBoxForTerm.setValue(null);
                }
                else{
                    labelForError.setTextFill(Color.RED);
                    labelForError.setText("Please enter Term and Definition correctly!");
                }
            }
            else{
                categoryListForComboBox.add(categoryName);
                Category category = new Category(categoryName);
                categories.add(category);
                comboBoxCategory.getItems().add(categoryName);
                labelForError.setTextFill(Color.STEELBLUE);
                labelForError.setText("A new category is added!");
                if (termName != null && !termName.isEmpty() && !textAreaForDefinition.getText().trim().isEmpty()){
                    Term term = new Term(termName, textAreaForDefinition.getText());
                    category.addTerm(term);
                    labelForError.setTextFill(Color.STEELBLUE);
                    labelForError.setText("A new category & term are added!");
                    textAreaForDefinition.clear();
                    comboBoxForTerm.getItems().add(termName);
                    comboBoxForTerm.setValue(null);

                }
            }
        }
        else{
            labelForError.setTextFill(Color.RED);
            labelForError.setText("Please select a category!");
        }

    }

    @FXML
    void clearTrigger(ActionEvent event) {
        categoryListForComboBox.clear();
        categories.clear();
        categoryListForComboBox.add("General");
        Category category = new Category("General");
        categories.add(category);
        comboBoxCategory.getItems().clear();
        comboBoxCategory.getItems().add("General");
        comboBoxForTerm.getItems().clear();
        comboBoxForTerm.setValue(null);
        textAreaForDefinition.clear();
        currentCategory = null;
        labelForNumberOfWord.setText("");
        buttonForFlashCard.setText("");
    }

    @FXML
    void comboBoxCategoryTrigger(ActionEvent event) {
        labelForError.setText("");
        String categoryName = comboBoxCategory.getValue();
        comboBoxForTerm.getItems().clear();
        comboBoxForTerm.setValue(null);
        if (categoryName != null){
            if (categoryListForComboBox.contains(categoryName)) {
                int index = categoryListForComboBox.indexOf(categoryName);
                Category category = categories.get(index);
                ArrayList<String> termStringList = Function.termsToList(category);
                if (!termStringList.isEmpty()) {
                    comboBoxForTerm.getItems().addAll(termStringList);
                }
            }
        }
    }

    @FXML
    void deleteTrigger(ActionEvent event) {
        currentCategory = null;
        labelForNumberOfWord.setText("");
        buttonForFlashCard.setText("");
        String categoryName = comboBoxCategory.getValue();
        if (categoryName != null && !categoryName.isEmpty()){
            int index = categoryListForComboBox.indexOf(categoryName);
            String termName = comboBoxForTerm.getValue();
            if (termName != null && !termName.isEmpty()){
                Category category = categories.get(index);
                Term term = new Term(termName, textAreaForDefinition.getText().trim());
                category.removeTerm(term);
                comboBoxForTerm.setValue(null);
                comboBoxForTerm.getItems().clear();
                textAreaForDefinition.clear();
                ArrayList<String> termStringList = Function.termsToList(category);
                if (!termStringList.isEmpty()){
                    comboBoxForTerm.getItems().addAll(termStringList);
                }
                labelForError.setTextFill(Color.STEELBLUE);
                labelForError.setText("A term in " + categoryName + " is removed!");
            }
            else{
                categoryListForComboBox.remove(categoryName);
                categories.remove(index);
                comboBoxCategory.setValue(null);
                comboBoxCategory.getItems().clear();
                comboBoxCategory.getItems().addAll(categoryListForComboBox);
                labelForError.setTextFill(Color.STEELBLUE);
                labelForError.setText("The category: " + categoryName + " is removed!");
            }
        }
    }

    @FXML
    void flashCardTrigger(ActionEvent event) {
        if (currentCategory != null) {
            if (isTermName) {
                String definition = currentCategory.getTerms().get(currentIndex).getDefinition();
                buttonForFlashCard.setText(definition);
                isTermName = false;
            } else {
                String termName = currentCategory.getTerms().get(currentIndex).getName();
                buttonForFlashCard.setText(termName);
                isTermName = true;
            }
        }
        else{
            labelForError.setTextFill(Color.RED);
            labelForError.setText("Please select a valid category!");
        }
    }

    @FXML
    void loadTrigger(ActionEvent event) {
        labelForError.setText("");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Pick a file to load data!");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text File", "*.txt"));
        fileChooser.setInitialDirectory(new File("."));
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null){
            ArrayList<Category> temp = Data.loadFile(file);
            if (temp != null){
                categories = temp;
                categoryListForComboBox.clear();
                for (Category category : categories){
                    categoryListForComboBox.add(category.getName());
                }
                comboBoxCategory.getItems().clear();
                comboBoxCategory.getItems().addAll(categoryListForComboBox);
                comboBoxCategory.setValue(null);
                comboBoxForTerm.setValue(null);
                textAreaForDefinition.clear();
                buttonForFlashCard.setText("");
                labelForNumberOfWord.setText("");
                labelForError.setTextFill(Color.STEELBLUE);
                labelForError.setText("Load progress is successful!");

            }
            else{
                labelForError.setTextFill(Color.RED);
                labelForError.setText("Load progress is failed!");
            }

        }
        else{
            labelForError.setTextFill(Color.RED);
            labelForError.setText("Load progress is interrupted!");
        }


    }

    @FXML
    void nextWordTrigger(ActionEvent event) {
        if (currentCategory != null){
            int temp = currentIndex + 1;
            if (temp < currentCategory.getTerms().size()) {
                currentIndex = temp;
                String termName = currentCategory.getTerms().get(currentIndex).getName();
                buttonForFlashCard.setText(termName);
                isTermName = true;
                labelForNumberOfWord.setText(currentIndex + 1 + " / " + currentCategory.getTerms().size());
            }
        }
        else{
            labelForError.setTextFill(Color.RED);
            labelForError.setText("Please press Test button!");
        }

    }

    @FXML
    void previousWordTrigger(ActionEvent event) {
        if (currentCategory != null){
            int temp = currentIndex - 1;
            if (temp >= 0) {
                currentIndex = temp;
                String termName = currentCategory.getTerms().get(currentIndex).getName();
                buttonForFlashCard.setText(termName);
                isTermName = true;
                labelForNumberOfWord.setText(currentIndex + 1 + " / " + currentCategory.getTerms().size());
            }
        }
        else{
            labelForError.setTextFill(Color.RED);
            labelForError.setText("Please press Test button!");
        }
    }

    @FXML
    void saveTrigger(ActionEvent event) {
        labelForError.setText("");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Pick a file to save to...");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text File", "*.txt"));
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.setInitialFileName("flashCardJava.txt");
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            String status = Data.saveFile(categories, file);
            if (status.equals("error")) {
                labelForError.setTextFill(Color.RED);
                labelForError.setText("Save progress is failed!");
            }
            else{
                labelForError.setTextFill(Color.STEELBLUE);
                labelForError.setText("Save progress is successful!");
            }
        }
        else{
            labelForError.setTextFill(Color.RED);
            labelForError.setText("Save progress is interrupted!");
        }




    }
    @FXML
    void testTrigger(ActionEvent event) {
        labelForError.setText("");
        String categoryName = comboBoxCategory.getValue();
        if (categoryName != null && !categoryName.isEmpty()){
            if (categoryListForComboBox.contains(categoryName)){
                String termName = comboBoxForTerm.getValue();
                int index = categoryListForComboBox.indexOf(categoryName);
                Category category = categories.get(index);
                if (!category.getTerms().isEmpty()) {
                    currentCategory = category;
                    if (termName != null && !termName.isEmpty()) {
                        currentIndex = category.indexOf(termName);
                        buttonForFlashCard.setText(termName);
                    }
                    else {
                        currentIndex = 0;
                        buttonForFlashCard.setText(currentCategory.getTerms().get(currentIndex).getName());
                    }
                    labelForNumberOfWord.setText(currentIndex + 1 + " / " + currentCategory.getTerms().size());
                    isTermName = true;

                }
                else{
                    buttonForFlashCard.setText("");
                    labelForNumberOfWord.setText("");
                    currentCategory = null;
                    labelForError.setTextFill(Color.RED);
                    labelForError.setText("Category: " + categoryName + " is not available to test!");
                }
            }
            else{
                labelForError.setTextFill(Color.RED);
                labelForError.setText("Category: " + categoryName + " is not available to test!");
            }
        }
        else{
            labelForError.setTextFill(Color.RED);
            labelForError.setText("Please select a category");
        }
    }

    @FXML
    public void initialize (){
        comboBoxCategory.getItems().add("General");
        Category category = new Category("General");
        categoryListForComboBox.add("General");
        categories.add(category);
    }
}
