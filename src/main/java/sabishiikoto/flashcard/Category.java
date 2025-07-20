package sabishiikoto.flashcard;

import java.util.ArrayList;

public class Category {
    private String name;
    private ArrayList<Term> terms;
    public Category(String name){
        this.name = name;
        this.terms = new ArrayList<>();
    }
    public String getName(){
        return name;
    }

    public ArrayList<Term> getTerms(){
        return this.terms;
    }
    @Override
    public boolean equals (Object object){
        if (this == object){
            return true;
        }
        else if (object == null || this.getClass() != object.getClass()){
            return false;
        }
        Category category = (Category) object;
        return this.name.equals(category.name);
    }
    public void addTerm ( Term term){
        this.terms.add(term);
    }
    public void removeTerm (Term term) {
        this.terms.remove(term);
    }

    /**
     * Check if the term name is in the term list of a category.
     * @param termName String name of the object.
     * @return True if the category contains the name, False otherwise.
     */
    public boolean contains (String termName){
        for (Term item : this.terms){
            if (item.getName().equals(termName)){
                return true;
            }
        }
        return false;
    }

    /**
     * Loop through the term list, and replace the term in the list.
     * @param term -> Term, a new term to replace the old one.
     */
    public boolean replace(Term term){
        for (Term item: this.terms){
            if (item.getName().equals(term.getName())){
                int index = terms.indexOf(item);
                terms.add(index, term);
                terms.remove(index + 1);
                return true;
            }
        }
        return false;
    }
    public int indexOf (String termName){
        for (int i = 0; i < this.terms.size(); i++){
            if (terms.get(i).getName().equals(termName)){
                return i;
            }
        }
        return -1;
    }

}
