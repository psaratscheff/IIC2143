/**
  Copyright (C) 2015  Diego Cirujano Cuesta

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License along
    with this program; if not, write to the Free Software Foundation, Inc.,
    51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package ciruman;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.StackPane;

/**
 * ListCell that returns cells with ellipsis(...) applied automatically because
 * the default functionality of JavaFX ListViews is a horizontal scroll.
 *
 * @author Diego Cirujano
 */
public class EllipsisListCell extends ListCell<String> {

    private final Label label = new Label();
    private final StackPane pane;

    public EllipsisListCell() {
        pane = new StackPane();
        pane.setMinWidth(0);
        pane.setPrefWidth(1);
        pane.getChildren().add(label);
        pane.setAlignment(Pos.TOP_LEFT);
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        if (empty || item == null) {
            setGraphic(null);
            setText("");
        } else {
            label.setText(item);
            setGraphic(pane);
        }
    }
}