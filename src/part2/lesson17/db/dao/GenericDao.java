package part2.lesson17.db.dao;


import java.util.List;
import java.util.Optional;

/**
 * @author KhafizovAR on 03.12.2019.
 * @project Stc20JavaMiddle
 */
public interface GenericDao<T> {
    Optional<T> add(T pojo);

    Optional<T> getById(Integer id);

    Optional<T> updateById(T pojo);

    boolean deleteById(Integer id);

    List<T> getAll();

    List<T> addAll(List<T> objs);

    boolean truncate();
}