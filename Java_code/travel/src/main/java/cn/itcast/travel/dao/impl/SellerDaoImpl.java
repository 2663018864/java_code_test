package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class SellerDaoImpl implements SellerDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 查询旅游线路的商家信息
     * @param sid
     * @return
     */
    @Override
    public Seller findRouteSellerBySid(int sid) {
        try {
            //定义sql
            String sql = "select * from tab_seller where sid = ?";
            Seller seller = template.queryForObject(sql, new BeanPropertyRowMapper<Seller>(Seller.class), sid);
            return seller;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }

    }
}
