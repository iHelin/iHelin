package me.ianhe.isite.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import me.ianhe.isite.config.SystemProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * @author iHelin
 * @since 2019-07-27 21:16
 */
@Component
public class JwtComponent {

    @Autowired
    private SystemProperties systemProperties;

    /**
     * 用户登录成功后生成Jwt
     * 使用Hs256算法  私匙使用用户密码
     *
     * @return
     */
    public String createJWT(String subject) {
        //指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        //生成JWT的时间
        long currentTimeMillis = System.currentTimeMillis();
        Date now = new Date(currentTimeMillis);
        long expMillis = currentTimeMillis + systemProperties.getJwtExp();
        Date expiration = new Date(expMillis);
        //创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        //这里其实就是new一个JwtBuilder，设置jwt的body
        return Jwts.builder()
            .setSubject(subject)
            .setExpiration(expiration)
            .signWith(SignatureAlgorithm.HS256, systemProperties.getJwtSecret())
//            .setClaims(claims)
            .setId(UUID.randomUUID().toString())
            .setIssuedAt(now)
            .compact();
    }


    /**
     * Token的解密
     *
     * @param token 加密后的token
     * @return Claims
     */
    public Claims parseJWT(String token) {
        //得到DefaultJwtParser
        return Jwts.parser()
            //设置签名的秘钥
            .setSigningKey(systemProperties.getJwtSecret())
            //设置需要解析的jwt
            .parseClaimsJws(token).getBody();
    }
}
