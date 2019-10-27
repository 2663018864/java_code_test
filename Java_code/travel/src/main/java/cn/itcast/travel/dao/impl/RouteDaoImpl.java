package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 查询总记录数
     * @param cid
     * @param rname
     * @return
     */
    @Override
    public int findTotalCount(int cid, String rname) {
        //定义sql语句
        String sql = "select count(*) from tab_route where 1 = 1 ";
        List list = new ArrayList();
        //动态获取sql
        sql = getSql(sql, list, cid, rname).toString();
//        System.out.println(sql);
        return template.queryForObject(sql, Integer.class, list.toArray());
    }

    /**
     * 分页查询旅游线路
     * @param cid
     * @param start
     * @param pageSize
     * @param rname
     * @return
     */
    @Override
    public List<Route> findByPage(int cid, int start, int pageSize, String rname) {
        //定义sql
        String sql = "select * from tab_route where 1 = 1 ";
        List list = new ArrayList();
        //动态获取sql
        StringBuilder sb = getSql(sql, list, cid, rname);
        sb.append(" limit ?, ? ");
        list.add(start);
        list.add(pageSize);
        sql = sb.toString();
//        System.out.println(sql);
        return template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), list.toArray());
    }

    /**
     * 查询旅游线路，通过rid
     * @param rid
     * @return
     */
    @Override
    public Route findRouteByRid(String rid) {
        try {
            //定义sql
            String sql = "select * from tab_route where rid = ?";
            Route route = template.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rid);
            return route;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取动态sql
     * @param sql
     * @param list
     * @param cid
     * @param rname
     * @return
     */
    public StringBuilder getSql(String sql, List list, int cid, String rname) {
        StringBuilder sb = new StringBuilder(sql);
        if (cid != 0) {
            sb.append(" and cid = ? ");
            list.add(cid);
        }
        if (rname != null && !"".equals(rname) && !"null".equals(rname)) {
            sb.append(" and rname like ? ");
            list.add("%"+rname+"%");
        }

        return sb;
    }
}
