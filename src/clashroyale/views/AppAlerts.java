package clashroyale.views;

import javafx.scene.control.Alert;

/**
 * The type App alerts.
 */
public class شAppAlerts {
    private String title;
    private String headerText;
    private String contentText;

    /**
     * Instantiates a new App alerts.
     *
     * @param title       the title
     * @param headerText  the header text
     * @param contentText the content text
     */
    public AppAlerts(String title, String headerText, String contentText) {
        this.title = title;
        this.headerText = headerText;
        this.contentText = contentText;
    }

    /**
     * Show information alert.
     */
    public void showInformationAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
