package interfaces;

import java.util.ArrayList;
import java.util.Optional;

public interface DAO<T> {

    Optional<T> get(long id);

    ArrayList<T> getAll();

    void save(T t);

    void update(T t, String[] params);

    void delete(T t);


}
