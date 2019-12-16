package part2.lesson15.db.dao;

import part2.lesson15.db.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * @author KhafizovAR on 03.12.2019.
 * @project Stc20JavaMiddle
 */
public interface GenericDao<T> {
    public Optional<T> add(T pojo);

    public Optional<T> getById(Integer id);

    public Optional<T> updateById(T pojo);

    public boolean deleteById(Integer id);

    public List<T> getAll();

    public boolean addAll(List<T> objs);

    public boolean truncate();
}
