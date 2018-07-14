package me.ianhe.isite.config;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Properties;

/**
 * 分页拦截器
 *
 * @author iHelin
 * @since 2017/11/10 14:58
 */
@Intercepts({@Signature(type = Executor.class, method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class PaginationInterceptor implements Interceptor {

    private static final int MAPPED_STATEMENT_INDEX = 0;
    private static final int PARAMETER_INDEX = 1;
    private static final int ROW_BOUNDS_INDEX = 2;

    private MysqlDialect dialect;

    @Override
    public Object intercept(Invocation invocation) throws Exception {
        Object[] queryArgs = invocation.getArgs();
        RowBounds rowBounds = (RowBounds) queryArgs[ROW_BOUNDS_INDEX];
        int offset = rowBounds.getOffset();
        int limit = rowBounds.getLimit();
        if (dialect.supportsLimit()) {
            if (offset != RowBounds.NO_ROW_OFFSET || limit != RowBounds.NO_ROW_LIMIT) {
                MappedStatement ms = (MappedStatement) queryArgs[MAPPED_STATEMENT_INDEX];
                StatementType statementType = ms.getStatementType();
                if (statementType == null || statementType == StatementType.CALLABLE) {
                    return invocation.proceed();
                }
                BoundSql boundSql = ms.getBoundSql(queryArgs[PARAMETER_INDEX]);
                String sql = createOffsetLimitSql(offset, limit, statementType, boundSql);
                if (StringUtils.isNotBlank(sql)) {
                    BoundSql newBoundSql = createBoundSql(ms, boundSql, sql, rowBounds);
                    MappedStatement newMs = createMappedStatement(ms, newBoundSql);
                    queryArgs[MAPPED_STATEMENT_INDEX] = newMs;
                    queryArgs[ROW_BOUNDS_INDEX] = new RowBounds();
                }
            }
        }
        return invocation.proceed();
    }

    /**
     * create offset limit sql
     *
     * @param offset
     * @param limit
     * @param statementType
     * @param boundSql
     * @return
     */
    private String createOffsetLimitSql(int offset, int limit, StatementType statementType, BoundSql boundSql) {
        String sql = boundSql.getSql().trim();
        if (dialect.supportsLimitOffset()) {
            sql = dialect.getLimitString(sql, offset, limit);
        } else {
            sql = dialect.getLimitString(sql, 0, limit);
        }
        return sql;
    }

    /**
     * create bound sql
     *
     * @param ms
     * @param boundSql
     * @param sql
     * @param rowBounds
     * @return
     */
    private BoundSql createBoundSql(MappedStatement ms, BoundSql boundSql, String sql, RowBounds rowBounds) {
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappings();
        Object parameterObj = boundSql.getParameterObject();
        BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, parameterMappingList, parameterObj);
        for (ParameterMapping mapping : boundSql.getParameterMappings()) {
            String prop = mapping.getProperty();
            if (boundSql.hasAdditionalParameter(prop)) {
                newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
            }
        }
        return newBoundSql;
    }

    /**
     * create mapped statement
     *
     * @param ms
     * @param newBoundSql
     * @return
     */
    private MappedStatement createMappedStatement(MappedStatement ms, final BoundSql newBoundSql) {
        Builder statementBuilder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(),
                parameterObject -> newBoundSql, ms.getSqlCommandType());
        statementBuilder.resource(ms.getResource());
        statementBuilder.fetchSize(ms.getFetchSize());
        statementBuilder.statementType(ms.getStatementType());
        statementBuilder.keyGenerator(ms.getKeyGenerator());
        statementBuilder.keyProperty(ArrayUtils.toString(ms.getKeyProperties(), null));
        statementBuilder.keyColumn(ArrayUtils.toString(ms.getKeyColumns(), null));
        statementBuilder.databaseId(ms.getDatabaseId());
        statementBuilder.timeout(ms.getTimeout());
        statementBuilder.parameterMap(new ParameterMap.Builder(ms.getConfiguration(), ms.getId(), List.class, newBoundSql
                .getParameterMappings()).build());
        statementBuilder.resultMaps(ms.getResultMaps());
        statementBuilder.resultSetType(ms.getResultSetType());
        statementBuilder.cache(ms.getCache());
        statementBuilder.flushCacheRequired(ms.isFlushCacheRequired());
        statementBuilder.useCache(ms.isUseCache());
        return statementBuilder.build();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        dialect = new MysqlDialect();
    }

}
