package sabishiikoto.flashcard;

import java.util.*;

public class Function {
//    public static ArrayList<Category> removeCategory(ArrayList<Category> categories, Category category){
//        for (Category item : categories){
//            if (item.getName().equals(category.getName())){
//                categories.remove(category);
//            }
//        }
//        return categories;
//    }
//    public static ArrayList<Category> addCategory(ArrayList<Category> categories, Category category){
//        for (Category item : categories){
//            if (item.getName().equals(category.getName())){
//                return categories;
//            }
//        }
//        categories.add(category);
//        return categories;
//    }
    public static ArrayList<String> termsToList (Category category){
        ArrayList<String> termStringList = new ArrayList<>();
        for (Term term : category.getTerms()){
            termStringList.add(term.getName());
        }
        return termStringList;
    }
}
