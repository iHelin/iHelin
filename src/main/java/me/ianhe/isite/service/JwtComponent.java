package me.ianhe.isite.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import me.ianhe.isite.config.SystemProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
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
    public String createJWT(Map<String, Object> claims, String subject) {
        //指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        //生成JWT的时间
        long currentTimeMillis = System.currentTimeMillis();
        Date now = new Date(currentTimeMillis);
        long expMillis = currentTimeMillis + systemProperties.getJwtExp();
        Date expiration = new Date(expMillis);
        //创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        //这里其实就是new一个JwtBuilder，设置jwt的body
        return Jwts.builder()
            //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
            .setClaims(claims)
            //设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
            .setId(UUID.randomUUID().toString())
            //iat: jwt的签发时间
            .setIssuedAt(now)
            //代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roleid之类的，作为用户的唯一标志。
            .setSubject(subject)
            //设置签名使用的签名算法和签名使用的秘钥
            .signWith(SignatureAlgorithm.HS256, systemProperties.getJwtSecret())
            //设置过期时间
            .setExpiration(expiration).compact();
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
