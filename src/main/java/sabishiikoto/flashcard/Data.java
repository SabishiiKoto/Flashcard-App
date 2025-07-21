package sabishiikoto.flashcard;

import java.io.*;
import java.util.ArrayList;

public class Data {
    public static String saveFile (ArrayList<Category> categories, File file){
        if (categories == null || categories.isEmpty()){ // Reset the file if save an empty ArrayList
            try (PrintWriter printWriter = new PrintWriter(new FileWriter(file.getPath()))){
                return "File reset!";
            }
            catch(IOException e){
                return "error";
            }
        }
        try{
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            for (Category category : categories){
                bufferedWriter.write(category.getName() + "\n");
                if (!category.getTerms().isEmpty()){
                    for (Term term : category.getTerms()){
                        bufferedWriter.write(term.getName() + ": " + term.getDefinition() + "\n");
                    }
                }
                bufferedWriter.flush();
            }
            return "success";
        }
        catch(IOException e){
            return "error";
        }
    }
    public static ArrayList<Category> loadFile (File file){
        int categoryIndex = -1;
        ArrayList<Category> categories = new ArrayList<>();
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line = bufferedReader.readLine();
            while (line != null){
                if (!line.contains(":")){ // Line is a category
                    Category category = new Category(line);
                    categories.add(category);
                    categoryIndex++;
                }
                else{
                    String[] list = line.split(":");
                    Term term = new Term(list[0].trim(),list[1].trim());
                    Category category = categories.get(categoryIndex);
                    category.addTerm(term);
                }
                line = bufferedReader.readLine();
            }
            return categories;
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
