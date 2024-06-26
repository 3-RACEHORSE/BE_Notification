package com.sparos4th2.alarm.infrastructure.mongotemplate.impl;

import com.sparos4th2.alarm.domain.Alarm;
import com.sparos4th2.alarm.infrastructure.mongotemplate.CustomAlarmRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CustomAlarmRepositoryImpl implements CustomAlarmRepository {
    private final MongoTemplate mongoTemplate;

    @Override
    public Page<Alarm> findAllAlarm(String receiverUuid, Pageable pageable) {
        Criteria criteria = new Criteria();

        // 수신자 일치, alarm 내림차순
        Query query = new Query(criteria).with(pageable)
                .skip(pageable.getPageSize() * pageable.getPageNumber())
                .limit(pageable.getPageSize())
                .with(Sort.by(Sort.Order.desc("alarmTime"))); // alarmTime 내림차순 정렬

        log.info("Query >>> {}", query);

        List<Alarm> alarms = mongoTemplate.find(query, Alarm.class);

        return PageableExecutionUtils.getPage(
                alarms,
                pageable,
                () -> mongoTemplate.count(query.skip(-1).limit(-1), Alarm.class)
        );    }
}
