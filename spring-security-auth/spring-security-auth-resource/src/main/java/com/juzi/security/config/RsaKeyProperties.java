package com.juzi.security.config;


import com.juzi.security.utils.RsaUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.security.PublicKey;

@ConfigurationProperties(prefix = "auth.rsa")
@Slf4j
public class RsaKeyProperties {

    private String pubKeyFile;

    private PublicKey publicKey;

    @PostConstruct
    public void init() {
        try {
            publicKey = RsaUtils.getPublicKey(pubKeyFile);
        }catch (Exception e) {
            e.printStackTrace();
            log.error("密钥对初始化失败");
        }
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public String getPubKeyFile() {
        return pubKeyFile;
    }

    public void setPubKeyFile(String pubKeyFile) {
        this.pubKeyFile = pubKeyFile;
    }
}
