package ch.bytecrowd.dokupository.gui.models;

import ch.bytecrowd.dokupository.models.interfaces.Keyable;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;

public class ViewEntity<T extends Keyable, K> extends RecursiveTreeObject<K> {

    private SimpleLongProperty id = new SimpleLongProperty();
    private SimpleBooleanProperty active = new SimpleBooleanProperty();

    public ViewEntity() {
    }

    public ViewEntity(T entity) {
        this.id = new SimpleLongProperty(entity.getId());
    }

    public K toViewEntity(T t) {

        return (K) new ViewEntity<T, K>(t);
    }

    public void fillEntity(T t) {

        t.setId(getId());
    }

    public long getId() {
        return id.get();
    }

    public void setId(Long id) {
        this.id.set(id);
    }

    public SimpleLongProperty idProperty() {
        return id;
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public boolean isActive() {
        return active.get();
    }

    public void setActive(boolean active) {
        this.active.set(active);
    }

    public SimpleBooleanProperty activeProperty() {
        return active;
    }

    @Override
    public String toString() {
        return "ViewEntity{" +
                "id=" + id.getValue() +
                ", active=" + active.getValue() +
                '}';
    }
}