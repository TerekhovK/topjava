package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepositoryImpl implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;


    @Autowired
    public JdbcUserRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
            batchUpdate(user);
        } else {
            namedParameterJdbcTemplate.update(
                    "UPDATE users SET name=:name, email=:email, password=:password, " +
                            "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", parameterSource);
            jdbcTemplate.update("DELETE FROM user_roles WHERE USER_ID=? ", user.getId());
            batchUpdate(user);

        }
        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> user = jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id);
        setRoles(DataAccessUtils.singleResult(user));
        return DataAccessUtils.singleResult(user);
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> user = jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        setRoles(DataAccessUtils.singleResult(user));
        return DataAccessUtils.singleResult(user);
    }

    @Override
    public List<User> getAll() {
        List<User> users = jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER);
        Map<Integer, Set<Role>> roleMap = new HashMap<>();
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("SELECT * FROM user_roles");
        while (rowSet.next()) {
            roleMap.computeIfAbsent(rowSet.getInt("user_id"), (id) -> EnumSet.noneOf(Role.class));
            roleMap.get(rowSet.getInt("user_id")).add(Role.valueOf(rowSet.getString("role")));
        }
        users.forEach(k -> k.setRoles(roleMap.get(k.getId())));
        return users;
    }

    protected void setRoles(User u) {
        if (u != null) {
            List<Role> list = jdbcTemplate.query("SELECT * FROM user_roles WHERE user_id=?",
                    (rs, rowCount) -> (Role.valueOf(rs.getString("role"))),
                    u.getId());
            u.setRoles(EnumSet.copyOf(list));
        }
    }

    protected void batchUpdate(User user) {
        if (!CollectionUtils.isEmpty(user.getRoles())) {

            jdbcTemplate.batchUpdate("INSERT INTO user_roles (user_id, role) VALUES (?,?)", new BatchPreparedStatementSetter() {
                List<Role> roles = new ArrayList<>(user.getRoles());

                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Role role = roles.get(i);
                    ps.setInt(1, user.getId());
                    ps.setString(2, role.toString());
                }

                @Override
                public int getBatchSize() {
                    return roles.size();
                }
            });
        }

    }
}
