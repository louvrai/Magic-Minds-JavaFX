package controllers;

import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatController {

    @FXML
    private WebView webView;

    public void initialize() {
        // Load the chatbot URL into the WebView
        String chatbotURL = "https://chatcube.co/chat/212bac08-4605-4b22-aeb7-af32487ccb8e";
        webView.getEngine().load(chatbotURL);
    }
}
