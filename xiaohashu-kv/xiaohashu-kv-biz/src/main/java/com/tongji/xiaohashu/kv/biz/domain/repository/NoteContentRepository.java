package com.tongji.xiaohashu.kv.biz.domain.repository;

import com.tongji.xiaohashu.kv.biz.domain.dataobject.NoteContentDO;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.UUID;

/**
 * @author tongji
 * @time 2025/3/21 10:05
 * @description
 */
public interface NoteContentRepository extends CassandraRepository<NoteContentDO, UUID> {

}
