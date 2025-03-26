package com.tongji.xiaohashu.note.biz.rpc;

import com.tongji.xiaohashu.distributed.id.generator.api.DistributedIdGeneratorFeignApi;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * @author tongji
 * @time 2025/3/24 15:58
 * @description ID 生成服务
 */
@Component
public class DistributedIdGeneratorRpcService {
    @Resource
    private DistributedIdGeneratorFeignApi distributedIdGeneratorFeignApi;

    public String getSnowflakeId() {
        return distributedIdGeneratorFeignApi.getSnowflakeId("tongji");
    }
}
