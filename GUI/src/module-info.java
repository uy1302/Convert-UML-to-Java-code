/**
 * 
 */
/**
 * 
 */
module GUI {
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.controls;
	exports main;
	opens main to javafx.graphics, javafx.fxml;
}