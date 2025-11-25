module org.oniteam.oregontrailfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens org.oniteam.oregontrailfx to javafx.fxml;
    exports org.oniteam.oregontrailfx;
}