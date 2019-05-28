package cn.tycoding.api.auth.endpoint;

import cn.tycoding.api.common.constant.enums.CommonEnums;
import cn.tycoding.api.common.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tycoding
 * @date 2019-05-26
 */
@Slf4j
@RestController
@RequestMapping("/token")
public class SctTokenEndpoint {

    @Autowired
    private TokenStore tokenStore;

    @DeleteMapping("/logout")
    public Result logout(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader) {
        log.info("Logout  >>  ....");
        if (StringUtils.isBlank(authHeader)) {
            return new Result(CommonEnums.LOGOUT_ERROR);
        }
        String tokenValue = authHeader.replace(OAuth2AccessToken.BEARER_TYPE.toLowerCase(), "").trim();
        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(tokenValue);
        if (oAuth2AccessToken == null || StringUtils.isBlank(oAuth2AccessToken.getValue())) {
            return new Result(CommonEnums.LOGOUT_ERROR);
        }
        tokenStore.removeAccessToken(oAuth2AccessToken);
        return new Result();
    }
}
