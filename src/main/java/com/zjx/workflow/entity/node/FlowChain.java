package com.zjx.workflow.entity.node;


import com.zjx.workflow.entity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 流的链
 *
 * @author cgn
 * @date 2021/06/15
 */
@Data
@Table
@Entity
@DynamicInsert
@DynamicUpdate
public class FlowChain extends BaseEntity {

    /**
     * 名称
     */
    @Column
    String name;

    /**
     * 开始节点
     */
    @OneToOne
    Node startNode;



}
