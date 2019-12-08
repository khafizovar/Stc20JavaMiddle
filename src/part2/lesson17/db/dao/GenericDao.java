package part2.lesson17.db.dao;


import java.sql.Connection;
import java.util.List;

/**
 * @author KhafizovAR on 03.12.2019.
 * @project Stc20JavaMiddle
 */
public interface GenericDao<T> {
    public boolean add(T pojo);

    public T getById(Integer id);

    public boolean updateById(T pojo);

    public boolean deleteById(Integer id);

    public List<T> getAll();

    public boolean addAll(List<T> objs);

    public boolean addM(T obj, Connection conn);

    public boolean truncate();
}
