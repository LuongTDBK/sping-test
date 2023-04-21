package vn.neo.service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import vn.neo.constant.Constants;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtilsService {
    @Value("${application.service.token-expired-time-in-hour:1}" )
    public final int EXPIRED_TIME =1 ;
    public String exactUserName (String token){
        return exactClaim(token, Claims::getSubject);
    }
    private <T> T exactClaim (String token, Function<Claims, T> claimsResolver){
        return claimsResolver.apply(exactAllClaim(token));
    }
    private Claims exactAllClaim(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey(){
        byte[] keyByte = Decoders.BASE64.decode(Constants.SECRET_KEY);
        return Keys.hmacShaKeyFor(keyByte);
    }

    /**
     * generate token
     */
    public String generateToken (UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }
    private String generateToken (Map<String , Object> exactClaims,
                                 UserDetails userDetails){
        return Jwts
                .builder()
                .setClaims(exactClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 *60 *60 * EXPIRED_TIME))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * valid Token
     */
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = exactUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token)) ;
    }
    // compare expired Date of token with current !
    private boolean isTokenExpired(String token){
        Date expiredDate = exactClaim(token, Claims::getExpiration);
        return expiredDate.before(new Date());
    }


}
