package net.exam.assess.lrxexam.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import net.exam.assess.lrxexam.service.TokenGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @Aytor lrx
 * @Date 2021-09-05
 * token generator,
 * just mock implement, which was not effective.
 *
 */
@Service
public class TokenMockGenerator implements TokenGenerator {

    private static Logger LOGGER = LoggerFactory.getLogger(TokenMockGenerator.class);

    private static final int MAX_REQ_PER_MILLSEC = 1000;
    private static final int EXPIRE_TIME = 1;
    private static final TimeUnit EXPIRE_TIME_UNIT = TimeUnit.MILLISECONDS;
    private static final int MAX_RETRY_NUM = 10;

    private Cache<String, Boolean> idCache;
    private Object _generate_lock = new Object();

    public TokenMockGenerator() {
        super();
        init();
    }


    @Override
    public String randomId() {
        return blockAndGenerateId();
    }

    public String blockAndGenerateId() {
        synchronized (_generate_lock) {
            int try_cnt = 0;
            while (try_cnt < MAX_RETRY_NUM) {
                String newId = tryGenerateId();
                if (idCache.getIfPresent(newId) == null) {
                    idCache.put(newId, true);
                    return newId;
                }
            }
            return null;
        }
    }


    private String tryGenerateId() {
        StringBuilder idBuff = new StringBuilder();
        idBuff.append(System.currentTimeMillis());
        int seq = new Random(System.currentTimeMillis()).nextInt(MAX_REQ_PER_MILLSEC);
        idBuff.append(new DecimalFormat("000").format(seq));
        return idBuff.toString();
    }

    private void init() {
        initCache();
    }

    private void initCache() {
        idCache = createCache();
    }

    private Cache<String, Boolean> createCache() {
        Cache<String, Boolean> idCache = CacheBuilder.newBuilder()
                .maximumSize(MAX_REQ_PER_MILLSEC)
                .expireAfterWrite(EXPIRE_TIME, EXPIRE_TIME_UNIT)
                .build(new CacheLoader<String, Boolean>() {
                    @Override
                    public Boolean load(String s) throws Exception {
                        return null;
                    }
                });
        return idCache;
    }
}
