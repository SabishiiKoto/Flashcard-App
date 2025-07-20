module sabishiikoto.flashcard {
    requires javafx.controls;
    requires javafx.fxml;


    opens sabishiikoto.flashcard to javafx.fxml;
    exports sabishiikoto.flashcard;
}