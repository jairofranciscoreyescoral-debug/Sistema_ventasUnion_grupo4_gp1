module pe.edu.upeu.taller_git_gp1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.desktop;
    requires javafx.base;
    requires javafx.graphics;

    //activar esta cosa
    requires org.xerial.sqlitejdbc;

    opens pe.edu.upeu.taller_git_gp1 to javafx.fxml;

    opens pe.edu.upeu.taller_git_gp1.modelo to javafx.base,javafx.fxml;//agregamos
    opens pe.edu.upeu.taller_git_gp1.controller to javafx.fxml;//agregamos
    opens pe.edu.upeu.taller_git_gp1.views to javafx.fxml;//agregamos

    exports pe.edu.upeu.taller_git_gp1;
    //añadimos los paquetes del proyecto

    exports pe.edu.upeu.taller_git_gp1.utils;
    exports pe.edu.upeu.taller_git_gp1.test;
    exports pe.edu.upeu.taller_git_gp1.modelo;
    exports pe.edu.upeu.taller_git_gp1.interfaces;
    exports pe.edu.upeu.taller_git_gp1.implementacion;
    exports pe.edu.upeu.taller_git_gp1.controller;

}