package com.zjx.workflow.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Map;

/**
 * 条件
 *
 * @author cgn
 * @date 2021/06/15
 */
@Data
@Table
@Entity
@DynamicInsert
@DynamicUpdate
public class Conditions extends BaseEntity {
    /**
     * 校验字段
     */
    @Column
    private String fieldName;
    /**
     * 正则表达式
     */
    @Column
    private String reg;

    /**
     * 校验
     *
     * @param source
     * @return
     */
    public boolean check(Map<String, String> source) {
        String s = source.get(fieldName);
        if (s == null) {
            return false;
        }
        return s.matches(reg);
    }
}
