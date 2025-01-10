module org.example.nrp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires annotations;
    requires java.sql;

    opens org.example.nrp to javafx.fxml;
    exports org.example.nrp;
    exports org.example.nrp.visualLayer;
    opens org.example.nrp.visualLayer to javafx.fxml;
    exports org.example.nrp.dataLayer;
    opens org.example.nrp.dataLayer to javafx.fxml;
}