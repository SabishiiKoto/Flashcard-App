package sabishiikoto.flashcard;

public class Term {
    private String name;
    private String definition;
    public Term(String name, String definition){
        this.name = name;
        this.definition = definition;
    }
    public void setDefinition(String definition){
        this.definition = definition;
    }
    public String getName(){
        return this.name;
    }
    public String getDefinition(){
        return this.definition;
    }
    @Override
    public boolean equals (Object object) {
        if (this == object) {
            return true;
        } else if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        Term term = (Term) object;
        return this.name.equals(term.name);
    }
}
