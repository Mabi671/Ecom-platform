package org.example;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.*;

public class Main extends Application {
    GridPane mainPane = new GridPane();
    Scene scene = new Scene(mainPane, 1200, 800);
    VBox preLogin = new VBox();
    GridPane loginPage = new GridPane();
    VBox aftLogin = new VBox();
    HBox upperPane = new HBox();
    Button seeCart = new Button("CART");
    Button seeProducts = new Button("PRODUCTS");
    Button logOutButton = new Button("LOGOUT");

    VBox adminPane = new VBox();
    HBox upperAdminPane = new HBox();

    Button backToLoginPage = new Button("<--");




    GridPane productManager = new GridPane();
    ComboBox<List<String>> listOfProducts = new ComboBox<>();
    TextField productName = new TextField();
    TextArea productDesc = new TextArea();
    TextField productPrice = new TextField();
    FileChooser productImage = new FileChooser();
    TextField imagePath = new TextField();
    Button choosePath = new Button("(P)");
    TextField productId = new TextField();
    Button B_deleteProduct = new Button("Delete");
    Button B_updateProduct = new Button("Update");
    Button B_addProduct = new Button("Add");
    Label errorLabel = new Label();


    ScrollPane scrollProducts = new ScrollPane();
    FlowPane productsPane = new FlowPane();
    TextField nameField = new TextField("");
    PasswordField passwordField = new PasswordField();
    TextField emailField = new TextField("");
    Button logButton = new Button("Login");
    TextField dataErrorLabel = new TextField("Something went wrong, Try again");
    Button regButton = new Button("Register");


    @Override
    public void start(Stage primaryStage) throws Exception {
        adminPane.setVisible(false);
        adminPane.setStyle("-fx-background-color: linear-gradient(#ABDCFF, #0396FF)");

        upperAdminPane.setStyle("-fx-background-color: #00b7c2");
        upperAdminPane.setMinSize(1200, 80);



        backToLoginPage.setMinSize(80, 80);




        adminPane.setAlignment(Pos.TOP_CENTER);
        upperAdminPane.setAlignment(Pos.TOP_CENTER);

        productId.setEditable(false);


        seeCart.setMinSize(80, 80);
        logOutButton.setMinSize(80, 80);
        seeProducts.setMinSize(80, 80);
        seeProducts.setVisible(false);


        productManager.add(new Label("Choose"), 0, 0);
        productManager.add(listOfProducts, 1, 0);
        productManager.add(new Label("Product name"), 0, 1);
        productManager.add(productName, 1, 1);
        productManager.add(new Label("Product description"), 0, 2);
        productManager.add(productDesc, 1, 2);
        productManager.add(new Label("Product price"), 0, 3);
        productManager.add(productPrice, 1, 3);
        productManager.add(new Label("Image path"), 0, 4);
        productManager.add(imagePath, 1, 4);
        productManager.add(choosePath, 2, 4);
        productManager.add(new Label("Product ID"), 0, 5);
        productManager.add(productId, 1, 5);
        productManager.add(errorLabel, 1, 7);

        productManager.add(B_deleteProduct, 0 ,6);
        productManager.add(B_updateProduct, 1, 6);
        productManager.add(B_addProduct, 2, 6);
        productManager.setStyle("-fx-background-color: #f5f5dc");
        adminPane.getChildren().addAll(upperAdminPane, productManager);
        upperAdminPane.getChildren().addAll(backToLoginPage);
        productManager.setMinSize(500, 500);
        productManager.setMaxSize(500, 500);
        productManager.setAlignment(Pos.CENTER);
        productManager.setHgap(10);
        productManager.setVgap(10);

        errorLabel.setVisible(false);
        errorLabel.setText("Something went wrong, try again");
        preLogin.setVisible(true);

        B_addProduct.setMinSize(100, 20);

        B_updateProduct.setMinSize(100, 20);

        B_deleteProduct.setMinSize(100, 20);

        aftLogin.setVisible(false);
        aftLogin.setStyle("-fx-background-color: linear-gradient(#FFF6B7, #F6416C)");
        aftLogin.setAlignment(Pos.TOP_CENTER);

        upperPane.setStyle("-fx-background-color: #00bf56");
        upperPane.setMinSize(1200, 80);
        upperPane.setAlignment(Pos.TOP_RIGHT);
        upperPane.getChildren().addAll(seeProducts, seeCart, logOutButton);


        scrollProducts.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollProducts.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollProducts.setMinWidth(1200);
        productsPane.setMinSize(1200, 800);
        productsPane.setAlignment(Pos.TOP_CENTER);
        productsPane.setHgap(25);
        productsPane.setVgap(25);
        productsPane.setStyle("-fx-background-color: linear-gradient(#FFF6B7, #F6416C)");


        preLogin.setMinSize(1200, 800);
        preLogin.setStyle("-fx-background-color: lightgreen;");
        preLogin.setAlignment(Pos.CENTER);

        mainPane.setStyle("-fx-background-color: lightgreen;");
        mainPane.setMinSize(1200, 800);
        mainPane.setAlignment(Pos.TOP_CENTER);


        preLogin.getChildren().addAll(loginPage);

        loginPage.setMinSize(400, 400);
        loginPage.setMaxSize(400, 400);
        loginPage.setAlignment(Pos.CENTER);
        loginPage.setStyle("-fx-background-color: lightblue;");

        nameField.setMinSize(100, 30);
        passwordField.setMinSize(100, 30);

        emailField.setMinSize(100, 30);

        dataErrorLabel.setEditable(false);
        dataErrorLabel.setMaxSize(200, 20);
        dataErrorLabel.setVisible(false);


        loginPage.add(new Label("Username"), 1, 0);
        loginPage.add(nameField, 1, 1);
        loginPage.add(new Label("Password"), 1, 2);
        loginPage.add(passwordField, 1, 3);
        loginPage.add(new Label("Email"), 1, 4);
        loginPage.add(emailField, 1, 5);
        loginPage.add(logButton, 1, 6);
        loginPage.add(regButton, 1, 7);
        loginPage.add(dataErrorLabel, 1, 8);
        loginPage.setVgap(10);
        logButton.setMinSize(100, 30);

        regButton.setMinSize(100, 30);

        scrollProducts.setContent(productsPane);
        mainPane.getChildren().addAll(preLogin, aftLogin, adminPane);
        aftLogin.getChildren().addAll(upperPane, scrollProducts);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());

        primaryStage.setTitle("ecom");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        /// go back
        backToLoginPage.setOnAction(event -> {
            adminPane.setVisible(false);
            preLogin.setVisible(true);
            nameField.clear();
            emailField.clear();
            passwordField.clear();
        });
        /// selector
        listOfProducts.setOnAction(actionEvent -> {
            List<String> selected = listOfProducts.getValue();
            if (selected != null){
                productName.setText(selected.get(0));
                productDesc.setText(selected.get(1));
                productPrice.setText(selected.get(2));
                imagePath.setText(selected.get(4));
                productId.setText(selected.get(3));
            }
        });
        /// path selection
        choosePath.setOnAction(event -> {
            File selectedFile = productImage.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                imagePath.setText(selectedFile.getAbsolutePath());
            } else {
                imagePath.setText("No file selected");
            }
        });
        /// tab management
        productDesc.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.TAB) {
                productPrice.requestFocus();
                event.consume();
            }
        });
        //// price to be number
        productPrice.addEventFilter(KeyEvent.KEY_RELEASED, keyEvent -> {
            try{
                Float.parseFloat(productPrice.getText());
            }catch(NumberFormatException e){
                keyEvent.consume();
                productPrice.setText("");
            }
        });
        ///adding product
        B_addProduct.setOnAction(event -> {
            try {
                String[] text = getTexts();
                if (text[0].isEmpty()){
                    throw new RuntimeException();
                }
                addProduct(text, JwtHandler.getToken(true));
                clearTexts();
                updateList();
                errorLabel.setVisible(false);
            } catch (Exception e) {
                errorLabel.setVisible(true);
            }
        });
        /// updating product
        B_updateProduct.setOnAction(actionEvent -> {
            try {
                String[] text = getTexts();
                if (text[0].isEmpty()){
                    throw new RuntimeException();
                }
                deleteProduct(text[4], JwtHandler.getToken(true));
                addProduct(text, JwtHandler.getToken(true));
                clearTexts();
                updateList();
                errorLabel.setVisible(false);
            } catch (Exception e) {
                errorLabel.setVisible(true);
            }
        });
        /// deleting product
        B_deleteProduct.setOnAction(actionEvent -> {
            try {
                deleteProduct(productId.getText(), JwtHandler.getToken(true));
                clearTexts();
                updateList();
                errorLabel.setVisible(false);
            } catch (Exception e) {
                errorLabel.setVisible(true);
            }
        });
        seeCart.setOnAction(event -> {
            createCartPane("delete");
            seeProducts.setVisible(true);
        });
        seeProducts.setOnAction(event -> {
            createCartPane("add");
            seeProducts.setVisible(false);
        });

        // log_out
        logOutButton.setOnAction(event -> {
            aftLogin.setVisible(false);
            nameField.clear();
            emailField.clear();
            passwordField.clear();
        });
        /// login button
        logButton.setOnAction(actionEvent -> {
            try {
                String token = getAuthToken(getLoginData(), "admin");
                System.out.println(token);
                if (!token.equals("Invalid credentials") && !token.isEmpty()){
                    JwtHandler.storeToken(token, true);
                    updateList();
                    adminPane.setVisible(true);
                    dataErrorLabel.setVisible(false);
                    return;
                }
                token = getAuthToken(getLoginData(), "user");
                if(!token.equals("Invalid credentials") && !token.isEmpty()){
                    JwtHandler.storeToken(token, false);
                    createCartPane("add");
                    aftLogin.setVisible(true);
                    dataErrorLabel.setVisible(false);
                }else{
                    dataErrorLabel.setVisible(true);
                }
            } catch (Exception e) {
                dataErrorLabel.setVisible(true);
            }
        });
        /// register button
        regButton.setOnAction(actionEvent -> {
            try{
                dataErrorLabel.setVisible(true);
                String[] regData = getLoginData();
                if (regData[1].length() < 8 || regData[1].length() > 16){
                    dataErrorLabel.setText("Password must have 8-16 characters");
                    return;
                }
                else if (regData[2].replace("@gmail.com", "").equals(regData[2])){
                    dataErrorLabel.setText("Wrong email template");
                    return;
                }
                else if(sendLoginData(getLoginData()).equals("Email taken")){
                    dataErrorLabel.setText("This Email Is Taken");
                    return;
                }
                dataErrorLabel.setVisible(false);
            } catch(Exception e){
                dataErrorLabel.setVisible(true);
            }
        });
        // admins cannot stay logged in
        // check if we have valid token, login
        try{
            if(logWithToken(JwtHandler.getToken(false)).equals("ACCEPTED")){
                System.out.println("Logged with token");
                aftLogin.setVisible(true);
                createCartPane("add");
                dataErrorLabel.setVisible(false);
            }
        }catch (Exception e){
            dataErrorLabel.setVisible(true);
            dataErrorLabel.setText("No connection");
            System.out.println(e.getClass());
        }

    }

    //// sending requests

    private String logWithToken(String token) throws Exception{
        String path = "http://localhost:8080/auth/login";
        return RequestHandler.postRequest(path, token);
    }
    ///admin for admin user for user
    private String getAuthToken(String[] data, String type) throws Exception{
        String path = "http://localhost:8080/auth/"+type;
        String json = "{\"username\":\""+data[0]+"\",\"password\":\""+data[1]+"\"}";
        return RequestHandler.postRequest(path, json);
    }
    private String sendLoginData(String[] data) throws Exception {
        String path = "http://localhost:8080/user/user";
        String json = "{\"username\":\""+data[0]+"\",\"email\":\""+data[2]+"\",\"password\":\""+data[1]+"\",\"role\":\"user\"}";
        return RequestHandler.postRequest(path, json);
    }
    private boolean addProduct(String[] data, String token) throws Exception{
        String path = "http://localhost:8080/products";
        String json = "{\"name\":\""+data[0]+"\",\"description\":\""+data[1]+"\",\"price\":\""+data[2]+"\",\"image\":\""+data[3]+"\"}";
        RequestHandler.postWithToken(path, json, token);
        return true;
    }
    private void deleteProduct(String id, String token) throws Exception {
        String path = "http://localhost:8080/products/"+id;
        RequestHandler.deleteRequest(path, token);
    }
    private List<String[]> getProducts(String token) throws Exception {
        String path = "http://localhost:8080/products";
        return RequestHandler.getRequest(path, token);
    }
    private void addToCart(String product_id, String token) throws Exception{
        String path = "http://localhost:8080/cart/"+product_id;
        RequestHandler.postWithToken(path, token, token);
    }
    private void deleteFromCart(String product_id) throws Exception{
        String path = "http://localhost:8080/cart/product/"+product_id;
        RequestHandler.deleteRequest(path, JwtHandler.getToken(false));
    }
    private List<String[]> seeCartItems(String token) throws Exception{
        String path = "http://localhost:8080/cart/products";
        return RequestHandler.getRequest(path, token);
    }

    /// get texts from product manager
    private String[] getTexts(){
        return new String[]{
                productName.getText(),
                productDesc.getText(),
                productPrice.getText(),
                imagePath.getText().replaceAll("\\\\", "/"),
                productId.getText()
        };
    }
    private void clearTexts(){
        productName.setText("");
        productDesc.setText("");
        productPrice.setText("");
        imagePath.setText("");
        productId.setText("");
    }
    private String[] getLoginData(){
        return new String[]{
                nameField.getText(),
                passwordField.getText(),
                emailField.getText()
        };
    }
    private void updateList(){
        listOfProducts.getItems().clear();
        try {
            for(String[] list : getProducts(JwtHandler.getToken(true)))
            {
                listOfProducts.getItems().add(List.of(list));
            }
        } catch (Exception e) {
            System.out.println("No products");
        }
    }

    private void createCartPane(String type){
        try{
            productsPane.getChildren().clear();
            for(String[]s : type.equals("add") ? getProducts(JwtHandler.getToken(false)) : seeCartItems(JwtHandler.getToken(false))){
                productsPane.getChildren().add(createProductPane(s, type));
            }
            if (productsPane.getChildren().isEmpty() && type.equals("delete")){
                productsPane.getChildren().add(new Label("You dont have any products in your cart"));
            }
        }catch (Exception e){
            System.out.println("Could not create");
        }
    }

    private GridPane createProductPane(String[] data, String type){
        GridPane productPane = new GridPane();
        productPane.setMinSize(100, 100);
        productPane.setMaxSize(500, 500);
        productPane.setStyle("-fx-background-color: linear-gradient(#ABDCFF, #0396FF)");
        productPane.setHgap(15);
        TextField name = new TextField(data[0]);
        name.setEditable(false);
        TextArea desc = new TextArea(data[1]);
        desc.setEditable(false);
        desc.setWrapText(true);
        desc.setMaxWidth(300);
        TextField price = new TextField(data[2] + " PLN");
        price.setEditable(false);
        Image img = new Image("file:"+data[4]);
        if (img.isError()){
            img = new Image("file:src/main/resources/images/1.jpg");
        }
        ImageView imgView = new ImageView(img);
        imgView.setFitHeight(150);
        imgView.setFitWidth(150);
        Button add = new Button(type.toUpperCase());
        add.setMinWidth(80);
        add.setOnAction(event -> {
            try {
                if(type.equals("add")){
                    addToCart(data[3], JwtHandler.getToken(false));
                }else{
                    deleteFromCart(data[3]);
                    createCartPane("delete");
                }
            } catch (Exception e) {
                System.out.println("Could not add to cart");
            }
        });
        productPane.add(name, 1, 0);
        productPane.add(price, 2, 0);
        productPane.add(imgView, 0, 1);
        productPane.add(desc, 1, 1);
        productPane.add(add, 2, 1);
        return productPane;
    }


    /// adding random data for testing
    void addData(String token) throws Exception {
        List<String[]> products = new ArrayList<>();
        Random rand = new Random();

        String[] productNames = {
                "AlphaGadget", "BetaWidget", "GammaTool", "DeltaDevice", "EpsilonGear",
                "ZetaBox", "EtaMachine", "ThetaRig", "IotaKit", "KappaSystem",
                "LambdaUnit", "MuDrive", "NuEngine", "XiBoard", "OmicronHub",
                "PiFrame", "RhoStick", "SigmaChip", "TauMod", "UpsilonBlock"
        };
        String[] descriptions = {
                "Fast and reliable", "Lightweight design", "Durable and strong", "Compact and portable",
                "Advanced technology", "User-friendly interface", "High-performance", "Affordable solution",
                "Next-gen innovation", "Eco-friendly materials", "Optimized efficiency", "Customizable settings",
                "Versatile usage", "Enhanced features", "Improved stability", "Secure and safe", "Plug-and-play",
                "Low power consumption", "Ergonomic design", "Minimalist aesthetic"
        };
        for (int i = 0; i < 20; i++) {
            String name = productNames[i];
            String description;
            StringBuilder k = new StringBuilder();
            for(int z = 0; z < 5;z++){
                k.append(descriptions[rand.nextInt(descriptions.length)]).append(" and ");
            }
            description = k.toString();
            int number = rand.nextInt(500) + 1;

            String[] entry = new String[] {
                    name,
                    description,
                    String.valueOf(number),
                    "images/1.jpg"
            };

            products.add(entry);
        }
        for (String[] product : products) {
            System.out.println(addProduct(product, token));
        }
    }
    public static void main(String[] args)  throws Exception   {
        Main main = new Main();
        System.out.println(JwtHandler.getToken(false));
//        main.addData(JwtHandler.getToken(false));
        launch(args);
    }

}