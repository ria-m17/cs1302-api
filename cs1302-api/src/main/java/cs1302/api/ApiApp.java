package cs1302.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.net.URLEncoder;
import javafx.scene.text.Font;
import javafx.scene.control.ComboBox;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.lang.Math;

/**
 * This application takes in a user-selected region from the drop
 * down, and displays a Wikipedia page about a randomly generated country
 * in that region.
 */

public class ApiApp extends Application {
    Stage stage;
    Scene scene;
    VBox root;

    private final Gson gson =  new GsonBuilder()
        .setPrettyPrinting()                          // enable nice output when printing
        .create();                                    // builds and returns a Gson object
    private HttpClient client;
    double latitude;
    double longitude;
    String airQuality;
    TextField locationTextField;
    Label weatherLabel;
    Button searchButton;
    int aqi;
    Label airQualityLabel;
    VBox weatherInfo;
    Label humidityLabel;
    HBox top;
    HBox left;
    HBox center;
    ComboBox<String> dropDown;
    String selectedRegion;
    Label wikipediaInfoLabel;

/**
 * Constructs an {@code ApiApp} object. This default (i.e., no argument)
 * constructor is executed in Step 2 of the JavaFX Application Life-Cycle.
 */

    public ApiApp() {
        root = new VBox();
    } // ApiApp

/**
 * Initializes the necessary scene graph components.
 */

    public void init() {
        client = HttpClient.newHttpClient();
        searchButton = new Button("Get Info");
        searchButton.setPrefWidth(100);
        top = new HBox();
        left = new HBox();
        center = new HBox();
        wikipediaInfoLabel = new Label();
    } // init

    /** {@inheritDoc} */
    @Override
    public void start(Stage stage) {
        dropDown = new ComboBox<>();
        dropDown.getItems().addAll("Africa", "Europe", "Asia", "Oceania");
        dropDown.setValue("Africa"); // Set default value
        dropDown.setPrefWidth(150);
        top.getChildren().addAll(searchButton);
        top.setAlignment(Pos.CENTER_RIGHT);
        left.getChildren().addAll(dropDown);
        left.setAlignment(Pos.CENTER_LEFT);
        HBox.setMargin(left, new Insets(0, 260, 0, 0));
        center.getChildren().addAll(left,top);
        center.setAlignment(Pos.CENTER);
        searchButton.setOnAction(event -> {
            selectedRegion = dropDown.getValue().toString();
            try {
                Country wikiInput = getCountriesResponse(selectedRegion);
                String wikipediaInfo = getWikipediaInfo(wikiInput);
                wikipediaInfoLabel.setText(wikipediaInfo);
                wikipediaInfoLabel.setWrapText(true);
                wikipediaInfoLabel.setFont(Font.font("Arial", 12));
                wikipediaInfoLabel.setPrefWidth(500);
            } catch (IOException e) {
                // handle the exception
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } //catch
        });
        HBox pane = new HBox(wikipediaInfoLabel);
        pane.setPrefHeight(500);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(center, pane);
        Scene scene = new Scene(root, 510, 300);
        stage.setScene(scene);
        stage.setTitle("Country & Wikipedia App");
        stage.show();
    } //start

/**
 * Returns a random country from the specified region using the REST Countries API.
 * @param region a String representing the region of the countries to search for.
 * @return a randomly selected Country object from the list of countries in the specified region
 * @throws IOException if there is an error sending the HTTP request
 * @throws InterruptedException if the thread is interrupted while waiting for the HTTP response
 */

    public Country getCountriesResponse(String region) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.first.org/data/v1/countries?region=" +
            region + "&limit=600&pretty=true"))
            .header("Accept", "application/json")
            .build();
        try {
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new IOException(response.toString());
            } // if
            String jsonResponse = response.body().trim();
            ApiFirstResponse  apiFirstResponse = gson.fromJson(jsonResponse,
                ApiFirstResponse.class);
            Map<String, Country> data = apiFirstResponse.getData();
            List<Country> countries = new ArrayList<>(data.values());
            int randomIndex = (int) (Math.random() * countries.size());
            Country randomCountry = countries.get(randomIndex);
            return randomCountry;
        } catch (IOException | InterruptedException e) {
            throw new IOException("Error sending Http request", e);
        } // catch
    } // getCountries

    /**
     * Returns information about the given country from the Wikipedia API.
     * @param location the Country object for which to retrieve information from Wikipedia API
     * @return a String representing information about the given country
     * @throws IOException if there is an error sending the HTTP request
*/

    private String getWikipediaInfo(Country location) throws IOException {
        String encodedCountry = URLEncoder.encode(location.getCountry(), StandardCharsets.UTF_8);
        String url = String.format("https://en.wikipedia.org/w/api.php?action=query&format"
            + "=json&prop=extracts&exsentences=3&explaintext=1&titles=%s", encodedCountry);
        // Make a HTTP request to the Wikipedia API
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Accept", "application/json")
            .GET()
            .build();
        try {
            HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new IOException(response.toString());
            } // if
            WikipediaResponse wikipediaResponse = gson.fromJson(response.body(),
                WikipediaResponse.class);
            return wikipediaResponse.getExtract();
        } catch (InterruptedException e) {
            throw new IOException(e.getMessage());
        } // catch

    } // getWikipediaInfo

}  // ApiApp
